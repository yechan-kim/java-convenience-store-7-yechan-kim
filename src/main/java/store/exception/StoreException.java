package store.exception;

public class StoreException extends IllegalArgumentException {

    private static final String ERROR_PREFIX = "[ERROR] ";

    private StoreException(String message) {
        super(message);
    }

    public static StoreException from(ErrorMessage errorMessage) {
        return new StoreException(ERROR_PREFIX + errorMessage.getMessage());
    }
}
