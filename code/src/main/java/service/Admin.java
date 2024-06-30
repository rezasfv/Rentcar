package service;

/**
 * Rappresent an admin object
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public class Admin {
    
    //Attributes
    private final String email;
    private final String password;

    /**
     * Constructor
     * 
     * @param email the email
     * @param password the password
     */
    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Get the email
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get the password
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}
