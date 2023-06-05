package adaptiveschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import adaptiveschool.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
	
	@Query(value = "SELECT DISTINCT subject.id, subject.name, subject.description FROM class_teacher_subject_link \n" +
			"LEFT JOIN subject ON class_teacher_subject_link.subject_id = subject.id \n" +
            "WHERE teacher_id = :idTeacher", nativeQuery=true)
    List<Subject> findSubjectsByTeacher(@Param("idTeacher") int idTeacher);
    
    @Query(value = "SELECT DISTINCT subject.id, subject.name, subject.description FROM class_teacher_subject_link \n" +
    		"LEFT JOIN subject ON class_teacher_subject_link.subject_id = subject.id \n" +
            "WHERE clazz_id = :classId", nativeQuery=true)
	List<Subject> findSubjectsByClass(@Param(value="classId") Integer classId);
}