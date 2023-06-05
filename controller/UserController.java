package adaptiveschool.controller;

import adaptiveschool.dto.EditUserDTO;
import adaptiveschool.repository.UserRepository;
import adaptiveschool.service.PasswordDecodeService;
import adaptiveschool.service.UserService;
import adaptiveschool.service.base.CredentialsMailingServiceBase;
import adaptiveschool.wrapper.GeneralResponseWrapper;
import adaptiveschool.wrapper.Status;
import io.swagger.annotations.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @NonNull
    private UserRepository userRepository;

    @NonNull
    private PasswordDecodeService passwordDecodeService;

    @NonNull
    private UserService userService;

    @NonNull
    private CredentialsMailingServiceBase credentialsMailingService ;

    @GetMapping("/credentials/teachers")
    @PreAuthorize("hasRole('ADMIN')")
    public GeneralResponseWrapper<Void> emailTeachersCredentials(){
        credentialsMailingService.sendTeachersCredentials();
        return new GeneralResponseWrapper<>(Status.of(HttpStatus.OK), null);
    }
    
    @GetMapping("/credentials/students")
    @PreAuthorize("hasRole('ADMIN')")
    public GeneralResponseWrapper<Void> emailStudentsCredentials(@RequestParam Integer classId){
        credentialsMailingService.sendStudentsCredentials(classId);
        return new GeneralResponseWrapper<>(Status.of(HttpStatus.OK), null);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{idUser}")
    public ResponseEntity<EditUserDTO> deactivate(@ApiParam(value = "ID of user", required = true) @PathVariable int idUser){
        return ResponseEntity.ok(userService.deactivate(idUser));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'USER')")
    @RequestMapping(value = "/email/{mail}/", method = RequestMethod.HEAD)
    @ResponseBody
    public GeneralResponseWrapper<EditUserDTO> getByMail(@PathVariable String mail){
        return new GeneralResponseWrapper(Status.of(HttpStatus.OK), userService.getUserByEmail(mail));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/login/{login}/", method = RequestMethod.HEAD)
    @ResponseBody
    public GeneralResponseWrapper<EditUserDTO> getByLogin(@PathVariable String login){
        return new GeneralResponseWrapper(Status.of(HttpStatus.OK), userService.getUserByLogin(login));
    }
}