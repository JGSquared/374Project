package problem.patterns;

import java.util.HashMap;
import java.util.List;

import problem.ClassStorage;
import problem.Constants;
import problem.Helpers;
import problem.api.CodeMapGetters;
import problem.api.IArrow;
import problem.api.IClass;
import problem.api.IField;
import problem.api.IPatternDetector;

public class AdapterPatternDetector implements IPatternDetector{
	private static final String colorString = "brown";
	private static final String patternLabelAdapter = "Adapter";
	private static final String patternLabelAdaptee = "Adaptee";
	private static final String patternLabelTarget = "Target";
	private static final String arrowLabel = "adapts";
	private String adaptee = "";
	private String adapter = "";
	private String target = "";
	private List<IClass> classes;
//	private StringBuilder sb = new StringBuilder();
	
	@Override
	public void detectPattern() {
//		this.classProperties = classProperties;
//		this.classCode = classCode;
//		CodeMapGetters getter;
		classes = ClassStorage.getClasses();
		for (IClass c : classes) {
//			getter = new CodeMapGetters(parsedCode);
			String classKey = Helpers.getName(c.getClassName());
//			this.sb = new StringBuilder(classCode.get(classKey));
			isAdapter(c);
			if ((!adaptee.equals("")) && (!adapter.equals("")) && (!target.equals(""))) {
				// TODO: Register pattern instead of labeling
//				Pattern adapt = new Pattern(patternLabelAdapter, colorString, "", adapter);
//				adapt.addRelatedClass(new Pattern(patternLabelAdaptee, colorString, arrowLabel, adaptee));
//				adapt.addRelatedClass(new Pattern(patternLabelTarget, colorString, "", target));
//				PatternStorage.addIClass(adapt);
				
				c.setColor(colorString);
				c.setPatternLabel(patternLabelAdapter);
				for (IClass ic : classes) {
					if (Helpers.getName(ic.getClassName()).equals(adaptee)) {
						ic.setColor(colorString);
						ic.setPatternLabel(patternLabelAdaptee);
						for (IArrow a : ic.getArrows()) {
							if (a.getTo().equals(Helpers.getName(ic.getClassName())) && a.getType().equals("associated")) {
								a.setLabel(arrowLabel);
							}
						}
						c.addRelated(ic);
					} else if (Helpers.getName(ic.getClassName()).equals(target)) {
						ic.setClassName(colorString);
						ic.setPatternLabel(patternLabelTarget);
						c.addRelated(ic);
					}
				}
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

	private void isAdapter(IClass c) {
		String currentClass = Helpers.getName(c.getClassName());
		String currentImplements = Helpers.getName(c.getRelatedClassNames("implements").toString());
		currentImplements = currentImplements.equals("[]") ? "" : currentImplements.substring(0, currentImplements.length() - 1);
		for (IClass ic : classes) {
			String otherClass = ic.getClassName();
			if (currentClass.equals(otherClass)) {
				continue;
			} else {
				target = currentImplements;
				adapter = currentClass;
				isAssociated(c);
			}
		}
	}
	
	private void isAssociated(IClass c) {
		List<String> associated = c.getRelatedClassNames("associated");
		for (String a : associated) {
			containsInterfaceField(c, a);
		}
		
//		for (String s : parsedCode.keySet()) {
//			if (s.contains("associated")) {
//				containsInterfaceField(parsedCode, parsedCode.get(s));
//			}
//		}
	}
	 
	private void containsInterfaceField(IClass c, String adaptee) {
		adaptee = Helpers.getName(adaptee);
		
		List<IField> fields = c.getFields();
		String type;
//		CodeMapGetters newGetter;
		String name;
		String currentClass;
		int access;
		for (IField field : fields) {
			type = Helpers.getName(field.getName());
			if (type.equals(adaptee)) {
				for (IClass ic : this.classes) {
//					newGetter = new CodeMapGetters(c);
					currentClass = Helpers.getName(ic.getClassName());
					if (currentClass.equals(type)) {
						access = ic.getAccess();
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
	
//	private void labelAdaptee(String className) {
//		String temp = this.classCode.get(className);
//		if (temp == null) {
//			return;
//		}
//		String name = Helpers.getName(className);
//		StringBuilder tempSB = new StringBuilder(temp);
//		int colorOffset = tempSB.indexOf(Constants.COLOR_OFFSET);
//		tempSB.replace(colorOffset,
//				colorOffset + Constants.COLOR_OFFSET.length(), colorString);
//		int labelOffset = tempSB.indexOf(Constants.LABEL_OFFSET);
//		tempSB.replace(labelOffset,
//				labelOffset + Constants.LABEL_OFFSET.length(),
//				patternLabelAdaptee);
//		classCode.put(className, tempSB.toString());
//	}
//	
//	private void labelAdapter(String className) {
//		String temp = this.classCode.get(className);
//		if (temp == null) {
//			return;
//		}
//		StringBuilder tempSB = new StringBuilder(temp);
//		String name = Helpers.getName(className);
//		int colorOffset = tempSB.indexOf(Constants.COLOR_OFFSET);
//		tempSB.replace(colorOffset,
//				colorOffset + Constants.COLOR_OFFSET.length(), colorString);
//		int labelOffset = tempSB.indexOf(Constants.LABEL_OFFSET);
//		tempSB.replace(labelOffset,
//				labelOffset + Constants.LABEL_OFFSET.length(),
//				patternLabelAdapter);
//		classCode.put(className, tempSB.toString());
//	}
//	
//	private void labelTarget(String className) {
//		String temp = this.classCode.get(className);
//		if (temp == null) {
//			return;
//		}
//		StringBuilder tempSB = new StringBuilder(temp);
//		String name = Helpers.getName(className);
//		int colorOffset = tempSB.indexOf(Constants.COLOR_OFFSET);
//		tempSB.replace(colorOffset,
//				colorOffset + Constants.COLOR_OFFSET.length(), colorString);
//		int labelOffset = tempSB.indexOf(Constants.LABEL_OFFSET);
//		tempSB.replace(labelOffset,
//				labelOffset + Constants.LABEL_OFFSET.length(),
//				patternLabelTarget);
//		classCode.put(className, tempSB.toString());
//	}
//	
//	private void labelArrow(String currentClass, String otherClass) {
//		// Given a className, finds that class in the
//		// StringBuilder and labels the correct arrow as the decorates arrow
//		String className = Helpers.getName(currentClass);
//		StringBuilder tempSB = new StringBuilder(this.classCode.get(className));
//		otherClass = Helpers.getName(otherClass);
//		String otherClassName = Helpers.getName(otherClass);
//		int classIndex = 0;
//		if (classIndex == -1) {
//			return;
//		}
//		int otherClassIndex;
//		while ((otherClassIndex = tempSB.indexOf(otherClassName, classIndex)) != -1) {
//			if (associatedArrow(otherClassIndex)) {
//				break;
//			}
//			classIndex = otherClassIndex + 1;
//		}
//		if (otherClassIndex == -1) {
//			return;
//		}
//		int arrowOffset = tempSB.indexOf(Constants.ARROW_OFFSET, otherClassIndex);
//		tempSB.replace(arrowOffset, arrowOffset + Constants.ARROW_OFFSET.length(),
//				arrowLabel);
//		classCode.put(currentClass, tempSB.toString());
//	}
//	
//	private boolean associatedArrow(int index) {
//		// Given an index in a StringBuilder to an arrow, returns
//		// true if it is an associated arrow, false otherwise
//		int arrowheadIndex = sb.indexOf("arrowhead", index);
//		int arrowStart = 11;
//		int arrowEnd = 15;
//		int styleIndex = sb.indexOf("style", index);
//		int styleStart = 7;
//		int styleEnd = 12;
//		String arrowhead = sb.substring(arrowheadIndex + arrowStart,
//				arrowheadIndex + arrowEnd);
//		String style = sb.substring(styleIndex + styleStart, styleIndex
//				+ styleEnd);
//		
//		return arrowhead.equals("open") && style.equals("solid");
//	}
}
