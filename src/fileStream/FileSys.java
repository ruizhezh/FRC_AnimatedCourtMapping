package fileStream;

import java.io.File;

public class FileSys {
	protected String sPath;// = new String("Libraries/Documents/JavaFileStream/Sample.txt");
	protected File file;// = new File();
	
	enum FileStatus{
		File_Exists,
		File_Missing,
		File_Created,
		Command_Successful,
		Command_Failed,
		File_Deleted
	};
	
	public FileSys(String sPath){
		this.sPath = sPath;
		file = new File(sPath);
	}
	
	
	public boolean hasFile(){
		return file.exists();		
	}
	
	public FileStatus CreateFile(){
		File tempFileParent = file.getParentFile();
		boolean tempMake = true;
		if(!tempFileParent.exists()){
			try {
				tempMake = tempFileParent.mkdirs();
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		if(tempMake){
			if(file.exists()){
				return FileStatus.File_Exists;		
			}else{
				boolean btemp = true;
				try {
					file.createNewFile();
				} catch (Exception e) {
					btemp = false;
					e.printStackTrace();
				}
				if(btemp){
					return FileStatus.File_Created;
				}else{
					return FileStatus.Command_Failed;					
				}
			}
		}else{
			return FileStatus.Command_Failed;
		}
	}
	
	public FileStatus DeleteFile(){
		FileStatus FS_Temp = FileStatus.File_Deleted;
		try{
			if(!file.exists()){
				FS_Temp = FileStatus.File_Missing;
			}else{
				file.delete();
			}
		}catch(Exception e){
			FS_Temp = FileStatus.Command_Failed;
		}
		return FS_Temp;
	}
	
	//TODO
	
	public String getFilePath(){
		return sPath;		
	}
	
	public File getFile(){
		return file;
	}
}
