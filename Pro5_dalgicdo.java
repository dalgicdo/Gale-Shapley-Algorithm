import java.util.ArrayList;
import java.io.*;

public class Pro5_dalgicdo {
	
	public static ArrayList<Student> studentList = new ArrayList<Student>();
	public static ArrayList<School> schoolList = new ArrayList<School>();
	public static SMPSolver my_solver = new SMPSolver(studentList, schoolList);
	public static ArrayList<Student> St2 = new ArrayList<Student>(); // additional lists for school suitor matching
	public static ArrayList<School> Sc2 = new ArrayList<School>();
	public static SMPSolver my_solver2 = new SMPSolver(St2, Sc2);
	public static boolean optionDCheck = false; // boolean for checking error in matching section

	public static void main(String[] args) throws IOException {
		
		String menuChoice;
		do {
			displayMenu();
			System.out.println();
			
			menuChoice = BasicMethods.getMenuChoice("Enter choice: ");
			
			if (menuChoice.equalsIgnoreCase("L")) {
				loadSchools(schoolList);
				loadStudents(studentList, schoolList);
				
				for (int a = 0; a <= schoolList.size() - 1; a++) {  
					schoolList.get(a).calcRankings(studentList);
				}
			}
			else if (menuChoice.equalsIgnoreCase("E")) {
				editData(studentList, schoolList);
			}
			else if (menuChoice.equalsIgnoreCase("P")) {
				System.out.println("STUDENTS:");
				System.out.println();
				printStudents(studentList, schoolList);
				System.out.println();
				System.out.println("SCHOOLS:");
				System.out.println();
				printSchools(studentList, schoolList);
				System.out.println();
			}
			else if(menuChoice.equalsIgnoreCase("M")) {
				
				int error = validity(schoolList);
				
				if (studentList.size() == 0 || schoolList.size() == 0 ) {
					System.out.println("STUDENT-OPTIMAL MATCHING");
					System.out.println();
					System.out.println("ERROR: No suitors are loaded!");
					System.out.println();
					System.out.println("SCHOOL-OPTIMAL MATCHING");
					System.out.println();
					System.out.println("ERROR: No suitors are loaded!");
					System.out.println();
				}
				
				else if (error != studentList.size()) {
					System.out.println("STUDENT-OPTIMAL MATCHING");
					System.out.println();
					System.out.println("ERROR: The number of suitor and receiver openings must be equal!");
					System.out.println();
					System.out.println("SCHOOL-OPTIMAL MATCHING");
					System.out.println();
					System.out.println("ERROR: The number of suitor and receiver openings must be equal!");
					System.out.println();
				}
				else {
					optionDCheck = true;
					
					listCopy(studentList,schoolList);
					
					System.out.println("STUDENT-OPTIMAL MATCHING");
					System.out.println();
					
					my_solver.StuMatch();
					my_solver.calcRegrets();
					
					System.out.println("Stable matching? Yes");
					System.out.println("Average suitor regret: " + String.format("%.2f",my_solver.getAvgSuitorRegret()));
					System.out.println("Average receiver regret: " + String.format("%.2f", my_solver.getAvgReceiverRegret()));
					System.out.println("Average total regret: " + String.format("%.2f",my_solver.getAvgTotalRegret()));
					System.out.println();
					System.out.println(studentList.size() + " matches made in " + my_solver.getElapsedTime() + "ms!");
					System.out.println();
					
					System.out.println("SCHOOL-OPTIMAL MATCHING");
					System.out.println();
					
					my_solver2.SchMatch();
					my_solver2.calcRegrets();
					
					System.out.println("Stable matching? Yes");
					System.out.println("Average suitor regret: " + String.format("%.2f",my_solver2.getAvgReceiverRegret()));
					System.out.println("Average receiver regret: " + String.format("%.2f",my_solver2.getAvgSuitorRegret()));
					System.out.println("Average total regret: " + String.format("%.2f",my_solver2.getAvgTotalRegret()));
					System.out.println();
					System.out.println(studentList.size() + " matches made in " + my_solver2.getElapsedTime() + "ms!");
					System.out.println();
				}
			}
			else if (menuChoice.equalsIgnoreCase("D")) {
				
				if (optionDCheck != true) {
					System.out.println("STUDENT-OPTIMAL SOLUTION");
					System.out.println();
					System.out.println("ERROR: No matches exist!");
					System.out.println();
					System.out.println("SCHOOL-OPTIMAL SOLUTION");
					System.out.println();
					System.out.println("ERROR: No matches exist!");
					System.out.println();
				}
				else {
					System.out.println("STUDENT-OPTIMAL SOLUTION");
					System.out.println();
					System.out.println("Matches:");
					System.out.println("--------");
					
					for (int t = 0; t <= schoolList.size() - 1; t++) {
						schoolList.get(t).printMatches(studentList);
					}
					System.out.println();
					
					System.out.println("Stable matching? Yes");
					System.out.println("Average suitor regret: " + String.format("%.2f",my_solver.getAvgSuitorRegret()));
					System.out.println("Average receiver regret: " + String.format("%.2f", my_solver.getAvgReceiverRegret()));
					System.out.println("Average total regret: " + String.format("%.2f",my_solver.getAvgTotalRegret()));
					System.out.println();
					
					System.out.println("SCHOOL-OPTIMAL SOLUTION");
					System.out.println();
					System.out.println("Matches:");
					System.out.println("--------");
					
					for (int b = 0; b <= Sc2.size() - 1; b++) {
						Sc2.get(b).printMatches(St2);
					}
					System.out.println();
					
					System.out.println("Stable matching? Yes");
					System.out.println("Average suitor regret: " + String.format("%.2f",my_solver2.getAvgReceiverRegret()));
					System.out.println("Average receiver regret: " + String.format("%.2f",my_solver2.getAvgSuitorRegret()));
					System.out.println("Average total regret: " + String.format("%.2f",my_solver2.getAvgTotalRegret()));
					System.out.println();
				}
			}
			else if (menuChoice.equalsIgnoreCase("X")) { 
				if (optionDCheck != true) {
					System.out.println("ERROR: No matches exist!");
					System.out.println();
				}
				else {
					printComparison(my_solver, my_solver2);
				}
			}
			else if (menuChoice.equalsIgnoreCase("R")) {
				studentList.clear();
				schoolList.clear();
				St2.clear();
				Sc2.clear();
				optionDCheck = false;
				
				my_solver = new SMPSolver(studentList, schoolList);
				my_solver2 = new SMPSolver(St2, Sc2);
				
				System.out.println("Database cleared!");
				System.out.println();
			}
			else if (menuChoice.equalsIgnoreCase("Q")) {
				System.out.println("Hasta luego!");
			}
			
		} while(!(menuChoice.equalsIgnoreCase("Q")));

	}
	
	public static void displayMenu() {
		System.out.println("JAVA STABLE MARRIAGE PROBLEM v3");
		System.out.println();
		System.out.println("L - Load students and schools from file");
		System.out.println("E - Edit students and schools");
		System.out.println("P - Print students and schools");
		System.out.println("M - Match students and schools using Gale-Shapley algorithm");
		System.out.println("D - Display matches");
		System.out.println("X - Compare student-optimal and school-optimal matches");
		System.out.println("R - Reset database");
		System.out.println("Q - Quit");
	}
	
	public static int loadSchools(ArrayList<School> schList) throws IOException {
		
		boolean loopBool = false;
		
		do {
			String userChoice = BasicMethods.getString("Enter school file name (0 to cancel): ");
			
			if (userChoice.equals("0")) {
				System.out.println();
				System.out.println("File loading process canceled.");
				System.out.println();
				loopBool = true;
			}
			else {
				String row;
				
				File my_file = new File(userChoice);
				boolean file_state = my_file.exists();
				
				if (file_state == true) {
					final BufferedReader fileReader = new BufferedReader(new FileReader(userChoice));
					loopBool = true;
					
					int count = -1;
					
					do {
						count += 1;
						row = fileReader.readLine();
						
						if (row != null) {
							String[] splitRow = row.split(",");
							School new_sch = new School(Double.parseDouble(splitRow[1]), Integer.parseInt(splitRow[2]));
							new_sch.setName(splitRow[0]); // using inheritance to set the name
							
							if (new_sch.isValid() == true) {
								schList.add(new_sch);
							}
						}
						
					} while(row != null);
					fileReader.close();
					
					System.out.println();		
					System.out.println(count + " of " + count + " schools loaded!");
					System.out.println();		
				}
				
				else {
					System.out.println();		
					System.out.println("ERROR: File not found!");
					System.out.println();		
					loopBool = false;
				}
			}
			
		} while(loopBool == false);
		
		return schList.size();
	}
	
	
	public static int loadStudents(ArrayList<Student> stuList, ArrayList<School> schList) throws IOException {
		
		boolean loopBool_2 = false;
		
		do {
			String userChoice_2 = BasicMethods.getString("Enter student file name (0 to cancel): ");
			
			if (userChoice_2.equals("0")) {
				System.out.println();
				System.out.println("File loading process canceled.");
				System.out.println();
				loopBool_2 = true;
			}
			else {
				String line;
				
				File stuFile = new File(userChoice_2);
				boolean file_cond = stuFile.exists();
				
				if (file_cond == true) {
					final BufferedReader stuFileReader = new BufferedReader(new FileReader(userChoice_2));
					loopBool_2 = true;
					
					int noOfStu = -1;
					
					do {
						noOfStu += 1;
						line = stuFileReader.readLine();
						
						if (line != null) {
							String splitLine[] = line.split(",");
							Student new_stu = new Student(Double.parseDouble(splitLine[1]), Integer.parseInt(splitLine[2]));
							new_stu.setName(splitLine[0]);
							
							ArrayList<Integer> stuRankList = new_stu.getRankingList();
							for (int a = 3; a <= schList.size() + 2; a++) {
								stuRankList.add(Integer.parseInt(splitLine[a]));
							}
							stuList.add(new_stu);
						}
						
					} while(line != null);
					stuFileReader.close();
					
					System.out.println();		
					System.out.println(noOfStu + " of " + noOfStu + " students loaded!");
					System.out.println();		
				}
				else {
					System.out.println();		
					System.out.println("ERROR: File not found!");
					System.out.println();		
					loopBool_2 = false;
				}	
			}	
		} while(loopBool_2 == false);
		
		return stuList.size();
	}
	
	public static void printStudents(ArrayList <Student> stuList, ArrayList <School> schList) throws IOException {
		System.out.println(" #   Name                                         GPA  ES  Assigned school                         Preferred school order"); // "I feel guilty."
		//System.out.printf(" %-4s%-45s%-5s%-4s%-40s%-24s%n", "#", "Name", "GPA", "ES", "Assigned school", "Preferred school order");
		System.out.println("---------------------------------------------------------------------------------------------------------------------------");
		
		for (int i = 0; i <= stuList.size() - 1; i++) {			
			Student stuObj = stuList.get(i);
			stuObj.printStu(schList, i);
		}
		System.out.println("---------------------------------------------------------------------------------------------------------------------------");
	}
	
	public static void printSchools(ArrayList <Student> stuList, ArrayList <School> schList) throws IOException {
		System.out.println(" #   Name                                     # spots  Weight  Assigned students                       Preferred student order");
		//System.out.printf(" %-45s%-9s%-8s%-38s%-47s%n", "#   Name", "# spots", "Weight", "Assigned student", "Preferred student order");
		System.out.println("------------------------------------------------------------------------------------------------------------------------------");
		
		
		for (int a = 0; a <= schList.size() - 1; a++) {
			School schObj = schList.get(a);
			schObj.printSch(stuList, schList, a);
		}
		System.out.println("------------------------------------------------------------------------------------------------------------------------------");
	}
	
	public static void listCopy(ArrayList<Student> stuList, ArrayList<School> schList) throws IOException {
		
		for (int i = 0; i <= stuList.size() - 1; i++) { // copying student info
			double GPA = stuList.get(i).getGPA();
			int ES = stuList.get(i).getES();
			Student newStu = new Student(GPA, ES);
			newStu.setName(stuList.get(i).getName());
			
			ArrayList<Integer> RankList = stuList.get(i).getRankingList();
			for (int u = 0; u <= RankList.size() - 1; u++) {
				newStu.getRankingList().add(RankList.get(u));
			}
			St2.add(newStu);
		}
		
		for (int y = 0; y <= schList.size() - 1; y++) { // copying school info
			double alpha = schList.get(y).getAlpha();
			int maxSpots = schList.get(y).getMaxMatches();
			School newSch = new School(alpha, maxSpots);
			
			newSch.setName(schList.get(y).getName());
			ArrayList<Integer> RankListSch = schList.get(y).getRankingList();
			for (int r = 0; r <= RankListSch.size() - 1; r++) {
				newSch.getRankingList().add(RankListSch.get(r));
			}
			Sc2.add(newSch);
		}
		
	}
	
	public static void editStudents(ArrayList<Student> stuList, ArrayList<School> schList) throws IOException {
		
		int editChoice;
		
		do {
			printStudents(stuList, schList);
			editChoice = BasicMethods.getInt("Enter student (0 to quit): ", 0, stuList.size());
			System.out.println();
			
			if (editChoice == 0) {
				break;
			}
			
			Student stuObj = stuList.get(editChoice - 1);
			stuObj.setName(BasicMethods.getString("Name: "));
			stuObj.setGPA(BasicMethods.getDouble("GPA: ", 0, 4));
			stuObj.setES(BasicMethods.getInt("Extracurricular score: ", 0, 5));
			BasicMethods.getInt("Maximum number of matches: ", 0, schList.size());
			
			boolean errorBool = true;
			String userChoice = ""; 
			
			do {
				try {
					userChoice = BasicMethods.getString("Edit rankings (y/n): ");
					
					if ((userChoice.equalsIgnoreCase("y")) || (userChoice.equalsIgnoreCase("n"))) {
						errorBool = true; 	
					}
					else {
						errorBool = false;
						System.out.println("ERROR: Choice must be 'y' or 'n'!");
					}
				}
				catch (Exception e) {
					errorBool = false;
					System.out.println("ERROR: Choice must be 'y' or 'n'!");
				}	
			} while (errorBool == false);
			System.out.println();
			
			boolean rankBool;
			int rankStu = 0;
			int noOfSch = schList.size();
			
			if (userChoice.equalsIgnoreCase("y")) {
				
				System.out.println();
				System.out.println("Student " + stuObj.getName() + "'s rankings:");
				stuObj.getRankingList().clear();
				for (int j = 0; j <= noOfSch - 1; j++) { // looping through schools
					rankStu = BasicMethods.getInt("School " + schList.get(j).getName() + ": ", 1, noOfSch);
						
					if (j == 0) {
						stuObj.setRanking(j, rankStu);
					}
					else { // to check ERROR for 2nd, 3rd ... nth items added
						rankBool = stuObj.getRankingList().contains(rankStu);
							
						while (rankBool == true) {
							System.out.println("ERROR: Rank " + rankStu + " already used!");
							System.out.println();
							rankStu = BasicMethods.getInt("School " + schList.get(j).getName() + ": ", 1, noOfSch);
							rankBool = stuObj.getRankingList().contains(rankStu);
						}
						stuObj.setRanking(j, rankStu);
					}	
				}
				System.out.println();
				System.out.println();
			}
	 
		} while (editChoice != 0);
	}
	
	public static void editSchools(ArrayList<Student> stuList, ArrayList<School> schList) throws IOException {
		
		int editChoice;
		do {
			printSchools(stuList, schList);
			editChoice = BasicMethods.getInt("Enter school (0 to quit): ", 0, schList.size());
			System.out.println();
			
			if (editChoice == 0) {
				break;
			}
			
			School schObj = schList.get(editChoice - 1);
			schObj.setName(BasicMethods.getString("Name: "));
			schObj.setAlpha(BasicMethods.getDouble("GPA weight: ", 0, 1));
			schObj.setMaxSpots2(BasicMethods.getInt("Maximum number of matches: ", 0, stuList.size()));
			System.out.println();
			
			schObj.getCompScoreList().clear();
			schObj.getRankingList().clear();
			
			schObj.calcRankings(stuList);
		}
		while (editChoice != 0);
	}
	
	public static void editData(ArrayList<Student> stuList, ArrayList<School> schList) throws IOException {
		String userIn;
		
		do {
			System.out.println("Edit data");
			System.out.println("---------");
			System.out.println("S - Edit students");
			System.out.println("H - Edit high schools");
			System.out.println("Q - Quit");
			System.out.println();
			
			userIn = BasicMethods.getMenuChoice("Enter choice: ");
			
			// ERRORS
			if (userIn.equalsIgnoreCase("S")) {
				
				if (stuList.size() == 0) {
					System.out.println("ERROR: No students are loaded!");	
					System.out.println();
				}
				else {
					editStudents(studentList, schoolList);
				}
				
			}
			else if (userIn.equalsIgnoreCase("H")) {
				
				if (schList.size() == 0) {
					System.out.println("ERROR: No schools are loaded!");
					System.out.println();
				}
				else {
					editSchools(studentList, schoolList);
				}	
			}
			
		} while (!(userIn.equalsIgnoreCase("Q")));
		
	}
	
	public static void printComparison(SMPSolver stuM, SMPSolver schM) {
		
		System.out.println("Solution              Stable    Avg school regret   Avg student regret     Avg total regret       Comp time (ms)"); // I feel guilty. :)
		System.out.println("----------------------------------------------------------------------------------------------------------------");
		
		if (my_solver.getAvgReceiverRegret() >= 100) {
			System.out.printf("%-25s%-18s%-21s%-21s%-24s%-1s%n", "Student optimal", "Yes", String.format("%5.2f", my_solver.getAvgReceiverRegret()), String.format("%5.2f", my_solver.getAvgSuitorRegret()), String.format("%5.2f", my_solver.getAvgTotalRegret()), my_solver.getElapsedTime());
			System.out.printf("%-25s%-18s%-21s%-21s%-23s%-1s%n", "School optimal", "Yes", String.format("%5.2f", my_solver2.getAvgReceiverRegret()), String.format("%5.2f", my_solver2.getAvgSuitorRegret()), String.format("%5.2f", my_solver2.getAvgTotalRegret()), my_solver2.getElapsedTime());
		}
		else {
			System.out.printf("%-25s%-19s%-21s%-21s%-25s%-1s%n", "Student optimal", "Yes", String.format("%5.2f", my_solver.getAvgReceiverRegret()), String.format("%5.2f", my_solver.getAvgSuitorRegret()), String.format("%5.2f", my_solver.getAvgTotalRegret()), my_solver.getElapsedTime());
			System.out.printf("%-25s%-19s%-21s%-21s%-25s%-1s%n", "School optimal", "Yes", String.format("%5.2f", my_solver2.getAvgReceiverRegret()), String.format("%5.2f", my_solver2.getAvgSuitorRegret()), String.format("%5.2f", my_solver2.getAvgTotalRegret()), my_solver2.getElapsedTime());
		}
		System.out.println("----------------------------------------------------------------------------------------------------------------");
		
		String stable = "Tie";
		String schReg = "Tie";
		String stuReg = "Tie";
		String avgReg = "Tie";
		String compTime = "Tie";
		
		// CONDITIONS
		
		if (my_solver.getAvgReceiverRegret() < my_solver2.getAvgReceiverRegret()) {
			schReg = "Student-opt";
		}
		else if (my_solver.getAvgReceiverRegret() > my_solver2.getAvgReceiverRegret()) {
			schReg = "School-opt";
		}
		
		if (my_solver.getAvgSuitorRegret() < my_solver2.getAvgSuitorRegret()) {
			stuReg = "Student-opt";
		}
		else if (my_solver.getAvgSuitorRegret() > my_solver2.getAvgSuitorRegret()) {
			stuReg = "School-opt";
		}
		
		if (my_solver.getAvgTotalRegret() < my_solver2.getAvgTotalRegret()) {
			avgReg = "Student-opt";
		}
		else if (my_solver.getAvgTotalRegret() > my_solver2.getAvgTotalRegret()) {
			avgReg = "School-opt";
		}
		
		if (my_solver.getElapsedTime() < my_solver2.getElapsedTime()) {
			compTime = "Student-opt";
		}
		else if (my_solver.getElapsedTime() > my_solver2.getElapsedTime()) {
			compTime = "School-opt";
		}
		
		// PRINT CONDITIONS
		if (compTime.equals("Student-opt")) {
			System.out.printf("%-25s%-21s%-21s%-21s%-13s%-1s%n" , "WINNER", stable, schReg, stuReg, avgReg, compTime);
		}
		else if (compTime.equals("School-opt")) {
			System.out.printf("%-25s%-21s%-21s%-21s%-14s%-1s%n" , "WINNER", stable, schReg, stuReg, avgReg, compTime);
		}
		else {
			System.out.printf("%-25s%-21s%-21s%-21s%-21s%-1s%n" , "WINNER", stable, schReg, stuReg, avgReg, compTime);
		}
		
		System.out.println("----------------------------------------------------------------------------------------------------------------");
		System.out.println();
		
	}
	 
	public static int validity(ArrayList<School> schList) {
		int totalSpots = 0;
		
		for (int i = 0; i <= schList.size() - 1; i++) {
			totalSpots += schList.get(i).getMaxMatches();
		}
		
		return totalSpots;
	}

	
	
	

}
