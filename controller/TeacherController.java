package adaptiveschool.controller;

import adaptiveschool.dto.EditUserDTO;
import adaptiveschool.dto.TeacherDTO;
import adaptiveschool.repository.TeacherRepository;
import adaptiveschool.repository.UserRepository;
import adaptiveschool.service.TeacherService;
import adaptiveschool.wrapper.GeneralResponseWrapper;
import adaptiveschool.wrapper.Status;
import io.swagger.annotations.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);

    @NonNull
    private TeacherRepository teacherRepository;

    @NonNull
    private UserRepository userRepository;

    @NonNull
    private TeacherService teacherService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public GeneralResponseWrapper<List<TeacherDTO>> getall() {
        return new GeneralResponseWrapper<>(Status.of(HttpStatus.OK), teacherService.getAll(teacherRepository.findAll()));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public GeneralResponseWrapper<TeacherDTO> addTeacher(
            @ApiParam(value = "Teacher object", required = true) @RequestBody TeacherDTO teacher) {
        LOGGER.info("Creating teacher [{} {}]", teacher.getLastname(), teacher.getFirstname());
        return new GeneralResponseWrapper<>(Status.of(HttpStatus.OK), teacherService.addOne(teacher));
    }

    @GetMapping("/{idTeacher}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('TEACHER') and principal.id == #idTeacher)")
    public GeneralResponseWrapper<TeacherDTO> getTeacher(
            @ApiParam(value = "ID of teacher", required = true) @PathVariable int idTeacher) {
        return new GeneralResponseWrapper<>(Status.of(HttpStatus.OK), teacherService.getOne(teacherRepository.findById(idTeacher).get()));
    }

    @PutMapping("/{idTeacher}")
    @PreAuthorize("hasRole('TEACHER') and principal.id == #idTeacher")
    public GeneralResponseWrapper<TeacherDTO> updateTeacher(
            @ApiParam(value = "User object", required = true) @RequestBody EditUserDTO teacher,
            @ApiParam(value = "ID of teacher", required = true) @PathVariable int idTeacher) {
        LOGGER.info("Updating teacher [{} {}]", teacher.getLastname(), teacher.getFirstname() + "created");
        return new GeneralResponseWrapper<>(Status.of(HttpStatus.OK), teacherService.updateTeacher(userRepository.findById(idTeacher).get(), teacher));
    }
}