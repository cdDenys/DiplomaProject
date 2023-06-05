package adaptiveschool.controller;

import adaptiveschool.dto.MarkTypeDTO;
import adaptiveschool.service.MarkTypeService;
import adaptiveschool.wrapper.GeneralResponseWrapper;
import adaptiveschool.wrapper.Status;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/mark_types")
@RequiredArgsConstructor
public class MarkTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);

    @NonNull
    private MarkTypeService markTypeService;

    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping
    public GeneralResponseWrapper<List<MarkTypeDTO>> getAll() {
        LOGGER.info("Getting all mark types.");
        return new GeneralResponseWrapper<>(Status.of(OK), markTypeService.getAll());
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/{id}")
    public GeneralResponseWrapper<MarkTypeDTO> getOne(@PathVariable("id") int id) {
        LOGGER.info("Getting mark type with id {}", id);
        return new GeneralResponseWrapper<>(Status.of(OK), markTypeService.getMarkTypeById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public GeneralResponseWrapper<MarkTypeDTO> addOne(@RequestBody MarkTypeDTO markTypeDTO) {
        LOGGER.info("Adding mark type [{} {}]", markTypeDTO.getMarkType(), markTypeDTO.getDescription());
        return new GeneralResponseWrapper<>(Status.of(CREATED), markTypeService.addMarkType(markTypeDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public GeneralResponseWrapper<MarkTypeDTO> updateOne(
            @RequestBody MarkTypeDTO markTypeDTO,
            @PathVariable("id") int id
    ){
        LOGGER.info(
                "Updating mark type with id {} to mark type [{} {}]",
                id, markTypeDTO.getMarkType(), markTypeDTO.getDescription()
        );

        return new GeneralResponseWrapper<>(
                Status.of(OK),
                markTypeService.updateMarkType(id, markTypeDTO)
        );
    }
}
