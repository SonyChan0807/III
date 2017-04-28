package homework5;

// 利用Overloading，設計兩個方法double maxElement(int x[][])與double maxElement(double x[][])，可以找出二維陣列的最大值並回傳

public class MaxElementTest {
	public static void main(String[] args) {
		int[][] intArray = { { 1, 6, 3 }, { 9, 5, 2 } };

		double[][] doubleArray = { { 1.2, 3.5, 2.2 }, { 7.4, 2.1, 8.2 } };

		System.out.println(maxElement(intArray));
		System.out.println(maxElement(doubleArray));

	}

	public static int maxElement(int[][] intArray) {
		int max = 0;

		for (int i = 0; i < intArray.length; i++) {
			for (int j = 0; j < intArray[i].length; j++){
				if (max < intArray[i][j]) {
					max = intArray[i][j];
				}
			}
		}
		return max;
	}

	public static double maxElement(double[][] doubleArray) {
		double max = 0;
		for (int i = 0; i < doubleArray.length; i++) {
			for (int j = 0; j < doubleArray[i].length; j++) {
				if (max < doubleArray[i][j]) {
					max = doubleArray[i][j];
				}
			}
		}
		return max;
	}
}
