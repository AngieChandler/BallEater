public class Driver{

	public static void main(String[] args){
		int level = 3;
		int maxLevel = 12;
		int xSize=600,ySize=600;
		GameArena arena = new GameArena(xSize,ySize);
		BallEater eater = new BallEater(30,30,20,"BLUE","YELLOW");
		eater.addToGameArena(arena);
		
		BallFactory ballFactory = new BallFactory(level);
		ballFactory.generateBalls(arena);
				
		while(true){
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
		
				
				
	}

}