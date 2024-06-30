package dao;

import service.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to search for cars in the database
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public final class SearchDAO extends BasicDAO<List<Car>>{
    /**
     * All the cars attributes
     */
    private float rentalrate;
    private String modelname;
    private String brandname;
    private int capacity;
    private String category;
    private String currentstatus;

    /**
     * The SQL query to be executed
     */
    private static String SQLQuery = "SELECT * FROM cars WHERE 1=1";
    /**
     * Constructor
     * 
     * @param c the connection to the database
     * @param rentalrate the rental price of the car
     * @param modelname the model name of the car
     * @param brandname the brand name of the car
     * @param capacity the capacity of the car
     * @param category the category of the car
     * @param currentstatus the current status of the car
     * @throws NullPointerException if c is null
     * @throws SQLException if an error occurs in the database
     */
    public SearchDAO(
            final Connection c,
            final float rentalrate,
            final String modelname,
            final String brandname,
            final int capacity,
            final String category,
            final String currentstatus)
    throws NullPointerException, SQLException{
        super(c);
        this.rentalrate = rentalrate;
        this.modelname = modelname;
        this.brandname = brandname;
        this.capacity = capacity;
        this.category = category;
        this.currentstatus = currentstatus;
    }

    @Override
    /**
     * Search for cars in the database
     * 
     * @throws SQLException if an error occurs
     */
    protected void doAccess() throws SQLException {
        ResultSet set = null;
        StringBuilder query = new StringBuilder("SELECT * FROM car c WHERE 1=1");
        final List<Car> cars = new ArrayList<>();
        if (rentalrate != 0) query.append(" AND rentalrate <= ?");
        if (modelname != null) query.append(" AND modelname = ?");
        if (brandname != null) query.append(" AND brandname = ?");
        if (capacity != 0) query.append(" AND capacity = ?");
        if (category != null) query.append(" AND category = CAST(? AS car_class)");
        if (currentstatus != null) query.append(" AND currentstatus = CAST(? AS car_status)");
        System.out.println( brandname);
        System.out.println( category);
        System.out.println( currentstatus);
        System.out.println(query.toString());
        try {
            PreparedStatement stmt = c.prepareStatement(query.toString());
            int paramIndex = 1;

            if (rentalrate != 0) stmt.setFloat(paramIndex++, rentalrate);
            if (modelname != null) stmt.setString(paramIndex++, modelname);
            if (brandname != null) stmt.setString(paramIndex++, brandname);
            if (capacity != 0) stmt.setInt(paramIndex++, capacity);
            if (category != null) stmt.setString(paramIndex++, category);
            if (currentstatus != null) stmt.setString(paramIndex, currentstatus);

            System.out.println(stmt.toString());
            set = stmt.executeQuery();
            //extract the data from the result set
            while (set.next()) {
                cars.add(new Car(set.getString("LicensePlate"),
                        set.getFloat("RentalRate"),
                        set.getInt("Capacity"),
                        set.getString("ModelName"),
                        set.getString("Category"),
                        set.getString("CurrentStatus"),
                        set.getString("BrandName")));

                LOGGER.info("Car found with the following params: " + cars.toString());
			}
        }
        catch (SQLException e) {
            LOGGER.error("Unable to close the result set and/or statement.", e);
        }
        finally {
            if (set != null) {
                set.close();
            }
        }
        //set the output param
        this.outputParam = cars;
    }


}
