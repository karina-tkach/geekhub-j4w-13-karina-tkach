package org.geekhub.ticketbooking.movie;

import org.geekhub.ticketbooking.exception.MovieValidationException;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.regex.Pattern;

@Component
public class MovieValidator {
    public void validate(Movie movie) {
        if (movie == null) {
            throw new MovieValidationException("Movie was null");
        }

        validateTitle(movie.getTitle());
        validateDescription(movie.getDescription());
        validateDuration(movie.getDurationInMins());
        validateReleaseDate(movie.getReleaseDate());
        validateCountry(movie.getCountry());
        validateAgeLimit(movie.getAgeLimit());
        validateGenre(movie.getGenre());
        validateImage(movie.getImage());
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

    private void validateGenre(Genre genre) {
        if (genre == null) {
            throw new MovieValidationException("Movie genre was null");
        }
    }

    private void validateImage(byte[] image) {
        if (image == null || Arrays.equals(image, "".getBytes())) {
            throw new MovieValidationException("Movie image was null or empty");
        }
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank() || title.length() > 26) {
            throw new MovieValidationException("Movie title was null or empty or more than 26 characters");
        }
    }
    private void validateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new MovieValidationException("Movie description was null or empty");
        }
    }
}
