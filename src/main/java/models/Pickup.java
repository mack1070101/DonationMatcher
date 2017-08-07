package models;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by mackenzie on 07/08/17.
 *
 * The pickup class describes a pickup event. It contains the associated information
 * for a pickup and is associated with a person.
 *
 *
 * @TODO INPUT VALIDTION
 */

public class Pickup {
    private UUID personId;
    private double latitude;
    private double longitude;
    private int category;
    private Date pickupAt;
    private String timeZoneId;

    /**
     * Constructor for the pickup class
     *
     * @note ISO 8601 date format is validated using the following method
     * https://stackoverflow.com/questions/2201925/converting-iso-8601-compliant-string-to-java-util-date
     *
     * @param personId
     * @param lat
     * @param lon
     * @param category
     * @param date
     * @param timeZoneId
     */
    public Pickup(UUID personId, double lat, double lon, int category,
                  String date, String timeZoneId){
        this.personId = personId;
        this.longitude = lat;
        this.longitude = lon;
        this.category = category;
        Calendar cal = javax.xml.bind.DatatypeConverter.parseDateTime(date);
        this.pickupAt = cal.getTime();
        this.timeZoneId = timeZoneId;
    }
/*
    public double distanceToDropoff(Recipient R){
        return -1.0;
    }
 */
}
