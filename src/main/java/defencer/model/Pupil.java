package defencer.model;

import lombok.*;

/**
 * @author Igor Gnes on 3/30/17.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pupil extends AbstractEntity {

    private String name;
    private String email;
    private String phone;
    private String description;
    private String occupation;
    private String nameOfProject;
}

