package dsa.project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class Game2048 extends JPanel implements Runnable{

	private static final long serialVersionUID = 10L;
	int[][] matrix;
	ArrayList<Integer> List1,List2,List3,ListUndo;
	private Random random = new Random();
	private boolean running;
	private Thread game;
	int row = 0;
	int col = 0;
	int score = 0;
	int highScore = 0;
	int scoreUndo = 0;
	int best = 0;
	int countMove = 0;
	int countUndo = 0;
	Image no2,no4,no8,no16,no32,no64,no128,no256,no512,no1024,no2048,logo,gameover,logo1;
	String highscore_file = "highscore.txt";
	
public Game2048 (){
	logo  = Toolkit.getDefaultToolkit().createImage("logo.jpg"); 
	gameover  = Toolkit.getDefaultToolkit().createImage("gameover.png"); 
	
	no2 = Toolkit.getDefaultToolkit().createImage("no2.jpg"); 
	no4  = Toolkit.getDefaultToolkit().createImage("no4.jpg"); 
	no8  = Toolkit.getDefaultToolkit().createImage("no8.jpg"); 
	no16  = Toolkit.getDefaultToolkit().createImage("no16.jpg"); 
	no32  = Toolkit.getDefaultToolkit().createImage("no32.jpg"); 
	no64  = Toolkit.getDefaultToolkit().createImage("no64.jpg"); 
	no128  = Toolkit.getDefaultToolkit().createImage("no128.jpg"); 
	no256  = Toolkit.getDefaultToolkit().createImage("no256.jpg"); 
	no512  = Toolkit.getDefaultToolkit().createImage("no512.jpg"); 
	no1024  = Toolkit.getDefaultToolkit().createImage("no1024.jpg"); 
	no2048  = Toolkit.getDefaultToolkit().createImage("no2048.jpg"); 

	matrix = new int[4][4];
	initialMatrix();
	getBest();

	KeyListener listener = new KeyListener(){
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				moveUp();
				saveBest();
				break;
			case KeyEvent.VK_DOWN:
				moveDown();
				saveBest();
				break;
			case KeyEvent.VK_LEFT:
				moveLeft();
				saveBest();
				break;
			case KeyEvent.VK_RIGHT:
				moveRight();
				saveBest();
				break;
			case KeyEvent.VK_ESCAPE:
				saveBest();
				newGame();
				getBest();
				break;	
			case KeyEvent.VK_SPACE:
				saveBest();
				stop();
				break;	
			case KeyEvent.VK_DELETE:
				best = 0;
				break;
			case KeyEvent.VK_CONTROL:
				Undo();	
				break;
		} 
	}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
		}
	};
	setFocusable(true);		// allows Game2048 to receive the focus.
	addKeyListener(listener);
}

@Override
public void run() {
	while (running){
		repaint();
		try { 
			Thread.sleep(10);
		} catch (InterruptedException e){
			e.printStackTrace();
		}
	}
}  	

public void start(){
	if(running)
	{
		return;
	}
	else
	{
		running = true;
		game = new Thread(this,"game");
		game.start();
	}
}

public void stop(){
	if(!running)
	{
		return;
	}
	else
	{
		running = false;
		System.exit(1);
	}
}

public void newGame(){
	score = 0;
	countUndo = 0;
	for(col= 0; col <4;col++)
	{
		for(row=0; row<4; row++)
		{
			matrix[col][row] = 0;
		}
	}
	initialMatrix();
}

public void initialMatrix(){
	int rCol1 = random.nextInt(4);
	int rRow1 = random.nextInt(4);
	int rCol2 = random.nextInt(4-rCol1);
	int rRow2 = random.nextInt(4-rRow1);
	for (int row =0; row <4; row++)
	{
		for(int col = 0; col <4; col++)
		{
			matrix[rCol1][rRow1] = 2;
			matrix[rCol2][rRow2] = (random.nextInt(2)+1)*2;
		}
	}
}	

public void addRandomNumber(){
	int numberOfZeroSpace = 0;
	for (int row = 0; row < 4; row++) 
	{
		for (int col = 0; col < 4; col++) 
		{
			if (matrix[col][row] == 0)
			{
				numberOfZeroSpace ++;
			}
		}
	}
	
	if(numberOfZeroSpace != 0)
	{
		int addedPosition = random.nextInt(numberOfZeroSpace);
		int countPosition = 0 ;
		for (int row = 0 ; row < 4; row++) 
		{
	        for (int col = 0; col < 4; col ++)
	        {
	        	if (matrix[col][row] == 0)
	        	{
	        		countPosition++;
	        	}
	        	if (countPosition == addedPosition + 1) 
	        	{
	        		matrix[col][row] =(random.nextInt(2)+1)*2;
	        		return;
	        	}
	        }
		}
	}
}

public void Undo(){
	
	List1 = new ArrayList<Integer>();
	List2 = new ArrayList<Integer>();
	int countChange = 0;
	
	for(col= 0; col <4;col++)
	{
		for(row=0; row<4; row++)
		{
			List1.add(matrix[col][row]);
		}
	}
	
	if(countUndo < 3 && score > 100)
	{
		for (int row = 0 ; row < 4; row++) 
		{
			for (int col = 0; col < 4; col ++)
			{
				matrix[col][row] = ListUndo.get(col*4+row);
			}
		}
		score = scoreUndo;
	
	}	

	for(col= 0; col <4;col++)
	{
		for(row=0; row<4; row++)
		{
			List2.add(matrix[col][row]);
		}
	}

	for (int i = 0; i < List1.size(); i++)
	{   
		if(List1.get(i).equals(List2.get(i)))
		{
			countChange = countChange +1;
		}
	}
	
	if(countChange == List1.size()){countUndo = countUndo + 0;countChange=0;}
	else
	{
		countUndo = countUndo + 1;
	}

}

public void pushUp(){
	for (int col =0; col <4; col++)
	{
		for (int loop = 0; loop <3; loop++)
		{
  			for(int row = 3; row > 0; row--)
			{
  				if(matrix[col][row-1] == 0)
				{
					matrix[col][row-1] = matrix[col][row];
					matrix[col][row] = 0;
   				}
			}
		}
	}
}

public void moveUp(){
	List1 = new ArrayList<Integer>();
	List2 = new ArrayList<Integer>();
	
	for(col= 0; col <4;col++)
	{
		for(row=0; row<4; row++)
		{
			List1.add(matrix[col][row]);
		}
	}
	
	pushUp();
	
	for (int col =0; col <4;col++)
	{
		for(int row = 0; row <3 ; row++)
		{
			if(matrix[col][row] == matrix[col][row+1])
			{
				matrix[col][row] = matrix[col][row+1] + matrix[col][row];
				matrix[col][row+1] = 0;
				score = score + matrix[col][row];	
			}
		}		
	}		
	
	pushUp();
	
	for(col= 0; col <4;col++)
	{
		for(row=0; row<4; row++)
		{
			List2.add(matrix[col][row]);
		}
	}

	for (int i = 0; i < List1.size(); i++)
	{   
		if(List1.get(i).equals(List2.get(i)))
		{
			countMove = countMove +1;
		}
	}
	
	if(countMove == List1.size()){countMove = 0;}
	else
	{
		addRandomNumber();
		countMove = 0;
		ListUndo = List1;
		scoreUndo = score - 100;
	}
}	

public void pushDown(){
	for (int col =0; col <4; col++)
	{
		for (int loop = 0; loop <3; loop++)
		{
			for(int row = 0; row < 3; row++)
			{
				if(matrix[col][row+1] == 0)
				{
					matrix[col][row+1] = matrix[col][row];
					matrix[col][row] = 0;
				}
			}	
		}
	}
}

public void moveDown(){
	List1 = new ArrayList<Integer>();
	List2 = new ArrayList<Integer>();
	
	for(col= 0; col <4;col++)
	{
		for(row=0; row<4; row++)
		{
			List1.add(matrix[col][row]);
		}
	}
	
	pushDown();
	
	for (int col2 =0; col2 <4; col2 ++)
	{
		for(int row2 = 3; row2 > 0 ; row2--)
		{
			if(matrix[col2][row2] == matrix[col2][row2-1])
			{
				matrix[col2][row2] = matrix[col2][row2-1] + matrix[col2][row2];
				matrix[col2][row2-1] = 0;
				score = score +  matrix[col2][row2];
			}
		}	 
	}	
	
	pushDown();
	
	for(col= 0; col <4;col++)
	{
		for(row=0; row<4; row++)
		{
			List2.add(matrix[col][row]);
		}
	}

	for (int i = 0; i < List1.size(); i++)
	{   
		if(List1.get(i).equals(List2.get(i)))
		{
			countMove = countMove +1;
		}
	}

	if(countMove == List1.size()){countMove = 0;}
	else
	{
		addRandomNumber();
		countMove = 0;
		ListUndo = List1;
		scoreUndo = score - 100;
	}
}

public void pushRight(){
	for (int loop = 0; loop <2; loop++)
	{
		for (int row1 =0; row1 <4; row1++)
		{
			for(int col1 = 0; col1 < 3; col1++)
			{
				if(matrix[col1+1][row1] == 0)
				{
					matrix[col1+1][row1] = matrix[col1][row1];
					matrix[col1][row1] = 0;
				}
			}
		}
	}		
}

public void moveRight(){
	List1 = new ArrayList<Integer>();
	List2 = new ArrayList<Integer>();
	
	for(col= 0; col <4;col++)
	{
		for(row=0; row<4; row++)
		{
			List1.add(matrix[col][row]);
		}
	}
	
	pushRight();
	
	for (int row2 =0; row2 <4; row2 ++)
	{
		for(int col2 = 3; col2 > 0 ; col2--)
		{
			if(matrix[col2][row2] == matrix[col2-1][row2])
			{
				matrix[col2][row2] = matrix[col2-1][row2] + matrix[col2][row2];
				matrix[col2-1][row2] = 0;
				score = score + matrix[col2][row2];
			}
		}	 
	}
	
	pushRight();
	
	for(col= 0; col <4;col++)
	{
		for(row=0; row<4; row++)
		{
			List2.add(matrix[col][row]);
		}
	}

	for (int i = 0; i < List1.size(); i++)
	{   
		if(List1.get(i).equals(List2.get(i)))
		{
			countMove = countMove +1;
		}
	}

	if(countMove == List1.size()){countMove = 0;}
	else
	{
		addRandomNumber();
		countMove = 0;
		ListUndo = List1;
		scoreUndo = score - 100;
	}
}	 

public void pushLeft(){
	for (int loop = 0; loop <2; loop++)
	{
		for (int row1 =0; row1 <4; row1++)
		{
			for(int col1 = 3; col1 > 0; col1--)
			{
				if(matrix[col1-1][row1] == 0)
				{
					matrix[col1-1][row1] = matrix[col1][row1];
					matrix[col1][row1] = 0;
				}
			}
		}
	}
}

public void moveLeft(){
	List1 = new ArrayList<Integer>();
	List2 = new ArrayList<Integer>();
	
	for(col= 0; col <4;col++)
	{
		for(row=0; row<4; row++)
		{
			List1.add(matrix[col][row]);
		}
	}
	
	pushLeft();
	
	for (int row2 =0; row2 <4;row2++)
	{
		for(int col2 = 0; col2 <3 ; col2++)
		{
			if(matrix[col2][row2] == matrix[col2+1][row2])
			{
				matrix[col2][row2] = matrix[col2+1][row2] + matrix[col2][row2];
				matrix[col2+1][row2] = 0;
				score = score + matrix[col2][row2];
			}

		}	 
	}
	
	pushLeft();

	for(col= 0; col <4;col++)
	{
		for(row=0; row<4; row++)
		{
			List2.add(matrix[col][row]);
		}
	}

	for (int i = 0; i < List1.size(); i++)
	{   
		if(List1.get(i).equals(List2.get(i)))
		{
			countMove = countMove +1;
		}
	}

	if(countMove == List1.size()){countMove = 0;}
	else
	{
		addRandomNumber();
		countMove = 0;
		ListUndo = List1;
		scoreUndo = score - 100;
	}
}

public boolean fullCol(){
	int count1 = 0;
	boolean check1 = false;
	for(col= 0; col <3;col++)
	{
		for(row=0; row<4; row++)
		{
			if(matrix[col][row] == 0 || matrix[col][row] == matrix[col+1][row] )
			{
				count1 = count1 +1;
			}
			else
			{
				continue;
			}
			
		}
	}
	if(count1 == 0)
	{
		check1 = true;
	}
	else
	{
		check1 = false;
	}
	return check1;
}

public boolean fullRow(){
	int count2 = 0;
	boolean check2 = false;
	for(col= 0; col <4;col++)
	{
		for(row=0; row<3; row++)
		{
			if(matrix[col][row] == 0 || matrix[col][row] ==matrix[col][row+1] )
			{
				count2 = count2 +1;
			}
			else
			{
				continue;
			}
		}
	}
	if(count2 == 0)
	{
		check2 = true;
	}
	else
	{
		check2 = false;
	}
	return check2;
}

public boolean fullSpecial()
{
	boolean check3 = false;
	for(col= 0; col <4;col++)
	{
		for(row=0; row<4; row++)
		{
			if(matrix[3][3] == 0 || matrix[3][3] == matrix[3][2]|| matrix[3][3] == matrix[2][3] )
			{
				check3 = false;
			}
			else
			{
				check3 = true;
			}
		}
	}
	return check3;
}
public boolean gameOver(){
	if(fullCol() && fullRow() && fullSpecial() )
	{
		return true;
	}
	else
	{
		return false;
	}
}

public void saveBest(){
	List3 = new ArrayList<Integer>();
	if(score > best)
	{
		highScore = score;
		best = score;
	}
	else
	{
		highScore = best;
	}
    List3.add(highScore);
    Game2048.WriteToFile(List3,highscore_file);     
} 

public int getBest(){
	List3 = Game2048.ReadFromFile(highscore_file);
	for (int i = 0; i < List3.size(); i++)
	{   
		if(List3.get(i) > best)
		{
			best = List3.get(i);
		}
	}
	return best;
}

public static <ListScore> void WriteToFile(ArrayList<ListScore> listscore, String url){  // Write information into file
	File file = new File(url);
	try{
		FileOutputStream fos = new FileOutputStream(file);				
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(listscore);
		fos.close();
		oos.close();
	}catch (FileNotFoundException ex){
		ex.printStackTrace();
	}catch (IOException ex){
		ex.printStackTrace();
	}
}

@SuppressWarnings("unchecked")
public static <ListScore>ArrayList<ListScore> ReadFromFile(String url){			// Read information from file
	ArrayList<ListScore> arrayList = new ArrayList<>();
	File file = new File(url);
	try{
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		arrayList = (ArrayList<ListScore>) ois.readObject();
		fis.close();
		ois.close();
	}catch (FileNotFoundException ex){
		ex.printStackTrace();
	}catch (IOException ex){
		ex.printStackTrace();
	}catch (ClassNotFoundException ex){
		ex.printStackTrace();
	}
	return arrayList;
}

public void paint(Graphics g ) {  
	super.paint(g);
	Graphics2D g2d = (Graphics2D)g;
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON); 			//makes the borders of the figures smoother.

    Font font = new Font("Agency FB", Font.BOLD,40);    
    
    g2d.setColor(Color.BLACK);
    g2d.fillRect(750, 200, 200, 900);
    g2d.drawImage(logo, 750, 0, null);
    
      
    g2d.setColor(Color.WHITE);
    g2d.setFont(font);
    g2d.drawString("SCORE:",755, 245);
    g2d.drawString(Integer.toString(score), 755, 285);
    g2d.drawString("BEST :", 755, 365);
    g2d.drawString(Integer.toString(best),755, 405);
    
    g2d.setColor(Color.YELLOW);
    g2d.drawRect(750, 202, 193, 100);
    g2d.drawRect(750, 320, 193, 100);
   
    
    g2d.setColor(Color.LIGHT_GRAY);
    for(int col = 160; col < 550; col = col + 110 )
    {
    	for(int row = 210; row < 550; row = row + 110)
    	{
    		g2d.drawRect(col, row, 100, 100);
    	}
    } 
   
    for(int col = 160; col < 550; col = col + 110)
    {
    	for(int row = 210; row < 550; row = row + 110)
    	{
      		if (matrix[(col-160)/110][(row-210)/110] == 0)
    		{
    			g2d.drawString(" ", col,row);
    		}
    		else if(matrix[(col-160)/110][(row-210)/110] == 2)
    		{
    			g2d.drawImage(no2, col, row, null);
    		}
    		else if(matrix[(col-160)/110][(row-210)/110] == 4)
    		{
    			g2d.drawImage(no4, col, row, null);
    		}
    		else if(matrix[(col-160)/110][(row-210)/110] == 8)
    		{
    			g2d.drawImage(no8, col, row, null);
    		}
    		else if(matrix[(col-160)/110][(row-210)/110] == 16)
    		{
    			g2d.drawImage(no16, col, row, null);
    		}
    		else if(matrix[(col-160)/110][(row-210)/110] == 32)
    		{
    			g2d.drawImage(no32, col, row, null);
    		}
    		else if(matrix[(col-160)/110][(row-210)/110] == 64)
    		{
    			g2d.drawImage(no64, col, row, null);
    		}
    		else if(matrix[(col-160)/110][(row-210)/110] == 128)
    		{
    			g2d.drawImage(no128, col, row, null);
    		}
    		else if(matrix[(col-160)/110][(row-210)/110] == 256)
    		{
    			g2d.drawImage(no256, col, row, null);
    		}
    		else if(matrix[(col-160)/110][(row-210)/110] == 512)
    		{
    			g2d.drawImage(no512, col, row, null);
    		}
    		else if(matrix[(col-160)/110][(row-210)/110] == 1024)
    		{
    			g2d.drawImage(no1024, col, row, null);
    		}else if(matrix[(col-160)/110][(row-210)/110] == 2048)
    		{
    			g2d.drawImage(no2048, col, row, null);
    		}
    	}
    }
   
    if(gameOver())
    {
       	g2d.drawImage(gameover, 150, 300, null);
    }

    setForeground(Color.RED);  
    
}

}

