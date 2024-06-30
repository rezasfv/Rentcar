package servlet;

import java.io.IOException;
import java.sql.Date;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.UpdateRentalTransactionDAO;
import service.RentalTransaction;

//from professor's code
import service.LogContext;
import service.Actions;
import service.Message;

/**
 * Update a rental transaction in the database
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public class UpdateRentalTransactionServlet extends AbstractDatabaseServlet{

    /**
     * Servlet to update a Rental Transaction
     * 
     * @param req the HttpServletRequest
     * @param res the HttpServletResponse
     * @throws IOException if an error occurs during the servlet execution
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        //Logging
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.UPDATE_RENTAL_TRANSACTION);

        //Param
        String payment_ID;
        float amount;
        String paymentstatus;
        Date issuedate;
        Date fromdate;
        Date todate;
        String costumer_id;
        String license_plate;
    
        //Models
        RentalTransaction rentalTransaction = null;
        Message message = null;

        try{
            // Get all the parameters from the request
            payment_ID = req.getParameter("payment_ID");
            //I check if the amount is a positive number
            if(Float.parseFloat(req.getParameter("amount")) >= 0){
                amount = Float.parseFloat(String.format("%10.2f", Float.parseFloat(req.getParameter("amount"))));
            }else{
                throw new IllegalArgumentException("The amount must be a positive number!");
            }
            //I check if the payment status is one of the following 
            paymentstatus = req.getParameter("paymentstatus");
            switch (paymentstatus) {
                case "pending": break;
                case "confirmed": break;
                case "cancelled": break;                  
                //None of the above
                default:
                    throw new IllegalArgumentException("The payment status must be one of the following: pending, confirmed, cancelled!");
            }
            issuedate = Date.valueOf(req.getParameter("issuedate"));
            if ((Date.valueOf(req.getParameter("fromdate"))).before(Date.valueOf(req.getParameter("todate")))) {
                fromdate = Date.valueOf(req.getParameter("fromdate"));
                todate = Date.valueOf(req.getParameter("todate"));
            } else {
                throw new IllegalArgumentException("From date must be before to date!");
            }
            costumer_id = req.getParameter("costumer_id");
            //I check if the license plate contains only letters and numbers
            if ((req.getParameter("license_plate")).matches("^[A-Za-z0-9]+$")){
                if((req.getParameter("license_plate")).length() > 20){
                    throw new IllegalArgumentException("The license plate must be less than 20 characters!");
                }else{
                    license_plate = req.getParameter("license_plate");
                }
            }else{
                throw new IllegalArgumentException("The license plate must contain only letters and numbers!");
            }

            //create a new rental transaction
            rentalTransaction = new RentalTransaction(payment_ID, amount, paymentstatus, issuedate, fromdate, todate, costumer_id, license_plate);
            new UpdateRentalTransactionDAO(getConnection(), rentalTransaction).access();
        }
        catch(SQLException ex){
            //Here I have to manage all the error that can happen during the update
            //WORK IN PROGRESS!
            if ("23000".equals(ex.getSQLState())) {
                message = new Message("Constraint conflict: SQL exception");
                LOGGER.error("Constraint conflict: SQL exception", ex);
            }else if("42000".equals(ex.getSQLState())){
                message = new Message("Syntax error: SQL exception");
                LOGGER.error("Syntax error: SQL exception", ex);
            }else{
                message = new Message("Unable to update the rental transaction: SQL exception");
                LOGGER.error("Unable to update the rental transaction: SQL exception", ex);
            }
        }
        catch(NullPointerException e){
            message = new Message("Unable to update the rental transaction: null pointer exception");
            LOGGER.error("Unable to update the rental transaction: null pointer exception", e);
        }

        /**
         * I try to print the page
         */
        try{
            //print the page
            res.setContentType("text/html; charset=UTF-8");

            PrintWriter out = res.getWriter();

            out.printf("<!DOCTYPE html>%n");
            out.printf("<html lang=\"en\">%n");
            //print the head
			out.printf("<head>%n");
			out.printf("<meta charset=\"utf-8\">%n");
			out.printf("<title>Update Rental Transaction</title>%n");
			out.printf("</head>%n");
            //print the body
            out.printf("<body>%n");
			out.printf("<h1>Update Rental Transaction</h1>%n");
			out.printf("<hr/>%n");
            if (message.isError()) {
                out.printf("<h2>An Error Occured!</h2>%n");
				out.printf("<ul>%n");
				out.printf("<li>error code: %s</li>%n", message.getErrorCode());
				out.printf("<li>message: %s</li>%n", message.getMessage());
				out.printf("<li>details: %s</li>%n", message.getErrorDetails());
				out.printf("</ul>%n");
            }else{
                out.printf("<h2>Rental Transaction Updated Correctly!</h2>%n");
                out.printf("<ul>%n");
                //Print the result
                out.printf("<li>Payment ID: %s</li>%n", rentalTransaction.getPayment_ID());
                out.printf("<li>Amount: %s</li>%n", rentalTransaction.getAmount());
                out.printf("<li>Payment Status: %s</li>%n", rentalTransaction.getPaymentstatus());
                out.printf("<li>Issue Date: %s</li>%n", rentalTransaction.getIssuedate());
                out.printf("<li>From Date: %s</li>%n", rentalTransaction.getFromdate());
                out.printf("<li>To Date: %s</li>%n", rentalTransaction.getTodate());
                out.printf("<li>Costumer ID: %s</li>%n", rentalTransaction.getCostumer_id());
                out.printf("<li>License Plate: %s</li>%n", rentalTransaction.getLicense_plate());
            }
            out.printf("</body>%n");
            //End of file
            out.printf("</html>%n");

            //Print to video
            out.flush();
            out.close();
        }
        catch(IOException ex){
            LOGGER.error("Unable to print the page: update rental transaction!", ex);
            //I propagate the exception
            throw ex;
        }
        finally{
            LogContext.removeIPAddress();
			LogContext.removeAction();
			LogContext.removeUser();
        }
    }
}
