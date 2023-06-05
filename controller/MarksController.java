package adaptiveschool.controller;

import adaptiveschool.dto.MarkDTO;
import adaptiveschool.dto.MarkDataPointDTO;
import adaptiveschool.dto.MarkTypeDTO;
import adaptiveschool.dto.SubjectAvgMarkDTO;
import adaptiveschool.service.base.MarkServiceBase;
import adaptiveschool.wrapper.GeneralResponseWrapper;
import adaptiveschool.wrapper.Status;
import io.swagger.annotations.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/marks")
@RequiredArgsConstructor
public class MarksController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    private MarkServiceBase markService;

    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("")
    GeneralResponseWrapper<List<MarkDataPointDTO>> getMarks (
            @ApiParam(value = "filter results by student id")
            @RequestParam(value = "student_id", required = false) Integer studentId,
            @ApiParam(value = "filter results by subject id")
            @RequestParam(value = "subject_id", required = false) Integer subjectId,
            @ApiParam(value = "filter results by class id")
            @RequestParam(value = "class_id", required = false) Integer classId,
            @ApiParam(value = "get marks received after specified date, accepts date in format 'yyyy-MM-dd'")
            @RequestParam(value = "period_start", required = false)
            @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate periodStart,
            @ApiParam(value = "get marks received before specified date, accepts date in format 'yyyy-MM-dd'")
            @RequestParam(value = "period_end", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate periodEnd)
    {
        logger.debug(
                "Called getMarks() with params: studentId : [{}], subjectId : [{}], classId : [{}], period : [{} - {}]",
                studentId, subjectId, classId, periodStart, periodEnd
        );
        return new GeneralResponseWrapper<>(
                Status.of(OK),
                markService.getFilteredByParams(subjectId, classId, studentId, periodStart, periodEnd));
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/avg")
    @ApiOperation(value = "Teacher gets student's average marks")
    GeneralResponseWrapper<List<SubjectAvgMarkDTO>> getAverageMarks (
            @ApiParam(value = "student id", required = true)
            @RequestParam(value = "student_id", required = true) Integer studentId,
            @ApiParam(value = "get average calculated after specified date, accepts date in format 'yyyy-MM-dd'")
            @RequestParam(value = "period_start", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate periodStart,
            @ApiParam(value = "get average calculated before specified date, accepts date in format 'yyyy-MM-dd'")
            @RequestParam(value = "period_end", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate periodEnd
    ){
        logger.debug("Called getAverageMarks() for studentId:[{}], period:[{} - {}]", studentId, periodStart, periodEnd);
        return new GeneralResponseWrapper<List<SubjectAvgMarkDTO>>(
                Status.of(OK),
                markService.getAverageMarks(studentId, periodStart, periodEnd)
        );
    }

    @PreAuthorize("(hasRole('TEACHER') and @securityExpressionService.hasLessonsInClass(principal.id, #markDTO.idLesson)) or hasRole('ADMIN')")
    @PostMapping
    public GeneralResponseWrapper<MarkDTO> postMark(
        @ApiParam(value = "mark,note,lesson's id and student's id", required = true)
        @RequestBody MarkDTO markDTO
    ){
        MarkDTO resultMarkDTO =  markService.saveMark(markDTO);
        return new GeneralResponseWrapper<>(Status.of(CREATED), resultMarkDTO);
    }

    @PreAuthorize("(hasRole('TEACHER') and @securityExpressionService.hasLessonsInClass(principal.id, #idLesson)) or hasRole('ADMIN')")
    @PutMapping("/lessons/{idLesson}/marktype")
    public GeneralResponseWrapper editType(
            @ApiParam(value = "ID of lesson", required = true) @PathVariable int idLesson,
            @ApiParam(value = "Type of mark", required = true) @RequestBody MarkTypeDTO markType
    ){
        markService.updateType(idLesson, markType.getMarkType());
        return new GeneralResponseWrapper<>(Status.of(CREATED), null);
    }
}
