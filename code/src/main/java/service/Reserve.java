package service;

import java.util.Date;

/** Rappresent a reservation object (relation)
 * 
 * @author Gabriella
 * @author Francesco Chemello
 * @author Luca Pellegrini
 * @version 1.0
 * @since 1.0
 */

public class Reserve {

       //Attributes
       private final int costumer_id;
       private final String payment_ID;
       private final String license_plate;
       private final Date fromDate;
       private final Date toDate;

       /**
        * Constructor
        *
        * @param costumer_id the costumer id
        * @param payment_ID the payment id
        * @param license_plate the license plate
        */
       public Reserve(int costumer_id, String payment_ID, String license_plate, Date fromDate, Date toDate){
              this.costumer_id = costumer_id;
              this.payment_ID = payment_ID;
              this.license_plate = license_plate;
              this.fromDate = fromDate;
              this.toDate = toDate;
       }

       /**
        * Get the costumer id
        * @return the costumer id
        */
       public int getCostumerId() {
              return costumer_id;
       }

       /**
        * Get the payment id
        * @return the payment id
        */
       public String getPaymentId() {
              return payment_ID;
       }

       /**
        * Get the license plate
        * @return the license plate
        */
       public String getLicensePlate() {
              return license_plate;
       }

       /**
        * Get the start date
        * @return the start date
        */
       public Date getFromDate() {
              return fromDate;
       }

       /**
        * Get the end date
        * @return the end date
        */
       public Date getToDate() {
              return toDate;
       }

       @Override
       public String toString() {
              return "<div class=\"card border border-info\" style=\"width: 18rem;\"> <div class=\"card-body\">" +
                      "<h5 class=\"card-title text-danger border p-2 mb-4\">License Plate: " + this.license_plate + "</h5>" +
                      "<h6 class=\"card-subtitle mb-3 text-muted\">" + this.fromDate + " - " + this.toDate + "</h6>" +
                      "</div></div>";
       }
}
