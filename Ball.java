/**
 * Models a simple solid sphere. 
 * This class represents a Ball object. When combined with the GameArena class,
 * instances of the Ball class can be displayed on the screen.
 */
public class Ball 
{
	// The following instance variables define the
	// information needed to represent a Ball
	// Feel free to more instance variables if you think it will 
	// support your work... 
	
	private double xPosition;			// The X coordinate of this Ball
	private double yPosition;			// The Y coordinate of this Ball
	private double size;				// The diameter of this Ball
	private String colour = "WHITE";	// The colour of this Ball

										// Permissable colours are 8 bit hexadecimal 
                                        // RGB values in the format #RRGGBB. e.g.
                                        //
                                        // Pure red is FF0000
                                        // Pure green is 00FF00
                                        // Pure blue is 0000FF

	private double xSpeed;
	private double ySpeed;

    private double GRAVITY = 0.2;
    private double friction = 0.9;

	//ball state - whether this Ball has been eaten or not
	private boolean isEaten;
	public boolean inCollision = false;
	 
	/**
	 * Obtains the current position of this Ball.
	 * @return the X coordinate of this Ball within the GameArena.
	 */	 
	public double getXPosition()
	{
		return xPosition;
	}

	/**
	 * Obtains the current position of this Ball.
	 * @return the Y coordinate of this Ball within the GameArena.
	 */
	public double getYPosition()
	{
		return yPosition;
	}

	/**
	 * Moves the current position of this Ball to the given co-ordinates
	 * @param x the new x co-ordinate of this Ball
	 */
	public void setXPosition(double x)
	{
		this.xPosition = x;
	}

	/**
	 * Moves the current position of this Ball to the given co-ordinates
	 * @param y the new y co-ordinate of this Ball
	 */
	public void setYPosition(double y)
	{
		this.yPosition = y;
	}

	/**
	 * Obtains the size of this Ball.
	 * @return the diameter of this Ball,in pixels.
	 */
	public double getSize()
	{
		return size;
	}
	/**
	 * Obtains the size of this Ball.
	 * @param size, the diameter of this Ball,in pixels.
	 */
	public void setSize(double size){
		this.size = size;
	}
	
	/**
	 * Obtains the colour of this Ball.
	 * @return a textual description of the colour of this Ball.
	 */
	public String getColour()
	{
		return colour;
	}
	/**
	sets the colour of this Ball
	@param colour, a String representing the colour.
	*/
	public void setColour(String colour){
		this.colour = colour;
	}

	/******************************************************/

	/**
	constructor for the Ball 
	@param x, set the initial x-coordinate of the Ball
	@param y, set the initial y-coordinate of the Ball
	@param diameter, the diameter of the Ball in pixels
	@param col, a String representing the colour of the Ball
	*/
	public Ball(double x, double y, double diameter, String col)
	{
		xPosition = x;
		yPosition = y;
        xSpeed = 0;
        ySpeed = 0;

		size = diameter;
		colour = col;
		
		isEaten = false;
	}	

	/**
	sets the x speed of this Ball
	@param speed, the speed of this Ball in pixels per frame
	*/
    public void setXSpeed(double speed)
    {
        xSpeed = speed;
    }

	/**
	sets the y speed of this Ball
	@param speed, the speed of this Ball in pixels per frame	
	*/
    public void setYSpeed(double speed)
    {
        ySpeed = speed;
    }
	
	/**gets the x speed of this Ball
	@return the x speed
	*/	
	public double getXSpeed(){
		return xSpeed;
	}
	/**gets the y speed of this Ball
	@return the y speed
	*/	
	public double getYSpeed(){
		return ySpeed;
	}

	/**
	moves this Ball according to the current speed
	*/
    public void move()
    {
		xPosition += xSpeed;
		yPosition += ySpeed;
    }

	/**
	moves this Ball and checks for the edge of the GameArena 
	@param maxX, the x edge of the GameArena in pixels
	@param maxY, the y edge of the GameArena in pixels
	*/
	public void bounce(double maxX, double maxY)
	{
        move();
		if (xPosition > maxX || xPosition < 0)
        {
			xSpeed = -xSpeed;
		    xPosition += xSpeed;
            xSpeed = xSpeed * friction;
        }

		if (yPosition > maxY || yPosition < 0)
        {
			ySpeed = -ySpeed;
		    yPosition += ySpeed;
            ySpeed = ySpeed * friction;
        }
	}
	
	/**
	simple collision detection and bounce between Balls - doesn't obey the laws of physics. 
	@param b, the Ball to check this Ball against.
	*/
	public void collision(Ball b){
		double dist = Math.sqrt((xPosition - b.getXPosition())*(xPosition - b.getXPosition()) + (yPosition - b.getYPosition())*(yPosition - b.getYPosition()));

		if(dist < (size + b.getSize()) && !inCollision && !b.inCollision){
			inCollision = true;
			double tempXSpeed = b.getXSpeed();
			double tempYSpeed = b.getYSpeed();

			//if this ball is to the left
			if(xPosition < b.getXPosition()){
				xSpeed = -Math.abs(tempYSpeed);			
				b.setXSpeed(Math.abs(ySpeed));
			}
			else{
				xSpeed = Math.abs(tempYSpeed);			
				b.setXSpeed(-Math.abs(ySpeed));				
			}
			if(yPosition < b.getYPosition()){
				ySpeed = -Math.abs(tempXSpeed);
				b.setYSpeed(Math.abs(xSpeed));
			}
			else{
				ySpeed = -Math.abs(tempXSpeed);
				b.setYSpeed(Math.abs(xSpeed));
			}
			
			
			//so if xPosition < b.xPosition then this ball is on the left
			//if yPosition < b.yPosition then this ball is higher up, but we can treat that as normal and imagine we're looking at it the other way up!
			//tan theta = opposite/adjacent
			//double theta = Math.atan((b.getYPosition()-yPosition)/(b.getXPosition()-xPosition));
			//want the speeds to reflect in the normal of this line between centres
			//so have existing xSpeed and ySpeed
			//if xSpeed = 0, then the result would be mainly -xSpeed = ySpeed

		}
		else if(dist < (size + b.getSize())){
			inCollision = false;
			b.inCollision = false;
		}
	}


	/**
	sets the level of friction applied to this Ball
	@param friction, the level of friction to apply to this Ball, default is 0.9
	*/
	public void setFriction(double friction){
			this.friction = friction;
	}
	
	/**
	obtains the level of friction applied to this Ball
	@return friction
	*/
	public double getFriction(){
		return friction;
	}

	/**
	applies gravity to this Ball when calculating movement, also detects the edge of the GameArena
	@param maxX, the edge of the GameArena
	@param maxY, the edge of the GameArena
	*/
	public void gravity(double maxX, double maxY)
	{
        ySpeed = ySpeed + GRAVITY;
        bounce(maxX, maxY);

    }

	/**
	displays the settings of this Ball, including colour, size and position
	*/
	public void display(){
			System.out.println("Ball: colour="+colour+", x="+xPosition+", y="+yPosition+", diameter="+size); 	
	}

	/**
	sets this Ball's isEaten state to true.
	*/
	public void isEaten(){
		this.isEaten = true;
	}
	/**
	checks whether this Ball has been eaten
	@return true if the Ball has been eaten
	*/
	public boolean getEaten(){
		return isEaten;
	}
}
