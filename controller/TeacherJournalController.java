package adaptiveschool.controller;

import adaptiveschool.dto.ClassDTO;
import adaptiveschool.dto.TeacherDTO;
import adaptiveschool.dto.TeacherJournalDTO;
import adaptiveschool.dto.SubjectDTO;
import adaptiveschool.service.ClassTeacherSubjectServiceImpl;
import adaptiveschool.wrapper.GeneralResponseWrapper;
import adaptiveschool.wrapper.Status;
import io.swagger.annotations.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class TeacherJournalController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @NonNull
    private ClassTeacherSubjectServiceImpl classTeacherSubject;

    @PostMapping("/teachers/{teacherId}/classes/{classId}/subjects/{subjectId}/journal")
    @PreAuthorize("hasRole('ADMIN')")
    public GeneralResponseWrapper<TeacherJournalDTO> postConection(
            @ApiParam(value = "ID of teacher", required = true) @PathVariable("teacherId") final int teacherId,
            @ApiParam(value = "ID of class", required = true) @PathVariable("classId") final int classId,
            @ApiParam(value = "ID of subject", required = true) @PathVariable("subjectId") final int subjectId)
    {
        classTeacherSubject.saveClassTeacherSubject(new TeacherJournalDTO(teacherId, classId, subjectId), true);
        logger.info("Connection: teacher[{}], class[{}], subject[{}] ",teacherId, classId, subjectId);
        return new GeneralResponseWrapper<>(Status.of(CREATED), new TeacherJournalDTO(teacherId, classId, subjectId));
    }
}