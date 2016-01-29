package problem.patterns;

import java.util.HashMap;
import java.util.List;

import problem.Constants;
import problem.Helpers;
import problem.api.IPatternDetector;

public class AdapterPatternDetector implements IPatternDetector{
	private static final String colorString = "brown";
	private static final String patternLabelAdapter = "\\n\\<\\<Adapter\\>\\>";
	private static final String patternLabelAdaptee = "\\n\\<\\<Adaptee\\>\\>";
	private static final String patternLabelTarget = "\\n\\<\\<Target\\>\\>";
	private static final String arrowLabel = "adapts";
	private String adaptee = "";
	private String adapter = "";
	private String target = "";
	private StringBuilder sb = new StringBuilder();
	@Override
	public void detectPattern(List<HashMap<String, String>> classCode, StringBuilder sb) {
		this.sb = sb;
		for (HashMap<String, String> parsedCode : classCode) {
			isAdapter(parsedCode, classCode);
			if ((!adaptee.equals("")) && (!adapter.equals("")) && (!target.equals(""))) {
				labelAdaptee(adaptee);
				labelAdapter(adapter);
				labelTarget(target);
				labelArrow(adapter, adaptee);
				adaptee = "";
				adapter = "";
				target = "";
			}
		}
	}

	private void isAdapter(HashMap<String, String> parsedCode, List<HashMap<String, String>> classCode) {
		String currentClass = Helpers.getName(parsedCode.get("className"), "/");
		String currentImplements = Helpers.getName(parsedCode.get("implements"), "/");
		currentImplements = currentImplements.equals("[]") ? "" : currentImplements.substring(0, currentImplements.length() - 1);
		for (HashMap<String, String> c : classCode) {
			String otherClass = c.get("className");
			if (currentClass.equals(otherClass)) {
				continue;
			} else {
				target = currentImplements;
				adapter = currentClass;
				isAssociated(parsedCode);
			}
		}
	}
	
	private void isAssociated(HashMap<String, String> parsedCode) {
		for (String s : parsedCode.keySet()) {
			if (s.contains("associated")) {
				containsField(parsedCode, parsedCode.get(s));
			}
		}
	}
	 
	private void containsField(HashMap<String, String> parsedCode, String adaptee) {
		adaptee = Helpers.getName(adaptee, "\\.");
		for (String s : parsedCode.keySet()) {
			if (s.contains("field")) {
				String field = parsedCode.get(s);
				String[] fieldProperties = field.split(":");
				String type = Helpers.getName(fieldProperties[2], "\\.");
				if (type.equals(adaptee)) {
					this.adaptee = type;
				}
			}
		}
	}
	
	private void labelAdaptee(String className) {
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
				patternLabelAdaptee);
	}
	
	private void labelAdapter(String className) {
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
				patternLabelAdapter);
	}
	
	private void labelTarget(String className) {
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
				patternLabelTarget);
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
