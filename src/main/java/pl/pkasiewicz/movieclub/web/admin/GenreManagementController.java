package pl.pkasiewicz.movieclub.web.admin;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pkasiewicz.movieclub.domain.genre.GenreService;
import pl.pkasiewicz.movieclub.domain.genre.dto.GenreDto;

@Controller
@RequestMapping("/admin")
class GenreManagementController {
    private final GenreService genreService;

    public GenreManagementController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/add-genre")
    public String addGenreForm(Model model) {
        GenreDto genre = new GenreDto();
        model.addAttribute("genre", genre);
        return "admin/genre-form";
    }

    @PostMapping("/add-genre")
    public String addGenre(GenreDto genre, RedirectAttributes redirectAttributes) {
        genreService.addGenre(genre);
        redirectAttributes.addFlashAttribute(
                AdminController.NOTIFICATION_ATTRIBUTE,
                "Gatunek %s został dodany".formatted(genre.getName())
        );
        return "redirect:/admin";
    }

    @GetMapping("/edit-genre/{id}")
    public String editGenre(@PathVariable Long id, Model model) {
        GenreDto genre = genreService.findGenreById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("genre", genre);
        return "admin/edit-genre-form";
    }

    @PostMapping("/edit-genre/{id}")
    public String updateGenre(@PathVariable Long id, GenreDto genre, RedirectAttributes redirectAttributes) {
        genreService.updateGenre(id, genre);
        redirectAttributes.addFlashAttribute(
                AdminController.NOTIFICATION_ATTRIBUTE,
                "Gatunek %s został zapisany".formatted(genre.getName())
        );
        return "redirect:/admin";
    }

    @GetMapping("/delete-genre/{id}")
    public String deleteGenre(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        GenreDto genre = genreService.findGenreById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        genreService.deleteGenre(id);
        redirectAttributes.addFlashAttribute(
                AdminController.NOTIFICATION_ATTRIBUTE,
                "Gatunek %s został usunięty".formatted(genre.getName())
        );
        return "redirect:/admin";
    }
}
