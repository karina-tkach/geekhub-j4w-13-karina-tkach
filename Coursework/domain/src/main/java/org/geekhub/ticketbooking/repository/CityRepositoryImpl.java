package org.geekhub.ticketbooking.repository;

import org.geekhub.ticketbooking.model.City;
import org.geekhub.ticketbooking.repository.interfaces.CityRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class CityRepositoryImpl implements CityRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CityRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<City> getAllCities() {
        return null;
    }

    @Override
    public City getCityById(int cityId) {
        return null;
    }

    @Override
    public City getCityByName(String name) {
        return null;
    }

    @Override
    public int addCity(City city) {
        return 0;
    }

    @Override
    public void updateCityById(int cityId) {

    }

    @Override
    public void deleteCityById(int cityId) {

    }
}
