package pl.pkasiewicz.movieclub.domain.user;

import pl.pkasiewicz.movieclub.domain.user.dto.UserCredentialsDto;

import java.util.stream.Collectors;

class UserCredentialsDtoMapper {
    static UserCredentialsDto mapToDto(User entity) {
        return UserCredentialsDto.builder()
                .email(entity.getEmail())
                .password(entity.getPassword())
                .roles(entity.getRoles()
                        .stream()
                        .map(UserRole::getName)
                        .collect(Collectors.toSet()))
                .build();
    }
}
