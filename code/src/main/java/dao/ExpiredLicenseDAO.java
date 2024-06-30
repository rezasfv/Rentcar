package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.License;
import service.User;

/**
 * This class is used to check if the user has an expired license
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public class ExpiredLicenseDAO extends BasicDAO<List<License>>{

    /**
     * Constructor
     * 
     * @param c the connection to the database
     * @param user the user to be checked
     * @throws NullPointerException if the connection is null
     * @throws SQLException if the connection is not valid
     */
    protected ExpiredLicenseDAO(final Connection c, final User user) throws NullPointerException, SQLException {
        super(c);
        this.user = user;
    }

    @Override
    /**
     * Check if the user has an expired license
     * 
     * @throws SQLException if an error occurs during the query
     */
    protected void doAccess() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<License> licenses = new ArrayList<>();
        try{
            pstmt = c.prepareStatement(SQLQuery);
            pstmt.setInt(1, (int) user.getCostumer_ID());
            rset = pstmt.executeQuery();
            if(rset.next()){
                //The query returned a result, so the user has an expired license
                LOGGER.info("The user has an expired license");
                licenses.add(new License(rset.getString("LicenseNumber"),
                                         rset.getString("TypeAccess"),
                                         rset.getString("ExpiryDate"),
                                         rset.getString("IssuingDate")
                                        )
                            );
            }else{
                //The query returned no result, so the user has not an expired license
                LOGGER.info("The user has not an expired license");
            }
            //set the output param
            this.outputParam = licenses;
        }
        catch (SQLException e){
            LOGGER.error("Unable to check if the user has an expired license");
        }
    }
    
    /**
     * The user that has to be checked
     */
    private final User user;

    /**
     * The SQL query to execute
     */
    private static final String SQLQuery = "SELECT * FROM DriversLicense INNER JOIN HoldsLicense ON DriversLicense.LicenseNumber = HoldsLicense.LicenseNumber WHERE HoldsLicense.ExpiryDate < CURDATE() AND HoldsLicense.UserID = ?";
}
