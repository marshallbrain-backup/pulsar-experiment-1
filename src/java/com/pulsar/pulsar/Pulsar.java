package pulsar;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import files.FileParser;
import files.GameFile;
import input.Keyboard;
import input.Mouse;
import settings.Settings;
import ui.engine.Circle;
import ui.engine.ScreenPosition;
import ui.engine.VectorGraphics;
import universe.Universe;

public class Pulsar extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L; //?
	
	private int fps = 0; //fps counter
	private int tick = 0; //tick counter
	
	private boolean worldLoaded = false; //is a save loaded
	
	public static boolean running = false; //is the game running
	
	public Thread mainThread; //the main game thread
	
	public JFrame frame; //the main frame
	
	private Settings settings; //settings
	private Universe universe; //game logic class
	private Keyboard keyboard; //keyboard manager
	private Mouse mouse; //mouse manager
	
	private GameFile gf;
	
	/**
	 * Gets the spesified setting value
	 *
	 * @param  frame	the jframe for the game
	 * @param  settings	the settings
	 */
	public Pulsar(JFrame f, Settings s) {
		frame = f;
		settings = s;
	}
	
	/**
	 * initalises variables
	 */
	public void init() {
		
		mouse = new Mouse(); //creates mouse class
		keyboard = new Keyboard(); //creates keyboard class
		
		//adds mouse and keyboard listeners to the canvas
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		this.addMouseWheelListener(mouse);
		this.addKeyListener(keyboard);
		
		this.requestFocusInWindow(); //sets the canvas to be the active window
		
		createBufferStrategy(2); //creates the basses for the graphacs for the canvas
		
		gf = FileParser.readCommon(); //reads in all files in common
		gf.replaceVars(); //replaces variables
		
		//TODO create class that loads all ui images
		
		//Create civ here
		
	}
	
	/**
	 * runs nessesary functions to start main game loop
	 */
	public synchronized void start(){
		
		if(running)return; //if the game is already running, do nothing
		running = true;
		mainThread = new Thread(this); //makes a new thread using this class
		mainThread.start(); //starts the thread
		
	}
	
	/**
	 * Stops the game
	 */
	public synchronized void stop(){
		
		if(!running){ //if the game is nolonger running close the window
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			return;
		}
		
		running = false;
		
		mainThread.interrupt(); //stops the main thread
		
		stop(); //calls stop again to close the window
		
	}
	
	/**
	 * Main game loop
	 */
	public void run() {
		
		init(); //initalise variables

		final int MAX_UPDATES = 5; //max number of game ticks to do before rendering a frame
//		final int MAX_RENDERS = 1;
		
		double lastUpdateTime = System.nanoTime(); //time stamp of the last time the game ticked
		double lastRenderTime = System.nanoTime(); //time stamp of the last time the game rendered
		
		final double TARGET_FPS = settings.get("FPS", 60); //target fps of the game
		final double TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS; //time between each render
		final double GAME_HERTZ = 60; //target tick rate
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ; //time between each tick
		
		int lastSecondTime = (int) (lastUpdateTime / 1000000000); //second number at start of game loop, relevant for fps counts
		
		//main game loop
		while (running) {
			
			double now = System.nanoTime(); //gets current system time
			int updateCount = 0; //number of ticks that have ocured
			
			//runs the tick function until eather the time between the last update and now is less than the set time between ticks or 
			//									  the udate count is grater than the max number of updates
			while((now-lastUpdateTime) > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES) {
				tick();
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				updateCount++;
			}
			
			if ((now-lastUpdateTime) > TIME_BETWEEN_UPDATES) {
				lastUpdateTime = now-TIME_BETWEEN_UPDATES;
			}
			
//			if((now-lastRenderTime) > TIME_BETWEEN_RENDERS && updateCount < MAX_RENDERS) {
//				float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
//				render(interpolation);
//				lastRenderTime += TIME_BETWEEN_RENDERS;
//			}
//			
//			if ((now-lastRenderTime) > TIME_BETWEEN_RENDERS) {
//				lastRenderTime = now-TIME_BETWEEN_RENDERS;
//			}
			
			float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
			render(interpolation);
			lastRenderTime = now;
	 
			int thisSecond = (int) (lastUpdateTime / 1000000000);
			
			if (thisSecond > lastSecondTime) {
				
				lastSecondTime = thisSecond;
				
				if(fps < TARGET_FPS)
					System.out.println(fps);
				if(tick < GAME_HERTZ)
					System.out.println(tick);
				fps = 0;
				tick = 0;
				
			}
			
			while ((now-lastRenderTime) < TIME_BETWEEN_RENDERS && (now-lastUpdateTime) < TIME_BETWEEN_UPDATES) {
				
				Thread.yield();
				
				try {
					Thread.sleep(1);
				} catch(Exception e) {
				}
				
				now = System.nanoTime();
				
			}
			
		}
		
		stop();
		
	}
	
	public void tick(){
		
		keyboard.poll(); //set current keyboard state
		mouse.poll(); //set current mouse state
		
		//if a game world is loaded call the tick function for that world
		if(worldLoaded) {
			universe.tick();
		} else {
			universe = new Universe(null, gf);
			worldLoaded = true;
		}
		
		tick++;
		
	}
	
	public void render(float interpolation){
		
		BufferStrategy bs = this.getBufferStrategy();
		
		Graphics g = bs.getDrawGraphics();
		VectorGraphics vg = new VectorGraphics(g);
		
		//RENDER HERE
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		
		g.setColor(Color.WHITE);
		vg.translationSet(ScreenPosition.CENTER);
		vg.drawCircle(new Circle(0, 0, 50));
		
		//END RENDER
		
		g.dispose();
		bs.show();
		
		fps++;
		
	}

}
