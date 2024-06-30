package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
//from professor's code
import service.Actions;
import service.LogContext;

/**
 * Servlet to logout an admin
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public class AdminLogoutServlet extends AbstractDatabaseServlet{
    
    @Override
    /**
     * Servlet to logout an admin
     * 
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * 
     * @throws ServletException if an error occurs during the servlet execution
     * @throws IOException if an error occurs during the servlet execution
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            //Logging
            LogContext.setIPAddress(request.getRemoteAddr());
            LogContext.setAction(Actions.ADMIN_LOGOUT);

            // I invalidate the session
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
        }
        catch(IllegalStateException ex){
            LOGGER.error("Session already invalidated!");
        }
        //I redirect the user to the home page
        response.sendRedirect("home.jsp");
    }
}
