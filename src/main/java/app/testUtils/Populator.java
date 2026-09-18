package app.testUtils;

import app.entities.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

import java.util.*;

public class Populator {

    public static Map<String, Movie> moviePopulate(EntityManagerFactory emf) {

        try (EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();

            try {
                em.createNativeQuery(
                        "TRUNCATE TABLE movie RESTART IDENTITY CASCADE"
                ).executeUpdate();

                Movie movie1 = new Movie(
                        "en",
                        "The Matrix",
                        80.5,
                        "1999-03-31",
                        25000,
                        8.7
                );

                Movie movie2 = new Movie(
                        "en",
                        "The Dark Knight",
                        90.2,
                        "2008-07-18",
                        30000,
                        9.0
                );

                Movie movie3 = new Movie(
                        "en",
                        "Inception",
                        85.7,
                        "2010-07-16",
                        28000,
                        8.8
                );

                em.persist(movie1);
                em.persist(movie2);
                em.persist(movie3);

                em.flush();

                Map<String, Movie> seeded = new LinkedHashMap<>();
                seeded.put("movie1", movie1);
                seeded.put("movie2", movie2);
                seeded.put("movie3", movie3);

                em.getTransaction().commit();

                return seeded;

            } catch (PersistenceException e) {

                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }

                throw e;
            }
        }
    }


    public static Map<String, Actor> actorPopulate(EntityManagerFactory emf) {

        try (EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();

            try {
                em.createNativeQuery(
                        "TRUNCATE TABLE actor RESTART IDENTITY CASCADE"
                ).executeUpdate();

                Actor actor1 = new Actor("Keanu Reeves");
                Actor actor2 = new Actor("Christian Bale");
                Actor actor3 = new Actor("Leonardo DiCaprio");

                em.persist(actor1);
                em.persist(actor2);
                em.persist(actor3);

                em.flush();

                Map<String, Actor> seeded = new LinkedHashMap<>();
                seeded.put("actor1", actor1);
                seeded.put("actor2", actor2);
                seeded.put("actor3", actor3);

                em.getTransaction().commit();

                return seeded;

            } catch (PersistenceException e) {

                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }

                throw e;
            }
        }
    }


    public static Map<String, Director> directorPopulate(EntityManagerFactory emf) {

        try (EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();

            try {
                em.createNativeQuery(
                        "TRUNCATE TABLE director RESTART IDENTITY CASCADE"
                ).executeUpdate();

                Director director1 = new Director("Lana Wachowski", "Director", "Directing");
                Director director2 = new Director("Christopher Nolan", "Director","Directing");

                em.persist(director1);
                em.persist(director2);

                em.flush();

                Map<String, Director> seeded = new LinkedHashMap<>();
                seeded.put("director1", director1);
                seeded.put("director2", director2);

                em.getTransaction().commit();

                return seeded;

            } catch (PersistenceException e) {

                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }

                throw e;
            }
        }
    }


    public static Map<String, Genre> genrePopulate(EntityManagerFactory emf) {

        try (EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();

            try {
                em.createNativeQuery(
                        "TRUNCATE TABLE genre RESTART IDENTITY CASCADE"
                ).executeUpdate();

                Genre genre1 = new Genre("Action");
                Genre genre2 = new Genre("Drama");
                Genre genre3 = new Genre("Science Fiction");

                em.persist(genre1);
                em.persist(genre2);
                em.persist(genre3);

                em.flush();

                Map<String, Genre> seeded = new LinkedHashMap<>();
                seeded.put("genre1", genre1);
                seeded.put("genre2", genre2);
                seeded.put("genre3", genre3);

                em.getTransaction().commit();

                return seeded;

            } catch (PersistenceException e) {

                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }

                throw e;
            }
        }
    }

    public static Map<String, Movie_actor> movieActorPopulate(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.createNativeQuery(
                    "TRUNCATE TABLE Movie_actor, Movie, Actor " +
                            "RESTART IDENTITY CASCADE"
            ).executeUpdate();

            Actor actor1 = new Actor("Keanu Reeves");
            Actor actor2 = new Actor("Christian Bale");

            Movie movie1 = new Movie(
                    "en",
                    "The Matrix",
                    80.0,
                    "1999-03-31",
                    20000,
                    8.7
            );

            Movie movie2 = new Movie(
                    "en",
                    "The Dark Knight",
                    90.0,
                    "2008-07-18",
                    30000,
                    9.0
            );

            em.persist(actor1);
            em.persist(actor2);

            em.persist(movie1);
            em.persist(movie2);

            Movie_actor movieActor1 =
                    new Movie_actor("Neo", movie1, actor1);

            Movie_actor movieActor2 =
                    new Movie_actor("Bruce Wayne", movie2, actor2);

            em.persist(movieActor1);
            em.persist(movieActor2);

            em.flush();
            em.getTransaction().commit();

            Map<String, Movie_actor> map = new LinkedHashMap<>();
            map.put("movieActor1", movieActor1);
            map.put("movieActor2", movieActor2);

            return map;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }



    public static Map<String, Movie_genre> movieGenrePopulate(
            EntityManagerFactory emf) {

        try (EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();

            try {
                em.createNativeQuery(
                        "TRUNCATE TABLE movie_genre, movie, genre RESTART IDENTITY CASCADE"
                ).executeUpdate();

                Movie movie1 = new Movie(
                        "en",
                        "The Matrix",
                        80.5,
                        "1999-03-31",
                        25000,
                        8.7
                );

                Movie movie2 = new Movie(
                        "en",
                        "The Dark Knight",
                        90.2,
                        "2008-07-18",
                        30000,
                        9.0
                );

                Genre genre1 = new Genre("Action");
                Genre genre2 = new Genre("Science Fiction");
                Genre genre3 = new Genre("Drama");

                em.persist(movie1);
                em.persist(movie2);

                em.persist(genre1);
                em.persist(genre2);
                em.persist(genre3);

                em.flush();

                Movie_genre movieGenre1 =
                        new Movie_genre(movie1, genre1);

                Movie_genre movieGenre2 =
                        new Movie_genre(movie1, genre2);

                Movie_genre movieGenre3 =
                        new Movie_genre(movie2, genre1);

                Movie_genre movieGenre4 =
                        new Movie_genre(movie2, genre3);

                em.persist(movieGenre1);
                em.persist(movieGenre2);
                em.persist(movieGenre3);
                em.persist(movieGenre4);

                em.flush();

                Map<String, Movie_genre> seeded = new LinkedHashMap<>();
                seeded.put("movieGenre1", movieGenre1);
                seeded.put("movieGenre2", movieGenre2);
                seeded.put("movieGenre3", movieGenre3);
                seeded.put("movieGenre4", movieGenre4);

                em.getTransaction().commit();

                return seeded;

            } catch (PersistenceException e) {

                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }

                throw e;
            }
        }
    }
}

