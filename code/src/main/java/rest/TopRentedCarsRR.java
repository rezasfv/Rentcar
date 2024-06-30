package rest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.TopRentedCarDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.Actions;
import service.Car;
import service.Message;
import service.ResourceList;

/**
 * A REST resource for retrieve the top rented cars.
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public class TopRentedCarsRR extends AbstractRR{

    /**
     * Costructor
     * 
     * @param action the action to be performed
     * @param req the HttpServletRequest
     * @param res the HttpServletResponse
     * @param con the Connection
     */
    public TopRentedCarsRR(HttpServletRequest req, HttpServletResponse res, Connection con) {
        super(Actions.SEARCH_CARS_BY_PARAMS, req, res, con);
    }

    /**
     * Method for the REST service
     */
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void doServe() throws IOException {
        List<Car> cl = null;
        Message m = null;

        try {
            // creates a new DAO to access the database
            cl = new TopRentedCarDAO(con).access().getOutputParam();

            if (cl != null) {
                LOGGER.info("Top rented cars successfully retrieved!");
                res.setStatus(HttpServletResponse.SC_OK);
                new ResourceList(cl).toJSON(res.getOutputStream());
            }else{
                //something went wrong!
                LOGGER.error("Cannot retrieve the top rented cars!");

                m = new Message("Cannot retrieve the top rented cars: unexpected error.", "E5A1", null);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
            
        }
        catch (SQLException ex) {
            LOGGER.error("Cannot retrieve the top rented cars: unexpected database error.", ex);

            m = new Message("Cannot retrieve the top rented cars: unexpected database error.", "E5A1", ex.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
    }
}
