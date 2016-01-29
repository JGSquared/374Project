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

	public DecoratorPatternDetector() {
	}

	@Override
	public void detectPattern(List<HashMap<String, String>> classCode,
			StringBuilder sb) {
		this.sb = sb;
		for (HashMap<String, String> code : classCode) {
			if (checkDecorator(code, classCode)) {
				labelDecorator(code.get("className"), code);
			}
			classTree = new ArrayList<>();
		}
	}

	private boolean checkDecorator(HashMap<String, String> code,
			List<HashMap<String, String>> classCode) {
		if (code.get("decorator") != null) {
			return true;
		}
		String currentClass = code.get("className");
		String currentExtends = code.get("extends");
		for (HashMap<String, String> c : classCode) {
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
					if (checkDecorator(c, classCode)) {
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
		String otherClassName = Helpers.getName(otherClass, "/");
		for (String s : code.keySet()) {
			if (s.contains("associated")) {
				String associatedName = Helpers.getName(code.get(s), "\\.");
				if (associatedName.equals(otherClassName)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkConstructor(HashMap<String, String> code,
			String otherClass) {
		String otherClassName = Helpers.getName(otherClass, "/");
		for (String s : code.keySet()) {
			if (s.contains("method")) {
				String method = code.get(s);
				String[] methodProps = method.split(":");
				// int access = Integer.parseInt(methodProps[0]);
				String name = methodProps[1];
				if (name.equals("<init>")) {
					String argTypesString = methodProps[2];
					argTypesString = argTypesString.replaceAll("\\[", "");
					argTypesString = argTypesString.replaceAll("\\]", "");

					String[] splitArgs = argTypesString.split(",");
					ArrayList<String> argTypes = new ArrayList<String>();
					for (int i = 0; i < splitArgs.length; i++) {
						argTypes.add(Helpers.getName(splitArgs[i].trim(), "\\."));
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
		String name = Helpers.getName(className, "/");
		int classIndex = Helpers.getClassDeclarationIndex(name, sb);
		if (classIndex == -1) {
			return;
		}
		int colorOffset = this.sb.indexOf(Constants.COLOR_OFFSET,
				classIndex);
		this.sb.replace(colorOffset,
				colorOffset + Constants.COLOR_OFFSET.length(), colorString);
		int labelOffset = this.sb.indexOf(Constants.LABEL_OFFSET,
				classIndex);
		this.sb.replace(labelOffset,
				labelOffset + Constants.LABEL_OFFSET.length(),
				decoratorLabel);
	}

	private void labelComponent(String otherClass) {
		// Given a className, finds that class in the
		// StringBuilder and labels it as a component
		if (!componentLabeled) {
			String className = Helpers.getName(otherClass, "\\.");
			className = Helpers.getName(otherClass, "/");
			int classIndex = Helpers.getClassDeclarationIndex(className,
					this.sb);
			if (classIndex == -1) {
				return;
			}
			System.out.println(className);
			System.out.println(sb.substring(classIndex - 20, classIndex + 20));
			int colorOffset = this.sb.indexOf(Constants.COLOR_OFFSET,
					classIndex);
			this.sb.replace(colorOffset,
					colorOffset + Constants.COLOR_OFFSET.length(), colorString);
			int labelOffset = this.sb.indexOf(Constants.LABEL_OFFSET,
					classIndex);
			this.sb.replace(labelOffset,
					labelOffset + Constants.LABEL_OFFSET.length(),
					componentLabel);
			componentLabeled = true;
		}
	}

	private void labelArrow(String currentClass, String otherClass) {
		// Given a className, finds that class in the
		// StringBuilder and labels the correct arrow as the decorates arrow
		String className = Helpers.getName(currentClass, "/");
		otherClass = Helpers.getName(otherClass, "\\.");
		String otherClassName = Helpers.getName(otherClass, "/");
		int classIndex = Helpers.getClassDeclarationIndex(className, sb);
		if (classIndex == -1) {
			return;
		}
		int otherClassIndex;
		while ((otherClassIndex = sb.indexOf(otherClassName, classIndex)) != -1) {
			if (associatedArrow(otherClassIndex)) {
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
	}

	private boolean associatedArrow(int index) {
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
