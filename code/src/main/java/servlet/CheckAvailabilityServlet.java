package servlet;

import dao.CheckAvailabilityDAO;
import dao.GetCarDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.Car;
import service.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


/**
 * Servlet to Check Availability of a car
 *
 * @author Luca Pellegrini
 * @version 1.0
 * @since 1.0
 */
public class CheckAvailabilityServlet extends AbstractDatabaseServlet {


    /**
     * Servlet Get method to check availability of a car
     *
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException if an error occurs during the servlet execution
     * @throws IOException if an error occurs during the servlet execution
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user1");
        if(user == null){
            session.setAttribute("afterLoginPath", request.getContextPath()+"/check-availability?licencePlate="+request.getParameter("licencePlate"));
            response.sendRedirect(request.getContextPath()+"/login");
            return;
        }

        try (Connection con = getConnection()) {
            Car car = new GetCarDAO(con, request.getParameter("licencePlate")).access().getOutputParam();
            request.setAttribute("car1", car);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.getRequestDispatcher("jsp/check-availability.jsp").forward(request, response);
    }
    /**
     * Servlet Post method to check availability of a car
     * 
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException if an error occurs during the servlet execution
     * @throws IOException if an error occurs during the servlet execution
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String licencePlate = null ;
        long userID = -1;
        java.util.Date fromDate = null;
        java.util.Date toDate = null;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            fromDate = df.parse(request.getParameter("formDate"));
            toDate = df.parse(request.getParameter("toDate"));
        } catch (Exception e) {
            LOGGER.error("Error during data parsing" + e.getMessage());
            request.setAttribute("errorMessage", "Error parsing dates");
            request.getRequestDispatcher("jsp/check-availability.jsp").forward(request, response);
        }

        if (fromDate == null || toDate == null) {
            // Redirect back page after null dates
            LOGGER.info("Dates are null");
            request.setAttribute("message", "Dates are wrong");
            request.getRequestDispatcher("jsp/check-availability.jsp").forward(request, response);
        }

        else if (fromDate.after(toDate)) {
            // Redirect back page after swap dates
            LOGGER.info("Dates are swapped");
            request.setAttribute("message", "Dates are swapped");
            request.getRequestDispatcher("jsp/check-availability.jsp").forward(request, response);
        }

        licencePlate = request.getParameter("licensePlate");

        User user = (User) request.getSession().getAttribute("user1");

        try (Connection con1 = getConnection()) {
            CheckAvailabilityDAO checkAvailability = new CheckAvailabilityDAO(con1, licencePlate, fromDate, toDate);
            Boolean isAvailable = checkAvailability.access().getOutputParam();

            if (isAvailable)
            {
                LOGGER.info("Car " + licencePlate + " is available");
                request.getSession().setAttribute("availableLicencePlate",licencePlate);
                request.getSession().setAttribute("availableFromDate",fromDate);
                request.getSession().setAttribute("availableToDate",toDate);
                request.getSession().setAttribute("user1", user);
                request.getSession().setAttribute("message", "Car " + licencePlate + " is available in the selected period");
                response.sendRedirect(request.getContextPath()+"/reserve");

            } else {
                // Redirect back page after no availability
                LOGGER.info("Car " + licencePlate + " not available");
                request.getSession().setAttribute("licencePlate",licencePlate);
                request.setAttribute("message", "Car " + licencePlate + " not available in the selected period");
                request.getRequestDispatcher("jsp/check-availability.jsp").forward(request, response);
            }

        } catch (Exception e) {
            LOGGER.error("Error during reservation" + e.getMessage());
            request.setAttribute("errorMessage", "Error during reservation");
            request.getRequestDispatcher("jsp/check-availability.jsp").forward(request, response);
        }
    }

}