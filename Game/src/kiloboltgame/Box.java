package kiloboltgame;

public class Box {
	private int speedX, centerX, centerY;
	private Background bg = StartingClass.getBg1();
	
	public void update() {
		
		centerX += speedX;
		speedX = bg.getSpeedX();
		}
    
	public Box(int x, int y)
	{
		System.out.println(x+" "+y);
		centerX=x;
		centerY=y;
	}
	
	public int getX()
	{
		return centerX;
		
	}
	public void reinit(int x,int y)
	{
		centerX=x;
		centerY=y;
	}
	
	public int getY()
	{
		return centerY;
	}
	
	public Background getBg() {
		return bg;
	}
	public void setX(int centerX) {
		this.centerX = centerX;
	}

	public void setY(int centerY) {
		this.centerY = centerY;
	}
	
	
	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	
	public void setBg(Background bg) {
		this.bg = bg;
	}
}

