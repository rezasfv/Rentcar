package rest;

import dao.GetCarDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import service.Car;
import service.LogContext;
import service.Message;

/**
 * A REST resource for get {@link Car}.
 *
 * @author Luca Pellegrini
 * @version 1.00
 * @since 1.00
 */
public final class GetCarRR extends AbstractRR {
    /**
     * Creates a new REST resource for get a {@code Car}.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @param con the connection to the database.
     */
    public GetCarRR(HttpServletRequest req, HttpServletResponse res, Connection con) {
        super("LIST_CAR", req, res, con);
    }

    @Override
    protected void doServe() throws IOException {
        Car car = null;
        Message m = null;

        try {
            String path = this.req.getRequestURI();
            path = path.substring(path.indexOf("car") + 3).split("/")[1];
            System.out.println("----path" + path);
            LogContext.setResource(path);
            car = (new GetCarDAO(this.con, path)).access().getOutputParam();
            if (car != null) {
                LOGGER.info("Car successfully get.");
                this.res.setStatus(HttpServletResponse.SC_OK);
                car.toJSON(this.res.getOutputStream());
            } else {
                LOGGER.error("Requested Car not Found.");
                m = new Message("Cannot get car: not Found.", "E404", null);
                this.res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                m.toJSON(this.res.getOutputStream());
            }
        } catch (SQLException ex) {
            LOGGER.error("Cannot get car: unexpected database error.", ex);
            m = new Message("Cannot get car(s): unexpected database error.", "E5A1", ex.getMessage());
            this.res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(this.res.getOutputStream());
        }

    }
}