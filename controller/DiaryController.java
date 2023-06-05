package adaptiveschool.controller;

import adaptiveschool.dto.DiaryEntryDTO;
import adaptiveschool.security.JwtUser;
import adaptiveschool.service.base.DiaryServiceBase;
import adaptiveschool.wrapper.GeneralResponseWrapper;
import adaptiveschool.wrapper.Status;
import io.swagger.annotations.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/diaries")
@RequiredArgsConstructor
public class DiaryController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    private DiaryServiceBase diaryService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("")
    GeneralResponseWrapper<List<DiaryEntryDTO>> getDiary(
            @ApiParam(value = "first day of week, accepts date in format 'yyyy-MM-dd'", required=true)
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate weekStartDate
    ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtUser user = (JwtUser) auth.getPrincipal();

        return new GeneralResponseWrapper<>(
                Status.of(OK),
                diaryService.getDiary(weekStartDate, user.getId().intValue())
        );
    }
}
