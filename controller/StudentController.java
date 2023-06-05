package adaptiveschool.controller;

import adaptiveschool.dto.EditUserDTO;
import adaptiveschool.dto.StudentDTO;
import adaptiveschool.dto.TeacherDTO;
import adaptiveschool.model.Student;
import adaptiveschool.model.User;
import adaptiveschool.repository.StudentRepository;
import adaptiveschool.service.StudentService;
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
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @NonNull
    private StudentRepository studentRepository;

    @NonNull
    StudentService studentService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public GeneralResponseWrapper<Student> addStudent(
            @ApiParam(value = "Student object", required = true) @RequestBody StudentDTO student) {
        LOGGER.info("Creating student [{} {}]", student.getLastname(), student.getFirstname());
        return new GeneralResponseWrapper<>(Status.of(HttpStatus.CREATED), studentService.addOne(student));
    }

    @GetMapping("/{idStudent}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or (hasRole('USER') and principal.id == #idStudent)")
    public GeneralResponseWrapper<StudentDTO>getStudent(
            @ApiParam(value = "ID of student", required = true) @PathVariable int idStudent) {
        return new GeneralResponseWrapper<>(Status.of(HttpStatus.OK), studentService.getOne(studentRepository.findById(idStudent).get()));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @GetMapping("/classes/{idClass}")
    public GeneralResponseWrapper<List<StudentDTO>> getStudentsByClass(
            @ApiParam(value = "id of class", required = true) @PathVariable int idClass){
        return new GeneralResponseWrapper<>(Status.of(HttpStatus.OK), studentService.getAll(studentRepository.findByClazzId(idClass)));
    }

    @PutMapping("/{idStudent}")
    @PreAuthorize("hasRole('USER') and principal.id == #idStudent")
    public GeneralResponseWrapper<User> updateStudent(
            @ApiParam(value = "User object", required = true)  @RequestBody EditUserDTO student,
            @ApiParam(value = "ID of pupil", required = true)  @PathVariable int idStudent){
        LOGGER.info("Updating student [{} {}]", student.getLastname(), student.getFirstname());

        return new GeneralResponseWrapper<>(Status.of(HttpStatus.OK) , studentService.updateStudent(studentRepository.findById(idStudent).get(), student));
    }
}