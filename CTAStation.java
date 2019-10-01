// Yuki Hinaga, CS 201, Section 01, 12/6/2018 

package project;

import java.io.File;

import java.util.ArrayList;
import java.util.Scanner;

import project.GeoLocation;

public class CTAStation extends GeoLocation { // inheritance
	// This class, CTAStation, has information / attributes of the stations.

	private String name; //Declaring the instance variables.
	private String location;
	private boolean opened;
	private boolean wheelchair;
	private GeoLocation geolocation;
	private int pos;


	public int getPos() { //getters for the position (accessor)
		return pos;
	}

	public void setPos(int pos) { // setters for the position (mutator)
		this.pos = pos;
	}

	public String getName() { // getters for the name (accessor)
		return name;
	}

	public void setName(String name) { // setters for the name (mutator)
		this.name = name;
	}

	public String getLocation() { // getters for the location (accessor)
		return location;
	}

	public void setLocation(String location) { // setters for the location (mutator)
		this.location = location;
	}

	public boolean isOpened() { // accessor for the openness 
		return opened;
	}

	public void setOpened(boolean opened) { // setters for the openness (mutator)
		this.opened = opened;
	}

	public boolean isWheelchair() { // accessor for wheelchair accessibility
		return wheelchair;
	}

	public void setWheelchair(boolean wheelchair) { // mutator for wheelchair accessibility
		this.wheelchair = wheelchair;
	}

	public GeoLocation getGeolocation() { // accessor for geolocation
		return geolocation;
	}

	public void setGeolocation(GeoLocation geolocation) { // mutator for geolocation
		this.geolocation = geolocation;
	}


	@Override
	public String toString() { // toString method that displays the information of the stations
		return "CTAStation [name=" + name + ", location=" + location + ", opened=" + opened + ", wheelchair="
		+ wheelchair + ", geolocation=" + geolocation + ", pos=" + pos + "]";
	}

	@Override
	public boolean equals(Object obj) { //equals method
		if (obj == this) { 
			return true; 
		} 

		if (!(obj instanceof CTAStation)) { 
			return false; 
		} 
		CTAStation c = (CTAStation) obj; 

		if(!c.getName().equals(this.getName())||!c.getLocation().equals(this.getLocation())||c.isWheelchair()!=this.wheelchair||c.isOpened()!=c.isOpened()||!c.getGeolocation().equals(this.getGeolocation())||c.getPos()!=this.pos) {
			return false;
		} return true;
	}

	public CTAStation(String name, String location, boolean wheelchair, double lat, double lng, int pos) { // non-default constructor
		this.name = name;
		this.location = location;
		this.wheelchair = wheelchair;
		this.geolocation = new GeoLocation(lat, lng);
		this.pos = pos;
	}

	public static void displayStationNames(ArrayList<CTAStation> stations) { //displaying all the station names in the arraylist
		for(CTAStation station: stations) {
			System.out.println(station.name);
		}
	}

	public static void displayByWeelchair(ArrayList<CTAStation> stations, boolean withWheelchair) { //displaying wheelchair accessibility
		if(withWheelchair) {
			System.out.println("Following stations are wheelchair-acessible: ");
		} else {
			System.out.println("Following stations are not wheelchair-acessible: ");
		}
		for(CTAStation station: stations) { 
			if(station.wheelchair == withWheelchair) {
				System.out.println(station.name);	
			}
		}
	}

	public static CTAStation displayNearest(ArrayList<CTAStation> stations) { //displaying the nearest station using lat and lng
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the latitude: ");
		double lat = Double.parseDouble(input.nextLine());
		System.out.println("Enter the longitude: ");
		double lng = Double.parseDouble(input.nextLine());

		GeoLocation g1 = new GeoLocation(lat,lng);
		CTAStation closest = stations.get(0);
		double mindistance = GeoLocation.caicDistance(g1, closest.geolocation); 

		for(CTAStation station: stations) {
			double distance = GeoLocation.caicDistance(g1, station.geolocation); // uses distance formula to calculate 
			if(distance<mindistance) {
				mindistance = distance;
				closest = station;
			}
		}

		return closest;

	}

	public static CTAStation displayNearest(ArrayList<CTAStation> stations, GeoLocation location) { // displaying the nearest station using geolocation
		if(stations.size()==0) {
			System.out.println("Error: station list is empty");
		}
		CTAStation closest = stations.get(0);
		double mindistance = GeoLocation.caicDistance(location, closest.geolocation);

		for(CTAStation station: stations) {
			double distance = GeoLocation.caicDistance(location, station.geolocation); // uses distance formula to calculate
			if(distance<mindistance) {
				mindistance = distance;
				closest = station;
			}
		}

		return closest;

	}

}
