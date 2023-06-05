package adaptiveschool.dto;

import adaptiveschool.model.Clazz;
import adaptiveschool.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDTO {

    private int Id;

    private String firstname;

    private String lastname;

    private String patronymic;

    private String classe;

    private int classId;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            timezone = "Europe/Kiev",
            pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String login;

    private String email;

    private String phone;

    private String avatar;

    public StudentDTO(String firstname, String lastname, String patronymic, String classe, int classId, LocalDate dateOfBirth, String login, String email, String phone, String avatar) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.classe = classe;
        this.classId = classId;
        this.dateOfBirth = dateOfBirth;
        this.login = login;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
    }
}
