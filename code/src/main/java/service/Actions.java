/*
* Copyright (c) 2023 University of Padua, Italy
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*       https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package service;

/**
 * Contains constants for the actions performed by the application.
*
* @author Nicola Ferro
* @version 1.00
* @since 1.00
*/
public final class Actions {

    /**
     * List of all possible actions that can be done by the application.
     * 
     * <!>
     * They must be written as: public static final String ACTION_NAME = "ACTION_NAME";
     * <!>
     */

    /**
     * Search cars by parameters.
     */
    public static final String SEARCH_CARS_BY_PARAMS = "SEARCH_CARS_BY_PARAMS";

    /**
     * Search all the cars.
     */
    public static final String SEARCH_ALL_CARS = "SEARCH_ALL_CARS";

    /**
     * Create a new car.
     */
    public static final String CREATE_CAR = "CREATE_CAR";

    /**
     * List cars.
     */
    public static final String LIST_CAR = "LIST_CAR";

    /**
     * Create a new license.
     */
    public static final String CREATE_License = "CREATE_License";
    public static final String Edit_License = "CREATE_License";

    /**
     * Update a car.
     */
    public static final String UPDATE_CAR = "UPDATE_CAR";

    /**
     * Delete a car.
     */
    public static final String DELETE_CAR = "DELETE_CAR";

    /**
     * Add a card.
     */
    public static final String ADD_CARD = "ADD_CARD";

    /**
     * Remove a card.
     */
    public static final String REMOVE_CARD = "REMOVE_CARD";

    /**
     * Add a rental transaction.
     */
    public static final String ADD_RENTAL_TRANSACTION = "ADD_RENTAL_TRANSACTION";

    /**
     * Remove a rental transaction.
     */
    public static final String REMOVE_RENTAL_TRANSACTION = "REMOVE_RENTAL_TRANSACTION";

    /**
     * Update a rental transaction.
     */
    public static final String UPDATE_RENTAL_TRANSACTION = "UPDATE_RENTAL_TRANSACTION";

    /**
     * Create a new customer.
     */
    public static final String CREATE_Customer = "CREATE_Customer";

    /**
     * Update a customer.
     */
    public static final String Update_Customer = "CREATE_Customer";

    /**
     * Admin login.
     */
    public static final String ADMIN_LOGIN = "ADMIN_LOGIN";

    /**
     * Admin logout.
     */
    public static final String ADMIN_LOGOUT = "ADMIN_LOGOUT";

    /**
     * This class can be neither instantiated nor sub-classed.
    */
    private Actions() {
        throw new AssertionError(String.format("No instances of %s allowed.", Actions.class.getName()));
    }
}
