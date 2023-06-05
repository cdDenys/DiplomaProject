package adaptiveschool.auxiliary;

import adaptiveschool.dto.AddedUsersDTO;
import adaptiveschool.dto.MarkTypeDTO;
import adaptiveschool.dto.StudentDTO;
import adaptiveschool.dto.TeacherDTO;
import adaptiveschool.model.Clazz;
import adaptiveschool.model.MarkType;
import adaptiveschool.model.Student;
import adaptiveschool.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The utility class created to transform the DTO to model, and vice versa.
 */
public class Utility {

    public static StudentDTO convert(Student student){
        Clazz clazz = student.getClasses().stream().filter(Clazz::isActive).findFirst().orElseGet(Clazz::new);
        return StudentDTO.builder().firstname(student.getFirstName())
                .lastname(student.getLastName())
                .patronymic(student.getPatronymic())
                .login(student.getLogin())
                .dateOfBirth(student.getDateOfBirth())
                .classe(clazz.getName())
                .classId(clazz.getId())
                .email(student.getEmail())
                .avatar(student.getAvatar())
                .phone(student.getPhone()).build();
    }

    public static TeacherDTO convert(User teacher){
        return TeacherDTO.builder().firstname(teacher.getFirstName())
                .lastname(teacher.getLastName())
                .patronymic(teacher.getPatronymic())
                .login(teacher.getLogin())
                .email(teacher.getEmail())
                .phone(teacher.getPhone())
                .dateOfBirth(teacher.getDateOfBirth())
                .avatar(teacher.getAvatar())
                .build();
    }

    public static List<AddedUsersDTO> convert(List<User> users){
        return users.stream().map(i-> AddedUsersDTO.builder()
                .firstname(i.getFirstName())
                .lastname(i.getLastName())
                .patronymic(i.getPatronymic())
                .login(i.getLogin())
                .password(i.getPassword())
                .role(i.getRole())
                .build()).collect(Collectors.toCollection(ArrayList::new));
    }

    public static MarkTypeDTO convert(MarkType markType){
        return markType != null ?
                    MarkTypeDTO.builder().markType(markType.getMarkType())
                    .description(markType.getDescription())
                    .isActive(markType.isActive())
                    .id(markType.getId())
                    .build()
                : new MarkTypeDTO();
    }

    public static MarkType convert(MarkTypeDTO markTypeDTO){
        return markTypeDTO != null ?
                    MarkType.builder()
                    .markType(markTypeDTO.getMarkType())
                    .description(markTypeDTO.getDescription())
                    .isActive(markTypeDTO.isActive())
                    .build() : new MarkType();
    }
}
