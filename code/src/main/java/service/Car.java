package service;

import com.fasterxml.jackson.core.*;
import java.io.*;

/**
 * Rappresent a car object
 * 
 * @author Francesco Chemello
 * @author Luca Pellegrini
 * @version 1.0
 * @since 1.0
 */
public class Car extends AbstractResource {
    
    // Attributes
    private final String license_plate;
    private final float rentalrate;
    private final int capacity;
    private final String modelname;
    private final String category;
    private final String currentstatus;
    private final String brandname;

    /**
     * Constructor 
     * 
     * @param license_plate the license plate
     * @param rentalrate the rental rate
     * @param capacity the capacity
     * @param modelname the model name
     * @param category the category
     * @param currentstatus the current status
     * @param brandname the brand name
     */
    public Car(String license_plate, float rentalrate, int capacity, String modelname, String category, String currentstatus, String brandname) {
        this.license_plate = license_plate;
        this.rentalrate = rentalrate;
        this.capacity = capacity;
        this.modelname = modelname;
        this.category = category;
        this.currentstatus = currentstatus;
        this.brandname = brandname;
    }

    /**
     * Get the license plate of the car
     * 
     * @return the license plate
     */
    public String getLicensePlate() {
        return license_plate;
    }

    /**
     * Get the rental rate of the car
     * 
     * @return the rental rate
     */
    public float getRentalRate() {
        return rentalrate;
    }

    /**
     * Get the capacity of the car
     * 
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Get the model name of the car
     * 
     * @return the model name
     */
    public String getModelName() {
        return modelname;
    }

    /**
     * Get the category of the car
     * 
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Get the current status of the car
     * 
     * @return the current status
     */
    public String getCurrentStatus() {
        return currentstatus;
    }

    /**
     * Get the brand name of the car
     * 
     * @return the brand name
     */
    public String getBrandName() {
        return brandname;
    }

    /**
     * Write car to json
     *
     * @param out the OutputStream
     */
    @Override
    protected final void writeJSON(final OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeFieldName("car");

        jg.writeStartObject();

        jg.writeStringField("licenseplate", license_plate);
        jg.writeNumberField("rentalrate", rentalrate);
        jg.writeNumberField("capacity", capacity);
        jg.writeStringField("category", category);
        jg.writeStringField("currentstatus", currentstatus);
        jg.writeStringField("brandname", brandname);
        jg.writeStringField("modelname", modelname);

        jg.writeEndObject();

        jg.writeEndObject();

        jg.flush();
    }

    /**
     * Creates a {@code Car} from its JSON representation.
     *
     * @param in the input stream containing the JSON document.
     *
     * @return the {@code Car} created from the JSON representation.
     *
     * @throws IOException if something goes wrong while parsing.
     */
    public static Car fromJSON(final InputStream in) throws IOException  {

        // the fields read from JSON
        String jlicense_plate = null;
        float jrentalrate = -1;
        int jcapacity = -1;
        String jmodelname = null;
        String jcategory = null;
        String jcurrentstatus = null;
        String jbrandname = null;

        try {
            final JsonParser jp = JSON_FACTORY.createParser(in);

            // while we are not on the start of an element or the element is not
            // a token element, advance to the next element (if any)
            while (jp.getCurrentToken() != JsonToken.FIELD_NAME || !"car".equals(jp.getCurrentName())) {

                // there are no more events
                if (jp.nextToken() == null) {
                    LOGGER.error("No Car object found in the stream.");
                    throw new EOFException("Unable to parse JSON: no Car object found.");
                }
            }

            while (jp.nextToken() != JsonToken.END_OBJECT) {

                if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

                    switch (jp.getCurrentName()) {
                        case "licenseplate":
                            jp.nextToken();
                            jlicense_plate = jp.getText();
                            break;
                        case "rentalrate":
                            jp.nextToken();
                            jrentalrate = jp.getFloatValue();
                            break;
                        case "capacity":
                            jp.nextToken();
                            jcapacity = jp.getIntValue();
                            break;
                        case "modelname":
                            jp.nextToken();
                            jmodelname = jp.getText();
                            break;
                        case "category":
                            jp.nextToken();
                            jcategory = jp.getText();
                            break;
                        case "currentstatus":
                            jp.nextToken();
                            jcurrentstatus = jp.getText();
                            break;
                        case "brandname":
                            jp.nextToken();
                            jbrandname = jp.getText();
                            break;
                    }
                }
            }
        } catch(IOException e) {
            LOGGER.error("Unable to parse an Car object from JSON.", e);
            throw e;
        }

        return new Car(jlicense_plate, jrentalrate, jcapacity, jmodelname, jcategory, jcurrentstatus, jbrandname);
    }

    @Override
    public String toString() {
        return  "<div class=\"card border border-info\" style=\"width: 18rem;\"> <div class=\"card-body\">" +
                "<h5 class=\"card-title text-danger border p-2 mb-4\">License Plate: " + this.license_plate + "</h5>" +
                "<h6 class=\"card-subtitle mb-3 text-muted\">Rental Rate: " + this.rentalrate + "</h6>" +
                "<p class=\"card-text\">Capacity: " + this.capacity + "</p>" +
                "<p class=\"card-text\">Model Name: " + this.modelname + "</p>" +
                "<p class=\"card-text\">Category: " + this.category + "</p>" +
                "<p class=\"card-text\">Current Status: " + this.currentstatus + "</p>" +
                "<p class=\"card-text\">Brand Name: " + this.brandname + "</p>" +
                "<form method=\"get\" action=\"check-availability\">\n" +
                "   <input type=\"hidden\" name=\"licencePlate\" value=\""+this.license_plate+"\" required>\n" +
                "   <input type=\"submit\" value=\"Reserve\">\n" +
                "</form>"+
                "</div></div>";
    }

}
