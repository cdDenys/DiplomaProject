package adaptiveschool.controller;

import adaptiveschool.dto.DataForLoginDTO;
import adaptiveschool.service.LoginGeneratorService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/login")
@Api(description = "Operation to create login")
public class LoginGeneratorController {
    @Autowired
    LoginGeneratorService loginGeneratorService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/generate")
    public DataForLoginDTO getLogin(
            @ApiParam(value = "Data for login", required = true)
            @RequestBody DataForLoginDTO data
    ){
        return loginGeneratorService.generateLogin(data);
    }
}