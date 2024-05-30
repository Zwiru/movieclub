package pl.pkasiewicz.movieclub.domain.movie;

import org.springframework.stereotype.Component;
import pl.pkasiewicz.movieclub.domain.genre.Genre;
import pl.pkasiewicz.movieclub.domain.genre.GenreRepository;
import pl.pkasiewicz.movieclub.domain.movie.dto.MovieDto;
import pl.pkasiewicz.movieclub.domain.movie.dto.MovieSaveDto;
import pl.pkasiewicz.movieclub.domain.rating.Rating;
import pl.pkasiewicz.movieclub.domain.rating.RatingRepository;

import java.util.Set;

@Component
class MovieDtoMapper {
    private final GenreRepository genreRepository;
    private final RatingRepository ratingRepository;

    public MovieDtoMapper(GenreRepository genreRepository, RatingRepository ratingRepository) {
        this.genreRepository = genreRepository;
        this.ratingRepository = ratingRepository;
    }

    static MovieDto toDto(Movie movie) {
        double avgRating = movie.getRatings().stream()
                .map(Rating::getRating)
                .mapToDouble(val -> val)
                .average().orElse(0);
        int ratingCount = movie.getRatings().size();
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getOriginalTitle(),
                movie.getShortDescription(),
                movie.getDescription(),
                movie.getYoutubeTrailerId(),
                movie.getReleaseYear(),
                movie.getGenre().getName(),
                movie.isPromoted(),
                movie.getPoster(),
                avgRating,
                ratingCount);
    }

//    Movie toEntity(MovieSaveDto dto) {
//        Movie movie = new Movie();
//        movie.setId(dto.getId());
//        movie.setTitle(dto.getTitle());
//        movie.setOriginalTitle(dto.getOriginalTitle());
//        movie.setShortDescription(dto.getShortDescription());
//        movie.setDescription(dto.getDescription());
//        movie.setYoutubeTrailerId(dto.getYoutubeTrailerId());
//        movie.setReleaseYear(dto.getReleaseYear());
//        Genre genre = genreRepository.findByNameIgnoreCase(dto.getGenre()).get();
//        movie.setGenre(genre);
//        movie.setPromoted(dto.isPromoted());
//        movie.setPoster(dto.getPoster());
//        Set<Rating> ratings = ratingRepository.findByMovie_Id(dto.getId()).get();
//        movie.setRatings(ratings);
//        return movie;
//    }
}
