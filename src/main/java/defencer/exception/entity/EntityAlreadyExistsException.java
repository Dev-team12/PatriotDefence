package defencer.exception.entity;

/**
 * @author Igor Gnes on 4/6/17.
 */
public class EntityAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public EntityAlreadyExistsException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
