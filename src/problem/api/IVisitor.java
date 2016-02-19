package problem.api;

public interface IVisitor {
	void preVisit(ITraverser c);
	void visit(ITraverser c);
	void postVisit(ITraverser c);
	
	void addPreVisit(Class klass, ICommand command);
	void addVisit(Class klass, ICommand command);
	void addPostVisit(Class klass, ICommand command);
}
