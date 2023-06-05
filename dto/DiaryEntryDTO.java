package adaptiveschool.dto;

import java.time.LocalDate;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class DiaryEntryDTO {

    private int lessonId;

    private LocalDate date;

    private byte lessonNumber;

    private String subjectName;

    private String homeWork;

    private Integer homeworkFileId;

    private byte mark;
    
    private String note;
}
