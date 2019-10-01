// Yuki Hinaga, CS 201, Section 01, 12/6/2018  

package project;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import project.GeoLocation;
import project.CTARoute;

public class CTAStation_Client {
	//
	//  This class, CTAStation_Client, is the main application class, including the menu options.
	//  It does specifically the following: computing the path between two stations in path(), providing the menu options to do
	//  displaying all stations, displaying wheelchair access, displaying the nearest station, displaying info of a specific station,
	//  displaying info of all stations, adding a new station, deleting a station, showing the path from one station to another,
	//  and exit.
	// 

	static HashMap <String, CTARoute> Routes = new HashMap<String, CTARoute>();
	//static ArrayList<CTARoute> Routes = new ArrayList<CTARoute>(); // all the lines are loaded while initializing from the input file
	static CTARoute redline = new CTARoute("RED", new ArrayList<CTAStation>()); 
	static CTARoute greenline = new CTARoute("GREEN", new ArrayList<CTAStation>());
	static CTARoute blueline = new CTARoute("BLUE", new ArrayList<CTAStation>());
	static CTARoute brownline = new CTARoute("BROWN", new ArrayList<CTAStation>());
	static CTARoute purpleline = new CTARoute("PURPLE", new ArrayList<CTAStation>());
	static CTARoute pinkline = new CTARoute("PINK", new ArrayList<CTAStation>());
	static CTARoute orangeline = new CTARoute("ORANGE", new ArrayList<CTAStation>());
	static CTARoute yellowline = new CTARoute("YELLOW", new ArrayList<CTAStation>());
	static Scanner sc = null;
	static ArrayList<CTARoute> routes = new ArrayList<CTARoute>(); 
	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws Exception{ // main, handling the menu options
		initialize("src/project/CTAStops.csv");
		initializeLines();
		while(true) {
			System.out.println("Choose either:\n" +   // displaying the option menu and prompting the user's input.
					"1. Display the names of all stations\n" + 
					"2. Display the stations with wheelchair access\n" + 
					"3. Display the nearest station to a location\n" + 
					"4. Display information for a station with a specific name\n" + 
					"5. Display information for all stations\n" + 
					"6. Add a new station\n" + 
					"7. Delete an existing station\n" + 
					"8. Show the path from one station to another\n" +
					"9. Exit");
			String ch = input.nextLine();
			if(ch.equals("1") || ch.equalsIgnoreCase("Display the station names")){ 
				for(CTARoute route: routes) {
					System.out.println(route.getName()+":");
					route.displayStationNames(); // calling displayStationNames method
					System.out.println();
				}
			} else if (ch.equals("2") || ch.equalsIgnoreCase("Display the wheelchair accessibility")) {
				boolean flag = true;
				while(flag) {
					try {
						flag = true;
						System.out.println("Choose either: 1. Wheelchair accessible stations or 2. Wheelchair inaccessible stations. Choose 1 or 2.");
						String ch2 = input.nextLine();
						for(CTARoute route: routes) { //displaying stations with each line
							System.out.println(route.getName()+":"); 
							if(ch2.equals("1") ||ch2.equalsIgnoreCase("Wheelchair accessible stations")) {
								CTAStation.displayByWeelchair(route.getStops(),true);	// calling displayByWheelchair method
							} else if (ch2.equals("2") ||ch2.equalsIgnoreCase("Wheelchair inaccessible stations")) {
								CTAStation.displayByWeelchair(route.getStops(),false);	
							}
							System.out.println();
						}
					} catch (Exception e) {
						flag = false;
						System.out.println("Invalid input. Try again.");
					}
				}
			} else if (ch.equals("3") || ch.equalsIgnoreCase("Display the nearest station")) {
				displayNearestStation();
			} else if(ch.equals("4") || ch.equalsIgnoreCase("Display information for a station with a specific name")){
				displaySpecificStation();
			} else if (ch.equals("5") || ch.equalsIgnoreCase("Display information for all stations")) {
				for(CTARoute route: routes) {
					System.out.println(route.getName()+":");
					for(CTAStation station: route.getStops()) {
						System.out.println(station); // displaying the information of all stations.
					}
				}
			} else if (ch.equals("6") || ch.equalsIgnoreCase("Add a new station")) {
				addStation();
			}else if (ch.equals("7") || ch.equalsIgnoreCase("Delete an existing station")) {
				deleteStation();
			} else if (ch.equals("8")||ch.equalsIgnoreCase("Show the path from one station to another")){
				getPath();
			} else if (ch.equals("9") || ch.equalsIgnoreCase("Exit")) { // exit
				System.out.println("Thank you!");
				break;
			} else {
				System.out.println("Check your input again."); // in case the choice is invalid
				System.out.println("");
			}
		}
	}

	public static void initialize(String filename) { //method to read a file and initialize the arraylist
		try {
			File cta = new File(filename); // reading a file that contains data of stations
			sc = new Scanner(cta);	
		} catch(FileNotFoundException e) { // implementing a try and catch method in case a file is not found
			System.out.println("File not found");
		}

		Routes.put("red",redline); //adding stations on the red line to the hashmap Routes (the following does that for each line)
		Routes.put("green",greenline);
		Routes.put("blue",blueline);
		Routes.put("brown",brownline);
		Routes.put("purple",purpleline);
		Routes.put("pink",pinkline);
		Routes.put("orange",orangeline);
		Routes.put("yellow",yellowline);

		routes.add(redline);
		routes.add(greenline);
		routes.add(blueline);
		routes.add(brownline);
		routes.add(purpleline);
		routes.add(pinkline);
		routes.add(orangeline);
		routes.add(yellowline);
	}

	public static void initializeLines() { // method to initialize lines
		int i = 0;
		while (sc.hasNextLine()) { // skipping the first and second line of the data (unnecessary when reading a file)
			if(i<=1) {
				i++;
				sc.nextLine();
				continue;
			}
			String s = sc.nextLine();
			String[] details = s.split(","); // separate the list by commas
			boolean isWheelchairOK = false;
			if(details[4].equals("TRUE")) {
				isWheelchairOK = true;
			}
			//storing data of each column to variables
			double lat = Double.parseDouble(details[1]); 
			double lng = Double.parseDouble(details[2]);
			int redpos = Integer.parseInt(details[5]);
			int greenpos = Integer.parseInt(details[6]);
			int bluepos = Integer.parseInt(details[7]);
			int brownpos = Integer.parseInt(details[8]);
			int purplepos = Integer.parseInt(details[9]);
			int pinkpos = Integer.parseInt(details[10]);
			int orangepos = Integer.parseInt(details[11]);
			int yellowpos = Integer.parseInt(details[12]);


			if(redpos > -1) { //add stations that are on red lines (number bigger than -1) (does this for each line)
				CTAStation station = new CTAStation(details[0],details[3],isWheelchairOK,lat,lng,redpos);
				redline.addStation(station);
			}
			if(greenpos > -1) {
				CTAStation station = new CTAStation(details[0],details[3],isWheelchairOK,lat,lng,greenpos);
				greenline.addStation(station);
			}

			if(bluepos > -1) {
				CTAStation station = new CTAStation(details[0],details[3],isWheelchairOK,lat,lng,bluepos);
				blueline.addStation(station);
			}

			if(brownpos > -1) {
				CTAStation station = new CTAStation(details[0],details[3],isWheelchairOK,lat,lng,brownpos);
				brownline.addStation(station);
			}

			if(purplepos > -1) {
				CTAStation station = new CTAStation(details[0],details[3],isWheelchairOK,lat,lng,purplepos);
				purpleline.addStation(station);
			}

			if(pinkpos > -1) {
				CTAStation station = new CTAStation(details[0],details[3],isWheelchairOK,lat,lng,pinkpos);
				pinkline.addStation(station);
			}

			if(orangepos > -1) {
				CTAStation station = new CTAStation(details[0],details[3],isWheelchairOK,lat,lng,orangepos);
				orangeline.addStation(station);
			}

			if(yellowpos > -1) {
				CTAStation station = new CTAStation(details[0],details[3],isWheelchairOK,lat,lng,yellowpos);
				yellowline.addStation(station);
			}
		}
	}

	public static void displayNearestStation() {// method to display nearest station
		boolean flag = false;
		double lat = 0;
		double lng = 0;
		while(!flag) {
			try { // implementing try and catch
				flag = true;
				System.out.println("Enter the latitude: ");
				lat = Double.parseDouble(input.nextLine());
				System.out.println("Enter the longitude: ");
				lng = Double.parseDouble(input.nextLine());
			} catch(NumberFormatException ex){
				flag = false;
				System.out.println("Incorrect input. Try again"); // in case the input is invalid
			}	
		}
		GeoLocation g1 = new GeoLocation(lat,lng);
		ArrayList<CTAStation> closeststations = new ArrayList<CTAStation>();
		for(CTARoute route: routes) {
			CTAStation station = CTAStation.displayNearest(route.getStops(), g1); // calling displayNearest method
			closeststations.add(station);
		}
		System.out.println(CTAStation.displayNearest(closeststations, g1).getName());
	}

	public static void displaySpecificStation() { // method to display info for specific stations
		int count = 0;
		System.out.println("Enter the name of the station: ");
		String name = input.nextLine();
		for(CTARoute route: routes) {
			for(CTAStation station: route.getStops()) {
				if (station.getName().equals(name)) { //displaying the information of the station which name equals the user's input
					System.out.println(route.getName());
					System.out.println(station);
					count++;
					break;
				} 
			}	
		}
		if(count == 0) {
			System.out.println("No station found. Enter a valid station name"); // in case the station not found.	
		}
	}

	public static void addStation() { // method to add station
		boolean flag = false;
		while(!flag) {
			try { // implementing try and catch for all inputs
				flag = true;
				System.out.println("Enter the name of the station"); // getting the information of a new station
				String name = input.nextLine();
				System.out.println("Enter the latitude of the station");
				double lat = Double.parseDouble(input.nextLine());
				System.out.println("Enter the longtitude of the station");
				double lng = Double.parseDouble(input.nextLine());
				System.out.println("Enter the location of the station");
				String location = input.nextLine();
				System.out.println("Enter the wheelchair accessibility of the station");
				String wheelchair = input.nextLine();
				boolean isWheelchair = false;
				if(wheelchair.equalsIgnoreCase("TRUE")) {
					isWheelchair = true;
				}
				System.out.println("Enter the position on the Red line. If it is not on the Red line, enter -1"); // getting the position on each line
				int redpos = input.nextInt();
				System.out.println("Enter the position on the Green line. If it is not on the Green line, enter -1");
				int greenpos = input.nextInt();	
				System.out.println("Enter the position on the Blue line. If it is not on the Blue line, enter -1");
				int bluepos = input.nextInt();	
				System.out.println("Enter the position on the Brown line. If it is not on the Brown line, enter -1");
				int brownpos = input.nextInt();	
				System.out.println("Enter the position on the Purple line. If it is not on the Purple line, enter -1");
				int purplepos = input.nextInt();
				System.out.println("Enter the position on the Pink line. If it is not on the Pink line, enter -1");
				int pinkpos = input.nextInt();	
				System.out.println("Enter the position on the Orange line. If it is not on the Orange line, enter -1");
				int orangepos = input.nextInt();	
				System.out.println("Enter the position on the Yellow line. If it is not on the Yellow line, enter -1");
				int yellowpos = input.nextInt();	

				if(redpos>-1) { // adding the station to each line in a correct order
					CTAStation station = new CTAStation(name,location,isWheelchair,lat,lng,redpos);
					redline.insertStation(redpos, station); // calling insertStation method to add
				}
				if(greenpos>-1) {
					CTAStation station = new CTAStation(name,location,isWheelchair,lat,lng,greenpos);
					greenline.insertStation(greenpos, station);
				}
				if(bluepos>-1) {
					CTAStation station = new CTAStation(name,location,isWheelchair,lat,lng,bluepos);
					blueline.insertStation(bluepos, station);
				}
				if(brownpos>-1) {
					CTAStation station = new CTAStation(name,location,isWheelchair,lat,lng,brownpos);
					brownline.insertStation(brownpos, station);
				}
				if(purplepos>-1) {
					CTAStation station = new CTAStation(name,location,isWheelchair,lat,lng,purplepos);
					purpleline.insertStation(purplepos, station);
				}
				if(pinkpos>-1) {
					CTAStation station = new CTAStation(name,location,isWheelchair,lat,lng,pinkpos);
					pinkline.insertStation(pinkpos, station);
				}
				if(orangepos>-1) {
					CTAStation station = new CTAStation(name,location,isWheelchair,lat,lng,orangepos);
					orangeline.insertStation(orangepos, station);
				}
				if(yellowpos>-1) {
					CTAStation station = new CTAStation(name,location,isWheelchair,lat,lng,yellowpos);
					yellowline.insertStation(yellowpos, station);
				}
			}catch(Exception e) {
				flag = false;
				System.out.println("Incorrect input. Try again"); // in case the input is invalid
			}
		}
	}

	public static void deleteStation() { // method to delete station
		boolean flag = false;
		while(!flag) {
			try { // implementing try and catch for all inputs
				flag = true;
				System.out.println("Enter the name of the station to delete: "); // getting the information of the station to delete
				String name = input.nextLine();
				System.out.println("Enter the line the station belongs to: ");
				String line = input.nextLine();

				if(line.equalsIgnoreCase("Red")){ // deleting a station from a correct line
					redline.removeStation(name); // calling removeStation method
				}

				if(line.equalsIgnoreCase("Green")){
					greenline.removeStation(name);	
				}

				if(line.equalsIgnoreCase("Blue")){
					blueline.removeStation(name);	
				}
				if(line.equalsIgnoreCase("Brown")){
					brownline.removeStation(name);	
				}
				if(line.equalsIgnoreCase("Purple")){
					purpleline.removeStation(name);	
				}
				if(line.equalsIgnoreCase("Pink")){
					pinkline.removeStation(name);	
				}
				if(line.equalsIgnoreCase("Orange")){
					orangeline.removeStation(name);	
				}
				if(line.equalsIgnoreCase("Yellow")){
					yellowline.removeStation(name);	
				}

			}catch(Exception e) {
				flag = false;
				System.out.println("Incorrect input. Try again"); // in case the input is invalid
			}
		}
	}

	public static void getPath() { //method to get path
		boolean flag = false;
		while(!flag) {
			try {
				boolean isOriginSelected = false; 
				CTAStation origin = null;//starting station
				String line1 = null;
				while(!isOriginSelected) {
					isOriginSelected = true;
					String lines = "";
					CTAStation temp = null;
					System.out.println("Enter the station you are at: "); //the current station
					String current = input.nextLine();
					int count = 0;
					for(CTARoute route: routes) { // find a line to which a starting station belongs
						temp = route.getStation(current);
						if(temp != null) {
							origin = temp;
							count++;
							lines += route.getName() + ",";
							line1 = route.getName().toLowerCase();
						}
					}
					if(count==0) { //no station found
						isOriginSelected = false;
						System.out.println("No station found. Try again");
					}
					if(count >1) { // station on multiple lines
						System.out.println("Found multiple stations for the name entered on " + lines);
						System.out.println("Please select the line: ");
						String line = input.nextLine().toLowerCase();
						origin = Routes.get(line).getStation(current);
						line1 = line;
					}
				}
				boolean isDestinationSelected = false;
				CTAStation destination = null; //destination
				String line2 = null;
				while(!isDestinationSelected) { 
					isDestinationSelected = true;
					String lines = "";
					CTAStation temp;
					System.out.println("Enter the station you are going to: "); //the destination
					String station2 = input.nextLine();
					int count = 0;
					for(CTARoute route: routes) { // find a line to which a end station belongs
						temp = route.getStation(station2);
						if(temp != null) {
							destination = temp;
							count++;
							lines += route.getName() + ",";
							line2 = route.getName().toLowerCase();
						}
					}
					if(count==0) { // no station found
						isDestinationSelected = false;
						System.out.println("No station found. Try again");
					}
					if(count >1) { // multiple station found
						System.out.println("Found multiple stations for the name entered on " + lines);
						System.out.println("Please select the line: ");
						String line = input.nextLine().toLowerCase();
						line2 = line;
						destination = Routes.get(line).getStation(station2);
					}
				}
				String results = CTAStation_Client.path(origin, destination, line1, line2); //calling path method
				System.out.println(results);
				System.out.println("Do you want to save the path to the outputfile? Answer Y/N");
				String A = input.nextLine();
				if(A.equalsIgnoreCase("Y")) {
					System.out.println("Enter a file name: "); //writing an output file
					String filename = input.nextLine();
					File file = new File(filename);
					FileOutputStream fop;
					try{ // implementing try and catch
						fop = new FileOutputStream(file);
						if (!file.exists()) {
							file.createNewFile();
						}
						byte[] contentInBytes;
						contentInBytes = results.getBytes();
						fop.write(contentInBytes);
						fop.close();
						System.out.println("Done");
						break;
					} catch (IOException e) {
						System.out.println("Invalid input. Try again:");
					}
				} break;
			} catch(Exception e) {
				System.out.println("Invalid input. Try again");
			}
		}
	}

	public static String path(CTAStation station1, CTAStation station2, String line1, String line2) {  //computing the path between two locations
		CTARoute startRoute = Routes.get(line1);
		CTARoute endRoute = Routes.get(line2);
		String results = "Take " + startRoute.getName() + " at station: " + station1.getName();
		if(startRoute == endRoute) {
			results += ("\nGet off the train at: " + station2.getName());
			return results;
		} 
		for(CTAStation r: endRoute.getStops()) { 
			if(startRoute.hasStation(r)) { //if has a common station (transit) 
				results += ("\nTransfer to " + endRoute.getName() + " at station: " + r.getName());
				results += ("\nGet off the train at: " + station2.getName());
				return results;
			} else {
				for(Map.Entry<String, CTARoute> item: Routes.entrySet()) { // when there is two time transit
					CTARoute mr = item.getValue();
					if(!(mr.getName().equals(startRoute.getName()) || mr.getName().equals(endRoute.getName()))) { //select the lines that don't have initial or final stops
						for(CTAStation s: mr.getStops()) {  //for each stop on this line, check if transfer can be made to line containing initial stop
							if(startRoute.hasStation(s)) {
								for(CTAStation t: mr.getStops()) { //for each stop on this line, check if transfer can be made to line containing final stop
									if(endRoute.hasStation(t)) {
										results = ("Take " + startRoute.getName() + " at station: " + station1.getName()); 
										results += ("\nTransfer to " + mr.getName() + " at station: " + s.getName());
										results += ("\nTransfer to " + endRoute.getName() + " at station: " + t.getName());
										results += ("\nGet off the train at: " + station2.getName());
										return results;
									}
								}
							}
						}
					}
				}
			}
		}
		return "No path found";
	}

}

/* Test and Results
 * 1. Display the name of the stations: test by comparing the output with the text file
 *    The output shows all of the names of the statons in the list.
 * 
 * 2. Display the wheelchair access: test by comparing the output with the text file
 * 	  The output shows all of the names for both wheelchair-accessible stations and non-accessible stations.
 * 
 * 3. Display the nearest station to a location: test by calculating the distance 
 * 	  using the distance formula and by comparing the closest station with the output
 *    The output seems working well. Here is some examples...
 *    Input: lat 41.886784, lng -87.785628 Output: Ridgeland CORRECT
 *    Input: lat 41.8854,   lng -87.655    Output: Morgan CORRECT
 *    Input: lat 41.8361963 lng -87.626559 Output: 35th-Bronzeville-IIT CORRECT
 * 
 * 
 * 4. Display information for a station with a specific name: test by checking the output with the input 
 *    and also by comparing the output with the text file.
 *    The output shows the correct information of the station entered. If there are multiple stations for the same name,
 *    it outputs information for both.
 *    
 * 5. Display information for all stations: test by comparing the output with the text file.
 *    The output shows all of the stations and information correctly.
 * 
 * 6. Add a new station: test by searching the name of the new station using the choice 4, 
 *    by comparing the output with the input, and by checking the order (position) by comparing the output
 *    with the input using the choice 1.
 *    It works. Here is an example...
 *    The name: Tokyo, lat: 41.831577, lng: -87.626026, location: elevated, wheelchair: true
 *    {red, green, blue, brown, purple, pink, orange, yellow} = {-1,20,-1,-1,-1,-1,-1,-1}
 *    When searching Tokyo, it outputs:
 *    GREEN
      CTAStation [name=Tokyo, location=elevated, opened=false, wheelchair=true, geolocation=(41.831577, -87.626026), pos=20]
 *    
 *    
 * 7. Delete an existing station: test by searching the name of the station deleted.
 *    It works. Here is an example...
 *    The name to delete: Ashland, The line the station belongs to: green
 *    Try displaying with specific names...
 *    Searching Ashland, it outputs only orange and pink but not green.
 *    PINK
      CTAStation [name=Ashland, location=elevated, opened=false, wheelchair=true, geolocation=(41.885268, -87.666969), pos=11]
      ORANGE
      CTAStation [name=Ashland, location=elevated, opened=false, wheelchair=true, geolocation=(41.839234, -87.665317), pos=5]
 * 
 * 8. Display the trains and the stations to another location: test by finding the nearest station from the 
 *    current location and the nearest station from the destination and by checking the map to confirm the 
 *    output is correct.
 *    I just did a path from a station to another.
 *    Here are examples:
 *    Starting station: Roosevelt (green or red), Destination Harlem (green or red): CORRECT
 *    Take GREEN at station: Roosevelt
	  Get off the train at: Harlem
 *    
 *    Starting station: Rosemont (blue), Destination: Roosevelt (green or red): CORRECT
 *    Take BLUE at station: Rosemont
	  Transfer to RED at station: Addison
	  Get off the train at: Roosevelt
 *    
 *    Starting station: Dempster-Skokie (yellow), Destination: Rosemont (blue): CORRECT
 *    Take YELLOW at station: Dempster-Skokie
      Transfer to RED at station: Howard
      Transfer to BLUE at station: Jackson
      Get off the train at: Rosemont
 *    
 *    When the user chooses to create an output file, it successfully did so in the Finder.
 *    
 * 9. Exit: test by entering exit or 9 and by checking the outcome.
 * 	  It exits.
 * 
 */
