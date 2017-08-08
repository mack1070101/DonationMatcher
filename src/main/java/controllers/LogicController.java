package controllers;

import models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mackenzie on 08/08/17.
 *
 * http://janmatuschek.de/LatitudeLongitudeBoundingCoordinates#Latitude
 * used for a semi crude implementation of geographic distance
 * Would use sqlite and Rtrees if time allowed
 */
public class LogicController {
    private DatabaseController dbc;
    private int earthRadius = 3959; //miles
    private double angluarRadius = 10 / earthRadius;

    public LogicController(DatabaseController dbc) {
        this.dbc = dbc;
    }

    public void findMatches() throws SQLException {
        ResultSet rs = dbc.statement.executeQuery("SELECT * from pickup;");
        ArrayList<Person> people = new ArrayList<Person>();

        while (rs.next()) {
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String street = rs.getString("street");
            String city = rs.getString("city");
            String state = rs.getString("state");
            String postal = rs.getString("postal");
            String country = rs.getString("country");
            String email =rs.getString("email");
            String phone = rs.getString("phone");

            people.add(new Person(firstName, lastName, street,
                  city, state, postal, country, email,
                  phone));
        }

        for(Person p: people){
            rs = dbc.fetchSuitableRecipients(p.get);
        }
    }
}