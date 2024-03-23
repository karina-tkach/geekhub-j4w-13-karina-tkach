package org.geekhub.ticketbooking.validator;

import org.geekhub.ticketbooking.exception.MovieValidationException;
import org.geekhub.ticketbooking.exception.ShowValidationException;
import org.geekhub.ticketbooking.model.Movie;
import org.geekhub.ticketbooking.model.Show;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class ShowValidator {
    private final MovieValidator movieValidator;
    public ShowValidator(MovieValidator movieValidator) {
        this.movieValidator = movieValidator;
    }
    public void validate(Show show) {
        if (show == null) {
            throw new ShowValidationException("Show was null");
        }

        validatePrice(show.getPrice());
        validateMovie(show.getMovie());
        validateTime(show, show.getStart(), show.getEnd());
    }
    private void validatePrice(int price) {
        if(price < 0) {
            throw new ShowValidationException("Show price was negative");
        }
    }
    private void validateMovie(Movie movie) {
        try {
            movieValidator.validate(movie);
        }
        catch (MovieValidationException exception) {
            throw new ShowValidationException(exception.getMessage());
        }
    }
    private void validateTime(Show show, OffsetDateTime start, OffsetDateTime end) {
        if(!start.plusMinutes(show.getMovie().getDurationInMins()).isEqual(end)) {
            throw new ShowValidationException("Show time was incorrect");
        }
    }
}
