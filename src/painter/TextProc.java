package painter;

import java.util.ArrayList;

import fileStream.txtFileStream;

public class TextProc {
	private String thisLine;
	private txtFileStream stream;
	private String sPath;
	private boolean haveBlockData;
	private int blockDataStartPoint;
	
	public TextProc(String sPath){
		thisLine = "";
		this.sPath = sPath;
		stream = new txtFileStream(sPath);	
		stream.CreateFile();
		stream.writeText("0 0 0");
		haveBlockData = false;
		blockDataStartPoint = -1;
	}
	
	/*private String readGivenLine(int line){
		return stream.readTextGivenLine(line).trim();
	}*/
	
	protected void Open() {
		try {
            Process ps = Runtime.getRuntime().exec("cmd /c start notepad " + sPath);
            ps.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } 
	}
	
	protected void RunPythonSupport(String pythonPath){
		try {
			Process ps = Runtime.getRuntime().exec("python " + pythonPath);
            ps.waitFor();
            System.out.println(pythonPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	protected boolean getBlockDataStatus() {
		return haveBlockData;
	}
	
	protected void readLine(){
		thisLine = stream.readTextLastLine().trim();
	}
	
	/**Notice: 
	 * .txt Files MUST be written in THIS format: 
	 * <p>"p134 n034 p000" -- 
	 * Use "0" to fill, 'n' and 'p' to show Sign<p>
	 * ANYTHING ELSE WILL BE THROWN AS EXCEPTIONS!!!
	 * @param start = 0 6 12 
	 * @param  end  = 5 11 17 */
	@Deprecated
	protected int getPos(int start, int end){
		int iSign = 1;
		int iNum = 0;
		try{
			String sTemp = thisLine.substring(start, end);
			if(sTemp.substring(0,1).equals("p")){
				iSign = 1;
			}else{
				iSign = -1;
			}
			iNum = Integer.parseInt(sTemp.substring(1,5));
		}catch(NumberFormatException e) {
			System.out.print("Error: TextProc.readPos(): ");
			e.printStackTrace();
		}
		return iSign*iNum;
	}
	
	@Deprecated
	protected int getCal(){
		return 0;
	}
	
	
	protected int getPosX(int defaultData) {
		haveBlockData = false;
		blockDataStartPoint = -1;
		//start
		String sTemp = thisLine.trim().replace(" ", "_");
		int marker1 = 0;
		int marker2 = 0;
		int num_out = defaultData;
		try {
			for(int i=0;i<sTemp.length();i++){
				if(sTemp.substring(i, i+1).equals("_")){
					marker2 = i;
					break;
				}
			}
			num_out = (int)Math.round(Double.parseDouble(sTemp.substring(marker1,marker2)));
		} catch (Exception e) {
			e.printStackTrace();//don't handle
		}
		return num_out;
	}
	
	protected int getPosY(int defaultData) {
		String sTemp = thisLine.trim().replace(" ", "_");
		int marker1 = 0;
		int marker2 = 0;
		int num_out = defaultData;
		int i;
		int j;
		try {
			for(i=0;i<sTemp.length();i++){
				if(sTemp.substring(i, i+1).equals("_")){
					marker1 = i+1;
					break;
				}
			}
			for(j=i+1;j<sTemp.length();j++){
				if(sTemp.substring(j, j+1).equals("_")){
					marker2 = j;
					break;
				}
			}
			
			num_out = (int)Math.round(Double.parseDouble(sTemp.substring(marker1,marker2)));
		} catch (Exception e) {
			e.printStackTrace();//don't handle
		}
		return num_out;
	}
	
	protected int getPosR(int defaultData) {
		String sTemp = thisLine.trim().replace(" ", "_");
		int marker1 = 0;
		int marker2 = sTemp.length();
		int num_out = defaultData;
		int i;
		int j;
		try {
			for(i=0;i<sTemp.length();i++){
				if(sTemp.substring(i, i+1).equals("_")){
					break;
				}
			}
			for(j=i+1;j<sTemp.length();j++){
				if(sTemp.substring(j, j+1).equals("_")){
					marker1 = j+1;
					break;
				}
			}
			for(int k=j+1;k<sTemp.length();k++){
				if(k == sTemp.length()-1){
					marker2 = k+1;
					break;
				}
				else if(sTemp.substring(k, k+1).equals("_")){
					marker2 = k;
					haveBlockData = true;
					blockDataStartPoint = k+1;
					break;
				}
			}
			
			num_out = (int)Math.round(Double.parseDouble(sTemp.substring(marker1,marker2)));
		} catch (Exception e) {
			e.printStackTrace();//don't handle
		}
		return num_out;
	}
	/**
	 * @param angle MUST BE RADIAN
	 * */
	public void getblockData(ArrayList<Integer> distanceList, ArrayList<Integer> offsetList,ArrayList<Integer> xObstacleList,ArrayList<Integer> yObstacleList, int pos_x, int pos_y, double angle){
		distanceList.clear();
		offsetList.clear();
		xObstacleList.clear();
		yObstacleList.clear();
		if (haveBlockData) {
			String sTempO = thisLine.trim().replace(" ", "_");
			String sTemp = sTempO.substring(blockDataStartPoint, sTempO.length());
			int putMarker = 0;//even for distance odd for offset
			int startPoint = 0;
			try {
				while (true) {
					for (int i = startPoint; i < sTemp.length(); i++) {
						if (sTemp.substring(i, i + 1).equals("_")) {
							if (putMarker % 2 == 0) {
								distanceList.add(Integer.parseInt(sTemp.substring(startPoint, i)));
							} else {
								offsetList.add(Integer.parseInt(sTemp.substring(startPoint, i)));
							}
							putMarker++;
							startPoint = i + 1;
							break;
						}
						if (i == sTemp.length() - 1) {
							if (putMarker % 2 == 0) {
								distanceList.add(Integer.parseInt(sTemp.substring(startPoint, i + 1)));
							} else {
								offsetList.add(Integer.parseInt(sTemp.substring(startPoint, i + 1)));
							}
							putMarker++;
							startPoint = -1;
							break;
						}
					}
					if (startPoint == -1) {
						break;
					}
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			
			if(distanceList.size()==offsetList.size()&&!distanceList.isEmpty()&&!offsetList.isEmpty()){
				int num = distanceList.size();
				for(int i=0;i<num;i++){
					if (distanceList.get(i)>=120) {
						double x_out = pos_x + distanceList.get(i) * Math.sin(angle)
								+ offsetList.get(i) * Math.cos(angle);
						double y_out = pos_y + distanceList.get(i) * Math.cos(angle)
								- offsetList.get(i) * Math.sin(angle);
						xObstacleList.add((int) x_out);
						yObstacleList.add((int) y_out);
					}
				}
			}
		}else{}
		//print(xObstacleList);
		//print(yObstacleList);
	}
	
	@SuppressWarnings("unused")
	@Deprecated
	private void print(ArrayList<Integer> a){
		for(int i=0;i<a.size();i++){
			System.out.print(a.get(i)+", ");
		}
		System.out.println();
	}
	
}
