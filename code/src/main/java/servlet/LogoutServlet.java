package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.User;

import java.io.IOException;


/**
 * Servlet to LogoutServlet a user
 * 
 * @version 1.0
 * @since 1.0
 */
public class LogoutServlet extends AbstractDatabaseServlet {

    /**
     * Servlet for GET method to Logout
     *
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException if an error occurs during the servlet execution
     * @throws IOException if an error occurs during the servlet execution
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user1");
        session.setAttribute("user1",null);

        LOGGER.info(String.format("the user with Id [%d] attempted to logout", user.getCostumer_ID()));
        response.sendRedirect(request.getContextPath() + "/");

    }

}


