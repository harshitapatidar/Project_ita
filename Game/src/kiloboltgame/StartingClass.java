package kiloboltgame;

import java.applet.Applet;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Random;
public class StartingClass extends Applet implements Runnable, KeyListener {

	private Robot robot;
	private Image image, currentSprite, character, characterDown, characterJumped, background,blackbox;
	private Graphics second;
	private URL base;
	private int x;
	private static Background bg1, bg2;
	private static Box box,box1;
	Random randomGenerator = new Random();
	@Override
	public void init() {

		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Q-Bot Alpha");
		try {
			base = getDocumentBase();
		} catch (Exception e) {
		}

		// Image Setups
		character = getImage(base, "data/character.png");
		characterDown = getImage(base, "data/down.png");
		characterJumped = getImage(base, "data/jumped.png");
		currentSprite = character;
		background = getImage(base, "data/background.png");
		blackbox=getImage(base,"data/blackbox.png");
	}

	@Override
	public void start() {
		bg1 = new Background(0,0);
		bg2 = new Background(2160, 0);
		robot = new Robot();
        box=new Box(750,420);
        box1=new Box(300,350);
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void stop() {
	}

	@Override
	public void destroy() {
	}
    
	public int next_box()
	{
		return randomGenerator.nextInt(50-0 + 1) + 0;
	}
	@Override
	public void run() {
		while (true) {
			
		   
		    if (robot.isJumped()){
				currentSprite = characterJumped;
			}else if (robot.isJumped() == false && robot.isDucked() == false){
				currentSprite = character;
			}
			
			box.update();
			box1.update();
			
			x=next_box();
			
			if(box.getX() <=0){
					if(x%2==0)
					{
						box.reinit(900+x,420);
						box.update();
					}
					else
					{
						box.reinit(900+x,350);	
						box.update();
					}
					
					
			}
			
			
			if(box1.getX() <=0){
					if(x%2==0)
					{
						box1.reinit(900+x,420);
						box1.update();
					}
					else
					{
						box1.reinit(900+x,350);	
						box1.update();
					}
					 
				}
			
			 if((box.getX()- robot.getCenterX() < 300 && box.getX()- robot.getCenterX() > 0) || (box1.getX()- robot.getCenterX() < 300 && box1.getX()- robot.getCenterX() > 0))
		    	 robot.update(0);
			 
		     else
		    	 robot.update(1);
			bg1.update();
			bg2.update();
			
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);

	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
		g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
		g.drawImage(currentSprite, robot.getCenterX() - 61, robot.getCenterY() - 63, this);
		
		g.drawImage(blackbox, box.getX() -25, box.getY()-25, this); 
		g.drawImage(blackbox, box1.getX() -25, box1.getY()-25, this);  
		
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			System.out.println("Move up");
			break;

		case KeyEvent.VK_DOWN:
			currentSprite = characterDown;
			if (robot.isJumped() == false){
				robot.setDucked(true);
				robot.setSpeedX(0);
			}
			break;

		

		
		case KeyEvent.VK_SPACE:
			robot.jump();
			break;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			System.out.println("Stop moving up");
			break;

		case KeyEvent.VK_DOWN:
			currentSprite = character;
			robot.setDucked(false);
			break;

		

		

		case KeyEvent.VK_SPACE:
			break;

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}

    public static Box getbox(){
    	return box;
    }
	//changes

}