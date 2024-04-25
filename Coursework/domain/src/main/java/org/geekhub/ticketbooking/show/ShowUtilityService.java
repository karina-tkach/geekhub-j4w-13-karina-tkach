package org.geekhub.ticketbooking.show;

import org.geekhub.ticketbooking.cinema.Cinema;
import org.geekhub.ticketbooking.cinema.CinemaService;
import org.geekhub.ticketbooking.hall.Hall;
import org.geekhub.ticketbooking.hall.HallService;
import org.geekhub.ticketbooking.show_seat.ShowSeat;
import org.geekhub.ticketbooking.show_seat.ShowSeatService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShowUtilityService {
    private final HallService hallService;
    private final CinemaService cinemaService;
    private final ShowSeatService showSeatService;

    public ShowUtilityService(HallService hallService, CinemaService cinemaService, ShowSeatService showSeatService) {
        this.hallService = hallService;
        this.cinemaService = cinemaService;
        this.showSeatService = showSeatService;
    }

    public Map<Integer, String> getShowsSelectOptions(List<Show> shows) {
        if (shows == null) {
            return Collections.emptyMap();
        }

        return shows.stream()
            .collect(Collectors.toMap(
                Show::getId,
                show -> {
                    Hall hall = hallService.getHallById(show.getHallId());
                    String hallName = hall.getHallName();
                    Cinema cinema = cinemaService.getCinemaById(hall.getCinemaId());
                    String cinemaName = cinema.getName();
                    String cityName = cinema.getCity().getName();

                    return cityName + ", " + cinemaName + ", " + hallName;
                }
            ));
    }

    public Map<Integer, List<ShowSeat>> getShowsSeats(List<Show> shows) {
        Map<Integer, List<ShowSeat>> seatsByShow = new HashMap<>();

        if (shows == null) {
            return seatsByShow;
        }

        for (Show show : shows) {
            List<ShowSeat> seats = showSeatService.getSeatsByHallAndShow(show.getHallId(), show.getId());
            seatsByShow.put(show.getId(), seats);
        }
        return seatsByShow;
    }

    public Map<Integer, List<Integer>> getSeatsLayoutForShow(List<Show> shows) {
        Map<Integer, List<Integer>> seatsLayout = new HashMap<>();

        if (shows == null) {
            return seatsLayout;
        }

        for (Show show : shows) {
            List<Integer> rowsAndCols = new ArrayList<>();
            int rows = hallService.getHallById(show.getHallId()).getRows();
            int columns = hallService.getHallById(show.getHallId()).getColumns();
            rowsAndCols.add(rows);
            rowsAndCols.add(columns);
            seatsLayout.put(show.getId(), rowsAndCols);
        }
        return seatsLayout;
    }
}
