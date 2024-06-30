package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.SeeAllCarsDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.Actions;
import service.Car;
import service.LogContext;
import service.Message;

/**
 * Search all the cars
 *
 * @version 1.0
 * @since 1.0
 */
public class SeeAllCarsServlet extends AbstractDatabaseServlet {

    /**
     * Handles the GET request
     *
     * @param req the HttpServletRequest
     * @param res the HttpServletResponse
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.SEARCH_CARS_BY_PARAMS);

        Message message = null;
        List<Car> cars = null;

        try {
            // Search for cars
            cars = new SeeAllCarsDAO(getConnection()).access().getOutputParam();
            if (cars.isEmpty()) {
                message = new Message("No cars found in the database.", "", "");
            } else {
                message = new Message("Cars retrieved successfully.", "", "");
            }
        } catch (NullPointerException e) {
            message = new Message("The argument is null: null pointer exception", e.getMessage(), "E100");
            LOGGER.error("The argument is null: null pointer exception", e);
        } catch (SQLException e) {
            message = new Message("The argument is invalid: SQL exception", e.getMessage() + " - error SQL: " + e.getSQLState(), "E200");
            LOGGER.error("The argument is invalid: SQL exception", e);
        } catch (IllegalArgumentException e) {
            message = new Message("The argument is not valid: illegal argument exception", e.getMessage(), "E300");
            LOGGER.error("The argument is not valid: illegal argument exception", e);
        }


        try {
            req.setAttribute("cars", cars);
            req.setAttribute("message", message);

            HttpSession session = req.getSession();
            session.setAttribute("cars", cars);
            res.sendRedirect("jsp/allcars.jsp");
        } catch (IOException ex) {
            LOGGER.error("Unable to print the page: see all cars!", ex);
            throw ex;
        } finally {
            LogContext.removeIPAddress();
            LogContext.removeAction();
            LogContext.removeUser();
        }
    }
}
