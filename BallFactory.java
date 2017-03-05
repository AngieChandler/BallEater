import java.util.*;

/**
This class generates and controls all Balls on the GameArena when playing BallEater
	@see BallEater
	@see Ball
	@see GameArena
	
	@author Angie Chandler
*/
public class BallFactory{

	int level; //between 1 and 12
	GameArena arena;
	BallEaterGame game;
	String[] colours = {"BLUE", "RED", "YELLOW", "GREEN", "WHITE", "ORANGE", "MAGENTA", "PINK", "CYAN"};
	Ball[] ball;
	String targetColour;
	int maxBalls = 100;
	double baseSize = 300.0;
	int[] ballsPerLevel = {3,5,8,10,18,26,33,45,58,70,85,100};
	Random random;

	/**
	constructor, generates a new BallFactory with a given level and BallEater colour (target colour)
	@param level, the game's current level (int 1-11) 
	@param targetColour, String representing the colour that the BallEater is able to eat
	*/
	public BallFactory(int level,String targetColour){
		this.level = level;
		this.targetColour = targetColour;
	}
	
	/**
	constructor, generates a new (easier) BallFactory for kids with a given level and BallEater colour (target colour)
	@param level, the game's current level (int 1-11)
	@param targetColour, String representing the colour that the BallEater is able to eat
	@param isKids, boolean declaring whether the game is in kids' mode
	*/
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

	
	public BallFactory(BallEaterGame game){
		this.game = game;
		this.level = game.getLevel();
		this.targetColour = (game.getBallEater()).getColour();
		
		if(game.getIsKids()){
			for(int i=0;i<ballsPerLevel.length;i++){
				ballsPerLevel[i] = (i+1)*2;
			}
			baseSize = 100.0;
		}
		random  = new Random();
		
	}
	
	
	/**
	generates the balls for this level and adds them to the GameArena
	@param arena, the GameArena
	*/
	public void generateBalls(GameArena arena){
		String ballColour;

		this.arena = arena;
		
		//speed, colours and so on depend on level
		double maxSpeed = level;		
		maxBalls = ballsPerLevel[level-1];
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

	/**
	changes the current level of this BallEater game
	@param level, integer between 1 and 11.
	*/
	public void changeLevel(int level){
		this.level = level;
	}
	
	/**
	resets the Balls in the GameArena, removing the existing ones and generating new ones
	*/
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
	
	/**
	checks for collisions between Balls and BallEater and responds to these collisions.
	@param b, the BallEater to check against
	@return ballsRemaining
	*/
	public int checkCollision(BallEater b){
		for(int i=0;i<maxBalls;i++){
			if(!ball[i].getEaten()){
				double dist = Math.sqrt((ball[i].getXPosition() - b.getXPosition())*(ball[i].getXPosition() - b.getXPosition()) + (ball[i].getYPosition() - b.getYPosition())*(ball[i].getYPosition() - b.getYPosition()));

				if(dist < (b.getSize() + ball[i].getSize())){
					if(ball[i].getColour().equals(targetColour)){
						removeBall(i);
						game.addBallEatenScore();
						//b.chomp(); //animation - incomplete
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

	/**
	Changes the target colour (and the colour of the BallEater)
	@param b, the BallEater in the game
	*/
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
	
	
	/**
	checks for collisions between Balls and responds to these collisions. Note that collisions between Balls do not obey proper laws of physics!
	@param b, the Ball object to check against
	@param index, the index in the array of Balls of this ball
	*/
	public void checkCollision(int index){
		if(index<maxBalls){
			for(int i=index+1;i<maxBalls;i++){
				if(!ball[i].equals(ball[index]) && !ball[i].getEaten())
					ball[index].collision(ball[i]);
			}		
		}
	}
	

	/**
	removes a Ball from the GameArena when it is eaten
	@param no, the integer representing the Ball to remove
	*/
	private void removeBall(int no){
		ball[no].setColour("#123456");
		//ball[no].setSize(0);
		ball[no].isEaten();
		arena.removeBall(ball[no]);
	}
	
	/**
	counts how many balls of the target colour remain to be eaten
	@return the number of balls of the correct colour remaining
	*/
	private int ballsRemaining(){
		int ballsToGo = 0;
		for(int i=0;i<maxBalls;i++){
			if(ball[i].getColour().equals(targetColour))
				ballsToGo++;
		}
		
		return ballsToGo;
	}

	/**
	moves the Balls in this BallFactory according to their current speed
	*/
	public void move(){
		for(int i=0;i<maxBalls;i++){
			ball[i].move();
		}
	}
	
	/**
	moves the balls in this BallFactory and checks for collisions against the edge of the GameArena, and other balls.
	*/
	public void bounce(){
		for(int i=0;i<maxBalls;i++){
			if(!ball[i].getEaten()){
				ball[i].bounce(arena.getArenaWidth(),arena.getArenaHeight());
				checkCollision(i);
			}
		}
	
	}
	
}