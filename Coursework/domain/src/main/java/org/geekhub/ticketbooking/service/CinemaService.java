package org.geekhub.ticketbooking.service;

import org.geekhub.ticketbooking.exception.CinemaValidationException;
import org.geekhub.ticketbooking.model.Cinema;
import org.geekhub.ticketbooking.model.City;
import org.geekhub.ticketbooking.model.Hall;
import org.geekhub.ticketbooking.repository.interfaces.CinemaRepository;
import org.geekhub.ticketbooking.validator.CinemaValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    private final CinemaValidator cinemaValidator;
    private final CityService cityService;
    private final HallService hallService;
    private final Logger logger = LoggerFactory.getLogger(CinemaService.class);

    public CinemaService(CinemaRepository cinemaRepository, CinemaValidator cinemaValidator, CityService cityService, HallService hallService) {
        this.cinemaRepository = cinemaRepository;
        this.cinemaValidator = cinemaValidator;
        this.cityService = cityService;
        this.hallService = hallService;
    }

    public List<Cinema> getAllCinemas() {
        return null;
    }

    public Cinema getCinemaById(int cinemaId) {
     cinemaRepository.getCinemaById(cinemaId);
     //hallService.getHallsByCinema()
        return null;
    }

    public List<Cinema> getCinemasByCity(City city) {
return null;
    }

    public Cinema getCinemaByCityAndName(City city, String name) {
return null;
    }

    public Cinema addCinema(Cinema cinema) {
        try {
            logger.info("Try to add cinema");
            cinemaValidator.validate(cinema);
            City city = cityService.getCityByName(cinema.getCity().getName());

            if (city == null) {
                throw new CinemaValidationException("Cinema city was incorrect");
            }
            int cityId = city.getId();

            int id = cinemaRepository.addCinema(cinema, cityId);
            if (id == -1) {
                throw new CinemaValidationException("Unable to retrieve the generated key");
            }

            for (Hall hall : cinema.getHalls()) {
                hallService.addHall(hall);
            }

            logger.info("Cinema was added:\n{}", cinema);
            return getCinemaById(id);
        } catch (CinemaValidationException | DataAccessException exception) {
            logger.warn("Cinema wasn't added: {}\n{}", cinema, exception.getMessage());
            return null;
        }

    }

    public Cinema updateCinemaById(Cinema cinema, int cinemaId) {
        return null;
    }

    public boolean deleteCinemaById(int cinemaId) {
        Cinema cinemaToDel = getCinemaById(cinemaId);
        try {
            if (cinemaToDel == null) {
                throw new CinemaValidationException("Cinema with id '" + cinemaId + "' not found");
            }
            cinemaRepository.deleteCinemaById(cinemaId);

            logger.info("Cinema was deleted:\n{}", cinemaToDel);
            return true;
        } catch (CinemaValidationException | DataAccessException exception) {
            logger.warn("Cinema wasn't deleted: {}\n{}",cinemaToDel, exception.getMessage());
            return false;
        }
    }
}
