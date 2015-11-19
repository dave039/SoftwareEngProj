package state_manager;

import java.awt.Graphics2D;

public interface State {

    public void update(boolean[] keys);

    public void render(Graphics2D g);
}
