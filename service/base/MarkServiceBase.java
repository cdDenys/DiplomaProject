package adaptiveschool.service.base;

import java.time.LocalDate;
import java.util.List;

import adaptiveschool.dto.MarkDTO;
import adaptiveschool.dto.MarkDataPointDTO;
import adaptiveschool.dto.SubjectAvgMarkDTO;

public interface MarkServiceBase {
    List<MarkDataPointDTO> getFilteredByParams(Integer subjectId, Integer classId, Integer studentId, LocalDate startDate, LocalDate endDate);
    MarkDTO saveMark(MarkDTO dto);
    void updateType(int idLesson, String markType);
    List<SubjectAvgMarkDTO> getAverageMarks(Integer studentId, LocalDate start, LocalDate end);
}
