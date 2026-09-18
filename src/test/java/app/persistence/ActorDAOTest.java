package app.persistence;
import app.config.HibernateTestConfig;
import app.entities.Actor;
import app.testUtils.Populator;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ActorDAOTest {

    private final EntityManagerFactory emf =
        HibernateTestConfig.getEntityManagerFactory();

    private ActorDAO actorDAO;
    private Map<String, Actor> seeded;

    @BeforeEach
    void beforeEach() {
        seeded = Populator.actorPopulate(emf);
        actorDAO = new ActorDAO(emf);
    }

    @AfterAll
    void shutdown() {
        emf.close();
    }

    @Test
    void create() {

        Actor actor = new Actor("Tom Hanks");

        Actor created = actorDAO.create(actor);

        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("Tom Hanks", created.getName());
    }

    @Test
    void read() {

        Actor actor = seeded.get("actor1");

        Actor result = actorDAO.read(actor.getId());

        assertNotNull(result);
        assertEquals(actor.getId(), result.getId());
        assertEquals("Keanu Reeves", result.getName());
    }

    @Test
    void readAll() {

        Actor actor1 = new Actor("Tom Hanks");
        Actor actor2 = new Actor("Brad Pitt");

        actorDAO.create(actor1);
        actorDAO.create(actor2);

        var actors = actorDAO.readAll();

        assertNotNull(actors);
        assertTrue(actors.size() >= 5);
    }

    @Test
    void update() {

        Actor actor = seeded.get("actor1");

        actor.setName("Keanu Reeves Updated");

        Actor updated = actorDAO.update(actor);

        assertNotNull(updated);
        assertEquals(
            "Keanu Reeves Updated",
            updated.getName()
        );

        Actor result = actorDAO.read(actor.getId());

        assertEquals(
            "Keanu Reeves Updated",
            result.getName()
        );
    }

    @Test
    void delete() {

        Actor actor = seeded.get("actor1");

        int id = actor.getId();

        actorDAO.delete(id);

        Actor result = actorDAO.read(id);

        assertNull(result);
    }

    @Test
    void findByName() {

        Actor result =
            actorDAO.findByName("Keanu Reeves");

        assertNotNull(result);

        // Keanu Reeves has ID 1 in Populator
        assertEquals(1, result.getId());

        assertEquals(
            "Keanu Reeves",
            result.getName()
        );
    }
}
