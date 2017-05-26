package defencer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OptimisticLockType;

import java.io.Serializable;
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
@Table(name = "refusal")
public class Refusal extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "instructor_id", nullable = false)
    private Long instructorId;
    @Column(name = "project_id", nullable = false)
    private Long projectId;
    @Column(name = "instructor_name", nullable = false)
    private String instructorNames;
}
