package models;

import org.junit.Test;

/**
 * Created by mackenzie on 07/08/17.
 *
 *
 * ODO add database hook in for person
 */
@SuppressWarnings("unused")
public class PickupTest {
    private final Person goodPerson = new Person("Leeroy", "Jenkins",
            "1234 Fake St.", "las Vegas", "CA", "94107", "US",
            "leeroy@warcraft.com", "605-555-5555");

    @Test
    public void testGoodPickup(){
        Pickup p = new Pickup(goodPerson.getPhone(), 37.728921, -122.324225, 45,
                "2016-11-29T16:00:00-08:00", "America/Los_Angeles");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testPickWithoutPerson(){
        Pickup p = new Pickup(" ", 37.728921, -122.324225, 45,
                "2016-11-29T16:00:00-08:00", "America/Los_Angeles");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPickWithBlankField(){
        Pickup p = new Pickup("780-444-4444", 37.728921, -122.324225, 45,
                " ", "America/Los_Angeles");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPickWithBadCategory(){
        Pickup p = new Pickup("780-444-4444", 37.728921, -122.324225, 128,
                "2016-11-29T16:00:00-08:00", "America/Los_Angeles");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testPickwithBadLat(){
         Pickup p = new Pickup("780-444-4444", 978.728921, -122.324225, 128,
                "2016-11-29T16:00:00-08:00", "America/Los_Angeles");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPickwithBadLon(){
         Pickup p = new Pickup("780-444-4444", 37.728921, -10022.324225, 128,
                "2016-11-29T16:00:00-08:00", "America/Los_Angeles");
    }
}