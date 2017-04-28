package homework4;

import java.util.Scanner;

public class BorrowMoneyTest {
	
	//進階題一 
	
	public static void main(String[] args) {
		int[][] mArray = { { 25, 2500 }, { 32, 800 }, { 8, 500 }, { 19, 1100 }, { 27, 1200 } };
		int money;
		int countMember = 0;

		String member = " ";

		System.out.println("想借多少錢??");
		Scanner console = new Scanner(System.in);

		money = console.nextInt();

		console.close();

		for (int i = 0; i < mArray.length; i++) {
			if (mArray[i][1] >= money) {
				member = member + " " + mArray[i][0];
				countMember++;
			}
		}
		System.out.printf("有錢可借的員工編號: %s 共  %d人!", member, countMember);

	}
}
