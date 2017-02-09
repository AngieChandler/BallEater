import java.util.*;

public class BallFactory{

	int level; //between 1 and 12
	GameArena arena;
	String[] colours = {"BLUE", "RED", "YELLOW", "GREEN", "WHITE", "ORANGE", "GREY", "MAGENTA", "PINK", "CYAN", "DARKGREY", "LIGHTGREY"};
	Ball[] ball;
	String targetColour;
	int maxBalls = 100;
	int[] ballsPerLevel = {3,5,8,10,18,26,33,45,58,70,85,100};
	
	public BallFactory(int level,String targetColour){
		this.level = level;
		this.targetColour = targetColour;
	}

	public void generateBalls(GameArena arena){
		String ballColour;

		this.arena = arena;
		
		//speed, colours and so on depend on level
		double maxSpeed = level;		
		maxBalls = ballsPerLevel[level];
		ball = new Ball[maxBalls];
		int size = (int)(300.0/(double)maxBalls);
		
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
			ball[i].setColour("BLACK");
			arena.removeBall(ball[i]);
		}		
		
		//add new ones
		generateBalls(arena);
	}
	

	public int checkCollision(BallEater b){
		for(int i=0;i<maxBalls;i++){
			if(ball[i].getSize()>0){
				double dist = Math.sqrt((ball[i].getXPosition() - b.getXPosition())*(ball[i].getXPosition() - b.getXPosition()) + (ball[i].getYPosition() - b.getYPosition())*(ball[i].getYPosition() - b.getYPosition()));

				if(dist < (b.getSize() + ball[i].getSize())){
					if(ball[i].getColour().equals(targetColour)){
						removeBall(i);
						return ballsRemaining();						
					}
					return -1;
					
				}				
			}
		}
		
		return ballsRemaining();
	}

	public void checkCollision(Ball b){
		for(int i=0;i<maxBalls;i++){
			if(!ball[i].equals(b) && ball[i].getSize() > 0)
				b.collision(ball[i]);
		}		
	}
	
	
	private void removeBall(int no){
		ball[no].setColour("BLACK");
		ball[no].setSize(0);
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
			if(ball[i].getSize() > 0){
				ball[i].bounce(arena.getArenaWidth(),arena.getArenaHeight());
				checkCollision(ball[i]);
			}
		}
	
	}
	
}