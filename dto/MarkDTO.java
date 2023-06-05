package adaptiveschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarkDTO {

    private int idMark;
    
    private int idStudent;
    
    private int idLesson;

    private Byte mark;

    private String note;
}
