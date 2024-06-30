package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@SuppressWarnings("rawtypes")
/**
 * This class is used to update the status of the cars that have expired reservation
 * 
 * NOTE: Is design to be run every day at 00:00 because it checks the reservation that have expired in the current day
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public class ExpiredReservationDAO extends BasicDAO{

    /**
     * Constructor
     * 
     * @param c the connection to the database
     * @throws NullPointerException if the connection is null
     * @throws SQLException if the connection is not valid
     */
    protected ExpiredReservationDAO(final Connection c) throws NullPointerException, SQLException {
        super(c);
    }

    @Override
    /**
     * Update the status of the cars that have expired reservation
     * 
     * @throws SQLException if an error occurs during the query
     */
    protected void doAccess() throws SQLException{
        PreparedStatement expiredreservation_pstmt = null;
        PreparedStatement updatecar_pstmt = null;
        ResultSet expResultSet = null;
        try {
            //I get all the license plate of the expired reservation
            expiredreservation_pstmt = c.prepareStatement(SQLQuery_ExpiredReservation);
            expResultSet = expiredreservation_pstmt.executeQuery();

            LOGGER.info("Getting the expired reservation from the database");
            while (expResultSet.next()){
                //Now I have update the relative cars from reserve to available
                try {
                    updatecar_pstmt = c.prepareStatement(SQLQuery_UpdateCar);
                    updatecar_pstmt.setString(1, expResultSet.getString("LicensePlate"));
                    //I update one car at time
                    updatecar_pstmt.executeUpdate();

                    LOGGER.info("The car with license plate: %s has been updated to available", expResultSet.getString("LicensePlate"));
                } 
                catch (SQLException e){
                    LOGGER.error("Error in updating the car status of this car: " + expResultSet.getString("LicensePlate"));
                } 
                finally {
                    if (updatecar_pstmt != null){
                        updatecar_pstmt.close();
                    }
                }
            //end of while
            }
        }
        catch (SQLException ex){
            LOGGER.error("Unable to get the expired reservation from the database");
        }
        finally {
            if(expiredreservation_pstmt != null){
                expiredreservation_pstmt.close();
            }
        }
    } 

    /**
     * Query to get all the license plate of the expired reservation
     */
    private static String SQLQuery_ExpiredReservation = "SELECT LicensePlate FROM RentalTransaction WHERE ToDate = CURDATE()";

    /**
     * Update the car status from rented to available. I only update cars that have status rented
     */
    private static String SQLQuery_UpdateCar = "UPDATE Car SET CurrentStatus = 'available' WHERE LicensePlate = ? AND CurrentStatus = \"rented\"";
}