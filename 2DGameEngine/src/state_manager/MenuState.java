package state_manager;

import game.GamePanel;
import game.GameWindow;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import utilities.FileLoader;
import utilities.Keys;

public class MenuState extends GameState {
		
	Font font;
	
	public MenuState(){
		backgroundImage = FileLoader.loadImage("/resources/menu_background.png");
		font = new Font("Times New Roman", Font.BOLD, 20);
	}
	
    public void keyPressed(int key) {
        if(key == Keys.ENTER){
        	GamePanel.setState(new LevelOneState());
        }
    }

    public void keyReleased(int key) {
    }

    public void keyTyped(int key) {
    }

    public void update() {
    }

    public void render(Graphics2D g) {
        g.drawImage(backgroundImage, 0, 0, GameWindow.WIDTH, GameWindow.HEIGHT, null);
        
        g.setFont(font);
        g.setColor(Color.RED);
        g.drawString("Press Enter To Begin!", 150, 200);
    }
}
