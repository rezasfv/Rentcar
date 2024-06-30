package servlet;


import dao.LoginDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import service.User;

import java.io.IOException;
import java.sql.Connection;

/**
 * Servlet to login a user
 * 
 * @version 1.0
 * @since 1.0
 */
public class LoginServlet  extends AbstractDatabaseServlet {

    /**
     * Servlet for GET method to login
     *
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException if an error occurs during the servlet execution
     * @throws IOException if an error occurs during the servlet execution
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
    }

    /**
     * Servlet to login a user
     * 
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException if an error occurs during the servlet execution
     * @throws IOException if an error occurs during the servlet execution
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (Connection con = getConnection()) {
            LoginDAO loginDAO = new LoginDAO(con, email);
            User user = loginDAO.access().getOutputParam();
            String hashedPassword = user.getPasswordCostumer() == null  ? "" : user.getPasswordCostumer();

            if (user.getEmail() != null && BCrypt.checkpw(password, hashedPassword )) {
                HttpSession session = request.getSession();
                session.setAttribute("user1", user);
                String afterLoginPath =(String) session.getAttribute("afterLoginPath");
                if(afterLoginPath == null){
                    response.sendRedirect(request.getContextPath()+"/dashboard");// Redirect to home page after successful login
                    return;
                }
                session.setAttribute("afterLoginPath",null);
                response.sendRedirect(afterLoginPath);
                // Redirect to home page after successful login
            } else {
                request.setAttribute("errorMessage", "Invalid email or password");
                request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error during login");
            request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
        }
    }

}


