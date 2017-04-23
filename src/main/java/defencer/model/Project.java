package defencer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OptimisticLockType;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * @author Igor Gnes on 3/30/17.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.DIRTY, dynamicUpdate = true)
@Table(name = "project")
public class Project extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "date_start", nullable = false)
    private LocalDate dateStart;
    @Column(name = "date_finish", nullable = false)
    private LocalDate dateFinish;
    @Column(name = "place", nullable = false)
    private String place;
    @Column(name = "date_of_creation", nullable = false)
    private LocalDate dateOfCreation;
    @Column(name = "car", nullable = false)
    private String car;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "description", nullable = false)
    private String description;

    public Project(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Project(Long id, String name, LocalDate dateStart, LocalDate dateFinish,
                   String place, String author, String car, String description) {
        this.id = id;
        this.name = name;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.place = place;
        this.author = author;
        this.car = car;
        this.description = description;
    }
}

