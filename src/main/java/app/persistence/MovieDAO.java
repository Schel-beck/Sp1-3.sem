package app.persistence;

import app.entities.Director;
import app.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
public class MovieDAO implements IDAO<Movie, Integer>{
    private EntityManagerFactory emf;
    @Override
    public Movie create(Movie obj) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
        em.close();
        return obj;
    }

    @Override
    public Movie read(Integer id) {
        EntityManager em = emf.createEntityManager();
        Movie obj = em.find(Movie.class, id);
        em.close();
        return obj;
    }

    @Override
    public List<Movie> readAll() {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Movie> query = em.createQuery("SELECT p FROM Movie p",Movie.class);
        List<Movie> results = query.getResultList();

        em.close();
        return  results;
    }

    @Override
    public Movie update(Movie object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Movie updatedObject = em.merge(object);
        em.getTransaction().commit();
        em.close();
        return updatedObject;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Movie obj = read(id);
        if (obj != null)
        {
            em.remove(obj);
        }
        em.getTransaction().commit();
        em.close();
    }

    public double findTotalAverageRatingOfAllMovies() {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT AVG(m.voteAverage) FROM Movie m",
                    Double.class
            ).getSingleResult();

        } finally {
            em.close();
        }
    }
    public ArrayList<Movie> findTopTenHighestRatedMovies() {
        EntityManager em = emf.createEntityManager();

        try {
            return new ArrayList<>(
                    em.createQuery(
                                    "SELECT m FROM Movie m ORDER BY m.voteAverage DESC",
                                    Movie.class
                            )
                            .setMaxResults(10)
                            .getResultList()
            );

        } finally {
            em.close();
        }
    }
    public ArrayList<Movie> findTopTenLowestRatedMovies() {
        EntityManager em = emf.createEntityManager();

        try {
            return new ArrayList<>(
                    em.createQuery(
                                    "SELECT m FROM Movie m ORDER BY m.voteAverage ASC",
                                    Movie.class
                            )
                            .setMaxResults(10)
                            .getResultList()
            );

        } finally {
            em.close();
        }
    }
    public ArrayList<Movie> findByTitle(String title) {
        EntityManager em = emf.createEntityManager();

        try {
            return new ArrayList<>(
                    em.createQuery(
                                    "SELECT m FROM Movie m " +
                                            "WHERE LOWER(m.title) LIKE LOWER(:title)",
                                    Movie.class
                            )
                            .setParameter("title", "%" + title + "%")
                            .getResultList()
            );
        } finally {
            em.close();
        }
    }

}
