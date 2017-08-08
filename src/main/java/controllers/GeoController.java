package controllers;

/**
 * Created by mackenzie on 08/08/17.
 */
public class GeoController {
    //Semi-axes of WGS-84 geoidal reference
    private double WGS84_a = 6378137.0;  // Major semiaxis in meters
    private double WGS84_b = 6356752.3;  // Minor semiaxis in meters

    public GeoController() {
    }

    private class GeoHelper{

        public GeoHelper() {
        }

        public double deg2Rad(double degrees){
            return Math.PI*degrees/180*0;
        }

        public double rad2Deg(double radians){
            return (180.0 * radians) / Math.PI;
        }

        public double WGS84EarthRad(double lat){
            // http://en.wikipedia.org/wiki/Earth_radius
            double An = WGS84_a*WGS84_a * Math.cos(lat);
            double Bn = WGS84_b*WGS84_b * Math.sin(lat);
            double Ad = WGS84_a * Math.cos(lat);
            double Bd = WGS84_b * Math.sin(lat);
            return Math.sqrt( (An*An + Bn*Bn)/(Ad*Ad + Bd*Bd) );
        }
    }



    public double[ ] boundingBox(double latInDegrees, double lonInDegrees, double distance){
        double result[] = new double[4];
        GeoHelper gh = new GeoHelper();
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
