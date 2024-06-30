package servlet;/*
 * Copyright 2018-2023 University of Padua, Italy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import dao.LoginDAO;
import dao.RegisterUserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.message.StringFormattedMessage;
import service.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Creates a new Customer into the database.
 * 
 * @version 1.0
 * @since 1.0
 */
public final class RegistrationServlet extends AbstractDatabaseServlet {
    /**
     * Servlet for GET method to create-customer-form (Register page)
     *
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException if an error occurs during the servlet execution
     * @throws IOException if an error occurs during the servlet execution
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/create-customer-form.jsp").forward(request, response);
    }

    /**
     * Creates a new Customer into the database.
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
        LogContext.setAction(Actions.CREATE_Customer);
        // request parameters
        String email = null;
        String firstName = null;
        String lastName = null;
        String dateOfBirth = null;
        String addressCostumer= null;
        String phone= null;
        String nationality= null;
        String passwordCostumer= null;
        String licenseNumber= null;
        String typeAccess= null;
        String expirationDate= null;
        String issuingDate= null;
        // model
        User e = null;
        License l = null;
        Message m = null;

        try {


            // retrieves the request parameters
            email = req.getParameter("email");
            firstName = req.getParameter("firstName");
            lastName = req.getParameter("lastName");
            dateOfBirth = req.getParameter("dateOfBirth");
            addressCostumer= req.getParameter("addressCustomer");
            phone= req.getParameter("phone");
            nationality= req.getParameter("nationality");
            passwordCostumer= req.getParameter("passwordCustomer");
            licenseNumber= req.getParameter("licenseNumber");


            // at this point we know it is a valid integer
            LogContext.setResource(req.getParameter("email"));
            // creates a new Customer from the request parameters
            e = new User(email,firstName,lastName,dateOfBirth,addressCostumer,phone,nationality,passwordCostumer,licenseNumber);
            l = new License( licenseNumber,typeAccess,expirationDate,issuingDate);
            // creates a new object for accessing the database and stores the Customer
            new RegisterUserDAO(getConnection(), e).access();

            m = new Message("Customer successfully created.");

            LOGGER.info("Customer %d successfully created in the database.", email);

        } catch (NumberFormatException ex) {
            m = new Message(
                    "Cannot create the Customer. Invalid input parameters",
                    "E100", ex.getMessage());

            LOGGER.error(
                    "Cannot create the Customer. Invalid input parameters",
                    ex);
        } catch (SQLException ex) {
            if ("23505".equals(ex.getSQLState())) {
                m = new Message(String.format("Cannot create the Customer: Customer %d already exists.", email), "E300",
                        ex.getMessage());
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                LOGGER.error(
                        new StringFormattedMessage("Cannot create the Customer: Customer %d already exists.", email),
                        ex);
            } else {
                m = new Message("Cannot create the Customer: unexpected error while accessing the database.", "E200",
                        ex.getMessage());
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                LOGGER.error("Cannot create the Customer: unexpected error while accessing the database.", ex);
            }
        }

        try {
            LoginDAO loginDAO = new LoginDAO(getConnection(), email);
            User user = loginDAO.access().getOutputParam();
            if (user != null && user.getEmail() != null) {

            // stores the Customer and the message as a request attribute
            req.setAttribute("user1", user);
            req.setAttribute("message", m);
            req.setAttribute("license1", l);
            HttpSession session1 = req.getSession();
            session1.setAttribute("license", l);

            HttpSession session = req.getSession();
            session.setAttribute("user1", e);
            res.sendRedirect(req.getContextPath()+"/dashboard");
            } else {
                req.setAttribute("error", "Registration failed.");
                req.getRequestDispatcher("jsp/register.jsp").forward(req, res);
            }
        } catch(Exception ex) {
            LOGGER.error(new StringFormattedMessage("Unable to send response when creating Customer %d.", email), ex);
            try {
                throw ex;
            } catch (SQLException exc) {
                throw new RuntimeException(exc);
            }
        } finally {
            LogContext.removeIPAddress();
            LogContext.removeAction();
            LogContext.removeResource();
        }

    }

}
