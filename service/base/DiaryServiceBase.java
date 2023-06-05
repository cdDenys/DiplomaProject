package adaptiveschool.service.base;

import java.time.LocalDate;
import java.util.List;

import adaptiveschool.dto.DiaryEntryDTO;

public interface DiaryServiceBase {
    List<DiaryEntryDTO> getDiary(LocalDate weekStart, int studentId);
}
