package problem.phase;

import problem.api.IPatternDetector;
import problem.api.IPhase;
import problem.patterns.CompositePatternDetector;

public class CompositePhase implements IPhase {

	@Override
	public void execute() {
		IPatternDetector detector = new CompositePatternDetector();
		detector.detectPattern();
	}

}
