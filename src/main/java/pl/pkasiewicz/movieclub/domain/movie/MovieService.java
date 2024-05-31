package pl.pkasiewicz.movieclub.domain.movie;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pkasiewicz.movieclub.domain.genre.Genre;
import pl.pkasiewicz.movieclub.domain.genre.GenreRepository;
import pl.pkasiewicz.movieclub.domain.movie.dto.MovieDto;
import pl.pkasiewicz.movieclub.domain.movie.dto.MovieSaveDto;
import pl.pkasiewicz.movieclub.storage.FileStorageService;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final FileStorageService fileStorageService;
    private final GenreRepository genreRepository;

    public MovieService(MovieRepository movieRepository,
                        FileStorageService fileStorageService,
                        GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.fileStorageService = fileStorageService;
        this.genreRepository = genreRepository;
    }

    public List<MovieDto> getAllPromotedMovies() {
        return movieRepository.findAllByPromotedIsTrue().stream()
                .map(MovieDtoMapper::toDto)
                .toList();
    }

    public Optional<MovieDto> findMovieById(long id) {
        return movieRepository.findById(id).map(MovieDtoMapper::toDto);
    }

    public List<MovieDto> findMoviesByGenreName(String genre) {
        return movieRepository.findAllByGenre_NameIgnoreCase(genre).stream()
                .map(MovieDtoMapper::toDto)
                .toList();
    }

    public void addMovie(MovieSaveDto movieToSave) {
        Movie movie = new Movie();
        movie.setTitle(movieToSave.getTitle());
        movie.setOriginalTitle(movieToSave.getOriginalTitle());
        movie.setPromoted(movieToSave.isPromoted());
        movie.setReleaseYear(movieToSave.getReleaseYear());
        movie.setShortDescription(movieToSave.getShortDescription());
        movie.setDescription(movieToSave.getDescription());
        movie.setYoutubeTrailerId(movieToSave.getYoutubeTrailerId());
        Genre genre = genreRepository.findByNameIgnoreCase(movieToSave.getGenre()).orElseThrow();
        movie.setGenre(genre);
        if (movieToSave.getPoster() != null && !movieToSave.getPoster().isEmpty()) {
            String savedFileName = fileStorageService.saveImage(movieToSave.getPoster());
            movie.setPoster(savedFileName);
        }
        movieRepository.save(movie);
    }

    public List<MovieDto> findTopMovies(int size) {
        Pageable page = Pageable.ofSize(size);
        return movieRepository.findTopByRating(page).stream()
                .map(MovieDtoMapper::toDto)
                .toList();
    }

    public void updateMovie(Long id, MovieSaveDto movieToUpdate) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(movieToUpdate.getTitle());
        movie.setOriginalTitle(movieToUpdate.getOriginalTitle());
        movie.setPromoted(movieToUpdate.isPromoted());
        movie.setReleaseYear(movieToUpdate.getReleaseYear());
        movie.setShortDescription(movieToUpdate.getShortDescription());
        movie.setDescription(movieToUpdate.getDescription());
        movie.setYoutubeTrailerId(movieToUpdate.getYoutubeTrailerId());
        Genre genre = genreRepository.findByNameIgnoreCase(movieToUpdate.getGenre()).orElseThrow();
        movie.setGenre(genre);
        if (movieToUpdate.getPoster() != null && !movieToUpdate.getPoster().isEmpty()) {
            String savedFileName = fileStorageService.saveImage(movieToUpdate.getPoster());
            movie.setPoster(savedFileName);
        }
        if (movieToUpdate.getPoster().isEmpty()) {
            String poster = movieRepository.findById(id).get().getPoster();
            movie.setPoster(poster);
        }
        movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
