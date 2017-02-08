import java.util.*;

public class Driver{

	public static void main(String[] args){
		int xSize=300,ySize=300;
		Ball[] ball = new Ball[100];
		GameArena arena = new GameArena(xSize,ySize);
		BallEater eater = new BallEater(30,30,20,"BLUE","YELLOW");
		eater.addToGameArena(arena);
		
		//stolen from Joe's sample 7
		Random random = new Random();
		double maxSpeed = 10;		
		for(int i=0;i<ball.length;i++){
            double x = random.nextDouble() * arena.getArenaWidth();
            double y = random.nextDouble() * arena.getArenaHeight();
            double s1 = maxSpeed - random.nextDouble()*maxSpeed*2;
            double s2 = maxSpeed - random.nextDouble()*maxSpeed*2;

		    ball[i] = new Ball(x, y, 2, "#FF0000");
            ball[i].setXSpeed(s1);
            ball[i].setYSpeed(s2);
			ball[i].setFriction(1);

		    arena.addBall(ball[i]);
		}
				
		while(true){
			arena.pause();
			for(int i=0;i<ball.length;i++){
				ball[i].bounce(arena.getArenaWidth(),arena.getArenaHeight());
			}
		
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