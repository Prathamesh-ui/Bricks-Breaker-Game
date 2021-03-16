
package OOP_Project;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Map {
    public int map[][];			//2D array to store bricks
    public int brickwidth;	//Width of brick
    public int brickheight;	//Height of Brick 
    public Map(int r, int c){	//Constructor to recieve no of rows and columns that how much rows and columns generated
        map = new int[r][c];  //instanciate 2D array with value of  row amd column 
        for(int i=0; i < map.length; i++){
            for(int j=0; j < map[0].length; j++ ){	//Iterae through the columns
                map[i][j]=1;	//The brick which does not intersected by ball and 0 if brick is intersected by ball
            }
        }
        brickwidth = 540/c;		//Calculating  brick width
        brickheight = 150/r;		//Calculating Height of brick
    }
    public void draw(Graphics2D g){				//2D graphic function to draw bricks
        for(int i=0; i < map.length; i++){
            for(int j=0; j < map[0].length; j++ ){
                if(map[i][j]>0){ //If value is grater than zero than create the brick
                    g.setColor(Color.white);
                    g.fillRect(j*brickwidth+80, i*brickheight+50, brickwidth, brickheight);	//Create Bricks but have no boarders
                    				//X-Position			//Y-Position
                    g.setStroke(new BasicStroke(3));		//To create Strocks between bricks
                    g.setColor(Color.black); 
                    g.drawRect(j*brickwidth+80, i*brickheight+50, brickwidth, brickheight);//Create black boarder for bricks
                    
                }
            }
        }
    }
    public void setBrickValue(int value, int r, int c){ // Here we pass value as 0 if ball is intersect to brick
        map[r][c]=value; 
    }  
    
}

