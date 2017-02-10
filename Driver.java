public class Driver{

	public static void main(String[] args){
		int level = 1;
		String eaterColour = "BLUE";
		boolean isKids = false;
		if(args.length>0 && args[0].equals("kids"))
			isKids = true;
		
		System.out.println("BALL EATER");
		System.out.println("Help the BallEater eat the balls that match its colour...");
		System.out.println("Level 1");
		
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

			eater.animation();			
		}		
		
		System.out.println("Game over");

		arena.exit();
				
	}

}