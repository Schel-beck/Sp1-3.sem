package app.persistence;

import app.config.HibernateConfig;
import app.config.HibernateTestConfig;
import app.entities.Director;
import app.testUtils.Populator;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DirectorDAOTest {

    private final EntityManagerFactory emf =
        HibernateTestConfig.getEntityManagerFactory();

    private DirectorDAO directorDAO;
    private Map<String, Director> seeded;

    @BeforeEach
    void beforeEach() {
        seeded = Populator.directorPopulate(emf);
        directorDAO = new DirectorDAO(emf);
    }

    @AfterAll
    void shutdown() {
        emf.close();
    }

    @Test
    void create() {

        Director director = new Director("Christopher", "Director", "Directing");

        Director created = directorDAO.create(director);

        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("Christopher", created.getName());
    }

    @Test
    void read() {

        Director director = new Director("Christopher", "Director", "Directing");
        director = directorDAO.create(director);

        Director result = directorDAO.read(director.getId());

        assertNotNull(result);
        assertEquals(director.getId(), result.getId());
        assertEquals("Christopher", result.getName());
    }

    @Test
    void readAll() {

        Director director1 = new Director("Christopher", "Director", "Directing");
        Director director2 = new Director("Steven", "Director", "Directing");

        directorDAO.create(director1);
        directorDAO.create(director2);

        var directors = directorDAO.readAll();

        assertNotNull(directors);
        assertTrue(directors.size() >= 2);
    }

    @Test
    void update() {

        Director director = new Director("Christopher", "Director", "Directing");
        director = directorDAO.create(director);

        director.setName("Christopher Nolan Updated");

        Director updated = directorDAO.update(director);

        assertNotNull(updated);
        assertEquals(
            "Christopher Nolan Updated",
            updated.getName()
        );

        Director result = directorDAO.read(director.getId());

        assertEquals(
            "Christopher Nolan Updated",
            result.getName()
        );
    }

    @Test
    void delete() {

        Director director = new Director("Director To Delete", "Director", "Directing");
        director = directorDAO.create(director);

        int id = director.getId();

        directorDAO.delete(id);

        Director result = directorDAO.read(id);

        assertNull(result);
    }

    @Test
    void findByName() {

        Director result =
            directorDAO.findByName("Christopher Nolan");

        assertNotNull(result);
        //christopher nolan har id 2 i populator klassen
        assertEquals(2, result.getId());
        assertEquals("Christopher Nolan", result.getName());
    }
}