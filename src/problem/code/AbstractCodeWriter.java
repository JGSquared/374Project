package problem.code;

import java.util.HashMap;
import java.util.Map;

import problem.api.ICommand;
import problem.api.ITraverser;
import problem.api.IVisitor;
import problem.api.IWriter;

public abstract class AbstractCodeWriter implements IWriter {
	private Map<Class, ICommand> preVisitClassToCommand = new HashMap<Class, ICommand>();
	private Map<Class, ICommand> visitClassToCommand = new HashMap<Class, ICommand>();
	private Map<Class, ICommand> postVisitClassToCommand = new HashMap<Class, ICommand>();

	public AbstractCodeWriter() {
	}

	@Override
	public void preVisit(ITraverser c) {
		Class realClass = c.getClass();
		ICommand command = preVisitClassToCommand.get(realClass);
		command.execute(c);
	}

	@Override
	public void visit(ITraverser c) {
		Class realClass = c.getClass();
		ICommand command = visitClassToCommand.get(realClass);
		command.execute(c);
	}

	@Override
	public void postVisit(ITraverser c) {
		Class realClass = c.getClass();
		ICommand command = postVisitClassToCommand.get(realClass);
		command.execute(c);
	}

	@Override
	public void addPreVisit(Class klass, ICommand command) {
		preVisitClassToCommand.put(klass, command);
	}

	@Override
	public void addVisit(Class klass, ICommand command) {
		visitClassToCommand.put(klass, command);
	}

	@Override
	public void addPostVisit(Class klass, ICommand command) {
		postVisitClassToCommand.put(klass, command);
	}

}
