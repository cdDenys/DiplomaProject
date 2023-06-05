package adaptiveschool.controller;

import adaptiveschool.dto.EditUserDTO;
import adaptiveschool.dto.TeacherDTO;
import adaptiveschool.model.User;
import adaptiveschool.repository.StudentRepository;
import adaptiveschool.repository.UserRepository;
import adaptiveschool.service.StudentService;
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

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminEditUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);

    @NonNull
    private StudentRepository studentRepository;

    @NonNull
    private UserRepository userRepository;

    @NonNull
    private StudentService studentService;

    @NonNull
    private TeacherService teacherService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/students/{idStudent}")
    public GeneralResponseWrapper<User> updateStudent(
            @ApiParam(value = "User object.", required = true) @RequestBody EditUserDTO student,
            @ApiParam(value = "ID of the pupil.", required = true) @PathVariable int idStudent
    ){
        LOGGER.info("Updating student [{} {}]", student.getLastname(), student.getFirstname());
        return new GeneralResponseWrapper<>(
                Status.of(HttpStatus.OK),
                studentService.adminUpdateStudent(studentRepository.findById(idStudent).get(), student)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/teachers/{idTeacher}")
    public GeneralResponseWrapper<TeacherDTO> updateTeacher(
            @ApiParam(value = "User object.", required = true) @RequestBody EditUserDTO teacher,
            @ApiParam(value = "ID of the teacher.", required = true) @PathVariable int idTeacher
    ){
        LOGGER.info("Updating teacher [{} {}]", teacher.getLastname(), teacher.getFirstname() + "created");

        return new GeneralResponseWrapper<>(
                Status.of(HttpStatus.OK),
                teacherService.adminUpdateTeacher(userRepository.findById(idTeacher).get(), teacher)
        );
    }
}
