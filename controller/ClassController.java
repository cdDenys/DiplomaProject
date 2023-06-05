package adaptiveschool.controller;

import adaptiveschool.dto.ClassDTO;
import adaptiveschool.service.ClassServiceImpl;
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
@RequestMapping("/classes")
@RequiredArgsConstructor
public class ClassController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassController.class);

	@NonNull
    private ClassServiceImpl classService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public GeneralResponseWrapper<ClassDTO> addClass(
            @ApiParam(value = "Class object", required = true) @RequestBody ClassDTO newClassDTO){
        LOGGER.info("Add class [{} - {}]", newClassDTO.getClassName(), newClassDTO.getClassYear());
        return new GeneralResponseWrapper<>(
                Status.of(HttpStatus.CREATED),
                classService.saveClass(newClassDTO));
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('TEACHER') and @securityExpressionService.teachesInClass(principal.id, #classId)) " +
            "or (hasRole('USER') and @securityExpressionService.isMemberOfClass(principal.id, #classId))")
    @GetMapping("/{classId}")
    public GeneralResponseWrapper<ClassDTO> getClassById(
            @ApiParam(value = "ID of class", required = true)
            @PathVariable int classId
    ){
        return new GeneralResponseWrapper<>(
                Status.of(HttpStatus.OK),
                classService.findClassById(classId)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @GetMapping
    public GeneralResponseWrapper<List<ClassDTO>> getAllClasses(
            @ApiParam(value = "Only the classes that learn the subject with specified ID will be returned")
            @RequestParam(required = false) Integer subjectId
    ){
        if (subjectId == null) {
            return new GeneralResponseWrapper<>(
                    Status.of(HttpStatus.OK),
                    classService.getAllClasses()
            );
        } else {
            return new GeneralResponseWrapper<>(
                    Status.of(HttpStatus.OK),
                    classService.getClassesBySubject(subjectId)
            );
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public GeneralResponseWrapper<ClassDTO> editClass(
            @ApiParam(value = "ID of class", required = true) @PathVariable int id,
            @ApiParam(value = "Class object", required = true) @RequestBody ClassDTO editableClass
    ){
        LOGGER.info("Update class [id={}]", id);
        return new GeneralResponseWrapper<>(
                Status.of(HttpStatus.CREATED),
                classService.updateClass(id, editableClass)
        );
    }
}