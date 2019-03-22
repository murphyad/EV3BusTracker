package transit;


import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import lejos.utility.TextMenu;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.MovePilot;

/**
 * Class that runs the program and contains the menus
 * for the EV3
 * @author murphyad
 *
 */
public class EV3 {
	private static Controller controller = new Controller();
	private static boolean imported;
	private static RegulatedMotor leftMotor = Motor.C;
	private static RegulatedMotor rightMotor = Motor.B;
	private static MovePilot pilot;
	private static EV3UltrasonicSensor ultrasonicSensor;
	private static SampleProvider sensorProvider;
	private static float wheelDiameter = 5.0f;
	private static float trackWidth = 25.0f;
	private static double speed = 20;
	private static double rotateSpeed = 40;
	private static double delayTime = 0;
	private static String[] digits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Done"};
    private static int passengers;
    private static boolean trackPassengers = false;
	
	
	public static void main(String[] args){
		ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S4);
		sensorProvider = ultrasonicSensor.getDistanceMode();
		pilot = new MovePilot(wheelDiameter,trackWidth,leftMotor,rightMotor);
		String[] mainMenu = { "Import", "Export", "Search", "Edit", "Movement" };
		controller.initialize(); // might be bad
		imported = false;
		while(true){
			LCD.clearDisplay();
			LCD.refresh();
			switch((new TextMenu(mainMenu, 1, "Main Menu")).select()){
			case 0:
				importMenu();
				break;
			case 1:
				if(imported){
					exportFiles();
				}else{
					LCD.clearDisplay();
					LCD.refresh();
					display("You should probably import before you try to export!");
					Button.ENTER.waitForPress();
				}
				
				break;
			case 2:
				if(imported){
					searchMenu();
				}else{
					LCD.clearDisplay();
					LCD.refresh();
					display("You should probably import before you try to search!");
					Button.ENTER.waitForPress();
				}
				break;
			case 3:
				if(imported){
					editMenu();
				}else{
					LCD.clearDisplay();
					LCD.refresh();
					display("You should probably import before you try to edit!");
					Button.ENTER.waitForPress();
				}
				break;
			case 4:
				if(imported){
					movementMenu();
				}else{
					LCD.clearDisplay();
					LCD.refresh();
					display("You should probably import before you try to move!");
					Button.ENTER.waitForPress();
				}
				break;
			case -1:
			default:
				System.exit(0);
				break;
			}
		}
	}

	/**
	 * Represents the search menu on the EV3
	 * Allows the user to select which search function they would like to perform
	 * @author murphyad
	 */
	private static void searchMenu(){
		LCD.clearDisplay();
		LCD.refresh();
		String[] items = {"Routes with Stop", "Next Trip on RT"};
		int index = -1;
		while(true){
			LCD.clearDisplay();
			LCD.refresh();
			switch((new TextMenu(items, 1, "Search")).select()){
			case 0:
				String[] stopIDs = controller.getStopArray();
				index = (new TextMenu(stopIDs, 1, "Stop IDs")).select();
				// index is equal to -1 when the user selects the back button
				if (index > -1){
					if(Controller.routesWithStop(stopIDs[index]) != null) {
						String result = "";
						Routes routes = Controller.routesWithStop(stopIDs[index]);
						for(int i=0;i<routes.getColOfRoutes().size();i++){
							result = result + ((Route) (routes.getColOfRoutes().toArray()[i])).getRouteId() + '\n';
						}
						display(result + "\n" + "Press enter to continue...");
						Button.ENTER.waitForPress();
					}else {
						display("No routes contain that stop");
						Button.ENTER.waitForPress();
					}
				}else {
					display("Invalid Stop ID selection");
					Button.ENTER.waitForPress();
				}
				break;
			case 1:
				String[] routeIDs = controller.getRouteArray();
				index = (new TextMenu(routeIDs, 1, "Route IDs")).select();
				// index is equal to -1 when the user selects the back button
				if (index > -1){
					if(Controller.searchForNextTrip(routeIDs[index]) != null) {
						display("Next Trip: " + Controller.searchForNextTrip(routeIDs[index]).getTripId()+
								"\nPress enter to continue..");
						Button.ENTER.waitForPress();
					}
				}else {
					display("Invalid Stop ID selection");
					Button.ENTER.waitForPress();
				}
				break;
			case -1:
			default:
				return;
			}
		}
	}

	/**
	 * Represents the method in the EV3 for the user to import files
	 * Allows the user to select individual files and imports the data from those files
	 * @author murphyad
	 */
	private static void importMenu(){
		LCD.clearDisplay();
		LCD.refresh();
		
		File ev3_dir = new File("./");
		File[] files = ev3_dir.listFiles();
		String[] options = {"Routes: Not Selected","Trips: Not Selected","Stops: Not Selected",
				"StopTimes: Not Selected","Import"};
		String[] fileNames = new String[files.length];
		for(int i = 0;i<files.length;i++){
			fileNames[i] = files[i].getName();
		}
		File routes = null;
		File trips = null;
		File stops = null; 
		File stop_times = null;
		while(true){
			LCD.clearDisplay();
			LCD.refresh();
			switch((new TextMenu(options,1,"Import Files")).select()){
			case 0:
				int routeFile = (new TextMenu(fileNames,1,"Select Route File")).select();
				if(routeFile < 0){
					//TODO this is the back button
				}else{
					routes = files[routeFile];
					options[0] = "Routes: " + fileNames[routeFile];
				}
				break;
			case 1:
				int tripFile = (new TextMenu(fileNames,1,"Select Trip File")).select();
				if(tripFile < 0){
					//TODO this is the back button
				}else{
					trips = files[tripFile];
					options[1] = "Trips: " + fileNames[tripFile];
				}
				break;
			
			case 2:
				int stopFile = (new TextMenu(fileNames,1,"Select Stop File")).select();
				if(stopFile < 0){
					//TODO this is the back button
				}else{
					stops = files[stopFile];
					options[2] = "Stop: " + fileNames[stopFile];
				}
				break;
			case 3:
				int stopTimeFile = (new TextMenu(fileNames,1,"Select StopTime File")).select();
				if(stopTimeFile < 0){
					//TODO this is the back button
				}else{
					stop_times = files[stopTimeFile];
					options[3] = "StopTimes: " + fileNames[stopTimeFile];
				}
				break;
			case 4:
				LCD.clearDisplay();
				LCD.refresh();
				display("Importing files... please wait.");
				if(routes==null||trips==null||stops==null||stop_times==null){
					display("Error in importing, please make sure that a file is selected for each data type.");
					Button.ENTER.waitForPress();
					imported = false;
				}else{
					boolean route_success, trip_success, stopTime_success, stop_success;
					//attempt to import the files
					stopTime_success = controller.importStopTimes(stop_times);
					trip_success = controller.importTrips(trips);
					stop_success = controller.importStops(stops);
					route_success = controller.importRoutes(routes);				
				
					if(route_success&&trip_success&&stopTime_success&&stop_success){
						controller.update();
						display("All files imported.");
						imported = true;
						Button.ENTER.waitForPress();
						return;
					}else{
						String error = "Error importing: ";
						if(!route_success){
							error = error + "routes, ";
						}
						if(!trip_success){
							error = error + "trips, ";
						}
						if(!stopTime_success){
							error = error + "stop_times, ";
						}
						if(!stop_success){
							error = error + "stops";
						}
						display(error);
						Button.ENTER.waitForPress();
						imported = false;
					}
				}
				break;
			case -1:
			default:
				return;
			}
		}
	}
	
	/**
	 * Represents the menu on the EV3 that guides the user through the process of exporting files
	 * Allows the user to select individual files and exports the data to those selected files
	 * @author murphyad
	 */
	private static void exportFiles(){
		LCD.clearDisplay();
		LCD.refresh();
		
		File ev3_dir = new File("./");
		File[] files = ev3_dir.listFiles();
		String[] options = {"Routes: Not Selected","Trips: Not Selected","Stops: Not Selected",
				"StopTimes: Not Selected","Export"};
		String[] fileNames = new String[files.length];
		for(int i = 0;i<files.length;i++){
			fileNames[i] = files[i].getName();
		}
		File routes = null;
		File trips = null;
		File stops = null; 
		File stop_times = null;
		while(true){
			LCD.clearDisplay();
			LCD.refresh();
			switch((new TextMenu(options,1,"Export Files")).select()){
			case 0:
				int routeFile = (new TextMenu(fileNames,1,"Select Route File")).select();
				if(routeFile < 0){
					//TODO this is the back button
				}else{
					routes = files[routeFile];
					options[0] = "Routes: " + fileNames[routeFile];
				}
				break;
			case 1:
				int tripFile = (new TextMenu(fileNames,1,"Select Trip File")).select();
				if(tripFile < 0){
					//TODO this is the back button
				}else{
					trips = files[tripFile];
					options[1] = "Trips: " + fileNames[tripFile];
				}
				break;
			
			case 2:
				int stopFile = (new TextMenu(fileNames,1,"Select Stop File")).select();
				if(stopFile < 0){
					//TODO this is the back button
				}else{
					stops = files[stopFile];
					options[2] = "Stop: " + fileNames[stopFile];
				}
				break;
			case 3:
				int stopTimeFile = (new TextMenu(fileNames,1,"Select StopTime File")).select();
				if(stopTimeFile < 0){
					//TODO this is the back button
				}else{
					stop_times = files[stopTimeFile];
					options[3] = "StopTimes: " + fileNames[stopTimeFile];
				}
				break;
			case 4:
				LCD.clearDisplay();
				LCD.refresh();
				display("Exporting files... please wait.");
				if(routes==null||trips==null||stops==null||stop_times==null){
					display("Error in exporting, please make sure that a file is selected for each data type.");
					Button.ENTER.waitForPress();
					imported = false;
				}else{
					boolean route_success, trip_success, stopTime_success, stop_success;
					//attempt to import the files
					stopTime_success = controller.exportRoutes(routes);
					trip_success = controller.exportTrips(trips);
					stop_success = controller.exportStops(stops);
					route_success = controller.exportRoutes(routes);
				
					if(route_success&&trip_success&&stopTime_success&&stop_success){
						controller.update();
						display("All files exported.");
						imported = true;
						Button.ENTER.waitForPress();
						return;
					}else{
						String error = "Error exporting: ";
						if(!route_success){
							error = error + "routes, ";
						}
						if(!trip_success){
							error = error + "trips, ";
						}
						if(!stopTime_success){
							error = error + "stop_times, ";
						}
						if(!stop_success){
							error = error + "stops";
						}
						display(error);
						Button.ENTER.waitForPress();
						imported = false;
					}
				}
				break;
			case -1:
			default:
				return;
			}
		}
	}


	public static void editMenu(){
		LCD.clearDisplay();
		LCD.refresh();
		String[] items = {"Set Scaling Factor", "Set Linear Speed", "Set Rotation Speed"};
		
		int index = -1;
		while(true){
			LCD.clearDisplay();
			LCD.refresh();
			int currentDigit = 0;
			switch((new TextMenu(items, 1, "Edit")).select()){
			//Set Scaling Factor
			case 0:
				int scale = 0;
				while(currentDigit!=-1&&currentDigit!=10){
					LCD.clearDisplay();
					LCD.refresh();
					
					currentDigit = (new TextMenu(digits,1,"Scale " + String.valueOf(scale))).select();
					if(currentDigit!=-1&&currentDigit!=10){
						scale = scale * 10;
						scale += currentDigit;
					}
				}
				if(currentDigit!=-1){
					LCD.clearDisplay();
					LCD.refresh();
					controller.setScale(scale);
					display("Distance scale: " + scale + " has been set.  Press enter to continue...");
					Button.ENTER.waitForPress();
				}
				break;
			//Set Linear Speed
			case 1:
				int speed = 0;
				while(currentDigit!=-1&&currentDigit!=10){
					LCD.clearDisplay();
					LCD.refresh();
					currentDigit = (new TextMenu(digits,1,"Scale " + String.valueOf(speed) + '%')).select();
					if(currentDigit!=-1&&currentDigit!=10){
						speed = speed * 10;
						speed += currentDigit;
					}
				}
				if(currentDigit!=-1){
					if(speed<=100 && speed>0){
						LCD.clearDisplay();
						LCD.refresh();
						setRobotSpeed(speed);  //TODO Uncomment when setSpeed is created
						display("Linear Speed: " + speed + " has been set.  Press enter to continue");
						Button.ENTER.waitForPress();
					}else{
						LCD.clearDisplay();
						LCD.refresh();
						display("Speed: " + speed + " is not a valid speed! Please enter a value between 1-100.  "
								+ "Press enter to continue.");
						Button.ENTER.waitForPress();
					}
					
				}
				break; //AARON THERES NO BREAK
				//Set Rotaional Speed
			case 2:
				int speedR = 0;
				while(currentDigit!=-1&&currentDigit!=10){
					LCD.clearDisplay();
					LCD.refresh();
					currentDigit = (new TextMenu(digits,1,"Scale " + String.valueOf(speedR) + '%')).select();
					if(currentDigit!=-1&&currentDigit!=10){
						speedR = speedR * 10;
						speedR += currentDigit;
					}
				}
				if(currentDigit!=-1){
					if(speedR<=100 && speedR>0){
						LCD.clearDisplay();
						LCD.refresh();
						setRotationSpeed(speedR);  //TODO Uncomment when setSpeed is created
						display("Speed: " + speedR + " has been set.  Press enter to continue");
						Button.ENTER.waitForPress();
					}else{
						LCD.clearDisplay();
						LCD.refresh();
						display("Rotaional Speed: " + speedR + " is not a valid speed! Please enter a value between 1-100.  "
								+ "Press enter to continue.");
						Button.ENTER.waitForPress();
					}
					
				}
				break;
			//Edit Stop Time
			case 3:
				String[] stopTimeIDs = controller.getStopTimeArray();
				int stopTime = (new TextMenu(stopTimeIDs, 1, "Stop Times")).select();
				if(stopTime != -1){
					modifyStopTime(controller.getStopTime(stopTime));
				}
				break;
			case -1:
			default:
				return;
			}
		}
	}
	
	private static void modifyStopTime(StopTime stopTime){
		String[] menu = {"Change Arrive Time","Change Depart Time"};
		int index = 0;
		int currentDigit = 0;
		int hours = 0;
		int minutes = 0;
		while(index != -1){
			LCD.clearDisplay();
			LCD.refresh();
			index = (new TextMenu(menu, 1, "StopTime: "+stopTime.getTripId()+"::"+stopTime.getSequenceNumber())).select();
			if(index != -1){
				//Get Hours
				LCD.clearDisplay();
				LCD.refresh();
				display("Please enter the hour");
				Button.ENTER.waitForPress();
				hours = getNumber();
				//Get Minutes
				LCD.clearDisplay();
				LCD.refresh();
				display("Please enter the minutes");
				Button.ENTER.waitForPress();
				minutes = getNumber();
				if(hours < 0 || hours > 23){
					LCD.clearDisplay();
					LCD.refresh();
					display("Hour: " + hours + " is not valid. Press enter to continue...");
					Button.ENTER.waitForPress();
				}else if(minutes < 0 || minutes > 59){
					LCD.clearDisplay();
					LCD.refresh();
					display("Minute: " + minutes + " is not valid. Press enter to continue...");
					Button.ENTER.waitForPress();
				}else{
					switch(index){
					//Change Arrival Time
					case 0:
						stopTime.setArrivalTime(hours + ":" + minutes);
						LCD.clearDisplay();
						display("Arrival time: "+ hours + ":" + minutes + " has been set.  Press enter to continue...");
						Button.ENTER.waitForPress();
						break;
					//Change Departure Time
					case 1:
						stopTime.setDepartureTime(hours + ":" + minutes);
						LCD.clearDisplay();
						display("Departure time: "+ hours + ":" + minutes + " has been set.  Press enter to continue...");
						Button.ENTER.waitForPress();
						break;
						
					case -1:
						default:
							return;
					}
				}
			}
		}
			
	}

	
	public static void movementMenu(){
		delayTime = 0;
		LCD.clearDisplay();
		LCD.refresh();
		String[] items = {"Perform Full Trip", "Perform Part of Trip","Track Passengers?"};
		int index = -1;
		String[] trips = controller.getTripArray();
		while(true){
			LCD.clearDisplay();
			LCD.refresh();
			switch((new TextMenu(items, 1, "Movement")).select()){
			//Full Trip
			case 0:
				index = (new TextMenu(trips, 1, "Trips")).select();
				if(index != -1){
					LCD.clearDisplay();
					LCD.refresh();
					controller.distanceOfTrip(trips[index]);
					performTrip();
				}
				break;
			//Perform Part of Trip
			case 1:
				index = (new TextMenu(trips, 1, "Trips")).select();
				if(index != -1){
					//Get stops on trip
					ArrayList<Stop> stops = controller.getStopsOnTrip(trips[index]);
					String[] stopIDS = new String[stops.size()];
					for(int i = 0; i<stops.size(); i++){
						stopIDS[i] = stops.get(i).getStopId();
					}
				//Allow user to select stop
				int stopIndex = (new TextMenu(stopIDS, 1, "Trips")).select();
					if(stopIndex != -1){
						String startingStop = stopIDS[stopIndex];
					}
				
				
				//Get number of stops to visit
				int currentDigit = 0;
				int numStops = 0;
				while(currentDigit!=-1&&currentDigit!=10){
					LCD.clearDisplay();
					LCD.refresh();
					currentDigit = (new TextMenu(digits,1,"Scale " + String.valueOf(numStops))).select();
					if(currentDigit!=-1&&currentDigit!=10){
						numStops = numStops * 10;
						numStops += currentDigit;
					}
				}
				if(numStops > stopIDS.length - stopIndex){
					//Throw error
				}else{
					controller.limitTrip(trips[index], stopIDS[stopIndex], numStops);
					performTrip();
				}
				
				}
				break;
			//Track Passengers
			case 2:
				String[] decision = {"Yes","No"};
				LCD.clearDisplay();
				LCD.refresh();
				index = (new TextMenu(decision, 1, "Track Passengers?")).select();
				switch(index){
				case 0:
					trackPassengers = true;
					break;
				case 1:
					trackPassengers = false;
					break;
				case -1:
				default:
					break;
				}
				break;
			case -1:
				default:
					return;
			}
		}
	}
	
	//Allows the users to increment and decrement the number of passengers on the bus
	private static void logPassengers(){
		String[] options = {"Add Pass." , "Subtrack Pass.", "Continue.."};
		while(true){
			LCD.clearDisplay();
			LCD.refresh();
			switch((new TextMenu(options, 1, "Passengers = " + passengers)).select()){
			case 0:
				passengers++;
				break;
			case 1:
				if(passengers>0){
					passengers--;
				}else{
					LCD.clearDisplay();
					display("Cannot remove passengers from empty bus!");
				}
				break;
			case 2:
			case -1:
				default:
					LCD.clearDisplay();
					LCD.refresh();
					return;
			}
		}
	}
	
	private static void performTrip(){
		passengers = 0;
		
		while(controller.hasNextStopInInstructions()){
			if(trackPassengers){
				logPassengers();
			}
			controller.perform(1);
		}
		LCD.clearDisplay();
		LCD.refresh();
		display("Trip Complete...");
		Delay.msDelay(2000);
		if(trackPassengers){
			LCD.clearDisplay();
			LCD.refresh();
			display("Passengers aboard at end of trip: " + passengers);
			Delay.msDelay(2000);
		}
		LCD.clearDisplay();
		LCD.refresh();
		display("There was " + delayTime + " seconds of delay on this trip.");
		Delay.msDelay(2000);
		LCD.clearDisplay();
		LCD.refresh();
		display("Press enter to continue...");
		Button.ENTER.waitForPress();
	}
	
	
	private static int getNumber(){
		int num = 0;
		int currentDigit = 0;
		while(currentDigit!=-1&&currentDigit!=10){
			LCD.clearDisplay();
			LCD.refresh();
			currentDigit = (new TextMenu(digits,1, String.valueOf(num))).select();
			if(currentDigit!=-1&&currentDigit!=10){
				num = num * 10;
				num += currentDigit;
			}
		}
		if(currentDigit!=-1){
			return num;
		}else{
			return -1;
		}
	}
	
	/**
	 * Chops and displays the message on the EV3
	 * Adapted from leJOS EV3 Forum: https://lejos.sourceforge.io/forum/viewtopic.php?t=8884
	 * @author murphyad
	 * @param message the message to be displayed
	 */
	public static void display(String message) {
		String chopString = "";                 
		int counter = 1;                     
		String[] messages = {""};               
		if (message.contains("\n")) {         
			messages = message.split("\n");      
			counter = messages.length;         
		}

		for (int count = 0; count < counter; count++) {   
			if (counter > 1) {
				message = messages[count];            
			}
			while (message.length() > 18) {            
				for (int i=18; i > 0; i--) {        
					if (message.substring(i,i+1).equals(" ")) {                  
						chopString = chopString + message.substring(0, i) + "\n";      
						message = message.substring(i+1);                       
						i = 0;                                            
					} else if (message.substring(i,i+1).equals("-") && i < 18) {  
						chopString = chopString + message.substring(0, i+1) + "\n";      
						message = message.substring(i+1);                       
						i = 0;                                            
					}
					if (i == 1) {
						chopString = chopString + message.substring(0, 18) + "\n";
						message = message.substring(18);
					}
				}
			}
			if (message.length() > 0) {
				chopString = chopString + message;     
			}
			if (counter > 1) chopString = chopString + "\n"; 
		}
		String[] chops = chopString.split("\n");      
		LCD.clear();
		for (int i = 0; i < chops.length; i++) {  
			if (i == chops.length-1 && i > 7) {
				LCD.drawString("" + chops[i], 0, 7);   
				Delay.msDelay(500);
			} else if (i > 6) {                     
				LCD.drawString("\\/     ", 7, 7);   
				while (BrickFinder.getLocal().getKeys().getButtons() != Keys.ID_DOWN);   
				LCD.scroll();                     
				LCD.drawString("  ", 7, 6);
				LCD.drawString("" + chops[i], 0, 6);   
				Delay.msDelay(500);                  
			}
			else {
				LCD.drawString("" + chops[i], 0, i);   
				Delay.msDelay(500);
			}
		}
		return;
	}
	/*
	 * This method sets the linear speed of the robot.
	 */
	public static void setRobotSpeed(double speedCentimetersSec) {
		speed = speedCentimetersSec;
	}
	/*
	 * This method moves the robot forward with the set speed.
	 */
	public static double forward(double cm) {
		return forward(cm, speed);
	}
	 /*
	   * This method makes the robot travel forward in a straight line for a provided amount in centimeters.
	   * Also takes the speed to travel in cm/s.
	   * The return is a double of the time taken to travel in seconds.
	   */
	  public static double forward(double cm, double speedCentimetersSec) {
		  double startTime = System.nanoTime();
		  double timeToStop = cm / speedCentimetersSec * 1000000000.0 + startTime;
		  double delayStart;
		  double delayEnd;
		  pilot.setLinearAcceleration(4000);
		  pilot.setLinearSpeed(speedCentimetersSec); //straight speed cm/sec
		  pilot.forward();
	      float[] obstacle = new float[1];
	      while (System.nanoTime() < timeToStop) {
	    	  sensorProvider.fetchSample(obstacle, 0);
	    	  if(obstacle[0] < 0.1) {
	    		  delayStart = System.nanoTime();
	    		  while(obstacle[0] < 0.1) {
	    		  	  pilot.stop();
	    		  	  sensorProvider.fetchSample(obstacle, 0);
	    	  	  }
	    		  delayEnd = System.nanoTime();
	    		  delayTime += (delayEnd-delayStart) / 1000000000.0;
	    		  timeToStop = timeToStop + ((delayStart - delayEnd));
	    	  }
	    	  if(!pilot.isMoving()) {
	    		  pilot.forward();
	    	  }
	      }
	      pilot.stop();
	      return (timeToStop - startTime) / 1000000000.0;
//	      pilot.travel(-dist,false);
//	      pilot.rotate(90,false);
//	      pilot.travel(10,false);
//	      pilot.rotate(-90,false);
	  }
	  /*
	   * This method sets the rotational speed.
	   */
	  public static void setRotationSpeed(double speedDegreeSec) {
		  rotateSpeed = speedDegreeSec;
	  }
	  /*
	   * This method turns the bot with the set speed.
	   */
	  public static double turn(double degrees) {
		  return turn(degrees, rotateSpeed);
	  }
	  /*
	   * This method makes the robot turn the passed amount of degrees. Positive is turning to the left, negative to the right.
	   * Arguments: Degree to turn as a double, speed in degrees per second to turn as a double.
	   * return: The amount of time elapsed in seconds as a double.
	   */
	  public static double turn(double degrees, double speedDegreeSec) {
		  double startTime = System.nanoTime() / 1000000000.0;
		  pilot.setAngularSpeed(speedDegreeSec); //rotation/turning deg/sec
		  pilot.rotate(degrees);
		  return System.nanoTime() / 1000000000.0 - startTime;
	  }
}
