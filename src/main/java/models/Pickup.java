package models;

import com.sun.xml.internal.ws.util.StringUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mackenzie on 07/08/17.
 */
public class Pickup {
    private int personId;
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
    public Pickup(int personId, double lat, double lon, int category,
                  String date, String timeZoneId){
        this.personId = personId;
        this.longitude = lat;
        this.longitude = lon;
        this.category = category;
        Calendar cal = javax.xml.bind.DatatypeConverter.parseDateTime(date);
        this.pickupAt = cal.getTime();
        this.timeZoneId = timeZoneId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Date getPickupAt() {
        return pickupAt;
    }

    public  void setPickupAt(String date){
        Calendar cal = javax.xml.bind.DatatypeConverter.parseDateTime(date);
        this.pickupAt = cal.getTime();
    }
    public void setPickupAt(Date pickupAt) {
        this.pickupAt = pickupAt;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }
}
