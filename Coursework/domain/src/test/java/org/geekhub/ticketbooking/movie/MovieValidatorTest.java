package org.geekhub.ticketbooking.movie;

import org.geekhub.ticketbooking.exception.MovieValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MovieValidatorTest {
    private MovieValidator movieValidator;

    @BeforeEach
    public void setUp() {
        movieValidator = new MovieValidator();
    }

    @Test
    void validate_shouldNotThrowException_whenValidMovie() {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setDescription("This is a test movie");
        movie.setDurationInMins(120);
        movie.setReleaseDate(OffsetDateTime.now());
        movie.setCountry("United States");
        movie.setAgeLimit(12);
        movie.setGenre(Genre.ACTION);
        movie.setImage("image".getBytes());

        assertDoesNotThrow(() -> movieValidator.validate(movie), "Validation should pass for a valid movie");
    }

    @Test
    void validate_shouldThrowException_whenNullMovie() {
        assertThrows(MovieValidationException.class, () -> movieValidator.validate(null), "Validation should throw exception for null movie");
    }

    @ParameterizedTest(name = "Validation should throw exception for title: {0}")
    @CsvSource({
        "null",
        "\"\"",
        "This is a very long movie title that exceeds the maximum allowed length"
    })
    void validate_shouldThrowException_whenInvalidTitle(String title) {
        Movie movie = new Movie();
        movie.setTitle(title);

        assertThrows(MovieValidationException.class, () -> movieValidator.validate(movie));
    }

    @ParameterizedTest
    @CsvSource({
        "null",
        "\"\""
    })
    void validate_shouldThrowException_whenInvalidDescription(String description) {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setTitle(description);

        assertThrows(MovieValidationException.class, () -> movieValidator.validate(movie));
    }

    @Test
    void validate_shouldThrowException_whenNegativeDuration() {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setDescription("This is a test movie");
        movie.setDurationInMins(-120);

        assertThrows(MovieValidationException.class, () -> movieValidator.validate(movie), "Validation should throw exception for negative duration");
    }

    @Test
    void validate_shouldThrowException_whenNullReleaseDate() {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setDescription("This is a test movie");
        movie.setDurationInMins(120);
        movie.setReleaseDate(null);

        assertThrows(MovieValidationException.class, () -> movieValidator.validate(movie), "Validation should throw exception for null release date");
    }

    @Test
    void validate_shouldThrowException_whenNullCountry() {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setDescription("This is a test movie");
        movie.setDurationInMins(120);
        movie.setReleaseDate(OffsetDateTime.now());
        movie.setCountry(null);

        assertThrows(MovieValidationException.class, () -> movieValidator.validate(movie), "Validation should throw exception for null country");
    }

    @Test
    void validate_shouldThrowException_whenInvalidCountryCharacters() {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setDescription("This is a test movie");
        movie.setDurationInMins(120);
        movie.setReleaseDate(OffsetDateTime.now());
        movie.setCountry("United States!");

        assertThrows(MovieValidationException.class, () -> movieValidator.validate(movie), "Validation should throw exception for invalid characters in country");
    }

    @Test
    void validate_shouldThrowException_whenNegativeAgeLimit() {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setDescription("This is a test movie");
        movie.setDurationInMins(120);
        movie.setReleaseDate(OffsetDateTime.now());
        movie.setCountry("United States");
        movie.setAgeLimit(-12);

        assertThrows(MovieValidationException.class, () -> movieValidator.validate(movie), "Validation should throw exception for negative age limit");
    }

    @Test
    void validate_shouldThrowException_whenNullGenre() {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setDescription("This is a test movie");
        movie.setDurationInMins(120);
        movie.setReleaseDate(OffsetDateTime.now());
        movie.setCountry("United States");
        movie.setAgeLimit(12);
        movie.setGenre(null);

        assertThrows(MovieValidationException.class, () -> movieValidator.validate(movie), "Validation should throw exception for null genre");
    }

    @Test
    void validate_shouldThrowException_whenNullImage() {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setDescription("This is a test movie");
        movie.setDurationInMins(120);
        movie.setReleaseDate(OffsetDateTime.now());
        movie.setCountry("United States");
        movie.setAgeLimit(12);
        movie.setGenre(Genre.ACTION);
        movie.setImage(null);

        assertThrows(MovieValidationException.class, () -> movieValidator.validate(movie), "Validation should throw exception for null image");
    }

    @Test
    void validate_shouldThrowException_whenEmptyImage() {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setDescription("This is a test movie");
        movie.setDurationInMins(120);
        movie.setReleaseDate(OffsetDateTime.now());
        movie.setCountry("United States");
        movie.setAgeLimit(12);
        movie.setGenre(Genre.ACTION);
        movie.setImage("".getBytes());

        assertThrows(MovieValidationException.class, () -> movieValidator.validate(movie), "Validation should throw exception for empty image");
    }
}
