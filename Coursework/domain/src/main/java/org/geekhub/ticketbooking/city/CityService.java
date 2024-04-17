package org.geekhub.ticketbooking.city;

import org.geekhub.ticketbooking.exception.CityValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private final CityValidator cityValidator;
    private final Logger logger = LoggerFactory.getLogger(CityService.class);

    public CityService(CityRepository cityRepository, CityValidator cityValidator) {
        this.cityRepository = cityRepository;
        this.cityValidator = cityValidator;
    }

    public List<City> getAllCities() {
        try {
            logger.info("Try to get all cities");
            List<City> cities = cityRepository.getAllCities();
            logger.info("Cities ware fetched successfully");
            return cities;
        } catch (DataAccessException exception) {
            logger.warn("Cities weren't fetched\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public City getCityById(int cityId) {
        try {
            logger.info("Try to get city by id");
            City city = cityRepository.getCityById(cityId);
            logger.info("City was fetched successfully");
            return city;
        } catch (DataAccessException exception) {
            logger.warn("City wasn't fetched\n{}", exception.getMessage());
            return null;
        }
    }

    public City getCityByName(String name) {
        try {
            logger.info("Try to get city by name");
            cityValidator.validateName(name);
            City city = cityRepository.getCityByName(name);
            logger.info("City was fetched successfully");
            return city;
        } catch (CityValidationException | DataAccessException exception) {
            logger.warn("City wasn't fetched\n{}", exception.getMessage());
            return null;
        }
    }

    public City addCity(City city) {
        try {
            logger.info("Try to add city");
            cityValidator.validate(city);

            City existsCity = getCityByName(city.getName());
            if (existsCity != null) {
                throw new CityValidationException(
                    "City with name '" + existsCity.getName() + "' already exists");
            }

            int id = cityRepository.addCity(city);
            if (id == -1) {
                throw new CityValidationException("Unable to retrieve the generated key");
            }

            logger.info("City was added:\n{}", city);
            return getCityById(id);
        } catch (CityValidationException | DataAccessException exception) {
            logger.warn("City wasn't added: {}\n{}", city, exception.getMessage());
            return null;
        }
    }

    public City updateCityById(City city, int cityId) {
        City cityToUpdate = getCityById(cityId);
        try {
            logger.info("Try to update city");
            if (cityToUpdate == null) {
                throw new CityValidationException("City with id '" + cityId + "' not found");
            }
            cityValidator.validate(city);

            cityRepository.updateCityById(city, cityId);
            logger.info("City was updated:\n{}", city);
            return getCityById(cityId);
        } catch (CityValidationException | DataAccessException exception) {
            logger.warn("City wasn't updated: {}\n{}", city, exception.getMessage());
            return null;
        }
    }

    public boolean deleteCityById(int cityId) {
        City cityToDel = getCityById(cityId);
        try {
            logger.info("Try to delete city");
            if (cityToDel == null) {
                throw new CityValidationException("City with id '" + cityId + "' not found");
            }

            cityRepository.deleteCityById(cityId);
            logger.info("City was deleted:\n{}", cityToDel);
            return true;
        } catch (CityValidationException | DataAccessException exception) {
            logger.warn("City wasn't deleted: {}\n{}", cityToDel, exception.getMessage());
            return false;
        }
    }

    public List<City> getCitiesWithPagination(int pageNumber, int limit) {
        try {
            if (pageNumber < 0 || limit < 0) {
                throw new IllegalArgumentException("Page number and limit must be greater than 0");
            }
            logger.info("Try to get cities with pagination");
            List<City> cities = cityRepository.getCitiesWithPagination(pageNumber, limit);
            logger.info("Cities were fetched with pagination successfully");
            return cities;
        } catch (IllegalArgumentException | DataAccessException exception) {
            logger.warn("Cities weren't fetched with pagination\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public int getCitiesRowsCount() {
        try {
            logger.info("Try to get cities rows count");
            int count = cityRepository.getCitiesRowsCount();
            logger.info("Cities rows count were fetched successfully");
            return count;
        } catch (DataAccessException | NullPointerException exception) {
            logger.warn("Cities rows count weren't fetched\n{}", exception.getMessage());
            return -1;
        }
    }
}
