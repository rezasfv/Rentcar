package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import org.mindrot.jbcrypt.BCrypt;
import service.User;
import java.time.LocalDate;
/**
 * Register a new customer in the database
 *
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
public class RegisterUserDAO extends BasicDAO {

    private static final String STATEMENT_EMAIL_EXISTENCE = "SELECT COUNT(*) FROM Customer WHERE Email = ?";
    private static final String STATEMENT_INSERT_LICENSE = "INSERT INTO driverslicense (licensenumber, typeaccept, expirationdate, issuingdate) VALUES (?, ?::license_type, ?, ?)";
    private static final String STATEMENT_INSERT_CUSTOMER = "INSERT INTO customer (email, firstname, lastname, phone, databirth, address_costumer, nationality, password_costumer, licensenumber) VALUES (?::email, ?::name_, ?::name_, ?::phone_number, ?, ?::address, ?::nationality, ?::passwd, ?)";

    private final User user;

    public RegisterUserDAO(final Connection con, final User user) throws SQLException {
        super(con);
        this.user = user;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement emailCheckStatement = null;
        ResultSet resultSet = null;
        String hashedPassword = BCrypt.hashpw(user.getPasswordCostumer(), BCrypt.gensalt());
        LocalDate specificDate = LocalDate.of(2021, 2, 2);

        java.sql.Date sqlSpecificDate = java.sql.Date.valueOf(specificDate);


        try {
            // Check database connection by executing a simple query
            try (Statement testStatement = c.createStatement()) {
                testStatement.execute("SELECT 1");
            } catch (SQLException ex) {
                LOGGER.error("Error testing database connection", ex);
                throw new SQLException("Error testing database connection: " + ex.getMessage());
            }

            emailCheckStatement = c.prepareStatement(STATEMENT_EMAIL_EXISTENCE);
            emailCheckStatement.setString(1, user.getEmail());
            resultSet = emailCheckStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count > 0) {
                throw new Exception("Email already exists");
            }

            PreparedStatement licenseStatement = c.prepareStatement(STATEMENT_INSERT_LICENSE);
            licenseStatement.setString(1, user.getLicenseNumber());
            licenseStatement.setString(2, "B");
            licenseStatement.setDate(3, Date.valueOf(specificDate));
            licenseStatement.setDate(4, Date.valueOf(specificDate));
            licenseStatement.executeUpdate();

            PreparedStatement customerStatement = c.prepareStatement(STATEMENT_INSERT_CUSTOMER);
            customerStatement.setString(1, user.getEmail());
            customerStatement.setString(2, user.getFirstName());
            customerStatement.setString(3, user.getLastName());
            customerStatement.setString(4, user.getPhone());
            customerStatement.setDate(5, Date.valueOf(String.valueOf(user.getDateOfBirth())));
            customerStatement.setString(6, user.getAddressCostumer());
            customerStatement.setString(7, user.getNationality());
            customerStatement.setString(8, hashedPassword);
            customerStatement.setString(9, user.getLicenseNumber());
            customerStatement.executeUpdate();

            LOGGER.info("User registered {}.", user.getEmail());

        } catch (SQLException ex) {
            LOGGER.error("Error accessing the database", ex);
            throw ex;
        } catch (Exception ex) {
            LOGGER.error("Error registering user", ex);
            throw ex;
        } finally {
            try {
                if (emailCheckStatement != null) {
                    emailCheckStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ex) {
                LOGGER.error("Error closing statement or result set", ex);
            }
        }
    }
}
