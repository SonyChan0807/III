package homework4;

import java.util.Scanner;

public class DayOfYearTest {

	//請設計一隻程式由鍵盤輸入三個整數，分別代表西元yyyy年，mm月，dd日，它會顯示是該年的第幾天
	//"無"錯誤重新輸入功能
	
	static int[] monthDay = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	static int[] monthDayRum = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public static void main(String[] args) {

		int year;
		int month;
		int day;

		System.out.println("三個整數分別代表西元yyyy年mm月dd日");
		Scanner console = new Scanner(System.in);

		year = console.nextInt();
		month = console.nextInt();
		day = console.nextInt();

		if (year > 0) {
			if (month <= 12 && month > 0) {
				if (isRumYear(year)) {
					if (day <= monthDayRum[month - 1]) {
						countDay(monthDayRum, year, month, day);
					} else {
						System.out.println("日期錯誤");
					}
				} else {
					if (day <= monthDay[month - 1]) {
						countDay(monthDay, year, month, day);
					} else {
						System.out.println("日期錯誤");
					}
				}
			} else {
				System.out.println("月份錯誤");
			}
		} else {
			System.out.println("年份錯誤");
		}

		console.close();
	}

	public static boolean isRumYear(int year) {
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			return true; // 可被4除盡但不能被100除盡  或 400除盡
		} else {
			return false; 
		}
	}

	public static void countDay(int[] monthArray, int year, int month, int day) {
		int count = 0;

		for (int i = 0; i < month - 1; i++) {
			count += monthArray[i];
		}
		count += day;

		System.out.printf("西元%d年,第%d天", year, count);

	}

}
