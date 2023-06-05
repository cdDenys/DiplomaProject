package adaptiveschool.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import adaptiveschool.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer>{

    Optional<PasswordResetToken> findOptionalByToken(String token);
}
