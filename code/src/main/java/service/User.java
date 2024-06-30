package service;

import java.sql.Date;

/**
 * User class
 * 
 * @version 1.0
 * @since 1.0
 */
public class User {

    // Attributes
    private  long costumer_ID;
    private  String email;
    private  String firstName;
    private  String lastName;
    private  Date dateOfBirth;
    private  String addressCostumer;
    private  String Phone;
    private  String nationality;
    private  String passwordCostumer;
    private  String licenseNumber;



    /**
     * Constructor
     */
    public User() {
        super();
    }

    /**
     * Constructor
     * 
     * @param costumer_ID the costumer id
     * @param email the email
     * @param firstName the first name
     * @param lastName the last name
     * @param dataOfBirth the date of birth
     * @param addressCostumer the address
     * @param phone the phone number
     * @param nationality the nationality
     * @param passwordCostumer the password
     * @param licenseNumber the license number
     */
    public User(long costumer_ID, String email, String firstName, String lastName, String dataOfBirth, String addressCostumer, String phone, String nationality, String passwordCostumer, String licenseNumber) {
        this.costumer_ID = costumer_ID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = Date.valueOf(dataOfBirth);
        this.addressCostumer = addressCostumer;
        Phone = phone;
        this.nationality = nationality;
        this.passwordCostumer = passwordCostumer;
        this.licenseNumber = licenseNumber;
    }

    /**
     * Constructor
     * 
     * @param email the email
     * @param firstName the first name
     * @param lastName the last name
     * @param dateOfBirth the date of birth
     * @param addressCostumer the address
     * @param phone the phone number
     * @param nationality the nationality
     * @param licenseNumber the license number
     */
    public User(String email, String firstName, String lastName, String dateOfBirth, String addressCostumer, String phone, String nationality, String licenseNumber) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = Date.valueOf(dateOfBirth);
        this.addressCostumer = addressCostumer;
        Phone = phone;
        this.nationality = nationality;
        this.licenseNumber = licenseNumber;
    }

    /**
     * Constructor
     * 
     * @param email the email
     * @param firstName the first name
     * @param lastName the last name
     * @param dataOfBirth the date of birth
     * @param addressCostumer the address
     * @param phone the phone number
     * @param nationality the nationality
     * @param passwordCostumer the password
     * @param licenseNumber the license number
     */
    public User(String email, String firstName, String lastName, String dataOfBirth, String addressCostumer, String phone, String nationality, String passwordCostumer, String licenseNumber) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = Date.valueOf(dataOfBirth);
        this.addressCostumer = addressCostumer;
        Phone = phone;
        this.nationality = nationality;
        this.passwordCostumer = passwordCostumer;
        this.licenseNumber = licenseNumber;
    }

    /**
     * Constructor
     * 
     * @param email the email
     * @param passwordCostumer the password
     */
    public User(String email, String passwordCostumer) {
        this.email = email;
        this.passwordCostumer = passwordCostumer;
    }

    /**
     * Get the costumer id
     * @return the costumer id
     */
    public long getCostumer_ID() {
        return costumer_ID;
    }

    /**
     * Set the costumer id
     * @param costumer_ID the costumer id
     */
    public void setCostumer_ID(long costumer_ID) {
        this.costumer_ID = costumer_ID;
    }

    /**
     * Get the email
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the first name
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the first name
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the last name
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the last name
     * @return the last name
     */
    public String getFullName() {
        return firstName+" "+lastName;
    }
    /**
     * Get the date of birth
     * @return the date of birth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Set the date of birth
     * @param dataOfBirth the date of birth
     */
    public void setDateOfBirth(String dataOfBirth) {
        this.dateOfBirth = Date.valueOf(dataOfBirth);
    }

    /**
     * Get the address
     * @return the address
     */
    public String getAddressCostumer() {
        return addressCostumer;
    }

    /**
     * Set the address
     * @param addressCostumer the address
     */
    public void setAddressCostumer(String addressCostumer) {
        this.addressCostumer = addressCostumer;
    }

    /**
     * Get the phone number
     * @return the phone number
     */
    public String getPhone() {
        return Phone;
    }

    /**
     * Set the phone number
     * @param phone the phone number
     */
    public void setPhone(String phone) {
        Phone = phone;
    }

    /**
     * Get the nationality
     * @return the nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Set the nationality
     * @param nationality the nationality
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * Get the password
     * @return the password
     */
    public String getPasswordCostumer() {
        return passwordCostumer;
    }

    /**
     * Set the password
     * @param passwordCostumer the password
     */
    public void setPasswordCostumer(String passwordCostumer) {
        this.passwordCostumer = passwordCostumer;
    }

    /**
     * Get the license number
     * @return the license number
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /**
     * Set the license number
     * @param licenseNumber the license number
     */
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}
