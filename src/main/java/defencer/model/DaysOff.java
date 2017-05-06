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
 * @author Igor Gnes on 5/7/17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.DIRTY, dynamicUpdate = true)
@Table(name = "days_off")
public class DaysOff extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;
    @Column(name = "date_to", nullable = false)
    private LocalDate dateTo;
    @Column(name = "instructor_id", nullable = false)
    private Long instructorId;
}
