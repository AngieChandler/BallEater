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
	
	private GameArena arena;
	
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

	public void addToGameArena(GameArena arena){
		this.arena = arena;
		
		for(Ball b : components){
			arena.addBall(b);
		}
	}
	
	
	public String getColour(){
		return bodyCol;
	}
	
	public double getSize(){
		return diameter;
	}
	
	public double getXPosition(){
		return x;
	}
	
	public double getYPosition(){		
		return y;
	}
	
	public void setXPosition(double x){
		body.setXPosition(x);
		leftEye.setXPosition(x-diameter/2);
		leftPupil.setXPosition(x-diameter/2);
		rightEye.setXPosition(x+diameter/2);
		rightPupil.setXPosition(x+diameter/2);
		mouth.setXPosition(x);				
	}

	public void setYPosition(double y){
		body.setYPosition(y);
		leftEye.setYPosition(y-diameter/2);
		leftPupil.setYPosition(y-diameter/2);
		rightEye.setYPosition(y-diameter/2);
		rightPupil.setYPosition(y-diameter/2);
		mouth.setYPosition(y+diameter/2);						
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
		for(Ball b : components){
			b.move();
		}
	}
	
	public void bounce(double maxX,double maxY){
		for(int i=0;i<6;i++){
			components[i].bounce(maxX,maxY);
			if(i==BODY && components[BODY]!=null){
				x = components[BODY].getXPosition();				
				y = components[BODY].getYPosition();
			}
		}		
	}
	
	public void display(){
		System.out.println("BallEater: colour="+bodyCol+", eyes="+eyeCol+", x="+x+", y="+y+", diameter="+diameter); 
	}
	
}