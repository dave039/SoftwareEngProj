package game;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 700;

    GamePanel panel; //displays image of game

    public GameWindow(String title) {
        JFrame window = new JFrame(title); //initialize window
        panel = new GamePanel();	//initialize panel
        window.add(panel);	//add the panel to the window

        window.setSize(WIDTH, HEIGHT);	//set the size of window
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit the program when the window is closed
        window.setVisible(true); //make the window show up

        panel.run(); //start the game loop
    }
}
