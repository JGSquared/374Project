package problem.api;

import java.util.List;

public interface IClass extends ITraverser {
	String getColor();
	void setColor(String color);
	String getClassName();
	void setClassName(String className);
	int getAccess();
	void setAccess(int access);
	String getPatternLabel();
	void setPatternLabel(String patternLabel);
	List<IField> getFields();
	void addIField(IField field);
	List<IMethod> getMethods();
	void addIMethod(IMethod method);
	List<IArrow> getArrows();
	void addIArrow(IArrow arrow);
	List<String> getRelatedClassNames(String relationType);
	
}
