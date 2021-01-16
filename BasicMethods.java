import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class BasicMethods {
	
	public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	public static int userInt;
	public static double userDouble;
	public static String userString;
	public static boolean logicBool = false;
	public static ArrayList <String> menuOpt = new ArrayList <>(Arrays.asList("L", "l", "E", "e", "P", "p", "M", "m", "D", "d", "R", "r", "Q", "q", "S", "s", "H", "h", "X", "x"));
	
	public static int getInteger(String prompt, int LB, int UB) throws IOException { // for "how many students?"
		
		do {
			try {
				System.out.print(prompt);
				userInt = Integer.parseInt(reader.readLine());
				System.out.println();
				
				if (userInt < LB || userInt > UB) {
					logicBool = false;
					System.out.println("ERROR: Input must be an integer in [" + LB + ", infinity]!");
					System.out.println();	
				}
				else {
					logicBool = true;
				}
			}
			
			catch (Exception e) {
				logicBool = false;
				System.out.println();
				System.out.println("ERROR: Input must be an integer in [" + LB + ", infinity]!");
				System.out.println();
			}	
		}
		while (logicBool == false);
		return userInt;	
	}
	
    public static int getInt(String prompt, int LB, int UB) throws IOException { // for ES, ranks since no "[0, infinity]!" error
    	
		do {
			try {
				System.out.print(prompt);
				userInt = Integer.parseInt(reader.readLine());
				
				if (userInt < LB || userInt > UB) {
					logicBool = false;
					System.out.println();	
					System.out.println("ERROR: Input must be an integer in [" + LB + ", " + UB + "]!");
					System.out.println();
				}
				else {
					logicBool = true;
					// System.out.println();
				}
			}
			
			catch (Exception e) {
				System.out.println();	
				System.out.println("ERROR: Input must be an integer in [" + LB + ", " + UB + "]!");
				logicBool = false;
				System.out.println();
			}	
		}
		while (logicBool == false);
		return userInt;	
	}
	
	public static double getDouble(String prompt, double LB, double UB) throws IOException { // for GPA and school alpha
		
		String printUB = String.format("%.2f", UB);
		do {
			try {
				System.out.print(prompt);
				userDouble = Double.parseDouble(reader.readLine());
				
				if (userDouble < LB || userDouble > UB) {
					logicBool = false;
					System.out.println();
					System.out.println("ERROR: Input must be a real number in [0.00" + ", " + printUB + "]!");
					System.out.println();
					
				}
				else {
					logicBool = true;
					//System.out.println();
				}
				
			}
			
			catch (Exception e) {
				logicBool = false;
				System.out.println();
				System.out.println("ERROR: Input must be a real number in [0.00, " + printUB + "]!");
				System.out.println();		
			}		
		}
		while (logicBool == false);
		return userDouble;
	}
	
	public static String getString(String prompt) throws IOException { // to get names
		System.out.print(prompt);
		userString = reader.readLine();
		
		return userString;
	}
	
	public static String getMenuChoice(String prompt) throws IOException { // for menu choice

		try {
			System.out.print(prompt);	
			userString  = reader.readLine();
			System.out.println();
				
			if (menuOpt.contains(userString) == true) {
				return userString;
			}
			else {
				//System.out.println();
				System.out.println("ERROR: Invalid menu choice!");
				System.out.println();
			}			
		}
			
		catch (Exception e) {
			//System.out.println();
			System.out.println("ERROR: Invalid menu choice!");
			System.out.println();
		}
		return userString;
	}
	
	public static boolean checkArrayListElements(ArrayList <Integer> inAL, int userIn) throws IOException {
		boolean errorBool = true;
	
		if (inAL.size() == 0) {
			inAL.add(userIn);
		}
		else {
			boolean my_bool = inAL.contains(userIn);
			
			if (my_bool == false) {
				inAL.add(userIn);
			}
			else if (my_bool == true) {
				System.out.println("ERROR: School " + userIn + " is already matched!");
				errorBool = false;
				System.out.println();
			}
		}
		return errorBool;
	}


}
