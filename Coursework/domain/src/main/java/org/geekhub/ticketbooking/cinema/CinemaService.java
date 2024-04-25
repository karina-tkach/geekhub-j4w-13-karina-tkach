package org.geekhub.ticketbooking.cinema;

import org.geekhub.ticketbooking.exception.CinemaValidationException;
import org.geekhub.ticketbooking.city.City;
import org.geekhub.ticketbooking.city.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    private final CinemaValidator cinemaValidator;
    private final CityService cityService;
    private final Logger logger = LoggerFactory.getLogger(CinemaService.class);

    public CinemaService(CinemaRepository cinemaRepository, CinemaValidator cinemaValidator, CityService cityService) {
        this.cinemaRepository = cinemaRepository;
        this.cinemaValidator = cinemaValidator;
        this.cityService = cityService;
    }

    public List<Cinema> getAllCinemas() {
        try {
            logger.info("Try to get cinemas");
            List<Cinema> cinemas = cinemaRepository.getAllCinemas();
            logger.info("Cinemas were fetched successfully");
            return cinemas;
        } catch (DataAccessException exception) {
            logger.warn("Cinemas weren't fetched\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public Cinema getCinemaById(int cinemaId) {
        try {
            logger.info("Try to get cinema by id");
            Cinema cinema = cinemaRepository.getCinemaById(cinemaId);
            logger.info("Cinema was fetched successfully");
            return cinema;
        } catch (DataAccessException exception) {
            logger.warn("Cinema wasn't fetched\n{}", exception.getMessage());
            return null;
        }
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

            logger.info("Cinema was added:\n{}", cinema);
            return getCinemaById(id);
        } catch (CinemaValidationException | DataAccessException exception) {
            logger.warn("Cinema wasn't added: {}\n{}", cinema, exception.getMessage());
            return null;
        }
    }

    public Cinema updateCinemaById(Cinema cinema, int cinemaId) {
        Cinema cinemaToUpdate = getCinemaById(cinemaId);
        try {
            logger.info("Try to update cinema");
            if (cinemaToUpdate == null) {
                throw new CinemaValidationException("Cinema with id '" + cinemaId + "' not found");
            }
            cinemaValidator.validate(cinema);
            City city = cityService.getCityByName(cinema.getCity().getName());

            if (city == null) {
                throw new CinemaValidationException("Cinema city was incorrect");
            }
            int cityId = city.getId();

            cinemaRepository.updateCinemaById(cinema, cinemaId, cityId);
            logger.info("Cinema was updated:\n{}", cinema);
            return getCinemaById(cinemaId);
        } catch (CinemaValidationException | DataAccessException exception) {
            logger.warn("Cinema wasn't updated: {}\n{}", cinemaId, exception.getMessage());
            return null;
        }
    }

    public boolean deleteCinemaById(int cinemaId) {
        Cinema cinemaToDel = getCinemaById(cinemaId);
        try {
            logger.info("Try to delete cinema");
            if (cinemaToDel == null) {
                throw new CinemaValidationException("Cinema with id '" + cinemaId + "' not found");
            }
            cinemaRepository.deleteCinemaById(cinemaId);

            logger.info("Cinema was deleted:\n{}", cinemaToDel);
            return true;
        } catch (CinemaValidationException | DataAccessException exception) {
            logger.warn("Cinema wasn't deleted: {}\n{}", cinemaToDel, exception.getMessage());
            return false;
        }
    }

    public List<Cinema> getCinemasWithPagination(int pageNumber, int limit) {
        try {
            if (pageNumber < 0 || limit < 0) {
                throw new IllegalArgumentException("Page number and limit must be greater than 0");
            }
            logger.info("Try to get cinemas with pagination");
            List<Cinema> cinemas = cinemaRepository.getCinemasWithPagination(pageNumber, limit);
            logger.info("Cinemas were fetched with pagination successfully");
            return cinemas;
        } catch (IllegalArgumentException | DataAccessException exception) {
            logger.warn("Cinemas weren't fetched with pagination\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public int getCinemasRowsCount() {
        try {
            logger.info("Try to get cinemas rows count");
            int count = cinemaRepository.getCinemasRowsCount();
            logger.info("Cinemas rows count were fetched successfully");
            return count;
        } catch (DataAccessException | NullPointerException exception) {
            logger.warn("Cinemas rows count weren't fetched\n{}", exception.getMessage());
            return -1;
        }
    }
}
