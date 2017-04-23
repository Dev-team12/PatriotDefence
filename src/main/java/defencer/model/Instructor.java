package defencer.model;

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
    private String role;
    @Column(name = "phone")
    private String phone;
    @Column(name = "project_id")
    private int projectId;
    @Column(name = "status")
    private String status;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    public Instructor(Long id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }

    public Instructor(Long id, String firstName, String lastName, String qualification,
                      String role, String phone, String status, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.qualification = qualification;
        this.role = role;
        this.phone = phone;
        this.status = status;
        this.email = email;
    }
}
