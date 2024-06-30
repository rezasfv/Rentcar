package dao;

import service.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to list cars from the database
 * 
 * @author Luca Pellegrini
 * @version 1.0
 * @since 1.0
 */
public final class ListCarDAO extends BasicDAO<List<Car>>{

    /**
     * Constructor
     *
     * @param c the connection to the database
     * @throws NullPointerException if c is null
     * @throws SQLException if an error occurs in the database
     */
    public ListCarDAO(final Connection c)
    throws NullPointerException, SQLException{
        super(c);
    }

    @Override
    /**
     * List cars from the database
     * 
     * @throws SQLException if an error occurs
     */
    protected void doAccess() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        final List<Car> cars = new ArrayList<>();

        try {
			pstmt = c.prepareStatement(SQLQuery);

            rset = pstmt.executeQuery();

            //extract the data from the result set
            while (rset.next()) {
				cars.add(new Car(rset.getString("LicensePlate"),
                                 rset.getFloat("RentalRate"),
                                 rset.getInt("Capacity"),
                                 rset.getString("ModelName"),
                                 rset.getString("Category"),
                                 rset.getString("CurrentStatus"),
                                 rset.getString("BrandName")));

                LOGGER.info("Car found with licenseplate: " + rset.getString("LicensePlate"));
			}
        }
        catch (SQLException e) {
            LOGGER.error("Unable to close the result set and/or statement.", e);
        }
        finally {
            if (rset != null) {
                rset.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        //set the output param
        this.outputParam = cars;
    }

    /**
     * The SQL query to be executed
     */
    private static final String SQLQuery = "SELECT * FROM Car ";
}
