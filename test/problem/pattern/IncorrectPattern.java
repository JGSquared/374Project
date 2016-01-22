package problem.pattern;

public class IncorrectPattern {
	private static IncorrectPattern uniqueInstance;
	
	public IncorrectPattern() {
		
	}
	
	public static IncorrectPattern getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new IncorrectPattern();
		}
		return uniqueInstance;
	}
}
