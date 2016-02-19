package problem.api;

import java.util.List;

public interface IMethod extends ITraverser {
	
	String getName();
	void setName(String name);
	int getAccess();
	void setAccess(int access);
	List<String> getArgTypes();
	void addArgType(String argType);
	String getReturnType();
	void setReturnType(String returnType);

}
