/**
 * 
 */
package me.figo.models;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

/**
 * object representing the response of a /catalog/banks/{country_code} request
 * 
 * @author Daniel
 *
 */
public class CatalogBank {

	@Expose
	private String bank_name;
	
	@Expose
	private Icon icon;
	
	@Expose
	private Language language;

	@Expose
	private String bank_code;
	
	@Expose
	private String bic;
	
	@Expose
	private List<Credential> credentials;
	
	@Expose
	private String advice;

	public String getBic() {
		return bic;
	}

	public Language getLanguage() {
		return language;
	}

	public List<Credential> getCredentials() {
		return credentials;
	}

	public String getAdvice() {
		return advice;
	}

	public String getBankName() {
		return bank_name;
	}

	public String getBankCode() {
		return bank_code;
	}

	/**
	 * wrapper class holding the list of banks as returned by figo api
	 * 
	 * @author Daniel
	 *
	 */
	public static class CatalogBanksResponse {
		/**
		 * List of banks asked for
		 */
		@Expose
		private List<CatalogBank> banks;

		public List<CatalogBank> getBanks() {
			return banks;
		}

	}

	public static class Icon {
		
		@Expose
		private Map<String,String> resolutions;
		
		@Expose
		private String url;

		public Map<String, String> getResolutions() {
			return resolutions;
		}

		public void setResolutions(Map<String, String> resolutions) {
			this.resolutions = resolutions;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}
	
	/**
	 * @return bank icon URL
	 */
	public String getIconUrl() {
		if(icon!=null){
			return getIcon().getUrl();
		}
		return "";
	}
	
	/**
	 * @return the bank icon in other resolutions
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getAdditionalIcons() {
		if(icon!=null&&icon.getResolutions().size()>1){
			return icon.getResolutions();
		}
		return null;
	}

	public Icon getIcon() {
		return icon;
	}
}
