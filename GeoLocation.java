/* Yuki Hinaga, CS 201, Sec 1, 12/6/2018*/ 

package project;

public class GeoLocation {
	
// This class, GeoLocation, is with the latitude and the longitude of stations, calculating the distance between two locations.

	
	private double lat; //declaring instance variables (lat and lng)
	private double lng;
	
	
	public GeoLocation() { //default constructor
		lat = 23.0;
		lng = 45.0;
	}
	
	public GeoLocation(double lat, double lng) { // non-default constructor
		this.lat = lat;
		this.lng = lng;
	}
	
	public void setLat(double lat) { //setter for lat
		this.lat = lat;
	}
	
	public void setLng(double lng) { // setter for lng
		this.lng = lng;
	}
	
	public double getLat() { // getter for lat
		return lat;
	}
	
	public double getLng() { // getter for lng
		return lng;
	}
	
	public String toString() { // toString method
		return "(" + lat + ", " + lng + ")";
	}
	
	public boolean equals(GeoLocation g) { //equals method
		if(lat < -90 || lat > 90.8) {
			return false;
		} else if (lng < -180 || lng > 180.9) {
			return false;
		} 
		return true;
	}
	
	public static double caicDistance(GeoLocation g1, GeoLocation g2) { // calculating the distance between two locations
		return Math.sqrt(Math.pow(g1.lat-g2.lat,2)+Math.pow(g1.lng-g2.lng,2));
	}
}