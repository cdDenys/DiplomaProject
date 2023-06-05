package adaptiveschool.dto;

import  adaptiveschool.model.Subject;
import io.swagger.annotations.ApiModel;
import lombok.*;

@ApiModel
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SubjectDTO {

    private int subjectId;

    private String subjectName;

    private String subjectDescription;
}