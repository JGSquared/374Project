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
	private HashMap<String, String> classCode;
	private List<HashMap<String, String>> classProperties;
	
	@Override
	public void detectPattern(List<HashMap<String, String>> classProperties, HashMap<String, String> classCode) {
		this.classProperties = classProperties;
		this.classCode = classCode;
		CodeMapGetters getter;
		for (HashMap<String, String> parsedCode : classProperties) {
			getter = new CodeMapGetters(parsedCode);
			String classKey = Helpers.getName(getter.getClassName());
			this.sb = new StringBuilder(classCode.get(classKey));
			isAdapter(getter, classProperties);
			if ((!adaptee.equals("")) && (!adapter.equals("")) && (!target.equals(""))) {
				// TODO: Register pattern instead of labeling
				Pattern adapt = new Pattern(patternLabelAdapter, colorString, "", adapter);
				adapt.addRelatedClass(new Pattern(patternLabelAdaptee, colorString, arrowLabel, adaptee));
				adapt.addRelatedClass(new Pattern(patternLabelTarget, colorString, "", target));
				PatternStorage.addIClass(adapt);
//				labelAdaptee(adaptee);
//				labelAdapter(adapter);
//				labelTarget(target);
//				labelArrow(adapter, adaptee);
				adaptee = "";
				adapter = "";
				target = "";
			}
		}
	}

	private void isAdapter(CodeMapGetters getter, List<HashMap<String, String>> classCode) {
		String currentClass = Helpers.getName(getter.getClassName());
		String currentImplements = Helpers.getName(getter.getClassImplements().toString());
		currentImplements = currentImplements.equals("[]") ? "" : currentImplements.substring(0, currentImplements.length() - 1);
		for (HashMap<String, String> c : classCode) {
			String otherClass = c.get("className");
			if (currentClass.equals(otherClass)) {
				continue;
			} else {
				target = currentImplements;
				adapter = currentClass;
				isAssociated(getter);
			}
		}
	}
	
	private void isAssociated(CodeMapGetters getter) {
		ArrayList<String> associated = getter.getClassAssociates();
		for (String a : associated) {
			containsInterfaceField(getter, a);
		}
		
//		for (String s : parsedCode.keySet()) {
//			if (s.contains("associated")) {
//				containsInterfaceField(parsedCode, parsedCode.get(s));
//			}
//		}
	}
	 
	private void containsInterfaceField(CodeMapGetters getter, String adaptee) {
		adaptee = Helpers.getName(adaptee);
		
		ArrayList<String> fields = getter.getFieldNames();
		String type;
		CodeMapGetters newGetter;
		String currentClass;
		int access;
		for (String fieldName : fields) {
			type = Helpers.getName(getter.getFieldType(fieldName));
			if (type.equals(adaptee)) {
				for (HashMap<String, String> c : this.classProperties) {
					newGetter = new CodeMapGetters(c);
					currentClass = Helpers.getName(newGetter.getClassName());
					if (currentClass.equals(type)) {
						access = newGetter.getAccess();
						if (access == 1537) {
							this.adaptee = currentClass;
						}
					}
				}
			}
		}
		
//		for (String s : parsedCode.keySet()) {
//			if (s.contains("field")) {
//				String field = parsedCode.get(s);
//				String[] fieldProperties = field.split(":");
//				String type = Helpers.getName(fieldProperties[2]);
//				if (type.equals(adaptee)) {
//					for (HashMap<String, String> c : this.classProperties) {
//						String currentClass = Helpers.getName(c.get("className"));
//						if (currentClass.equals(type)) {
//							
//							int access = Integer.parseInt(c.get("access"));
//							if (access == 1537) {
//								this.adaptee = currentClass;
//							}
//						}
//					}
//				}
//			}
//		}
	}
	
	private void labelAdaptee(String className) {
		String temp = this.classCode.get(className);
		if (temp == null) {
			return;
		}
		String name = Helpers.getName(className);
		StringBuilder tempSB = new StringBuilder(temp);
		int colorOffset = tempSB.indexOf(Constants.COLOR_OFFSET);
		tempSB.replace(colorOffset,
				colorOffset + Constants.COLOR_OFFSET.length(), colorString);
		int labelOffset = tempSB.indexOf(Constants.LABEL_OFFSET);
		tempSB.replace(labelOffset,
				labelOffset + Constants.LABEL_OFFSET.length(),
				patternLabelAdaptee);
		classCode.put(className, tempSB.toString());
	}
	
	private void labelAdapter(String className) {
		String temp = this.classCode.get(className);
		if (temp == null) {
			return;
		}
		StringBuilder tempSB = new StringBuilder(temp);
		String name = Helpers.getName(className);
		int colorOffset = tempSB.indexOf(Constants.COLOR_OFFSET);
		tempSB.replace(colorOffset,
				colorOffset + Constants.COLOR_OFFSET.length(), colorString);
		int labelOffset = tempSB.indexOf(Constants.LABEL_OFFSET);
		tempSB.replace(labelOffset,
				labelOffset + Constants.LABEL_OFFSET.length(),
				patternLabelAdapter);
		classCode.put(className, tempSB.toString());
	}
	
	private void labelTarget(String className) {
		String temp = this.classCode.get(className);
		if (temp == null) {
			return;
		}
		StringBuilder tempSB = new StringBuilder(temp);
		String name = Helpers.getName(className);
		int colorOffset = tempSB.indexOf(Constants.COLOR_OFFSET);
		tempSB.replace(colorOffset,
				colorOffset + Constants.COLOR_OFFSET.length(), colorString);
		int labelOffset = tempSB.indexOf(Constants.LABEL_OFFSET);
		tempSB.replace(labelOffset,
				labelOffset + Constants.LABEL_OFFSET.length(),
				patternLabelTarget);
		classCode.put(className, tempSB.toString());
	}
	
	private void labelArrow(String currentClass, String otherClass) {
		// Given a className, finds that class in the
		// StringBuilder and labels the correct arrow as the decorates arrow
		String className = Helpers.getName(currentClass);
		StringBuilder tempSB = new StringBuilder(this.classCode.get(className));
		otherClass = Helpers.getName(otherClass);
		String otherClassName = Helpers.getName(otherClass);
		int classIndex = 0;
		if (classIndex == -1) {
			return;
		}
		int otherClassIndex;
		while ((otherClassIndex = tempSB.indexOf(otherClassName, classIndex)) != -1) {
			if (associatedArrow(otherClassIndex)) {
				break;
			}
			classIndex = otherClassIndex + 1;
		}
		if (otherClassIndex == -1) {
			return;
		}
		int arrowOffset = tempSB.indexOf(Constants.ARROW_OFFSET, otherClassIndex);
		tempSB.replace(arrowOffset, arrowOffset + Constants.ARROW_OFFSET.length(),
				arrowLabel);
		classCode.put(currentClass, tempSB.toString());
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
