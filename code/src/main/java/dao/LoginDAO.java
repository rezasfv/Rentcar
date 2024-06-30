package dao;

import service.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Login a user in the database
 *
 * @version 1.0
 * @since 1.0
 */
public class LoginDAO extends BasicDAO<User> {

    /**
     * The query to be executed (precompiled)
     */
    private static final String STATEMENT_LOGIN = "SELECT * FROM Customer WHERE email = ?::email";

    private final String email;

    /**
     * Constructor
     *
     * @param con the connection to the database
     * @param email the email of the user
     * @throws SQLException
     */
    public LoginDAO(final Connection con, final String email) throws SQLException {
        super(con);
        this.email = email;
    }

    @Override
    /**
     * Login a user in the database
     *
     * @throws SQLException if an error occurs
     */
    protected void  doAccess() throws SQLException {
        PreparedStatement stmnt = null;
        ResultSet rs = null;

        try {
            stmnt = c.prepareStatement(STATEMENT_LOGIN);
            stmnt.setString(1, email);

            rs = stmnt.executeQuery();

            if (rs.next()) {
                outputParam = new User();
                outputParam.setCostumer_ID(rs.getInt("Costumer_ID"));
                outputParam.setEmail(rs.getString("email"));
                outputParam.setFirstName(rs.getString("firstname"));
                outputParam.setLastName(rs.getString("lastname"));
                outputParam.setPhone(rs.getString("phone"));
                outputParam.setDateOfBirth(rs.getString("DataBirth")); // Assuming it's stored as string for now
                outputParam.setAddressCostumer(rs.getString("Address_Costumer"));
                outputParam.setNationality(rs.getString("Nationality"));
                outputParam.setPasswordCostumer(rs.getString("Password_Costumer"));
                outputParam.setLicenseNumber(rs.getString("LicenseNumber"));
            } else {
                System.out.println("User not found for email: " + email);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, "Error executing login query", ex);
            throw ex; // Rethrow the exception to be handled by the caller
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmnt != null) {
                stmnt.close();
            }
        }
    }
}
