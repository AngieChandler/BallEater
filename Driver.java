public class Driver{

	public static void main(String[] args){
		int level = 1;
		String eaterColour = "BLUE";
		boolean isKids = false;
		if(args.length>0){
			isKids = testIsKids(args[0]);
			level = testLevel(args[0]);
			if(args.length>1 && !isKids)
				isKids = testIsKids(args[1]);
			else if(args.length > 1)
				level = testLevel(args[1]);
		}
		
		System.out.println("BALL EATER");
		System.out.println("Help the BallEater eat the balls that match its colour...");
		System.out.println("Level "+level);
		
		int maxLevel = 12;
		int xSize=1000,ySize=800;
		GameArena arena = new GameArena(xSize,ySize);
		BallEater eater = new BallEater(30,30,20,eaterColour,"YELLOW");
		eater.addToGameArena(arena);
		
		BallFactory ballFactory = new BallFactory(level,eaterColour,isKids);
		ballFactory.generateBalls(arena);

		//check for starting with a ball on top of player
		while(ballFactory.checkCollision(eater)==-1){
			ballFactory.resetBalls();
		}		
				
		while(ballFactory.checkCollision(eater)!=-1){
			if(ballFactory.checkCollision(eater)==0){
				level++;
				if(level<=12)
					System.out.println("Congratulations, you won! Moving up to level "+level);
				else
					System.out.println("Congratulations, you won!");					
				
				ballFactory.changeLevel(level);
				ballFactory.resetBalls();
				
				//make sure the player doesn't start with a collision
				while(ballFactory.checkCollision(eater)==-1){
					ballFactory.resetBalls();
				}		
			}
			arena.pause();
			ballFactory.bounce();
		
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
			eater.bounce(arena.getArenaWidth(),arena.getArenaHeight());	

			//eater.animation();			
		}		
		
		System.out.println("Game over");

		arena.exit();
				
	}

	private static boolean testIsKids(String args){
		if(args.equals("kids"))
			return true;
		return false;
	}
	
	//there is a parse method for this, but this avoids exceptions when the possible inputs are known
	private static int testLevel(String args){
		for(int i=1;i<=12;i++){
			if(args.equals(""+i))
				return i;
		}
		return 1;
	}
	
}