package models;

import org.junit.Test;

/**
 * Created by mackenzie on 07/08/17.
 *
 *
 * TODO improve testing of input valdiation
 * TODO add testing for rest of week
 */
public class RecipientTest {
    //Sample data for testing getters and setters
    private final Person goodPerson = new Person("Leeroy", "Jenkins",
            "1234 Fake St.", "Las Vegas","CA", "94107", "US",
            "leeroy@warcraft.com", "605-555-5555");
    @Test
    public void testGoodRecipient(){
        Recipient r = new Recipient(goodPerson.getPhone(), 37.809052, -122.483365,
                7, 44536, 44382, 19514, 12035,
                18094, 41561, 55924);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPickWithBadCategory(){
         Recipient r = new Recipient(goodPerson.getPhone(), 37.809052, -122.483365,
                128, 44536, 44382, 19514, 12035,
                18094, 41561, 5592);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPickwithBadLon(){
                Recipient r = new Recipient(goodPerson.getPhone(), 3007.809052, -122.483365,
                7, 44536, 44382, 19514, 12035,
                18094, 41561, 55924);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPickwithBadLat(){
                Recipient r = new Recipient(goodPerson.getPhone(), 37.809052, -10022.483365,
                7, 44536, 44382, 19514, 12035,
                18094, 41561, 55924);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRecipientWithBadAvailibility(){
                Recipient r = new Recipient(goodPerson.getPhone(), 37.809052, -122.483365,
                7, 4453096, 44382, 19514, 12035,
                18094, 41561, 131072);
    }

}