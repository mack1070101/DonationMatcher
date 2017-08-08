package models;

import java.util.UUID;

/**
 * Created by mackenzie on 07/08/17.
 *
 *
 * @TODO better data validation
 *
 * The recipient class represents an event where food must be recieved
 */
public class Recipient {
    private String personId;
    private double latitude;
    private double longitude;
    private int restrictions;
    private int sundayHours;
    private int mondayHours;
    private int tuesdayHours;
    private int wednesdayHours;
    private int thursdayHours;
    private int fridayHours;
    private int saturdayHours;

    public Recipient(String personId, double latitude, double longitude,
                     int restrictions, int sundayHours, int mondayHours,
                     int tuesdayHours, int wednesdayHours, int thursdayHours,
                     int fridayHours, int saturdayHours) {

        this.personId = personId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.restrictions = restrictions;
        this.sundayHours = sundayHours;
        this.mondayHours = mondayHours;
        this.tuesdayHours = tuesdayHours;
        this.wednesdayHours = wednesdayHours;
        this.thursdayHours = thursdayHours;
        this.fridayHours = fridayHours;
        this.saturdayHours = saturdayHours;
    }

}
