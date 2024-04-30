package org.geekhub.ticketbooking.movie;

import org.geekhub.ticketbooking.exception.MovieValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private MovieValidator movieValidator;
    private MovieService movieService;

    @BeforeEach
    public void setup() {
        movieService = new MovieService(movieRepository, movieValidator);
    }

    @Test
    void getAllMovies_shouldReturnMovies_whenNoException() {
        List<Movie> expectedMovies = Collections.singletonList(new Movie());

        when(movieRepository.getAllMovies()).thenReturn(expectedMovies);

        List<Movie> actualMovies = movieService.getAllMovies();

        verify(movieRepository, times(1)).getAllMovies();

        assertEquals(expectedMovies, actualMovies, "The getAllMovies method did not return the expected list of movies.");
    }

    @Test
    void getAllMovies_shouldReturnEmptyList_whenDataAccessException() {
        when(movieRepository.getAllMovies()).thenThrow(new DataAccessException("Failed to fetch movies") {});

        List<Movie> actualMovies = movieService.getAllMovies();

        verify(movieRepository, times(1)).getAllMovies();

        assertTrue(actualMovies.isEmpty(), "The getAllMovies method did not return an empty list when an exception occurred.");
    }

    @Test
    void getMovieById_shouldReturnMovie_whenValidId() {
        Movie expectedMovie = new Movie();

        when(movieRepository.getMovieById(anyInt())).thenReturn(expectedMovie);

        Movie actualMovie = movieService.getMovieById(1);

        verify(movieRepository, times(1)).getMovieById(1);

        assertEquals(expectedMovie, actualMovie, "The getMovieById method did not return the expected movie.");
    }

    @Test
    void getMovieById_shouldReturnNull_whenInvalidId() {
        when(movieRepository.getMovieById(anyInt())).thenThrow(new DataAccessException("Failed to fetch movie") {});

        Movie actualMovie = movieService.getMovieById(1);

        verify(movieRepository, times(1)).getMovieById(1);

        assertNull(actualMovie, "The getMovieById method did not return null when an exception occurred.");
    }

    @Test
    void getMovieByTitle_shouldReturnMovie_whenValidTitle() {
        String title = "The Shawshank Redemption";
        Movie expectedMovie = new Movie(1,title, "ABCD", 142,
            OffsetDateTime.now(), "US", 13, Genre.ACTION, new byte[] {1,2,3});

        when(movieRepository.getMovieByTitle(title)).thenReturn(expectedMovie);

        Movie actualMovie = movieService.getMovieByTitle(title);

        assertEquals(expectedMovie, actualMovie, "The returned movie does not match the expected movie.");
        verify(movieRepository, times(1)).getMovieByTitle(title);
    }

    @Test
    void getMovieByTitle_shouldReturnNull_whenNullTitle() {
        String title = null;

        assertNull(movieService.getMovieByTitle(title));
        verify(movieRepository, never()).getMovieByTitle(title);
    }

    @Test
    void addMovie_shouldReturnAddedMovie_whenValidMovie() {
        Movie movie = new Movie(1,"ABCD", "ABCD", 142,
            OffsetDateTime.now(), "US", 13, Genre.ACTION, new byte[] {1,2,3});
        int generatedId = 1;

        doNothing().when(movieValidator).validate(movie);
        when(movieRepository.getMovieByTitle(movie.getTitle())).thenReturn(new Movie());
        when(movieRepository.addMovie(movie)).thenReturn(generatedId);
        when(movieRepository.getMovieById(generatedId)).thenReturn(movie);

        Movie addedMovie = movieService.addMovie(movie);

        assertNotNull(addedMovie, "The added movie should not be null.");
        assertEquals(generatedId, addedMovie.getId(), "The added movie's ID does not match the generated ID.");
        verify(movieRepository, times(1)).getMovieByTitle(movie.getTitle());
        verify(movieRepository, times(1)).addMovie(movie);
    }

    @Test
    void addMovie_shouldReturnNull_whenDuplicatedMovie() {
        Movie movie = new Movie(1,"ABCD", "ABCD", 142,
            OffsetDateTime.now(), "US", 13, Genre.ACTION, new byte[] {1,2,3});

        when(movieRepository.getMovieByTitle(movie.getTitle())).thenReturn(movie);

        assertNull(movieService.addMovie(movie));
        verify(movieRepository, times(1)).getMovieByTitle(movie.getTitle());
        verify(movieRepository, never()).addMovie(movie);
    }

    @Test
    void addMovie_shouldReturnNull_whenUnableToRetrieveKey() {
        Movie movie = new Movie(1,"ABCD", "ABCD", 142,
            OffsetDateTime.now(), "US", 13, Genre.ACTION, new byte[] {1,2,3});

        when(movieRepository.getMovieByTitle(movie.getTitle())).thenReturn(null);
        when(movieRepository.addMovie(movie)).thenReturn(-1);

        assertNull(movieService.addMovie(movie));
        verify(movieRepository, times(1)).getMovieByTitle(movie.getTitle());
        verify(movieRepository, times(1)).addMovie(movie);
    }

    @Test
    void updateMovieById_shouldReturnUpdatedMovie_whenValidMovieId() {
        int movieId = 1;
        Movie movieToUpdate = new Movie(1,"ABCD", "ABCD", 142,
            OffsetDateTime.now(), "US", 13, Genre.ACTION, new byte[] {1,2,3});

        when(movieRepository.getMovieById(movieId)).thenReturn(movieToUpdate);
        doNothing().when(movieValidator).validate(movieToUpdate);
        doNothing().when(movieRepository).updateMovieById(movieToUpdate, movieId);

        Movie result = movieService.updateMovieById(movieToUpdate, movieId);

        assertNotNull(result);
        assertEquals(movieToUpdate, result);
    }

    @Test
    void updateMovieById_shouldReturnNull_whenInvalidMovieId() {
        int movieId = 1;
        Movie movieToUpdate = new Movie(1,"ABCD", "ABCD", 142,
            OffsetDateTime.now(), "US", 13, Genre.ACTION, new byte[] {1,2,3});

        when(movieRepository.getMovieById(movieId)).thenReturn(null);

        Movie result = movieService.updateMovieById(movieToUpdate, movieId);

        assertNull(result);
    }

    @Test
    void updateMovieById_shouldReturnNull_whenInvalidMovie() {
        int movieId = 1;
        Movie movieToUpdate = new Movie(1,"ABCD", "ABCD", 142,
            OffsetDateTime.now(), "US", 13, Genre.ACTION, new byte[] {1,2,3});

        when(movieRepository.getMovieById(movieId)).thenReturn(movieToUpdate);
        doThrow(MovieValidationException.class).when(movieValidator).validate(movieToUpdate);

        Movie result = movieService.updateMovieById(movieToUpdate, movieId);

        assertNull(result);
    }

    @Test
    void deleteMovieById_shouldReturnTrue_whenValidMovieId() {
        int movieId = 1;
        Movie movieToDelete = new Movie(movieId,"ABCD", "ABCD", 142,
            OffsetDateTime.now(), "US", 13, Genre.ACTION, new byte[] {1,2,3});

        when(movieRepository.getMovieById(movieId)).thenReturn(movieToDelete);

        boolean result = movieService.deleteMovieById(movieId);

        assertTrue(result);
    }

    @Test
    void deleteMovieById_shouldReturnFalse_whenInvalidMovieId() {
        int movieId = 1;

        when(movieRepository.getMovieById(movieId)).thenReturn(null);

        boolean result = movieService.deleteMovieById(movieId);

        assertFalse(result);
    }

    @Test
    void getMoviesWithPagination_shouldReturnMovies_whenValidInputs() {
        List<Movie> expectedMovies = List.of(new Movie(), new Movie());

        when(movieRepository.getMoviesWithPagination(1, 10)).thenReturn(expectedMovies);

        List<Movie> actualMovies = movieService.getMoviesWithPagination(1, 10);

        verify(movieRepository).getMoviesWithPagination(1, 10);

        assertEquals(expectedMovies, actualMovies, "The list of movies returned by getMoviesWithPagination was incorrect.");
    }

    @Test
    void getMoviesWithPagination_shouldReturnEmptyList_whenInvalidInputs() {
        assertTrue(movieService.getMoviesWithPagination(-1, 10).isEmpty());
        assertTrue(movieService.getMoviesWithPagination(1, -10).isEmpty());

        verify(movieRepository, never()).getMoviesWithPagination(anyInt(), anyInt());
    }

    @Test
    void getMoviesWithPagination_shouldReturnEmptyList_whenDataAccessException() {
        when(movieRepository.getMoviesWithPagination(anyInt(), anyInt())).thenThrow(new DataAccessException("Database connection error") {});

        List<Movie> actualMovies = movieService.getMoviesWithPagination(1, 10);

        verify(movieRepository).getMoviesWithPagination(1, 10);

        assertEquals(Collections.emptyList(), actualMovies, "The list of movies returned by getMoviesWithPagination was not empty.");
    }

    @Test
    void getMoviesRowsCount_shouldReturnCount_whenNoException() {
        when(movieRepository.getMoviesRowsCount()).thenReturn(100);

        int actualCount = movieService.getMoviesRowsCount();

        verify(movieRepository).getMoviesRowsCount();

        assertEquals(100, actualCount, "The count returned by getMoviesRowsCount was incorrect.");
    }

    @Test
    void getMoviesRowsCount_shouldReturnNegativeOne_whenDataAccessException() {
        when(movieRepository.getMoviesRowsCount()).thenThrow(new DataAccessException("Database connection error") {});

        int actualCount = movieService.getMoviesRowsCount();

        verify(movieRepository).getMoviesRowsCount();

        assertEquals(-1, actualCount, "The count returned by getMoviesRowsCount was not -1.");
    }

    @Test
    void setMovieForUpdateWithEmptyFile_shouldSetOldImage_whenNoException() {
        Movie movie = new Movie();
        movie.setId(1);
        Movie oldMovie = new Movie();
        oldMovie.setImage(new byte[]{1, 2, 3});

        when(movieRepository.getMovieById(1)).thenReturn(oldMovie);

        boolean result = movieService.setMovieForUpdate(movie, new MultipartFile() {
            @Override
            public String getName() {
                return "";
            }

            @Override
            public String getOriginalFilename() {
                return "";
            }

            @Override
            public String getContentType() {
                return "";
            }

            @Override
            public boolean isEmpty() {
                return true;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        });

        assertTrue(result, "setMovieForUpdate should return true");
        assertArrayEquals(oldMovie.getImage(), movie.getImage(), "The image should not be updated");
    }

    @Test
    void setMovieForUpdateWithEmptyFile_shouldSetNewImage_whenNoException() throws IOException {
        Movie movie = new Movie();
        movie.setId(1);
        Movie oldMovie = new Movie();
        oldMovie.setImage(new byte[]{1, 2, 3});

        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image".getBytes());

        when(movieRepository.getMovieById(1)).thenReturn(oldMovie);

        boolean result = movieService.setMovieForUpdate(movie, file);

        assertTrue(result, "setMovieForUpdate should return true");
        assertArrayEquals(file.getBytes(), movie.getImage(), "The image should be updated");
    }

    @Test
    void getByTitleIgnoreCaseWithPagination_shouldReturnAllMovies_whenNullKeyword() {
        int pageNumber = 1;
        int limit = 10;

        List<Movie> expectedMovies = Collections.singletonList(new Movie());

        when(movieRepository.getMoviesWithPagination(pageNumber, limit)).thenReturn(expectedMovies);

        List<Movie> movies = movieService.getByTitleIgnoreCaseWithPagination(null, pageNumber, limit);

        assertEquals(expectedMovies, movies, "The movies should be fetched without filtering by title");
    }

    @Test
    void getByTitleIgnoreCaseWithPagination_shouldReturnAllMovies_whenEmptyKeyword() {
        int pageNumber = 1;
        int limit = 10;

        List<Movie> expectedMovies = Collections.singletonList(new Movie());

        when(movieRepository.getMoviesWithPagination(pageNumber, limit)).thenReturn(expectedMovies);

        List<Movie> movies = movieService.getByTitleIgnoreCaseWithPagination("", pageNumber, limit);

        assertEquals(expectedMovies, movies, "The movies should be fetched without filtering by title");
    }

    @Test
    void getByTitleIgnoreCaseWithPagination_shouldReturnAllMoviesByKeyword_whenKeywordIsPresent() {
        int pageNumber = 1;
        int limit = 10;
        String keyword = "action";

        List<Movie> expectedMovies = Collections.singletonList(new Movie());

        when(movieRepository.getByTitleIgnoreCaseWithPagination(keyword, pageNumber, limit)).thenReturn(expectedMovies);

        List<Movie> movies = movieService.getByTitleIgnoreCaseWithPagination(keyword, pageNumber, limit);

        assertEquals(expectedMovies, movies, "The movies should be fetched by title");
    }

    @Test
    void getByTitleIgnoreCaseWithPagination_shouldReturnEmptyList_whenDataAccessException() {
        int pageNumber = 1;
        int limit = 10;
        String keyword = "action";

        when(movieRepository.getByTitleIgnoreCaseWithPagination(keyword, pageNumber, limit))
            .thenThrow(new DataAccessException("Database connection error") {});

        List<Movie> movies = movieService.getByTitleIgnoreCaseWithPagination(keyword, pageNumber, limit);

        assertEquals(Collections.emptyList(), movies, "An empty list should be returned");
    }

    @Test
    void getMoviesByTitleRowsCount_shouldReturnCount_whenNoException() {
        String keyword = "action";
        int expectedCount = 10;

        when(movieRepository.getMoviesByTitleRowsCount(keyword)).thenReturn(expectedCount);

        int count = movieService.getMoviesByTitleRowsCount(keyword);

        assertEquals(expectedCount, count, "The count should be fetched successfully");
    }

    @Test
    void getMoviesByTitleRowsCount_shouldReturnNegativeOne_whenDataAccessException() {
        String keyword = "action";

        when(movieRepository.getMoviesByTitleRowsCount(keyword))
            .thenThrow(new DataAccessException("Database connection error") {});

        int count = movieService.getMoviesByTitleRowsCount(keyword);

        assertEquals(-1, count, "The count should be -1");
    }
}
