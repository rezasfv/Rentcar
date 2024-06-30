package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.TopRentedCarDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.Actions;
import service.Car;
import service.LogContext;
import service.Message;

/**
 * Select the top most rented car
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public class TopRentedCarServlet extends AbstractDatabaseServlet{

    /**
     * Get the top 5 most rented cars
     * @param req the HttpServletRequest
     * @param res the HttpServletResponse
     * @throws IOException if an error occurs during the servlet execution
     * @throws SQLException if an error occurs during the servlet execution
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.SEARCH_CARS_BY_PARAMS);

        Message message = null;
        List<Car> cars = null;

        try {
            // Search for cars
            cars = new TopRentedCarDAO(getConnection()).access().getOutputParam();
            if (cars.isEmpty()) {
                message = new Message("No reservation found in the database.", "", "");
            } else {
                message = new Message("Top cars retrieved successfully.", "", "");
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

        //Return the results (5)
        req.getRequestDispatcher("/jsp/home.jsp").forward(req, res);
    }
}
