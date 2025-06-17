package exception;

/**
 * Exception that will be thrown if AT fails for reasons other than verification
 */
public class ATException extends Exception {

  public ATException(String message) {
    super(message);
  }

  public ATException(String message, Exception e) {
    super(message, e);
  }
}