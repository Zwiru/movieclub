package pl.pkasiewicz.movieclub.domain.user.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRegistrationDto {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
