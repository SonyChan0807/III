package homework8;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class TrainTest {
	
	static Set<Train> trainSet = new TreeSet<>();
	
	public static void main(String[] args){
		
		trainSet.add(new Train(202, "普悠瑪", "樹林", "花蓮", 400));
		trainSet.add(new Train(1254, "區間", "屏東", "基隆", 700));
		trainSet.add(new Train(118, "自強", "高雄", "台北", 500));
		trainSet.add(new Train(1288, "區間", "新竹", "基隆", 400));
		trainSet.add(new Train(122, "自強", "台中", "花蓮", 600));
		trainSet.add(new Train(1222, "區間", "樹林", "七堵", 300));
		trainSet.add(new Train(1254, "區間", "屏東", "基隆", 700));
		
		System.out.println("------ Use Iterator ------");
		
		Iterator<Train> it = trainSet.iterator();
		while(it.hasNext()){
			Train train = (Train)(it.next());
			System.out.println("車次: " + train.getNumber()
								+ " 車種: " + train.getType()
								+ " 出發地: " + train.getStart()
								+ " 目的地: " + train.getDest()
								+ " 票價: " + train.getPrice());
		}
		
		
//		Customized Sort - List
//		Collections.sort(trainSet, new Comparator<Train>(){
//			@Override
//			public int compare(Train o1, Train o2) {
//				return o1.getNumber() - o2.getNumber();
//			}
//		});
		
//		Lambda expression
//		Collections.sort(trainSet, (Train t1, Train t2) -> t1.getNumber() - t2.getNumber());

		// Use Iterator in for Loop
		for(Iterator<Train> it1 = trainSet.iterator(); it1.hasNext();){
			
			Train train = (Train) it1.next();
			System.out.println("車次: " + train.getNumber()
			+ " 車種: " +  train.getType()
			+ " 出發地: " +  train.getStart()
			+ " 目的地: " +  train.getDest()
			+ " 票價: " +  train.getPrice());
		}
		
		
		System.out.println("------ Add new train ------");
		
		addTrain(116, "自強", "高雄", "台北", 500);
		
		// Other Iteration Method - ForEach
		
		for(Train train: trainSet){
			System.out.println("車次: " + train.getNumber()
			+ " 車種: " + train.getType()
			+ " 出發地: " + train.getStart()
			+ " 目的地: " + train.getDest()
			+ " 票價: " + train.getPrice());
		}
		
	}
	
	// 建立一個方法傳入所需要的資訊並用add(E, index) 方法加到list最後
	public static void addTrain(int number, String type, String start, String dest, double price){
		trainSet.add(new Train(number, type, start, dest, price));
	}
}



