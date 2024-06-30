package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import service.Car;

/**
 * Delete a car in the database
 * 
 * @author Francesco Chemello
 * @author Luca Pellegrini
 * @version 1.0
 * @since 1.0
 */
public class DeleteCarDAO extends BasicDAO<Car>{

    /**
     * Delete a car in the database
     * 
     * @param c the connection to the database
     * @param licenseplate the licenseplate of car to be deleted
     * @throws NullPointerException if the connection is null
     * @throws SQLException if the connection is not valid
     */
    public DeleteCarDAO(final Connection c, final String licenseplate) throws NullPointerException, SQLException {
        super(c);
        if (licenseplate == null) {
            LOGGER.error("The licenseplate cannot be null.");
            throw new NullPointerException("The licenseplate cannot be null.");
        } else {
            this.licenseplate = licenseplate;
        }
    }

    @Override
    /**
     * Delete a car in the database
     * 
     * @throws SQLException if an error occurs during the deletion
     */
    protected void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Car car = null;

        try {
            pstmt = c.prepareStatement(STATEMENT);
            pstmt.setString(1, licenseplate);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                car = new Car(rs.getString("licenseplate"), rs.getFloat("rentalrate"), rs.getInt("capacity"), rs.getString("modelname"), rs.getString("category"), rs.getString("currentstatus"), rs.getString("brandname"));
                LOGGER.info("Car {} successfully deleted from the database.", car.getLicensePlate());
            }
        } catch (NullPointerException ex) {
            LOGGER.error("Unable to delete the car because license plate is null!", ex);
            throw ex;
        } catch (SQLException e) {
            LOGGER.error("Unable to delete the car", e);
            throw e;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

        }
        outputParam = car;
    }

    /**
     * The statement to execute (precompiled)
     */
    private final String STATEMENT = "DELETE FROM Car WHERE LicensePlate = ? RETURNING *";

    /**
     * The car to delete
     */
    private final String licenseplate;
    
}