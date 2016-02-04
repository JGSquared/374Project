package problem.patterns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import problem.Constants;
import problem.FileProperties;
import problem.Helpers;
import problem.api.IPatternDetector;

public class DecoratorPatternDetector implements IPatternDetector {
	private static final String colorString = "green";
	private static final String decoratorLabel = "\\n\\<\\<Decorator\\>\\>";
	private static final String componentLabel = "\\n\\<\\<Component\\>\\>";
	private static final String arrowLabel = "decorates";
	private StringBuilder sb;
	private boolean componentLabeled = false;
	private List<HashMap<String, String>> classTree = new ArrayList<>();
	private HashMap<String, String> classCode = new HashMap<>();

	public DecoratorPatternDetector() {
	}

	@Override
	public void detectPattern(List<HashMap<String, String>> classProperties,
			HashMap<String, String> classCode) {
		this.classCode = classCode;
		for (HashMap<String, String> code : classProperties) {
			String classKey = Helpers.getName(code.get("className"));
			this.sb = new StringBuilder(classCode.get(classKey));
			if (checkDecorator(code, classProperties)) {
				labelDecorator(code.get("className"), code);
			}
			classTree = new ArrayList<>();
		}
	}

	private boolean checkDecorator(HashMap<String, String> code,
			List<HashMap<String, String>> classProperties) {
		if (code.get("decorator") != null) {
			return true;
		}
		String currentClass = code.get("className");
		String currentExtends = code.get("extends");
		for (HashMap<String, String> c : classProperties) {
			String otherClass = c.get("className");
			if (currentClass.equals(otherClass)) {
				continue;
			} else if (currentExtends.equals(otherClass)) {
				if (isAssociated(code, otherClass) && checkConstructor(code, otherClass)) {
					labelComponent(otherClass);
					labelArrow(currentClass, otherClass);
					labelDecorator(code.get("className"), code);
					code.put("decorator", "true");
					return true;
				} else {
					this.classTree.add(code);
					if (checkDecorator(c, classProperties)) {
						labelDecorator(c.get("className"), c);
						labelDecorator(code.get("className"), code);
						c.put("decorator", "true");
						code.put("decorator", "true");
						return true;
					}
				}
			}
		}
		if (isAssociated(code, currentExtends) && checkConstructor(code, currentExtends)) {
			labelComponent(currentExtends);
			labelArrow(currentClass, currentExtends);
			labelDecorator(code.get("className"), code);
			code.put("decorator", "true");
			return true;
		}
		return false;
	}

	private boolean isAssociated(HashMap<String, String> code, String otherClass) {
		String otherClassName = Helpers.getName(otherClass);
		for (String s : code.keySet()) {
			if (s.contains("associated")) {
				String associatedName = Helpers.getName(code.get(s));
				if (associatedName.equals(otherClassName)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkConstructor(HashMap<String, String> code,
			String otherClass) {
		String otherClassName = Helpers.getName(otherClass);
		for (String s : code.keySet()) {
			if (s.contains("method")) {
				String method = code.get(s);
				String[] methodProps = method.split(":");
				String name = methodProps[1];
				if (name.equals("<init>")) {
					String argTypesString = methodProps[2];
					argTypesString = argTypesString.replaceAll("\\[", "");
					argTypesString = argTypesString.replaceAll("\\]", "");

					String[] splitArgs = argTypesString.split(",");
					ArrayList<String> argTypes = new ArrayList<String>();
					for (int i = 0; i < splitArgs.length; i++) {
						argTypes.add(Helpers.getName(splitArgs[i].trim()));
					}
					if (argTypes.contains(otherClassName)) {
						return true;
					}
				}
			}
		}
		for (HashMap<String, String> c : this.classTree) {
			if (code.equals(c)) {
				continue;
			}
			if (checkConstructor(c, otherClass)) {
				return true;
			}
		}

		return false;
	}

	private void labelDecorator(String className, HashMap<String, String> code) {
		// given a className, finds that class and labels it as a decorator
		if (code.get("decorator") != null) {
			return;
		}
		String name = Helpers.getName(className);
		StringBuilder sb = new StringBuilder(this.classCode.get(name));
		int colorOffset = sb.indexOf(Constants.COLOR_OFFSET);
		sb.replace(colorOffset,
				colorOffset + Constants.COLOR_OFFSET.length(), colorString);
		int labelOffset = sb.indexOf(Constants.LABEL_OFFSET);
		sb.replace(labelOffset,
				labelOffset + Constants.LABEL_OFFSET.length(),
				decoratorLabel);
		this.classCode.put(name, sb.toString());
	}

	private void labelComponent(String otherClass) {
		// Given a className, finds that class in the
		// StringBuilder and labels it as a component
		if (!componentLabeled) {
			String className = Helpers.getName(otherClass);
			if (this.classCode.get(className) == null) {
				return;
			}
			StringBuilder sb = new StringBuilder(this.classCode.get(className) );
			int classIndex = Helpers.getClassDeclarationIndex(className,
					sb);
			int colorOffset = sb.indexOf(Constants.COLOR_OFFSET,
					classIndex);
			sb.replace(colorOffset,
					colorOffset + Constants.COLOR_OFFSET.length(), colorString);
			int labelOffset = sb.indexOf(Constants.LABEL_OFFSET,
					classIndex);
			sb.replace(labelOffset,
					labelOffset + Constants.LABEL_OFFSET.length(),
					componentLabel);
			componentLabeled = true;
			this.classCode.put(className, sb.toString());
		}
	}

	private void labelArrow(String currentClass, String otherClass) {
		// Given a className, finds that class in the
		// StringBuilder and labels the correct arrow as the decorates arrow
		String className = Helpers.getName(currentClass);
		String otherClassName = Helpers.getName(otherClass);
		StringBuilder sb = new StringBuilder(this.classCode.get(className));
		int classIndex = 0;
		int otherClassIndex;
		while ((otherClassIndex = sb.indexOf(otherClassName, classIndex)) != -1) {
			if (associatedArrow(otherClassIndex, sb)) {
				break;
			}
			classIndex = otherClassIndex + 1;
		}
		if (otherClassIndex == -1) {
			return;
		}
		int arrowOffset = sb.indexOf(Constants.ARROW_OFFSET, otherClassIndex);
		sb.replace(arrowOffset, arrowOffset + Constants.ARROW_OFFSET.length(),
				arrowLabel);
		this.classCode.put(className, sb.toString());
	}

	private boolean associatedArrow(int index, StringBuilder sb) {
		// Given an index in a StringBuilder to an arrow, returns
		// true if it is an associated arrow, false otherwise
		int arrowheadIndex = sb.indexOf("arrowhead", index);
		int arrowStart = 11;
		int arrowEnd = 15;
		int styleIndex = sb.indexOf("style", index);
		int styleStart = 7;
		int styleEnd = 12;
		String arrowhead = sb.substring(arrowheadIndex + arrowStart,
				arrowheadIndex + arrowEnd);
		String style = sb.substring(styleIndex + styleStart, styleIndex
				+ styleEnd);

		return arrowhead.equals("open") && style.equals("solid");
	}

}
