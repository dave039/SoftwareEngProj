package game_object;

import game.GameWindow;
import java.awt.Graphics2D;

public class EnemyObject extends GameObject {

	public EnemyObject(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		type = GameObject.ENEMY_TYPE;
		speed = 5;
		
	}
	
	public void render(Graphics2D g) {
		g.drawImage(image, x, y, width, height, null);
    }

	
    public void update() {
        super.update();     
                
        y ++;
        x += (int)(Math.sin(y));       
              
        
        if(y > GameWindow.HEIGHT){
        	isOnScreen = false;        	
        }
    }
    
    public void collisionDetected(int type){
		if(type == GameObject.PROJECTILE_TYPE){			
			isAlive = false;
		}
	}
}
