package org.geekhub.ticketbooking.repository.interfaces;

import org.geekhub.ticketbooking.model.City;

import java.util.List;

public interface CityRepository {
    List<City> getAllCities();

    City getCityById(int cityId);

    City getCityByName(String name);

    int addCity(City city);

    void updateCityById(City city, int cityId);

    void deleteCityById(int cityId);
}
