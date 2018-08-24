package painter;

public class FieldProc {
	
	private int length = Constants.fieldLength; //centimeters
	private int width = Constants.fieldWidth; //centimeters
	private double scale = Constants.scalingFactor;
	private int baseX = 0;
	private int baseY = 0;
	
	public FieldProc(){}
	
	public void setBase(int baseX, int baseY){
		//shiyongpingmianzhijiaozuobiaoxizouxiaweilingdian
		this.baseX = baseX;
		this.baseY = baseY;	
	}
	
	public int convertX(int currentX){
		int convertedX;
		convertedX = (int)((currentX + baseX)/scale);
		
		if(convertedX>=width/scale){
			convertedX = (int)(width/scale);
		}else if(convertedX<=0){
			convertedX = 0;
		}else{}
		
		return convertedX;
	}
	
	public int convertY(int currentY){
		int convertedY;
		convertedY = (int)((length/scale)-(currentY+baseY)/scale);
		
		if(convertedY>=length/scale){
			convertedY = (int)(length/scale);
		}else if(convertedY<=0){
			convertedY = 0;
		}else{}
		
		return convertedY;
	}
	
	public int convertR(int currentR){
		return 180-currentR;
	}
}
