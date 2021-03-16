package OOP_Project;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame obj = new JFrame();		//Creating object of Jframe to create frame/window for game
        GamePlay g = new GamePlay();		//Creating gameplay object to play game
        obj.setBounds(10, 10, 700, 600);		//Window with 700 width and 600 height
        obj.setTitle("Brick-it-out");		//Game title
        obj.setResizable(false);		//False then we cannot change size of window
        obj.setVisible(true);		//To see the frame on screen
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//To close window
        obj.add(g);	//Add object of gameplay
    }
}

