package homework3;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class LottoryAdvTest {

	// 進階挑戰：輸入不要的數字後，直接亂數印出6個號碼且不得重覆

	public static void main(String[] args) {

		int hateNum;

		System.out.println("阿文 請輸入你討厭的數字");
		Scanner console = new Scanner(System.in);
		
		//檢驗範圍
		while (true) {

			hateNum = Integer.parseInt(console.nextLine());

			if (hateNum <= 9 && hateNum >= 0) {
				break;
			} else {
				System.out.println("超出範圍@@ 再輸入一次.....");
			}
		}

		console.close();

		System.out.println("============");
		
		int index;
		int choice = 0;
		List<Integer> lotteryList = new ArrayList<>();
		Set<Integer> lotterySet = new HashSet<>();

		// 產生排除討厭數字的list
		for (int i = 1; i <= 49; i++) {
			if ((i % 10.0) == hateNum || (i / 10.0) == hateNum) {
				continue;
			} else {
				lotteryList.add(i);
				choice++;
			}
		}

		// set 方法
		do {
			index = (int) (Math.random() * choice);
			lotterySet.add(lotteryList.get(index));
		} while (lotterySet.size() < 6);

		
		lotterySet.forEach(System.out::println);
		
		System.out.println("============");

		
		// Shuffle 方法
		Collections.sort(lotteryList);
		Collections.shuffle(lotteryList);
		for (int i = 0; i < 6; i++) {
			System.out.printf("%d%n", lotteryList.get(i));
		}
		
		System.out.println("============");
		
		// Swap 方法
		Collections.sort(lotteryList);
		for (int i = 0; i < 6; i++){
			Collections.swap(lotteryList, i, (int)(Math.random()*lotteryList.size()));
			System.out.printf("%d%n",lotteryList.get(i));
		}	
	}
}
