package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Movie_genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_genre_id", nullable = false)
    private int id;

    @Setter
    @ManyToOne
    @ToString.Exclude
    private Movie movie;

    @Setter
    @ManyToOne
    @ToString.Exclude
    private Genre genre;

    public Movie_genre(Movie movie, Genre genre) {
        this.movie = movie;
        this.genre = genre;
    }
}
