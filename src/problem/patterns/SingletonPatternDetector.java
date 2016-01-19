package problem.patterns;

import java.util.HashMap;

import org.objectweb.asm.Opcodes;

import com.sun.xml.internal.bind.v2.runtime.Name;

import problem.Helpers;
import problem.api.IPatternDetector;

public class SingletonPatternDetector implements IPatternDetector {

	private HashMap<String, String> parsedCode;
	private String className;

	public SingletonPatternDetector(HashMap<String, String> parsedCode) {
		this.parsedCode = parsedCode;
		this.className = parsedCode.get("className");
	}

	@Override
	public boolean isPattern() {
		if (checkStatus() && checkForGetInstance()
				&& checkForPrivateConstructor()) {
			return true;
		}
		return false;
	}

	public boolean checkStatus() {
		String[] items;
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

	public boolean checkForGetInstance() {
		String[] items;
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

	public boolean checkForPrivateConstructor() {
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