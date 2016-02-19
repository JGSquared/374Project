package problem.patterns;

import java.util.List;

import org.objectweb.asm.Opcodes;

import problem.ClassStorage;
import problem.Helpers;
import problem.api.IClass;
import problem.api.IField;
import problem.api.IMethod;
import problem.api.IPatternDetector;

public class SingletonPatternDetector implements IPatternDetector {
	private static final String colorString = "blue";
	private static final String patternLabel = "Singleton";
	private List<IClass> classes;

	public SingletonPatternDetector() {
	}

	@Override
	public void detectPattern() {
//		CodeMapGetters getter;
		this.classes = ClassStorage.getClasses();
		for (IClass c : classes) {
//			getter = new CodeMapGetters(parsedCode);
			if (checkStatus(c) && checkForGetInstance(c) && checkForPrivateConstructor(c)) {
				String className = Helpers.getName(c.getClassName());
//				Pattern singleton = new Pattern(patternLabel, colorString, "", className);
//				PatternStorage.addIClass(singleton);
				c.setColor(colorString);
				c.setPatternLabel(patternLabel);
//				StringBuilder sb = new StringBuilder(classCode.get(className));
//				// int fromIndex = Helpers.getClassDeclarationIndex(className,
//				// sb);
//				int colorOffset = sb.indexOf(Constants.COLOR_OFFSET);
//				sb.replace(colorOffset, colorOffset + Constants.COLOR_OFFSET.length(), colorString);
//				int labelOffset = sb.indexOf(Constants.LABEL_OFFSET);
//				sb.replace(labelOffset, labelOffset + Constants.LABEL_OFFSET.length(), patternLabel);
//				classCode.put(className, sb.toString());
			}
		}
	}

	public boolean checkStatus(IClass c) {
		String className = Helpers.getName(c.getClassName());
		int privateOp = Opcodes.ACC_PRIVATE;
		int staticOp = Opcodes.ACC_STATIC;
		int volatileOp = Opcodes.ACC_VOLATILE;

		List<IField> fields = c.getFields();
		String fieldType;
		int access;
		for (IField field : fields) {
			fieldType = Helpers.getName(field.getType());
			access = field.getAccess();
			if (fieldType.equals(className)
					&& ((privateOp + staticOp == access) || (privateOp + staticOp + volatileOp == access))) {
				return true;
			}
		}

		// for (String key : parsedCode.keySet()) {
		// if (key.contains("field")) {
		// items = parsedCode.get(key).split(":");
		// String fieldType = items[2];
		// int access = Integer.parseInt(items[0]);
		// if (Helpers.getName(fieldType).equals(
		// Helpers.getName(className))) {
		// if ((privateOp + staticOp == access)
		// || (privateOp + staticOp + volatileOp == access)) {
		// return true;
		// }
		// }
		// }
		// }

		return false;
	}

	public boolean checkForGetInstance(IClass c) {
		String className = Helpers.getName(c.getClassName());
		int publicOp = Opcodes.ACC_PUBLIC;
		int staticOp = Opcodes.ACC_STATIC;
		int syncOp = Opcodes.ACC_SYNCHRONIZED;

		List<IMethod> methods = c.getMethods();
		String returnType;
		int access;
		for (IMethod method : methods) {
			returnType = Helpers.getName(method.getReturnType());
			access = method.getAccess();
			if (returnType.equals(className)
					&& ((publicOp + staticOp == access) || (publicOp + staticOp + syncOp == access))) {
				return true;
			}
		}

//		for (String key : parsedCode.keySet()) {
//			if (key.contains("method")) {
//				items = parsedCode.get(key).split(":");
//				int access = Integer.parseInt(items[0]);
//				String returnType = Helpers.getName(items[3]);
//				if (returnType.equals(Helpers.getName(className))) {
//					if ((publicOp + staticOp == access) || (publicOp + staticOp + syncOp == access)) {
//						return true;
//					}
//				}
//
//			}
//		}
		return false;
	}

	public boolean checkForPrivateConstructor(IClass c) {
		int privateOp = Opcodes.ACC_PRIVATE;
		int countedConstructors = 0;
		int countedPrivateConstructors = 0;
		
		List<IMethod> methods = c.getMethods();
		String name;
		int access;
		for (IMethod method : methods) {
			name = method.getName();
			access = method.getAccess();
			if (name.equals("<init>")) {
				countedConstructors++;
				if (privateOp == access) {
					countedPrivateConstructors++;
				}
			}
		}
		
//		for (String key : parsedCode.keySet()) {
//			if (key.contains("method")) {
//				items = parsedCode.get(key).split(":");
//				int access = Integer.parseInt(items[0]);
//				String name = items[1];
//				if (name.equals("<init>")) {
//					countedConstructors++;
//				}
//				if (privateOp == access && name.equals("<init>")) {
//					countedPrivateConstructors++;
//				}
//			}
//		}
		if (countedConstructors == countedPrivateConstructors) {
			return true;
		}
		return false;
	}
}