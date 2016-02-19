package problem.patterns;

import java.util.ArrayList;
import java.util.List;

import problem.ClassStorage;
import problem.Helpers;
import problem.api.CodeMapGetters;
import problem.api.IArrow;
import problem.api.IClass;
import problem.api.IMethod;
import problem.api.IPatternDetector;

public class DecoratorPatternDetector implements IPatternDetector {
	private static final String colorString = "green";
	private static final String decoratorLabel = "Decorator";
	private static final String componentLabel = "Component";
	private static final String arrowLabel = "decorates";
	private String componentName;
	private boolean componentLabeled = false;
	private List<IClass> classes;
//	private List<HashMap<String, String>> classProperties = new ArrayList<>();
//	private HashMap<String, String> classCode = new HashMap<>();

	public DecoratorPatternDetector() {
	}

	@Override
	public void detectPattern() {
//		this.classProperties = classProperties;
//		this.classCode = classCode;
//		CodeMapGetters getter;
		classes = ClassStorage.getClasses();
		
		for (IClass c : classes) {
//			getter = new CodeMapGetters(code);
			if (checkDecorator(c)) {
				String label = decoratorLabel;
				String color = colorString;
				String arrow = "";
				String className = Helpers.getName(c.getClassName());
//				Pattern decorator = new Pattern(label, color, arrow, className);
//				decorator.addRelatedClass(new Pattern(componentLabel, colorString, arrowLabel, this.componentName));
//				PatternStorage.addIClass(decorator);
				c.setColor(color);
				c.setPatternLabel(label);
				for (IClass ic : classes) {
					if (Helpers.getName(ic.getClassName()).equals(this.componentName)) {
						ic.setColor(colorString);
						ic.setPatternLabel(componentLabel);
						for (IArrow a : ic.getArrows()) {
							if (Helpers.getName(a.getTo()).equals(this.componentName) && a.getType().equals("associated")) {
								a.setLabel(arrowLabel);
							}
						}
					}
				}
//				labelDecorator(getter);
//				labelComponent();
//				if (isAssociated(getter)) {
//					labelArrow(getter.getClassName());
//				}
			}
		}
	}

	private boolean checkDecorator(IClass c) {
		this.componentName = getComponent(c, c);
		if (this.componentName.equals("")
				|| Helpers.getName(this.componentName).equals(Helpers.getName(c.getClassName()))) {
			return false;
		}

		return true;
	}

	private String getComponent(IClass original, IClass possibleComponent) {

		String componentName = Helpers.getName(possibleComponent.getClassName());
		int access = possibleComponent.getAccess();
		// 1537 Interface 1057 Abstract Class
		if (access == 1537 || access == 1057) {
			if (checkConstructor(original, componentName)) {
				return componentName;
			}
		}

		List<String> extendsClass = possibleComponent.getRelatedClassNames("extends");
		if (extendsClass != null) {
			String extendsName = Helpers.getName(extendsClass.get(0));
			for (IClass ic : this.classes) {
				String extender = Helpers.getName(ic.getClassName());
				if (extendsName.equals(extender)) {
					String component = getComponent(original, ic);
					if (!component.equals("")) {
						return component;
					}
				} else if (checkConstructor(original, extendsName)) {
					return extendsName;
				}
			}
//			for (int i = 0; i < this.classProperties.size(); i++) {
//				HashMap<String, String> nextClassProps = this.classProperties.get(i);
//				CodeMapGetters newGetter = new CodeMapGetters(nextClassProps);
//				String extender = Helpers.getName(newGetter.getClassName());
//				if (extendsName.equals(extender)) {
//					String component = getComponent(original, newGetter);
//					if (!component.equals("")) {
//						return component;
//					}
//				} else if (checkConstructor(original, extendsName)) {
//					return extendsName;
//				}
//			}
		}

		String implementsName;
		List<String> interFaces = possibleComponent.getRelatedClassNames("implements");
		if (interFaces.size() == 1) {
			implementsName = Helpers.getName(interFaces.get(0));
			for (IClass ic : this.classes) {
				String implementer = Helpers.getName(ic.getClassName());
				if (implementsName.equals(implementer)) {
					String component = getComponent(original, ic);
					if (!component.equals("")) {
						return component;
					}
				} else if (checkConstructor(original, implementsName)) {
					return implementsName;
				}
			}
//			for (int i = 0; i < this.classProperties.size(); i++) {
//				HashMap<String, String> nextClassProps = this.classProperties.get(i);
//				CodeMapGetters newGetter = new CodeMapGetters(nextClassProps);
//				String implementer = Helpers.getName(newGetter.getClassName());
//				if (implementsName.equals(implementer)) {
//					String component = getComponent(original, newGetter);
//					if (!component.equals("")) {
//						return component;
//					}
//				} else if (checkConstructor(original, implementsName)) {
//					return implementsName;
//				}
//			}
		}

		return "";
	}
	
	private boolean checkConstructor(IClass c, String className) {
		String name = Helpers.getName(className);
		List<IMethod> methods = c.getMethods();
		String methodName;
//		String[] argTypesArray;
		List<String> argTypes;
		for (IMethod method : methods) {
			methodName = method.getName();
			if (methodName.equals("<init>")) {
				argTypes = method.getArgTypes();
//				argTypes = new ArrayList<>();
//				for (int i = 0; i < argTypesArray.length; i++) {
//					argTypes.add(Helpers.getName(argTypesArray[i].trim()));
//				}
				if (argTypes.contains(name)) {
					return true;
				}
			}
		}
		return false;
	}
	
//	private boolean isAssociated(CodeMapGetters getter) {
//		String otherClassName = Helpers.getName(this.componentName);
//
//		for (String a : getter.getClassAssociates()) {
//			String associatedName = Helpers.getName(a);
//			if (associatedName.equals(otherClassName)) {
//				return true;
//			}
//		}
//
//		return false;
//	}

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
