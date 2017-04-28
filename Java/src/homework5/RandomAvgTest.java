package homework5;


//請設計一個方法為randAvg()，從10個 0～100(含100)的整數亂數中取平均值並印出這10個亂數與平均值


public class RandomAvgTest {
	public static void main(String[] args){

		int sum = 0;
		int[] nums = new int[100];
		
		// Create sequential numbers from 1 to 100
		for(int i = 0 ; i < nums.length; i++){
			nums[i] = i + 1;
		}
		
		// random swap numbers
		
		for(int i = 0; i < nums.length; i++){
			int temp = nums[i];
			int index = (int) (Math.random()*100);
			nums[i] = nums[index];
			nums[index] =  temp;
		}
		
		System.out.println("本次亂數結果: ");
		for(int i = 0; i < 10; i++){
			System.out.printf("%d ", nums[i]);
			sum += nums[i];
		}
		
			
		System.out.printf("平均為: %2.2f", sum / 10.0 );
		
	}

}
