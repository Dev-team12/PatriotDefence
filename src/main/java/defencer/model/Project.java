package defencer.model;

import lombok.*;
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
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "instructors")
    private String instructors;
    @Column(name = "cars")
    private String cars;

    public Project(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getNameId() {
        return "# " + getId() + " " + getName();
    }
}

