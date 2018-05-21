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
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class twoPlayer extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	int[][] matrix;
	int[][] matrix2;
	ArrayList<Integer> List1,List2,List4,List5;
	private Random random = new Random();
	private boolean running;
	private Thread game2;
	int row = 0;
	int col = 0;
	int row2 = 0;
	int col2 = 0;
	int score = 0;
	int countMove = 0;
	int score2 = 0;
	int countMove2 = 0;
	Image no2,no4,no8,no16,no32,no64,no128,no256,no512,no1024,no2048,logo,gameover,players;
	
public twoPlayer (){
	logo  = Toolkit.getDefaultToolkit().createImage("logo.jpg"); 
	players  = Toolkit.getDefaultToolkit().createImage("players.jpg"); 
	gameover  = Toolkit.getDefaultToolkit().createImage("gameover.jpg"); 
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
	matrix2 = new int[4][4];
	matrix = new int[4][4];
	initialMatrix();
	initialMatrix2();


	KeyListener listener = new KeyListener(){
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				moveUp2();
				break;
			case KeyEvent.VK_DOWN:
				moveDown2();
				break;
			case KeyEvent.VK_LEFT:
				moveLeft2();
				break;
			case KeyEvent.VK_RIGHT:
				moveRight2();
				break;
			case KeyEvent.VK_SPACE:
				stop();
				break;
			case KeyEvent.VK_W:
				moveUp();
				break;
			case KeyEvent.VK_S:
				moveDown();
				break;
			case KeyEvent.VK_A:
				moveLeft();
				break;
			case KeyEvent.VK_D:
				moveRight();
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

public void start2player(){
	if(running)
	{
		return;
	}
	else
	{
		running = true;
		game2 = new Thread(this,"game1");
		game2.start();
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

public void initialMatrix2(){
	int r2Col1 = random.nextInt(4);
	int r2Row1 = random.nextInt(4);
	int r2Col2 = random.nextInt(4-r2Col1);
	int r2Row2 = random.nextInt(4-r2Row1);
	for (int row2 =0; row2 <4; row2++)
	{
		for(int col2 = 0; col2 <4; col2++)
		{
			matrix2[r2Col1][r2Row1] = 2;
			matrix2[r2Col2][r2Row2] = (random.nextInt(2)+1)*2;
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
	
	for (int col =0; col <4; col ++)
	{
		for(int row = 3; row > 0 ; row--)
		{
			if(matrix[col][row] == matrix[col][row-1])
			{
				matrix[col][row] = matrix[col][row-1] + matrix[col][row];
				matrix[col][row-1] = 0;
				score = score +  matrix[col][row];
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
	}
}

public void pushRight(){
	for (int loop = 0; loop <2; loop++)
	{
		for (int row =0; row <4; row++)
		{
			for(int col = 0; col < 3; col++)
			{
				if(matrix[col+1][row] == 0)
				{
					matrix[col+1][row] = matrix[col][row];
					matrix[col][row] = 0;
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
	
	for (int row =0; row <4; row ++)
	{
		for(int col = 3; col > 0 ; col--)
		{
			if(matrix[col][row] == matrix[col-1][row])
			{
				matrix[col][row] = matrix[col-1][row] + matrix[col][row];
				matrix[col-1][row] = 0;
				score = score + matrix[col][row];
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
	}
}	 

public void pushLeft(){
	for (int loop = 0; loop <2; loop++)
	{
		for (int row =0; row <4; row++)
		{
			for(int col = 3; col > 0; col--)
			{
				if(matrix[col-1][row] == 0)
				{
					matrix[col-1][row] = matrix[col][row];
					matrix[col][row] = 0;
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
	
	for (int row =0; row <4;row++)
	{
		for(int col = 0; col <3 ; col++)
		{
			if(matrix[col][row] == matrix[col+1][row])
			{
				matrix[col][row] = matrix[col+1][row] + matrix[col][row];
				matrix[col+1][row] = 0;
				score = score + matrix[col][row];
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


public void addRandomNumber2(){
	int numberOfZeroSpace = 0;
	for (int row2 = 0; row2 < 4; row2++) 
	{
		for (int col2 = 0; col2 < 4; col2++) 
		{
			if (matrix2[col2][row2] == 0)
			{
				numberOfZeroSpace ++;
			}
		}
	}
	
	if(numberOfZeroSpace != 0)
	{
		int addedPosition = random.nextInt(numberOfZeroSpace);
		int countPosition = 0 ;
		for (int row2 = 0 ; row2 < 4; row2++) 
		{
	        for (int col2 = 0; col2 < 4; col2 ++)
	        {
	        	if (matrix2[col2][row2] == 0)
	        	{
	        		countPosition++;
	        	}
	        	if (countPosition == addedPosition + 1) 
	        	{
	        		matrix2[col2][row2] =(random.nextInt(2)+1)*2;
	        		return;
	        	}
	        }
		}
	}
}

public void pushUp2(){
	for (int col2 =0; col2 <4; col2++)
	{
		for (int loop = 0; loop <3; loop++)
		{
  			for(int row2 = 3; row2 > 0; row2--)
			{
  				if(matrix2[col2][row2-1] == 0)
				{
					matrix2[col2][row2-1] = matrix2[col2][row2];
					matrix2[col2][row2] = 0;
   				}
			}
		}
	}
}

public void moveUp2(){
	List4 = new ArrayList<Integer>();
	List5 = new ArrayList<Integer>();
	
	for(col2= 0; col2 <4;col2++)
	{
		for(row2=0; row2<4; row2++)
		{
			List4.add(matrix2[col2][row2]);
		}
	}
	
	pushUp2();
	
	for (int col2 =0; col2 <4;col2++)
	{
		for(int row2 = 0; row2 <3 ; row2++)
		{
			if(matrix2[col2][row2] == matrix2[col2][row2+1])
			{
				matrix2[col2][row2] = matrix2[col2][row2+1] + matrix2[col2][row2];
				matrix2[col2][row2+1] = 0;
				score2 = score2 + matrix2[col2][row2];	
			}
		}		
	}		
	
	pushUp2();
	
	for(col2= 0; col2 <4;col2++)
	{
		for(row2=0; row2<4; row2++)
		{
			List5.add(matrix2[col2][row2]);
		}
	}

	for (int i = 0; i < List4.size(); i++)
	{   
		if(List4.get(i).equals(List5.get(i)))
		{
			countMove2 = countMove2 +1;
		}
	}
	
	if(countMove2 == List4.size()){countMove2 = 0;}
	else
	{
		addRandomNumber2();
		countMove2 = 0;
	}
}	

public void pushDown2(){
	for (int col2 =0; col2 <4; col2++)
	{
		for (int loop = 0; loop <3; loop++)
		{
			for(int row2 = 0; row2 < 3; row2++)
			{
				if(matrix2[col2][row2+1] == 0)
				{
					matrix2[col2][row2+1] = matrix2[col2][row2];
					matrix2[col2][row2] = 0;
				}
			}	
		}
	}
}

public void moveDown2(){
	List4 = new ArrayList<Integer>();
	List5 = new ArrayList<Integer>();
	
	for(col2= 0; col2 <4;col2++)
	{
		for(row2=0; row2<4; row2++)
		{
			List4.add(matrix2[col2][row2]);
		}
	}
	
	pushDown2();
	
	for (int col2 =0; col2 <4; col2 ++)
	{
		for(int row2 = 3; row2 > 0 ; row2--)
		{
			if(matrix2[col2][row2] == matrix2[col2][row2-1])
			{
				matrix2[col2][row2] = matrix2[col2][row2-1] + matrix2[col2][row2];
				matrix2[col2][row2-1] = 0;
				score2 = score2 +  matrix2[col2][row2];
			}
		}	 
	}	
	
	pushDown2();
	
	for(col2= 0; col2 <4;col2++)
	{
		for(row2=0; row2<4; row2++)
		{
			List5.add(matrix2[col2][row2]);
		}
	}

	for (int i = 0; i < List4.size(); i++)
	{   
		if(List4.get(i).equals(List5.get(i)))
		{
			countMove2 = countMove2 +1;
		}
	}

	if(countMove2 == List4.size()){countMove2 = 0;}
	else
	{
		addRandomNumber2();
		countMove2 = 0;
	}
}

public void pushRight2(){
	for (int loop = 0; loop <2; loop++)
	{
		for (int row2 =0; row2 <4; row2++)
		{
			for(int col2 = 0; col2 < 3; col2++)
			{
				if(matrix2[col2+1][row2] == 0)
				{
					matrix2[col2+1][row2] = matrix2[col2][row2];
					matrix2[col2][row2] = 0;
				}
			}
		}
	}		
}

public void moveRight2(){
	List4 = new ArrayList<Integer>();
	List5 = new ArrayList<Integer>();
	
	for(col2= 0; col2 <4;col2++)
	{
		for(row2=0; row2<4; row2++)
		{
			List4.add(matrix2[col2][row2]);
		}
	}
	
	pushRight2();
	
	for (int row2 =0; row2 <4; row2 ++)
	{
		for(int col2 = 3; col2 > 0 ; col2--)
		{
			if(matrix2[col2][row2] == matrix2[col2-1][row2])
			{
				matrix2[col2][row2] = matrix2[col2-1][row2] + matrix2[col2][row2];
				matrix2[col2-1][row2] = 0;
				score2 = score2 + matrix2[col2][row2];
			}
		}	 
	}
	
	pushRight2();
	
	for(col2= 0; col2 <4;col2++)
	{
		for(row2=0; row2<4; row2++)
		{
			List5.add(matrix2[col2][row2]);
		}
	}

	for (int i = 0; i < List4.size(); i++)
	{   
		if(List4.get(i).equals(List5.get(i)))
		{
			countMove2 = countMove2 +1;
		}
	}

	if(countMove2 == List4.size()){countMove2 = 0;}
	else
	{
		addRandomNumber2();
		countMove2 = 0;
	}
}	 

public void pushLeft2(){
	for (int loop = 0; loop <2; loop++)
	{
		for (int row2 =0; row2 <4; row2++)
		{
			for(int col2 = 3; col2 > 0; col2--)
			{
				if(matrix2[col2-1][row2] == 0)
				{
					matrix2[col2-1][row2] = matrix2[col2][row2];
					matrix2[col2][row2] = 0;
				}
			}
		}
	}
}

public void moveLeft2(){
	List4 = new ArrayList<Integer>();
	List5 = new ArrayList<Integer>();
	
	for(col2= 0; col2 <4;col2++)
	{
		for(row2=0; row2<4; row2++)
		{
			List4.add(matrix2[col2][row2]);
		}
	}
	
	pushLeft2();
	
	for (int row2 =0; row2 <4;row2++)
	{
		for(int col2 = 0; col2 <3 ; col2++)
		{
			if(matrix2[col2][row2] == matrix2[col2+1][row2])
			{
				matrix2[col2][row2] = matrix2[col2+1][row2] + matrix2[col2][row2];
				matrix2[col2+1][row2] = 0;
				score2 = score2 + matrix2[col2][row2];
			}

		}	 
	}
	
	pushLeft2();

	for(col2= 0; col2 <4;col2++)
	{
		for(row2=0; row2<4; row2++)
		{
			List5.add(matrix2[col2][row2]);
		}
	}

	for (int i = 0; i < List4.size(); i++)
	{   
		if(List4.get(i).equals(List5.get(i)))
		{
			countMove2 = countMove2 +1;
		}
	}

	if(countMove2 == List4.size()){countMove2 = 0;}
	else
	{
		addRandomNumber2();
		countMove2 = 0;
	}
}

public boolean fullCol2(){
	int count4 = 0;
	boolean check4 = false;
	for(col2= 0; col2 <3;col2++)
	{
		for(row2=0; row2<4; row2++)
		{
			if(matrix2[col2][row2] == 0 || matrix2[col2][row2] == matrix2[col2+1][row2] )
			{
				count4 = count4 +1;
			}
			else
			{
				continue;
			}
			
		}
	}
	if(count4 == 0)
	{
		check4 = true;
	}
	else
	{
		check4 = false;
	}
	return check4;
}

public boolean fullRow2(){
	int count5 = 0;
	boolean check5 = false;
	for(col2= 0; col2 <4;col2++)
	{
		for(row2=0; row2<3; row2++)
		{
			if(matrix2[col2][row2] == 0 || matrix2[col2][row2] ==matrix2[col2][row2+1] )
			{
				count5 = count5 +1;
			}
			else
			{
				continue;
			}
		}
	}
	if(count5 == 0)
	{
		check5 = true;
	}
	else
	{
		check5 = false;
	}
	return check5;
}

public boolean fullSpecial2()
{
	boolean check6 = false;
	for(col2 = 0; col2 < 4;col2++)
	{
		for(row2 = 0; row2 < 4; row2++)
		{
			if(matrix2[3][3] == 0 || matrix2[3][3] == matrix2[3][2]|| matrix2[3][3] == matrix2[2][3] )
			{
				check6 = false;
			}
			else
			{
				check6 = true;
			}
		}
	}
	return check6;
}

public boolean gameOver2(){
	if(fullCol2() && fullRow2() && fullSpecial2() )
	{
		return true;
	}
	else
	{
		return false;
	}
}


public void paint(Graphics g ) {  
	super.paint(g);
	Graphics2D g2d = (Graphics2D)g;
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON); 			//makes the borders of the figures smoother.

	Font font = new Font("Agency FB", Font.BOLD,40);    
	Font font2 = new Font("Agency FB", Font.BOLD,300); 
	
	g2d.drawImage(players, 0, 0, null);
   
	g2d.setColor(Color.BLACK);
    g2d.fillRect(650, 200, 200, 900);
    g2d.drawImage(logo, 650, 0, null);
      
    g2d.setColor(Color.WHITE);
    g2d.setFont(font);
    g2d.drawString("PLAYER 1:",655, 245);
    g2d.drawString(Integer.toString(score), 655, 285);
    g2d.setColor(Color.WHITE);
    g2d.setFont(font);
    g2d.drawString("PLAYER 2:",655, 545);
    g2d.drawString(Integer.toString(score2), 655, 585);
    
    g2d.setColor(Color.YELLOW);
    g2d.drawRect(650, 202, 200, 100);
    
    g2d.setColor(Color.YELLOW);
    g2d.drawRect(650, 502, 200, 100);
      
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
    
    g2d.setColor(Color.LIGHT_GRAY);
    for(int col = 910; col < 1250; col = col + 110 )
    {
    	for(int row = 210; row < 550; row = row + 110)
    	{
    		g2d.drawRect(col, row, 100, 100);
    	}
    } 
   
    for(int col = 910; col < 1250; col = col + 110)
    {
    	for(int row = 210; row < 550; row = row + 110)
    	{
      		if (matrix2[(col-910)/110][(row-210)/110] == 0)
    		{
    			g2d.drawString(" ", col,row);
    		}
    		else if(matrix2[(col-910)/110][(row-210)/110] == 2)
    		{
    			g2d.drawImage(no2, col, row, null);
    		}
    		else if(matrix2[(col-910)/110][(row-210)/110] == 4)
    		{
    			g2d.drawImage(no4, col, row, null);
    		}
    		else if(matrix2[(col-910)/110][(row-210)/110] == 8)
    		{
    			g2d.drawImage(no8, col, row, null);
    		}
    		else if(matrix2[(col-910)/110][(row-210)/110] == 16)
    		{
    			g2d.drawImage(no16, col, row, null);
    		}
    		else if(matrix2[(col-910)/110][(row-210)/110] == 32)
    		{
    			g2d.drawImage(no32, col, row, null);
    		}
    		else if(matrix2[(col-910)/110][(row-210)/110] == 64)
    		{
    			g2d.drawImage(no64, col, row, null);
    		}
    		else if(matrix2[(col-910)/110][(row-210)/110] == 128)
    		{
    			g2d.drawImage(no128, col, row, null);
    		}
    		else if(matrix2[(col-910)/110][(row-210)/110] == 256)
    		{
    			g2d.drawImage(no256, col, row, null);
    		}
    		else if(matrix2[(col-910)/110][(row-210)/110] == 512)
    		{
    			g2d.drawImage(no512, col, row, null);
    		}
    		else if(matrix2[(col-910)/110][(row-210)/110] == 1024)
    		{
    			g2d.drawImage(no1024, col, row, null);
    		}else if(matrix2[(col-910)/110][(row-210)/110] == 2048)
    		{
    			g2d.drawImage(no2048, col, row, null);
    		}
    	}
    }
    
    g2d.setColor(Color.WHITE);
    g2d.setFont(font2);
    if(gameOver() && gameOver2())
    {
    	if(score > score2)
    	{
    		g2d.drawString("Player 1 WIN",25, 550);
    	}
    	else if(score < score2)
    	{
    		g2d.drawString("Player 2 WIN",25, 550);
    	}
    }
    

    
}

}
