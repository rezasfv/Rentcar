package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.Car;

/**
 * This class is used to get the top 6 most rented cars
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public class TopRentedCarDAO extends BasicDAO<List<Car>>{

    /**
     * Constructor
     * @param c the connection to the database
     * @throws NullPointerException if the connection is null
     * @throws SQLException if the connection is not valid
     */
    public TopRentedCarDAO(Connection c) throws NullPointerException, SQLException {
        super(c);
    }

    /**
     * Get the top 6 most rented cars
     * 
     * @throws SQLException if an error occurs during the query
     * @throws Exception if an error occurs during the query
     */
    @Override
    protected void doAccess() throws SQLException, Exception {
        PreparedStatement reservation_pstmt = null;
        PreparedStatement reservedcar_pstmt = null;
        ResultSet expResultSet = null;
        ResultSet carResultSet = null;
        //where I store the cars informations
        final List<Car> cars = new ArrayList<>();
        int count = 6;
        try {
            //I get all the license plate from reserve
            reservation_pstmt = c.prepareStatement(SQLQuery_CountLicensePlates);
            expResultSet = reservation_pstmt.executeQuery();

            LOGGER.info("Getting all the reservation from the database");

            //I get the top 6 most reserved cars
            while (expResultSet.next() && count > 0){
                //Now I have to get all the informations about the cars that have been reserved
                try {
                    reservedcar_pstmt = c.prepareStatement(SQLQuery_ReservedCars);
                    reservedcar_pstmt.setString(1, expResultSet.getString("LicensePlate"));
                    //I get one car at time
                    carResultSet = reservedcar_pstmt.executeQuery();
                    if(carResultSet.next()){
                        cars.add(new Car(carResultSet.getString("LicensePlate"),
                                        carResultSet.getFloat("RentalRate"),
                                        carResultSet.getInt("Capacity"),
                                        carResultSet.getString("ModelName"),
                                        carResultSet.getString("Category"),
                                        carResultSet.getString("CurrentStatus"),
                                        carResultSet.getString("BrandName")));
                        //I've found a car!
                        count--;
                    }
                                        
                    LOGGER.info("The car with license plate: %s has been updated to available", expResultSet.getString("LicensePlate"));
                    
                    //I clear the var
                    carResultSet = null;
                } 
                catch (SQLException e){
                    LOGGER.error("Unable to get informations about this car: " + expResultSet.getString("LicensePlate"));
                } 
                finally {
                    if (reservedcar_pstmt != null){
                        reservedcar_pstmt.close();
                    }
                }
            //end of while
            }
        }
        catch (SQLException ex){
            LOGGER.error("Unable to get the number of reservation for each car from the database");
        }
        finally {
            if(reservation_pstmt != null){
                reservation_pstmt.close();
            }
        }
        this.outputParam = cars;
    }

    /**
     * Query to count the number of reservation for each car
     */
    private static final String SQLQuery_CountLicensePlates = "SELECT LicensePlate, COUNT(*) as Count FROM RentalTransaction GROUP BY LicensePlate ORDER BY Count DESC";

    /**
     * Query to get imfotmations of a car
     */
    private static final String SQLQuery_ReservedCars = "SELECT * FROM Car WHERE LicensePlate = ?";

    
}
