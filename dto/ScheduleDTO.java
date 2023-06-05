package adaptiveschool.dto;

import adaptiveschool.model.Clazz;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDTO {

    @Future
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate startOfSemester;

    @Future
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate endOfSemester;

    @NotNull
    private ClassDTO className;

    private List<LessonDTO> mondaySubjects;

    private List<LessonDTO> tuesdaySubjects;

    private List<LessonDTO> wednesdaySubjects;

    private List<LessonDTO> thursdaySubjects;

    private List<LessonDTO> fridaySubjects;

    private List<LessonDTO> saturdaySubjects;

    public int getClassId()
    {
        return className.getId();
    }
}
