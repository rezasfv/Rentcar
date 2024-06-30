package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SuppressWarnings("rawtypes")
/**
 * Remove a rental transaction from the database
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public class RemoveRentalTransactionDAO extends BasicDAO{

    /**
     * Remove a rental transaction from the database
     * 
     * @param c the connection to the database
     * @param rentalTransaction the rental transaction to be removed
     * @throws NullPointerException if the connection is null
     * @throws SQLException if the connection is not valid
     */
    public RemoveRentalTransactionDAO(final Connection c, final String payment_ID) throws NullPointerException, SQLException {
        super(c);
        this.payment_ID = payment_ID;
    }

    @Override
    /**
     * Remove the rental transaction from the database
     * 
     * @throws SQLException if an error occurs during the deletion
     * @throws NullPointerException if the payment ID is null
     */
    protected void doAccess() throws SQLException {
        PreparedStatement pstmt = null;
        try{
            if(payment_ID == null){
                LOGGER.error("The payment ID cannot be null.");
                throw new NullPointerException("The payment ID cannot be null.");
            }
            pstmt = c.prepareStatement(statement);
            pstmt.setString(1, payment_ID);

            //I esecute the query
            pstmt.execute();

            LOGGER.info("Rental transaction removed successfully with payment ID: %s", payment_ID);
        }
        catch(NullPointerException ex){
            LOGGER.error("Unable to remove the rental transaction because payment ID is null!", ex);
            throw ex;
        }
        catch(SQLException e){
            LOGGER.error("Unable to remove the rental transaction", e);
            throw e;
        }
        finally{
            if(pstmt != null){
                pstmt.close();
            }
        }
    }

    /**
     * The statement to execute (precompiled)
     */
    private final String statement = "DELETE FROM RentalTransaction WHERE Payment_ID = ?";

    /**
     * The rental transaction to be removed
     */
    private String payment_ID;
    
}
