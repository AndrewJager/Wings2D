package framework;

public class StateMachine {
	private State activeState;

	public State getActiveState() {
		return activeState;
	}

	public void setActiveState(State activeState) {
		this.activeState = activeState;
	}
}
