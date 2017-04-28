package homework7;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

//承上題，請寫一個程式，能讀出Object.dat這四個物件，並執行speak()方法觀察結果如何 (請利用多型簡化本題的程式設計)

public class ReadObjData {
	public static void main(String[] args) {
		String path = "C:\\data\\Object.dat";
		String path2 = "C:\\data\\Object2.dat";

		ObjectInputStream ois = null;
		File file = new File(path);

		// 方法一: 利用Header作為檔案結尾 使用Animal class 多型呼叫speak()
		if (file.exists() && file.isFile()) {
			try {
				ois = new ObjectInputStream(new FileInputStream(file));
				while (ois.readBoolean()) { // workaround Header false
					Animals animals = (Animals) ois.readObject();
					animals.speak();
				}
				System.out.println("檔案讀取結束");
			} catch (Exception e) {
				System.out.println("檔案讀取錯誤");
				e.printStackTrace();
			} finally {
				try {
					ois.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		} else {
			System.out.println("目標路徑不是檔案或不存在");
		}

		System.out.println("=============================");

		file = new File(path2);

		// 方法二: 正常讀取處理Exception 使用ISpeak Interface 多型呼叫 speak2()
		if (file.exists() && file.isFile()) {
			try (ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file))) {

				while (true) {
					((ISpeak) oin.readObject()).speak2();
				}

			} catch (EOFException efe) {
				System.out.println("檔案讀取結束");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("目標路徑不是檔案或不存在");
		}

	}

}
