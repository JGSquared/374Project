package problem.patterns;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;

import org.objectweb.asm.Opcodes;

import com.sun.xml.internal.bind.v2.runtime.Name;

import problem.Constants;
import problem.Helpers;
import problem.api.IPatternDetector;

public class SingletonPatternDetector implements IPatternDetector {
	private static final String colorString = "blue";
	private static final String patternLabel = "\\n\\<\\<Singleton\\>\\>";

	public SingletonPatternDetector() {
	}

	@Override
	public void detectPattern(List<HashMap<String, String>> classCode, StringBuilder sb) {
		for (HashMap<String, String> parsedCode : classCode) {
			if (checkStatus(parsedCode) && checkForGetInstance(parsedCode)
					&& checkForPrivateConstructor(parsedCode)) {
				String className = Helpers.getName(parsedCode.get("className"), "/");
				int fromIndex = Helpers.getClassDeclarationIndex(className, sb);
				int colorOffset = sb.indexOf(Constants.COLOR_OFFSET, fromIndex);
				sb.replace(colorOffset, colorOffset + Constants.COLOR_OFFSET.length(), colorString);
				int labelOffset = sb.indexOf(Constants.LABEL_OFFSET, fromIndex);
				sb.replace(labelOffset, labelOffset + Constants.LABEL_OFFSET.length(), patternLabel);
			}
		}
	}

	public boolean checkStatus(HashMap<String, String> parsedCode) {
		String[] items;
		String className = parsedCode.get("className");
		int privateOp = Opcodes.ACC_PRIVATE;
		int staticOp = Opcodes.ACC_STATIC;
		int volatileOp = Opcodes.ACC_VOLATILE;

		for (String key : parsedCode.keySet()) {
			if (key.contains("field")) {
				items = parsedCode.get(key).split(":");
				String fieldType = items[2];
				int access = Integer.parseInt(items[0]);
				if (Helpers.getName(fieldType, "\\.").equals(
						Helpers.getName(className, "/"))) {
					if ((privateOp + staticOp == access)
							|| (privateOp + staticOp + volatileOp == access)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean checkForGetInstance(HashMap<String, String> parsedCode) {
		String[] items;
		String className = parsedCode.get("className");
		int publicOp = Opcodes.ACC_PUBLIC;
		int staticOp = Opcodes.ACC_STATIC;
		int syncOp = Opcodes.ACC_SYNCHRONIZED;
		for (String key : parsedCode.keySet()) {
			if (key.contains("method")) {
				items = parsedCode.get(key).split(":");
				int access = Integer.parseInt(items[0]);
				String returnType = Helpers.getName(items[3], "\\.");
				if (returnType.equals(Helpers.getName(className, "/"))) {
					if ((publicOp + staticOp == access)
							|| (publicOp + staticOp + syncOp == access)) {
						return true;
					}
				}

			}
		}
		return false;
	}

	public boolean checkForPrivateConstructor(HashMap<String, String> parsedCode) {
		String[] items;
		int privateOp = Opcodes.ACC_PRIVATE;
		int countedConstructors = 0;
		int countedPrivateConstructors = 0;
		for (String key : parsedCode.keySet()) {
			if (key.contains("method")) {
				items = parsedCode.get(key).split(":");
				int access = Integer.parseInt(items[0]);
				String name = items[1];
				if (name.equals("<init>")) {
					countedConstructors++;
				}
				if (privateOp == access && name.equals("<init>")) {
					countedPrivateConstructors++;
				}
			}
		}
		if (countedConstructors == countedPrivateConstructors) {
			return true;
		}
		return false;
	}
}