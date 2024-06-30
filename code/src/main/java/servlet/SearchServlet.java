package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import dao.SearchDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//Log service -> professor's code
import jakarta.servlet.http.HttpSession;
import service.LogContext;
import service.Actions;
//SQL tables
import service.Car;

/**
 * Search cars using the search form
 */
public class SearchServlet extends AbstractDatabaseServlet{

    /**
     * Search cars using the search form
     * 
     * @param req the HttpServletRequest
     * @param res the HttpServletResponse
     * @throws IOException if an error occurs during the servlet execution
     * @throws ServletException if an error occurs during the servlet execution
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        LogContext.setIPAddress(req.getRemoteAddr());
		LogContext.setAction(Actions.SEARCH_CARS_BY_PARAMS);
        System.out.println("Hii");
        //possible params
        float rentalrate = 0;
        String modelname = null;
        int capacity = 0;

        List<Car> cars = null;
        List<String> carsHTML = new ArrayList<>();
        /**
         * Implement the actual research in the database
         */
        try{
            //get ALL the parameters from the request
            //I check if the amount is a positive number
            System.out.println(req.getParameter("rentalrate"));
            if(req.getParameter("rentalrate") != null && !Objects.equals(req.getParameter("rentalrate"), "") && Float.parseFloat(req.getParameter("rentalrate")) >= 0){
                rentalrate = Float.parseFloat(String.format("%10.2f", Float.parseFloat(req.getParameter("rentalrate"))));
            }

            //I check the range of the capacity
            if(req.getParameter("capacity") != null && !Objects.equals(req.getParameter("capacity"), "") && Integer.parseInt(req.getParameter("capacity")) > 0 && Integer.parseInt(req.getParameter("capacity")) <= 10){
                capacity = Integer.parseInt(req.getParameter("capacity"));
            }
            //I check the length of the model name
            if(req.getParameter("modelname") != null && !Objects.equals(req.getParameter("modelname"), "") && (req.getParameter("modelname")).length() > 50){
                modelname = req.getParameter("modelname");
            }

            //I check if the category is one of the following
            String category = req.getParameter("category");
            String[] categories = {"small","medium","large","SUV",""};
            boolean isDefinedCategory = Arrays.asList(categories).contains(category);
            if(category != null && !isDefinedCategory){
                //throw new IllegalArgumentException("The category must be one of the following: small, medium, large, SUV!");
            }

            //I check if the current status is one of the following
            String currentstatus = req.getParameter("currentstatus");
            String[] statuses = {
                    "available",
                    "rented",
                    "maintenance",
                    ""
            };
            boolean isDefinedStatus = Arrays.asList(statuses).contains(currentstatus);
            if(currentstatus != null && !isDefinedStatus){
                //throw new IllegalArgumentException("The current status must be one of the following: available, rented, maintenance!");
            }
            //I check if the brand name is one of the following
            String brandname = req.getParameter("brandname");
            String[] brandNames = {
                    "Toyota",
                    "Honda",
                    "Ford",
                    "Chevrolet",
                    "Nissan",
                    "Volkswagen",
                    "BMW",
                    "Mercedes-Benz",
                    "Audi",
                    "Other",
                    ""
            };
            boolean isDefinedBrandName = Arrays.asList(brandNames).contains(brandname);
            if(brandname != null && !isDefinedBrandName){
                    //throw new IllegalArgumentException("The brand name must be one of the following: Toyota, Honda, Ford, Chevrolet, Nissan, Volkswagen, BMW, Mercedes-Benz, Audi, Other!");
            }

            //search for cars
            cars = new SearchDAO(getConnection(), rentalrate, modelname, brandname, capacity, category, currentstatus).access().getOutputParam();
            for (Car car : cars) {
                carsHTML.add(car.toString());
            }
            HttpSession session = req.getSession();
            session.setAttribute("carsHTML", carsHTML);
            req.setAttribute("rentalrate", rentalrate != 0 ? rentalrate : null);
            req.setAttribute("modelname", modelname);
            req.setAttribute("capacity", capacity != 0 ? capacity : null);
            req.setAttribute("category", category);
            req.setAttribute("currentstatus", currentstatus);
            req.setAttribute("brandname", brandname);


            //log
            LOGGER.info("Cars found successfully using params: %f, %s, %s, %d, %s, %b", rentalrate, modelname, brandname, capacity, category, currentstatus);

        } 
        catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } 
        catch(IllegalArgumentException e){
            e.printStackTrace();
            LOGGER.error("Unable to create the car: " + e.getMessage());
        }
        req.getRequestDispatcher("/jsp/search-car.jsp").forward(req, res);

    }
}
