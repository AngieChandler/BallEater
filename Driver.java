/** This is the Driver class for BallEater. The game can accept input to declare the game fast (fast 1-10), kids or to declare a level (1-11)
	
	@author Angie Chandler

*/
public class Driver{

	public static void main(String[] args){
		int level = 1;
		boolean isKids = false;
		int isFast = 0;
		if(args.length>0){
			isKids = testIsKids(args[0]);
			level = testLevel(args[0]);
			isFast = testIsFast(args[0]);
			if(args.length>1 && !isKids)
				isKids = testIsKids(args[1]);
			else if(args.length > 1)
				level = testLevel(args[1]);
		}

		BallEaterGame game = new BallEaterGame(level,isFast,isKids, "BLUE");
		game.play();
				
	}
	/**
	test for a "kids" game entry
	@return true or false for Kids game entry
	*/
	private static boolean testIsKids(String args){
		if(args.equals("kids"))
			return true;
		return false;
	}

	/** test for fast game
	@return true or false for Fast game entry
	*/
	private static int testIsFast(String args){
		if(args.startsWith("fast")){
			if(args.equals("fast"))
				return 1;
			for(int i=1;i<11;i++){
				if(args.endsWith(""+i))
					return i;
			}
			return 1;
		}
		return 0;
	}
	
	//there is a parse method for this, but this avoids exceptions when the possible inputs are known
	/** test for game level, parse int without exception
	@return level number
	*/
	private static int testLevel(String args){
		for(int i=1;i<=12;i++){
			if(args.equals(""+i))
				return i;
		}
		return 1;
	}
	
}