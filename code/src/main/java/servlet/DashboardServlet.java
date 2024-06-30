package servlet;

import dao.ReserveByUserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.Reserve;
import service.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Servlet to DashboardServlet a user
 * 
 * @version 1.0
 * @since 1.0
 */
public class DashboardServlet extends AbstractDatabaseServlet {

    /**
     * Servlet for GET method to dashboard
     *
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException if an error occurs during the servlet execution
     * @throws IOException if an error occurs during the servlet execution
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user1");
        if(user !=null){
            LOGGER.info(String.format("the user with Id [%d] attempted to access dashboard", user.getCostumer_ID()));
            try (Connection con = getConnection()) {
                List<String> reservationCards = new ArrayList<>();
                List<Reserve> reservations = new ReserveByUserDAO(getConnection(), user).access().getOutputParam();
                for (Reserve reservation : reservations) {
                    reservationCards.add(reservation.toString());
                }
                session.setAttribute("reservationsHTML", reservationCards);

                response.sendRedirect("jsp/dashboard.jsp");
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }else{
            response.sendRedirect(request.getContextPath() + "/login");
        }

    }

}


