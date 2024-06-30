package service;

/**
 * Rappresent a license object
 * 
 * @version 1.0
 * @since 1.0
 */
public class License {

    // Attributes
    private String licenseNumber;
    private String typeAccess;
    private String expirationDate;
    private String issuingDate;

    public License(String licenseNumber, String typeAccess, String expirationDate, String issuingDate) {
        this.licenseNumber = licenseNumber;
        this.typeAccess = typeAccess;
        this.expirationDate =expirationDate;
        this.issuingDate = issuingDate;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getTypeAccess() {
        return typeAccess;
    }

    public void setTypeAccess(String typeAccess) {
        this.typeAccess = typeAccess;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getIssuingDate() {
        return issuingDate;
    }

    public void setIssuingDate(String issuingDate) {
        this.issuingDate = issuingDate;
    }
}