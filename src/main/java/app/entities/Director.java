package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@ToString
@NoArgsConstructor
@Getter
@Entity
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_id", nullable = false)
    private Integer id;
    @Column(unique = true, name = "name", length = 100)
    @Setter
    private String name;
    @Column(name = "job", length = 100)
    private String job;
    @Column(name = "department")
    private String department;
    @OneToMany(mappedBy = "director", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //kig fetch type og cascade type
    private Set<Movie> Movies = new HashSet<>();
    public void addMovies(Movie movie) {
        this.Movies.add(movie);
        if (movie != null) {
            movie.setDirector(this);
        }
    }

    public Director(String name, String job, String department) {
        this.name = name;
        this.job = job;
        this.department = department;
    }
}
