package adaptiveschool.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PasswordResetDTO {

    String password;
    
    String token;
}
