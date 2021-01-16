import java.util.ArrayList;
import java.util.Arrays;

public class SMPSolver {

	private ArrayList <Student> stuList = new ArrayList <Student>();
	private ArrayList <School> schList = new ArrayList <School>();
	private double avgSuitorRegret; // average suitor regret
	private double avgReceiverRegret; // average receiver regret
	private double avgTotalRegret;
	private boolean matchLogicBool; // for the case when everybody is matched in the round but one suitor is then free after another proposed after he/she
	private long elapsedTime;
	private ArrayList <Integer> regretList = new ArrayList <Integer>(); // FINAL EXAM
	private double std_dev;
	
	// CONSTRUCTOR
	public SMPSolver(ArrayList <Student> S, ArrayList <School> R) {
		this.stuList = S;
		this.schList = R;
		this.avgSuitorRegret = 0;
		this.avgReceiverRegret = 0;
		this.avgTotalRegret = 0;
		this.matchLogicBool = true;
		this.elapsedTime = 0;
		this.regretList = new ArrayList <Integer>(); //FINAL EXAM
		this.std_dev = 0;
	}
	
	// getters
	public double getAvgSuitorRegret() {
		return this.avgSuitorRegret;
	}
	public double getAvgReceiverRegret() {
		return this.avgReceiverRegret;
	}
	public double getAvgTotalRegret() {
		return this.avgTotalRegret;
	}
	public boolean getMatchLogBool() {
		return this.matchLogicBool;
	}
	public long getElapsedTime() {
		return this.elapsedTime;
	}
	public double getStDevReceiverRegret() {
		return this.std_dev;
	}
	
	// other METHODS
	public void reset(ArrayList<Student> S , ArrayList<School> R ) {
		S.clear();
		R.clear();
		System.out.println("Database cleared!");
		System.out.println();
	}
	
	// matching METHODS
	public boolean makeStudentEngagement(int stuNum, int receiver) { // set receiver to 0 for implementation purposes
		
		// variables I need for students
		ArrayList <Integer> stu_State = this.stuList.get(stuNum).getMatchList();
		ArrayList <Integer> stu_Rankings = this.stuList.get(stuNum).getRankingList();
		//System.out.println(stu_Rankings);
		int stuBestChoice = stu_Rankings.get(this.stuList.get(stuNum).getBestOption() - 1) - 1; // the best available school for student to propose to
		receiver = stuBestChoice;
		
		// variables I need for schools
		ArrayList<Integer> sch_State = this.schList.get(receiver).getMatchList(); // aList that represents the engagement state of school
		ArrayList<Integer> sch_Rankings = this.schList.get(receiver).getRankingList();
		
		// other variables
		boolean returnBool = false;
		
		// logical conditions for the Gale-Shapely Algorithm
		if (schList.get(receiver).getFreeSpots() != 0) {
			
			stu_State.set(0, receiver);
			
			if (sch_State.get(0) == -1) { //condition to change initialization of -1 to proper student to avoid any errors later
				sch_State.set(0, stuNum); // if it is the school's first proposal received
			}
			else {
				sch_State.add(stuNum);
			}
			
			this.stuList.get(stuNum).incBestOption(); // setting the student's best available option
			this.schList.get(receiver).setSpotNum(); // decreasing the spots available in the school
			
	
			returnBool = true;
		}
		else if (schList.get(receiver).getFreeSpots() == 0) { // case for competing students
			
			// FINDING THE POOREST STUDENT
			ArrayList <Integer> lowestStuList = new ArrayList <>(Arrays.asList(0, 0)); // list representing the info of poorest student
			for (int a = 0; a <= sch_State.size() - 1; a++) { // loop to find the lowest ranked admitted student
				
				int stuID = sch_State.get(a); // student index
				int stu_Rank = sch_Rankings.get(stuID); // student's rank determined by the school
				
				if (stu_Rank > lowestStuList.get(1)) { // if the rank is poorer than the previous student in admission list, switch
					lowestStuList.set(0, stuID);
					lowestStuList.set(1, stu_Rank);
				}	
			}
			
			// COMPETITON BETWEEN STUDENTS
			int proposedRank = sch_Rankings.get(stuNum); // the suitor student's rank
			
			int removableStuIndex = sch_State.indexOf(lowestStuList.get(0)); // index in sch_State if the student is to be removed
			ArrayList <Integer> preStuMatchList = stuList.get(lowestStuList.get(0)).getMatchList(); // student to be removed's to be updated match list
			
			if (proposedRank < lowestStuList.get(1)) {
				
				this.matchLogicBool = false;
				preStuMatchList.set(0, -1); // now the previous match (student) is unmatched
				stu_State.set(0, receiver); // setting proposing student's match
				
				//System.out.println("2nd cond.");
				//System.out.println("Student free'd up: " + stuList.get(sch_State.get(removableStuIndex)).getName());
				
				sch_State.remove(removableStuIndex); // the poorer student is removed from the  school's admission list
				sch_State.add(stuNum); // proposing student is admitted into the list
				
				this.stuList.get(stuNum).incBestOption(); // admitted student's next best option is set
				
				returnBool = true;	
			}
			else {
				this.stuList.get(stuNum).incBestOption(); // in case student is not admitted, his/her next best option is still updated
				
				// PRINT STATEMENETS TO BE DELETED LATER
				//System.out.println("Name: " + stuList.get(stuNum).getName());
				//System.out.println("School name: " + schList.get(receiver).getName());
				//System.out.println("states (stu/sch): " + stu_State + sch_State);
				//System.out.println("rankings list state: " + studentRankings2);
				//System.out.println("Student's next best option: " + this.stuList.get(stuNum).getBestOption());
				
			}	
		}
		return returnBool;
	}
	
	public boolean makeSchoolEngagement(int schNum, int receiver) {
		
		// logic variables
		boolean rBool2 = false;
		
		// school variables
		ArrayList<Integer> sch_Rankings = schList.get(schNum).getRankingList();
		ArrayList<Integer> sch_Matches = schList.get(schNum).getMatchList();
		
		int schBestChoice = sch_Rankings.indexOf(schList.get(schNum).getBestOption()); // getting the school's best option
		receiver = schBestChoice; // school's best choice
		boolean freeSpots = schList.get(schNum).freeSpots(); // "any spots available?" //
		
		// student variables
		ArrayList<Integer> stu_Rankings = stuList.get(receiver).getRankingList();
		ArrayList<Integer> stu_Matches = stuList.get(receiver).getMatchList(); 
		
		// MATCHING
		if (freeSpots == true) { // school only proposes if it has open spots
			
			if (stu_Matches.get(0) == -1) {
				
				stu_Matches.set(0, schNum);
				if (sch_Matches.get(0) == -1) {
					sch_Matches.set(0, receiver); // in case the list was empty
				}
				else {
					sch_Matches.add(receiver);
				}
				
				schList.get(schNum).setSpotNum(); // decreasing the spots available
				schList.get(schNum).setBestOption(); // increasing the best option
				
				rBool2 = true;
				
			}
			
			// COMPETITION
			else if (stu_Matches.get(0) != -1) {
				
				int stu_PreMatchRank = stu_Rankings.indexOf(stu_Matches.get(0) + 1) + 1; // getting the rank of previously matched school
				int proposedRank = stu_Rankings.indexOf(schNum + 1) + 1; // getting the proposing school's rank 
				
				if (proposedRank < stu_PreMatchRank) {
					this.matchLogicBool = false;
					
					// REMOVALS
					School removing_Sch = schList.get(stu_Matches.get(0));
					removing_Sch.increaseSpots();
					ArrayList<Integer> removing_MList = removing_Sch.getMatchList();
					removing_MList.remove(removing_MList.indexOf(receiver)); // removing the student from previous school's list
					
					if (removing_MList.size() == 0) { // in case the school had only one match
						removing_MList.add(-1);
					}
					
					//System.out.println("2nd cond.");
					//System.out.println("School free'd up: " + removing_Sch.getName());
					
					// ADMISSION
					stu_Matches.set(0, schNum); // changing student's match list
					if (sch_Matches.get(0) == -1) { // adding student to the school's admission list
						sch_Matches.set(0, receiver); // in case the list was empty
					}
					else {
						sch_Matches.add(receiver);
					}
					
					// SETTING PARAMETERS
					schList.get(schNum).setSpotNum(); // decreasing the spots available
					schList.get(schNum).setBestOption(); // increasing the best option
					
					rBool2 = true;	
				}
				
				else {
					schList.get(schNum).setBestOption(); 
					
					// PRINT STATEMENETS TO BE DELETED LATER
					//System.out.println("Name: " + schList.get(schNum).getName());
					//System.out.println("Student name: " + stuList.get(receiver).getName());
					//System.out.println("states (stu/sch): " + stu_Matches + sch_Matches);
					//System.out.println("rankings list state: " + studentRankings2);
					//System.out.println("School's next best option: " + this.schList.get(schNum).getBestOption());
				}
			}		
		}
		return rBool2;
	}
	
	public boolean StuMatch() {
		
		boolean numChecker;
		int numOfUnmatchedStu;
		
		//int count = 0;
		
		long t0 = System.currentTimeMillis();
		
		do {
			numOfUnmatchedStu = 0;
			//count++;
			
			//System.out.println("---- " + "round: " + count + " -----------");
			
			for (int j = 0; j <= stuList.size() - 1; j++) {
				
				if (stuList.get(j).getMatchList().get(0) == -1) {
					numChecker = makeStudentEngagement(j, 0);
					//System.out.println(numChecker);
					//System.out.println();
					
					if (numChecker == true && this.matchLogicBool == false) {
						numOfUnmatchedStu++;
					}
					else if (numChecker == false) {
						numOfUnmatchedStu++;
					}
				}		
			}
			
		} while (numOfUnmatchedStu != 0);
		
		this.elapsedTime = System.currentTimeMillis() - t0;
		return true;
	}
	
	public boolean SchMatch() {
		
		boolean numChecker2;
		int unmatchedSch;
		
		long t0 = System.currentTimeMillis();
		//int count = 0;
		
		do {
			unmatchedSch = 0;
			//count++;
			
			//System.out.println("---- " + "round: " + count + " -----------");
			
			for (int z = 0; z <= schList.size() - 1; z++) {
				this.matchLogicBool = true;
				
				numChecker2 = makeSchoolEngagement(z, 0);
				//System.out.println(numChecker2);
				//System.out.println("MatchLogic Boolean: " + this.matchLogicBool);
				//System.out.println();
				
				if (this.matchLogicBool == false) { // a school eliminated its competition, need to do one more round
					unmatchedSch++;
				}
				else if (numChecker2 == true && schList.get(z).getFreeSpots() != 0) {
					unmatchedSch++;
				}
				//else if (numChecker2 == true && schList.get(z).freeSpots() == true) {
					//unmatchedSch++;
				//}
				else if (numChecker2 == false && schList.get(z).freeSpots() == true) { // condition for the case where no matching occurred
					unmatchedSch++;
				}
			}
		
		} while (unmatchedSch != 0);
		this.elapsedTime = System.currentTimeMillis() - t0;
		return true;
	}
	
	public void calcRegrets() {
		
		int stuRegret = 0;
		int schRegret = 0;
		
		// STUDENT SECTION
		for (int x = 0; x <= stuList.size() - 1; x++) {
			
			ArrayList<Integer> stu_MatchList = stuList.get(x).getMatchList(); 
			int stu_assign = stu_MatchList.get(0); // getting the student's matched school
			ArrayList<Integer> stu_RankList = stuList.get(x).getRankingList();
			
			int stuS_Rank = stu_RankList.indexOf(stu_assign + 1) + 1; // student's rank of matched school
			stuRegret = stuS_Rank - 1;
			
			this.avgSuitorRegret += stuRegret;	
		}
		double numOfStu = stuList.size();
		this.avgTotalRegret += this.avgSuitorRegret;
		this.avgSuitorRegret /= numOfStu;
		//System.out.println("Student regret: " + this.avgSuitorRegret);
		
		// SCHOOL SECTION
		for (int j = 0; j <= schList.size() - 1; j++) {
			
			ArrayList<Integer> sch_MatchList = schList.get(j).getMatchList();
			ArrayList<Integer> sch_RankList = schList.get(j).getRankingList();
			int sch_assign;
			int schS_Rank;
			
			for (int e = 0; e <= sch_MatchList.size() - 1; e++) {
				sch_assign = sch_MatchList.get(e); // getting the student that was admitted
				schS_Rank = sch_RankList.get(sch_assign); // getting school's rank of admitted student
				
				schRegret = schS_Rank - 1;
				this.regretList.add(schRegret); // FINAL EXAM
				this.avgReceiverRegret += schRegret;
			}
		}
		double numOfSch = schList.size();
		this.avgTotalRegret += this.avgReceiverRegret;
		this.avgReceiverRegret /= numOfSch;
		//System.out.println("School regret: " + this.avgReceiverRegret);
		
		this.avgTotalRegret /= (numOfStu + numOfSch);
	}
	
	public void calcStdDeviation() { // check line 342 for the logic of my SMPSolver
		
		this.std_dev = 0;
		double average = 0.0;
		int n = this.regretList.size();
		int regret;
		int sigmaSum = 0;
		
		if (n == 1) {
			this.std_dev = 0;
		}
		else {
			for (int i = 0; i <= this.regretList.size() - 1; i++) { // calculating the mean
				average += this.regretList.get(i);
			}
			average /= n;
			
			for (int u = 0; u <= n - 1; u++) {
				sigmaSum += Math.pow(this.regretList.get(u) - average, 2);
			}
			
			this.std_dev = Math.sqrt(sigmaSum / n); 
		}
		
	}
		
	
	
}
