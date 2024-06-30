package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import service.RentalTransaction;

@SuppressWarnings("rawtypes")
/**
 * This class is used to undo a reservation in the database
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public class UndoReservationDAO extends BasicDAO{

    /**
     * Constructor
     * 
     * @param c the connection to the database
     * @param rentalTransaction the rental transaction to be undone
     * @throws NullPointerException if the connection is null
     * @throws SQLException if the connection is not valid
     */
    protected UndoReservationDAO(final Connection c, final RentalTransaction rentalTransaction) 
    throws NullPointerException, SQLException {
        super(c);
        this.rentalTransaction = rentalTransaction;
    }

    @Override
     /**
     * Undo a reservation
     * 
     * @throws SQLException if there is an error accessing the database
     */
    protected void doAccess() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet valResSet = null;
        //First I have to check if the starting date of the reservation is not in the past.
        try{
            pstmt = c.prepareStatement(SQLQuery_checkReservation);
            pstmt.setString(1, rentalTransaction.getPayment_ID());
            valResSet = pstmt.executeQuery();
            if(!valResSet.next()){
                //The reservation is in the past
                LOGGER.error("The reservation is in the past, so I can't undo it");
                return;
            }else{
                //The reservation is in the future
                LOGGER.info("The reservation %s is in the future, so I can undo it", rentalTransaction.getPayment_ID());
                try{
                    pstmt = c.prepareStatement(SQLQuery_deleteReservation);
                    pstmt.setString(1, rentalTransaction.getPayment_ID());
                    pstmt.execute();

                    LOGGER.info("Reservation undone successfully with payment ID: %s", rentalTransaction.getPayment_ID());
                }
                catch(SQLException e){
                    LOGGER.error("Unable to undo the reservation!", e);
                }
                finally{
                    if(pstmt != null){
                        pstmt.close();
                    }
                }
            }
        }
        catch(SQLException e){
            LOGGER.error("Unable to check the validity of the reservation!", e);
        }
        finally{
            if(pstmt != null){
                pstmt.close();
            }
        }
    }

    /**
     * The rental transaction that has to be undone
     */
    private final RentalTransaction rentalTransaction;

    /**
     * The query to execute to check the validity of the reservation     
     */
    private static String SQLQuery_checkReservation = "SELECT Payment_ID FROM RentalTransaction WHERE Payment_ID = ? AND StartDate >= CURDATE()";

    /**
     * The query to execute to undo the reservation
     */
    private static String SQLQuery_deleteReservation = "DELETE FROM RentalTransaction WHERE Payment_ID = ?";
    
}
