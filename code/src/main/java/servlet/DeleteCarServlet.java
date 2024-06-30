package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import dao.DeleteCarDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.Actions;
//from professor's code
import service.LogContext;
import service.Message;

/**
 * Delete a car in the database
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public class DeleteCarServlet extends AbstractDatabaseServlet{

    /**
     * Servlet to delete a Car
     * 
     * @param req the HttpServletRequest
     * @param res the HttpServletResponse
     * @throws IOException if an error occurs during the servlet execution
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        //Logging
        LogContext.setIPAddress(req.getRemoteAddr());
		LogContext.setAction(Actions.CREATE_CAR);

        //Params
        String license_plate = "";

        //Models
        Message message = null;

        try{
            // Get all the parameters from the request
            license_plate = req.getParameter("license_plate");
            new DeleteCarDAO(getConnection(), license_plate).access();
            
        }
        catch(SQLException ex){
            //Here I have to manage all the error that can happen during the update
            //WORK IN PROGRESS!
            if("22023".equals(ex.getSQLState())){
                message = new Message("The argument is invalid: SQL exception");
                LOGGER.error("The argument is invalid: SQL exception", ex);
            }else{
                message = new Message("Unable to delete the car: SQL exception");
                LOGGER.error("Unable to delete the car: SQL exception", ex);
            }
        }
        catch(NullPointerException e){
            message = new Message("Unable to delete the car: null pointer exception");
            LOGGER.error("Unable to delete the car: null pointer exception", e);
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
			out.printf("<title>Delete Car</title>%n");
			out.printf("</head>%n");
            //print the body
            out.printf("<body>%n");
			out.printf("<h1>Delete Car</h1>%n");
			out.printf("<hr/>%n");
            if (message.isError()) {
                out.printf("<h2>An Error Occured!</h2>%n");
				out.printf("<ul>%n");
				out.printf("<li>error code: %s</li>%n", message.getErrorCode());
				out.printf("<li>message: %s</li>%n", message.getMessage());
				out.printf("<li>details: %s</li>%n", message.getErrorDetails());
				out.printf("</ul>%n");
            }else{
                out.printf("<h2>Car Deleted Correctly!</h2>%n");
                out.printf("<ul>%n");
                //Print the result
				out.printf("<li>License Plate: %s</li>%n", license_plate);
				out.printf("</ul>%n");
            }
            out.printf("</body>%n");
            //End of file
            out.printf("</html>%n");

            //Print to video
            out.flush();
            out.close();
        }
        catch(IOException ex){
            LOGGER.error("Unable to print the page: delete car!", ex);
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
