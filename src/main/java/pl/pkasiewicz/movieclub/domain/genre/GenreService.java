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

    public Optional<GenreDto> findGenreById(Long id) {
        return genreRepository.findById(id).map(GenreDtoMapper::toDto);
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

    public void updateGenre(Long id, GenreDto genreToUpdate) {
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(genreToUpdate.getName());
        genre.setDescription(genreToUpdate.getDescription());
        genreRepository.save(genre);
    }

    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }
}
