package painter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.filechooser.FileSystemView;

import image.ImageProc;

public class Main extends JPanel implements ActionListener {
	//main ID
	private static final long serialVersionUID = 1L;
	//get desktop on windows
	private static FileSystemView fsv = FileSystemView.getFileSystemView();
	protected static File desktopAddress=fsv.getHomeDirectory(); 
	//Config
	public static final double AnimationHZ = 60;
	static String createPath = desktopAddress.getPath()+"/FRC_MiniMap/robotPos.txt";
	static boolean openOnUse = true; //debug only, opening a txt and running python client with a active thread on it may cause issues
	FieldProc fieldProc = new FieldProc();
	static TextProc textProc = new TextProc(createPath);
	//initiate
	java.net.URL courtImgURL = Main.class.getResource("/image/courtPic.png");
	protected Image courtImage = getToolkit().getImage(courtImgURL);
	static InputStream carImageStream;
	//set scheduler
	protected Timer timer = new Timer((int)(1000.0/AnimationHZ), this);
    //set mapping params
	int pos_x=0;
	int pos_y=0;
	int angle=0;
	protected ArrayList<Integer> offsetList = new ArrayList<Integer>();
	protected ArrayList<Integer> distanceList = new ArrayList<Integer>();
	protected ArrayList<Integer> xObstacleList = new ArrayList<Integer>();
	protected ArrayList<Integer> yObstacleList = new ArrayList<Integer>();
	static int scaledCarDia;
	static int scaledBlockDia;
	
	//set drawing params
	final static int diameterCar = 154; //diameter of the car picture (centimeter)
	final static int diameterBlock = 35; //diameter of block on screen
	final int base_x=230;//230;
	final int base_y=34;//34;
		//33 '4 box
	
	public Main(){
        if(courtImage!=null)
        	System.out.println("Mapping_Ready...");
    }
	
	@Override
	public void addNotify()
    {
		// Call the superclass and start the timer
        super.addNotify();
        timer.start();
    }
	
	@Override
    public void removeNotify()
    {
    	// Call the superclass and stop the timer
        timer.stop();
        super.removeNotify();
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		textProc.readLine();
		pos_x = textProc.getPosX(pos_x);
		pos_y = textProc.getPosY(pos_y);
		angle = textProc.getPosR(angle);
		textProc.getblockData(distanceList, offsetList, xObstacleList, yObstacleList, pos_x, pos_y, Math.toRadians(angle));
		repaint();
	}
	
	public void paint(Graphics g){
		super.paint(g);		
		g.drawImage(courtImage, 0, 0, (int)(Constants.fieldWidth/Constants.scalingFactor) ,
				(int)(Constants.fieldLength/Constants.scalingFactor), this);
		//set Origin (Starting Point)
		fieldProc.setBase(base_x, base_y);
		int tempX = fieldProc.convertX(pos_x);
		int tempY = fieldProc.convertY(pos_y);
		carImageStream = Main.class.getClassLoader().getResourceAsStream("image/carPic.png");
		try {
			g.drawImage(ImageProc.rotateImage(ImageIO.read(carImageStream), angle), tempX-scaledCarDia/2, tempY-scaledCarDia/2, scaledCarDia, scaledCarDia, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!xObstacleList.isEmpty()&&!yObstacleList.isEmpty()){
			for(int i=0;i<xObstacleList.size();i++){
				int obsX = fieldProc.convertX(xObstacleList.get(i));
				int obsY = fieldProc.convertY(yObstacleList.get(i));
				g.setColor(Color.yellow);
				g.fillRect(obsX-scaledBlockDia/2, obsY-scaledBlockDia/2, scaledBlockDia, scaledBlockDia);
			}
		}
		
		//paint
		/*
		double angle_rad = fieldProc.convertR(angle)/180.0*Math.PI;
		double deltaX = Math.sin(angle_rad)*pointerLength;
		double deltaY = Math.cos(angle_rad)*pointerLength;
		g.setColor(Color.WHITE);
		int[] X = {(int)(tempX - deltaY/pointerLength*5),(int)(tempX + deltaY/pointerLength*5),(int)(tempX+deltaX)};
		int[] Y = {(int)(tempY + deltaX/pointerLength*5),(int)(tempY - deltaX/pointerLength*5),(int)(tempY+deltaY)};
		g.fillPolygon(X, Y, 3);//arrow
		int Robot_L = (int)(71/Constants.scalingFactor);
		int Robot_W = (int)(83/Constants.scalingFactor);
		g.setColor(Color.BLACK);
		int[] XR = {-Robot_W/2,Robot_W/2,Robot_W/2,-Robot_W/2};
		int[] YR = {Robot_L/2,Robot_L/2,-Robot_L/2,-Robot_L/2};
		int[] XR2 = {0,0,0,0};
		int[] YR2 = {0,0,0,0};
		for (int i = 0;i < 4;i++){
			XR2[i] = (int) (XR[i] * Math.cos(-angle_rad) - YR[i] * Math.sin(-angle_rad));
			YR2[i] = (int) (XR[i] * Math.sin(-angle_rad) + YR[i] * Math.cos(-angle_rad));
		}
		for (int i = 0;i < 4;i++){
			XR2[i] += tempX;
			YR2[i] += tempY;
		}
		g.fillPolygon(XR2, YR2, 4);//chassis
		*/
	}
	
	public static void main(String []args){
		Constants.useDResolution();
		scaledCarDia = (int)(diameterCar/Constants.scalingFactor);
		scaledBlockDia = (int)(diameterBlock/Constants.scalingFactor);
		if(openOnUse){
			textProc.Open();
		}
		
		JFrame courtMap = new JFrame();
		courtMap.setVisible(true);
		courtMap.setSize((int)(Constants.fieldWidth/Constants.scalingFactor)+16, 
				(int)(Constants.fieldLength/Constants.scalingFactor)+39);
		courtMap.setLocation(20, 20);
		courtMap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		courtMap.getContentPane().add(new Main());
	}
}
