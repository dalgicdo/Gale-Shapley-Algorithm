import java.util.ArrayList;

public class Student extends Participant {
	
	private double GPA;
	private int ES;
	private int bestOption;
	private ArrayList <Double> IndCompScoreList = new ArrayList<Double>();
	
	// CONSTRUCTORS
	public Student(double GPA, int ES) {
		super();
		this.GPA = GPA;
		this.ES = ES;
		this.bestOption = 1;
		ArrayList <Double> IndCompScoreList = new ArrayList<Double>();
	}
	
	// getters 
	public double getGPA() {
		return this.GPA;
	}
	public int getES() {
		return this.ES;
	}
	public int getBestOption() {
		return this.bestOption;
	}
	public ArrayList<Double> getIndCompScoreList() {
		return this.IndCompScoreList;
	}
	
	// setters
	public void setGPA(double GPA) {
		this.GPA = GPA;
	}
	public void setES (int ES) {
		this.ES = ES;
	}
	public void incBestOption() {
		this.bestOption += 1;
	}
	public void setBestOption() {
		this.bestOption = 1;
	}
	
	// useful methods
	public void printStu(ArrayList <? extends Participant> schList, int i) {
		String stuName = super.getName(); // using "super" to access the parent class, also could have used this because of inheritance
		String stuGPA = String.format("%.2f", this.GPA);
		int stuES = this.ES;
		String sch_assigned = "-";
		String sch_order = "";
		ArrayList<Integer> rankList = super.getRankingList();
		 
		if (super.getMatchList().get(0) != -1) {
			String schName = schList.get(super.getMatchList().get(0)).getName();
			sch_assigned = schName;
		}
		
		for (int b = 0; b <= rankList.size() - 1; b++) {
			
			if (b == rankList.size() - 1) {
				sch_order += schList.get(rankList.get(b) - 1).getName();
			}
			else {
				sch_order +=  schList.get(rankList.get(b) - 1).getName() + ", ";
			}
		}
		if (i < 9) {
			System.out.printf("%-5s%-44s%-7s%-3s%-40s%-24s%n", "  " + (i+1) + ". ", stuName, stuGPA, stuES, sch_assigned, sch_order);
		}
		else if (i > 8 && i < 99) {
			System.out.printf("%-5s%-44s%-7s%-3s%-40s%-24s%n", " " + (i+1) + ". ", stuName, stuGPA, stuES, sch_assigned, sch_order);
		}
		else if (i > 98 && i < 999) {
			System.out.printf("%-5s%-44s%-7s%-3s%-40s%-24s%n", (i+1) + ". ", stuName, stuGPA, stuES, sch_assigned, sch_order);
		}
		
	}

}
