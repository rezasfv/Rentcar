package servlet;

import dao.AddRentalTransactionDAO;
import dao.GetCarDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.Car;

import java.io.BufferedReader;
import java.util.HashMap;
import service.RentalTransaction;
import service.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.concurrent.TimeUnit;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
/**
 * Servlet to reserve a car
 *
 * @author Luca Pellegrini
 * @version 1.0
 * @since 1.0
 */
public class ReservationServlet extends AbstractDatabaseServlet {


    /**
     * Servlet Get method to reserve a car
     *
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException if an error occurs during the servlet execution
     * @throws IOException if an error occurs during the servlet execution
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user1");
        if(user == null){
            session.setAttribute("afterLoginPath", "/HW1/reserve");
            response.sendRedirect("/HW1/login");
            return;
        }
        session.setAttribute("userFullName",user.getFullName());

        String availableLicencePlate = (String) session.getAttribute("availableLicencePlate");
        java.util.Date availableFromDate = (java.util.Date) session.getAttribute("availableFromDate");
        java.util.Date availableToDate = (java.util.Date) session.getAttribute("availableToDate");

        if( availableLicencePlate == null ||
            availableFromDate == null ||
            availableToDate == null
        ){
            response.sendRedirect("/HW1/check-availability");
            return;
        }

        try (Connection con = getConnection()) {
            Car car = new GetCarDAO(con,availableLicencePlate).access().getOutputParam();
            float amount = car.getRentalRate() * (1 + TimeUnit.DAYS.convert(availableToDate.getTime() - availableFromDate.getTime(), TimeUnit.MILLISECONDS));
            session.setAttribute("availableCar", car);
            session.setAttribute("amount",amount);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            session.setAttribute("availableFromDate",outputFormat.format(availableFromDate));
            session.setAttribute("availableToDate",outputFormat.format(availableToDate));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.getRequestDispatcher("jsp/reserve.jsp").forward(request, response);
    }
    /**
     * Servlet to reserve a car
     * 
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException if an error occurs during the servlet execution
     * @throws IOException if an error occurs during the servlet execution
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            HttpSession session = request.getSession();
            HashMap<String, String> errorMap = new HashMap<>();

        BufferedReader reader = request.getReader();
        StringBuilder jsonBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }

        // Parse JSON data to extract paymentId
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonBody.toString(), JsonObject.class);
        String paymentId = jsonObject.get("paymentId").getAsString();

        long amount  = (long) (float) session.getAttribute("amount");
            Car car = (Car) session.getAttribute("availableCar");
            User user = (User) session.getAttribute("user1");
            Date fromDate = Date.valueOf((String) session.getAttribute("availableFromDate"));
            Date toDate = Date.valueOf((String) session.getAttribute("availableToDate"));
            Date issueDate = new Date(System.currentTimeMillis());
            String paymentStatus = "pending";

            if (paymentId == null) {
                errorMap.put("paymentId", "Payment Id is missing");
            }
            if (amount == 0) {
                errorMap.put("amount", "Amount is missing");
            }
            if (car == null) {
                errorMap.put("car", "car is missing");
            }
            if (user == null) {
                errorMap.put("user", "user is missing");
            }
            if (fromDate == null) {
                errorMap.put("fromDate", "Start Date is missing");
            }
            if (toDate == null) {
                errorMap.put("toDate", "End Date is missing");
            }

            if (!errorMap.isEmpty()) {
                errorMap.put("status", "error");
                sendJsonResponse(response, errorMap);
                return;
            }

        LOGGER.info(("RentalTransaction instance created for user " +
                "id [%s] and license plate [%s]").formatted(
                user.getCostumer_ID(),
                car.getLicensePlate()
        ));

         RentalTransaction rentalTranInstance = new RentalTransaction(
                paymentId,
                (float) amount,
                paymentStatus,
                issueDate,
                fromDate,
                toDate,
                String.valueOf(user.getCostumer_ID()),
                car.getLicensePlate()
        );



        LOGGER.info(("attempted to insert rental transaction for user " +
                "id [%s] and license plate [%s]").formatted(
                user.getCostumer_ID(),
                car.getLicensePlate()
        ));
        try (Connection Con = getConnection()) {
            new AddRentalTransactionDAO(Con,rentalTranInstance).access();
            HashMap<String, String> successMap = new HashMap<>();
            successMap.put("status", "success");
            sendJsonResponse(response, successMap);
        } catch (SQLException e) {
            errorMap.put("database", "an error happened during database connection.");
            errorMap.put("status", "error");
            sendJsonResponse(response, errorMap);

        }




    }

    // Method to send JSON response
    private void sendJsonResponse(HttpServletResponse response, HashMap<String, String> data) throws IOException {
        // Set content type to JSON
        response.setContentType("application/json");

        // Convert data to JSON string using Gson library
        Gson gson = new Gson();
        String jsonData = gson.toJson(data);

        // Write JSON string to response output stream
        response.getWriter().write(jsonData);
    }

}