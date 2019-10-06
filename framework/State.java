package framework;

public class State {
	private boolean stateEnded;
	private boolean isTransition; 
	private State nextState;
	
	public State()
	{
		isTransition = false; // Can go to any other state at any other time (eg: idle or walking)
	}
	public State(State nextState)
	{
		this.nextState = nextState;
	}
	
	public void update(StateMachine machine)
	{
		if (isTransition && stateEnded)
		{
			machine.setActiveState(this.nextState);
		}
	}
}
