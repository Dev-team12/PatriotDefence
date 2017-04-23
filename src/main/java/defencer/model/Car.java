package defencer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OptimisticLockType;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @author Igor Gnes on 4/16/17.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.DIRTY, dynamicUpdate = true)
@Table(name = "car")
public class Car extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String carName;
    @Column(name = "project_id")
    private Long projectId;
    @Column(name = "status")
    private String status;

    public Car(Long id, String carName, String status) {
        this.id = id;
        this.carName = carName;
        this.status = status;
    }
}

