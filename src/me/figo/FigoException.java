package me.figo;

/***
 * Base Class for all figo Exceptions.
 * 
 * It extends the normal Java exceptions with an error_code field, which carries the computer readable error reason.
 * 
 * @author Stefan Richter <stefan.richter@figo.me>
 *
 */
public class FigoException extends Exception {

	private static final long serialVersionUID = -3645017096212930985L;

	private String error_code;
	
	public FigoException(String error_code, String error_message) {
		super(error_message);
		
		this.error_code = error_code;
	}

	public FigoException(String error_code, String error_message, Throwable exc) {
		super(error_message, exc);
		
		this.error_code = error_code;
	}
	
	public FigoException(ErrorResponse response) {
		this(response.getError(), response.getErrorDescription());
	}
	
	public String getErrorCode() {
		return error_code;
	}
	
	public class ErrorResponse {
		private String error;
		private String error_description;
		
		public ErrorResponse() {}
		
		public String getError() {
			return error;
		}
		
		public String getErrorDescription() {
			return error_description;
		}
	}
}
