package adaptiveschool.controller;

import java.util.List;

import adaptiveschool.service.SubjectService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import adaptiveschool.dto.SubjectDTO;
import adaptiveschool.wrapper.GeneralResponseWrapper;
import adaptiveschool.wrapper.Status;
import io.swagger.annotations.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SubjectController.class);

	@NonNull
	private SubjectService subjectService;

	@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
	@GetMapping()
	public GeneralResponseWrapper<List<SubjectDTO>> getAllSubjects(
			@ApiParam(value = "Only subjects studied in specified class will be returned") @RequestParam(required = false) Integer classId) {
		if (classId == null) {
			return new GeneralResponseWrapper<>(Status.of(OK), subjectService.getAllSubjects());
		} else {
			return new GeneralResponseWrapper<>(Status.of(OK), subjectService.getSubjectsByClass(classId));
		}
	}

	@PreAuthorize("hasRole('ADMIN') or (hasRole('TEACHER') and  @securityExpressionService.teachesSubject(principal.id, #id))")
	@GetMapping("/{id}")
	public GeneralResponseWrapper<SubjectDTO> getSubjectById(
			@ApiParam(value = "ID of subject", required = true) @PathVariable int id) {
			return new GeneralResponseWrapper<>(Status.of(OK), subjectService.getSubjectById(id));
	}

	@PreAuthorize("hasRole('TEACHER') and principal.id == #id")
	@GetMapping("/teachers/{id}")
	public GeneralResponseWrapper<List<SubjectDTO>> getSubjectsTeacher(
			@ApiParam(value = "ID of teacher", required = true) @PathVariable int id) {
			return new GeneralResponseWrapper<>(Status.of(OK), subjectService.getSubjectsByTeacher(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public GeneralResponseWrapper<SubjectDTO> addSubject(
			@ApiParam(value = "Subject object", required = true) @RequestBody SubjectDTO newSubject) {
		
			LOGGER.info("Adding a new subject [{}]", newSubject.getSubjectName());
			return new GeneralResponseWrapper<>(Status.of(CREATED), subjectService.addSubject(newSubject));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public GeneralResponseWrapper<SubjectDTO> editSubject(
			@ApiParam(value = "ID of object", required = true) @PathVariable int id,
			@ApiParam(value = "Subject object", required = true) @RequestBody SubjectDTO editSubject) {
		
			LOGGER.info("Updating an existing subject with id = [{}] ", id);
			return new GeneralResponseWrapper<>(Status.of(CREATED), subjectService.editSubject(id, editSubject));
	}
}