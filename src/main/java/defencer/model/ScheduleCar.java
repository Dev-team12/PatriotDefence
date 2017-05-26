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
 * @author Igor Hnes on 17.05.17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.DIRTY, dynamicUpdate = true)
@Table(name = "car_project")
public class ScheduleCar extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "car_id")
    private Long carId;
    @Column(name = "project_id")
    private Long projectId;
    @Column(name = "project_start")
    private LocalDate projectStart;
    @Column(name = "project_finish")
    private LocalDate projectFinish;
    @Column(name = "car_name")
    private String carName;
}
