import java.util.*;

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
    int deadline;
    int completiontime;
	
	
	protected Assignment() {
	}
	
	protected Assignment(int number, int weight, int deadline, int completiontime) {
		this.number = number;
		this.weight = weight;
        this.deadline = deadline;
        this.completiontime = completiontime;
	}
	
	
	
	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 * Return -1 if a1 > a2
	 * Return 1 if a1 < a2
	 * Return 0 if a1 = a2 
	 */
	@Override
	public int compare(Assignment a1, Assignment a2) {
		
		// TODO Implement this
		//compare a1weight/completiontime vs a2weight/completiontime
		
		int a1wtoh = a1.weight / a1.completiontime;
		int a2wtoh = a2.weight / a2.completiontime;
		
		if(a1wtoh > a2wtoh) {
			return -1;
		}
		
		else if(a1wtoh < a2wtoh) {
			return 1;
		}
		
		if(a1wtoh == a2wtoh) {
			if(a1.deadline < a2.deadline) {
				return -1;
			}
			else if(a1.deadline > a2.deadline) {
				return 1;
			}
			
		}
		
		return 0;
		
	}
}

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
    int lastDeadline = 0;
    double grade = 0.0;
	
	protected HW_Sched(int[] weights, int[] deadlines, int[] completiontimes, int size) throws Exception {
        if(size==0){
            throw new Exception("There is no assignment.");
        }
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i], completiontimes[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m =size;
	}
	
	
	/**
	 * 
	 * @return Array where output[i] corresponds to the assignment 
	 * that will be done at time i.
	 */
	public ArrayList<Integer> SelectAssignments() {
		
		//Sort assignments
		//Order will depend on how compare function is implemented
		Collections.sort(Assignments, new Assignment());

        //TODO Implement this

		// If homeworkPlan[i] has a value -1, it indicates that the 
		// i'th timeslot in the homeworkPlan is empty
		ArrayList<Integer> homeworkPlan = new ArrayList<>();
		for (int i=0; i < lastDeadline; ++i) {
			homeworkPlan.add(-1);
		}
		
		//plan: do hw with biggest weight, soonest deadline & shortest completiontime
		//note: hws can be submitted late, -10% of weight every hour
		//late past due date is 0%
		//no need to finish all homeworks in one day if it doesnt work
		
		
		double perfgrade = 0.0;
		
		for(int i = 0, j = 0; i < Assignments.size();) {
			
			
			for(int k = 0; k < Assignments.get(i).completiontime; k++) {
				
				
				if(j < homeworkPlan.size() && j < Assignments.get(i).deadline) {
					
					homeworkPlan.set(j, Assignments.get(i).number);
					j++;
				}
				else if(j >= homeworkPlan.size() && j < Assignments.get(i).deadline){
					
					homeworkPlan.add(Assignments.get(i).number);
					grade = grade - (double)(Assignments.get(i).weight * 0.1);
					j++;
					
					
				}
				else if(j < homeworkPlan.size() && j >= Assignments.get(i).deadline && homeworkPlan.get(j-1) == Assignments.get(i).number){
					
					homeworkPlan.set(j, Assignments.get(i).number);
					grade = grade - (Assignments.get(i).weight * 0.1);
					j++;
				}
				else if(j < homeworkPlan.size() && j >= Assignments.get(i).deadline && homeworkPlan.get(j-1) != Assignments.get(i).number){
					
					grade = grade - (double)(Assignments.get(i).weight / Assignments.get(i).completiontime);
					
					
				}
				else if(j >= homeworkPlan.size() && j >= Assignments.get(i).deadline && homeworkPlan.get(j-1) == Assignments.get(i).number){
					
					homeworkPlan.add(Assignments.get(i).number);
					grade = grade - (Assignments.get(i).weight * 0.1);
					j++;
					
					
				}
		
				else if(j >= homeworkPlan.size() && j >= Assignments.get(i).deadline && homeworkPlan.get(j-1) != Assignments.get(i).number){
					
					grade = grade - (double)(Assignments.get(i).weight / Assignments.get(i).completiontime);
					
				}
				
			}
			
			grade = grade + Assignments.get(i).weight;
			perfgrade = perfgrade + Assignments.get(i).weight;
			i++;
		}
		
		//grade = (((double)(int) grade / (int) perfgrade) * (double) 100.0);
		
		
		
		return homeworkPlan;
		
	}
	public static void main(String[] args) throws Exception {
		int[] weights = {40, 20};
        int[] deadlines = {2, 6};
        int[] comptimes = {4, 5};
        
        int m = weights.length;
		
        HW_Sched plan = new HW_Sched(weights, deadlines, comptimes, m);
        System.out.println(plan.SelectAssignments());
        System.out.println(plan.grade);

      
	}
}
	



