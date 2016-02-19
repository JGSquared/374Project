package problem;

import java.util.ArrayList;
import java.util.List;

import problem.api.IArrow;
import problem.api.IClass;

public class ClassStorage {
	private volatile static ClassStorage uniqueController;
	private List<IClass> classes = new ArrayList<IClass>();
	
	private ClassStorage() {
		
	}
	
	public static ClassStorage getInstance() {
		if (uniqueController == null) {
			synchronized (ClassStorage.class) {
				if (uniqueController == null) {
					uniqueController = new ClassStorage();
				}
			}
		}
		return uniqueController;
	}
	
	public void addIClass(IClass iClass) {
		classes.add(iClass);
	}
	
	public List<IClass> getClasses() {
		return classes;
	}
	
	public void setClassesToLabel(List<String> classNames) {
		for (int i = 0; i < classNames.size(); i++) {
			classNames.set(i, Helpers.getName(classNames.get(i)));
		}
		falsifyAllFlags();
		for (IClass c : classes) {
			if (classNames.contains(Helpers.getName(c.getClassName()))) {
				c.setCanLabel(true);
				for (IArrow a : c.getArrows()) {
					a.setCanLabel(true);
				}
			}
		}
	}
	
	private void falsifyAllFlags() {
		for (IClass c : classes) {
			c.setCanLabel(false);
			for (IArrow a : c.getArrows()) {
				a.setCanLabel(false);
			}
		}
	}
	
}
