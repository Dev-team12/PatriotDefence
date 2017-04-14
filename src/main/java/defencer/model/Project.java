package defencer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OptimisticLockType;

import java.io.Serializable;
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
    @Column(name = "data_start", nullable = false)
    private String dataFrom;
    @Column(name = "data_finish", nullable = false)
    private String dataTo;
    @Column(name = "place", nullable = false)
    private String place;
    @Column(name = "data_of_creation", nullable = false)
    private String dataOfCreation;
    @Column(name = "car", nullable = false)
    private String car;
    @Column(name = "instructor_id", nullable = false)
    private int instructors;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "description", nullable = false)
    private String description;
}
