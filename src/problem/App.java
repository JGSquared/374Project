package problem;

import java.io.IOException;

import problem.api.IClass;
import problem.api.IDesignParser;
import problem.api.IGraphDesign;
import problem.api.IPatternDetector;
import problem.gui.UI;
import problem.patterns.AdapterPatternDetector;
import problem.patterns.DecoratorPatternDetector;
import problem.patterns.SingletonPatternDetector;

public class App {
	
	public static void main(String[] args) throws IOException {
		
		IDesignParser dp = new ClassDesignParser();
		IGraphDesign graphDesigner = new DotGraphDesign();
		
		dp.parse(args, graphDesigner);
		IPatternDetector newPattern = new AdapterPatternDetector();
		IPatternDetector otherPattern = new SingletonPatternDetector();
		IPatternDetector detPattern = new DecoratorPatternDetector();
		detPattern.detectPattern();
		newPattern.detectPattern();
		otherPattern.detectPattern();
		for (IClass className : ClassStorage.getClasses()) {
//			System.out.println(className.getClassName());
//			System.out.println(className.getPatternLabel());
		}
		ConfigProperties.getInstance().setupConfig("./input_output/config.txt");
//		
//		for (Pattern p : PatternStorage.getPatterns()) {
//			System.out.println("Class name: " + p.getClassName());
//			System.out.println("Pattern: " + p.getLabel());
//			for (Pattern p2 : p.getRelatedClasses()) {
//				System.out.println("Related class: " + p2.getClassName());
//			}
//		}
		
		UI ui = new UI();
	}
	
}
