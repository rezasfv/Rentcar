package dao;

import java.sql.*;

/**
 * This class is used to check if a car si available in a selected period
 *
 * @author Luca Pellegrini
 * @version 1.0
 * @since 1.0
 */
public class CheckAvailabilityDAO extends BasicDAO<Boolean>{

    String licensePlate = null;
    java.util.Date fromPeriod = null;
    java.util.Date toPeriod = null;

    /**
     * Constructor
     *
     * @param c the connection to the database
     * @param licensePlate the license plate to check
     * @param fromPeriod the start date
     * @param toPeriod the end date
     * @throws NullPointerException if the connection is null
     * @throws SQLException if the connection is not valid
     */
    public CheckAvailabilityDAO(final Connection c, String licensePlate, java.util.Date fromPeriod, java.util.Date toPeriod) throws NullPointerException, SQLException {
        super(c);
        this.licensePlate = licensePlate;
        this.fromPeriod = fromPeriod;
        this.toPeriod = toPeriod;
    }

    @Override
    /**
     * Check if a car or some cars are available in the period
     * 
     * @throws SQLException if an error occurs during the query
     */
    protected void doAccess() throws SQLException{
        PreparedStatement checkreservation_pstmt = null;
        ResultSet expResultSet = null;
        Boolean isAvaliable = false;

        try {
            //to check only one car
            if (licensePlate != null)
            {
                checkreservation_pstmt = c.prepareStatement(SQLQUERY_SELECTONECARPERIOD);
                checkreservation_pstmt.setString(1, licensePlate);
                checkreservation_pstmt.setDate(2, new java.sql.Date(toPeriod.getTime()));
                checkreservation_pstmt.setDate(3, new java.sql.Date(fromPeriod.getTime()));
            }
            //to check any car
            else
            {
                checkreservation_pstmt = c.prepareStatement(SQLQUERY_SELECTCARSPERIOD);
                checkreservation_pstmt.setDate(1, new java.sql.Date(toPeriod.getTime()));
                checkreservation_pstmt.setDate(2, new java.sql.Date(fromPeriod.getTime()));
            }
            expResultSet = checkreservation_pstmt.executeQuery();

            if (expResultSet.next())
            {
                isAvaliable = true;
            }

        }
        catch (SQLException ex){
            LOGGER.error("Unable to check the car(s) reservation" + ex.toString());
        }
        finally {
            if(checkreservation_pstmt != null){
                checkreservation_pstmt.close();
            }
        }

        this.outputParam = isAvaliable;
    } 

    /**
     * Query to get all the license plate of the expired reservation
     */
    //private static String SQLQuery_ExpiredReservation = "SELECT LicensePlate FROM Car WHERE LicensePlate = ? AND CurrentStatus = ?::car_status";
    private static final String SQLQUERY_SELECTCARSPERIOD = "SELECT c.licenseplate FROM car as c WHERE c.licenseplate NOT IN (SELECT r.licenseplate FROM rentaltransaction as r WHERE r.fromdate <= ?::date AND r.todate >= ?::date)"; //toPeriod - fromPeriod
    private static final String SQLQUERY_SELECTONECARPERIOD = "SELECT c.licenseplate FROM car as c WHERE c.licenseplate = ? AND c.licenseplate NOT IN (SELECT r.LicensePlate FROM rentaltransaction as r WHERE r.fromdate <= ?::date AND r.todate >= ?::date)"; //toPeriod - fromPeriod

}