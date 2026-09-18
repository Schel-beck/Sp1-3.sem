
package app.persistence;

import app.config.HibernateTestConfig;
import app.entities.Movie;
import app.testUtils.Populator;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MovieDAOTest {

    private final EntityManagerFactory emf =
        HibernateTestConfig.getEntityManagerFactory();

    private MovieDAO movieDAO;
    private Map<String, Movie> seeded;

    @BeforeEach
    void beforeEach() {
        seeded = Populator.moviePopulate(emf);
        movieDAO = new MovieDAO(emf);
    }

    @AfterAll
    void shutdown() {
        emf.close();
    }

    @Test
    void create() {

        Movie movie = new Movie(
            "en",
            "Interstellar",
            85.5,
            "2014-11-07",
            30000,
            8.6
        );

        Movie created = movieDAO.create(movie);

        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("Interstellar", created.getTitle());
    }

    @Test
    void read() {

        Movie movie = seeded.get("movie1");

        Movie result = movieDAO.read(movie.getId());

        assertNotNull(result);
        assertEquals(movie.getId(), result.getId());
        assertEquals("The Matrix", result.getTitle());
    }

    @Test
    void readAll() {

        List<Movie> movies = movieDAO.readAll();

        assertNotNull(movies);
        assertTrue(movies.size() >= 3);
    }

    @Test
    void update() {

        Movie movie = seeded.get("movie1");

        movie.setTitle("The Matrix Updated");

        Movie updated = movieDAO.update(movie);

        assertNotNull(updated);
        assertEquals(
            "The Matrix Updated",
            updated.getTitle()
        );

        Movie result = movieDAO.read(movie.getId());

        assertEquals(
            "The Matrix Updated",
            result.getTitle()
        );
    }

    @Test
    void delete() {

        Movie movie = seeded.get("movie1");

        int id = movie.getId();

        movieDAO.delete(id);

        Movie result = movieDAO.read(id);

        assertNull(result);
    }

    @Test
    void findTotalAverageRatingOfAllMovies() {

        double average =
            movieDAO.findTotalAverageRatingOfAllMovies();

        // Movie ratings from Populator:
        // 8.7, 9.0, 8.8
        // Average = 8.833...

        assertEquals(
            8.833,
            average,
            0.01
        );
    }

    @Test
    void findTopTenHighestRatedMovies() {

        ArrayList<Movie> movies =
            movieDAO.findTopTenHighestRatedMovies();

        assertNotNull(movies);
        assertTrue(movies.size() <= 10);

        // The first movie should have the highest rating
        assertEquals(
            "The Dark Knight",
            movies.get(0).getTitle()
        );
    }

    @Test
    void findTopTenLowestRatedMovies() {

        ArrayList<Movie> movies =
            movieDAO.findTopTenLowestRatedMovies();

        assertNotNull(movies);
        assertTrue(movies.size() <= 10);

        // The first movie should have the lowest rating
        assertEquals(
            "The Matrix",
            movies.get(0).getTitle()
        );
    }
    @Test
    void findByTitle() {
        ArrayList<Movie> movies =
            movieDAO.findByTitle("matrix");

        assertNotNull(movies);
        assertFalse(movies.isEmpty());
        assertEquals("The Matrix", movies.get(0).getTitle());
    }

}
