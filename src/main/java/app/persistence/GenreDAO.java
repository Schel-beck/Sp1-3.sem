package app.persistence;

import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import app.service.GenreResponseDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
public class GenreDAO implements IDAO<Genre, Integer>{
    private EntityManagerFactory emf;
    @Override
    public Genre create(Genre obj) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
        em.close();
        return obj;
    }

    @Override
    public Genre read(Integer id) {
        EntityManager em = emf.createEntityManager();
        Genre obj = em.find(Genre.class, id);
        em.close();
        return obj;
    }

    @Override
    public List<Genre> readAll() {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Genre> query = em.createQuery("SELECT p FROM Genre p",Genre.class);
        List<Genre> results = query.getResultList();

        em.close();
        return  results;
    }

    @Override
    public Genre update(Genre object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Genre updatedObject = em.merge(object);
        em.getTransaction().commit();
        em.close();
        return updatedObject;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Genre obj = read(id);
        if (obj != null)
        {
            em.remove(obj);
        }
        em.getTransaction().commit();
        em.close();
    }
    public Genre findByName(String name) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery(
                            "SELECT d FROM Genre d WHERE d.name = :name",
                            Genre.class
                    )
                    .setParameter("name", name)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }
}
