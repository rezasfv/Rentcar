package service;

import java.sql.Date;

/**
 * Rappresent a rental transaction object
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public class RentalTransaction {

    // Attributes
    private final String payment_ID; // Stripe payment id is string
    private final float amount;
    private final String paymentstatus;
    private final Date issuedate;
    private final Date fromdate;
    private final Date todate;
    private final String costumer_id;
    private final String license_plate;

    /**
     * Constructor
     * 
     * @param payment_ID the payment ID
     * @param amount the amount
     * @param paymentstatus the payment status
     * @param issuedate the issue date
     * @param fromdate the starting date of the rental
     * @param todate the ending date of the rental
     * @param costumer_id the costumer ID
     * @param license_plate the license plate
     */
    public RentalTransaction(String payment_ID, float amount, String paymentstatus, Date issuedate, Date fromdate, Date todate, String costumer_id, String license_plate) {
        this.payment_ID = payment_ID;
        this.amount = amount;
        this.paymentstatus = paymentstatus;
        this.issuedate = issuedate;
        this.fromdate = fromdate;
        this.todate = todate;
        this.costumer_id = costumer_id;
        this.license_plate = license_plate;
    }

    /**
     * Get the payment ID of the rental transaction
     * @return the payment ID
     */
    public String getPayment_ID() {
        return payment_ID;
    }

    /**
     * Get the amount of the rental transaction
     * @return the amount
     */
    public float getAmount() {
        return amount;
    }

    /**
     * Get the payment status of the rental transaction
     * @return the payment status
     */
    public String getPaymentstatus() {
        return paymentstatus;
    }

    /**
     * Get the issue date of the rental transaction
     * @return the issue date
     */
    public Date getIssuedate() {
        return issuedate;
    }

    /**
     * Get the from date of the rental transaction
     * @return the from date
     */
    public Date getFromdate() {
        return fromdate;
    }

    /**
     * Get the to date of the rental transaction
     * @return the to date
     */
    public Date getTodate() {
        return todate;
    }

    /**
     * Get the costumer ID of the rental transaction
     * @return the costumer ID
     */
    public String getCostumer_id() {
        return costumer_id;
    }

    /**
     * Get the license plate of the rental transaction
     * @return the license plate
     */
    public String getLicense_plate() {
        return license_plate;
    }
}
