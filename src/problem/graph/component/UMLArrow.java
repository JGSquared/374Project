package problem.graph.component;

import problem.api.IArrow;
import problem.api.IVisitor;

public class UMLArrow implements IArrow {
	private String from;
	private String to;
	private String color;
	private String type;
	private String arrowhead;
	private String style;
	private String label;

	public UMLArrow(String from, String to, String color, String type, String arrowhead, String style, String label) {
		this.from = from;
		this.to = to;
		this.color = color;
		this.type = type;
		this.arrowhead = arrowhead;
		this.style = style;
		this.label = label;
	}

	@Override
	public void accept(IVisitor v) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getFrom() {
		return from;
	}

	@Override
	public void setFrom(String from) {
		this.from = from;
	}

	@Override
	public String getTo() {
		return to;
	}

	@Override
	public void setTo(String to) {
		this.to = to;
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getArrowhead() {
		return arrowhead;
	}

	@Override
	public void setArrowhead(String arrowhead) {
		this.arrowhead = arrowhead;
	}

	@Override
	public String getStyle() {
		return style;
	}

	@Override
	public void setStyle(String style) {
		this.style = style;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}

}
