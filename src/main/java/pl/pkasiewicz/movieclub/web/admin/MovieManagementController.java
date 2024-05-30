package pl.pkasiewicz.movieclub.web.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pkasiewicz.movieclub.domain.genre.GenreService;
import pl.pkasiewicz.movieclub.domain.genre.dto.GenreDto;
import pl.pkasiewicz.movieclub.domain.movie.MovieService;
import org.springframework.ui.Model;
import pl.pkasiewicz.movieclub.domain.movie.dto.MovieDto;
import pl.pkasiewicz.movieclub.domain.movie.dto.MovieSaveDto;

import java.util.List;

@Controller
class MovieManagementController {
    private final MovieService movieService;
    private final GenreService genreService;

    public MovieManagementController(MovieService movieService, GenreService genreService) {
        this.movieService = movieService;
        this.genreService = genreService;
    }

    @GetMapping("/admin/add-movie")
    public String addMovieForm(Model model) {
        MovieSaveDto movie = new MovieSaveDto();
        List<GenreDto> allGenres = genreService.findAllGenres();
        model.addAttribute("genres", allGenres);
        model.addAttribute("movie", movie);
        return "admin/movie-form";
    }

    @PostMapping("/admin/add-movie")
    public String addMovie(MovieSaveDto movie, RedirectAttributes redirectAttributes) {
        movieService.addMovie(movie);
        redirectAttributes.addFlashAttribute(
                AdminController.NOTIFICATION_ATTRIBUTE,
                "Film %s został zapisany".formatted(movie.getTitle())
        );
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit-movie/{id}")
    public String editMovieForm(@PathVariable Long id, Model model) {
        MovieDto movie = movieService.findMovieById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<GenreDto> allGenres = genreService.findAllGenres();
        model.addAttribute("genres", allGenres);
        model.addAttribute("movie", movie);
        return "admin/edit-movie-form";
    }

    @PostMapping("/admin/edit-movie/{id}")
    public String updateMovie(@PathVariable Long id, @ModelAttribute MovieSaveDto movie, RedirectAttributes redirectAttributes) {
        movieService.updateMovie(id, movie);
        redirectAttributes.addFlashAttribute(
                AdminController.NOTIFICATION_ATTRIBUTE,
                "Film %s został zapisany".formatted(movie.getTitle())
        );
        return "redirect:/admin";
    }
}
