package adaptiveschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeworkFileDTO{
    private int idLesson;

    private String homework;

    private String fileData;

    private String fileName;

    private String fileType;
}