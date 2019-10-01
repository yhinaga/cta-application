// Yuki Hinaga, CS 201, Section 01, 12/06/2018  

package project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import project.GeoLocation;
import project.CTAStation;

public class CTARoute {

	//
	// This class, CTARoute, contains major methods to do the following:
	// displaying the stations, adding stations, removing stations, inserting stations, looking up stations, 
	// displaying the nearest stations.
	//


	private String name;   // instance variables (name of lines)
	private ArrayList<CTAStation> stops; //secondary list

	public String getName() { //accessor for name
		return name;
	}
	public void setName(String name) { // mutator for name
		this.name = name;
	}
	public ArrayList<CTAStation> getStops() { // accessor for the arraylist
		return stops;
	}
	public void setStops(ArrayList<CTAStation> stops) { // mutator for the arraylist
		this.stops = stops;
	}

	@Override
	public String toString() { // toString
		return "CTARoute [name=" + name + ", stops=" + stops + ", toString()=" + super.toString() + "]";
	}


	@Override //equals method
	public boolean equals(Object obj) {
		if (obj == this) { 
			return true; 
		} 

		if (!(obj instanceof CTAStation)) { 
			return false; 
		} 
		CTAStation c = (CTAStation) obj; 

		if(!c.getName().equals(this.getName())) {
			return false;
		} return true;
	}


	public CTARoute(String name, ArrayList<CTAStation> stops){ //non-default constructor
		this.name = name;
		this.stops = stops;
	}


	public void displayStationNames() { 
		for(CTAStation station: stops) {
			System.out.println(station.getName());  // get names from the arraylist and display
		}
	}

	public void addStation(CTAStation station) { // method to add stations
		stops.add(station);
	}

	public void removeStation (String stationName) { // method to remove stations
		for(CTAStation station: stops) {
			if(station.getName().equals(stationName)) {
				int pos = station.getPos();
				for(CTAStation st: stops) {
					if(st.getPos()>pos) { //
						st.setPos(st.getPos()-1); // adjust the order by subtracting 1 from the positions that are after the position deleted
					}
				}
				stops.remove(station); // remove station
				System.out.println(stationName + " has been removed from " + name);
				return;
			}
		}
		System.out.println("The station not found"); // in case the station does not exist
	}

	public void insertStation(int position, CTAStation station) { // method to insert stations
		station.setPos(position);
		for(CTAStation st: stops) {
			if(st.getPos()>=position) {
				st.setPos(st.getPos()+1); //adjust the order by adding 1 to the positions that are after the position added
			}
		}
		addStation(station);
	}

	public CTAStation lookupStation(String name) {
		for(CTAStation station: stops) { 
			if(station.getName().equals(name)) {
				return station;
			}
		}
		return null;
	}

	public CTAStation nearestStation(double lat, double lng){ // method to return the nearest station (default)
		GeoLocation g1 = new GeoLocation(lat,lng);
		CTAStation closest = stops.get(0);
		double mindistance = GeoLocation.caicDistance(g1, closest.getGeolocation());

		for(CTAStation station: stops) {
			double distance = GeoLocation.caicDistance(g1, station.getGeolocation());
			if(distance<mindistance) {
				mindistance = distance;
				closest = station;
			}
		}
		return closest;

	}

	public CTAStation nearestStation(GeoLocation location){ // overload method to return the nearest station based on location
		CTAStation closest = stops.get(0);
		double mindistance = GeoLocation.caicDistance(location, closest.getGeolocation());

		for(CTAStation station: stops) {
			double distance = GeoLocation.caicDistance(location, station.getGeolocation());
			if(distance<mindistance) {
				mindistance = distance;
				closest = station;
			}
		}
		return closest;
	}

	public boolean hasStation(CTAStation station) { //method to check if a certain station is in a line
		for(CTAStation s: stops) {
			if(s.getName().equals(station.getName())) {
				double distance = GeoLocation.caicDistance(station.getGeolocation(), s.getGeolocation());
				if(distance <= 0.001) {
					return true;
				}	
			}
		}
		return false;
	}

	public CTAStation getStation(String name) { //method to get a station with a name
		for(CTAStation station: stops) {
			if(station.getName().equals(name)) {
				return station;
			}
		}
		return null;
	}
}
