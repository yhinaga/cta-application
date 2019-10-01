# Chicago Transit Authority (CTA) Model

## 1. Date
   October 2018 - December 2018
   
## 2. Location
   Chicago, IL
   
## 3. Creater
   Yuki Hinaga (Bacheloer of Science in Computer Science at Illinois Institute of Technology)
   
## 4. Summary of the Project
   - Created a program in Java that allows the user to create, modify, remove, and search CTA stations and their information.
   - Implemented the features to find the closest station from the current location and to generate a path between two stations which could include multiple transits.
   - Developed a deep understanding of Object-Oriented Programming, including classes, objects, inheritance, and exception-handling.

## 5. Major Files
   - CTARoute.java: class for the following actions:
   	 - displaying the stations, 
   	 - adding stations, 
   	 - removing stations, 
   	 - inserting stations, 
   	 - looking up stations, and 
   	 - displaying the nearest stations.
   - CTAStation.java: class with the information and attributes of the stations
   - CTAStation_Client.java: main application class with the menu options. Class for the following actions:
     - computing the path between two stations in path(),
     - providing the menu options to do displaying all stations,
     - displaying wheelchair access, displaying the nearest station, 
     - displaying info of a specific station, 
     - displaying info of all stations, 
     - adding a new station, 
     - deleting a station, 
     - showing the path from one station to another, and
     - exiting.