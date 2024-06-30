package rest;

import dao.DeleteCarDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import service.Car;
import service.LogContext;
import service.Message;

/**
 * A REST resource for deleting {@link Car}.
 *
 * @author Luca Pellegrini
 * @version 1.00
 * @since 1.00
 */
public final class DeleteCarRR extends AbstractRR {
    /**
     * Creates a new REST resource for deleting {@code Car}s.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @param con the connection to the database.
     */
    public DeleteCarRR(HttpServletRequest req, HttpServletResponse res, Connection con) {
        super("DELETE_CAR", req, res, con);
    }

    @Override
    protected void doServe() throws IOException {
        Car car = null;
        Message m = null;

        try {
            String path = this.req.getRequestURI();
            final String  licenseplate = path.substring(path.indexOf("car") + 3).split("/")[1];
            LogContext.setResource(licenseplate);
            car = (new DeleteCarDAO(this.con, licenseplate)).access().getOutputParam();
            if (car != null) {
                LOGGER.info("Car successfully deleted.");
                this.res.setStatus(HttpServletResponse.SC_OK);
                car.toJSON(this.res.getOutputStream());
            } else {
                LOGGER.warn("Car not found. Cannot delete it.");
                m = new Message(String.format("Car " + path + " not found. Cannot delete it."), "E5A3", null);
                this.res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                m.toJSON(this.res.getOutputStream());
            }
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            LOGGER.warn("Cannot delete the car: wrong format for URI /car/{licenseplate}.", ex);
            m = new Message("Cannot delete the car: wrong format for URI /car/{licenseplate}.", "E4A7", ex.getMessage());
            this.res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            m.toJSON(this.res.getOutputStream());
        } catch (SQLException ex) {
            if ("23503".equals(ex.getSQLState())) {
                LOGGER.warn("Cannot delete the car: other resources depend on it.");
                m = new Message("Cannot delete the car: other resources depend on it.", "E5A4", ex.getMessage());
                this.res.setStatus(HttpServletResponse.SC_CONFLICT);
                m.toJSON(this.res.getOutputStream());
            } else {
                LOGGER.error("Cannot delete the car: unexpected database error.", ex);
                m = new Message("Cannot delete the car: unexpected database error.", "E5A1", ex.getMessage());
                this.res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(this.res.getOutputStream());
            }
        }

    }
}