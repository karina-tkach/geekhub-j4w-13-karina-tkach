package org.geekhub.ticketbooking.city;

import org.geekhub.ticketbooking.exception.CityValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {
    @Mock
    private CityRepository cityRepository;
    @Mock
    private CityValidator cityValidator;
    private CityService cityService;

    @BeforeEach
    public void setup() {
        cityService = new CityService(cityRepository, cityValidator);
    }

    @Test
    void getAllCities_shouldReturnCities_whenNoException() {
        List<City> expectedCities = Collections.singletonList(new City(1, "City 1"));
        when(cityRepository.getAllCities()).thenReturn(expectedCities);

        List<City> actualCities = cityService.getAllCities();

        assertEquals(expectedCities, actualCities, "The list of cities does not match the expected result.");
        verify(cityRepository, times(1)).getAllCities();
    }

    @Test
    void getAllCities_shouldReturnEmptyList_whenDataAccessException() {
        when(cityRepository.getAllCities()).thenThrow(new DataAccessException("Database connection error") {
        });

        List<City> actualCities = cityService.getAllCities();

        assertTrue(actualCities.isEmpty(), "The list of cities should be empty.");
        verify(cityRepository, times(1)).getAllCities();
    }

    @Test
    void getCityById_shouldReturnCity_whenNoException() {
        int cityId = 1;
        City expectedCity = new City(cityId, "City 1");
        when(cityRepository.getCityById(cityId)).thenReturn(expectedCity);

        City actualCity = cityService.getCityById(cityId);

        assertEquals(expectedCity, actualCity, "The city does not match the expected result.");
        verify(cityRepository, times(1)).getCityById(cityId);
    }

    @Test
    void getCityById_shouldReturnNull_whenDatAccessException() {
        int cityId = 1;
        when(cityRepository.getCityById(cityId)).thenThrow(new DataAccessException("Database connection error") {
        });

        City actualCity = cityService.getCityById(cityId);

        assertNull(actualCity, "The city should be null.");
        verify(cityRepository, times(1)).getCityById(cityId);
    }

    @Test
    void getCityByName_shouldReturnCity_whenNoException() {
        String cityName = "City 1";
        City expectedCity = new City(1, cityName);
        when(cityRepository.getCityByName(cityName)).thenReturn(expectedCity);

        City actualCity = cityService.getCityByName(cityName);

        assertEquals(expectedCity, actualCity, "The city does not match the expected result.");
        verify(cityValidator, times(1)).validateName(cityName);
        verify(cityRepository, times(1)).getCityByName(cityName);
    }

    @Test
    void getCityByName_shouldReturnNull_whenCityValidationException() {
        String cityName = "Invalid City";
        doThrow(new CityValidationException("Invalid city name")).when(cityValidator).validateName(cityName);

        City actualCity = cityService.getCityByName(cityName);

        assertNull(actualCity, "The city should be null.");
        verify(cityValidator, times(1)).validateName(cityName);
        verify(cityRepository, never()).getCityByName(cityName);
    }

    @Test
    void getCityByName_shouldReturnNull_whenDataAccessException() {
        String cityName = "City 1";
        when(cityRepository.getCityByName(cityName)).thenThrow(new DataAccessException("Database connection error") {
        });

        City actualCity = cityService.getCityByName(cityName);

        assertNull(actualCity, "The city should be null.");
        verify(cityValidator, times(1)).validateName(cityName);
        verify(cityRepository, times(1)).getCityByName(cityName);
    }

    @Test
    void addCity_shouldAdd_whenNoException() {
        City city = new City(1, "New City");
        when(cityRepository.addCity(city)).thenReturn(1);
        when(cityRepository.getCityByName(city.getName())).thenReturn(null);
        when(cityService.getCityById(1)).thenReturn(city);

        City addedCity = cityService.addCity(city);

        assertEquals(city, addedCity, "The added city does not match the expected result.");
        verify(cityValidator, times(1)).validate(city);
        verify(cityRepository, times(1)).addCity(city);
        verify(cityRepository, times(1)).getCityByName(city.getName());
        verify(cityRepository, times(1)).getCityById(1);
    }

    @Test
    void addCity_shouldNotAdd_whenCityValidationException() {
        City city = new City(1, "Existing City");
        when(cityRepository.getCityByName(city.getName())).thenReturn(city);

        City addedCity = cityService.addCity(city);

        assertNull(addedCity, "The added city should be null.");
        verify(cityValidator, times(1)).validate(city);
        verify(cityRepository, times(1)).getCityByName(city.getName());
        verify(cityRepository, never()).addCity(city);
        verify(cityRepository, never()).getCityById(anyInt());
    }

    @Test
    void addCity_shouldNotAdd_whenDataAccessException() {
        City city = new City(1, "New City");
        when(cityRepository.addCity(city)).thenReturn(-1);

        City addedCity = cityService.addCity(city);

        assertNull(addedCity, "The added city should be null.");
        verify(cityValidator, times(1)).validate(city);
        verify(cityRepository, times(1)).addCity(city);
        verify(cityRepository, never()).getCityById(anyInt());
    }

    @Test
    void updateCityById_shouldUpdateCity_whenValidCityId() {
        int cityId = 1;
        City cityToUpdate = new City(cityId, "New York");
        when(cityRepository.getCityById(cityId)).thenReturn(cityToUpdate);
        doNothing().when(cityValidator).validate(cityToUpdate);
        doNothing().when(cityRepository).updateCityById(cityToUpdate, cityId);

        City result = cityService.updateCityById(cityToUpdate, cityId);

        assertNotNull(result);
        assertEquals(cityToUpdate, result);
    }

    @Test
    void updateCityById_shouldNotUpdateCity_whenInvalidCityId() {
        int cityId = 1;
        City cityToUpdate = new City(cityId, "New York");
        when(cityRepository.getCityById(cityId)).thenReturn(null);

        City result = cityService.updateCityById(cityToUpdate, cityId);

        assertNull(result);
    }

    @Test
    void updateCityById_shouldNotUpdateCity_whenInvalidCity() {
        int cityId = 1;
        City cityToUpdate = new City(cityId, null);
        when(cityRepository.getCityById(cityId)).thenReturn(new City(cityId, "New York"));
        doThrow(new CityValidationException("Invalid city")).when(cityValidator).validate(cityToUpdate);

        City result = cityService.updateCityById(cityToUpdate, cityId);

        assertNull(result);
    }

    @Test
    void deleteCityById_shouldDeleteCity_whenValidCityId() {
        int cityId = 1;
        City cityToDelete = new City(cityId, "New York");
        when(cityRepository.getCityById(cityId)).thenReturn(cityToDelete);
        doNothing().when(cityRepository).deleteCityById(cityId);

        boolean result = cityService.deleteCityById(cityId);

        assertTrue(result);
    }

    @Test
    void deleteCityById_shouldNotDeleteCity_whenInvalidCityId() {
        int cityId = 1;
        when(cityRepository.getCityById(cityId)).thenReturn(null);

        boolean result = cityService.deleteCityById(cityId);

        assertFalse(result);
    }

    @Test
    void getCitiesWithPagination_shouldReturnCities_whenValidParameters() {
        List<City> expectedCities = List.of(new City(1, "City 1"), new City(2, "City 2"));
        when(cityRepository.getCitiesWithPagination(1, 10)).thenReturn(expectedCities);

        List<City> actualCities = cityService.getCitiesWithPagination(1, 10);

        verify(cityRepository).getCitiesWithPagination(1, 10);

        assertEquals(expectedCities, actualCities, "The getCitiesWithPagination method returned an incorrect list of cities.");
    }

    @Test
    void getCitiesWithPagination_shouldReturnEmptyList_whenInvalidParameters() {
        List<City> result = cityService.getCitiesWithPagination(-1, -10);

        assertEquals(Collections.emptyList(), result, "The getCitiesWithPagination method did not return an empty list when an exception occurred.");
        verify(cityRepository, never()).getCitiesWithPagination(anyInt(), anyInt());
    }

    @Test
    void getCitiesWithPagination_shouldReturnEmptyList_whenDataAccessException() {
        when(cityRepository.getCitiesWithPagination(anyInt(), anyInt())).thenThrow(new DataAccessException("Exception") {
        });

        List<City> actualCities = cityService.getCitiesWithPagination(1, 10);

        verify(cityRepository).getCitiesWithPagination(1, 10);

        assertEquals(Collections.emptyList(), actualCities, "The getCitiesWithPagination method did not return an empty list when an exception occurred.");
    }

    @Test
    void getCitiesRowsCount_shouldReturnCount_whenNoException() {
        int expectedCount = 100;

        when(cityRepository.getCitiesRowsCount()).thenReturn(expectedCount);

        int actualCount = cityService.getCitiesRowsCount();

        verify(cityRepository).getCitiesRowsCount();

        assertEquals(expectedCount, actualCount, "The getCitiesRowsCount method returned an incorrect count of cities.");
    }

    @Test
    void getCitiesRowsCount_shouldReturnNegativeOne_whenDataAccessException() {
        when(cityRepository.getCitiesRowsCount()).thenThrow(new DataAccessException("Exception") {
        });

        int actualCount = cityService.getCitiesRowsCount();

        verify(cityRepository).getCitiesRowsCount();

        assertEquals(-1, actualCount, "The getCitiesRowsCount method did not return -1 when an exception occurred.");
    }
}
