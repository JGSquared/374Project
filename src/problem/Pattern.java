package problem;

public class Pattern {

	private String label;
	private String color;
	private String arrow;
	private String className;
	
	public Pattern(String label, String color, String arrow, String className) {
		this.label = label;
		this.color = color;
		this.arrow = arrow;
		this.className = className;
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
}
