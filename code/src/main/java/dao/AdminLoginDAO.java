package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import service.Admin;

@SuppressWarnings("rawtypes")
/**
 * Login a user in the database
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public class AdminLoginDAO extends BasicDAO{

    /**
     * Constructor
     * 
     * @param c the connection to the database
     * @param admin the admin to be logged in
     * @throws NullPointerException if the connection is null
     * @throws SQLException if the connection is not valid
     */
    public AdminLoginDAO(final Connection c, final Admin admin) throws NullPointerException, SQLException {
        super(c);
        this.admin = admin;
    }

    @Override
    /**
     * Login a user in the database
     * 
     * @throws SQLException if an error occurs
     */
    protected void doAccess() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = c.prepareStatement(SQLQuery);
            pstmt.setString(1, admin.getEmail());
            pstmt.setString(2, admin.getPassword());
            
            rs = pstmt.executeQuery();
            
            if(!rs.next()){
                //No admin found. What is wrong? E-mail or password?
                pstmt = c.prepareStatement(SQLQueryPasswordIncorrect);
                pstmt.setString(1, admin.getEmail());
                rs = pstmt.executeQuery();
                if(!rs.next()){
                    //No admin found with that e-mail -> e-mail is wrong!
                    LOGGER.error("Email not correct: {}", admin.getEmail());
                }else{
                    //Admin found with that e-mail -> password is wrong!
                    LOGGER.error("Password not correct for email: {}", admin.getEmail());
                }
            }else{
                //Admin found
                LOGGER.info("Admin logged in successfully with email: {}", admin.getEmail());
            }
        } 
        catch (SQLException e) {
            LOGGER.error("Unable to login the admin", e);
            throw e;
        } 
        finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if(rs != null){
                rs.close();
            }
        }
    }

    /**
     * The query to be executed (precompiled)
     */
    private static final String SQLQuery = "SELECT * FROM Admin WHERE email = ? AND password = ?";

    /**
     * The query to check if the password is incorrect
     */
    private static final String SQLQueryPasswordIncorrect = "SELECT * FROM Admin WHERE email = ?";

    /**
     * The admin
     */
    private final Admin admin;

}
