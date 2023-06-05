package adaptiveschool.service;

import adaptiveschool.dto.TeacherJournalDTO;
import adaptiveschool.model.ClassTeacherSubjectLink;
import adaptiveschool.model.Clazz;
import adaptiveschool.model.Subject;
import adaptiveschool.model.Teacher;
import adaptiveschool.repository.ClassRepository;
import adaptiveschool.repository.ClassTeacherSubjectLinkRepository;
import adaptiveschool.repository.SubjectRepository;
import adaptiveschool.repository.TeacherRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClassTeacherSubjectServiceImpl implements ClassTeacherSubjectService {

    @NonNull
    private ClassTeacherSubjectLinkRepository classTeacherSubjectRepository;
    @NonNull
    private ClassRepository classRepository;
    @NonNull
    private TeacherRepository teacherRepository;
    @NonNull
    private SubjectRepository subjectRepository;

    @Override
    public ClassTeacherSubjectLink saveClassTeacherSubject(TeacherJournalDTO teacherJournalDTO, boolean isActive) {

        ClassTeacherSubjectLink classTeacherSubject = new ClassTeacherSubjectLink();

        int classId = teacherJournalDTO.getClassId();
        int teacherId = teacherJournalDTO.getTeacherId();
        int subjectId = teacherJournalDTO.getSubjectId();

        Clazz clazz = classRepository.findById(classId).get();
        Teacher teacher = teacherRepository.findById(teacherId).get();
        Subject subject = subjectRepository.findById(subjectId).get();

        classTeacherSubjectRepository.save(classTeacherSubject.builder()
                .clazz(clazz)
                .clazz_id(classId)
                .teacher(teacher)
                .teacher_id(teacherId)
                .subject(subject)
                .subject_id(subjectId)
                .isActive(isActive).build());
        return new ClassTeacherSubjectLink(classId, teacherId, subjectId, clazz, teacher, subject, isActive);
    }
}
