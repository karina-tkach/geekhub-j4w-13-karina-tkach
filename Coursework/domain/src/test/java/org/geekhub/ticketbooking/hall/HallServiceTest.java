package org.geekhub.ticketbooking.hall;

import org.geekhub.ticketbooking.exception.HallValidationException;
import org.geekhub.ticketbooking.seat.Seat;
import org.geekhub.ticketbooking.seat.SeatService;
import org.geekhub.ticketbooking.show.ShowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HallServiceTest {
    @Mock
    private HallRepository hallRepository;
    @Mock
    private HallValidator hallValidator;
    @Mock
    private ShowService showService;
    @Mock
    private SeatService seatService;
    private HallService hallService;

    @BeforeEach
    public void setUp() {
        hallService = new HallService(hallRepository, hallValidator, showService, seatService);
    }

    @Test
    void getHallById_shouldReturnHall_whenValidId() {
        int hallId = 1;
        Hall expectedHall = new Hall();
        when(hallRepository.getHallById(hallId)).thenReturn(expectedHall);

        Hall actualHall = hallService.getHallById(hallId);

        assertEquals(expectedHall, actualHall, "The returned hall does not match the expected hall.");
    }

    @Test
    void getHallById_shouldReturnNull_whenInvalidId() throws HallValidationException, DataAccessException {
        int hallId = -1;
        when(hallRepository.getHallById(hallId)).thenThrow(new HallValidationException("Invalid hall ID"));

        Hall actualHall = hallService.getHallById(hallId);

        assertNull(actualHall, "The returned hall should be null.");
    }

    @Test
    void getHallsByCinema_shouldReturnHalls_whenValidCinemaId() throws HallValidationException, DataAccessException {
        int cinemaId = 1;
        List<Hall> expectedHalls = Collections.singletonList(new Hall());
        when(hallRepository.getHallsByCinema(cinemaId)).thenReturn(expectedHalls);

        List<Hall> actualHalls = hallService.getHallsByCinema(cinemaId);

        assertEquals(expectedHalls, actualHalls, "The returned halls do not match the expected halls.");
    }

    @Test
    void getHallsByCinema_shouldReturnEmptyList_whenInvalidCinemaId() throws HallValidationException, DataAccessException {
        int cinemaId = -1;
        when(hallRepository.getHallsByCinema(cinemaId)).thenThrow(new HallValidationException("Invalid cinema ID"));

        List<Hall> actualHalls = hallService.getHallsByCinema(cinemaId);

        assertTrue(actualHalls.isEmpty(), "The returned halls should be an empty list.");
    }

    @Test
    void addHall_shouldAddHall_whenValidHall() {
        int cinemaId = 1;
        Hall hall = new Hall(1, "ABCD", 2, 2, cinemaId);

        doNothing().when(hallValidator).validate(hall);
        when(seatService.addSeat(any(Seat.class), eq(hall.getId()))).thenReturn(new Seat());
        when(hallRepository.addHall(hall, cinemaId)).thenReturn(1);
        when(hallRepository.getHallById(hall.getId())).thenReturn(hall);

        Hall result = hallService.addHall(hall, cinemaId);

        verify(hallValidator).validate(hall);
        verify(hallRepository).addHall(hall, cinemaId);

        assertNotNull(result);
        assertSame(hall, result);
    }

    @Test
    void addHall_shouldReturnNull_whenInvalidHall() {
        Hall hall = mock(Hall.class);
        int cinemaId = 1;

        doThrow(new HallValidationException("Exception")).when(hallValidator).validate(hall);

        Hall result = hallService.addHall(hall, cinemaId);

        verify(hallValidator).validate(hall);
        verify(hallRepository, never()).addHall(hall, cinemaId);

        assertNull(result);
    }

    @Test
    void addHall_shouldNotAddHall_whenNoKeyGenerated() {
        Hall hall = mock(Hall.class);
        int cinemaId = 1;

        doNothing().when(hallValidator).validate(hall);
        when(hallRepository.addHall(hall, cinemaId)).thenReturn(-1);

        Hall result = hallService.addHall(hall, cinemaId);

        verify(hallValidator).validate(hall);
        verify(hallRepository).addHall(hall, cinemaId);

        assertNull(result);
    }

    @Test
    void setHallSeats_shouldAdd_whenValidHall() {
        Hall hall = mock(Hall.class);
        int hallId = 1;

        verify(seatService, times(hall.getRows() * hall.getColumns())).addSeat(any(Seat.class), eq(hallId));
    }

    @Test
    void updateHallById_shouldUpdate_whenValidHallId() {
        int cinemaId = 1;
        int hallId = 1;
        Hall hallToUpdate = new Hall(hallId, "ABCD", 10, 10, cinemaId);

        when(hallRepository.getHallById(hallId)).thenReturn(hallToUpdate);

        Hall result = hallService.updateHallById(hallToUpdate, cinemaId, hallId);

        assertNotNull(result);
        assertEquals(hallId, result.getId());
        assertEquals(hallToUpdate.getRows(), result.getRows());
        assertEquals(hallToUpdate.getColumns(), result.getColumns());

        verify(hallValidator).validate(hallToUpdate);
        verify(hallRepository).updateHallById(hallToUpdate, cinemaId, hallId);
    }

    @Test
    void updateHallById_shouldReturnNull_whenInvalidHallId() {
        int cinemaId = 1;
        int hallId = 1;
        Hall updatedHall = new Hall();
        updatedHall.setId(hallId);
        updatedHall.setRows(8);
        updatedHall.setColumns(8);

        when(hallRepository.getHallById(hallId)).thenReturn(null);

        Hall result = hallService.updateHallById(updatedHall, cinemaId, hallId);

        assertNull(result);

        verify(hallValidator, never()).validate(updatedHall);
        verify(hallRepository, never()).updateHallById(updatedHall, cinemaId, hallId);
        verify(showService, never()).deleteShowById(anyInt());
        verify(seatService, never()).deleteSeatById(anyInt());
        verify(seatService, never()).addSeat(any(Seat.class), eq(hallId));
    }

    @Test
    void updateHallById_shouldNotUpdate_whenInvalidHall() {
        int cinemaId = 1;
        int hallId = 1;
        Hall hallToUpdate = new Hall();
        hallToUpdate.setId(hallId);
        hallToUpdate.setRows(10);
        hallToUpdate.setColumns(10);

        Hall updatedHall = new Hall();
        updatedHall.setId(hallId);
        updatedHall.setRows(0);
        updatedHall.setColumns(8);

        when(hallRepository.getHallById(hallId)).thenReturn(hallToUpdate);
        doThrow(HallValidationException.class).when(hallValidator).validate(updatedHall);

        Hall result = hallService.updateHallById(updatedHall, cinemaId, hallId);

        assertNull(result);

        verify(hallValidator).validate(updatedHall);
        verify(hallRepository, never()).updateHallById(updatedHall, cinemaId, hallId);
        verify(showService, never()).deleteShowById(anyInt());
        verify(seatService, never()).deleteSeatById(anyInt());
        verify(seatService, never()).addSeat(any(Seat.class), eq(hallId));
    }

    @Test
    void deleteHallById_shouldDelete_whenValidHallId() {
        int hallId = 1;
        Hall hallToDelete = new Hall(hallId, "ABCD", 1, 5, 5);
        List<Hall> halls = Arrays.asList(new Hall(1, "ABCD", 1, 5, 5), new Hall(2, "ABCD", 1, 5, 5));
        when(hallRepository.getHallById(hallId)).thenReturn(hallToDelete);
        when(hallRepository.getHallsByCinema(hallToDelete.getCinemaId())).thenReturn(halls);
        doNothing().when(hallRepository).deleteHallById(hallId);

        boolean result = hallService.deleteHallById(hallId);

        assertTrue(result);
        verify(hallRepository, times(1)).getHallById(hallId);
        verify(hallRepository, times(1)).getHallsByCinema(hallToDelete.getCinemaId());
        verify(hallRepository, times(1)).deleteHallById(hallId);
    }

    @Test
    void deleteHallById_shouldNotDelete_whenInvalidHallId() {
        int hallId = 1;
        Hall hallToDelete = null;
        when(hallRepository.getHallById(hallId)).thenReturn(hallToDelete);

        boolean result = hallService.deleteHallById(hallId);

        assertFalse(result);
        verify(hallRepository, times(1)).getHallById(hallId);
        verify(hallRepository, times(0)).getHallsByCinema(anyInt());
        verify(hallRepository, times(0)).deleteHallById(anyInt());
    }

    @Test
    void deleteHallById_shouldNotDelete_whenLastHall() {
        int hallId = 1;
        Hall hallToDelete = new Hall(hallId, "ABCD", 1, 5, 5);
        List<Hall> halls = Collections.singletonList(hallToDelete);
        when(hallRepository.getHallById(hallId)).thenReturn(hallToDelete);
        when(hallRepository.getHallsByCinema(hallToDelete.getCinemaId())).thenReturn(halls);

        boolean result = hallService.deleteHallById(hallId);

        assertFalse(result);
        verify(hallRepository, times(1)).getHallById(hallId);
        verify(hallRepository, times(1)).getHallsByCinema(hallToDelete.getCinemaId());
        verify(hallRepository, times(0)).deleteHallById(anyInt());
    }

    @Test
    void deleteHallById_shouldNotDelete_whenDataAccessException() {
        int hallId = 1;
        Hall hallToDelete = new Hall(hallId, "ABCD", 1, 5, 5);
        when(hallRepository.getHallById(hallId)).thenReturn(hallToDelete);
        doThrow(new DataAccessException("Data access error") {
        }).when(hallRepository).deleteHallById(hallId);

        boolean result = hallService.deleteHallById(hallId);

        assertFalse(result);
        verify(hallRepository, times(1)).getHallById(hallId);
        verify(hallRepository, times(1)).getHallsByCinema(hallToDelete.getCinemaId());
    }

    @Test
    void getHallsByCinemaWithPagination_shouldReturnHalls_whenValidInputs() {
        int cinemaId = 1;
        int pageNumber = 1;
        int limit = 10;
        List<Hall> expectedHalls = Collections.singletonList(new Hall());
        HallRepository hallRepository = mock(HallRepository.class);
        when(hallRepository.getHallsByCinemaWithPagination(cinemaId, pageNumber, limit)).thenReturn(expectedHalls);
        HallService hallService = new HallService(hallRepository, null, null, null);

        List<Hall> actualHalls = hallService.getHallsByCinemaWithPagination(cinemaId, pageNumber, limit);

        assertEquals(expectedHalls, actualHalls, "The halls fetched with pagination did not match the expected halls.");
        verify(hallRepository, times(1)).getHallsByCinemaWithPagination(cinemaId, pageNumber, limit);
    }

    @Test
    void getHallsByCinemaWithPagination_shouldReturnEmptyList_whenInvalidPageNumber() {
        int cinemaId = 1;
        int pageNumber = -1;
        int limit = 10;

        assertEquals(Collections.emptyList(), hallService.getHallsByCinemaWithPagination(cinemaId, pageNumber, limit));
    }

    @Test
    void getHallsByCinemaWithPagination_shouldReturnEmptyList_whenInvalidLimit() {
        int cinemaId = 1;
        int pageNumber = 1;
        int limit = -1;

        assertEquals(Collections.emptyList(), hallService.getHallsByCinemaWithPagination(cinemaId, pageNumber, limit));
    }

    @Test
    void getHallsByCinemaWithPagination_shouldReturnEmptyList_whenDataAccessException() {
        int cinemaId = 1;
        int pageNumber = 1;
        int limit = 10;
        HallRepository hallRepository = mock(HallRepository.class);
        when(hallRepository.getHallsByCinemaWithPagination(cinemaId, pageNumber, limit)).thenThrow(new DataAccessException("Exception") {
        });
        HallService hallService = new HallService(hallRepository, null, null, null);

        List<Hall> actualHalls = hallService.getHallsByCinemaWithPagination(cinemaId, pageNumber, limit);

        assertEquals(Collections.emptyList(), actualHalls, "The halls fetched with pagination should be an empty list.");
        verify(hallRepository, times(1)).getHallsByCinemaWithPagination(cinemaId, pageNumber, limit);
    }

    @Test
    void getHallsByCinemaRowsCount_shouldReturnCount_whenValidCinemaId() {
        int cinemaId = 1;
        int expectedCount = 5;
        HallRepository hallRepository = mock(HallRepository.class);
        when(hallRepository.getHallsByCinemaRowsCount(cinemaId)).thenReturn(expectedCount);
        HallService hallService = new HallService(hallRepository, null, null, null);

        int actualCount = hallService.getHallsByCinemaRowsCount(cinemaId);

        assertEquals(expectedCount, actualCount, "The count of halls by cinema rows did not match the expected count.");
        verify(hallRepository, times(1)).getHallsByCinemaRowsCount(cinemaId);
    }

    @Test
    void getHallsByCinemaRowsCount_shouldReturnNegativeOne_whenDataAccessException() {
        int cinemaId = 1;
        HallRepository hallRepository = mock(HallRepository.class);
        when(hallRepository.getHallsByCinemaRowsCount(cinemaId)).thenThrow(new DataAccessException("Exception") {
        });
        HallService hallService = new HallService(hallRepository, null, null, null);
        int actualCount = hallService.getHallsByCinemaRowsCount(cinemaId);

        assertEquals(-1, actualCount, "The count of halls by cinema rows should be -1.");
        verify(hallRepository, times(1)).getHallsByCinemaRowsCount(cinemaId);
    }
}
