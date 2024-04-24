package org.geekhub.ticketbooking.show;

import org.geekhub.ticketbooking.cinema.Cinema;
import org.geekhub.ticketbooking.cinema.CinemaService;
import org.geekhub.ticketbooking.hall.Hall;
import org.geekhub.ticketbooking.hall.HallService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShowUtilityService {
    private final HallService hallService;
    private final CinemaService cinemaService;

    public ShowUtilityService(HallService hallService, CinemaService cinemaService) {
        this.hallService = hallService;
        this.cinemaService = cinemaService;
    }

    public Map<Integer, String> getShowsSelectOptions(List<Show> shows) {
        return shows.stream()
            .collect(Collectors.toMap(
                Show::getId,
                show -> {
                    Hall hall = hallService.getHallById(show.getHallId());
                    String hallName = hall.getHallName();
                    Cinema cinema = cinemaService.getCinemaById(hall.getCinemaId());
                    String cinemaName = cinema.getName();
                    String cityName = cinema.getCity().getName();

                    return cityName + " " + cinemaName + " " + hallName;
                }
            ));
    }
}
