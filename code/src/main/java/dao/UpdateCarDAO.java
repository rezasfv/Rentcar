package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import service.Car;

@SuppressWarnings("rawtypes")
/**
 * Update a car in the database
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public class UpdateCarDAO extends BasicDAO{

    /**
     * Update a car in the database
     * 
     * @param c the connection to the database
     * @param car the car to be updated
     * @throws NullPointerException if the connection is null
     * @throws SQLException if the connection is not valid
     */
    public UpdateCarDAO(final Connection c, final Car car) throws NullPointerException, SQLException {
        super(c);
        if(car == null){
            LOGGER.error("The car cannot be null.");
            throw new NullPointerException("The car cannot be null.");
        }
        this.car = car;
    }

    @Override
    /**
     * Update a car in the database
     * 
     * @throws SQLException if an error occurs during the update
     */
    protected void doAccess() throws SQLException {
        
        PreparedStatement pstmt = null;
        try{
            if(car.getLicensePlate() == null){
                LOGGER.error("The license plate cannot be null.");
                throw new NullPointerException("The license plate cannot be null.");
            }
            pstmt = c.prepareStatement(statement);
            pstmt.setFloat(1, car.getRentalRate());
            pstmt.setInt(2, car.getCapacity());
            pstmt.setString(3, car.getModelName());
            pstmt.setString(4, car.getCategory());
            pstmt.setString(5, car.getCurrentStatus());
            pstmt.setString(6, car.getBrandName());
            pstmt.setString(7, car.getLicensePlate());

            //I esecute the query
			pstmt.execute();

            LOGGER.info("Car updated successfully with license plate: %s", car.getLicensePlate());
        }
        catch(NullPointerException ex){
            LOGGER.error("Unable to update the car because license plate is null!", ex);
            throw ex;
        }
        catch(SQLException e){
            LOGGER.error("Unable to create the car", e);
            throw e;
        }
        finally{
            if(pstmt != null){
                pstmt.close();
            }
        }
    }

    /**
     * The SQL statement to be executed (precompiled)
     */
    private final String statement = "UPDATE Car SET RentalRate = ?, Capacity = ?, ModelName = ?, Category = ?, CurrentStatus = ?, BrandName = ? WHERE LicensePlate = ?";

    /**
     * The car to update
     */
    private final Car car;
    
}
