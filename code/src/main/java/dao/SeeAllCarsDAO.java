package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import service.Car;

/**
 * This class is used to search all the cars in the database
 *
 * @version 1.0
 * @since 1.0
 */
public class SeeAllCarsDAO extends BasicDAO<List<Car>> {

    private static final String SQL_QUERY = "SELECT * FROM Car";

    /**
     * Constructor.
     *
     * @param c the database connection
     * @throws NullPointerException if the connection is null
     * @throws SQLException if a database access error occurs
     */
    public SeeAllCarsDAO(Connection c) throws NullPointerException, SQLException {
        super(c);
    }

    @Override
    protected void doAccess() throws SQLException, Exception {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        final List<Car> cars = new ArrayList<>();

        try {
            // Check database connection by executing a simple query
            try (var testStatement = c.createStatement()) {
                testStatement.execute("SELECT 1");
            } catch (SQLException ex) {
                LOGGER.error("Error testing database connection", ex);
                throw new SQLException("Error testing database connection: " + ex.getMessage());
            }

            // Prepare and execute the query
            pstmt = c.prepareStatement(SQL_QUERY);
            rset = pstmt.executeQuery();

            // Extract the data from the result set
            while (rset.next()) {
                cars.add(new Car(rset.getString("LicensePlate"),
                        rset.getFloat("RentalRate"),
                        rset.getInt("Capacity"),
                        rset.getString("ModelName"),
                        rset.getString("Category"),
                        rset.getString("CurrentStatus"),
                        rset.getString("BrandName")));
            }

            LOGGER.info("All cars found!");

            // Set the result to be returned
            setOutputParam(cars);

        } catch (SQLException ex) {
            LOGGER.error("Error accessing the database", ex);
            throw ex;
        } catch (Exception ex) {
            LOGGER.error("Error retrieving cars", ex);
            throw ex;
        } finally {
            try {
                if (rset != null) {
                    rset.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                LOGGER.error("Error closing statement or result set", ex);
            }
        }
    }

    /**
     * Sets the output parameter
     *
     * @param outputParam the list of cars to be set as the output parameter
     */
    private void setOutputParam(List<Car> outputParam) {
        this.outputParam = outputParam;
    }
}
