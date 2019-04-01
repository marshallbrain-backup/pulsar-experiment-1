package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import ui.engine.Point;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

	private static final int BUTTON_COUNT = 3; //max mouse buttons
	
	//mouse wheel spin direction
	private int wheelDir; //what the game reads
	private int wheelState; //what is gotten from the mouse
	
	private int[] stateSpecial; //state of mouse buttons
	
	private boolean[] state;
	
	private Point mousePos; //mouse position
	private Point mouseChange; //delta between current and last mouse position
	private Point currentPos; //current mouse position
	
	private State[] poll; //current buttons presed
	private StateSpecial[] pollSpecial;
	
	public Mouse() {
		
		//initilize variables
		wheelDir = 0;
		wheelState = 0;
		
		stateSpecial = new int[BUTTON_COUNT];
		state = new boolean[BUTTON_COUNT];
		mousePos = new Point(0, 0);
		mouseChange = new Point(0, 0);
		currentPos = new Point(0, 0);
		poll = new State[BUTTON_COUNT];
		pollSpecial = new StateSpecial[BUTTON_COUNT];
		
		for(int i = 0; i < BUTTON_COUNT; ++i) {
			poll[i] = State.RELEASED;
		}
		
		for(int i = 0; i < BUTTON_COUNT; ++i) {
			pollSpecial[i] = StateSpecial.NONE;
		}
		
	}
	
	public Mouse(Mouse m, int offsetX, int offsetY) {
		
		//initilize variables
		mousePos = new Point((int) (m.mousePos.getX())-offsetX, (int) (m.mousePos.getY())-offsetY);
		mouseChange = m.mouseChange;
		currentPos = m.currentPos;
		state = m.state;
		stateSpecial = m.stateSpecial;
		poll = m.poll;
		pollSpecial = m.pollSpecial;
		
	}

	/**
	 * Silidifys the current mouse data
	 * <p>
	 * Keeps data consistent during a game tick
	 */
	public synchronized void poll() {
		
		//mouse position and delta
		mouseChange = new Point(mousePos.getX()-currentPos.getX(), mousePos.getY()-currentPos.getY());
		mousePos = new Point(currentPos);
		
		//mouse wheel
		wheelDir = wheelState;
		wheelState = 0;
		
		//mouse buttons
		for(int i = 0; i < BUTTON_COUNT; ++i) {
			
			if(state[i]) {
				if(poll[i] == State.RELEASED)
					poll[i] = State.ONCE;
				else
					poll[i] = State.PRESSED;
			} else {
				poll[i] = State.RELEASED;
			}
			
			switch(stateSpecial[i]) {
			case 1:
				pollSpecial[i] = StateSpecial.CLICKED;
				poll[i] = State.PRESSED;
				break;
			case 2:
				pollSpecial[i] = StateSpecial.DOUBLE;
				poll[i] = State.PRESSED;
				break;
			default:
				pollSpecial[i] = StateSpecial.NONE;
				break;
			}
			
			stateSpecial[i] = 0;
			
		}
		
	}

	public Point getPosition() {
		return mousePos;
	}

	public Point getChange() {
		return mouseChange;
	}

	public int getWheelDir() {
		return wheelDir;
	}

	public boolean buttonDownOnce(int button) {
		return poll[button-1] == State.ONCE;
	}

	public boolean buttonDown(int button) {
		return poll[button-1] == State.ONCE ||
				poll[button-1] == State.PRESSED;
	}

	public boolean buttonClicked(int button) {
		return pollSpecial[button-1] == StateSpecial.CLICKED;
	}

	public boolean buttonDoubleClicked(int button) {
		return pollSpecial[button-1] == StateSpecial.DOUBLE;
	}
  
	public synchronized void mousePressed(MouseEvent e) {
		state[e.getButton()-1] = true;
	}

	public synchronized void mouseReleased(MouseEvent e) {
		state[e.getButton()-1] = false;
	}

	public synchronized void mouseEntered(MouseEvent e) {
		mouseMoved(e);
	}
  
	public synchronized void mouseExited(MouseEvent e) {
		mouseMoved(e);
	}
  
	public synchronized void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseMoved(MouseEvent e) {
		currentPos = e.getPoint();
	}

	public synchronized void mouseWheelMoved(MouseWheelEvent e) {
		wheelState = e.getWheelRotation();
	}
	
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2 && !e.isConsumed()) {
			e.consume();
			stateSpecial[e.getButton()-1] = 2;
		} else {
			stateSpecial[e.getButton()-1] = 1;
		}
	}
	
}