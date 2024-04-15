package org.geekhub.ticketbooking.controller;

import org.geekhub.ticketbooking.model.Movie;
import org.geekhub.ticketbooking.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Controller
@RequestMapping("admin/movies")
@SuppressWarnings("java:S1192")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public String viewHomePage(Model model) {
        return viewHomePageWithPagination(1, 2, model);
    }

    @GetMapping("/{page}/{pageSize}")
    public String viewHomePageWithPagination(@PathVariable int page,
                                             @PathVariable int pageSize, Model model) {
        List<Movie> movies = movieService.getMoviesWithPagination(page, pageSize);
        int rows = movieService.getMoviesRowsCount();
        if (rows == -1 || movies.isEmpty()) {
            model.addAttribute("error", "Can't load movies");
        } else {
            model.addAttribute("page", page);
            model.addAttribute("totalPages", Math.ceil(rows / (float) pageSize));
            model.addAttribute("listMovies", movies);
        }

        return "movies";
    }

    @GetMapping("/showNewMovieForm")
    public String showNewMovieForm(Model model) {
        Movie movie = new Movie();
        model.addAttribute("movie", movie);
        return "new_movie";
    }

    @PostMapping(value = "/saveMovie", consumes = MULTIPART_FORM_DATA_VALUE)
    public String saveMovie(@ModelAttribute("movie") Movie movie,
                            @RequestPart("file") MultipartFile file, Model model) {
        try {
            movie.setImage(file.getBytes());
        } catch (IOException e) {
            return setAttributesAndGetProperPage(model, "message",
                "Cannot set image to movie", "new_movie");
        }

        Movie addedMovie = movieService.addMovie(movie);
        if (addedMovie == null) {
            return setAttributesAndGetProperPage(model, "message",
                "Invalid movie parameters", "new_movie");
        }
        return setAttributesAndGetProperPage(model, "message",
            "You have successfully added movie", "new_movie");
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        if (movie == null) {
            return setAttributesAndGetProperPage(model, "error",
                "Cannot get movie by this id", "update_movie");
        }
        return "update_movie";
    }

    @PostMapping(value = "/updateMovie", consumes = MULTIPART_FORM_DATA_VALUE)
    public String updateMovie(@ModelAttribute("movie") Movie movie,
                              @RequestPart("file") MultipartFile file, Model model) {
        int movieId = movie.getId();
        Movie oldMovie = movieService.getMovieById(movieId);

        if (movie.getReleaseDate() == null) {
            movie.setReleaseDate(oldMovie.getReleaseDate());
        }
        try {
            if (file.isEmpty()) {
                movie.setImage(oldMovie.getImage());
            } else {
                movie.setImage(file.getBytes());
            }
        } catch (IOException e) {
            return setAttributesAndGetProperPage(model, "message",
                "Cannot set image to movie", "update_movie");
        }


        Movie updatedMovie = movieService.updateMovieById(movie, movieId);

        if (updatedMovie == null) {
            return setAttributesAndGetProperPage(model, "message",
                "Invalid movie parameters", "update_movie");
        }
        return setAttributesAndGetProperPage(model, "message",
            "You have successfully updated movie", "update_movie");
    }

    @GetMapping("/deleteMovie/{id}")
    public String deleteMovie(@PathVariable(value = "id") int id, Model model) {
        boolean result = movieService.deleteMovieById(id);
        if (!result) {
            return setAttributesAndGetProperPage(model, "error",
                "Cannot delete movie by this id", "update_movie");
        }
        return "redirect:/admin/movies";
    }

    private String setAttributesAndGetProperPage(Model model, String attributeName,
                                                 String attributeValue, String page) {
        model.addAttribute(attributeName, attributeValue);
        return page;
    }
}
