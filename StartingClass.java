package kiloboltgame;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.awt.*; 
import java.applet.*; 
import java.awt.event.*;


public class StartingClass extends Applet implements Runnable,MouseListener, KeyListener, ActionListener {

	private Robot robot;
	private Question question;
	private Image image, currentSprite, character, characterDown, characterJumped, background,blackbox;
	private Graphics second;
	private URL base;
	private static Background bg1, bg2;
	private static Box box,box1;
	private Random randomGenerator = new Random();
	private Label label1 = new Label();
	private Button btn1=new Button();
	private Button btn2=new Button();
	private String q="";
	private int flag;
	//ADDED VARIABLES
	private int x,boxpos=1,nextboxpos=0;
	private int xpos; 
	private int ypos;
	private boolean rect1Clicked; 
	private boolean rect2Clicked;
	private int rect1xco,rect1yco,rect1width,rect1height; 
	private int rect2xco,rect2yco,rect2width,rect2height; 
	
	@Override
	public void init() {

		setSize(800, 480);
		
		//ADDED DECLARATIONS
		rect1xco = 0; 
		rect1yco = 380; 
	    rect1width = 100; 
		rect1height = 50; 
		rect2xco = 700; 
		rect2yco = 380; 
	    rect2width = 100; 
		rect2height = 50; 
		
		/*rect1xco = 20; 
		rect1yco = 20; 
		rect1width = 100; 
		rect1height = 50; 
		*/
		setBackground(Color.BLACK);
		setFocusable(true);
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
		
		//ADDED MOUSE LISTENER
		addMouseListener(this);
		
	}
	String num;
	int n;
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn1) 
		{
			robot.jump();
			System.out.println("Button 1 was pressed");
			num=btn1.getName();
			n = Integer.parseInt(num);

			System.out.println(num);
			if(n==ropt)
				System.out.println("right");
			else
				System.out.println("wrong");
			
		}
		else
			System.out.println("Button 2 was pressed");
		num=btn2.getName();
		n = Integer.parseInt(num);
		System.out.println(num);
		if(n==ropt)
			System.out.println("right");
		else
			System.out.println("wrong");
	
		
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
	
	int ans=0;
	int opt2=0;
	int ropt;
	String r,w;
	Random random=new Random();
	public String getquestion()  {
		String q="";
		
		
	    int num1;
		int num2;
		num1 = random.nextInt(10);
		num2=random.nextInt(10);
		q=num1+"+"+num2;
		ans=num1+num2;
		do{
		opt2=random.nextInt(20);
		}while(ans==opt2);
		System.out.println(q);
		return q;
		
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
			}
		    else if(robot.isDucked()){
		    	currentSprite = characterDown;
		    }
		    else if (robot.isJumped() == false && robot.isDucked() == false){
				currentSprite = character;
			}
			
		       
			box.update();
			box1.update();
			x=next_box();
			/*GAVE TWO VARIABLES TO STORE PRESENT BOX AND NEXT BOX AND KEEP SHITFTING VALUES*/
			if(box.getX() <=0){
					boxpos=nextboxpos;
					nextboxpos=x%2;
					System.out.println("Present box "+boxpos +" Next box "+nextboxpos );
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
				boxpos=nextboxpos;
				nextboxpos=x%2;
				System.out.println("Present box "+boxpos +" Next box "+nextboxpos );
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
		    	 {
				 
				    
				     robot.update(0);
				     label1.setVisible(false);
				     label1.setSize( 200, 100 );
				     label1.setForeground(Color.WHITE);
				     label1.setBackground(Color.BLACK);
				     Font myFont = new Font("Serif",Font.BOLD,24);
		             label1.setFont(myFont);
		             if(flag==0){
		             q=getquestion();
		             r=""+ans;
			    	 w=""+opt2;
			    	 ropt=randomGenerator.nextInt(50-0 + 1) + 0;
		             flag=1;
		             }	  
				     label1.setText("");
				     label1.setLocation(300, 0); 
				     label1.setAlignment(Label.CENTER);
				     
				     btn1.setVisible(false);
				     btn2.setVisible(false);
				     btn1.setLocation(0,380);
				     btn1.setBackground(Color.blue);
				     btn2.setBackground(Color.blue);
				     btn2.setLocation(700,380);
				     btn1.setSize(100,50);
				     btn2.setSize(100,50);
				     btn1.addActionListener(this);
					 btn2.addActionListener(this);
				     
			    	 btn1.setForeground(Color.WHITE);
			    	 btn2.setForeground(Color.WHITE);
				     
			    	 if(ropt%2==0)
				     {
				    	
				    	 btn1.setLabel(r);
				    	 btn2.setLabel(w);
				    	 
				     
				     }
				     else
				     {
				    	 btn1.setLabel(w);
				    	 btn2.setLabel(r);
				    	 
				     }
				     
				     
				     
				     
				     
		    	 }
			 
		     else{
		    	 flag=0;
		    	 robot.update(1);
		    	 
		    	 //ADDED CODE TO MAKE HIM STAND UP AFTER HE DUCKS 
		    	 if(robot.isDucked()){
						currentSprite = character;
						robot.setDucked(false);
				 }
		    	
		    	 label1.setVisible(false);
		    	 btn1.setVisible(false);
		    	 btn2.setVisible(false);
		    	
		    	 
		    	 
		     }
		    bg1.update();
			bg2.update();
			add(label1);
			add(btn1);
			add(btn2);
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
		g.setFont(new Font("Serif",Font.BOLD,48));
		g.setColor(Color.BLUE);
		g.drawString(q, 300, 50);
		g.setColor(Color.black); 
		g.fillRect(0,380,100,50);
		g.setColor(Color.black); 
		g.fillRect(700,380,100,50);
		g.setFont(new Font("Serif",Font.PLAIN,36));
		if(ropt%2==0){
			
			g.setColor(Color.BLUE);
			g.drawString(r, 50, 430);
			g.setColor(Color.BLUE);
			g.drawString(w, 750, 430);
		}
		else{
			g.setColor(Color.BLUE);
			g.drawString(w, 50, 430);
			g.setColor(Color.BLUE);
			g.drawString(r, 750, 430);
		
		}
		
		/*THE CODE THAT MAKES HIM APPROPRIATELY JUMP N DUCK
		 * PLS ADD THE COLLISION CODE AT THE SYSOUT CODE SPACES  
		 */
		if (rect1Clicked) {
			  if(boxpos%2==0 && ropt%2==0){
				  robot.jump();
			  }
			  else if(boxpos%2==0 && ropt%2!=0){
				  System.out.println("Wrong");
			  }
			  else if(boxpos%2!=0 && ropt%2==0){
				  if (robot.isDucked() == false){
						robot.setDucked(true);
						robot.setSpeedX(0);
						
					}
					else if(robot.isDucked())
						robot.setDucked(false);
			  }
			  else
				 System.out.println("AGAIN WRONG XD");
			  
			  rect1Clicked=false;
		  }
		  
		  if (rect2Clicked) {
				
			  if(boxpos%2==0 && ropt%2!=0){
				  robot.jump();
			  }
			  else if(boxpos%2==0 && ropt%2==0){
				  System.out.println("Wrong");
			  }
			  else if(boxpos%2!=0 && ropt%2!=0){
				  if (robot.isDucked() == false){
						robot.setDucked(true);
						robot.setSpeedX(0);
						
					}
					else if(robot.isDucked())
						robot.setDucked(false);
			  }
			  else
				 System.out.println("AGAIN WRONG XD");
			  
			  rect2Clicked=false;
			
		  }
		
		
		
		
	}
	
	/* CODE FOR RECOGNISING MOUSE CLICKS*/
	
	@Override
	public void mouseClicked (MouseEvent me) { 

		  // Save the coordinates of the click lke this. 
		  xpos = me.getX(); 
		  ypos = me.getY(); 

		  // Check if the click was inside the rectangle area. 
		  if (xpos > rect1xco && xpos < rect1xco+rect1width && ypos >rect1yco &&  
		    ypos < rect1yco+rect1height)  rect1Clicked = true; 
		  // if it was not then rect1Clicked is false; 
		  else if(xpos > rect2xco && xpos < rect2xco+rect2width && ypos >rect2yco &&  
			ypos < rect2yco+rect2height)  rect2Clicked = true; 
		  //show the results of the click 
		  
		  repaint(); 

		 } 

	/*NOT REQUIRED TO DEFINE */
	
	public void mouseEntered (MouseEvent me) {} 
	
	public void mousePressed (MouseEvent me) {} 
	
	public void mouseReleased (MouseEvent me) {}  
	
	public void mouseExited (MouseEvent me) {}  

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

	
	
	

}