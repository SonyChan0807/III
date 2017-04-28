package homework4;

public class ArrayAvgTest {

	// 有個一維陣列如下：
	// {29, 100, 39, 41, 50, 8, 66, 77, 95, 15}
	// 請寫出一隻程式能輸出此陣列所有元素的平均值與大於平均值的元素

	public static void main(String[] args) {

		double sum = 0;
		double avg = 0;
		int[] numArray = new int[] { 29, 100, 39, 41, 50, 8, 66, 77, 95, 15 };

		for (int i = 0; i < numArray.length; i++) {
			sum += numArray[i];
		}

		avg = sum / numArray.length;

		System.out.printf("平均數: %2.2f 大於平均的數有: ", avg);

		for (int i = 0; i < numArray.length; i++) {
			if (numArray[i] > avg) {
				System.out.printf("%d ", numArray[i]);
			}
		}

	}
}
