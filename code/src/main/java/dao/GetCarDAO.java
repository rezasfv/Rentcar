package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import service.Car;

/**
 * Get car from database
 *
 * @author Luca Pellegrini
 * @version 1.0
 * @since 1.0
 */
public final class GetCarDAO extends BasicDAO<Car> {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT * FROM Car WHERE LicensePlate = ?";
    /**
     * The licenseplate to get
     */
    private final String licenseplate;
    /**
     * Get car from database
     *
     * @param c the connection to the database
     * @param licenseplate the licenseplate of the car to get
     * @throws SQLException if the connection is not valid
     * @throws NullPointerException if the connection is null
     */
    public GetCarDAO(Connection c, String licenseplate) throws SQLException, NullPointerException {
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
     * Get car from database
     *
     * @throws SQLException if an error occurs during the insertion
     */
    protected void doAccess() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Car car = null;

        try {
            pstmt = this.c.prepareStatement(STATEMENT);
            pstmt.setString(1, this.licenseplate);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                car = new Car(rs.getString("licenseplate"), rs.getFloat("rentalrate"), rs.getInt("capacity"), rs.getString("modelname"), rs.getString("category"), rs.getString("currentstatus"), rs.getString("brandname"));
                LOGGER.info("Car {} successfully get from the database.", car.getLicensePlate());
            }
        } catch (SQLException var8) {
            LOGGER.error("Unable to close the result set and/or statement.", var8);
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

        }

        this.outputParam = car;
    }
}