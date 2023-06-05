package adaptiveschool.controller;

import adaptiveschool.dto.AddedUsersDTO;
import adaptiveschool.dto.ClassDTO;
import adaptiveschool.service.PasswordDecodeService;
import adaptiveschool.wrapper.GeneralResponseWrapper;
import adaptiveschool.wrapper.Status;
import io.swagger.annotations.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users/credentials")
@RequiredArgsConstructor
public class PasswordDecodeController {
    @NonNull
    private PasswordDecodeService passwordDecodeService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public GeneralResponseWrapper<List<AddedUsersDTO>> getAll(){
        return new GeneralResponseWrapper<>(Status.of(OK), passwordDecodeService.decodemultiple());
    }
}