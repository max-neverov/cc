package cc.rest.exception;

/**
 * @author Maxim Neverov
 */
public class DbError extends RuntimeException {

    public DbError(String message, Throwable cause) {
        super(message, cause);
    }

}
