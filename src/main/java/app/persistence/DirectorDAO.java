package app.persistence;

import app.entities.Director;
import app.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDAO implements IDAO<Director, Integer>{
    private EntityManagerFactory emf;
    @Override
    public Director create(Director obj) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
        em.close();
        return obj;
    }

    @Override
    public Director read(Integer id) {
        EntityManager em = emf.createEntityManager();
        Director obj = em.find(Director.class, id);
        em.close();
        return obj;
    }

    @Override
    public List<Director> readAll() {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Director> query = em.createQuery("SELECT p FROM Director p",Director.class);
        List<Director> results = query.getResultList();

        em.close();
        return  results;
    }

    @Override
    public Director update(Director object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Director updatedObject = em.merge(object);
        em.getTransaction().commit();
        em.close();
        return updatedObject;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Director obj = read(id);
        if (obj != null)
        {
            em.remove(obj);
        }
        em.getTransaction().commit();
        em.close();
    }
    public Director findByName(String name) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery(
                            "SELECT d FROM Director d WHERE d.name = :name",
                            Director.class
                    )
                    .setParameter("name", name)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }
}
