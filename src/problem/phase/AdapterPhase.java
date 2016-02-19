package problem.phase;

import problem.api.IPatternDetector;
import problem.api.IPhase;
import problem.patterns.AdapterPatternDetector;

public class AdapterPhase implements IPhase {

	@Override
	public void execute() {
		IPatternDetector detector = new AdapterPatternDetector();
		detector.detectPattern();
	}

}
