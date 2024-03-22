package org.geekhub.ticketbooking.service;

import org.geekhub.ticketbooking.exception.CityValidationException;
import org.geekhub.ticketbooking.model.City;
import org.geekhub.ticketbooking.repository.interfaces.CityRepository;
import org.geekhub.ticketbooking.validator.CityValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        return  cityRepository.getAllCities();
    }

    public City getCityById(int cityId) {
        return cityRepository.getCityById(cityId);
    }

    public City getCityByName(String name) {
        try {
            logger.info("Try to get city by name");
            cityValidator.validateName(name);
            City city = cityRepository.getCityByName(name);
            logger.info("City was fetched successfully");
            return city;
        }
        catch (CityValidationException exception) {
            logger.warn(exception.getMessage());
            return null;
        }
    }

    public City addCity(City city) {
        try {
            logger.info("Try to add city");
            cityValidator.validate(city);

            City existsCity = getCityByName(city.getName());
            if (existsCity != null) {
                throw new IllegalArgumentException(
                    "City with name '" + existsCity.getName() + "' already exists");
            }

            int id = cityRepository.addCity(city);
            if (id == -1) {
                throw new IllegalArgumentException("Unable to retrieve the generated key");
            }
            city.setId(id);
            logger.info("City was added:\n" + city);
            return getCityById(id);
        } catch (IllegalArgumentException | CityValidationException exception) {
            logger.warn("City wasn't added: " + city + "\n" + exception.getMessage());
            return null;
        }
    }

    public City updateCityById(City city, int cityId) {
        try {
            logger.info("Try to update city");
            City cityToUpdate = getCityById(cityId);
            cityValidator.validate(city);
            cityValidator.validate(cityToUpdate);

            cityRepository.updateCityById(city, cityId);
            logger.info("City was updated:\n" + city);
            return getCityById(cityId);
        } catch (IllegalArgumentException | CityValidationException exception) {
            logger.warn("City wasn't updated: " + city + "\n" + exception.getMessage());
            return null;
        }
    }

    public boolean deleteCityById(int cityId) {
        City cityToDel = getCityById(cityId);
        try {
            logger.info("Try to delete city");
            cityValidator.validate(cityToDel);

            cityRepository.deleteCityById(cityId);
            logger.info("City was deleted:\n" + cityToDel);
            return true;
        } catch (IllegalArgumentException | CityValidationException exception) {
            logger.warn("City wasn't deleted: " + cityToDel + "\n" + exception.getMessage());
            return false;
        }
    }
}
