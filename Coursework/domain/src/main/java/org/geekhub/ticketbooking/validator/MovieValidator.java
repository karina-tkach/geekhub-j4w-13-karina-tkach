package org.geekhub.ticketbooking.validator;

import org.geekhub.ticketbooking.exception.MovieValidationException;
import org.geekhub.ticketbooking.model.Genre;
import org.geekhub.ticketbooking.model.Movie;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class MovieValidator {
    public void validate(Movie movie) {
        if (movie == null) {
            throw new MovieValidationException("Movie was null");
        }
        validateDuration(movie.getDurationInMins());
        validateReleaseDate(movie.getReleaseDate());
        validateCountry(movie.getCountry());
        validateAgeLimit(movie.getAgeLimit());
        validateGenres(movie.getGenres());
    }

    private void validateDuration(int durationInMins) {
        if (durationInMins < 1) {
            throw new MovieValidationException("Movie duration wasn't positive");
        }
    }

    private void validateReleaseDate(OffsetDateTime releaseDate) {
        if (releaseDate == null) {
            throw new MovieValidationException("Movie release date was null");
        }
    }

    private void validateCountry(String country) {
        Pattern letters = Pattern.compile("^[a-zA-Z ]+$");
        if (country == null || !letters.matcher(country).find()) {
            throw new MovieValidationException("Movie country must contain only letters and spaces");
        }
    }

    private void validateAgeLimit(int ageLimit) {
        if (ageLimit < 0) {
            throw new MovieValidationException("Movie age limit was negative");
        }
    }

    private void validateGenres(List<Genre> genres) {
        if (genres == null) {
            throw new MovieValidationException("Movie genres were null");
        }
    }
}
