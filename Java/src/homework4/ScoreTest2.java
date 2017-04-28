package homework4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ScoreTest2 {
	//進階二 使用List和 Score class
	
	public static void main(String[] args) {
		Map<Integer,Integer> records = new TreeMap<>();
		List<Score> scores = new ArrayList<>();
		
		scores.add(new Score(10, 35, 40, 100, 90, 85, 75, 70));
		scores.add(new Score(37, 75, 77, 89, 64, 75, 70, 95));
		scores.add(new Score(100, 70, 79, 90, 75, 70, 79, 90));
		scores.add(new Score(77, 95, 70, 89, 60, 75, 85, 79));
		scores.add(new Score(98, 70, 89, 90, 75, 90, 89, 90));
		scores.add(new Score(90, 80, 100, 75, 50, 20, 99, 75));
		
		
		for(Score s : scores){
			int maxScore = s.findMax();
			for(int i = 0; i < s.getSize(); i++){
				
				if(s.getSingleScore(i) == maxScore){
					if(records.containsKey(i + 1)){
						records.replace(i + 1, records.get(i + 1) + 1);   // replace method
					}else {
						records.put(i + 1, 1);   // put method
					}
				}
			}
		}
		
		System.out.println(records);
			
	}
}
