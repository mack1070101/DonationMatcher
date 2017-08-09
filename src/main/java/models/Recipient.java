package models;

/**
 * Created by mackenzie on 07/08/17.
 *
 *
 * TODO better data validation
 *
 * The recipient class represents an event where food must be recieved
 */
public class Recipient {
    private final String personId;
    private final double latitude;
    private final double longitude;
    private final int restrictions;
    private final int sundayHours;
    private final int mondayHours;
    private final int tuesdayHours;
    private final int wednesdayHours;
    private final int thursdayHours;
    private final int fridayHours;
    private final int saturdayHours;

    public Recipient(String personId, double latitude, double longitude,
                     int restrictions, int sundayHours, int mondayHours,
                     int tuesdayHours, int wednesdayHours, int thursdayHours,
                     int fridayHours, int saturdayHours) {

        if(latitude < -90 || latitude > 90) throw new IllegalArgumentException();
        if(longitude < -180 || longitude> 180) throw new IllegalArgumentException();
        if(restrictions > 63 || restrictions < 0) throw new IllegalArgumentException();
        if(personId.matches(".*\\w.*") == false) throw new IllegalArgumentException();
        if(sundayHours > 65535 || sundayHours< 0) throw new IllegalArgumentException();
        if(mondayHours > 65535 || mondayHours < 0) throw new IllegalArgumentException();
        if(tuesdayHours > 65535 || tuesdayHours < 0) throw new IllegalArgumentException();
        if(wednesdayHours > 65535 || wednesdayHours < 0) throw new IllegalArgumentException();
        if(thursdayHours > 65535 || thursdayHours < 0) throw new IllegalArgumentException();
        if(fridayHours > 65535 || fridayHours < 0) throw new IllegalArgumentException();
        if(saturdayHours > 65535 || saturdayHours < 0) throw new IllegalArgumentException();


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

    public String getPersonId() {
        return personId;
    }
    public String toCsv(){

        return String.valueOf(latitude) +
                "," +
                longitude +
                "," +
                restrictions +
                "," +
                sundayHours +
                "," +
                mondayHours +
                "," +
                tuesdayHours +
                "," +
                wednesdayHours +
                "," +
                thursdayHours +
                "," +
                fridayHours +
                "," +
                saturdayHours;
    }
}

