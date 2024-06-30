package dao;

import service.Reserve;

import java.sql.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reserve the car into database
 *
 * @version 1.0
 * @since 1.0
 */
public class ReserveDAO extends BasicDAO<Reserve> {

    /**
     * The query to be executed (precompiled)
     */
    private static final String STATEMENT_TRANSACTION = "INSERT INTO RentalTransaction (Amount, PaymentStatus, IssueDate) VALUES (?::public.amount,?::pay_status,?) RETURNING *";
    private static final String STATEMENT_RESERVATION = "INSERT INTO Reserve (Costumer_ID, Payment_ID, LicensePlate, FromDate, ToDate) VALUES (?,?,?::public.license_plate,?,?) RETURNING *";

    private final float amount;
    private final long customerID;
    private final String licencePlate;
    private final Date fromDate;
    private final Date toDate;

    /**
     * Constructor
     *
     * @param con the connection to the database
     * @param amount the amount of the reservation
     * @param customerID the customerID
     * @param licencePlate the license plate
     * @param fromDate the start date
     * @param toDate the end date
     * @throws SQLException if an error occurs
     */
    public ReserveDAO(final Connection con, float amount, long customerID, String licencePlate, Date fromDate, Date toDate) throws SQLException {
        super(con);
        this.amount = amount;
        this.customerID = customerID;
        this.licencePlate = licencePlate;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    /**
     * Reserve the car into database
     *
     * @throws SQLException if an error occurs
     */
    protected void  doAccess() throws SQLException {
        Reserve reserve = null;

        PreparedStatement transaction_stmnt = null;
        ResultSet rs = null;

        try {
            transaction_stmnt = c.prepareStatement(STATEMENT_TRANSACTION);

            transaction_stmnt.setFloat(1, amount);
            transaction_stmnt.setString(2, "confirmed");
            transaction_stmnt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            rs = transaction_stmnt.executeQuery();

            if (rs.next())
            {
                int paymentID = rs.getInt("Payment_ID");
                PreparedStatement reserveation_stmnt = null;
                ResultSet reserveation_rs = null;
                try {
                    reserveation_stmnt = c.prepareStatement(STATEMENT_RESERVATION);

                    reserveation_stmnt.setLong(1, customerID);
                    reserveation_stmnt.setInt(2, paymentID);
                    reserveation_stmnt.setString(3, licencePlate);
                    reserveation_stmnt.setDate(4, new java.sql.Date(fromDate.getTime()));
                    reserveation_stmnt.setDate(5, new java.sql.Date(toDate.getTime()));

                    reserveation_rs = reserveation_stmnt.executeQuery();

                    if (reserveation_rs.next())
                    {
                        reserve = new Reserve(reserveation_rs.getInt("Costumer_ID"),
                                reserveation_rs.getString("payment_ID"),
                                reserveation_rs.getString("LicensePlate"),
                                reserveation_rs.getDate("FromDate"),
                                reserveation_rs.getDate("ToDate"));

                    } else {
                        LOGGER.info("Reservation not accepted");
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(ReserveDAO.class.getName()).log(Level.SEVERE, "Error executing reservation query", ex);
                    throw ex; // Rethrow the exception to be handled by the caller
                } finally {
                    if (reserveation_rs != null) {
                        reserveation_rs.close();
                    }
                    if (reserveation_stmnt != null) {
                        reserveation_stmnt.close();
                    }
                }

            } else {
                LOGGER.info("Transaction not accepted");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReserveDAO.class.getName()).log(Level.SEVERE, "Error executing transaction query", ex);
            throw ex; // Rethrow the exception to be handled by the caller
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (transaction_stmnt != null) {
                transaction_stmnt.close();
            }
        }
        this.outputParam = reserve;
    }
}
