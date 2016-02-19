package problem.patterns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import problem.Constants;
import problem.FileProperties;
import problem.Helpers;
import problem.Pattern;
import problem.PatternStorage;
import problem.api.CodeMapGetters;
import problem.api.IPatternDetector;

public class DecoratorPatternDetector implements IPatternDetector {
	private static final String colorString = "green";
	private static final String decoratorLabel = "\\n\\<\\<Decorator\\>\\>";
	private static final String componentLabel = "\\n\\<\\<Component\\>\\>";
	private static final String arrowLabel = "decorates";
	private String componentName;
	private boolean componentLabeled = false;
	private List<HashMap<String, String>> classProperties = new ArrayList<>();
	private HashMap<String, String> classCode = new HashMap<>();

	public DecoratorPatternDetector() {
	}

	@Override
	public void detectPattern(List<HashMap<String, String>> classProperties, HashMap<String, String> classCode) {
		this.classProperties = classProperties;
		this.classCode = classCode;
		CodeMapGetters getter;
		for (HashMap<String, String> code : classProperties) {
			getter = new CodeMapGetters(code);
			if (checkDecorator(getter)) {
				// TODO: Store decorator
				String label = decoratorLabel;
				String color = colorString;
				String arrow = "";
				String className = Helpers.getName(getter.getClassName());
				Pattern decorator = new Pattern(label, color, arrow, className);
				decorator.addRelatedClass(new Pattern(componentLabel, colorString, arrowLabel, this.componentName));
				PatternStorage.addIClass(decorator);
//				labelDecorator(getter);
//				labelComponent();
//				if (isAssociated(getter)) {
//					labelArrow(getter.getClassName());
//				}
			}
		}
	}

	private boolean checkDecorator(CodeMapGetters getter) {
		this.componentName = getComponent(getter, getter);
		if (this.componentName.equals("")
				|| Helpers.getName(this.componentName).equals(Helpers.getName(getter.getClassName()))) {
			return false;
		}

		return true;
	}

	private String getComponent(CodeMapGetters original, CodeMapGetters possibleComponent) {

		String componentName = Helpers.getName(possibleComponent.getClassName());
		int access = possibleComponent.getAccess();
		// 1537 Interface 1057 Abstract Class
		if (access == 1537 || access == 1057) {
			if (checkConstructor(original, componentName)) {
				return componentName;
			}
		}

		String extendsName = possibleComponent.getClassExtends();
		if (extendsName != null) {
			extendsName = Helpers.getName(extendsName);
			for (int i = 0; i < this.classProperties.size(); i++) {
				HashMap<String, String> nextClassProps = this.classProperties.get(i);
				CodeMapGetters newGetter = new CodeMapGetters(nextClassProps);
				String extender = Helpers.getName(newGetter.getClassName());
				if (extendsName.equals(extender)) {
					String component = getComponent(original, newGetter);
					if (!component.equals("")) {
						return component;
					}
				} else if (checkConstructor(original, extendsName)) {
					return extendsName;
				}
			}
		}

		String implementsName;
		String[] interFaces = possibleComponent.getClassImplements();
		if (interFaces.length == 1) {
			implementsName = Helpers.getName(interFaces[0]);
			for (int i = 0; i < this.classProperties.size(); i++) {
				HashMap<String, String> nextClassProps = this.classProperties.get(i);
				CodeMapGetters newGetter = new CodeMapGetters(nextClassProps);
				String implementer = Helpers.getName(newGetter.getClassName());
				if (implementsName.equals(implementer)) {
					String component = getComponent(original, newGetter);
					if (!component.equals("")) {
						return component;
					}
				} else if (checkConstructor(original, implementsName)) {
					return implementsName;
				}
			}
		}

		return "";
	}
	
	private boolean checkConstructor(CodeMapGetters getter, String className) {
		String name = Helpers.getName(className);
		ArrayList<String> methods = getter.getMethodNames();
		String[] argTypesArray;
		ArrayList<String> argTypes;
		for (String methodName : methods) {
			if (methodName.equals("<init>")) {
				argTypesArray = getter.getMethodArgTypes(methodName);
				argTypes = new ArrayList<>();
				for (int i = 0; i < argTypesArray.length; i++) {
					argTypes.add(Helpers.getName(argTypesArray[i].trim()));
				}
				if (argTypes.contains(name)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isAssociated(CodeMapGetters getter) {
		String otherClassName = Helpers.getName(this.componentName);

		for (String a : getter.getClassAssociates()) {
			String associatedName = Helpers.getName(a);
			if (associatedName.equals(otherClassName)) {
				return true;
			}
		}

		return false;
	}

//	private void labelDecorator(CodeMapGetters getter) {
//		// given a className, finds that class and labels it as a decorator
//		String name = Helpers.getName(getter.getClassName());
//		StringBuilder sb = new StringBuilder(this.classCode.get(name));
//		int colorOffset = sb.indexOf(Constants.COLOR_OFFSET);
//		if (colorOffset == -1) {
//			return;
//		}
//		sb.replace(colorOffset, colorOffset + Constants.COLOR_OFFSET.length(), colorString);
//		int labelOffset = sb.indexOf(Constants.LABEL_OFFSET);
//		if (labelOffset == -1) {
//			return;
//		}
//		sb.replace(labelOffset, labelOffset + Constants.LABEL_OFFSET.length(), decoratorLabel);
//		this.classCode.put(name, sb.toString());
//	}
//
//	private void labelComponent() {
//		// Given a className, finds that class in the
//		// StringBuilder and labels it as a component
//		if (!componentLabeled) {
//			String className = Helpers.getName(this.componentName);
//			if (this.classCode.get(className) == null) {
//				return;
//			}
//			StringBuilder sb = new StringBuilder(this.classCode.get(className));
//			int colorOffset = sb.indexOf(Constants.COLOR_OFFSET);
//			sb.replace(colorOffset, colorOffset + Constants.COLOR_OFFSET.length(), colorString);
//			int labelOffset = sb.indexOf(Constants.LABEL_OFFSET);
//			sb.replace(labelOffset, labelOffset + Constants.LABEL_OFFSET.length(), componentLabel);
//			componentLabeled = true;
//			this.classCode.put(className, sb.toString());
//		}
//	}
//
//	private void labelArrow(String currentClass) {
//		// Given a className, finds that class in the
//		// StringBuilder and labels the correct arrow as the decorates arrow
//		String className = Helpers.getName(currentClass);
//		String otherClassName = Helpers.getName(this.componentName);
//		StringBuilder sb = new StringBuilder(this.classCode.get(className));
//		int classIndex = 0;
//		int otherClassIndex;
//		while ((otherClassIndex = sb.indexOf(otherClassName, classIndex)) != -1) {
//			if (associatedArrow(otherClassIndex, sb)) {
//				break;
//			}
//			classIndex = otherClassIndex + 1;
//		}
//		if (otherClassIndex == -1) {
//			return;
//		}
//		int arrowOffset = sb.indexOf(Constants.ARROW_OFFSET, otherClassIndex);
//		if (arrowOffset == -1) {
//			return;
//		}
//		sb.replace(arrowOffset, arrowOffset + Constants.ARROW_OFFSET.length(), arrowLabel);
//		this.classCode.put(className, sb.toString());
//	}
//
//	private boolean associatedArrow(int index, StringBuilder sb) {
//		// Given an index in a StringBuilder to an arrow, returns
//		// true if it is an associated arrow, false otherwise
//		int arrowheadIndex = sb.indexOf("arrowhead", index);
//		int arrowStart = 11;
//		int arrowEnd = 15;
//		int styleIndex = sb.indexOf("style", index);
//		int styleStart = 7;
//		int styleEnd = 12;
//		String arrowhead = sb.substring(arrowheadIndex + arrowStart, arrowheadIndex + arrowEnd);
//		String style = sb.substring(styleIndex + styleStart, styleIndex + styleEnd);
//
//		return arrowhead.equals("open") && style.equals("solid");
//	}

}
