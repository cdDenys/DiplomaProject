package adaptiveschool.dto;

import adaptiveschool.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddedUsersDTO {

    private String firstname;

    private String lastname;

    private String patronymic;

    private User.Role role;

    private String login;

    private String password;
}
