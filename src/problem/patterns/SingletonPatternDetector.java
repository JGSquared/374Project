package problem.patterns;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.objectweb.asm.Opcodes;

import com.sun.xml.internal.bind.v2.runtime.Name;

import problem.Constants;
import problem.Helpers;
import problem.Pattern;
import problem.PatternStorage;
import problem.api.CodeMapGetters;
import problem.api.IPatternDetector;

public class SingletonPatternDetector implements IPatternDetector {
	private static final String colorString = "blue";
	private static final String patternLabel = "\\n\\<\\<Singleton\\>\\>";

	public SingletonPatternDetector() {
	}

	@Override
	public void detectPattern(List<HashMap<String, String>> classProperties, HashMap<String, String> classCode) {
		CodeMapGetters getter;
		for (HashMap<String, String> parsedCode : classProperties) {
			getter = new CodeMapGetters(parsedCode);
			if (checkStatus(getter) && checkForGetInstance(getter) && checkForPrivateConstructor(getter)) {
				// TODO: Register pattern instead of labeling
				String className = Helpers.getName(parsedCode.get("className"));
				Pattern singleton = new Pattern(patternLabel, colorString, "", className);
				PatternStorage.addIClass(singleton);
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

	public boolean checkStatus(CodeMapGetters getter) {
		String[] items;
		String className = Helpers.getName(getter.getClassName());
		int privateOp = Opcodes.ACC_PRIVATE;
		int staticOp = Opcodes.ACC_STATIC;
		int volatileOp = Opcodes.ACC_VOLATILE;

		ArrayList<String> fields = getter.getFieldNames();
		String fieldType;
		int access;
		for (String fieldName : fields) {
			fieldType = Helpers.getName(getter.getFieldType(fieldName));
			access = getter.getFieldAccess(fieldName);
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

	public boolean checkForGetInstance(CodeMapGetters getter) {
		String[] items;
		String className = Helpers.getName(getter.getClassName());
		int publicOp = Opcodes.ACC_PUBLIC;
		int staticOp = Opcodes.ACC_STATIC;
		int syncOp = Opcodes.ACC_SYNCHRONIZED;

		ArrayList<String> methods = getter.getMethodNames();
		String returnType;
		int access;
		for (String methodName : methods) {
			returnType = Helpers.getName(getter.getMethodReturnType(methodName));
			access = getter.getMethodAccess(methodName);
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

	public boolean checkForPrivateConstructor(CodeMapGetters getter) {
		String[] items;
		int privateOp = Opcodes.ACC_PRIVATE;
		int countedConstructors = 0;
		int countedPrivateConstructors = 0;
		
		ArrayList<String> methods = getter.getMethodNames();
		int access;
		for (String methodName : methods) {
			access = getter.getMethodAccess(methodName);
			if (methodName.equals("<init>")) {
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