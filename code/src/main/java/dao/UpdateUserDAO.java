package dao;

import service.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("rawtypes")
public class UpdateUserDAO extends BasicDAO {

    private static final String UPDATE_STATEMENT = "UPDATE Customer SET FirstName=?::name_, LastName=?::name_, Phone=?::phone_number, DataBirth=?, Address_Costumer=?::address, Nationality=?::nationality WHERE Email=?::email";

    private final User user;

    public UpdateUserDAO(Connection con, User user) throws SQLException {
        super(con);
        this.user = user;
    }

    @Override
    protected void doAccess() throws SQLException {
        PreparedStatement stmnt = null;
        try {
            stmnt = c.prepareStatement(UPDATE_STATEMENT);
            stmnt.setString(1, user.getFirstName());
            stmnt.setString(2, user.getLastName());
            stmnt.setString(3, user.getPhone());
            stmnt.setDate(4, Date.valueOf(String.valueOf(user.getDateOfBirth())));
            stmnt.setString(5, user.getAddressCostumer());
            stmnt.setString(6, user.getNationality());
            stmnt.setString(7, user.getEmail());

            int rowsAffected = stmnt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows updated.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateUserDAO.class.getName()).log(Level.SEVERE, "Error executing update statement", ex);
            throw ex; // Rethrow the exception to be handled by the caller
        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
        }
    }
}
