package adaptiveschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherJournalDTO {

    @Min(1)
    private int teacherId;

    @Min(1)
    private int classId;

    @Min(1)
    private int subjectId;
}
