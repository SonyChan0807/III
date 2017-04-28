package homework7;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//請寫一個程式，可以在讀入一個檔案後，顯示有多少個位元組

public class FileSize {
	public static void main(String[] args) {

		String path = "E:\\sample.txt";
		
		// 方法一: 用FileInputStream實作
		try (FileInputStream fis = new FileInputStream(path)) {

			byte[] data = new byte[1];
			int length;
			int count = 0;
			while ((length = fis.read(data)) != -1) {
				count++;
			}

			System.out.printf("%s 檔案共 %d位元 %n", path, count);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("=======================");

		// 方法二: 用FileInputStream API

		try(FileInputStream fis = new FileInputStream(path)){
			System.out.printf("%s 檔案共 %d位元%n", path, fis.available());
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		System.out.println("=======================");

		// 方法三: 用File API

		File file = new File(path);
		System.out.printf("%s 檔案共 %d位元%n", path, file.length());

	}

}
