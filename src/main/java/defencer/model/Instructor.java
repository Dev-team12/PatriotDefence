package defencer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import javax.persistence.*;

/**
 * @author Igor Gnes on 3/30/17.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Instructor extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String qualification;
    @Column
    private String role;
    @Column
    private String phone;
    @Column
    private String status;
    @Column
    private String email;
    @Column
    private String password;


    @Override
    public String toString() {
        return firstName + " " + lastName + " " + phone + " " + email;
    }



}
