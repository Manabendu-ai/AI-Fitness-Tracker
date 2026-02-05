package riiku.fitness.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import riiku.fitness.userservice.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
}
