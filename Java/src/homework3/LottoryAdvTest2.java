package homework3;

import java.util.Arrays;
import java.util.Scanner;

public class LottoryAdvTest2 {
	// 進階挑戰：輸入不要的數字後，直接亂數印出6個號碼且不得重覆
	// 純Array方法

	public static void main(String[] args) {

		int hateNum;

		System.out.println("阿文 請輸入你討厭的數字");
		Scanner console = new Scanner(System.in);

		// 檢驗範圍
		while (true) {

			hateNum = Integer.parseInt(console.nextLine());

			if (hateNum <= 9 && hateNum >= 0) {
				break;
			} else {
				System.out.println("超出範圍@@ 再輸入一次.....");
			}
		}

		console.close();

		// 產生排除討厭數字的Array copyOf 解決不定長度
		int[] lArray = new int[1];
		int choice = 0;
		for (int i = 1; i <= 49; i++) {
			if ((i % 10.0) == hateNum || (i / 10.0) == hateNum) {
				continue;
			} else {
				lArray = Arrays.copyOf(lArray, choice + 1);
				lArray[choice] = i;
				choice++;
			}
		}

		int[] randomArray = swapArray(lArray);

		for (int j = 0; j < 6; j++) {
			System.out.printf("%d ", randomArray[j]);
		}

	}

	// implement swap by Array
	public static int[] swapArray(int[] array) {
		int temp;
		for (int i = 0; i < array.length; i++) {
			int randomIndex = (int) (Math.random() * array.length);
			temp = array[randomIndex];
			array[randomIndex] = array[i];
			array[i] = temp;

		}
		return array;

	}

}
