package servlet;

import dao.UpdateUserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.message.StringFormattedMessage;
import service.*;

import java.io.IOException;
import java.sql.SQLException;

public class UpdateCustomerServlet extends AbstractDatabaseServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.Update_Customer);


        // model
        User updatedUser =null;
        Message m = null;

        try {
            // Retrieve the updated User information from the request parameters
            String email = req.getParameter("email");
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String dateOfBirth = req.getParameter("dateOfBirth");
            String addressCostumer = req.getParameter("addressCustomer");
            String phone = req.getParameter("phone");
            String nationality = req.getParameter("nationality");
            String licenseNumber = req.getParameter("licenseNumber");

            // at this point we know it is a valid integer
            LogContext.setResource(req.getParameter("email"));
            updatedUser = new User(email, firstName,  lastName, dateOfBirth, addressCostumer, phone, nationality, licenseNumber);


            new UpdateUserDAO(getConnection(), updatedUser).access();



            m = new Message("User successfully updated.");

            LOGGER.info("User %d successfully updated in the database.", email);


        } catch (NumberFormatException ex) {
            m = new Message(
                    "Cannot create the User. Invalid input parameters",
                    "E100", ex.getMessage());

            LOGGER.error(
                    "Cannot create the User. Invalid input parameters",
                    ex);
        } catch (SQLException ex) {
            if ("23505".equals(ex.getSQLState())) {
                m = new Message(String.format("Cannot create the User: User already exists."), "E300",
                        ex.getMessage());

                LOGGER.error(
                        new StringFormattedMessage("Cannot create the User: User already exists."),
                        ex);
            } else {
                m = new Message("Cannot create the User: unexpected error while accessing the database.", "E200",
                        ex.getMessage());

                LOGGER.error("Cannot create the User: unexpected error while accessing the database.", ex);
            }
        }


        try {

            if (updatedUser != null && updatedUser.getEmail() != null) {

                // stores the Customer and the message as a request attribute
                req.setAttribute("user", updatedUser);
                req.setAttribute("message", m);

                HttpSession session = req.getSession();
                session.setAttribute("user1", updatedUser);
                // forwards the control to the create-Customer-result JSP
                res.sendRedirect("jsp/dashboard.jsp");
            } else {
                req.setAttribute("error", "Registration failed.");
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);
            }
        } catch(Exception ex) {
            LOGGER.error(new StringFormattedMessage("Unable to send response when updated User."), ex);
            throw ex;
        } finally {
            LogContext.removeIPAddress();
            LogContext.removeAction();
            LogContext.removeResource();
        }
    }
}
