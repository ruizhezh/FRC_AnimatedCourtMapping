package painter;

public class Constants {
	public static final int fieldWidth = 823;//hengxiang
	public static final int fieldLength = 1646;//zongxiang
	public static double scalingFactor;//= 2.5;
	
	public static void useDResolution(){
		int height=java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		double fieldLengthDouble = fieldLength;
		if(height>fieldLengthDouble){
			scalingFactor = 1;
		}else if(height>768){
			scalingFactor = fieldLengthDouble/height+0.2;
		}else{
			scalingFactor = 2.5;
		}
	}
	
	
}
