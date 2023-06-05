package adaptiveschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class JournalDTO {

    private int idSubject;
    
    private int idClass;

    private String subjectName;

    private String className;

    private int academicYear;
}
