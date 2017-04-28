package homework7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

//請寫一個程式讀取這個Sample.txt檔案，並輸出以下訊息：
//Sample.txt檔案共有xxx個位元組，yyy個字元，zzz列資料

public class SampleReader {

	public static void main(String[] args) {

		long byteCount = 0;
		long charCount = 0;
		long lineCount = 0;

		String path = "E:\\sample.txt";

		File file = new File(path);
		if (file.exists() && file.isFile()) {

			// use FileInputStream read() method (read single byte each time)
			try (FileInputStream is = new FileInputStream(path)) {
				int end;
				while ((end = is.read()) != -1) {
					byteCount++;
				}

			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Read bytes error");
			}

			// use FileReader read() method (read single char each time)

			try (FileReader fs = new FileReader(path)) {
				int end;
				while ((end = fs.read()) != -1) {
					charCount++;
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Read chars error");
			}

			// use BufferedReader readLine() method
			try (FileInputStream fis = new FileInputStream(path);
					BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
				String endOfLine = null;
				while ((endOfLine = br.readLine()) != null) {
					lineCount++;
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Read lines error");
			}

			System.out.printf("Sample.txt檔案共有 %d個位元組，%d個字元，%d列資料", byteCount, charCount, lineCount);
		} else {
			System.out.println("目標路徑不是檔案或不存在");
		}
	}

}