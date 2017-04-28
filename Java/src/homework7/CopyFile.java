package homework7;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

//請設計一個方法名為copyFile，這個方法有兩個參數。呼叫此方法時，第一個參數所代表的檔案會複製到第二個參數代表的檔案

public class CopyFile {
	public static void main(String[] args) {

		String from = "E:\\sample.txt";
		String to = "E:\\target.txt";

		try {
			copyFile(from, to);
			copyFile2(from, to);

		} catch (IOException ie) {
			System.out.println("複製過程錯誤");
			ie.printStackTrace();
		}

	}

	// 方法一: 用inputStream實作 BufferedInputStream 和 BufferedOutputStream 增加讀大檔速度
	public static void copyFile(String from, String to) throws IOException {
		File fileFrom = new File(from);
		File fileTo = new File(to);

		if (fileFrom.isFile()) {
			int i;
			try( BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileFrom));
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileTo))){
				while ((i = bis.read()) != -1) {
					bos.write(i);
				}
				bos.flush();
			} catch (IOException e){
				e.printStackTrace();
			}
			
			System.out.println("複製完畢");
			
		}

	}

	// 方法二: File copy 方法
	public static void copyFile2(String from, String to) throws IOException {

		Path source = Paths.get(from);
		Path destination = Paths.get(to);
		System.out.println(source);
		System.out.println(to);

		Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
		System.out.println("複製完畢");

		// CopyOption[] options = new CopyOption[]{
		// StandardCopyOption.REPLACE_EXISTING,
		// StandardCopyOption.COPY_ATTRIBUTES
		// };
		// Files.copy(source, destination, options);
	}
}
