package co.techmagic.randd.data.db;

/**
 * Created by ruslankuziak on 12/28/17.
 */

public class DataException extends RuntimeException {

    public DataException() {
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(Throwable cause) {
        super(cause);
    }
}
