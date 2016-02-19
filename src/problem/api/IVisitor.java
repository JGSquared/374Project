package problem.api;

public interface IVisitor {
	void preVisit(ITraverser c);
	void visit(ITraverser c);
	void postVisit(ITraverser c);
	
	void addPreVisit(Class klass, ICommand command);
	void addVisit(Class partOfInterest, ICommand command);
	void addPostVisit(Class partOfInterest, ICommand command);
}
