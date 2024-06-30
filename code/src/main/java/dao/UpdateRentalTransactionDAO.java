package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import service.RentalTransaction;

@SuppressWarnings("rawtypes")
/**
 * Update a rental transaction in the database
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public class UpdateRentalTransactionDAO extends BasicDAO{

    /**
     * Update a rental transaction in the database
     * 
     * @param c the connection to the database
     * @param rentalTransaction the rental transaction to be updated
     * @throws NullPointerException if the connection is null
     * @throws SQLException if the connection is not valid
     */
    public UpdateRentalTransactionDAO(final Connection c, RentalTransaction rentalTransaction) throws NullPointerException, SQLException {
        super(c);
        this.rentalTransaction = rentalTransaction;
    }

    @Override
    /**
     * Update a rental transaction in the database
     * 
     * @throws SQLException if an error occurs during the update
     */
    protected void doAccess() throws SQLException {
        PreparedStatement pstmt = null;
        try{
            if(rentalTransaction.getPayment_ID() == null){
                LOGGER.error("The payment ID cannot be null.");
                throw new NullPointerException("The payment ID cannot be null.");
            }
            pstmt = c.prepareStatement(statement);
            pstmt.setFloat(1, rentalTransaction.getAmount());
            pstmt.setString(2, rentalTransaction.getPaymentstatus());
            pstmt.setDate(3, rentalTransaction.getIssuedate());
            pstmt.setDate(4, rentalTransaction.getFromdate());
            pstmt.setDate(5, rentalTransaction.getTodate());
            pstmt.setString(6, rentalTransaction.getCostumer_id());
            pstmt.setString(7, rentalTransaction.getLicense_plate());
            pstmt.setString(8, rentalTransaction.getPayment_ID());

            //I esecute the query
            pstmt.execute();

            LOGGER.info("Rental transaction updated successfully with payment ID: %s", rentalTransaction.getPayment_ID());
        }
        catch(NullPointerException ex){
            LOGGER.error("Unable to update the rental transaction because payment ID is null!", ex);
            throw ex;
        }
        catch(SQLException e){
            LOGGER.error("Unable to update the rental transaction", e);
            throw e;
        }
        finally{
            if(pstmt != null){
                pstmt.close();
            }
        }
    }
    
    /**
     * The SQL statement to be executed (precompiled)
     */
    private final String statement = "UPDATE RentalTransaction SET amount = ?, paymentstatus = ?, issuedate = ?, fromdate = ?, todate = ?, costumer_id = ?, license_plate = ? WHERE payment_ID = ?";

    /**
     * The rental transaction to update
     */
    private final RentalTransaction rentalTransaction;
}
