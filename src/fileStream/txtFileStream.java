package fileStream;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class txtFileStream extends FileSys {
	//private String last_Content;
	
	public txtFileStream(String sPath) {
		super(sPath);
	}
	
	/**reader
	 * WILL NOT RETURN THE LAST LINE IF IT IS EMPTY*/
	public String readText(){
		if(file.canRead()){
			Scanner scanner = null;
			String tempOut = "";
			try {
				scanner = new Scanner(new FileReader(file));
				while(scanner.hasNextLine()){
					tempOut = tempOut+scanner.nextLine();
					if(scanner.hasNextLine()){
						tempOut = tempOut+"\n";
					}
					//System.out.println(tempOut);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				if(scanner!=null){
					scanner.close();
				}
			}
			return tempOut;
		}else{
			return FileStatus.File_Missing.toString();
		}
	}
	/**read the last line of the .txt file
	 * WILL NOT RETURN THE LAST LINE IF IT IS EMPTY*/
	public String readTextLastLine(){
		if(file.canRead()){
			Scanner scanner = null;
			String tempOut = "";
			try {
				scanner = new Scanner(new FileReader(file));
				while(scanner.hasNextLine()){
					tempOut = scanner.nextLine();
					//System.out.println(scanner.hasNextLine()+tempOut);
					if(!scanner.hasNextLine()){
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				if(scanner!=null){
					scanner.close();
				}
			}
			return tempOut;
		}else{
			return FileStatus.File_Missing.toString();
		}
	}
	/**read the given line of the .txt file
	 * WILL NOT RETURN THE LAST LINE IF IT IS EMPTY
	 * @param LineNum START FROM 1*/
	public String readTextGivenLine(int LineNum){
		int i = 0;
		if(file.canRead()&&LineNum!=0){//patched for nullLine exception
			Scanner scanner = null;
			String tempOut = "";
			try {
				scanner = new Scanner(new FileReader(file));
				while(scanner.hasNextLine()){
					tempOut = scanner.nextLine();
					i++;
					//System.out.println(scanner.hasNextLine()+tempOut);
					if(!scanner.hasNextLine()||i==LineNum){
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				if(scanner!=null){
					scanner.close();
				}
			}
			return tempOut;
		}else if(LineNum!=0){
			return FileStatus.File_Missing.toString();
		}else{
			return "";
			//return "LineNum CANNOT be ZERO";
		}
	}
		
	/**write*/
	public FileStatus writeText(String content){
		if(file.canRead()){
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(file);
				fileOutputStream.write(content.getBytes("GBK"));				
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				if(fileOutputStream!=null){
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return FileStatus.Command_Successful;
		}else{
			return FileStatus.File_Missing;
		}
	}
	/**add*/
	public FileStatus addText(String content, boolean nextLine){
		String currentContent = "";		
		if(file.canRead()){
			Scanner scanner = null;
			try {
				scanner = new Scanner(new FileReader(file));
				while(scanner.hasNextLine()){
					currentContent = currentContent+scanner.nextLine();
					if(scanner.hasNextLine()){
						currentContent = currentContent+"\n";
					}
					//System.out.println(tempOut);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				if(scanner!=null){
					scanner.close();
				}
			}
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(file);
				if(nextLine){
					fileOutputStream.write((currentContent+"\n"+content).getBytes("GBK"));
				}else{
					fileOutputStream.write((currentContent+content).getBytes("GBK"));
				}								
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				if(fileOutputStream!=null){
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return FileStatus.Command_Successful;
		}else{
			return FileStatus.File_Missing;
		}
	}
	
	
	
}
