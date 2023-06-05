package adaptiveschool.controller;

import adaptiveschool.service.ClassService;
import adaptiveschool.wrapper.GeneralResponseWrapper;
import adaptiveschool.wrapper.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import adaptiveschool.dto.ClassDTO;
import adaptiveschool.dto.NYTransitionDTO;
import adaptiveschool.service.StudentService;
import io.swagger.annotations.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/students/transition")
@RequiredArgsConstructor
public class NYTransitionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NYTransitionController.class);

    @NonNull
    private ClassService classService;
    @NonNull
    private StudentService studentService;

    public GeneralResponseWrapper<List<ClassDTO>> addNewYearClasses(@RequestBody List<ClassDTO> classDTOS){
        LOGGER.info("Add classes for new academic year");
        return new GeneralResponseWrapper<>(Status.of(HttpStatus.CREATED), classService.addNewYearClasses(classDTOS));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public GeneralResponseWrapper<List<NYTransitionDTO>> bindingStudentsToNewClasses(
            @ApiParam(value = "Transition of the class(new and old classes ID)", required = true)
            @RequestBody List<NYTransitionDTO> transitionDTOS
    ){
        LOGGER.info("Update old year class status, rebind students to new classes.");
        classService.updateClassStatusById(transitionDTOS, false);
        studentService.studentClassesRebinding(transitionDTOS);
        return new GeneralResponseWrapper<>(Status.of(HttpStatus.CREATED), transitionDTOS);
    }
}