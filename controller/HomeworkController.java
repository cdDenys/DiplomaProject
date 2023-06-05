package adaptiveschool.controller;

import adaptiveschool.dto.HomeworkDTO;
import adaptiveschool.dto.HomeworkFileDTO;
import adaptiveschool.service.JournalServiceImpl;
import adaptiveschool.wrapper.GeneralResponseWrapper;
import adaptiveschool.wrapper.Status;
import io.swagger.annotations.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/homeworks")
@RequiredArgsConstructor
public class HomeworkController {

    @NonNull
    private JournalServiceImpl journalServiceImpl;

    @GetMapping("/subjects/{idSubject}/classes/{idClass}")
    @PreAuthorize("(hasRole('TEACHER') and @securityExpressionService.hasLessonsInClass(principal.id, #idClass, #idSubject)) or hasRole('ADMIN')")
    public GeneralResponseWrapper<List<HomeworkDTO>> getHomeworks(
            @ApiParam(value = "ID of subject", required = true) @PathVariable int idSubject,
            @ApiParam(value = "ID of class", required = true) @PathVariable int idClass
    ){
        return new GeneralResponseWrapper<>(
                Status.of(HttpStatus.OK),
                journalServiceImpl.getHomework(idSubject,idClass)
        );
    }

    @PutMapping("/files")
    @PreAuthorize("(hasRole('TEACHER') and @securityExpressionService.hasLessonsInClass(principal.id, #homeworkFileDTO.idLesson)) or hasRole('ADMIN')")
    public GeneralResponseWrapper<HomeworkFileDTO> postHomework(
            @ApiParam(value = "Homework object", required = true)
            @RequestBody HomeworkFileDTO homeworkFileDTO
    ){
        journalServiceImpl.saveHomework(homeworkFileDTO);
        return new GeneralResponseWrapper<>(Status.of(HttpStatus.NO_CONTENT), null);
    }

    @GetMapping("/files/{idLesson}")
    @PreAuthorize("(hasRole('USER') and @securityExpressionService.isAttendingLesson(principal.id, #idLesson))"
            + " or (hasRole('TEACHER') and @securityExpressionService.hasLessonsInClass(principal.id, #idLesson))"
            + " or hasRole('ADMIN')")
    public GeneralResponseWrapper<HomeworkFileDTO> getFile(
            @ApiParam(value = "ID of the lesson", required = true)
            @PathVariable int idLesson
    ){
        return new GeneralResponseWrapper<>(
                Status.of(HttpStatus.OK),
                journalServiceImpl.getFile(idLesson)
        );
    }
}
