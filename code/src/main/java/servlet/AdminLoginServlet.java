package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Base64;

import dao.AdminLoginDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//from professor's code
import service.Actions;
import service.Admin;
import service.Message;
import service.LogContext;

public class AdminLoginServlet extends AbstractDatabaseServlet{

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        //Logging
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.ADMIN_LOGIN);

        //Params
        String email = "";
        String password = "";

        //Models
        Admin admin = null;
        Message message = null;

        try{
            // Get all the parameters from the request -> they are in BASE64
            // Get all the parameters from the request -> they are in BASE64
            String base64Data = req.getParameter("data");
            byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
            String decodedData = new String(decodedBytes, StandardCharsets.UTF_8);
            String[] credentials = decodedData.split(":");
            email = credentials[0];
            password = credentials[1];

            //create a new admin
            admin = new Admin(email, password);
            new AdminLoginDAO(getConnection(), admin).access();

        }
        catch(SQLException ex){
            if ("23505".equals(ex.getSQLState())) {
                message = new Message("The admin already exists in the database.");
            }
            else {
                message = new Message("An error occurred while creating the admin.");
            }
        }
        catch(NullPointerException ex){
            message = new Message("An error occurred while creating the admin.");
        }

        /**
         * I try to print the page
         */
        try{
            //print the page
            res.setContentType("text/html; charset=UTF-8");

            PrintWriter out = res.getWriter();
            
            out.printf("<!DOCTYPE html>%n");
            out.printf("<html lang=\"en\">%n");
            //print the head
			out.printf("<head>%n");
			out.printf("<meta charset=\"utf-8\">%n");
			out.printf("<title>Admin Login</title>%n");
			out.printf("</head>%n");
            //print the body
            out.printf("<body>%n");
			out.printf("<h1>Admin Login</h1>%n");
			out.printf("<hr/>%n");
            if (message.isError()) {
                out.printf("<h2>An Error Occured!</h2>%n");
				out.printf("<ul>%n");
				out.printf("<li>error code: %s</li>%n", message.getErrorCode());
				out.printf("<li>message: %s</li>%n", message.getMessage());
				out.printf("<li>details: %s</li>%n", message.getErrorDetails());
				out.printf("</ul>%n");
            }else{
                out.printf("<h2>Log in success!</h2>%n");
            }
            out.printf("</body>%n");
            //End of file
            out.printf("</html>%n");

            //Print to video
            out.flush();
            out.close();
        }
        catch(IOException ex){
            LOGGER.error("Unable to print the page: create car!", ex);
            //I propagate the exception
            throw ex;
        }
        finally{
            LogContext.removeIPAddress();
			LogContext.removeAction();
			LogContext.removeUser();
        }
    }
}
