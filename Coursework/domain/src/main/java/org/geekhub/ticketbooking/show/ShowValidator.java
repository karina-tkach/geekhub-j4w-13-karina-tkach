package org.geekhub.ticketbooking.show;

import org.geekhub.ticketbooking.exception.ShowValidationException;
import org.geekhub.ticketbooking.movie.Movie;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class ShowValidator {

    public void validate(Show show) {
        if (show == null) {
            throw new ShowValidationException("Show was null");
        }

        validatePrice(show.getPrice());
        validateMovie(show.getMovie());
        validateTime(show, show.getStart(), show.getEnd());
    }

    private void validatePrice(Long price) {
        if (price < 20) {
            throw new ShowValidationException("Show price was less than 20");
        }
    }

    private void validateMovie(Movie movie) {
        if(movie == null) {
            throw new ShowValidationException("Movie was null");
        }
    }

    private void validateTime(Show show, OffsetDateTime start, OffsetDateTime end) {
        if (!start.plusMinutes(show.getMovie().getDurationInMins()).isEqual(end)) {
            throw new ShowValidationException("Show time was incorrect");
        }
    }

}
