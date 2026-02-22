package riiku.fitness.userservice.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import riiku.fitness.userservice.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    boolean existsByEmail(String email);

    boolean existsByKeycloakId(String keycloakId);

    User findByEmail(
            @NotBlank(message = "email is required")
            @Email(message = "invalid email")
            String email
    );
}
