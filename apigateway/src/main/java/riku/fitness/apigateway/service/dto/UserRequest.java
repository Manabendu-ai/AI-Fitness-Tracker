package riku.fitness.apigateway.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "email is required")
    @Email(message = "invalid email")
    private String email;
    private String keyCloakId;
    @NotBlank(message = "password is required")
    @Size(min = 6, message = "password must have at least 6 characters")
    private String password;
    private String firstName;
    private String lastName;
}
