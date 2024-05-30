package pl.pkasiewicz.movieclub.domain.user.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class UserCredentialsDto {
    private String email;
    private String password;
    private Set<String> roles;
}
