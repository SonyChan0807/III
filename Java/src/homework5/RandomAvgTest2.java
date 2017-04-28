package homework5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


//請設計一個方法為randAvg()，從10個 0～100(含100)的整數亂數中取平均值並印出這10個亂數與平均值  
// Lambda

public class RandomAvgTest2 {
	
	public static void main(String[] args) {
		double avg = 0;
		List<Integer> nums = new ArrayList<>();
		
		for(int i = 0; i < 100; i++){
			nums.add(i + 1);
		}
		
		Collections.shuffle(nums);
		
		System.out.println("本次亂數結果: ");
		
		nums.stream()
			.filter(num -> nums.indexOf(num) < 10)
			.collect(Collectors.toList())
			.forEach((x) -> System.out.printf("%d ", x));
		
		
		avg = nums.stream()
				.filter(num -> nums.indexOf(num) < 10)
				.mapToInt(num -> num)
				.average().getAsDouble();
		
		System.out.printf("平均為: %2.2f", avg);
		
	}

}
