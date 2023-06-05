package adaptiveschool.controller;

import adaptiveschool.dto.JournalDTO;
import adaptiveschool.dto.JournalMarkDTO;
import adaptiveschool.service.JournalServiceImpl;
import adaptiveschool.wrapper.GeneralResponseWrapper;
import adaptiveschool.wrapper.Status;
import io.swagger.annotations.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/journals")
@RequiredArgsConstructor
public class JournalController {

    @NonNull
    private JournalServiceImpl journalServiceImpl;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public GeneralResponseWrapper<List<JournalDTO>> getJournals(){
        return new GeneralResponseWrapper<>(Status.of(HttpStatus.OK), journalServiceImpl.getJournals());
    }

    @PreAuthorize("(hasRole('TEACHER') and principal.id == #idTeacher) or hasRole('ADMIN')")
    @GetMapping("/teachers/{idTeacher}")
    public GeneralResponseWrapper<List<JournalDTO>> getJournalsTeacher(
            @ApiParam(value = "ID of teacher", required = true)
            @PathVariable int idTeacher
    ){
        return new GeneralResponseWrapper<>(
                Status.of(HttpStatus.OK),
                journalServiceImpl.getJournalsByTeacher(idTeacher)
        );
    }

    @PreAuthorize("(hasRole('TEACHER') and principal.id == #idTeacher) or hasRole('ADMIN')")
    @GetMapping("/teachers/{idTeacher}/active")
    public GeneralResponseWrapper<List<JournalDTO>> getActiveJournalsTeacher(
            @ApiParam(value = "ID of teacher", required = true)
            @PathVariable int idTeacher
    ){
        return new GeneralResponseWrapper<>(
                Status.of(HttpStatus.OK),
                journalServiceImpl.getActiveJournalsByTeacher(idTeacher)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/class/{idClass}")
    public GeneralResponseWrapper<List<JournalDTO>> getJournalsForClass(
            @ApiParam(value = "ID of class", required = true)
            @PathVariable int idClass
    ){
        return new GeneralResponseWrapper<>(
                Status.of(HttpStatus.OK),
                journalServiceImpl.getJournalsByClass(idClass)
        );
    }

    @PreAuthorize("(hasRole('TEACHER') and @securityExpressionService.hasLessonsInClass(principal.id, #idClass, #idSubject)) or hasRole('ADMIN')")
    @GetMapping("/subjects/{idSubject}/classes/{idClass}")
    public GeneralResponseWrapper<List<JournalMarkDTO>> getJournalTable(
            @ApiParam(value = "ID of subject", required = true) @PathVariable int idSubject,
            @ApiParam(value = "ID of class", required = true) @PathVariable int idClass
    ){
        return new GeneralResponseWrapper<>(
                Status.of(HttpStatus.OK),
                journalServiceImpl.getJournal(idSubject,idClass)
        );
    }
}