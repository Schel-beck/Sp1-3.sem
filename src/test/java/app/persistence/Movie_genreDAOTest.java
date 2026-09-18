
package app.persistence;

import app.config.HibernateTestConfig;
import app.entities.Movie_genre;
import app.testUtils.Populator;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Movie_genreDAOTest {

    private final EntityManagerFactory emf =
        HibernateTestConfig.getEntityManagerFactory();

    private Movie_genreDAO movieGenreDAO;
    private Map<String, Movie_genre> seeded;

    @BeforeEach
    void beforeEach() {
        seeded = Populator.movieGenrePopulate(emf);
        movieGenreDAO = new Movie_genreDAO(emf);
    }

    @AfterAll
    void shutdown() {
        emf.close();
    }

    @Test
    void create() {
        Movie_genre existing = seeded.get("movieGenre1");

        Movie_genre movieGenre =
            new Movie_genre(
                existing.getMovie(),
                existing.getGenre()
            );

        Movie_genre created =
            movieGenreDAO.create(movieGenre);

        assertNotNull(created);
        assertNotNull(created.getId());
        assertNotNull(created.getMovie());
        assertNotNull(created.getGenre());
    }

    @Test
    void read() {
        Movie_genre movieGenre =
            seeded.get("movieGenre1");

        Movie_genre result =
            movieGenreDAO.read(movieGenre.getId());

        assertNotNull(result);
        assertEquals(
            movieGenre.getId(),
            result.getId()
        );
        assertEquals(
            movieGenre.getMovie().getId(),
            result.getMovie().getId()
        );
        assertEquals(
            movieGenre.getGenre().getId(),
            result.getGenre().getId()
        );
    }

    @Test
    void readAll() {
        var movieGenres =
            movieGenreDAO.readAll();

        assertNotNull(movieGenres);
        assertTrue(movieGenres.size() >= 4);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
        Movie_genre movieGenre =
            seeded.get("movieGenre1");

        int id = movieGenre.getId();

        movieGenreDAO.delete(id);

        Movie_genre result =
            movieGenreDAO.read(id);

        assertNull(result);
    }
}

