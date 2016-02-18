package problem;

import java.util.ArrayList;
import java.util.List;

public class Pattern {

	private String label;
	private String color;
	private String arrow;
	private String className;
	private List<Pattern> relatedClasses;
	
	public Pattern(String label, String color, String arrow, String className) {
		this.label = label;
		this.color = color;
		this.arrow = arrow;
		this.className = className;
		this.relatedClasses = new ArrayList<>();
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public String getColor() {
		return this.color;
	}
	
	public String getArrow() {
		return this.arrow;
	}
	
	public String getClassName() {
		return this.className;
	}
	
	public List<Pattern> getRelatedClasses() {
		return this.relatedClasses;
	}
	
	public void addRelatedClass(Pattern pattern) {
		this.relatedClasses.add(pattern);
	}
}
