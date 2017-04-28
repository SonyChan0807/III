package homework8;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//印出這個物件裡的所有元素(使用Iterator, 傳統for與增強for)
//移除不是java.lang.Number家族的物件
//再次印出這個集合物件的所有元素，觀察是否已將非Number家族的物件移除成功

public class NewCollectionTest {
	public static void main(String[] args) {
		List<Object> list = new ArrayList<>();

		list.add(new Integer(100));
		list.add(new Double(3.14));
		list.add(new Long(21L));
		list.add(new Short("100"));
		list.add("Kitty");
		list.add("Snoopy2");
		list.add("Snoopy3");
		list.add(new Integer(100));
		list.add("Snoopy4");
		list.add(new BigInteger("1000"));

		PrintCollection pc = new PrintCollection(list);
		pc.printByIterator();
		pc.printByEnhanceForLoop();
		pc.printByForLoop();

		
		// Remove the non-number element 
		System.out.println();
		for (int i = 0; i < list.size(); i++){
			if(!(list.get(i) instanceof Number) ){
				list.remove(i);
				i--;
			} 
		}
		
		pc.printNumber();
	}
}

class PrintCollection {
	private List<Object> list;

	public PrintCollection(List<Object> list) {
		this.list = list;
	}

	void printByIterator() {

		System.out.println("--------PrintByIterator-------");
		Iterator<Object> it = list.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());

		}

	}

	void printByEnhanceForLoop() {

		System.out.println("--------PrintByEnhanceForLoop-------");
		for (Object o : list) {
			System.out.println(o);

		}

	}

	void printByForLoop() {
		System.out.println("--------PrintByForLoop-------");

		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));

		}

	}
	
	void printNumber() {

		System.out.println("--------PrintNumber-------");
		for (Object o : list) {
			System.out.println(o);

		}
	}


}
