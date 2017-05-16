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
 * @author Igor Hnes on 14.05.17.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.DIRTY, dynamicUpdate = true)
@Table(name = "expected")
public class Expected extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "instructor_id", nullable = false)
    private Long instructorId;
    @Column(name = "project_id", nullable = false)
    private Long projectId;
    @Column(name = "instructor_names", nullable = false)
    private String instructorNames;
    @Column(name = "start_date", nullable = false)
    private LocalDate projectStart;
    @Column(name = "finish_date", nullable = false)
    private LocalDate finishProject;
    @Column(name = "project_name", nullable = false)
    private String projectName;
    @Column(name = "status", nullable = false)
    private String status;

    public Expected(Long id, Long instructorId, LocalDate projectStart, LocalDate finishProject) {
        this.id = id;
        this.instructorId = instructorId;
        this.projectStart = projectStart;
        this.finishProject = finishProject;
    }
}
