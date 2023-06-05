package adaptiveschool.controller;

import adaptiveschool.dto.ClassDTO;
import adaptiveschool.dto.ScheduleDTO;
import adaptiveschool.model.Mark;
import adaptiveschool.repository.LessonRepository;
import adaptiveschool.service.ScheduleServiceImpl;
import adaptiveschool.wrapper.GeneralResponseWrapper;
import adaptiveschool.wrapper.Status;
import io.swagger.annotations.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class ScheduleController {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);
    @NonNull
    private ScheduleServiceImpl scheduleService;
    @NonNull
    private LessonRepository lessonRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/classes/{classId}/schedule")
    public GeneralResponseWrapper<ScheduleDTO> postSchedule(
            @ApiParam(value = "Schedule object", required = true) @RequestBody ScheduleDTO scheduleDTO)
    {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.of(scheduleDTO.getStartOfSemester().getYear(), scheduleDTO.getStartOfSemester().getMonth(), scheduleDTO.getStartOfSemester().getDayOfMonth());
        LocalDate endDate = LocalDate.of(scheduleDTO.getEndOfSemester().getYear(), scheduleDTO.getEndOfSemester().getMonth(), scheduleDTO.getEndOfSemester().getDayOfMonth());

        lessonRepository.deleteScheduleByBounds((startDate).format(formatter), (endDate).format(formatter),
                    scheduleDTO.getClassName().getId());
        scheduleService.saveSchedule(scheduleDTO);

        logger.info("Created for class[{}]", scheduleDTO.getClassId());

        return new GeneralResponseWrapper<>(Status.of(CREATED), scheduleDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/classes/{classId}/schedule")
    public GeneralResponseWrapper<ScheduleDTO> getSchedule(@ApiParam(value = "ID of class", required = true) @PathVariable("classId") final int classId){

        logger.debug("Shown for class [{}]", classId);
        return new GeneralResponseWrapper<>(Status.of(OK), scheduleService.getScheduleByClassId(classId));
    }
}