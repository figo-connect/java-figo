package me.figo.internal;

/***
 * Class Holding ephemeral types for action calls
 * 
 * @author Stefan Richter <stefan.richter@figo.me>
 *
 */
public class ActionTypes {
	
	/***
	 * Helper type for the call to /rest/sync
	 */
	@SuppressWarnings("unused")
	public static class SyncTokenRequest {
		/***
		 * State to return when coming back to the calling application after the synchronization finished
		 */
		private String state;
		
		/***
		 * URL to redirect to when the synchronization finished
		 */
		private String redirect_uri;
		
		public SyncTokenRequest(String state, String redirect_uri) {
			this.state = state;
			this.redirect_uri = redirect_uri;
		}
	}
	
	/***
	 * Helper type for the return value of /rest/sync
	 */
	public class SyncTokenResponse {
		public String task_token;
	}
}
