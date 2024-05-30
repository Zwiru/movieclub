package pl.pkasiewicz.movieclub.domain.genre;

import org.springframework.stereotype.Service;
import pl.pkasiewicz.movieclub.domain.genre.dto.GenreDto;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Optional<GenreDto> findGenreByName(String name) {
        return genreRepository.findByNameIgnoreCase(name)
                .map(GenreDtoMapper::toDto);
    }

    public List<GenreDto> findAllGenres() {
        return genreRepository.findAll().stream()
                .map(GenreDtoMapper::toDto)
                .toList();
    }
    public void addGenre(GenreDto genre) {
        Genre genreToSave = new Genre();
        genreToSave.setName(genre.getName());
        genreToSave.setDescription(genre.getDescription());
        genreRepository.save(genreToSave);
    }
}
