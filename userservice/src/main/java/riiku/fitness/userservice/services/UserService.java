package riiku.fitness.userservice.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import riiku.fitness.userservice.dto.UserRequest;
import riiku.fitness.userservice.dto.UserResponse;
import riiku.fitness.userservice.models.User;
import riiku.fitness.userservice.repository.UserRepo;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepo repo;

    public UserResponse register(UserRequest req) {

        User user;

        if(repo.existsByEmail(req.getEmail())){
            user = repo.findByEmail(req.getEmail());
            if(user.getKeycloakId() == null){
                user.setKeycloakId(req.getKeyCloakId());
                user = repo.save(user);
            }

        } else {
            user = reqToUser(req);
            user = repo.save(user);
        }

        return userToRES(user);
    }

    public User reqToUser(UserRequest req){
        User user = new User();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        user.setKeycloakId(req.getKeyCloakId());
        return user;
    }

    public UserResponse userToRES(User user){
        UserResponse res = new UserResponse();
        res.setFirstName(user.getFirstName());
        res.setLastName(user.getLastName());
        res.setEmail(user.getEmail());
        res.setKeycloakId(user.getKeycloakId());
        res.setPassword(user.getPassword());
        res.setId(user.getId());
        res.setCreatedAt(user.getCreatedAt());
        res.setUpdatedAt(user.getUpdatedAt());
        return res;
    }

    public UserResponse getUserProfile(String id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        return userToRES(user);
    }

    public boolean existsByUserId(String id) {
        log.info("Calling User Service for {}",id);
        return repo.existsByKeycloakId(id);
    }
}
