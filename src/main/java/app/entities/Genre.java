package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    @Setter
    private String name;
    @OneToMany(mappedBy = "genre", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //kig fetch type og cascade type
    private Set<Movie_genre> movieGenres = new HashSet<>();

    public void addMovieGenre(Movie_genre movieGenre) {
        this.movieGenres.add(movieGenre);
        if (movieGenre != null) {
            movieGenre.setGenre(this);
        }
    }
    public Genre(String name) {
        this.name = name;
    }
}