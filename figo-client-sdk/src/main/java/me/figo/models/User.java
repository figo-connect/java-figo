package me.figo.models;

import java.util.Date;
import java.util.HashMap;

import com.google.gson.annotations.Expose;

/**
 * Object representing an user
 * 
 * @author Stefan Richter
 */
public class User {
    /**
     * Internal figo Connect user ID
     */
    @Expose(serialize = false)
    private String                  user_id;

    /**
     * First and last name
     */
    @Expose
    private String                  name;

    /**
     * Email address
     */
    @Expose(serialize = false)
    private String                  email;

    /**
     * Postal address for bills, etc.
     */
    @Expose
    private HashMap<String, String> address;

    /**
     * This flag indicates whether the email address has been verified
     */
    @Expose(serialize = false)
    private boolean                 verified_email;

    /**
     * This flag indicates whether the user has agreed to be contacted by email
     */
    @Expose
    private boolean                 send_newsletter;

    /**
     * Two-letter code of preferred language
     */
    @Expose
    private String                  language;

    /**
     * This flag indicates whether the figo Account plan is free or premium
     */
    @Expose(serialize = false)
    private boolean                 premium;

    /**
     * Timestamp of premium figo Account expiry
     */
    @Expose(serialize = false)
    private Date                    premium_expires_on;

    /**
     * Provider for premium subscription or Null of no subscription is active
     */
    @Expose(serialize = false)
    private String                  premium_subscription;

    /**
     * Timestamp of figo Account registration
     */
    @Expose(serialize = false)
    private Date                    join_date;

    public User() {
    }

    /**
     * @return the internal figo Connect user ID
     */
    public String getUserId() {
        return user_id;
    }

    /**
     * @return the first and last name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the first and last name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the postal address for bills, etc
     */
    public HashMap<String, String> getAddress() {
        return address;
    }

    /**
     * @param address
     *            the postal address for bills, etc to set
     */
    public void setAddress(HashMap<String, String> address) {
        this.address = address;
    }

    /**
     * @return whether the email address has been verified
     */
    public boolean isVerifiedEmail() {
        return verified_email;
    }

    /**
     * @return whether the user has agreed to be contacted by email
     */
    public boolean isSendNewsletter() {
        return send_newsletter;
    }

    /**
     * @param send_newsletter
     *            the send newsletter setting to set
     */
    public void setSendNewsletter(boolean send_newsletter) {
        this.send_newsletter = send_newsletter;
    }

    /**
     * @return the two-letter code of preferred language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language
     *            the two-letter code of preferred language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return whether the figo Account plan is free or premium
     */
    public boolean isPremium() {
        return premium;
    }

    /**
     * @return the timestamp of premium figo Account expiry
     */
    public Date getPremiumExpiresOn() {
        return premium_expires_on;
    }

    /**
     * @return the provider for premium subscription or Null of no subscription is active
     */
    public String getPremiumSubscription() {
        return premium_subscription;
    }

    /**
     * @return the timestamp of figo Account registration
     */
    public Date getJoinDate() {
        return join_date;
    }
}
