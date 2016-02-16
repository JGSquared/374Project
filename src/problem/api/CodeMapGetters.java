package problem.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author greenjm
 * This class provides several methods for extracting any specific field
 * from the HashMap created during the ASM Loader phase.
 *
 */

public class CodeMapGetters {
	private HashMap<String, String> items;

	public CodeMapGetters(HashMap<String, String> items) {
		this.items = items;
	}
	
	// Methods for getting basic class properties
	public String getClassName() {
		/**
		 * @return full path to the class (i.e. String will be java.util.String or java/util/String)
		 */
		return items.get("className");
	}
	
	public int getAccess() {
		/**
		 * @return Integer Opcode for the class access value (i.e. Opcodes.ACC_PUBLIC)
		 */
		return Integer.parseInt(items.get("access"));
	}
	
	public String getClassExtends() {
		/**
		 * @return full path to the class that this class extends, or an empty String if none.
		 */
		return items.get("extends");
	}
	
	public String[] getClassImplements() {
		/**
		 * @return String array containing the path to every class the original class implements
		 */
		String interfacesString = items.get("implements");
		if (interfacesString.equals("[]")) {
			return new String[0];
		}
		return interfacesString.substring(1,
				interfacesString.length() - 1).split(",");
	}
	
	// Methods for getting class fields and their properties
	private String[] findFieldProperties(String fieldName) {
		/**
		 * Internal method to avoid duplicate code. Finds fieldName in HashMap and returns its properties
		 * @param fieldName: Name of field in class to find
		 * @return String array of the given field's properties, or an empty array if field not found.
		 */
		String field;
		String[] fieldProperties;
		String name;
		for (String s : items.keySet()) {
			if (s.contains("field")) {
				field = items.get(s);
				fieldProperties = field.split(":");
				name = fieldProperties[1];
				if (name.equals(fieldName))
					return fieldProperties;
			}
		}
		return new String[0];
	}
	
	public ArrayList<String> getFieldNames() {
		/**
		 * @return An ArrayList containing the name of every field within this class (can be used as params to other getters)
		 */
		ArrayList<String> fields = new ArrayList<>();
		String field;
		String[] fieldProperties;
		String name;
		for (String s : items.keySet()) {
			if (s.contains("field")) {
				field = items.get(s);
				fieldProperties = field.split(":");
				name = fieldProperties[1];
				fields.add(name);
			}
		}
		return fields;
	}
	
	public int getFieldAccess(String fieldName) {
		/**
		 * @param fieldName: Name of a field in the class (should be a value within the ArrayList returned by getFieldNames())
		 * @return Integer Opcode for the given field's access value, or -1 if the field is not found.
		 */
		int access = -1;
		String[] fieldProperties = findFieldProperties(fieldName);
		if (fieldProperties.length > 0) {
			access = Integer.parseInt(fieldProperties[0]);
		}
		return access;
	}
	
	public String getFieldType(String fieldName) {
		/**
		 * @param fieldName: Name of a field in the class (should be a value within the ArrayList returned by getFieldNames())
		 * @return full path of the given field's type, or an empty String if field not found
		 */
		String type = "";
		String[] fieldProperties = findFieldProperties(fieldName);
		if (fieldProperties.length > 0) {
			type = fieldProperties[2];
		}
		return type;
	}
	
	public String getFieldSignature(String fieldName) {
		/**
		 * @param fieldName: Name of a field in the class (should be a value within the ArrayList returned by getFieldNames())
		 * @return Signature of the given field (check README for more information)
		 */
		String signature = "";
		String[] fieldProperties = findFieldProperties(fieldName);
		if (fieldProperties.length >= 4) {
			signature = fieldProperties[3];
		}
		return signature;
	}
	
	// Methods for getting class methods and their properties
	private String[] findMethodProperties(String methodName) {
		/**
		 * Internal method to avoid duplicate code. Finds methodName in HashMap and returns its properties
		 * @param methodName: Name of method in class to find
		 * @return String array of the given method's properties, or an empty array if method not found.
		 */
		String method;
		String[] methodProperties;
		String name;
		for (String s : items.keySet()) {
			if (s.contains("method")) {
				method = items.get(s);
				methodProperties = method.split(":");
				name = methodProperties[1];
				if (name.equals(methodName))
					return methodProperties;
			}
		}
		return new String[0];
	}
	
	public ArrayList<String> getMethodNames() {
		/**
		 * @return An ArrayList containing the name of every method within this class (can be used as params to other getters)
		 */
		ArrayList<String> methods = new ArrayList<>();
		String method;
		String[] methodProperties;
		String name;
		for (String s : items.keySet()) {
			if (s.contains("method")) {
				method = items.get(s);
				methodProperties = method.split(":");
				name = methodProperties[1];
				methods.add(name);
			}
		}
		return methods;
	}
	
	public int getMethodAccess(String methodName) {
		/**
		 * @param methodName: Name of a method in the class (should be a value within the ArrayList returned by getMethodNames())
		 * @return Integer Opcode for the given method's access value, or -1 if the method is not found.
		 */
		int access = -1;
		String[] methodProperties = findMethodProperties(methodName);
		if (methodProperties.length > 0) {
			access = Integer.parseInt(methodProperties[0]);
		}
		return access;
	}
	
	public String[] getMethodArgTypes(String methodName) {
		/**
		 * @param methodName: Name of a method in the class (should be a value within the ArrayList returned by getMethodNames())
		 * @return String array containing the full path of each argument's type in the given method, or null if not found.
		 */
		String[] argTypes = null;
		String[] methodProperties = findMethodProperties(methodName);
		if (methodProperties.length > 0) {
			String argTypesString = methodProperties[2];
			argTypesString = argTypesString.replaceAll("\\[", "");
			argTypesString = argTypesString.replaceAll("\\]", "");
			argTypes = argTypesString.split(",");
		}
		return argTypes;
	}
	
	public String getMethodReturnType(String methodName) {
		/**
		 * @param methodName: Name of a method in the class (should be a value within the ArrayList returned by getMethodNames())
		 * @return Full path of the given method's return type, or an empty String if method is not found
		 */
		String returnType = "";
		String[] methodProperties = findMethodProperties(methodName);
		if (methodProperties.length > 0) {
			returnType = methodProperties[3];
		}
		return returnType;
	}
	
	// Methods for Uses and Associates
	public ArrayList<String> getClassUses() {
		/**
		 * @return ArrayList containing the full path of each class the current class uses
		 */
		ArrayList<String> uses = new ArrayList<>();
		String usesClass;
		for (String s : items.keySet()) {
			if (s.contains("uses")) {
				usesClass = items.get(s);
				uses.add(usesClass);
			}
		}
		return uses;
	}
	
	public ArrayList<String> getClassAssociates() {
		/**
		 * @return ArrayList containing the full path of each class the current class associates with
		 */
		ArrayList<String> associates = new ArrayList<>();
		String associatesClass;
		for (String s : items.keySet()) {
			if (s.contains("associated")) {
				associatesClass = items.get(s);
				associates.add(associatesClass);
			}
		}
		return associates;
	}
	
	// Methods for Sequence nodes
	public ArrayList<String> getSequenceNodeKeys() {
		/**
		 * @return ArrayList containing each key that maps to a sequence node
		 */
		String keyName = "sequenceNode";
		ArrayList<String> nodeKeys = new ArrayList<>();
		for (String s : items.keySet()) {
			if (s.contains(keyName)) {
				nodeKeys.add(s);
			}
		}
		return nodeKeys;
	}
	
	public String getSequenceNodeValue(String key) {
		/**
		 * @param key: A key to a sequence node in the HashMap (should come from a value in getSequenceNodeKeys())
		 * @return The full path of the given sequence node, or an empty String if not found
		 */
		String nodeValue = "";
		if (items.get(key) != null) {
			nodeValue = items.get(key);
		}
		return nodeValue;
	}
	
	// Methods for Sequence methods
	public ArrayList<String> getSequenceMethodKeys() {
		/**
		 * @return ArrayList containing each key that maps to a sequence method
		 */
		String keyName = "sequenceMethod";
		ArrayList<String> methodKeys = new ArrayList<>();
		for (String s : items.keySet()) {
			if (s.contains(keyName)) {
				methodKeys.add(s);
			}
		}
		return methodKeys;
	}
	
	public String getSequenceMethodCaller(String key) {
		/**
		 * @param key: a key to a sequence method in the HashMap (should come from getSequenceMethodKeys())
		 * @return The full path of the given method's caller, or an empty String if not found
		 */
		String caller = "";
		if (items.get(key) != null) {
			String methodValue = items.get(key);
			String[] methodParams = methodValue.split(":");
			caller = methodParams[0];
		}
		return caller;
	}
	
	public String getSequenceMethodCallee(String key) {
		/**
		 * @param key: a key to a sequence method in the HashMap (should come from getSequenceMethodKeys())
		 * @return The full path of the given method's callee, or an empty String if not found
		 */
		String callee = "";
		if (items.get(key) != null) {
			String methodValue = items.get(key);
			String[] methodParams = methodValue.split(":");
			callee = methodParams[1];
		}
		return callee;
	}
	
	public String getSequenceMethodName(String key) {
		/**
		 * @param key: a key to a sequence method in the HashMap (should come from getSequenceMethodKeys())
		 * @return The name of the given method, or an empty String if not found
		 */
		String method = "";
		if (items.get(key) != null) {
			String methodValue = items.get(key);
			String[] methodParams = methodValue.split(":");
			method = methodParams[2];
		}
		return method;
	}
	
	public String[] getSequenceMethodArgs(String key) {
		/**
		 * @param key: a key to a sequence method in the HashMap (should come from getSequenceMethodKeys())
		 * @return An array containing the full path of each argument type for the given method, or an empty array if not found
		 */
		String[] ifEmpty = new String[0];
		if (items.get(key) != null) {
			String methodValue = items.get(key);
			String[] methodParams = methodValue.split(":");
			String args = methodParams[3]
					.replaceAll("\\[", "")
					.replaceAll("\\]", "");
			if (args.equals(""))
				return ifEmpty;
			String[] types = args.split(",");
			return types;
		}
		return ifEmpty;
	}

}
