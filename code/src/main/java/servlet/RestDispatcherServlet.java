/*
 * Copyright 2018 University of Padua, Italy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package servlet;

import service.LogContext;
import service.Message;
import rest.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.Car;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Dispatches the request to the proper REST resource.
 *
 * @author Nicola Ferro (ferro@dei.unipd.it)
 * @author Luca Pellegrini
 * @version 1.00
 * @since 1.00
 */
public final class RestDispatcherServlet extends AbstractDatabaseServlet {

    /**
     * The JSON UTF-8 MIME media type
     */
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse res) throws IOException {

        LogContext.setIPAddress(req.getRemoteAddr());

        final OutputStream out = res.getOutputStream();

        try {

            // if the requested resource was a Car, delegate its processing and return
            if (processCar(req, res)) {
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
     * Checks whether the request if for an {@link Car} resource and, in case, processes it.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     *
     * @return {@code true} if the request was for an {@code Employee}; {@code false} otherwise.
     *
     * @throws Exception if any error occurs.
     */
    private boolean processCar(final HttpServletRequest req, final HttpServletResponse res) throws Exception {

        final String method = req.getMethod();

        String path = req.getRequestURI();
        Message m = null;

        // the requested resource was not an employee
        if (path.lastIndexOf("rest/car") <= 0) {
            return false;
        }

        res.setContentType(JSON_UTF_8_MEDIA_TYPE);

        // strip everything until after the /car
        path = path.substring(path.lastIndexOf("car")+3);
        // the request URI is: /car
        // if method GET, list car
        // if method POST, create car
        if (path.isEmpty() || path.equals("/")) { // url: rest/car/

            switch (method) {
                case "GET":
                    new ListCarRR(req, res, getConnection()).serve();
                    break;
                case "POST":
                    new CreateCarRR(req, res, getConnection()).serve();
                    break;
                default:
                    LOGGER.warn("Unsupported operation for URI /car: {}.", method);

                    m = new Message("Unsupported operation for URI /car.", "E4A5",
                            String.format("Requested operation %s.", method));
                    res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                    m.toJSON(res.getOutputStream());
                    break;
            }
        }
        else if (path.split("/").length==2) { // url: rest/car/{licenceplate}

            switch (method) {
                case "GET":
                    new GetCarRR(req, res, getConnection()).serve();
                    break;
                case "DELETE":
                    new DeleteCarRR(req, res, getConnection()).serve();
                    break;
                default:
                    LOGGER.warn("Unsupported operation for URI /car{}: {}.",path, method);

                    m = new Message(String.format("Unsupported operation for URI /car%s.",path), "E4A5",
                            String.format("Requested operation %s.", method));
                    res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                    m.toJSON(res.getOutputStream());
                    break;
            }
        }

        return true;

    }

}
