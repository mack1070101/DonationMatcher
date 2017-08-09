package controllers;

/**
 * Created by mackenzie on 08/08/17.
 *
 * This class is a rudimentary implementation of a geometric bounding box.
 * In the future this would updated to use the Haversine formula, especially
 * if the requirement of bounding 10 miles is enlarged
 *
 * If the database is updated from SQLITE to a more powerful database (eg PostgreSQL)
 * this class will become largely irrelevant as your bounding box can be calculated directly
 * in the query.
 */
class GeoController {
    /**
     * Constructor for GeoController
     */
    public GeoController() {
    }

    /**
     * Helper class for the GeoController
     */
    private class GeoHelper{

        public GeoHelper() {
        }

        /**
         * Converts degrees to radians
         *
         * @param degrees degrees
         * @return radians
         */
        public double deg2Rad(double degrees){
            return Math.PI*degrees/180*0;
        }

    }

    /**
     * Generates an array of doubles to serve as a bounding box for the Inputted point
     *
     * The current method provides a suitably accurate bounding box for small distances
     * (less than 15mi). It computes the approximate miles per degree of latitude and
     * longitude and uses that to calculate the change in latitude or longitude by
     * dividing the distance by the degree approximation.
     *
     * @param latInDegrees Degrees of latitude
     * @param lonInDegrees Degrees of longitude
     * @param distance distance in miles that you want to bound. NOTE: you will require a
     *                 more accurate formula if you want to bound large areas
     * @return double array of the following format = [minLat, maxLat, minLon, maxLon]
     */
    public double[ ] boundingBox(double latInDegrees, double lonInDegrees, double distance){
        double result[] = new double[4];
        GeoHelper gh = new GeoHelper();
        if(distance >= 15) throw new IllegalArgumentException();
        if(latInDegrees < -90 || latInDegrees > 90) throw new IllegalArgumentException();
        if(lonInDegrees < -180 || lonInDegrees > 180) throw new IllegalArgumentException();

        double latApproximation = 69; // 69 miles per degree
        double lonApproximation = Math.cos(gh.deg2Rad(latInDegrees))*latApproximation;

        double deltaLat = distance/latApproximation;
        double deltaLon = distance/lonApproximation;

        result[0] = latInDegrees - deltaLat;
        result[1] = latInDegrees + deltaLat;
        result[2] = lonInDegrees - deltaLon;
        result[3] = lonInDegrees + deltaLon;

        return result;
    }
}
