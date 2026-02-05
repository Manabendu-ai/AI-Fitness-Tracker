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
        if(repo.existsByEmail(req.getEmail())){
            throw new RuntimeException("Email Already Exists!");
        }
        User user = reqToUser(req);
        return userToRES(repo.save(user));
    }

    public User reqToUser(UserRequest req){
        User user = new User();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        return user;
    }

    public UserResponse userToRES(User user){
        UserResponse res = new UserResponse();
        res.setFirstName(user.getFirstName());
        res.setLastName(user.getLastName());
        res.setEmail(user.getEmail());
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
        return repo.existsById(id);
    }
}
