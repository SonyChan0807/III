package homework4;

public class ScoreTest {
	
	//進階二 純使用Array
	
	public static void main(String[] args){
		
		int[][] grades = {
			{10, 35, 40, 100, 90, 85, 75, 70},
			{37, 75, 77, 89, 64, 75, 70, 95},
			{100, 70, 79, 90, 75, 70, 79, 90},
			{77, 95, 70, 89, 60, 75, 85, 79},
			{98, 70, 89, 90, 75, 90, 89, 90},
			{90, 80, 100, 75, 50, 20, 99, 75}
		};
		
		int[] hiGrades = new int[6];   	//每次最高分
		int[] hiCounts = new int[8];	//每位同學最高分次數
		
		for(int i = 0; i < grades.length; i++){
			int hiGrade = 0;
			for(int j = 0; j < grades[i].length; j++){
				if (grades[i][j] > hiGrade){
					hiGrade = grades[i][j];
				}
				hiGrades[i] = hiGrade;
			}
		}
		
		for(int i = 0; i < grades.length; i++){
			for(int j = 0; j < grades[i].length; j++){
				if (grades[i][j] == hiGrades[i]){
					hiCounts[j] += 1;
				}
			}
		}
		
		for(int i = 0; i < hiCounts.length; i++){
			System.out.println( (i+1) + "號同學考最高分的次數: " + hiCounts[i]);
		}
	}
	
	
}
