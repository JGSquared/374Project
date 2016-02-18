package problem.patterns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import problem.Constants;
import problem.Helpers;
import problem.Pattern;
import problem.PatternStorage;
import problem.api.CodeMapGetters;
import problem.api.IPatternDetector;

public class CompositePatternDetector implements IPatternDetector {
	private static final String colorString = "yellow";
	private static final String componentLabel = "\\n\\<\\<Component\\>\\>";
	private static final String compositeLabel = "\\n\\<\\<Composite\\>\\>";
	private static final String leafLabel = "\\n\\<\\<Leaf\\>\\>";
	private CodeMapGetters component;
	private HashMap<String, String> classCode;
	private List<HashMap<String, String>> classProperties;

	public CompositePatternDetector() {
	}

	@Override
	public void detectPattern(List<HashMap<String, String>> classProperties,
			HashMap<String, String> classCode) {
		this.classProperties = classProperties;
		this.classCode = classCode;
		CodeMapGetters getter;
		for (HashMap<String, String> classProps : classProperties) {
			getter = new CodeMapGetters(classProps);
			if (checkComposite(getter)) {
				String className = Helpers.getName(getter.getClassName());
				String componentName = Helpers.getName(this.component.getClassName());
				Pattern composite = new Pattern(compositeLabel, colorString, "", className);
				composite.addRelatedClass(new Pattern(componentLabel, colorString, "", componentName));
				PatternStorage.registerPattern(composite);
				// TODO: register pattern instead of labeling
//				labelComponent(componentName);
//				labelComposite(getter.getClassName());
			} else if (checkLeaf(getter)) {
				String className = Helpers.getName(getter.getClassName());
				String componentName = Helpers.getName(this.component.getClassName());
				Pattern leaf = new Pattern(leafLabel, colorString, "", className);
				leaf.addRelatedClass(new Pattern(componentLabel, colorString, "", componentName));
				PatternStorage.registerPattern(leaf);
				// TODO: register pattern instead of labeling
//				labelComponent(componentName);
//				labelLeaf(getter.getClassName());
			}
		}
	}
	
	private boolean checkComposite(CodeMapGetters getter) {
		this.component = getComponent(getter);
		String className = Helpers.getName(getter.getClassName());
		String componentName;
		if (this.component == null || className.equals((componentName = Helpers.getName(this.component.getClassName())))) {
			return false;
		}
//		String componentName = Helpers.getName(this.component.get("className"));
		int methodEqualCount = 0;
		ArrayList<String> methods = getter.getMethodNames();
		String[] argTypesArray;
		String returnType;
		ArrayList<String> argTypes;
		for (String methodName : methods) {
			argTypesArray = getter.getMethodArgTypes(methodName);
			returnType = getter.getMethodReturnType(methodName);
			argTypes = new ArrayList<String>();
			for (int i = 0; i < argTypesArray.length; i++) {
				argTypes.add(Helpers.getName(argTypesArray[i].trim()));
			}
			if (argTypes.contains(componentName) && methodSigInComponent(methodName, argTypesArray, returnType)) {
				methodEqualCount++;	
			}
		}
		
//		for (String s : classProps.keySet()) {
//			if (s.contains("method")) {
//				String method = classProps.get(s);
//				String[] methodProps = method.split(":");
//				
//				String argTypesString = methodProps[2];
//				argTypesString = argTypesString.replaceAll("\\[", "");
//				argTypesString = argTypesString.replaceAll("\\]", "");
//
//				String[] splitArgs = argTypesString.split(",");
//				ArrayList<String> argTypes = new ArrayList<String>();
//				for (int i = 0; i < splitArgs.length; i++) {
//					argTypes.add(Helpers.getName(splitArgs[i].trim()));
//				}
//				if (argTypes.contains(componentName) && methodSigInComponent(method)) {
//					methodEqualCount++;	
//				}
//				
//			}
//		}
		if (methodEqualCount >= 2)
			return true;
		
		return false;
	}
	
	private boolean checkLeaf(CodeMapGetters getter) {
		this.component = getComponent(getter);
		String className = Helpers.getName(getter.getClassName());
		String componentName;
		if (this.component == null || className.equals((componentName = Helpers.getName(this.component.getClassName())))) {
			return false;
		}
//		String componentName = Helpers.getName(this.component.get("className"));
		int methodEqualCount = 0;
		
		ArrayList<String> methods = getter.getMethodNames();
		String[] argTypesArray;
		String returnType;
		ArrayList<String> argTypes;
		for (String methodName : methods) {
			argTypesArray = getter.getMethodArgTypes(methodName);
			returnType = getter.getMethodReturnType(methodName);
			argTypes = new ArrayList<>();
			for (int i = 0; i < argTypesArray.length; i++) {
				argTypes.add(Helpers.getName(argTypesArray[i].trim()));
			}
			if (!argTypes.contains(componentName) && methodSigInComponent(methodName, argTypesArray, returnType)) {
				methodEqualCount++;			
			}
		}
		
//		for (String s : classProps.keySet()) {
//			if (s.contains("method")) {
//				String method = classProps.get(s);
//				String[] methodProps = method.split(":");
//				
//				String argTypesString = methodProps[2];
//				argTypesString = argTypesString.replaceAll("\\[", "");
//				argTypesString = argTypesString.replaceAll("\\]", "");
//
//				String[] splitArgs = argTypesString.split(",");
//				ArrayList<String> argTypes = new ArrayList<String>();
//				for (int i = 0; i < splitArgs.length; i++) {
//					argTypes.add(Helpers.getName(splitArgs[i].trim()));
//				}
//				if (!argTypes.contains(componentName) && methodSigInComponent(method)) {
//					methodEqualCount++;			
//				}
//			}
//		}
		if (methodEqualCount > 0)
			return true;
		
		return false;
	}
	
	private boolean methodSigInComponent(String methodName, String[] argTypesArray, String returnType) {
//		method = method.substring(method.indexOf(":"));
		
		ArrayList<String> componentMethods = this.component.getMethodNames();
		String[] cArgtypes;
		String cReturnType;
		for (String cMethodName : componentMethods) {
			cArgtypes = this.component.getMethodArgTypes(cMethodName);
			cReturnType = this.component.getMethodReturnType(cMethodName);
			if (cMethodName.equals(methodName) && cArgtypes.equals(argTypesArray) && cReturnType.equals(returnType)) {
				return true;
			}
		}
//		for (String s : this.component.keySet()) {
//			if (s.contains("method")) {
//				String componentMethod = this.component.get(s);
//				componentMethod = componentMethod.substring(componentMethod.indexOf(":"));
//				if (componentMethod.equals(method)) {
//					return true;
//				}
//			}
//		}
		return false;
	}
	
	private CodeMapGetters getComponent(CodeMapGetters getter) {
		int methodCount = 0;
		// Is it an abstract class or interface
		String componentName = Helpers.getName(getter.getClassName());
		int access = getter.getAccess();
		//1537 Interface 1057 Abstract Class
		if (access == 1537 || access == 1057) {
			// 2 Methods that take this component as an argument
			ArrayList<String> methods = getter.getMethodNames();
			String[] argTypesArray;
			ArrayList<String> argTypes;
			for (String methodName : methods) {
				argTypesArray = getter.getMethodArgTypes(methodName);
				argTypes = new ArrayList<>();
				for (int i = 0; i < argTypesArray.length; i++) {
					argTypes.add(Helpers.getName(argTypesArray[i].trim()));
				}
				if (argTypes.contains(componentName)) {
					methodCount++;
				}
				
				if (methodCount >= 2) {
					return getter;
				}
			}
			
			
//			for (String s : getter.keySet()) {
//				if (s.contains("method")) {
//					String method = getter.get(s);
//					String[] methodProps = method.split(":");
//					
//					String argTypesString = methodProps[2];
//					argTypesString = argTypesString.replaceAll("\\[", "");
//					argTypesString = argTypesString.replaceAll("\\]", "");
//
//					String[] splitArgs = argTypesString.split(",");
//					ArrayList<String> argTypes = new ArrayList<String>();
//					for (int i = 0; i < splitArgs.length; i++) {
//						argTypes.add(Helpers.getName(splitArgs[i].trim()));
//					}
//					if (argTypes.contains(componentName)) {
//						methodCount++;			
//					}
//					
//					if (methodCount >= 2) {
//						return getter;
//					}
//				}
//			}
		}
		// If whatever I do extend/ implements is not not
		// If I extend, find in hashmap, call getComponent on that class
		String extendsName = getter.getClassExtends();
		if (extendsName != null) {
			extendsName = Helpers.getName(extendsName);
			for (int i = 0; i < this.classProperties.size(); i++) {
				HashMap<String, String> nextClassProps = this.classProperties.get(i);
				CodeMapGetters newGetter = new CodeMapGetters(nextClassProps);
				String extender = Helpers.getName(newGetter.getClassName());
				if (extendsName.equals(extender)) {
					CodeMapGetters component = getComponent(newGetter);
					if (component != null) {
						return component;
					}
				}
			}
		}
		
		String implementsName;
		String[] interFaces = getter.getClassImplements();
		if (interFaces.length == 1) {
			implementsName = Helpers.getName(interFaces[0]);
			for (int i = 0; i < this.classProperties.size(); i++) {
				HashMap<String, String> nextClassProps = this.classProperties.get(i);
				CodeMapGetters newGetter = new CodeMapGetters(nextClassProps);
				String implementer = Helpers.getName(newGetter.getClassName());
				if (implementsName.equals(implementer)) {
					CodeMapGetters component = getComponent(newGetter);
					if (component != null) {
						return component;
					}
				}
			}
		}
		return null;
	}
	
	private void labelComponent(String otherClass) {
		// Given a className, finds that class in the
		// StringBuilder and labels it as a component
		String className = Helpers.getName(otherClass);
		if (this.classCode.get(className) == null) {
			return;
		}
		StringBuilder sb = new StringBuilder(this.classCode.get(className) );
		int colorOffset = sb.indexOf(Constants.COLOR_OFFSET);
		if (colorOffset == -1) {
			return;
		}
		sb.replace(colorOffset,
				colorOffset + Constants.COLOR_OFFSET.length(), colorString);
		int labelOffset = sb.indexOf(Constants.LABEL_OFFSET);
		sb.replace(labelOffset,
				labelOffset + Constants.LABEL_OFFSET.length(),
				componentLabel);
		this.classCode.put(className, sb.toString());
			
	}
	
	private void labelComposite(String className) {
		// Given a className, labels that class as a composite
		String name = Helpers.getName(className);
		String code = this.classCode.get(name);
		if (code == null) {
			return;
		}
		StringBuilder sb = new StringBuilder(code);
		int colorOffset = sb.indexOf(Constants.COLOR_OFFSET);
		if (colorOffset == -1) {
			return;
		}
		sb.replace(colorOffset,
				colorOffset + Constants.COLOR_OFFSET.length(), colorString);
		int labelOffset = sb.indexOf(Constants.LABEL_OFFSET);
		sb.replace(labelOffset,
				labelOffset + Constants.LABEL_OFFSET.length(),
				compositeLabel);
		this.classCode.put(name, sb.toString());
	}
	
	private void labelLeaf(String className) {
		String name = Helpers.getName(className);
		String code = this.classCode.get(name);
		
		if (code == null) {
			return;
		}
		StringBuilder sb = new StringBuilder(code);
		int colorOffset = sb.indexOf(Constants.COLOR_OFFSET);
		if (colorOffset == -1) {
			return;
		}
		sb.replace(colorOffset,
				colorOffset + Constants.COLOR_OFFSET.length(), colorString);
		int labelOffset = sb.indexOf(Constants.LABEL_OFFSET);
		sb.replace(labelOffset,
				labelOffset + Constants.LABEL_OFFSET.length(),
				leafLabel);
		this.classCode.put(name, sb.toString());
	}

}
