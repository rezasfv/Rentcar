/*
 * Copyright 2023 University of Padua, Italy
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

package rest;

import dao.CreateCarDAO;
import service.Actions;
import service.Car;
import service.LogContext;
import service.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.EOFException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * A REST resource for creating {@link Car}s.
 *
 * @author Luca Pellegrini
 * @version 1.00
 * @since 1.00
 */
public final class CreateCarRR extends AbstractRR {

    /**
     * Creates a new REST resource for creating {@code Car}s.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @param con the connection to the database.
     */
    public CreateCarRR(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
        super(Actions.CREATE_CAR, req, res, con);
    }


    @Override
    protected void doServe() throws IOException {

        Car car = null;
        Message m = null;

        try {
            final Car carnew = Car.fromJSON(req.getInputStream());

            LogContext.setResource(carnew.getLicensePlate());

            // creates a new DAO for accessing the database and stores the car
            car = new CreateCarDAO(con, carnew).access().getOutputParam();

            if (car != null) {
                LOGGER.info("Car successfully created.");

                res.setStatus(HttpServletResponse.SC_CREATED);
                car.toJSON(res.getOutputStream());
            } else { // it should not happen
                LOGGER.error("Fatal error while creating car.");

                m = new Message("Cannot create the car: unexpected error.", "E5A1", null);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        } catch (EOFException ex) {
            LOGGER.warn("Cannot create the car: no Car JSON object found in the request.", ex);

            m = new Message("Cannot create the car: no Car JSON object found in the request.", "E4A8",
                    ex.getMessage());
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            m.toJSON(res.getOutputStream());
        } catch (SQLException ex) {
            if ("23505".equals(ex.getSQLState())) {
                LOGGER.warn("Cannot create the car: it already exists.");

                m = new Message("Cannot create the car: it already exists.", "E5A2", ex.getMessage());
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                m.toJSON(res.getOutputStream());
            } else {
                LOGGER.error("Cannot create the car: unexpected database error.", ex);

                m = new Message("Cannot create the car: unexpected database error.", "E5A1", ex.getMessage());
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        }
    }


}
