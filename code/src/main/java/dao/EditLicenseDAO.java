package dao;

import service.License;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Create License in the database
 *
 * @author Seyedreza Safavi
 * @version 1.0
 * @since 1.0
 */

@SuppressWarnings("rawtypes")
public class EditLicenseDAO extends BasicDAO{

    /**
     * The SQL statement to be executed (precompiled)
     */
    private final String STATEMENET_EditLicenseDAO = "UPDATE DriversLicense SET TypeAccept=?::license_type, expirationdate=?, issuingdate=? WHERE licensenumber=?";

    /**
     * The license to be created
     */
    private final License license;

    /**
     * Create a license car in the database
     * 
     * @param c the connection to the database
     * @param license the license to be created
     * @throws NullPointerException if the connection is null
     * @throws SQLException if the connection is not valid
     */
    public EditLicenseDAO(final Connection c, final License license) throws NullPointerException, SQLException {
        super(c);
        if(license == null){
            LOGGER.error("The license cannot be null.");
            throw new NullPointerException("The license cannot be null.");
        }
        this.license = license;
    }

    @Override
    /**
     * Create a license car in the database
     * 
     * @throws SQLException if an error occurs during the insertion
     */
    protected void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        try {
            pstmt = c.prepareStatement(STATEMENET_EditLicenseDAO);

            pstmt.setString(1, license.getTypeAccess());
            pstmt.setDate(2, Date.valueOf(license.getExpirationDate()));
            pstmt.setDate(3, Date.valueOf(license.getIssuingDate()));
            pstmt.setString(4, (license.getLicenseNumber()));

            //I esecute the query
            pstmt.execute();

            LOGGER.info("license created successfully with licence_number: %s", license.getLicenseNumber());
        }
        catch(SQLException e){
            LOGGER.error("Unable to create the License", e);
            throw e;
        }
        finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }
}

