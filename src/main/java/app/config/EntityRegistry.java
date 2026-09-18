package app.config;
import app.entities.*;
import org.hibernate.cfg.Configuration;

final class EntityRegistry {

    private EntityRegistry() {}

    static void registerEntities(Configuration configuration) {
        configuration.addAnnotatedClass(Movie.class);
        configuration.addAnnotatedClass(Director.class);
        configuration.addAnnotatedClass(Actor.class);
        configuration.addAnnotatedClass(Movie_actor.class);
        configuration.addAnnotatedClass(Genre.class);
        configuration.addAnnotatedClass(Movie_genre.class);
        // TODO: Add more entities here...
    }
}