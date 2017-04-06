package defencer.model;

import lombok.Getter;
import lombok.Setter;

/**
 * The main class for all entity.
 *
 * @author Igor Gnes on 3/30/17.
 */
@Getter
@Setter
public class AbstractEntity {

    private Long id;

    public boolean isNew() {
        return this.id == null;
    }
}
