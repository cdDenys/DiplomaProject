package adaptiveschool.service;

import adaptiveschool.dto.TeacherJournalDTO;
import adaptiveschool.model.ClassTeacherSubjectLink;

public interface ClassTeacherSubjectService {

    ClassTeacherSubjectLink saveClassTeacherSubject(TeacherJournalDTO teacherJournalDTO, boolean isActive);
}
