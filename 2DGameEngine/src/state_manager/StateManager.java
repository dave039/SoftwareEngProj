package state_manager;

import java.awt.Graphics2D;

public class StateManager implements State {

    private State currentState;

    public StateManager() {
    }

    public void setState(State state) {
        this.currentState = state;
    }

    public State getState() {
        return this.currentState;
    }

    public void update(boolean[] keys) {
        this.currentState.update(keys);
    }

    public void render(Graphics2D g) {
        this.currentState.render(g);
    }
}
