package controllers;

import org.junit.Test;

/**
 * Created by mackenzie on 08/08/17.
 */
@SuppressWarnings("unused")
public class GeoControllerTest {
    private final double validLat = 37.809052;
    private final double validLon = -122.483365;
    private final double distance = 10.0; //miles as per requirement

    @Test
    public void boundingBox() throws Exception {
        GeoController gc  = new GeoController();
        double[] bounds = gc.boundingBox(validLat, validLon, distance);
        //TODO validate output better
    }

    @Test(expected = IllegalArgumentException.class)
    public void boundingBox_badLat() throws Exception{
         double[] bounds = new GeoController().boundingBox(-91.2, validLon, distance);
    }

    @Test(expected = IllegalArgumentException.class)
    public void boundingBox_badLon() throws Exception{
         double[] bounds = new GeoController().boundingBox(validLat, 181.2, distance);
    }


    @Test(expected = IllegalArgumentException.class)
    public void boundingBox_badDistance() throws Exception{
         double[] bounds = new GeoController().boundingBox(validLat, validLon, 20.0);
    }
}