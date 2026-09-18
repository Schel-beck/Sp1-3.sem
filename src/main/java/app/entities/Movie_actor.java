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
public class Movie_actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_actor_id", nullable = false)
    private int id;
    @Setter
    @Column(name = "character", length = 100)
    private String character;


    @Setter
    @ManyToOne
    @ToString.Exclude
    private Movie movie;

    @Setter
    @ManyToOne
    @ToString.Exclude
    private Actor actor;

    public Movie_actor(String character, Movie movie, Actor actor) {
        this.character = character;
        this.movie = movie;
        this.actor = actor;
    }
}
