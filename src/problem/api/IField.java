package problem.api;

public interface IField extends ITraverser {
	
	String getName();
	void setName(String name);
	int getAccess();
	void setAccess(int access);
	String getType();
	void setType(String type);

}
