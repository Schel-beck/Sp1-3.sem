
package app.persistence;

import app.config.HibernateTestConfig;
import app.entities.Movie;
import app.entities.Movie_actor;
import app.testUtils.Populator;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Movie_actorDAOTest {

    private final EntityManagerFactory emf =
        HibernateTestConfig.getEntityManagerFactory();

    private Movie_actorDAO movieActorDAO;
    private Map<String, Movie_actor> seeded;

    @BeforeEach
    void beforeEach() {
        seeded = Populator.movieActorPopulate(emf);
        movieActorDAO = new Movie_actorDAO(emf);
    }

    @AfterAll
    void shutdown() {
        emf.close();
    }

    @Test
    void create() {

        Map<String, Movie_actor> actors =
            Populator.movieActorPopulate(emf);

        Movie_actor existing = actors.get("movieActor1");

        Movie_actor movieActor = new Movie_actor(
            "Trinity",
            existing.getMovie(),
            existing.getActor()
        );

        Movie_actor created =
            movieActorDAO.create(movieActor);

        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("Trinity", created.getCharacter());
    }

    @Test
    void read() {

        Movie_actor movieActor =
            seeded.get("movieActor1");

        Movie_actor result =
            movieActorDAO.read(movieActor.getId());

        assertNotNull(result);
        assertEquals(
            movieActor.getId(),
            result.getId()
        );
        assertEquals(
            "Neo",
            result.getCharacter()
        );
    }

    @Test
    void readAll() {

        var movieActors =
            movieActorDAO.readAll();

        assertNotNull(movieActors);

        assertTrue(movieActors.size() >= 2);
    }

    @Test
    void update() {

        Movie_actor movieActor =
            seeded.get("movieActor1");

        movieActor.setCharacter("Neo Updated");

        Movie_actor updated =
            movieActorDAO.update(movieActor);

        assertNotNull(updated);

        assertEquals(
            "Neo Updated",
            updated.getCharacter()
        );

        Movie_actor result =
            movieActorDAO.read(movieActor.getId());

        assertEquals(
            "Neo Updated",
            result.getCharacter()
        );
    }

    @Test
    void delete() {

        Movie_actor movieActor =
            seeded.get("movieActor1");

        int id = movieActor.getId();

        movieActorDAO.delete(id);

        Movie_actor result =
            movieActorDAO.read(id);

        assertNull(result);
    }
    @Test
    void findMoviesByActor() {
        Movie_actor movieActor = seeded.get("movieActor1");

        ArrayList<Movie> movies =
            movieActorDAO.findMoviesByActor(
                movieActor.getActor().getId()
            );

        assertNotNull(movies);
        assertFalse(movies.isEmpty());
        assertEquals("The Matrix", movies.get(0).getTitle());
    }

}


