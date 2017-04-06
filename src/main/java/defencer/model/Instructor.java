package defencer.model;

import lombok.*;

/**
 * @author Igor Gnes on 3/30/17.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Instructor extends AbstractEntity {

    private String firstName;
    private String lastName;
    private String qualification;
    private Role role;
    private String phone;
    private String email;
    private String password;
}
