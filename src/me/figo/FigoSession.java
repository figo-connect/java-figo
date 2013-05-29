package me.figo;

/***
 * Main entry point to the data access-part of the figo connect java library. Here you can retrieve all the
 * data the user granted your app access to.
 * 
 * @author Stefan Richter <stefan.richter@figo.me>
 */
public class FigoSession {
	
	private final String API_ENDPOINT = "api.leanbank.com";
	
	private String access_token = null;
	
	/***
	 * Creates a FigoSession instance
	 * 
	 * @param access_token the access token to bind this session to a user
	 */
	public FigoSession(String access_token) {
		this.access_token = access_token;
	}
}
