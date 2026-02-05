package riiku.fitness.userservice.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import riiku.fitness.userservice.dto.UserRequest;
import riiku.fitness.userservice.dto.UserResponse;
import riiku.fitness.userservice.services.UserService;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest req){
        return ResponseEntity.ok(service.register(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserProfile(
            @PathVariable("id") String id
    ){
        return ResponseEntity.ok(service.getUserProfile(id));
    }

    @GetMapping("/{id}/validate")
    public ResponseEntity<Boolean> validateUser(
            @PathVariable("id") String id
    ){
        return ResponseEntity.ok(service.existsByUserId(id));
    }
}
