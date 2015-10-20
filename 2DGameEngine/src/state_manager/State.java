package state_manager;

import java.awt.Graphics2D;

public interface State {
    
    public void update();
    public void render(Graphics2D g);
    public void keyPressed(int key);
    public void keyReleased(int key);
    public void keyTyped(int key);
}
