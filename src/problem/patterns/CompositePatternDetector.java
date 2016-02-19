package problem.patterns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import problem.ClassStorage;
import problem.Constants;
import problem.Helpers;
import problem.api.CodeMapGetters;
import problem.api.IClass;
import problem.api.IMethod;
import problem.api.IPatternDetector;

public class CompositePatternDetector implements IPatternDetector {
	private static final String colorString = "yellow";
	private static final String componentLabel = "Component";
	private static final String compositeLabel = "Composite";
	private static final String leafLabel = "Leaf";
	private IClass component;
	private List<IClass> classes;
//	private HashMap<String, String> classCode;
//	private List<HashMap<String, String>> classProperties;

	public CompositePatternDetector() {
	}

	@Override
	public void detectPattern() {
//		this.classProperties = classProperties;
//		this.classCode = classCode;
		classes = ClassStorage.getClasses();
//		CodeMapGetters getter;
		for (IClass c : classes) {
//			getter = new CodeMapGetters(classProps);
			if (checkComposite(c)) {
//				String className = Helpers.getName(c.getClassName());
//				String componentName = Helpers.getName(this.component.getClassName());
//				Pattern composite = new Pattern(compositeLabel, colorString, "", className);
//				composite.addRelatedClass(new Pattern(componentLabel, colorString, "", componentName));
//				PatternStorage.addIClass(composite);
				c.setColor(colorString);
				c.setPatternLabel(compositeLabel);
				for (IClass ic : classes) {
					if (Helpers.getName(this.component.getClassName()).equals(Helpers.getName(ic.getClassName()))) {
						ic.setColor(colorString);
						ic.setPatternLabel(componentLabel);
						c.addRelated(ic);
					}
				}
//				labelComponent(componentName);
//				labelComposite(getter.getClassName());
			} else if (checkLeaf(c)) {
//				String className = Helpers.getName(c.getClassName());
//				String componentName = Helpers.getName(this.component.getClassName());
//				Pattern leaf = new Pattern(leafLabel, colorString, "", className);
//				leaf.addRelatedClass(new Pattern(componentLabel, colorString, "", componentName));
//				PatternStorage.addIClass(leaf);
				c.setColor(colorString);
				c.setPatternLabel(leafLabel);
				for (IClass ic : classes) {
					if (Helpers.getName(this.component.getClassName()).equals(Helpers.getName(ic.getClassName()))) {
						ic.setColor(colorString);
						ic.setPatternLabel(componentLabel);
						c.addRelated(ic);
					}
				}
				// TODO: register pattern instead of labeling
//				labelComponent(componentName);
//				labelLeaf(getter.getClassName());
			}
		}
	}
	
	private boolean checkComposite(IClass c) {
		this.component = getComponent(c);
		String className = Helpers.getName(c.getClassName());
		String componentName;
		if (this.component == null || className.equals((componentName = Helpers.getName(this.component.getClassName())))) {
			return false;
		}
//		String componentName = Helpers.getName(this.component.get("className"));
		int methodEqualCount = 0;
		List<IMethod> methods = c.getMethods();
		String name;
		List<String> argTypes;
		String returnType;
//		ArrayList<String> argTypes;
		for (IMethod method : methods) {
			name = method.getName();
			argTypes = method.getArgTypes();
			returnType = method.getReturnType();
//			argTypes = new ArrayList<String>();
//			for (int i = 0; i < argTypesArray.length; i++) {
//				argTypes.add(Helpers.getName(argTypesArray[i].trim()));
//			}
			if (argTypes.contains(componentName) && methodSigInComponent(name, argTypes, returnType)) {
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
	
	private boolean checkLeaf(IClass c) {
		this.component = getComponent(c);
		String className = Helpers.getName(c.getClassName());
		String componentName;
		if (this.component == null || className.equals((componentName = Helpers.getName(this.component.getClassName())))) {
			return false;
		}
//		String componentName = Helpers.getName(this.component.get("className"));
		int methodEqualCount = 0;
		
		List<IMethod> methods = c.getMethods();
		String name;
//		String[] argTypesArray;
		String returnType;
		List<String> argTypes;
		for (IMethod method : methods) {
			name = method.getName();
			argTypes = method.getArgTypes();
			returnType = method.getReturnType();
//			argTypes = new ArrayList<>();
//			for (int i = 0; i < argTypesArray.length; i++) {
//				argTypes.add(Helpers.getName(argTypesArray[i].trim()));
//			}
			if (!argTypes.contains(componentName) && methodSigInComponent(name, argTypes, returnType)) {
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
	
	private boolean methodSigInComponent(String methodName, List<String> argTypes, String returnType) {
//		method = method.substring(method.indexOf(":"));
		
		List<IMethod> componentMethods = this.component.getMethods();
		String cMethodName;
		List<String> cArgtypes;
		String cReturnType;
		for (IMethod cMethod : componentMethods) {
			cMethodName = cMethod.getName();
			cArgtypes = cMethod.getArgTypes();
			cReturnType = cMethod.getReturnType();
			if (cMethodName.equals(methodName) && cArgtypes.equals(argTypes) && cReturnType.equals(returnType)) {
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
	
	private IClass getComponent(IClass c) {
		int methodCount = 0;
		// Is it an abstract class or interface
		String componentName = Helpers.getName(c.getClassName());
		int access = c.getAccess();
		//1537 Interface 1057 Abstract Class
		if (access == 1537 || access == 1057) {
			// 2 Methods that take this component as an argument
			List<IMethod> methods = c.getMethods();
//			String[] argTypesArray;
			List<String> argTypes;
			for (IMethod method : methods) {
				argTypes = method.getArgTypes();
//				argTypesArray = getter.getMethodArgTypes(methodName);
//				argTypes = new ArrayList<>();
//				for (int i = 0; i < argTypesArray.length; i++) {
//					argTypes.add(Helpers.getName(argTypesArray[i].trim()));
//				}
				if (argTypes.contains(componentName)) {
					methodCount++;
				}
				
				if (methodCount >= 2) {
					return c;
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
		List<String> extendsClass = c.getRelatedClassNames("extends");
		if (!extendsClass.isEmpty()) {
			String extendsName = extendsClass.get(0);
			extendsName = Helpers.getName(extendsName);
			for (IClass ic : this.classes) {
				String extender = Helpers.getName(ic.getClassName());
				if (extendsName.equals(extender)) {
					IClass component = getComponent(ic);
					if (component != null) {
						return component;
					}
				}
			}
//			for (int i = 0; i < this.classProperties.size(); i++) {
//				HashMap<String, String> nextClassProps = this.classProperties.get(i);
//				CodeMapGetters newGetter = new CodeMapGetters(nextClassProps);
//				String extender = Helpers.getName(newGetter.getClassName());
//				if (extendsName.equals(extender)) {
//					CodeMapGetters component = getComponent(newGetter);
//					if (component != null) {
//						return component;
//					}
//				}
//			}
		}
		
		String implementsName;
		List<String> interFaces = c.getRelatedClassNames("implements");
		if (interFaces.size() == 1) {
			implementsName = Helpers.getName(interFaces.get(0));
			for (IClass ic : this.classes) {
				String implementer = Helpers.getName(ic.getClassName());
				if (implementsName.equals(implementer)) {
					IClass component = getComponent(ic);
					if (component != null) {
						return component;
					}
				}
			}
//			for (int i = 0; i < this.classProperties.size(); i++) {
//				HashMap<String, String> nextClassProps = this.classProperties.get(i);
//				CodeMapGetters newGetter = new CodeMapGetters(nextClassProps);
//				String implementer = Helpers.getName(newGetter.getClassName());
//				if (implementsName.equals(implementer)) {
//					CodeMapGetters component = getComponent(newGetter);
//					if (component != null) {
//						return component;
//					}
//				}
//			}
		}
		return null;
	}
	
//	private void labelComponent(String otherClass) {
//		// Given a className, finds that class in the
//		// StringBuilder and labels it as a component
//		String className = Helpers.getName(otherClass);
//		if (this.classCode.get(className) == null) {
//			return;
//		}
//		StringBuilder sb = new StringBuilder(this.classCode.get(className) );
//		int colorOffset = sb.indexOf(Constants.COLOR_OFFSET);
//		if (colorOffset == -1) {
//			return;
//		}
//		sb.replace(colorOffset,
//				colorOffset + Constants.COLOR_OFFSET.length(), colorString);
//		int labelOffset = sb.indexOf(Constants.LABEL_OFFSET);
//		sb.replace(labelOffset,
//				labelOffset + Constants.LABEL_OFFSET.length(),
//				componentLabel);
//		this.classCode.put(className, sb.toString());
//			
//	}
//	
//	private void labelComposite(String className) {
//		// Given a className, labels that class as a composite
//		String name = Helpers.getName(className);
//		String code = this.classCode.get(name);
//		if (code == null) {
//			return;
//		}
//		StringBuilder sb = new StringBuilder(code);
//		int colorOffset = sb.indexOf(Constants.COLOR_OFFSET);
//		if (colorOffset == -1) {
//			return;
//		}
//		sb.replace(colorOffset,
//				colorOffset + Constants.COLOR_OFFSET.length(), colorString);
//		int labelOffset = sb.indexOf(Constants.LABEL_OFFSET);
//		sb.replace(labelOffset,
//				labelOffset + Constants.LABEL_OFFSET.length(),
//				compositeLabel);
//		this.classCode.put(name, sb.toString());
//	}
//	
//	private void labelLeaf(String className) {
//		String name = Helpers.getName(className);
//		String code = this.classCode.get(name);
//		
//		if (code == null) {
//			return;
//		}
//		StringBuilder sb = new StringBuilder(code);
//		int colorOffset = sb.indexOf(Constants.COLOR_OFFSET);
//		if (colorOffset == -1) {
//			return;
//		}
//		sb.replace(colorOffset,
//				colorOffset + Constants.COLOR_OFFSET.length(), colorString);
//		int labelOffset = sb.indexOf(Constants.LABEL_OFFSET);
//		sb.replace(labelOffset,
//				labelOffset + Constants.LABEL_OFFSET.length(),
//				leafLabel);
//		this.classCode.put(name, sb.toString());
//	}

}
