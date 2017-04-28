package homework4;

import java.util.Scanner;

public class DayOfYearTest2 {
	
	//請設計一隻程式由鍵盤輸入三個整數，分別代表西元yyyy年，mm月，dd日，它會顯示是該年的第幾天
	//"有"錯誤重新輸入功能
	
	static int[] monthDay = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	static int[] monthDayRum = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	
	public static void main(String[] args) {

		int[] input = new int[3];
		boolean isCorrect = false;
		System.out.println("三個整數分別代表西元yyyy年mm月dd日");
		Scanner console = new Scanner(System.in);
	
		while(!isCorrect){
		
			for(int i = 0; i < input.length; i++){
			    input[i] = console.nextInt();
			}
			
			isCorrect = checkFormat(input);
		
        }
		
		console.close();
		
		countDay(input[0], input[1], input[2]);
		
		
}

	public static boolean checkFormat(int[] input){
		
		int[] array;
		if ( input[0] > 0){
		     array = yearPicker(input[0]);
		} else {
		    System.out.println("年份格式錯誤,請重新輸入一次");
			return false;
		}
	
		if (input[1] >12 || input[1] <= 0) {
			System.out.println("月份格式錯誤,請重新輸入一次");
			return false;
		
		}
	
		if(input[2] > array[input[1] - 1]) {
			System.out.println("日期格式錯誤 ,請重新輸入一次");
			return false;
		}
		System.out.println("日期格式正確");
		return true;
	}

	public static int[] yearPicker(int year) {
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			return monthDayRum; // 可被4除盡但不能被100除盡 或 400除盡 回傳閏年陣列
		} else {
			return monthDay;
		}
	}

	public static void countDay( int year,int month, int day) {
		int count = 0;
		int[] monthArray = yearPicker(year);
		for (int i = 0; i < month- 1; i++) {
			count += monthArray[i];
		}
		count += day;

		System.out.printf("西元%d年,第%d天", year, count);

	}

}
