package adaptiveschool.dto;

import adaptiveschool.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditStudentDTO {

    private String firstname;

    private String lastname;

    private String patronymic;

    private Integer classId;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd"
    )
    private Date dateOfBirth;

    private String login;

    private String oldPass;

    private String newPass;

    private String email;

    private String phone;
}
