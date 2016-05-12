package OwnExceptions;


/**
 * Exeptions should not be run, because we have make our own error service
 * @author Olek
 *
 */
public class NoDigitException extends Exception {
	
	public NoDigitException(String msg) {
		super(msg);
	}
}
