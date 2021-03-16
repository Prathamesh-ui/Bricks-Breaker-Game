package OOP_Project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;		

public class GamePlay extends JPanel implements KeyListener, ActionListener {
//JPanel - container which to organize components, various layouts like background of frame
//KeyListener - It is used as interferance to receive keys which you pressed
//ActionListener - It is used as interferance to receive actions when you pressed a key
	
	private boolean play = false;		//Game shouldn't start by itself
	private int score = 0;		//Initial score is zero
		
	private int Total_Bricks = 21;		//Total no. of bricks
	
	private Timer timer;		//Timer class for setting the time of ball how fast should it move
	private int delay = 8;		//Speed of ball i.e. delay 8
	
	private int playerX=310;		//Starting position of slider
	private int ballposX=120;	//Starting position of ball w.r.t. X
	private int ballposY=350;	//Starting position of ball w.r.t.Y
	
	private int ballXdir = -1;	//Ball Direction after we start game
	private int ballYdir = -2;
	
	private Map map;
	
	public GamePlay()
	{
		map = new Map(3,7);	
		addKeyListener(this);		//Register the object with a component
		setFocusable(true);		//True = if the working of components in same tab
		setFocusTraversalKeysEnabled(false);	//Sets whether focus traversal keys are enabled for this Component
		timer = new Timer(delay, this);	//Speed of ball 
		timer.start();
	}
	
	public void paint(Graphics g)		//Drawing slider, ball, bricks
	{
		//Backgrounds
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		// drawing map
			map.draw((Graphics2D) g);
				
		//Boarders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 5, 592);
		g.fillRect(0, 0, 692, 5);
		g.fillRect(681, 0, 5, 592);
		
		// the scores 		
			g.setColor(Color.white);	//Font color
			g.setFont(new Font("serif",Font.BOLD, 25));
			g.drawString(""+score, 590,30);
				
		
		//Paddel
		g.setColor(Color.red);
		g.fillRect(playerX, 550, 105, 10);
		
		//ball
	g.setColor(Color.yellow);
	g.fillOval(ballposX, ballposY, 20, 20);
	
	// when you won the game
			if(Total_Bricks <= 0)
			{
				 play = false;
	             ballXdir = 0;		//If you won then ball should not move
	     		 ballYdir = 0;
	             g.setColor(Color.YELLOW);
	             g.setFont(new Font("serif",Font.BOLD, 30));
	             g.drawString("You Won", 260,300);
	             
	             g.setColor(Color.RED);
	             g.setFont(new Font("serif",Font.BOLD, 20));           
	             g.drawString("Press (Enter) to play again!!", 230,350);  
			}
			
			// when you lose the game
			if(ballposY > 570)
	        {
				 play = false;
	             ballXdir = 0;
	     		 ballYdir = 0;
	             g.setColor(Color.RED);
	             g.setFont(new Font("serif",Font.BOLD, 30));
	             g.drawString("Game Over, Your Score: "+score, 190,300);
	             
	             g.setColor(Color.RED);
	             g.setFont(new Font("serif",Font.BOLD, 20));           
	             g.drawString("Press (Enter) to Restart", 230,350);        
	        }
	
		g.dispose();		//A Graphics object cannot be used after dispose has been called. 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();	//moving of components starts
		if(play)
		{	//Creating rectangle around ball to detect intersect between ball and padel
			if(new Rectangle(ballposX, ballposY,20,20).intersects(new Rectangle(playerX, 550, 30, 10 )))
			{
				ballYdir = -ballYdir;	//If ball intersect padel then its direction is reversed
				ballXdir = -2;
			}
			else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 10)))
			{
				ballYdir = -ballYdir;
				ballXdir = ballXdir + 1;		//Here we used different direction of ball
			}
			else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 10)))
			{
				ballYdir = -ballYdir;
			}
			
			// check map collision with the ball		
		A: 	for(int i = 0; i<map.map.length; i++)
			{																		//2D array
				for(int j =0; j<map.map[0].length; j++)		//We need to break
				{				
					if(map.map[i][j] > 0)
					{
						//Detecting intersetion
						int brickX = j * map.brickwidth + 80;	//Detect the position of brick
						int brickY = i * map.brickheight + 50;
						int brickWidth = map.brickwidth;
						int brickHeight = map.brickheight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);		//Creating rectagle around bricks			
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);	//Creating rectangle around ball to detect intersect
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect))
						{					
							map.setBrickValue(0, i, j);
							score+=5;	
							Total_Bricks--;
							
							// when ball hit right or left of brick
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width)	
							{				//left														//right
								ballXdir = -ballXdir;
							}
							// when ball hits top or bottom of brick
							else
							{
								ballYdir = -ballYdir;				
							}
							
							break A;
						}
					}
				}
			}
			ballposX += ballXdir;		//Increase ball position in X direction
			ballposY += ballYdir;		//Increase ball position in Y direction
			if(ballposX<0)
			{
				ballXdir = -ballXdir;		//If ball hit to left boarder it reverse
			}
			if(ballposY<0)
			{
				ballYdir = -ballYdir;		//If ball hit to top boarder it reverse
			}
			if(ballposX>670)
			{
				ballXdir = -ballXdir;		//If ball hit to right boarder it reverse
			}
		}
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {	}
	@Override
	public void keyReleased(KeyEvent e) {	}

	@Override
	public void keyPressed(KeyEvent e) 
	{ 
		if(e.getKeyCode()== KeyEvent.VK_RIGHT)		//Right arrow key working
		{
			if(playerX>=570)
			{
				playerX=10;
			}
			else
			{
				moveRight();
			}
		}
		
		if(e.getKeyCode()== KeyEvent.VK_LEFT)		//Left arrow key working
		{
			if(playerX<=15)
			{
				playerX=570;
			}
			else
			{
				moveLeft();
			}
			
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{          
			if(!play)
			{
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				score = 0;
				Total_Bricks = 48;
				map = new Map(4,12);
				
				repaint();
			}
        }
		
	}
	
	public void moveRight()
	{
		play = true;			//It will start playing game
		playerX+=20;
	}
	
	public void moveLeft()
	{
		play = true;			//It will start playing game
		playerX-=20;
	}

}
