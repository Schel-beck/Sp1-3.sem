package app.persistence;

import app.entities.Actor;
import app.entities.Director;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ActorDAO implements IDAO<Actor, Integer>{
    private EntityManagerFactory emf;
    @Override
    public Actor create(Actor obj) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Actor updatedObject = em.merge(obj);
        em.getTransaction().commit();
        em.close();
        return updatedObject;
    }

    @Override
    public Actor read(Integer id) {
        EntityManager em = emf.createEntityManager();
        Actor obj = em.find(Actor.class, id);
        em.close();
        return obj;
    }

    @Override
    public List<Actor> readAll() {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Actor> query = em.createQuery("SELECT p FROM Actor p", Actor.class);
        List<Actor> results = query.getResultList();

        em.close();
        return  results;
    }

    @Override
    public Actor update(Actor object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Actor updatedObject = em.merge(object);
        em.getTransaction().commit();
        em.close();
        return updatedObject;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Actor obj = read(id);
        if (obj != null)
        {
            em.remove(obj);
        }
        em.getTransaction().commit();
        em.close();
    }
    public Actor findByName(String name) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT d FROM Actor d WHERE d.name = :name", Actor.class)
                    .setParameter("name", name)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }
}
