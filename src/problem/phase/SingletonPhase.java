package problem.phase;

import problem.api.IPatternDetector;
import problem.api.IPhase;
import problem.patterns.SingletonPatternDetector;

public class SingletonPhase implements IPhase {

	@Override
	public void execute() {
		IPatternDetector detector = new SingletonPatternDetector();
		detector.detectPattern();
	}

}
