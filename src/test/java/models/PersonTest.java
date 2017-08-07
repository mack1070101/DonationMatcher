package models;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mackenzie on 07/08/17.
 * @author mackenzie
 *
 * Tests written for the @see Person class
 *
 * @Todo
 * In the future, better validation should be done on the inputs to ensure
 * valid data (states are in the correct country), phone numbers are correct
 * format, country codes conform to some specification, postal codes conform
 * to some specification, ect.
 */
public class PersonTest {

    //Sample data for testing getters and setters
    Person goodPerson = new Person("Leeroy", "Jenkins",
            "1234 Fake St.", "CA", "94107", "US",
            "leeroy@warcraft.com", "605-555-5555");

    @Test
    public void testCorrectPerson(){
        Person p = new Person("Leeroy", "Jenkins",
                "1234 Fake St.", "CA", "94107", "US",
                "leeroy@warcraft.com", "605-555-5555");
    }

    @Test
    public void testBadPerson(){
        Person p = new Person("Leeroy", " ",
                "1234 Fake St.", "CA", "94107", "US",
                "leeroy@warcraft.com", "605-555-5555");
    }

    @Test
    public void testBadEmails() {
        try{
            goodPerson.setEmail("notanemail@email");
        } catch(IllegalArgumentException e){

        }
        try{
            goodPerson.setEmail("notanemail");
        } catch(IllegalArgumentException e){

        }
        try{
            goodPerson.setEmail("      ");
        } catch(IllegalArgumentException e){

        }
    }

    @Test
    public void testGoodEmail(){
        String oldEmail = goodPerson.getEmail();
        goodPerson.setEmail("newEmail@google.ca");
        assertNotEquals(oldEmail, goodPerson.getEmail());
    }

}
