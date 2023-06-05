package adaptiveschool.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectAvgMarkDTO {
    
    private double avgMark;
    
    private int subjectId;
    
    private String subjectName;
}
