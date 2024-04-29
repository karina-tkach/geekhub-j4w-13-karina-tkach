package org.geekhub.ticketbooking.cinema;

import org.geekhub.ticketbooking.city.City;
import org.geekhub.ticketbooking.city.CityService;
import org.geekhub.ticketbooking.exception.CinemaValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CinemaServiceTest {
    @Mock
    private CinemaRepository cinemaRepository;
    @Mock
    private CinemaValidator cinemaValidator;
    @Mock
    private CityService cityService;
    private CinemaService cinemaService;

    @BeforeEach
    void setUp() {
        cinemaService = new CinemaService(cinemaRepository,
            cinemaValidator,
            cityService);
    }

    @Test
    void getAllCinemas_shouldReturnProperList_whenNoException() {
        List<Cinema> expectedCinemas = new ArrayList<>();
        expectedCinemas.add(new Cinema(1, "Cinema 1", new City(1, "City 1"), "Street 1"));
        expectedCinemas.add(new Cinema(2, "Cinema 2", new City(2, "City 2"), "Street 2"));

        when(cinemaRepository.getAllCinemas()).thenReturn(expectedCinemas);

        List<Cinema> actualCinemas = cinemaService.getAllCinemas();

        assertEquals(expectedCinemas, actualCinemas, "The list of cinemas does not match the expected value.");
        verify(cinemaRepository, times(1)).getAllCinemas();
    }

    @Test
    void getAllCinemas_shouldReturnEmptyList_whenException() {
        when(cinemaRepository.getAllCinemas()).thenThrow(new DataAccessException("Database connection error") {});
        List<Cinema> actualCinemas = cinemaService.getAllCinemas();

        assertEquals(Collections.emptyList(), actualCinemas, "The list of cinemas should be empty.");
        verify(cinemaRepository, times(1)).getAllCinemas();
    }

    @Test
    void getCinemaById_shouldReturnProperCinema_whenNoException() {
        int cinemaId = 1;
        Cinema expectedCinema = new Cinema(cinemaId, "Cinema 1", new City(1, "City 1"), "Street 1");

        when(cinemaRepository.getCinemaById(cinemaId)).thenReturn(expectedCinema);

        Cinema actualCinema = cinemaService.getCinemaById(cinemaId);

        assertEquals(expectedCinema, actualCinema, "The cinema does not match the expected value.");
        verify(cinemaRepository, times(1)).getCinemaById(cinemaId);
    }

    @Test
    void getCinemaById_shouldReturnNull_whenException() {
        int cinemaId = 1;
        when(cinemaRepository.getCinemaById(cinemaId)).thenThrow(new DataAccessException("Database connection error") {});

        Cinema actualCinema = cinemaService.getCinemaById(cinemaId);

        assertNull(actualCinema, "The cinema should be null.");
        verify(cinemaRepository, times(1)).getCinemaById(cinemaId);
    }

    @Test
    void addCinema_shouldAdd_whenValidCity() {
        Cinema cinema = new Cinema(1, "Cinema 1", new City(1, "City 1"), "Street 1");
        City city = new City(1, "City 1");

        when(cityService.getCityByName(cinema.getCity().getName())).thenReturn(city);
        when(cinemaRepository.addCinema(cinema, city.getId())).thenReturn(1);
        when(cinemaRepository.getCinemaById(1)).thenReturn(cinema);

        Cinema addedCinema = cinemaService.addCinema(cinema);

        assertEquals(cinema, addedCinema, "The added cinema does not match the expected value.");
        verify(cinemaValidator, times(1)).validate(cinema);
        verify(cityService, times(1)).getCityByName(cinema.getCity().getName());
        verify(cinemaRepository, times(1)).addCinema(cinema, city.getId());
        verify(cinemaRepository, times(1)).getCinemaById(1);
    }

    @Test
    void addCinema_shouldNotAdd_whenInvalidCity() {
        Cinema cinema = new Cinema(1, "Cinema 1", new City(1, "City 1"), "Street 1");

        when(cityService.getCityByName(cinema.getCity().getName())).thenReturn(null);

        Cinema addedCinema = cinemaService.addCinema(cinema);

        assertNull(addedCinema, "The added cinema should be null.");
        verify(cinemaValidator, times(1)).validate(cinema);
        verify(cityService, times(1)).getCityByName(cinema.getCity().getName());
        verify(cinemaRepository, never()).addCinema(Mockito.any(), Mockito.anyInt());
        verify(cinemaRepository, never()).getCinemaById(Mockito.anyInt());
    }

    @Test
    void addCinema_shouldNotAdd_whenInvalidCinema() {
        Cinema cinema = new Cinema(1, "Cinema 1", new City(1, "City 1"), "Street 1");

        doThrow(new CinemaValidationException("Invalid cinema")).when(cinemaValidator).validate(cinema);

        Cinema addedCinema = cinemaService.addCinema(cinema);

        assertNull(addedCinema, "The added cinema should be null.");
        verify(cinemaValidator, times(1)).validate(cinema);
        verify(cityService, times(0)).getCityByName(cinema.getCity().getName());
        verify(cinemaRepository, never()).addCinema(Mockito.any(), Mockito.anyInt());
        verify(cinemaRepository, never()).getCinemaById(Mockito.anyInt());
    }

    @Test
    void addCinema_shouldReturnNull_whenDataAccessException() {
        Cinema cinema = new Cinema(1, "Cinema 1", new City(1, "City 1"), "Street 1");
        City city = new City(1, "City 1");

        when(cityService.getCityByName(cinema.getCity().getName())).thenReturn(city);
        when(cinemaRepository.addCinema(cinema, city.getId())).thenThrow(new DataAccessException("Database connection error") {});

        Cinema addedCinema = cinemaService.addCinema(cinema);

        assertNull(addedCinema, "The added cinema should be null.");
        verify(cinemaValidator, times(1)).validate(cinema);
        verify(cityService, times(1)).getCityByName(cinema.getCity().getName());
        verify(cinemaRepository, times(1)).addCinema(cinema, city.getId());
        verify(cinemaRepository, never()).getCinemaById(Mockito.anyInt());
    }

    @Test
    void updateCinemaById_shouldReturnUpdatedCinema_whenValidCinemaId() {
        int cinemaId = 1;
        City city = new City(1, "Test City");
        Cinema cinema = new Cinema(cinemaId, "Cinema", city, "Street");

        when(cinemaRepository.getCinemaById(cinemaId)).thenReturn(cinema);
        when(cityService.getCityByName(city.getName())).thenReturn(city);
        doNothing().when(cinemaValidator).validate(cinema);
        doNothing().when(cinemaRepository).updateCinemaById(cinema, cinemaId, city.getId());

        Cinema updatedCinema = cinemaService.updateCinemaById(cinema, cinemaId);

        assertNotNull(updatedCinema);
        assertEquals(cinemaId, updatedCinema.getId());
        verify(cinemaRepository, times(2)).getCinemaById(cinemaId);
        verify(cityService, times(1)).getCityByName(city.getName());
        verify(cinemaValidator, times(1)).validate(cinema);
        verify(cinemaRepository, times(1)).updateCinemaById(cinema, cinemaId, city.getId());
    }

    @Test
    void updateCinemaById_shouldReturnNull_whenInvalidCinemaId() {
        int cinemaId = 1;
        Cinema cinema = new Cinema();
        cinema.setId(cinemaId);

        when(cinemaRepository.getCinemaById(cinemaId)).thenReturn(null);

        Cinema updatedCinema = cinemaService.updateCinemaById(cinema, cinemaId);

        assertNull(updatedCinema);
        verify(cinemaRepository, times(1)).getCinemaById(cinemaId);
    }

    @Test
    void updateCinemaById_shouldReturnNull_whenInvalidCity() {
        int cinemaId = 1;
        City city = new City(1, "Test City");
        Cinema cinema = new Cinema(cinemaId, "Cinema", city, "Street");


        when(cinemaRepository.getCinemaById(cinemaId)).thenReturn(cinema);
        when(cityService.getCityByName(city.getName())).thenReturn(null);

        Cinema updatedCinema = cinemaService.updateCinemaById(cinema, cinemaId);

        assertNull(updatedCinema);
        verify(cinemaRepository, times(1)).getCinemaById(cinemaId);
        verify(cityService, times(1)).getCityByName(city.getName());
    }

    @Test
    void updateCinemaById_shouldReturnNull_whenInvalidCinema() {
        int cinemaId = 1;
        City city = new City(1, "Test City");
        Cinema cinema = new Cinema(cinemaId, "Cinema", city, "Street");

        when(cinemaRepository.getCinemaById(cinemaId)).thenReturn(cinema);
        doThrow(new CinemaValidationException("Validation failed")).when(cinemaValidator).validate(cinema);

        Cinema updatedCinema = cinemaService.updateCinemaById(cinema, cinemaId);

        assertNull(updatedCinema);
        verify(cinemaRepository, times(1)).getCinemaById(cinemaId);
        verify(cinemaValidator, times(1)).validate(cinema);
    }

    @Test
    void updateCinemaById_shouldReturnNull_whenDataAccessException() {
        int cinemaId = 1;
        City city = new City(1, "Test City");
        Cinema cinema = new Cinema(cinemaId, "Cinema", city, "Street");

        when(cinemaRepository.getCinemaById(cinemaId)).thenReturn(cinema);
        when(cityService.getCityByName(city.getName())).thenReturn(city);
        doNothing().when(cinemaValidator).validate(cinema);
        doThrow(new DataAccessException("Database error") {}).when(cinemaRepository).updateCinemaById(cinema, cinemaId, city.getId());

        Cinema updatedCinema = cinemaService.updateCinemaById(cinema, cinemaId);

        assertNull(updatedCinema);
        verify(cinemaRepository, times(1)).getCinemaById(cinemaId);
        verify(cityService, times(1)).getCityByName(city.getName());
        verify(cinemaValidator, times(1)).validate(cinema);
        verify(cinemaRepository, times(1)).updateCinemaById(cinema, cinemaId, city.getId());
    }

    @Test
    void deleteCinemaById_shouldReturnTrue_whenValidCinemaId() {
        int cinemaId = 1;
        Cinema cinemaToDelete = new Cinema();
        cinemaToDelete.setId(cinemaId);
        when(cinemaRepository.getCinemaById(cinemaId)).thenReturn(cinemaToDelete);

        boolean result = cinemaService.deleteCinemaById(cinemaId);

        assertTrue(result);
        verify(cinemaRepository).deleteCinemaById(cinemaId);
    }

    @Test
    void deleteCinemaById_shouldReturnFalse_whenInvalidCinemaId() {
        int cinemaId = 1;
        when(cinemaRepository.getCinemaById(cinemaId)).thenReturn(null);

        boolean result = cinemaService.deleteCinemaById(cinemaId);

        assertFalse(result);
    }

    @Test
    void getCinemasWithPagination_shouldReturnCinemas_whenValidPageNumberAndLimit() {
        int pageNumber = 1;
        int limit = 10;
        List<Cinema> expectedCinemas = Collections.singletonList(new Cinema());

        when(cinemaRepository.getCinemasWithPagination(pageNumber, limit)).thenReturn(expectedCinemas);

        List<Cinema> actualCinemas = cinemaService.getCinemasWithPagination(pageNumber, limit);

        assertEquals(expectedCinemas, actualCinemas, "The returned cinemas do not match the expected cinemas.");
    }

    @Test
    void getCinemasWithPagination_shouldReturnEmptyList_whenInvalidPageNumber() {
        int pageNumber = -1;
        int limit = 10;

        List<Cinema> actualCinemas = cinemaService.getCinemasWithPagination(pageNumber, limit);

        assertTrue(actualCinemas.isEmpty(), "The returned list of cinemas should be empty.");
    }

    @Test
    void getCinemasWithPagination_shouldReturnEmptyList_whenInvalidLimit() {
        int pageNumber = 1;
        int limit = -1;

        List<Cinema> actualCinemas = cinemaService.getCinemasWithPagination(pageNumber, limit);

        assertTrue(actualCinemas.isEmpty(), "The returned list of cinemas should be empty.");
    }

    @Test
    void getCinemasWithPagination_shouldReturnEmptyList_whenDataAccessException() {
        int pageNumber = 1;
        int limit = 10;

        doThrow(new DataAccessException("Database error") {}).when(cinemaRepository).getCinemasWithPagination(pageNumber, limit);

        List<Cinema> actualCinemas = cinemaService.getCinemasWithPagination(pageNumber, limit);

        assertTrue(actualCinemas.isEmpty(), "The returned list of cinemas should be empty.");
    }

    @Test
    void getCinemasRowsCount_shouldReturnCount_whenValidCount() {
        int expectedCount = 10;

        when(cinemaRepository.getCinemasRowsCount()).thenReturn(expectedCount);

        int actualCount = cinemaService.getCinemasRowsCount();

        assertEquals(expectedCount, actualCount, "The returned count does not match the expected count.");
    }

    @Test
    void getCinemasRowsCount_shouldReturnNegativeOne_whenInvalidCount() {
        doThrow(new DataAccessException("Database error") {}).when(cinemaRepository).getCinemasRowsCount();

        int actualCount = cinemaService.getCinemasRowsCount();

        assertEquals(-1, actualCount, "The returned count should be -1.");
    }
}
