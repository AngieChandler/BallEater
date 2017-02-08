import java.util.*;

public class BallFactory{

	int level;
	GameArena arena;
	String[] colours = {"BLUE", "RED", "YELLOW", "GREEN", "WHITE", "ORANGE", "GREY", "MAGENTA", "PINK", "CYAN", "DARKGREY", "LIGHTGREY"};
	Ball[] ball = new Ball[100];

	public BallFactory(int level){
		this.level = level;
	}

	public void generateBalls(GameArena arena){
		this.arena = arena;
		
		//stolen from Joe's sample 7
		Random random = new Random();
		double maxSpeed = level;		
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
	
	}
	
	public void move(){
		
	}
	
	public void bounce(){
		for(int i=0;i<ball.length;i++){
			ball[i].bounce(arena.getArenaWidth(),arena.getArenaHeight());
		}
	
	}
	
}