package servlet;

import java.io.IOException;
import java.sql.SQLException;

import dao.UpdateCarDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.Actions;
import service.Car;
//from professor's code
import service.LogContext;
import service.Message;

/**
 * Update a car in the database
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public class UpdateCarServlet extends AbstractDatabaseServlet{

    /**
     * Servlet to update a Car
     * 
     * @param req the HttpServletRequest
     * @param res the HttpServletResponse
     * @throws IOException if an error occurs during the servlet execution
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        //Logging
        LogContext.setIPAddress(req.getRemoteAddr());
		LogContext.setAction(Actions.CREATE_CAR);

        //Params
        String license_plate = "";
        float rentalrate = 0;
        int capacity = 0;
        String modelname = "";
        String category = "";
        String currentstatus = "";
        String brandname = "";

        //Models
        Car car = null;
        Message message = null;

        try{
            // Get all the parameters from the request
            //I check if the license plate contains only letters and numbers
            if ((req.getParameter("license_plate")).matches("^[A-Za-z0-9]+$")){
                if((req.getParameter("license_plate")).length() > 20){
                    throw new IllegalArgumentException("The license plate must be less than 20 characters!");
                }else{
                    license_plate = req.getParameter("license_plate");
                }
            }else{
                throw new IllegalArgumentException("The license plate must contain only letters and numbers!");
            }
            //I check if the amount is a positive number
            if(Float.parseFloat(req.getParameter("rentalrate")) >= 0){
                rentalrate = Float.parseFloat(String.format("%10.2f", Float.parseFloat(req.getParameter("amount"))));
            }else{
                throw new IllegalArgumentException("The amount must be a positive number!");
            }
            //I check the range of the capacity
            if(Integer.parseInt(req.getParameter("capacity")) > 0 && Integer.parseInt(req.getParameter("capacity")) <= 10){
                capacity = Integer.parseInt(req.getParameter("capacity"));
            }else{
                throw new IllegalArgumentException("The capacity must be between 1 and 10!");
            }
            //I check the length of the model name
            if((req.getParameter("modelname")).length() > 50){
                throw new IllegalArgumentException("The model name must be less than 50 characters!");
            }else{
                modelname = req.getParameter("modelname");
            }
            //I check if the category is one of the following
            category = req.getParameter("category");
            switch (category) {
                case "small": break;
                case "medium": break;
                case "large": break;
                case "SUV": break;
                //None of the above
                default:
                    throw new IllegalArgumentException("The category must be one of the following: small, medium, large, SUV!");
            }
            //I check if the current status is one of the following
            currentstatus = req.getParameter("currentstatus");
            switch (currentstatus) {
                case "available": break;
                case "rented": break;
                case "maintenance": break;
                //None of the above
                default:
                    throw new IllegalArgumentException("The current status must be one of the following: available, rented, maintenance!");
            }
            //I check if the brand name is one of the following
            brandname = req.getParameter("brandname");
            switch (brandname) {
                case "Toyota": break;
                case "Honda": break;
                case "Ford": break;
                case "Chevrolet": break;
                case "Nissan": break;
                case "Volkswagen": break;
                case "BMW": break;
                case "Mercedes-Benz": break;
                case "Audi": break;
                case "Other": break;
                //None of the above
                default:
                    throw new IllegalArgumentException("The brand name must be one of the following: Toyota, Honda, Ford, Chevrolet, Nissan, Volkswagen, BMW, Mercedes-Benz, Audi, Other!");
            }
            //create a new car
            car = new Car(license_plate, rentalrate, capacity, modelname, category, currentstatus, brandname);
            new UpdateCarDAO(getConnection(), car).access();
            
        }
        catch(SQLException ex){
            //Here I have to manage all the error that can happen during the update
            //WORK IN PROGRESS!
            if ("23000".equals(ex.getSQLState())) {
                message = new Message("Constraint conflict: SQL exception");
                LOGGER.error("Constraint conflict: SQL exception", ex);
            }else if("42000".equals(ex.getSQLState())){
                message = new Message("Syntax error: SQL exception");
                LOGGER.error("Syntax error: SQL exception", ex);
            } else {
                message = new Message("Unable to update the car: SQL exception");
                LOGGER.error("Unable to update the car: SQL exception", ex);
            }
        }
        catch(NullPointerException e){
            message = new Message("Unable to update the car: null pointer exception");
            LOGGER.error("Unable to update the car: null pointer exception", e);
        }
        catch(IllegalArgumentException e){
            message = new Message("Unable to create the car: " + e.getMessage());
            LOGGER.error("Unable to create the car: " + e.getMessage());
        }
        
        /**
         * I try to print the page
         */
        try{

            if (car != null) {

                // stores the Customer and the message as a request attribute
                req.setAttribute("createdCar", car);
                req.setAttribute("message", message);

                req.getRequestDispatcher("/jsp/addupdate-car-result.jsp").forward(req, res);
            } else {
                req.setAttribute("error", "Registration failed.");
                req.getRequestDispatcher("/jsp/addupdate-car-result.jsp").forward(req, res);
            }
        }
        catch(IOException ex){
            LOGGER.error("Unable to print the page: create car!", ex);
            //I propagate the exception
            throw ex;
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } finally{
            LogContext.removeIPAddress();
            LogContext.removeAction();
            LogContext.removeUser();
        }
    }
}
