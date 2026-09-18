
package app.persistence;

import app.config.HibernateTestConfig;
import app.entities.Genre;
import app.testUtils.Populator;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GenreDAOTest {

    private final EntityManagerFactory emf =
        HibernateTestConfig.getEntityManagerFactory();

    private GenreDAO genreDAO;
    private Map<String, Genre> seeded;

    @BeforeEach
    void beforeEach() {
        seeded = Populator.genrePopulate(emf);
        genreDAO = new GenreDAO(emf);
    }

    @AfterAll
    void shutdown() {
        emf.close();
    }

    @Test
    void create() {

        Genre genre = new Genre("Comedy");

        Genre created = genreDAO.create(genre);

        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("Comedy", created.getName());
    }

    @Test
    void read() {

        Genre genre = seeded.get("genre1");

        Genre result = genreDAO.read(genre.getId());

        assertNotNull(result);
        assertEquals(genre.getId(), result.getId());
        assertEquals("Action", result.getName());
    }

    @Test
    void readAll() {

        Genre genre1 = new Genre("Comedy");
        Genre genre2 = new Genre("Horror");

        genreDAO.create(genre1);
        genreDAO.create(genre2);

        var genres = genreDAO.readAll();

        assertNotNull(genres);
        assertTrue(genres.size() >= 5);
    }

    @Test
    void update() {

        Genre genre = seeded.get("genre1");

        genre.setName("Action Updated");

        Genre updated = genreDAO.update(genre);

        assertNotNull(updated);
        assertEquals(
            "Action Updated",
            updated.getName()
        );

        Genre result = genreDAO.read(genre.getId());

        assertEquals(
            "Action Updated",
            result.getName()
        );
    }

    @Test
    void delete() {

        Genre genre = seeded.get("genre1");

        int id = genre.getId();

        genreDAO.delete(id);

        Genre result = genreDAO.read(id);

        assertNull(result);
    }

    @Test
    void findByName() {

        Genre result =
            genreDAO.findByName("Action");

        assertNotNull(result);

        // Action has ID 1 in Populator
        assertEquals(1, result.getId());

        assertEquals(
            "Action",
            result.getName()
        );
    }
}
