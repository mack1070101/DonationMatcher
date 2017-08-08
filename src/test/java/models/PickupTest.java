package models;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by mackenzie on 07/08/17.
 *
 *
 * @TODO add database hook in for person
 */
public class PickupTest {
    Person goodPerson = new Person("Leeroy", "Jenkins",
            "1234 Fake St.", "CA", "94107", "US",
            "leeroy@warcraft.com", "605-555-5555");

    @Test
    public void testGoodPickup(){
        Pickup p = new Pickup(goodPerson.getUuid(), 37.728921, -122.324225, 45,
                "2016-11-29T16:00:00-08:00", "America/Los_Angeles");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testPickWithoutPerson(){
        Pickup p = new Pickup(UUID.randomUUID(), 37.728921, -122.324225, 45,
                "2016-11-29T16:00:00-08:00", "America/Los_Angeles");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testPickWithBlankField(){
        Pickup p = new Pickup(UUID.randomUUID(), 37.728921, -122.324225, 45,
                " ", "America/Los_Angeles");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPickWithBadCategory(){
        Pickup p = new Pickup(UUID.randomUUID(), 37.728921, -122.324225, 128,
                "2016-11-29T16:00:00-08:00", "America/Los_Angeles");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testPickwithBadLat(){
         Pickup p = new Pickup(UUID.randomUUID(), 978.728921, -122.324225, 128,
                "2016-11-29T16:00:00-08:00", "America/Los_Angeles");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPickwithBadLon(){
         Pickup p = new Pickup(UUID.randomUUID(), 37.728921, -10022.324225, 128,
                "2016-11-29T16:00:00-08:00", "America/Los_Angeles");
    }
}