package models;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mackenzie on 07/08/17.
 *
 * The pickup class describes a pickup event. It contains the associated information
 * for a pickup and is associated with a person.
 *
 *
 * TODO INPUT VALIDTION
 */

public class Pickup {
    public String getPersonId() {
        return personId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getCategory() {
        return category;
    }

    public Date getPickupAt() {
        return pickupAt;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    private final String personId;
    private double latitude;
    private double longitude;
    private int category;
    private Date pickupAt;
    private String timeZoneId;
    private int day;
    private String time;

    /**
     * Constructor for the pickup class
     *
     * @param personId ID of the person that owns this pickup
     * @param lat latitude where the pickup is located
     * @param lon longitude where the pickup is located
     * @param category category of pickup using the 6 bit packed int
     * @param date date on which the pickup will occur
     * @param timeZoneId IANA ID of the timezone
     *
     * note ISO 8601 date format is validated using the following method
     * https://stackoverflow.com/questions/2201925/converting-iso-8601-compliant-string-to-java-util-date
     */
    public Pickup(String personId, double lat, double lon, int category,
                  String date, String timeZoneId) {
        this.personId = personId;
        this.latitude = lat;
        this.longitude = lon;
        this.category = category;
        Calendar cal = javax.xml.bind.DatatypeConverter.parseDateTime(date);
        this.pickupAt = cal.getTime();
        this.timeZoneId = timeZoneId;
    }

    public String toCsv() {
        String sb = String.valueOf(latitude) +
                "," +
                longitude +
                "," +
                category +
                "," +
                pickupAt +
                "," +
                timeZoneId;

        return sb;
    }
}
