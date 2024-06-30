package dao;

import service.Reserve;
import service.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to get all the reservation of a specific user
 * 
 * @author Gabriella
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0 
 */
public final class ReserveByUserDAO extends BasicDAO<List<Reserve>>{

    /**
     * Constructor
     * 
     * @param c the connection to the database
     * @param user the user that has made the reservations
     * @throws SQLException if an error occur during the query execution
     * @throws NullPointerException if the connection is null 
     */
    public ReserveByUserDAO(final Connection c, final User user) throws NullPointerException, SQLException{
        super(c);
        this.user = user;
    }

    @Override
    public final void doAccess()throws SQLException{
        PreparedStatement pstmt = null;
        ResultSet resSet = null;

        final List<Reserve> reservation = new ArrayList<Reserve>();

        try{
            pstmt = c.prepareStatement(SQLQuery);
            pstmt.setInt(1, (int) user.getCostumer_ID());

            resSet = pstmt.executeQuery();

            //I get all the reservations of the user
            while (resSet.next()){
                reservation.add(new Reserve(resSet.getInt("costumer_id"),
                                resSet.getString("payment_ID"),
                                resSet.getString("licenseplate"),
                                resSet.getDate("fromDate"),
                                resSet.getDate("toDate")));
            }

            LOGGER.info("All the reservations of the user " + user.getCostumer_ID() + " have been retrieved");
        } 
        catch (SQLException e){
            LOGGER.error("Unable to retrieve all the reservations for the user %s", user.getCostumer_ID());
            System.out.println(e.getMessage());
        }        
        finally {
            if (resSet != null){
                resSet.close();
            }  
            
            if (pstmt != null){
                pstmt.close();
            } 
        }

        this.outputParam = reservation;
    }

    /**
     * The user that has made the reservations
     */
    private User user;

    /**
     * The SQL query to be executed
     */
    private static final String SQLQuery = "SELECT * FROM Reserve WHERE Costumer_ID = ?";

}


