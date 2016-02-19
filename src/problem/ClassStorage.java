package problem;

import java.util.ArrayList;
import java.util.List;

import problem.api.IClass;

public class ClassStorage {
	private volatile static ClassStorage uniqueController;
	private static List<IClass> classes = new ArrayList<IClass>();
	
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
	
	public static void addIClass(IClass iClass) {
		classes.add(iClass);
	}
	
	public static List<IClass> getClasses() {
		return classes;
	}
}
