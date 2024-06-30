package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.RemoveRentalTransactionDAO;

//from professor's code
import service.LogContext;
import service.Actions;
import service.Message;

/**
 * Remove a rental transaction in the database
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public class RemoveRentalTransactionServlet extends AbstractDatabaseServlet{

    /**
     * Servlet to remove a Rental Transaction
     * 
     * @param req the HttpServletRequest
     * @param res the HttpServletResponse
     * @throws IOException if an error occurs during the servlet execution
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        //Logging
        LogContext.setIPAddress(req.getRemoteAddr());
		LogContext.setAction(Actions.REMOVE_RENTAL_TRANSACTION);
        
        //Param
        String payment_ID = null;

        //Models
        Message message = null;

        try{
            // Get all the parameters from the request
            payment_ID = req.getParameter("payment_ID");
            new RemoveRentalTransactionDAO(getConnection(), payment_ID).access();
        }
        catch(SQLException ex){
            //Here I have to manage all the error that can happen during the update
            //WORK IN PROGRESS!
            if("22023".equals(ex.getSQLState())){
                message = new Message("The argument is invalid: SQL exception");
                LOGGER.error("The argument is invalid: SQL exception", ex);
            }else{
                message = new Message("Unable to remove the rental transaction: SQL exception");
                LOGGER.error("Unable to remove the rental transaction: SQL exception", ex);
            }
        }
        catch(NullPointerException e){
            message = new Message("Unable to remove the rental transaction: null pointer exception");
            LOGGER.error("Unable to remove the rental transaction: null pointer exception", e);
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
			out.printf("<title>Remove Rental Transaction</title>%n");
			out.printf("</head>%n");
            //print the body
            out.printf("<body>%n");
			out.printf("<h1>Remove Rental Transaction</h1>%n");
			out.printf("<hr/>%n");
            if (message.isError()) {
                out.printf("<h2>An Error Occured!</h2>%n");
				out.printf("<ul>%n");
				out.printf("<li>error code: %s</li>%n", message.getErrorCode());
				out.printf("<li>message: %s</li>%n", message.getMessage());
				out.printf("<li>details: %s</li>%n", message.getErrorDetails());
				out.printf("</ul>%n");
            }else{
                out.printf("<h2>Rental Transaction Removed Correctly!</h2>%n");
                out.printf("<ul>%n");
                //Print the result
                out.printf("<li>Payment ID: %s</li>%n", payment_ID);
            }
            out.printf("</body>%n");
            //End of file
            out.printf("</html>%n");

            //Print to video
            out.flush();
            out.close();
        }
        catch(IOException ex){
            LOGGER.error("Unable to print the page: remove rental transaction!", ex);
        }
        finally{
            LogContext.removeIPAddress();
			LogContext.removeAction();
			LogContext.removeUser();
        }
    }
}
