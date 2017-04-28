package homework7;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

// 請寫一隻程式，能夠亂數產生10個1～1000的整數，並寫入一個名為Data.txt的檔案裡

public class RandomWriteNum {

	public static void main(String[] args) throws IOException {
		String path = "E:\\Data.txt";

		File file = new File(path);

		try (FileOutputStream fos = new FileOutputStream(file, true);
				Writer w = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"))) {
			Set<Integer> set = new HashSet<>();
			while(set.size() <= 10){
				set.add((int)(Math.random()*1000) + 1);
			}
			
			for(int i : set){
				w.write(String.valueOf(i) + " ");
			}
			
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

}
