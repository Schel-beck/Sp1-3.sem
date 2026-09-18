package app.persistence;

import app.entities.Actor;
import app.entities.Movie;
import app.entities.Movie_actor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
public class Movie_actorDAO implements IDAO<Movie_actor, Integer>{
    private EntityManagerFactory emf;
    @Override
    public Movie_actor create(Movie_actor obj) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Actor actor = em.find(Actor.class, obj.getActor().getId());
            Movie movie = em.find(Movie.class, obj.getMovie().getId());

            obj.setActor(actor);
            obj.setMovie(movie);

            em.persist(obj);

            em.getTransaction().commit();

            return obj;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }

    @Override
    public Movie_actor read(Integer id) {
        EntityManager em = emf.createEntityManager();
        Movie_actor obj = em.find(Movie_actor.class, id);
        em.close();
        return obj;
    }

    @Override
    public List<Movie_actor> readAll() {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Movie_actor> query = em.createQuery("SELECT p FROM Movie_actor p",Movie_actor.class);
        List<Movie_actor> results = query.getResultList();

        em.close();
        return  results;
    }

    @Override
    public Movie_actor update(Movie_actor object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Movie_actor updatedObject = em.merge(object);
        em.getTransaction().commit();
        em.close();
        return updatedObject;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Movie_actor obj = read(id);
        if (obj != null)
        {
            em.remove(obj);
        }
        em.getTransaction().commit();
        em.close();
    }
    public ArrayList<Movie> findMoviesByActor(int actorId) {
        EntityManager em = emf.createEntityManager();

        try {
            return new ArrayList<>(
                    em.createQuery("SELECT ma.movie FROM Movie_actor ma " +
                                            "WHERE ma.actor.id = :actorId", Movie.class)
                            .setParameter("actorId", actorId)
                            .getResultList()
            );
        } finally {
            em.close();
        }
    }

}
