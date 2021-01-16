import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class School extends Participant {
	
	private double alpha;
	private int maxMatches; // max # of allowed matches / openings
	private int realTimeSpots; // var. to check if school still has any spot left
	private int bestOption;
	private ArrayList<Double> compScoreList = new ArrayList<Double>(); 
	
	// CONSTRUCTORS
	public School(double alpha, int spotsAvailable) {
		super();
		this.alpha = alpha;
		this.maxMatches = spotsAvailable;
		this.compScoreList = new ArrayList<Double>(); 
		this.realTimeSpots = spotsAvailable;
		this.bestOption = 1; 
	}
	
	// getters
	public double getAlpha() {
		return this.alpha;
	}
	public int getMaxMatches() {
		return this.maxMatches;
	}
	public int getFreeSpots() {
		return this.realTimeSpots;
	}
	public int getBestOption() {
		return this.bestOption;
	}
	public ArrayList<Double> getCompScoreList() {
		return this.compScoreList;
	}
	
	
	// setters
	public void setAlpha(double Alpha) {
		this.alpha = Alpha;
	}
	public void setSpotNum() {
		this.realTimeSpots -= 1;
	}
	public void increaseSpots() {
		this.realTimeSpots += 1;
	}
	public void setBestOption() {
		this.bestOption += 1;
	}
	public void setMaxSpots() {
		this.realTimeSpots = this.maxMatches;
	}
	public void setMaxSpots2(int a) {
		this.maxMatches = a;
	}
	public void setOption() {
		this.bestOption = 1;
	}
	
	// other
	public boolean isValid() {
		if (this.alpha < 0) {
			return false;
		}
		else {
			return true;
		}
	}
	public boolean freeSpots() {
		if (this.realTimeSpots != 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void printSch(ArrayList <? extends Participant> stuList, ArrayList <? extends Participant> schList , int i) {
		String schName = super.getName();
		String schAlpha = String.format("%.2f", this.alpha);
		int spots = this.maxMatches;
		String stu_assigned = "-";
		String stu_order = "";
		ArrayList<String> printList = new ArrayList<String>();
		
		if (super.getMatchList().get(0) != -1) { // need to change this later on since one school can have more than one student
			stu_assigned = "";
			for (int r = 0; r <= super.getMatchList().size() - 1; r++) {
				String stuName = stuList.get(super.getMatchList().get(r)).getName();
				
				if (r == super.getMatchList().size() - 1) {
					stu_assigned += stuName;
				}
				else {
					stu_assigned += stuName + ", ";
				}
			}
		}
		
		for (int r = 0; r <= stuList.size() - 1; r++) { // initializing my print array for schools
			printList.add("");
		}
		
		for (int e = 0; e <= stuList.size() - 1; e++) {
			//System.out.println(super.getRankingList());
			int placement_Index = super.getRankingList().get(e) - 1;
			//System.out.println(stuList.get(e));
			String stuName = stuList.get(e).getName();
			
			if (placement_Index == stuList.size() - 1) {
				printList.set(placement_Index, stuName);
			}
			else {
				printList.set(placement_Index, stuName + ", ");
			}
		}
		
		for (int x = 0; x <= printList.size() - 1; x++) { //constructing the print string
			stu_order += printList.get(x);
		}
		
		if (i < 9) {
			System.out.printf("%-5s%-47s%-5s%-6s%-40s%-23s%n", "  " + (i+1) + ". ", schName, spots, schAlpha, stu_assigned, stu_order);	
		}
		else if (i > 8 && i < 99) {
			System.out.printf("%-5s%-47s%-5s%-6s%-40s%-23s%n", " " + (i+1) + ". ", schName, spots, schAlpha, stu_assigned, stu_order);
		}
		else if (i > 98 && i < 999) {
			System.out.printf("%-5s%-47s%-5s%-6s%-40s%-23s%n", (i+1) + ". ", schName, spots, schAlpha, stu_assigned, stu_order);	
		}
	}
	
	public void calcRankings(ArrayList <Student> stuList) {
		ArrayList <Double> stu_Cscores = this.compScoreList; // school's recordings of student' cScores
		//stu_Cscores.clear();
		
		for (int i = 0; i <= stuList.size() - 1; i++) {
			ArrayList <Double> ind_Cscores = stuList.get(i).getIndCompScoreList(); // student's individual list of cScores
			ind_Cscores.clear();
			
			double stuGPA = stuList.get(i).getGPA();
			int stuES = stuList.get(i).getES();
			double actual_ES = stuES;
			double alpha = this.alpha;
			
			double compScore = alpha * stuGPA + (1 - alpha) * actual_ES;
			stu_Cscores.add(compScore);
			ind_Cscores.add(compScore);
		}
		Collections.sort(stu_Cscores, Collections.reverseOrder());
		
		Map <Double, ArrayList<Integer>> occurences = new HashMap<Double, ArrayList<Integer>>(); // utilizing HashMaps for the order of the students
		for (int k = 0; k <= stu_Cscores.size() - 1; k++) {
			ArrayList<Integer> keyList = new ArrayList<Integer>();
			double keyVal = stu_Cscores.get(k);
			
			for (int a = 0; a <= stu_Cscores.size() - 1; a++) {
				double checkVal = stu_Cscores.get(a);
				
				if (checkVal == keyVal) {
					keyList.add(a); // adding the position of the repeating composite score
				}
			}
			occurences.put(stu_Cscores.get(k), keyList); // key: repeating comp. score, value: its associated repetition indexes	
		}
		
		//System.out.println("Sorted list befpre part 2: " + stu_Cscores);
		//System.out.println("The repeating values dictionary: " + occurences);
		
		for (int j = 0; j <= stuList.size() - 1; j++) {
			ArrayList <Double> ind_Cscores2 = stuList.get(j).getIndCompScoreList(); // student's individual list of cScores
			//System.out.println(ind_Cscores2);
			
			for (int b = 0; b <= ind_Cscores2.size() - 1; b++) { // looping through student's individual cScore list
				
				double cScore = ind_Cscores2.get(b);
				int rankIndex = stu_Cscores.indexOf(cScore) + 1; // looking at it inside school's records (its index)!
				
				if (rankIndex != 0) {
					
					ArrayList<Integer> repList = occurences.get(cScore); // getting how many equal values cScores are
					for (int y = 0; y <= repList.size() - 1; y++) {
						
						if (repList.get(y) != -1) {
							rankIndex += y;
							repList.set(y, -1); // meaning that the student before took the spot
							break;
						}
					}
					
					super.getRankingList().add(j, rankIndex);
				}
				//System.out.println(cScore + " -- " + rankIndex);
			}
		}
		//System.out.println(this.rankings);
		//System.out.println("-------------------");
	}
	
	public void printMatches(ArrayList<Student> stuList) {
		
		String stuAdmitted;
		
		stuAdmitted = "";
		for (int r = 0; r <= super.getMatchList().size() - 1; r++) {
			String stuName = stuList.get(super.getMatchList().get(r)).getName();
				
			if (r == super.getMatchList().size() - 1) {
				stuAdmitted += stuName;
			}
			else {
				stuAdmitted += stuName + ", ";
			}
		}
		System.out.println(super.getName() + ": " + stuAdmitted);
		
	}
}
