package homework10;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Scanner;


public class NumConvertionTest {
	public static void main(String[] args) {
		String regEx = "\\d*\\.?\\d*";
		String numStr = "";
		String option = "";
		double num = 0.0;

		Scanner sc = new Scanner(System.in);
		System.out.println("請輸入數字:");

		while (true) {
			numStr = sc.nextLine();
			if (numStr.matches(regEx)) {
				num = Double.valueOf(numStr);
				break;
			} else {
				System.out.println("數字格式錯誤,請再輸入一次");
			}
		}

		System.out.println("請選擇(1)千分位(2)百分比(3)科學記號:");
		while (true){
			
			option = sc.nextLine();
			if (option.matches("[1-3]")){
				break;
			} else {
				System.out.println("請輸入1~3任一數字");
			}
		}
		
		switch (option) {
		case "1":
			Format df = new DecimalFormat("#,###.00000");
			System.out.println(df.format(num));
			break;
		case "2":
			Format df1= new DecimalFormat("#.00%");
			System.out.println(df1.format(num));
			break;
		case "3":
			System.out.printf("%6.2E", num);
			break;
		}
		
		sc.close();

	}
}
