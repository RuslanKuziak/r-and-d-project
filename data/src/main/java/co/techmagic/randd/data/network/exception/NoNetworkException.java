package co.techmagic.randd.data.network.exception;

/**
 * Created by ruslankuziak on 12/22/17.
 */

public class NoNetworkException extends RuntimeException {

    public NoNetworkException() {}

    public NoNetworkException(String message) {
        super(message);
    }

    public NoNetworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoNetworkException(Throwable cause) {
        super(cause);
    }
}