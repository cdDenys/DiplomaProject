package adaptiveschool.dto;

import adaptiveschool.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditUserDTO {

    private String firstname;

    private String lastname;

    private String patronymic;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd", timezone="EET")
    private LocalDate dateOfBirth;

    private String login;

    private String oldPass;

    private String newPass;

    private String email;

    private String phone;

    private String avatar;
}
