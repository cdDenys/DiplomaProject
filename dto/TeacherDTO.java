package adaptiveschool.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static adaptiveschool.model.User.NAME_PATTERN;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherDTO {

    private int id;

    private String firstname;

    private String lastname;

    private String patronymic;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            timezone = "Europe/Kiev",
            pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String login;

    private String email;

    private String phone;

    private String avatar;

    public TeacherDTO(String firstname, String lastname, String patronymic, LocalDate dateOfBirth, String login, String email, String phone, String avatar) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.dateOfBirth = dateOfBirth;
        this.login = login;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
    }
}
