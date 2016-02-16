package problem;

import java.util.ArrayList;
import java.util.List;

import problem.blueberrymuffin.ThreadController;

public class PatternStorage {
	private volatile static PatternStorage uniqueController;
	private static List<Pattern> patterns = new ArrayList<Pattern>();
	private PatternStorage() {
		
	}
	
	public static PatternStorage getInstance() {
		if (uniqueController == null) {
			synchronized (ThreadController.class) {
				if (uniqueController == null) {
					uniqueController = new PatternStorage();
				}
			}
		}
		return uniqueController;
	}
	
	public static void registerPattern(Pattern patternToRegister) {
		patterns.add(patternToRegister);
	}
	
	public static List<Pattern> getPatterns() {
		return patterns;
	}
}
