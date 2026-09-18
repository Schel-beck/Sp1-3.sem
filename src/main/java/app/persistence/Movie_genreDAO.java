package app.persistence;

import app.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
public class Movie_genreDAO implements IDAO<Movie_genre,Integer>{

    private EntityManagerFactory emf;
    @Override
    public Movie_genre create(Movie_genre obj) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Genre genre = em.find(Genre.class, obj.getGenre().getId());
            Movie movie = em.find(Movie.class, obj.getMovie().getId());

            obj.setGenre(genre);
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
    public Movie_genre read(Integer id) {
        EntityManager em = emf.createEntityManager();
        Movie_genre obj = em.find(Movie_genre.class, id);
        em.close();
        return obj;
    }

    @Override
    public List<Movie_genre> readAll() {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Movie_genre> query = em.createQuery("SELECT p FROM Movie_genre p",Movie_genre.class);
        List<Movie_genre> results = query.getResultList();

        em.close();
        return  results;
    }

    @Override
    public Movie_genre update(Movie_genre object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Movie_genre updatedObject = em.merge(object);
        em.getTransaction().commit();
        em.close();
        return updatedObject;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Movie_genre obj = read(id);
        if (obj != null)
        {
            em.remove(obj);
        }
        em.getTransaction().commit();
        em.close();
    }
}
