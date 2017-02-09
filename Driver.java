public class Driver{

	public static void main(String[] args){
		int level = 1;
		String eaterColour = "BLUE";
		
		System.out.println("BALL EATER");
		System.out.println("Help the BallEater eat the balls that match its colour...");
		System.out.println("Level 1");
		
		int maxLevel = 12;
		int xSize=600,ySize=600;
		GameArena arena = new GameArena(xSize,ySize);
		BallEater eater = new BallEater(30,30,20,eaterColour,"YELLOW");
		eater.addToGameArena(arena);
		
		BallFactory ballFactory = new BallFactory(level,eaterColour);
		ballFactory.generateBalls(arena);		
				
		while(ballFactory.checkCollision(eater)!=-1){
			if(ballFactory.checkCollision(eater)==0){
				level++;
				if(level<=12)
					System.out.println("Congratulations, you won! Moving up to level "+level);
				else
					System.out.println("Congratulations, you won!");					
				
				ballFactory.changeLevel(level);
				ballFactory.resetBalls();
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
			
		}		
		
		System.out.println("Game over");

		arena.exit();
				
	}

}