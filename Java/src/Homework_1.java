
public class Homework_1 {
	
	public static void main(String[] args){

		
		// Q1: 請設計一隻Java程式,計算12,6這兩個數值的和與積
		int product;
		int sum;
		int a = 12;
		int b = 6;
		
		product = a * b;
		sum = a + b;
		System.out.println("Q1 Ans: 12x6 =" + product + "; " + " 12+6=" + sum); //積
		
		
		// Q2: 請設計一隻Java程式,計算200顆蛋共是幾打幾顆? (一打為12顆)
		
		final int EGG_Num = 200;
		int quotient, remain;
		
		quotient = EGG_Num / 12;
		remain = EGG_Num % 12;
		System.out.printf("Q2 Ans: 200顆蛋共是  %d打 %d顆%n", quotient, remain);
		
		// Q3: 請由程式算出256559秒為多少天、多少小時、多少分與多少秒
		
		final int TOTAL_SEC = 256559;
		int dayNum; 
		int hourNum;
		int minNum;
		int secNum;
		int tempRemain;
		
		//計算天數
		dayNum = TOTAL_SEC / 86400;     
		tempRemain = TOTAL_SEC % 86400;
		
		//計算小時
		hourNum = tempRemain / 3600;
		tempRemain %= 3600;
		
		//計算分鐘
		minNum = tempRemain / 60;
		tempRemain %= 60;
		
		//餘數為秒
		secNum = tempRemain;
		
		System.out.println("Q3 Ans: 256559秒為 " + dayNum + "天" + hourNum + "小時" +
							minNum + "分" + secNum + "秒");
		
		// Q4: 請定義一個常數為3.1415(圓周率),並計算半徑為5的圓面積與圓周長
		
		final double PI = 3.1415;
		double circumference;
		double area;
		
		circumference = 2 * PI * 5;   // 2πr
		area = 5 * 5 *  PI;  // πr^2
		System.out.println("Q4 Ans: 圓面積: " + area + " 圓周長: " + circumference);
		//System.out.printf("Q4 Ans: 圓面積: %.4f 圓周長: %.4f %n", area, circumference);
		
		// Q5: 某人在銀行存入150萬,銀行利率為2%,如果每年利息都繼續存入銀行,請用程式計算10年後,本金加利息共有多少錢
		
		double principal = 1_500_000;
		double factor = 1.0;
		double interest = 2.0;
		
		// 計算複利因子
		for(int i=0 ; i<10 ; i++){
			factor = factor * ( 1+ (interest / 100)); 
		}
		
		principal *= factor;
		
		System.out.println("Q5 Ans: 銀行存入150萬,銀行利率為2 10年後,本金加利息共有 " + principal + " 元");
		//System.out.printf("Q5 Ans: 銀行存入150萬,銀行利率為2 10年後,本金加利息共有  %.4f 元 %n", principal);
		
		/* Q6: 請寫一隻程式,利用System.out.println()印出以下三個運算式結果:
		 * 5 + 5
		 * 5 + '5'
		 * 5 + "5"
		 * 並請用註解各別說明答案的產生原因
		 */
		
		System.out.println("Q6 Ans:");
		System.out.println("\t5 + 5 = " + (5+5) + " 因為兩個5都為數字");
		System.out.println("\t5 + '5' = " + (5 + '5') + " 因為後面的5為字元 unicode \u0035值等於53");
		System.out.println("\t5 + \"5\" = " + (5 + "5") + " 因為後面的5為字串  所以為字串串連");
		

	}
}
