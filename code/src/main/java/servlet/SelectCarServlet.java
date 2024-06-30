package servlet;

import dao.GetCarDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.Car;

import java.io.IOException;
import java.sql.Connection;

/**
 * Servlet to select a car
 *
 * @author Luca Pellegrini
 * @version 1.0
 * @since 1.0
 */
public class SelectCarServlet extends AbstractDatabaseServlet {

    /**
     * Servlet to select a car
     * 
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException if an error occurs during the servlet execution
     * @throws IOException if an error occurs during the servlet execution
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String licencePlate = request.getParameter("licencePlate");

        try (Connection con = getConnection()) {
            GetCarDAO getCar = new GetCarDAO(con, licencePlate);
            Car car = getCar.access().getOutputParam();
            if (car.getLicensePlate().equals(licencePlate)) {
                HttpSession session = request.getSession();
                session.setAttribute("car1", car);
                LOGGER.info("The car " + licencePlate + " has been selected");

                response.sendRedirect("jsp/reserve.jsp"); // Redirect to dates selection page
            } else {
                LOGGER.info("Invalid license plate");
                request.setAttribute("errorMessage", "Invalid license plate");
                request.getRequestDispatcher("jsp/home.jsp").forward(request, response);
            }

        } catch (Exception e) {
            LOGGER.error("Error during car selection", e);
            request.setAttribute("errorMessage", "Error during car selection");
            request.getRequestDispatcher("jsp/home.jsp").forward(request, response);
        }
    }

}