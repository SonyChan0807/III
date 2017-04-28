import java.io.File;

public class FileSeeker {
	public static void main(String[] args) {
		
		String path = "e:\\root";		
		seek(path);
			
	}
	
	
	public static void seek(String filePath){
		File file = new File(filePath);
		File[] currentFile = file.listFiles();
		for(File f : currentFile){
			if(f.isDirectory()){
				seek(f.getAbsolutePath());
			}else{
				System.out.println(f.getAbsolutePath());
			}
		}
	}
}
