package idealistic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
 
public class WriteFile {
	private File file;
	private String fileName = "/home/josu/Desktop/file";
	private FileOutputStream fop;
	
	WriteFile() {
		file = new File(fileName);
		
		// if file doesn't exists, then create it
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		
		try {
			fop = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	void write(String distance, String size, String rooms, String price) {
		String content = distance + "," +
				size + "," +
				rooms + "," +
				price + "\n";
		

			byte[] contentInBytes = content.getBytes();
 
			try {
				fop.write(contentInBytes);
				fop.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
	
	void closeFile() {
		try {
			fop.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
 
		File file = new File("/home/josu/Desktop/file");
		String content = "This is the text content";
 
		try (FileOutputStream fop = new FileOutputStream(file)) {
 
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			// get the content in bytes
			byte[] contentInBytes = content.getBytes();
 
			fop.write(contentInBytes);
			fop.flush();
			
			fop.write("supupi".getBytes());
			fop.flush();
			
			fop.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
