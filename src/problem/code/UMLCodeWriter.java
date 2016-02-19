package problem.code;

import java.io.IOException;
import java.io.OutputStream;

import problem.Helpers;
import problem.ClassStorage;
import problem.api.IClass;
import problem.api.ICommand;
import problem.api.ITraverser;
import problem.graph.component.UMLArrow;
import problem.graph.component.UMLClass;
import problem.graph.component.UMLField;
import problem.graph.component.UMLMethod;

public class UMLCodeWriter extends AbstractCodeWriter {
	private final OutputStream out;

	public UMLCodeWriter(OutputStream out) {
		this.out = out;
	}

	@Override
	public void generateGraph() {
		addPreVisits();
		addVisits();
		addPostVisits();
		
		write("digraph G{rankdir=BT;");
		
		for (IClass c : ClassStorage.getInstance().getClasses()) {
			c.accept(this);
		}
		
		write("}");
	}
	
	private void write(String s) {
		try {
			this.out.write(s.getBytes());
		}
		catch(IOException e) {
			throw new RuntimeException(s);
		}
	}
	
	private void addPreVisits() {
		this.addPreVisit(UMLClass.class, new ICommand() {
			
			@Override
			public void execute(ITraverser traverser) {
				UMLClass c = (UMLClass) traverser;
				String s = Helpers.getName(c.getClassName()) + "[ shape=\"record\", color=\"";
				s += c.getCanLabel() ? c.getColor() : "";
				s += "\", label=\"{";
				if (c.getAccess() == 1537) {
					s += "\\<\\<interface\\>\\>\\n";
				}
				s += Helpers.getName(c.getClassName());
				if (c.getCanLabel() && !c.getPatternLabel().equals("")) {
					s += "\\n\\<\\<" + c.getPatternLabel() + "\\>\\>";
				}
				write(s);
			}
		});
	}
	
	private void addVisits() {
		this.addVisit(UMLClass.class, new ICommand() {
			
			@Override
			public void execute(ITraverser traverser) {
				write("|");
			}
		});
		this.addVisit(UMLField.class, new ICommand() {
			
			@Override
			public void execute(ITraverser traverser) {
				UMLField f = (UMLField) traverser;
				if (f.getName().equals("<init>")) {
					return;
				}
				String s = Helpers.getAccessSymbol(f.getAccess()) + " " + f.getName() + " : " + Helpers.getName(f.getType()) + "\\l";
				write(s);
			}
		});
		this.addVisit(UMLMethod.class, new ICommand() {
			
			@Override
			public void execute(ITraverser traverser) {
				UMLMethod m = (UMLMethod) traverser;
				if (m.getName().equals("<init>")) {
					return;
				}
				String s = Helpers.getAccessSymbol(m.getAccess()) + " " + m.getName() + m.getArgTypes().toString().replaceAll("\\[", "(").replaceAll("\\]", ")")
						+ " : " + Helpers.getName(m.getReturnType()) + "\\l";
				write(s);
			}
		});
		this.addVisit(UMLArrow.class, new ICommand() {
			
			@Override
			public void execute(ITraverser traverser) {
				UMLArrow a = (UMLArrow) traverser;
				String s = Helpers.getName(a.getFrom()) + " -> " + Helpers.getName(a.getTo());
				s += " [arrowhead=\"" + a.getArrowhead() + "\", style=\"" + a.getStyle() + "\", label=\"";
				s += a.getCanLabel() ? a.getLabel() : "";
				s += "\", color=\"";
				s += a.getCanLabel() ? a.getColor() : "";
				s += "\"";
				s += "];";
				write(s);
			}
		});
	}
	
	private void addPostVisits() {
		this.addPostVisit(UMLClass.class, new ICommand() {
			
			@Override
			public void execute(ITraverser traverser) {
				write("}\"];");
			}
		});		
	}

	public OutputStream getStream() {
		return out;
	}
}
