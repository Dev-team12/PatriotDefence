package defencer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OptimisticLockType;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * @author Igor Gnes on 4/20/17.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.DIRTY, dynamicUpdate = true)
@Table(name = "project_times")
public class ProjectTimes extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "project_id", nullable = false)
    private Long projectId;
    @Column(name = "times")
    private Long projectTimes;
    @Column(name = "date_of_creation")
    private LocalDate dateOfCreation;

    public ProjectTimes(Long id, Long projectTimes) {
        this.id = id;
        this.projectTimes = projectTimes;
    }
}
