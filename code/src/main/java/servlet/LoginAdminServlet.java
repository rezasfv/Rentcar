package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (isValidEmail(email) && isValidAdmin(email, password)) {
            response.sendRedirect("dashboard.jsp");
        } else {
            response.sendRedirect("adminLogin.jsp?error=Invalid login credentials");
        }
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailPattern);
    }

    private boolean isValidAdmin(String email, String password) {
        // Validate admin credentials (this is just a simple example)
        return "admin@example.com".equals(email) && "admin123".equals(password);
    }
}