package adaptiveschool.service.base;

import adaptiveschool.dto.PasswordResetDTO;

public interface PasswordResetServiceBase {
    String trySendPasswordResetEmail(String username);
    String tryChangePassword(PasswordResetDTO passwordDTO);
}
