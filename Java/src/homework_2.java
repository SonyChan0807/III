
public class homework_2 {
	public static void main(String[] args){
		
		
		// Q1 計算1～1000的偶數和 (2+4+6+8+…+1000)
		
		long sum = 0L;
		for(int i = 0; i <= 1000; i+=2){
			sum += i;
		}
		System.out.println("Q1 Ans: " + sum);
		
		// Q2 計算1～10的連乘積 (1*2*3*…*10) (用for迴圈)
		
		double productFor = 1;
		for(int i = 0; i < 10; i++ ){
			productFor = (i + 1) * productFor;
		}
		System.out.println("Q2 Ans:" + productFor);
		
		// Q3 計算1～10的連乘積 (1*2*3*…*10) (用while迴圈)
		
		int j = 1;
		double productWhile = 1;
		while(j <= 10){
			productWhile = j * productWhile;
			j++;
		}
		System.out.println("Q3 Ans:" + productWhile);
		
		// Q4 1 4 9 16 25 36 49 64 81 100
		
		for(int i = 1; i <= 10; i++){
			System.out.printf("%d ",i*i);
		}
		
		
	}
}
