
import java.util.*;

/**
	This class defines and controls the BallEater object, which includes 6 separate Ball objects
	@see Ball
	
	@author Angie Chandler
*/

public class BallEater
{
	//reference numbers for BallEater elements
	public static final int BODY = 0;
	public static final int LEFT_EYE = 1;
	public static final int LEFT_PUPIL = 2;
	public static final int RIGHT_EYE = 3;
	public static final int RIGHT_PUPIL = 4;
	public static final int MOUTH = 5;

	//array of BallEater elements and position, size and speed variables
	private Ball[] components = new Ball[6];
	private double x;
	private double y;
	private double diameter;
	private String bodyCol;
	private String eyeCol;
	private double xSpeed = 1;
	private double ySpeed = 1;
	
	//reference to Joe's GameArena
	private GameArena arena;
	
	//the individual Balls in the BallEater - can be referenced individually or through array
	private Ball body;
	private Ball leftEye;
	private Ball leftPupil;
	private Ball rightEye;
	private Ball rightPupil;
	private Ball mouth;

	
	//animation variables
	private int yawnCounter=0;
	private int yawnMax = 10;
	private int yawnFreq = 20;
	private int chompCounter=0;
	private int chompMax = 10;
	private int winkCounter=0;
	private int winkMax = 10;
	private int winkFreq = 5;
	private Random random;
	
	/**
	Constructor - creates an instance of the BallEater class
	*/
	public BallEater(double x, double y, double diameter, String bodyCol, String eyeCol){
		this.x = x;
		this.y = y;
		this.diameter = diameter;
		this.bodyCol = bodyCol;
		this.eyeCol = eyeCol;
		
		body = new Ball(x,y,diameter,bodyCol);
		components[BODY] = body; //refer to the same ball, but within an array in case we need it
		
		leftEye = new Ball(x-diameter/2,y-diameter/2,diameter/5,eyeCol);
		components[LEFT_EYE] = leftEye;
		leftPupil = new Ball(x-diameter/2,y-diameter/2,diameter/10,"BLACK");
		components[LEFT_PUPIL] = leftPupil;
		rightEye = new Ball(x+diameter/2,y-diameter/2,diameter/5,eyeCol);
		components[RIGHT_EYE] = rightEye;
		rightPupil = new Ball(x+diameter/2,y-diameter/2,diameter/10,"BLACK");
		components[RIGHT_PUPIL] = rightPupil;
				
		mouth = new Ball (x,y+diameter/2,diameter/4,"BLACK");
		components[MOUTH] = mouth;		
		
		random = new Random();
	}

	/**
	adds the BallEater elements to the GameArena
	@param GameArena
	*/
	public void addToGameArena(GameArena arena){
		this.arena = arena;
		
		for(Ball b : components){
			arena.addBall(b);
		}
	}
	
	/**
	 * Obtains the colour of body of this BallEater.
	 * @return String colour

	*/
	public String getColour(){
		return bodyCol;
	}
	
	/**
	Sets the colour of the body of this BallEater
	@param String colour
	*/
	public void setColour(String colour){
		body.setColour(colour);
		bodyCol = colour;
	}
	/** obtains the size of the body of this BallEater
	@return double diameter in pixels*/
	public double getSize(){
		return diameter;
	}
	
	/** obtains the x position of this BallEater
	@return double x coordinate of the BallEater within the GameArena
	*/
	public double getXPosition(){
		return x;
	}
	/** obtains the y position of this BallEater
	@return double y coordinate of the BallEater within the GameArena
	*/
	public double getYPosition(){		
		return y;
	}
	/**
	 * Moves the current position of this BallEater to the given co-ordinates
	 * @param x the new x co-ordinate of this BallEater
	 */
	public void setXPosition(double x){
		body.setXPosition(x);
		leftEye.setXPosition(x-diameter/2);
		leftPupil.setXPosition(x-diameter/2);
		rightEye.setXPosition(x+diameter/2);
		rightPupil.setXPosition(x+diameter/2);
		mouth.setXPosition(x);				
	}

	/**
	 * Moves the current position of this BallEater to the given co-ordinates
	 * @param y the new y co-ordinate of this BallEater
	 */
	public void setYPosition(double y){
		body.setYPosition(y);
		leftEye.setYPosition(y-diameter/2);
		leftPupil.setYPosition(y-diameter/2);
		rightEye.setYPosition(y-diameter/2);
		rightPupil.setYPosition(y-diameter/2);
		mouth.setYPosition(y+diameter/2);						
	}
	/**
	 * Updates the current speed of this BallEater
	 * @param xSpeed the new x speed of this BallEater
	 */	
	public void setXSpeed(double xSpeed){
		this.xSpeed = xSpeed;
		for(int i=0;i<components.length;i++){
			components[i].setXSpeed(xSpeed);
		}
	}

	/** obtain the current xSpeed of this BallEater
	@return xSpeed
	*/
	public double getXSpeed(){
		return xSpeed;
	}
	
	/**
	 * Updates the current speed of this BallEater
	 * @param xSpeed the new x speed of this BallEater
	 */	
	public void setYSpeed(double ySpeed){
		this.ySpeed = ySpeed;
		for(int i=0;i<components.length;i++){
			components[i].setYSpeed(ySpeed);
		}		
	}
	
	/** obtain the current ySpeed of this BallEater
	@return ySpeed
	*/
	public double getYSpeed(){
		return ySpeed;		
	}
	
	/** makes this BallEater to move according to the current speed
	*/
	public void move(){
		for(Ball b : components){
			b.move();
		}
	}
	
	/** makes this BallEater move according to the current speed, and bounce if the edge is hit
	@param maxX, maxY define the edge of the area
	*/
	public void bounce(double maxX,double maxY){
		for(int i=0;i<6;i++){
			components[i].bounce(maxX,maxY);
			if(i==BODY && components[BODY]!=null){
				x = components[BODY].getXPosition();				
				y = components[BODY].getYPosition();
			}
		}		
	}
	
	/** display the colour and size settings of this BallEater
	*/
	public void display(){
		System.out.println("BallEater: colour="+bodyCol+", eyes="+eyeCol+", x="+x+", y="+y+", diameter="+diameter); 
	}
	
/*
//incomplete, animation of BallEater
	public void animation(){
		if(chompCounter==0)
			yawn();
		if(yawnCounter==0)
			wink();
	}
	public void chomp(){
		System.out.println("CHOMP");
	}
	
	private void yawn(){
		if(yawnCounter == 0){
			int guess = (int)(random.nextDouble()*yawnFreq);
		
			if(guess == yawnFreq){
				System.out.println("YAWN");
				double mouthSize = mouth.getSize();
				mouthSize*=2;
				mouth.setSize(mouthSize);
				yawnCounter++;
			}
		}
		else if(yawnCounter == yawnMax){
			mouth.setSize(diameter/4);
			yawnCounter=0;
		}
		else
			yawnCounter++;
	}

	private void wink(){
		if(winkCounter == 0){
			int guess = (int)(random.nextDouble()*yawnFreq);
			
			if(guess == winkFreq){
				//start wink
				//System.out.println("WINK");
				winkCounter++;
			}
		}
		else if(winkCounter == winkMax){
			//end wink
			winkCounter=0;
		}
		else
			winkCounter++;
	}
*/
	
	/** speeds this BallEater up for a given period of time
	*/
	public void startBoost(){
		xSpeed+=10;
		ySpeed+=10;
		for(int i=0;i<components.length;i++){
			components[i].setXSpeed(xSpeed);
			components[i].setYSpeed(ySpeed);
		}
	}
	
	/** returns this BallEater to normal speed
	*/
	public void endBoost(){
		xSpeed-=10;
		ySpeed-=10;
		for(int i=0;i<components.length;i++){
			components[i].setXSpeed(xSpeed);
			components[i].setYSpeed(ySpeed);
		}
	}	
}