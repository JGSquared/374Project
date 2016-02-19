package problem.graph.component;

import problem.api.IField;
import problem.api.IVisitor;

public class UMLField implements IField {
	private String name;
	private int access;
	private String type;

	public UMLField(String name, int access, String type) {
		this.name = name;
		this.access = access;
		this.type = type;
	}

	@Override
	public void accept(IVisitor v) {
		v.visit(this);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
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
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

}
