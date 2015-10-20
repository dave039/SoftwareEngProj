package state_manager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import utilities.Keys;

public class GameState implements State {
	
	protected boolean upKey = false;
	protected boolean downKey = false;
	protected boolean leftKey = false;
	protected boolean rightKey = false;
	protected boolean wKey = false;
	protected boolean aKey = false;
	protected boolean sKey = false;
	protected boolean dKey = false;
	protected boolean spaceKey = false;
	protected boolean shiftKey = false;
	protected boolean controlKey = false;
	protected boolean enterKey = false;
	
	
	protected BufferedImage backgroundImage;
	
	public GameState(){
		
	}
	
    public void keyPressed(int key) {
        if(key == Keys.UP){
        	upKey = true;
        }
        else if(key == Keys.DOWN){
        	downKey = true;
        }
        else if(key == Keys.LEFT){
        	leftKey = true;
        }
        else if(key == Keys.RIGHT){
        	rightKey = true;
        }
        else if(key == Keys.W){
        	wKey = true;
        }
        else if(key == Keys.A){
        	aKey = true;
        }
        else if(key == Keys.S){
        	sKey = true;
        }
        else if(key == Keys.D){
        	dKey = true;
        }
        else if(key == Keys.SPACE){
        	spaceKey = true;
        }
        else if(key == Keys.SHIFT){
        	shiftKey = true;
        }
        else if(key == Keys.CTRL){
        	controlKey = true;
        }
        else if(key == Keys.ENTER){
        	enterKey = true;
        }
    }

    public void keyReleased(int key) {
    	if(key == Keys.UP){
        	upKey = false;
        }
        else if(key == Keys.DOWN){
        	downKey = false;
        }
        else if(key == Keys.LEFT){
        	leftKey = false;
        }
        else if(key == Keys.RIGHT){
        	rightKey = false;
        }
        else if(key == Keys.W){
        	wKey = false;
        }
        else if(key == Keys.A){
        	aKey = false;
        }
        else if(key == Keys.S){
        	sKey = false;
        }
        else if(key == Keys.D){
        	dKey = false;
        }
        else if(key == Keys.SPACE){
        	spaceKey = false;
        }
        else if(key == Keys.SHIFT){
        	shiftKey = false;
        }
        else if(key == Keys.CTRL){
        	controlKey = false;
        }
        else if(key == Keys.ENTER){
        	enterKey = false;
        }
    }

    public void keyTyped(int key) {
    }

    public void update() {
    }

    public void render(Graphics2D g) {

    }
}
