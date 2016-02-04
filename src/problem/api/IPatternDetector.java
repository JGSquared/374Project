package problem.api;

import java.util.HashMap;
import java.util.List;

public interface IPatternDetector {

	public void detectPattern(List<HashMap<String, String>> classProperties, HashMap<String, String> classCode);
}
