package servlet;

import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import rest.TopRentedCarsRR;
import service.LogContext;
import service.Message;

/**
 * A servlet that manages the top rented cars.
 * 
 * @author Francesco Chemello
 * @version 1.0
 * @since 1.0
 */
public class TopRentedManagerServlet extends AbstractDatabaseServlet{

    /**
     * The JSON UTF-8 MIME media type
     */
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

    /**
     * Method for the REST service
     * 
     * @param req the HttpServletRequest
     * @param res the HttpServletResponse
     * @throws IOException if any error occurs
     */
    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse res) throws IOException {
        LogContext.setIPAddress(req.getRemoteAddr());

        final OutputStream out = res.getOutputStream();

        try {

            // if the requested resource was for the top rented, delegate its processing and return
            if (processTopRented(req, res)) {
                return;
            }
            // if none of the above process methods succeeds, it means an unknown resource has been requested
            LOGGER.warn("Unknown resource requested: %s.", req.getRequestURI());

            final Message m = new Message("Unknown resource requested.", "E4A6",
                    String.format("Requested resource is %s.", req.getRequestURI()));
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.setContentType(JSON_UTF_8_MEDIA_TYPE);
            m.toJSON(out);
        } catch (Throwable t) {
            LOGGER.error("Unexpected error while processing the REST resource.", t);

            final Message m = new Message("Unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(out);
        } finally {

            // ensure to always flush and close the output stream
            if (out != null) {
                out.flush();
                out.close();
            }

            LogContext.removeIPAddress();
        }
    }

    /**
     * Process the top rented cars.
     * 
     * @param req the HttpServletRequest
     * @param res the HttpServletResponse
     * @return true if the resource was correct, false otherwise
     * @throws Exception if any error occurs
     */
    private boolean processTopRented(final HttpServletRequest req, final HttpServletResponse res) throws Exception {
        final String method = req.getMethod();

        String path = req.getRequestURI();
        Message m = null;

        res.setContentType(JSON_UTF_8_MEDIA_TYPE);

        if (path.endsWith("/rests/toprentedcars")) {
            // method
            switch (method) {
                case "GET":
                    new TopRentedCarsRR(req, res, getConnection()).serve();
                    break;
                default:
                    LOGGER.warn("Unsupported operation!");

                    m = new Message("Unsupported operation! Error: E4A5");
                    res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                    m.toJSON(res.getOutputStream());
                    break;
            }
        } else {
            // the requested resource was not correct
            return false;
        }

        // the requested resource was correct
        return true;
    }
}
