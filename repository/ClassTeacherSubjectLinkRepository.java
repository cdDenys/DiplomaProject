package adaptiveschool.repository;

import adaptiveschool.model.ClassTeacherSubjectLink;
import adaptiveschool.model.ClassTeacherSubjectLinkId;
import adaptiveschool.service.SecurityExpressionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassTeacherSubjectLinkRepository extends JpaRepository<ClassTeacherSubjectLink, ClassTeacherSubjectLinkId> {

    @Query(value = "select distinct * from class_teacher_subject_link ct\n" +
            "left join clazz on ct.clazz_id=clazz.id\n" +
            "left join subject on subject.id=ct.subject_id\n" +
            "left join teacher on teacher.id=ct.teacher_id\n" +
            "where ct.teacher_id = :idTeacher", nativeQuery=true)
    List<ClassTeacherSubjectLink> findJournalsByTeacher(@Param("idTeacher") int idTeacher);

    @Query(value = "select distinct * from class_teacher_subject_link ct\n" +
            "left join clazz on ct.clazz_id=clazz.id\n" +
            "left join subject on subject.id=ct.subject_id\n" +
            "left join teacher on teacher.id=ct.teacher_id\n" +
            "where ct.teacher_id = :idTeacher and clazz.is_active=true ", nativeQuery=true)
    List<ClassTeacherSubjectLink> findActiveJournalsByTeacher(@Param("idTeacher") int idTeacher);

    @Query(value = "select distinct * from class_teacher_subject_link ct\n" +
            "left join clazz on ct.clazz_id=clazz.id\n" +
            "left join subject on subject.id=ct.subject_id\n" +
            "left join teacher on teacher.id=ct.teacher_id\n", nativeQuery=true)
    List<ClassTeacherSubjectLink> findJournals();

    @Query(value = "select * from class_teacher_subject_link ct\n" +
            "where teacher_id = :teacherId and clazz_id = :classId and subject_id = :subjectId and is_active = true", nativeQuery=true)
    ClassTeacherSubjectLink findByTeacherIdAndClazzIdAndSubjectId(@Param("teacherId") int teacherId,
                                                                  @Param("classId")int classId,
                                                                  @Param("subjectId")int subjectId);

    @Query(value = "select * from class_teacher_subject_link ct\n" +
            "where teacher_id = :teacherId and clazz_id = :classId and is_active = true", nativeQuery=true)
    List<ClassTeacherSubjectLink> findByTeacherIdAndClazzId(@Param("teacherId") int teacherId,
                                                      @Param("classId")int classId);

    List<ClassTeacherSubjectLink> findByTeacherIdAndSubjectId(int teacherId, int subjectId);

    List<ClassTeacherSubjectLink> findByClazzId(int classId);
}
