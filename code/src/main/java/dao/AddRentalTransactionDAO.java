package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import service.RentalTransaction;

@SuppressWarnings("rawtypes")
/**
 * Add a rental transaction to the database
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public class AddRentalTransactionDAO extends BasicDAO{

    /**
     * Add a rental transaction to the database
     * 
     * @param c the connection to the database
     * @param rentalTransaction the rental transaction to be added
     * @throws NullPointerException if the connection is null
     * @throws SQLException if the connection is not valid
     */
    public AddRentalTransactionDAO(final Connection c, final RentalTransaction rentalTransaction) throws NullPointerException, SQLException {
        super(c);
        this.rentalTransaction = rentalTransaction;
    }

    @Override
    /**
     * Add a rental transaction to the database
     * 
     * @throws SQLException if an error occurs during the insertion
     */
    protected void doAccess() throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = c.prepareStatement(statement);
            pstmt.setString(1, rentalTransaction.getPayment_ID());
            pstmt.setFloat(2, rentalTransaction.getAmount());
            pstmt.setString(3, rentalTransaction.getPaymentstatus());
            pstmt.setDate(4, rentalTransaction.getIssuedate());
            pstmt.setDate(5, rentalTransaction.getFromdate());
            pstmt.setDate(6, rentalTransaction.getTodate());
            pstmt.setInt(7, Integer.parseInt(rentalTransaction.getCostumer_id()));
            pstmt.setString(8, rentalTransaction.getLicense_plate());

            //I esecute the query
            pstmt.execute();

            LOGGER.info("Rental transaction added successfully with payment ID: %s", rentalTransaction.getPayment_ID());
        }
        catch(SQLException e){
            LOGGER.error("Unable to add the rental transaction!", e);
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
    private final String statement = "INSERT INTO RentalTransaction (payment_ID, amount, paymentstatus, issuedate, fromdate, todate, costumer_id, licenseplate) VALUES (?, ?, CAST(? AS pay_status), ?, ?, ?, ?, ?)";
    
    /**
     * The rental transaction to be added
     */
    private RentalTransaction rentalTransaction;
    
}
