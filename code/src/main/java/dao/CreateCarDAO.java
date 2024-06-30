package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import service.Car;

/**
 * Create a new car in the database
 * 
 * @author Francesco Chemello
 * @author Luca Pellegrini
 * @version 1.0
 * @since 1.0
 */
public class CreateCarDAO extends BasicDAO<Car>{

    /**
     * Create a new car in the database
     * 
     * @param c the connection to the database
     * @param car the car to be created
     * @throws SQLException if the connection is not valid
     * @throws NullPointerException if the connection is null
     */
    public CreateCarDAO(final Connection c, final Car car) throws SQLException, NullPointerException {
        super(c);
        if (car == null) {
            LOGGER.error("The car cannot be null.");
            throw new NullPointerException("The car cannot be null.");
        }
        this.car = car;
    }

    @Override
    /**
     * Create a new car in the database
     *
     * @throws SQLException if an error occurs during the insertion
     */
    protected final void doAccess() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the created car
        Car carnew = null;

        try {
            pstmt = c.prepareStatement(SQLQuery);
            pstmt.setString(1, car.getLicensePlate());
            pstmt.setFloat(2, car.getRentalRate());
            pstmt.setInt(3, car.getCapacity());
            pstmt.setString(4, car.getModelName());
            pstmt.setString(5, car.getCategory());
            pstmt.setString(6, car.getCurrentStatus());
            pstmt.setString(7, car.getBrandName());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                carnew = new Car(rs.getString("licenseplate"), rs.getFloat("rentalrate"), rs.getInt("capacity"),
                        rs.getString("modelname"), rs.getString("category"),rs.getString("currentstatus"),rs.getString("brandname"));

                LOGGER.info("Car {} successfully stored in the database.", carnew.getLicensePlate());
            }
        } 
        catch (SQLException e) {
            LOGGER.error("Unable to create the car", e);
            throw e;
        } 
        finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }

        outputParam = carnew;
    }

    /**
     * The SQL statement to be executed
     */
    private final String SQLQuery = "INSERT INTO Car (LicensePlate, RentalRate, Capacity, ModelName, Category, CurrentStatus, BrandName) VALUES (?, ?, ?, ?, ?::car_class, ?::car_status, ?) RETURNING *";

    /**
     * The car to be created
     */
    private final Car car;
}
