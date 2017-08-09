package controllers;

import models.Recipient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by mackenzie on 08/08/17.
 */
public class LogicControllerTest {
    DatabaseController db;
    private String[] badPickupOwner = ("Brett,Sullivan,2784 Ella Street,San Francisco,CA,94107,US,BrettJSullivan@teleworm.us," +
            "650-262-4366").split(",");
    private String[] badPickup = ("650-262-4366,37.728912,-122.324225,45,2016-11-29T16:00:00-08:00,America/Los_Angeles" +
            "").split(",");

    private String[] goodRecipientOwner = ("Louella,Henshaw,4824 Boring Lane,San Francisco,CA,94107,US," + "LouellaLHenshaw@gustr.com,415-645-8313").split(",");
    private String[] goodRecipient = "4925-573-5318,37.727542,-122.402876,61,43859,23301,53141,53343,32308,24869,42655".split(",");

    private String[] badRecipientOwner = "James,Pitts,4312 Alexander Avenue,San Francisco,CA,94111,US,JamesFPitts@cuvox.de,925-573-5318".split(",");
    private String[] badRecipient = ("925-573-5318,37.727542,-179.402876,61,43859,23301,53141,53343,32308,24869," +
            "42655").split(",");

    private String[] goodPickupOwner = "Carlos,Marsh,3998 Pretty View Lane,San Francisco,CA,94104,US,CarlosKMarsh@einrot.com,707-901-3071".split(",");
    private String[] goodPickup = "707-901-3071,37.817239,-122.447783,55,2016-11-29T17:00:00-08:00,America/Los_Angeles" .split(",");

    private LogicController logicController;

    @Before
    public void setUp() throws Exception {
        db = new DatabaseController("test.db");
        db.createPickupTable();
        db.createPersonsTable();
        db.createRecipientTable();

        db.insertIntoPersonTable(goodPickupOwner);
        db.insertIntoPickupTable(goodPickup);

        db.insertIntoPersonTable(goodRecipientOwner);
        db.insertIntoRecipientTable(goodRecipient);

        db.insertIntoPersonTable(badRecipientOwner);
        db.insertIntoRecipientTable(badRecipient);

        db.insertIntoPersonTable(badPickupOwner);
        db.insertIntoPickupTable(badPickup);
        logicController = new LogicController(db);
    }

    @After
    public void tearDown() throws Exception {
        File f = new File("test.db");
        f.delete();
    }

    @Test
    public void findMatches() throws Exception {
        HashMap<String, ArrayList<Recipient>> map = logicController.findMatches();

        assertEquals(map.get(goodPickupOwner[8]).size(),1);
        assertEquals(map.get(badPickupOwner[8]).size(), 0);
    }
}
