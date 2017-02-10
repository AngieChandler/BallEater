import java.util.*;

public class BallFactory{

	int level; //between 1 and 12
	GameArena arena;
	String[] colours = {"BLUE", "RED", "YELLOW", "GREEN", "WHITE", "ORANGE", "MAGENTA", "PINK", "CYAN"};
	Ball[] ball;
	String targetColour;
	int maxBalls = 100;
	double baseSize = 300.0;
	int[] ballsPerLevel = {3,5,8,10,18,26,33,45,58,70,85,100};
	Random random;

	
	public BallFactory(int level,String targetColour){
		this.level = level;
		this.targetColour = targetColour;
	}
	
	public BallFactory(int level,String targetColour,boolean isKids){
		this.level = level;
		this.targetColour = targetColour;
		
		if(isKids){
			for(int i=0;i<ballsPerLevel.length;i++){
				ballsPerLevel[i] = (i+1)*2;
			}
			baseSize = 100.0;
		}
		random  = new Random();
	}

	public void generateBalls(GameArena arena){
		String ballColour;

		this.arena = arena;
		
		//speed, colours and so on depend on level
		double maxSpeed = level;		
		maxBalls = ballsPerLevel[level];
		ball = new Ball[maxBalls];
		int size = (int)(baseSize/(double)maxBalls);
		
		//stolen from Joe's sample 7
		Random random = new Random();
		for(int i=0;i<ball.length;i++){
            double x = random.nextDouble() * arena.getArenaWidth();
            double y = random.nextDouble() * arena.getArenaHeight();
            double s1 = maxSpeed - random.nextDouble()*maxSpeed*2;
            double s2 = maxSpeed - random.nextDouble()*maxSpeed*2;

			if(i<level)
				ballColour = targetColour;
			else
				ballColour = colours[(int)(random.nextDouble() * colours.length)];
		    ball[i] = new Ball(x, y, size, ballColour);
            ball[i].setXSpeed(s1);
            ball[i].setYSpeed(s2);
			ball[i].setFriction(1);

		    arena.addBall(ball[i]);
		}
	
	}

	public void changeLevel(int level){
		this.level = level;
	}
	
	public void resetBalls(){
		//remove existing balls
		for(int i=0;i<maxBalls;i++){
			//ball[i].setColour("BLACK");
			if(!ball[i].getEaten())
				arena.removeBall(ball[i]);
			arena.pause();
			ball[i] = null;
		}		
		
		arena.pause();
		
		//add new ones
		generateBalls(arena);
	}
	

	public int checkCollision(BallEater b){
		for(int i=0;i<maxBalls;i++){
			if(!ball[i].getEaten()){
				double dist = Math.sqrt((ball[i].getXPosition() - b.getXPosition())*(ball[i].getXPosition() - b.getXPosition()) + (ball[i].getYPosition() - b.getYPosition())*(ball[i].getYPosition() - b.getYPosition()));

				if(dist < (b.getSize() + ball[i].getSize())){
					if(ball[i].getColour().equals(targetColour)){
						removeBall(i);
						b.chomp();
						int ballsLeft = ballsRemaining();
						if(level>1 && ballsLeft>0)
							changeTargetColour(b);
						return ballsRemaining();						
					}
					return -1;
					
				}				
			}
		}
		
		return ballsRemaining();
	}

	private void changeTargetColour(BallEater eater){
		String[] countColours = new String[maxBalls];
		for(int i=0;i<maxBalls;i++){
			if(!ball[i].getEaten())
				countColours[i] = ball[i].getColour();
			else
				countColours[i] = targetColour;
		}
		int newCol = (int)(random.nextDouble()*maxBalls);
		if(!countColours[newCol].equals(targetColour))
		{
			targetColour = countColours[newCol];
			eater.setColour(targetColour);
		}
	}
	
	public void checkCollision(Ball b){
		for(int i=0;i<maxBalls;i++){
			if(!ball[i].equals(b) && !ball[i].getEaten())
				b.collision(ball[i]);
		}		
	}
	
	
	private void removeBall(int no){
		ball[no].setColour("#123456");
		//ball[no].setSize(0);
		ball[no].isEaten();
		arena.removeBall(ball[no]);
	}
	
	private int ballsRemaining(){
		int ballsToGo = 0;
		for(int i=0;i<maxBalls;i++){
			if(ball[i].getColour().equals(targetColour))
				ballsToGo++;
		}
		
		return ballsToGo;
	}

	
	public void move(){
		for(int i=0;i<maxBalls;i++){
			ball[i].move();
		}
	}
	
	public void bounce(){
		for(int i=0;i<maxBalls;i++){
			if(!ball[i].getEaten()){
				ball[i].bounce(arena.getArenaWidth(),arena.getArenaHeight());
				checkCollision(ball[i]);
			}
		}
	
	}
	
}