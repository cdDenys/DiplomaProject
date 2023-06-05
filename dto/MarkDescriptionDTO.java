package adaptiveschool.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarkDescriptionDTO {
    private int idLesson;

    private Byte mark;
    
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy.MM.dd")
    private Date dateMark;
    
    private String typeMark;
    
    private String note;

}