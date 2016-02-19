package problem.graph.component;

import java.util.ArrayList;
import java.util.List;

import problem.api.IMethod;
import problem.api.IVisitor;

public class UMLMethod implements IMethod {
	private String name;
	private int access;
	private List<String> argTypes;
	private String returnType;

	public UMLMethod(String name, int access, String returnType) {
		this.name = name;
		this.access = access;
		this.argTypes = new ArrayList<>();
		this.returnType = returnType;
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
	public List<String> getArgTypes() {
		return argTypes;
	}

	@Override
	public void addArgType(String argType) {
		this.argTypes.add(argType);
	}

	@Override
	public String getReturnType() {
		return returnType;
	}

	@Override
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

}
