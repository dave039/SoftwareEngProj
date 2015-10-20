package state_manager;

import java.awt.Graphics2D;

public class StateManager implements State {
	
    private State currentState;
    

    public StateManager(){
    }
    
    public void setState(State state) {
        this.currentState = state;
    }

    public State getState() {
        return this.currentState;
    }

    public void update() {
        this.currentState.update();
    }

    public void render(Graphics2D g) {
        this.currentState.render(g);
    }

    public void keyPressed(int key) {
        this.currentState.keyPressed(key);
    }

    public void keyReleased(int key) {
        this.currentState.keyReleased(key);
    }

    public void keyTyped(int key) {
        this.currentState.keyTyped(key);
    }
}
