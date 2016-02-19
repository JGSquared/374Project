package problem.api;

public interface IArrow extends ITraverser {
	
	String getFrom();
	void setFrom(String from);
	String getTo();
	void setTo(String to);
	String getColor();
	void setColor(String color);
	String getType();
	void setType(String type);
	String getArrowhead();
	void setArrowhead(String arrowhead);
	String getStyle();
	void setStyle(String style);
	String getLabel();
	void setLabel(String label);
	boolean getCanLabel();
	void setCanLabel(boolean canLabel);
	
}
