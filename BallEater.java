public class BallEater
{
	public static final int BODY = 0;
	public static final int LEFT_EYE = 1;
	public static final int LEFT_PUPIL = 2;
	public static final int RIGHT_EYE = 3;
	public static final int RIGHT_PUPIL = 4;
	public static final int MOUTH = 5;

	private Ball[] components = new Ball[6];
	private double x;
	private double y;
	private double diameter;
	private String bodyCol;
	private String eyeCol;
	private double xSpeed = 1;
	private double ySpeed = 1;
	
	private Ball body;
	private Ball leftEye;
	private Ball leftPupil;
	private Ball rightEye;
	private Ball rightPupil;
	private Ball mouth;

	
	public BallEater(double x, double y, double diameter, String bodyCol, String eyeCol){
		this.x = x;
		this.y = y;
		this.diameter = diameter;
		this.bodyCol = bodyCol;
		this.eyeCol = eyeCol;
		
		Ball body = new Ball(x,y,diameter,bodyCol);
		components[BODY] = body; //refer to the same ball, but within an array in case we need it
		
		Ball leftEye = new Ball(x-diameter/2,y-diameter/2,diameter/5,eyeCol);
		components[LEFT_EYE] = leftEye;
		Ball leftPupil = new Ball(x-diameter/2,y-diameter/2,diameter/10,"BLACK");
		components[LEFT_PUPIL] = leftPupil;
		Ball rightEye = new Ball(x+diameter/2,y-diameter/2,diameter/5,eyeCol);
		components[RIGHT_EYE] = rightEye;
		Ball rightPupil = new Ball(x+diameter/2,y-diameter/2,diameter/10,"BLACK");
		components[RIGHT_PUPIL] = rightPupil;
				
		Ball mouth = new Ball (x,y+diameter/2,diameter/4,"BLACK");
		components[MOUTH] = mouth;		
	}

	
	
	public String getColour(){
		return components[BODY].getColour();
	}
	
	public double getSize(){
		return components[BODY].getSize();
	}
	
	public double getXPosition(){
		return components[BODY].getXPosition();
	}
	
	public double getYPosition(){
		return components[BODY].getYPosition();
	}
	
	public void setXPosition(double x){
		
				
	}
	
	public void setXSpeed(double xSpeed){
		this.xSpeed = xSpeed;
		for(int i=0;i<components.length;i++){
			components[i].setXSpeed(xSpeed);
		}
	}
	
	public double getXSpeed(){
		return xSpeed;
	}
	
	public void setYSpeed(double ySpeed){
		this.ySpeed = ySpeed;
		for(int i=0;i<components.length;i++){
			components[i].setYSpeed(ySpeed);
		}		
	}
	
	public double getYSpeed(){
		return ySpeed;		
	}
	
	public void move(){
		
	}
	
	public void bounce(){
		
	}
	
}