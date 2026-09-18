
package app;

import app.config.HibernateConfig;
import app.entities.*;
import app.persistence.*;
import app.service.*;

import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                HibernateConfig.getEntityManagerFactory();

        ApiReader apiReader = new ApiReader();

        MovieDAO movieDAO = new MovieDAO(emf);
        DirectorDAO directorDAO = new DirectorDAO(emf);
        Movie_actorDAO movieActorDAO = new Movie_actorDAO(emf);
        ActorDAO actorDAO = new ActorDAO(emf);
        GenreDAO genreDAO = new GenreDAO(emf);
        Movie_genreDAO movieGenreDAO = new Movie_genreDAO(emf);

        String genreURl = apiReader.createGenreUrl();
        String genreJson = apiReader.readAPI(genreURl);
        GenreResponseDTO genreResponseDTO =
                apiReader.convertFromJson(genreJson,GenreResponseDTO.class);

        int page = 1;
        int totalPages = 1;

        while (page <= 2) {

            String movieUrl =
                    apiReader.createUrlForMovie(page);

            String movieJson =
                    apiReader.readAPI(movieUrl);

            MovieResponseDTO movieResponse =
                    apiReader.convertFromJson(
                            movieJson,
                            MovieResponseDTO.class
                    );

            totalPages = movieResponse.getTotalPages();

            System.out.println(
                    "Page " + page + " of " + totalPages
            );

            for (MovieDTO movieDTO : movieResponse.getResults()) {

                Movie movie = new Movie(
                        movieDTO.getOriginalLanguage(),
                        movieDTO.getOriginalTitle(),
                        movieDTO.getPopularity(),
                        movieDTO.getReleaseDate(),
                        movieDTO.getVoteCount(),
                        movieDTO.getVoteAverage()
                );

                String creditsUrl =
                        apiReader.createCreditsUrl(
                                movieDTO.getId()
                        );

                String creditsJson =
                        apiReader.readAPI(creditsUrl);

                CreditsResponseDTO creditsResponse =
                        apiReader.convertFromJson(
                                creditsJson,
                                CreditsResponseDTO.class
                        );

                for (CrewDTO crewDTO :
                        creditsResponse.getCrew()) {

                    if ("Director".equals(crewDTO.getJob())) {

                        String directorName =
                                crewDTO.getOriginalName();

                        Director director =
                                directorDAO.findByName(directorName);

                        if (director == null) {

                            director = new Director(
                                    directorName,
                                    crewDTO.getJob(),
                                    crewDTO.getDepartment());

                            directorDAO.create(director);
                        }
                        movie.setDirector(director);
                    }
                }

                movie = movieDAO.create(movie);

                for (CastDTO castDTO : creditsResponse.getCast()) {

                    Actor actor = actorDAO.findByName(castDTO.getOriginalName());

                    if (actor == null) {

                        actor = new Actor(castDTO.getOriginalName());
                        actor = actorDAO.create(actor);
                    }

                    Movie_actor movieActor =
                            new Movie_actor(
                                    castDTO.getCharacter(),
                                    movie,
                                    actor
                            );

                    movieActorDAO.create(movieActor);
                }

                for (int genreId : movieDTO.getGenreIds()) {

                    for (GenreDTO genreDTO : genreResponseDTO.getGenres()) {

                        if (genreDTO.getId() == genreId) {

                            Genre genre = genreDAO.findByName(genreDTO.getName());

                            if (genre == null) {
                                genre = new Genre(genreDTO.getName());
                                genre = genreDAO.create(genre);
                            }

                            Movie_genre movieGenre =
                                    new Movie_genre(movie, genre);

                            movieGenreDAO.create(movieGenre);

                            movie.addMovieGenre(movieGenre);
                        }
                    }
                }
            }

            page++;
        }

        System.out.println("Finished!");
    }
}



