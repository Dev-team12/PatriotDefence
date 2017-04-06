package defencer.model;

import lombok.*;

/**
 * @author Igor Gnes on 3/30/17.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project extends AbstractEntity {

    private String name;
    private String time;
    private String place;
    private String dataOfCreation;
    private String resources;
    private String instructors;
    private String author;
    private String description;
}
