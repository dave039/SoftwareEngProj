package game_object;

import java.awt.Graphics2D;

public class ProjectileObject extends GameObject{

	public ProjectileObject(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		type = GameObject.PROJECTILE_TYPE;
		speed = 10;
	}
	
	public void update() {
        super.update();

        y -= speed;
        
        if(y < 0){
        	isAlive = false;
        }
    }
	
	public void render(Graphics2D g){
		g.drawImage(image, x, y, width, height, null);
	}
	
	public void collisionDetected(int type){
		if(type == GameObject.ENEMY_TYPE){
			isAlive = false;
		}else if(type == GameObject.BOSS_TYPE){
			isAlive = false;
		}
	}
}
