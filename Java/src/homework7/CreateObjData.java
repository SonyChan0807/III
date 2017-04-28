package homework7;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

//請寫一支程式，利用老師提供的Dog與Cat類別分別產生兩個物件，寫到C:\data\Object.dat裡。注意物件寫入需注意的事項，
//若C:\內沒有data資料夾，請用程式新增這個資料夾



public class CreateObjData {
	public static void main(String[] args){
	
		
		String path = "C:\\data\\";
		ObjectOutputStream oos = null;
		
		File file = new File(path);
		if (!file.exists()){
			file.mkdirs();
		}
		
		String fileName = "Object.dat";
		File newFile = new File(path+fileName);
		
		// 方法一: 使用Boolean header false 代表最後結束  true 代表有資料
		try{
			oos = new ObjectOutputStream (new FileOutputStream(newFile));
			oos.writeBoolean(true);
			oos.writeObject(new Dog("John"));
			oos.writeBoolean(true);
			oos.writeObject(new Dog("Kelly"));
			oos.writeBoolean(true);
			oos.writeObject(new Cat("ket"));
			oos.writeBoolean(true);
			oos.writeObject(new Cat("Jay"));
			oos.writeBoolean(false);
			oos.close();
		} catch(IOException e){
			e.printStackTrace();
		}
		
		
		// 方法二: 正常寫入
		String fileName2 = "object2.dat";
		newFile = new File(path+fileName2);
		
		try{
			oos = new ObjectOutputStream (new FileOutputStream(newFile));
			oos.writeObject(new Dog("John"));
			oos.writeObject(new Dog("Kelly"));
			oos.writeObject(new Cat("ket"));
			oos.writeObject(new Cat("Jay"));
			oos.close();	
		} catch (IOException e){
			e.printStackTrace();
		}
		
		
	}
}
