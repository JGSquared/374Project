package problem.phase;

import problem.api.IPatternDetector;
import problem.api.IPhase;
import problem.patterns.DecoratorPatternDetector;

public class DecoratorPhase implements IPhase {

	@Override
	public void execute() {
		IPatternDetector detector = new DecoratorPatternDetector();
		detector.detectPattern();
	}

}
