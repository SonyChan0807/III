package homework10;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class DateConverterTest {
	public static void main(String[] args) {
		
		String regEx1 = "\\d{4}(0[1-9]|1[1-2])([0-2][1-9]|3[0-1])";
		String regEx2 = "[123]";
		Date date = null;
		
		Scanner sc = new Scanner(System.in);
		System.out.println("請輸入日期");
		
		while (true){
			String dateStr = sc.nextLine();
			if (dateStr.length() != 8 || !(dateStr.matches(regEx1))){
				System.out.println("輸入日期格是錯誤");
			} else {
				try {
					SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
					date = sd.parse(dateStr);
				} catch (Exception e){
					System.out.println("格式不正確");
				}
				break;
			}
		}
				
		
		System.out.println("格是正確 ,請輸入轉換格式 (1)年/月/日(2)月/日/年(3)日/月/年三選一");
		String option = "";
		while (true){
			
			option = sc.nextLine();
			if (option.matches("[1-3]")){
				break;
			} else {
				System.out.println("請輸入1~3任一數字");
			}
		}
		
		switch (option){
		case "1":
			System.out.printf("%tY 年  %<tm 月 %<td 日%n", date);
//			break;
		case "2":
			System.out.printf("%tm 月  %<td 日 %<tY 年%n", date);
//			break;
		case "3":
			System.out.printf("%td 日   %<tm 月 %<tY 年%n", date);
//			break;
		}
		
	}
	

}
