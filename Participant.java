import java.util.ArrayList;
import java.util.Arrays;

public class Participant {
	
	private String name; // name
	private ArrayList <Integer> rankings = new ArrayList <Integer>(); // rankings of participants
	private ArrayList <Integer> matchList = new ArrayList <Integer>(); // match indices --> for students and schools initialize as -1
	private double regret; // total regret
	
	
	// CONSTRUCTORS
	public Participant() {
		this.name = "";
		this.rankings =  new ArrayList <Integer>();
		this.matchList = new ArrayList <>(Arrays.asList(-1)); // the method I use to indicate that in the beginning no one is matched
		this.regret = 0.0;
	}
	
	// getters
	public String getName() {
		return this.name;
	}
	public ArrayList<Integer> getRankingList() {
		return this.rankings;
	}
	public ArrayList<Integer> getMatchList() {
		return this.matchList;
	}
	public double getRegret() {
		return this.regret;
	}
	public int getNParticipants() { // return length of rankings array
		return this.matchList.size();
	}
	
	// setters
	public void setName (String name) {
		this.name = name;
	}
	public void setRanking (int i , int r) {
		this.rankings.add(i, r);
	}
	public void setMatch (int m) {
		this.matchList.add(m);
	}
	public void setRegret (int r) {
		this.regret += r;
	}
	
	
	
}
