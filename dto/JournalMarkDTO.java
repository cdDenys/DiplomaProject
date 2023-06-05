package adaptiveschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class JournalMarkDTO {

    private int idStudent;

    private String studentFullName;

    private List<MarkDescriptionDTO> marks;
}