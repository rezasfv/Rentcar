package servlet;

import dao.EditLicenseDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.message.StringFormattedMessage;
import service.Actions;
import service.License;
import service.LogContext;
import service.Message;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Creates a new License into the database.
 * 
 * @version 1.0
 * @since 1.0
 */
public class CreateLicenseServlet extends AbstractDatabaseServlet {

    /**
     * Creates a new License into the database.
     *
     * @param req the HTTP request from the client.
     * @param res the HTTP response from the server.
     *
     * @throws ServletException if any error occurs while executing the servlet.
     * @throws IOException if any error occurs in the client/server communication.
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.CREATE_License);

        // request parameters
        String licenseNumber = null;
        String typeAccess = null;
        String expirationDate = null;
        String issuingDate = null;

        // model
        License l= null;
        Message m = null;

        try {


            // retrieves the request parameters
            if((req.getParameter("licenseNumber")).length() > 20){
                throw new IllegalArgumentException("The license number must be less than 20 characters!");
            }else{
                licenseNumber = req.getParameter("licenseNumber");
            }
            typeAccess = req.getParameter("typeAccess");
            switch (typeAccess) {
                case "B": break;
                case "B1": break;
                //None of the above
                default:
                    throw new IllegalArgumentException("The type of access must be B or B1");
            }
            expirationDate = req.getParameter("expirationDate");
            issuingDate = req.getParameter("issuingDate");

            // set the badge of the License as the resource in the log context
            // at this point we know it is a valid integer
            LogContext.setResource(req.getParameter("licenseNumber"));
            // creates a new License from the request parameters
            l = new License(licenseNumber,typeAccess,expirationDate,issuingDate);

            // creates a new object for accessing the database and stores the License
            new EditLicenseDAO(getConnection(), l).access();

            m = new Message("license successfully created.");

            LOGGER.info("license %d successfully created in the database.", licenseNumber);

        } catch (NumberFormatException ex) {
            m = new Message(
                    "Cannot create the license. Invalid input parameters",
                    "E100", ex.getMessage());

            LOGGER.error(
                    "Cannot create the license. Invalid input parameters",
                    ex);
        } catch (SQLException ex) {
            if ("23505".equals(ex.getSQLState())) {
                m = new Message(("Cannot create the license: licenseNumber already exists."), "E300",
                        ex.getMessage());

                LOGGER.error(
                        new StringFormattedMessage("Cannot create the license: licenseNumber %d already exists.", licenseNumber),
                        ex);
            } else {
                m = new Message("Cannot create the license: unexpected error while accessing the database.", "E200",
                        ex.getMessage());

                LOGGER.error("Cannot create the license: unexpected error while accessing the database.", ex);
            }
        } catch (IllegalArgumentException ex){
            m = new Message("Cannot create the license: " + ex.getMessage());
            LOGGER.error("Cannot create the license: " + ex.getMessage());
        }

        try {
            // stores the CustLicenseomer and the message as a request attribute
            req.setAttribute("license", l);
            req.setAttribute("message", m);
            System.out.println("try");
            // forwards the control to the create-License-result JSP
            req.getRequestDispatcher("/jsp/create-license-result.jsp").forward(req, res);
        } catch(Exception ex) {
            LOGGER.error(new StringFormattedMessage("Unable to send response when creating license %d.", licenseNumber), ex);
            throw ex;
        } finally {
            LogContext.removeIPAddress();
            LogContext.removeAction();
            LogContext.removeResource();
        }

    }
}
