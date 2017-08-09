package controllers;

import models.Person;
import models.Pickup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by mackenzie on 08/08/17.
 *
 *
 * Presently the input validation tests are failing so that the can be addressed when more is known
 */
public class DatabaseControllerTest extends LogicControllerTest {

    private DatabaseController dbc = new DatabaseController("test.db");
    private String[] goodPerson = ("Brett,Sullivan,2784 Ella Street,San Francisco,CA,94107,US,BrettJSullivan@teleworm.us," +
            "650-262-4366").split(",");
    private String[] goodPickup = ("650-262-4366,37.728912,-122.324225,45,2016-11-29T16:00:00-08:00,America/Los_Angeles" +
            "").split(",");
    private String[] goodRecipient = ("408-702-0996,37.809052,-122.483365,7,44536,44382,19514,12035,18094,41561,55924" +
            "").split(",");
    @Before
    public void setUp() throws Exception {
       dbc = new DatabaseController("test.db");
    }

    @After
    public void tearDown() throws Exception{
        dbc.close();
        File f = new File("test.db");
        f.delete();
    }

    @Test
    public void createPersonsTable() throws Exception {
        dbc.createPersonsTable();
    }

    @Test
    public void createPickupTable() throws Exception {
        dbc.createPickupTable();
    }

    @Test
    public void createRecipientTable() throws Exception {
        dbc.createRecipientTable();
    }

    @Test(expected = SQLException.class)
    public void testInsertIntoPersonTable_NoTable() throws Exception {
        dbc.insertIntoPersonTable(goodPerson);
    }

    //TODO MUST FLESH OUT INPUT VALIDATION MORE
    @Test(expected = SQLException.class)
    public void testInsertIntoPersonTable_BadInput() throws Exception {
        String[] badPerson = goodPerson;
        badPerson[8] = "   ";
        dbc.createPersonsTable();
        dbc.insertIntoPersonTable(badPerson);
    }

    @Test
    public void insertIntoPickupTable() throws Exception {
        dbc.createPickupTable();
        dbc.insertIntoPickupTable(goodPickup);
    }

    @Test(expected = SQLException.class)
    public void testInsertIntoPickupTable_NoTable() throws Exception {
        dbc.insertIntoPickupTable(goodPickup);
    }


    @Test(expected = SQLException.class)
    public void testInsertIntoPickupTable_BadInput() throws Exception {
        //Test has been mocked to ensure future development addresses this
    }

    @Test
    public void insertIntoRecipientTable() throws Exception {
        dbc.createRecipientTable();
        dbc.insertIntoRecipientTable(goodRecipient);
    }

    @Test(expected = SQLException.class)
    public void testInsertIntoRecipientTable_NoTable() throws Exception {
        dbc.insertIntoRecipientTable(goodRecipient);
    }

    @Test(expected = SQLException.class)
    public void testInsertIntoRecipientTable_BadInput() throws Exception {
        //Test has been mocked to ensure future development addresses this
    }

    @Test
    public void fetchSuitableRecipients() throws Exception {
        //Covered by findMatches test in LogicControllerTest
    }

    @Test
    public void getPickup() throws Exception {
        dbc.createPickupTable();
        dbc.insertIntoPickupTable(goodPickup);
        Pickup pickup = dbc.getPickup(goodPickup[0]);
        assertNotEquals(null, pickup);
    }
    @Test
    public void getPickup_PickupDNE() throws Exception{
        dbc.createPickupTable();
        dbc.insertIntoPickupTable(goodPickup);
        Pickup pickup = dbc.getPickup("123-456-7888");

        assertEquals(null,pickup);
    }

    @Test
    public void getPerson() throws Exception {
        dbc.createPersonsTable();
        dbc.insertIntoPersonTable(goodPerson);
        Person person = dbc.getPerson(goodPerson[8]);
        assertNotEquals(null, person);
    }

    @Test
    public void getPerson_PersonDNE() throws Exception {
        dbc.createPersonsTable();
        dbc.insertIntoPersonTable(goodPerson);
        Person person = dbc.getPerson("123-456-7888");
        assertEquals(null, person);
    }
}