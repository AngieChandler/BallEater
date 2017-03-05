/** This is the BallEaterGame class that does the main game control for BallEater 
	@see BallEater
	@see GameArena
	@see Ball
	@see BallFactory
	
	@author Angie Chandler

*/
public class BallEaterGame{

	private int score;
	private int level;
	private int isFast;
	private boolean isKids;
	private String colour;
	private GameArena arena;
	private BallEater eater;
	private BallFactory ballFactory;
	private int maxLevel;
	private int xSize ;
	private int ySize;
	private boolean stopGame = false;
	
	/**
	constructor, sets up the BallEater game
	@param level, int representing the current level
	@param isFast, int level of BallEater speed (default is 0)
	@param isKids, boolean is a kids level, or not
	@param eaterColour, the target colour of the BallEater and target balls
	*/
	public BallEaterGame(int level, int isFast, boolean isKids, String eaterColour){
		score = 0; //add a score for the player
		this.isFast = isFast;
		this.isKids = isKids;
		this.level = level;
		
		System.out.println("BALL EATER");
		System.out.println("Help the BallEater eat the balls that match its colour...");
		System.out.println("Level "+level);
		
		maxLevel = 11;
		xSize=1000;
		ySize=800;
		arena = new GameArena(xSize,ySize);
		eater = new BallEater(30,30,20,eaterColour,"YELLOW");
		eater.addToGameArena(arena);
		
	}

	/**
	main game control - run the game until the BallEater collides with a Ball of the wrong colour and the game ends 
	*/
	public void play(){		
		int boostCounter = 100;
		int boostRecharge = 0;

		if(isFast>0){
			isKids = true;
			for(int i=0;i<isFast;i++)
				eater.startBoost();
		}

		//ballFactory = new BallFactory(level,eater.getColour(),isKids);
		ballFactory = new BallFactory(this);
		ballFactory.generateBalls(arena);

		//check for starting with a ball on top of player
		while(ballFactory.checkCollision(eater)==-1){
			ballFactory.resetBalls();
		}		
				
		while(ballFactory.checkCollision(eater)!=-1 && !stopGame){
			//game has been exited externally
			if(stopGame)
				break;
			//start new level
			if(ballFactory.checkCollision(eater)==0){
				level++;
				addLevelCompleteScore();
				if(level<12)
					System.out.println("Congratulations, you won! Moving up to level "+level+", your current score is "+score);
				else
					System.out.println("Congratulations, you won!");					
				
				ballFactory.changeLevel(level);
				ballFactory.resetBalls();
				
				//make sure the player doesn't start with a collision
				while(ballFactory.checkCollision(eater)==-1){
					ballFactory.resetBalls();
				}		
			}
			
			//slow game down so you can see it
			arena.pause();
			
			//pause
			if(arena.pausePressed()){
				while(!arena.rightPressed() && !arena.leftPressed() && !arena.upPressed() && !arena.downPressed()){
					arena.pause();
				}
			}
			
			//boost
			if(!arena.boostPressed()){
				//check for ending without running out
				if(boostCounter!=100){
					eater.endBoost();
					boostCounter = 100;
					boostRecharge = 0;
				}
				boostRecharge++;
			}
			else{ 
				if(boostRecharge>500){
					//charged enough to boost and trying to boost
				
					//if boost is run out, reset everything
					if(boostCounter==0){
						boostRecharge = 0;
						boostCounter = 100;					
						eater.endBoost();
					}
					else if(boostCounter == 100){
						//if there's still boost left, go!
						eater.startBoost();
						boostCounter--;
					}
				}
			}
			
			//move all balls
			ballFactory.bounce();
		
			//detect input
			if(arena.leftPressed()){
				double xSpeed = -Math.abs(eater.getXSpeed());
				eater.setXSpeed(xSpeed);
			}
			if(arena.rightPressed()){
				double xSpeed = Math.abs(eater.getXSpeed());
				eater.setXSpeed(xSpeed);
			}
			if(arena.upPressed()){
				double ySpeed = -Math.abs(eater.getYSpeed());
				eater.setYSpeed(ySpeed);
			}
			if(arena.downPressed()){
				double ySpeed = Math.abs(eater.getYSpeed());
				eater.setYSpeed(ySpeed);
			}
			
			//move eater
			eater.bounce(arena.getArenaWidth(),arena.getArenaHeight());	

			//animate eater
			//eater.animation();			
		}		
		
		System.out.println("Game over, your score was "+score);

		arena.exit();
				
	}
	
	//stop game
	public void stop(){
		stopGame = true;

		System.out.println("Game stopped, your score was "+score);

		arena.exit();
		
	}
	
	/**
	obtains the current score
	@return score
	*/
	public int getScore(){
		return score;
	}
	public void addBallEatenScore(){
		score+=10;
	}
	public void addLevelCompleteScore(){
		score+=100;
	}

	
	public int getLevel(){
		return level;
	}
	
	public String getTargetColour(){
		return eater.getColour();
	}
	
	public boolean getIsKids(){
		return isKids;
	}
	
	public BallEater getBallEater(){
		return eater;
	}
	
}