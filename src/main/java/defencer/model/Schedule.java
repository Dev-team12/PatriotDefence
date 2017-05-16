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
 * @author Igor Hnes on 08.05.17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.DIRTY, dynamicUpdate = true)
@Table(name = "instructor_project")
public class Schedule extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "instructor_id")
    private Long instructorId;
    @Column(name = "project_id")
    private Long projectId;
    @Column(name = "start_date")
    private LocalDate startProject;
    @Column(name = "finish_date")
    private LocalDate finishProject;
    @Column(name = "project_name")
    private String projectName;
    @Column(name = "instructor_name")
    private String instructorName;
    @Column(name = "status")
    private String status;

    public Schedule(Long id, String instructorName) {
        this.id = id;
        this.instructorName = instructorName;
    }

    public Schedule(Long id, Long instructorId) {
        this.id = id;
        this.instructorId = instructorId;
    }

    public Schedule(Long id, Long instructorId, LocalDate startProject, LocalDate finishProject) {
        this.id = id;
        this.instructorId = instructorId;
        this.startProject = startProject;
        this.finishProject = finishProject;
    }
}
