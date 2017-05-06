package defencer.model;

import lombok.*;
import org.hibernate.annotations.OptimisticLockType;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @author Igor Gnes on 4/17/17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.DIRTY, dynamicUpdate = true)
@Table(name = "type_of_project")
public class AvailableProject extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String projectName;
}
