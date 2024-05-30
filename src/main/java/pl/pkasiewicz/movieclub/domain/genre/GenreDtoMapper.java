package pl.pkasiewicz.movieclub.domain.genre;

import pl.pkasiewicz.movieclub.domain.genre.dto.GenreDto;

class GenreDtoMapper {
    static GenreDto toDto(Genre entity) {
        return GenreDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
