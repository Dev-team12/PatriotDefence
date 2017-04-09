package defencer.model;

import lombok.*;
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
@Table(name = "instructor")
public class Instructor extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "qualification")
    private String qualification;
    @Column(name = "role")
    private Role role;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
}
