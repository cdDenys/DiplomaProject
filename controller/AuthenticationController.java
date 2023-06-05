package adaptiveschool.controller;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import adaptiveschool.dto.PasswordResetDTO;
import adaptiveschool.security.JwtAuthenticationRequest;
import adaptiveschool.security.JwtTokenUtil;
import adaptiveschool.security.JwtUser;
import adaptiveschool.security.exceptions.TokenGlobalTimeExpiredException;
import adaptiveschool.service.PasswordResetService;
import adaptiveschool.wrapper.GeneralResponseWrapper;
import adaptiveschool.wrapper.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

@RestController
public class AuthenticationController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${jwt.token.header}")
    private String tokenHeader;

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;

    private AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtTokenUtil;

    private UserDetailsService userDetailsService;
    
    private PasswordResetService passwordResetService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                                    @Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService,
                                    PasswordResetService passwordResetService){
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("signin")
    public ResponseEntity<?> createAuthenticationToken(
            @ApiParam(value = "Login and Password", required = true)
            @RequestBody JwtAuthenticationRequest authenticationRequest
    ) {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final JwtUser userDetails = (JwtUser) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        HttpHeaders headers = new HttpHeaders();
        headers.add(tokenHeader, tokenPrefix + token);
        logger.info("User {} successfully authenticated", authenticationRequest.getUsername());

        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) throws TokenGlobalTimeExpiredException {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(tokenHeader, tokenPrefix + jwtTokenUtil.refreshToken(token));
            logger.info("Token refresed for user {}", username);
            return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
        } else {
            logger.warn("Token global lifetime expired, token was issued ", jwtTokenUtil.getIssuedAtDateFromToken(token));
            throw new TokenGlobalTimeExpiredException("Token global lifetime expired");
        }
    }
    
    @GetMapping("/requestPasswordReset")
    @ResponseBody
    public GeneralResponseWrapper<String> recoverPassword(
            @ApiParam(value = "Login or email", required = true) @RequestParam String query){
        return new GeneralResponseWrapper<String>(
                Status.of(HttpStatus.OK),
                passwordResetService.trySendPasswordResetEmail(query));
    }
    
    @PutMapping("/resetPassword")
    @ResponseBody
    public GeneralResponseWrapper<String> updatePassword(@RequestBody PasswordResetDTO passwordDTO){
        return new GeneralResponseWrapper<String>(
                Status.of(HttpStatus.OK), 
                passwordResetService.tryChangePassword(passwordDTO));
    }

    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}