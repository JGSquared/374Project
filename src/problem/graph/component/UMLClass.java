package problem.graph.component;

import java.util.ArrayList;
import java.util.List;

import problem.api.IArrow;
import problem.api.IClass;
import problem.api.IField;
import problem.api.IMethod;
import problem.api.IVisitor;

public class UMLClass implements IClass {
	private String color;
	private String className;
	private int access;
	private String patternLabel;
	private boolean canLabel;
	private List<IClass> related;
	private List<IField> fields;
	private List<IMethod> methods;
	private List<IArrow> arrows;

	public UMLClass(String color, String className, int access, String patternLabel, boolean canLabel) {
		this.color = color;
		this.className = className;
		this.access = access;
		this.patternLabel = patternLabel;
		this.canLabel = canLabel;
		this.related = new ArrayList<>();
		this.fields = new ArrayList<>();
		this.methods = new ArrayList<>();
		this.arrows = new ArrayList<>();
	}

	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
		for (IField f : this.fields) {
			f.accept(v);
		}
		v.visit(this);
		for (IMethod m : this.methods) {
			m.accept(v);
		}
		v.postVisit(this);
		for (IArrow a : this.arrows) {
			a.accept(v);
		}
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
	public String getClassName() {
		return className;
	}

	@Override
	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public int getAccess() {
		return access;
	}

	@Override
	public void setAccess(int access) {
		this.access = access;
	}

	@Override
	public String getPatternLabel() {
		return patternLabel;
	}

	@Override
	public void setPatternLabel(String patternLabel) {
		this.patternLabel = patternLabel;
	}

	@Override
	public List<IField> getFields() {
		return fields;
	}

	@Override
	public void addIField(IField field) {
		this.fields.add(field);
	}

	@Override
	public List<IMethod> getMethods() {
		return methods;
	}

	@Override
	public void addIMethod(IMethod method) {
		this.methods.add(method);
	}

	@Override
	public List<IArrow> getArrows() {
		return arrows;
	}

	@Override
	public void addIArrow(IArrow arrow) {
		this.arrows.add(arrow);
	}

	@Override
	public List<String> getRelatedClassNames(String relationType) {
		List<String> relatedClasses = new ArrayList<>();
		String type;
		for (IArrow arrow : this.arrows) {
			type = arrow.getType();
			if (type.equals(relationType)) {
				relatedClasses.add(arrow.getTo());
			}
		}
		return relatedClasses;
	}

	@Override
	public List<IClass> getRelated() {
		return related;
	}

	@Override
	public void addRelated(IClass related) {
		this.related.add(related);
	}

	@Override
	public boolean getCanLabel() {
		return canLabel;
	}

	@Override
	public void setCanLabel(boolean canLabel) {
		this.canLabel = canLabel;
	}

}
