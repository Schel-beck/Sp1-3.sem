package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.engine.internal.Cascade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@ToString
@NoArgsConstructor
@Getter
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id", nullable = false)
    private Integer id;
    @Column(name = "original_language", length = 100)
    private String originalLanguage;
    @Setter
    @Column(name = "title", length = 100)
    private String title;
    @Column(name = "popularity")
    private double popularity;
    @Column(name = "release_date")
    private String releaseDate;
    @Column(name = "vote_count")
    private int voteCount;
    @Column(name = "vote_average")
    private double voteAverage;

    @Setter
    @ManyToOne
    @ToString.Exclude
    private Director director;

    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //kig fetch type og cascade type
    private Set<Movie_actor> movieActors = new HashSet<>();

    public void addMovie_actor(Movie_actor movieActor) {
        this.movieActors.add(movieActor);
        if (movieActor != null) {
            movieActor.setMovie(this);
        }
    }
    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //kig fetch type og cascade type
    private Set<Movie_genre> movieGenres = new HashSet<>();

    public void addMovieGenre(Movie_genre movieGenre) {
        this.movieGenres.add(movieGenre);
        if (movieGenre != null) {
            movieGenre.setMovie(this);
        }
    }


    public Movie(String originalLanguage, String title, double popularity, String releaseDate,
                 int voteCount, double voteAverage) {
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
    }

}
