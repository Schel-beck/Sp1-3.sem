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
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id", nullable = false)
    private Integer id;
    @Column( unique = true, name = "name", length = 100)
    @Setter
    private String name;
    @OneToMany(mappedBy = "actor", fetch = FetchType.EAGER)
    //kig fetch type og cascade type
    private Set<Movie_actor> movieActors = new HashSet<>();

    public void addUser_question(Movie_actor movieActor) {
        this.movieActors.add(movieActor);
        if (movieActor != null) {
            movieActor.setActor(this);
        }
    }
    public Actor(String name) {
        this.name = name;
    }
    /*
    putter den i bindeledet mellem movie og actor pga M - M relation
    private String character;
     */

}
