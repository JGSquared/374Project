package problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import problem.api.CodeMapGetters;
import problem.api.IClass;
import problem.api.IField;
import problem.api.IGraphCode;
import problem.api.IGraphDesign;
import problem.api.IMethod;
import problem.api.IPatternDetector;
import problem.graph.component.UMLArrow;
import problem.graph.component.UMLClass;
import problem.graph.component.UMLField;
import problem.graph.component.UMLMethod;

public class DotGraphDesign implements IGraphDesign {
	private StringBuilder sb = new StringBuilder();
	private FileProperties fp = FileProperties.getInstance();
	private List<IGraphCode> codeGetters = new ArrayList<IGraphCode>();
	private HashMap<String, String> classCode = new HashMap<>();
	private List<HashMap<String, String>> classProperties = new ArrayList<>();
	private List<IPatternDetector> patternDetectors = new ArrayList<IPatternDetector>();
	private CodeMapGetters getters;

	@Override
	public void addGraphCode(HashMap<String, String> items) {
		getters = new CodeMapGetters(items);
		List<String> usedList = new ArrayList<String>();
//		StringBuilder graphCode = new StringBuilder();
//		for (int i = 0; i < codeGetters.size(); i++) {
//			graphCode.append(codeGetters.get(i).getCode(getters));
//		}
//		classCode.put(Helpers.getName(items.get("className")), graphCode.toString());
//		this.classProperties.add(items);
		String className = getters.getClassName();
		IClass c = new UMLClass("", className, getters.getAccess(), "", false);
		for (String fieldName : getters.getFieldNames()) {
			IField f = new UMLField(fieldName, getters.getFieldAccess(fieldName), getters.getFieldType(fieldName));
			c.addIField(f);
		}
		for (String methodName : getters.getMethodNames()) {
			IMethod m = new UMLMethod(methodName, getters.getMethodAccess(methodName), getters.getMethodReturnType(methodName));
			String[] argTypes = getters.getMethodArgTypes(methodName);
			for (int i = 0; i < argTypes.length; i++) {
				m.addArgType(argTypes[i]);
			}
			c.addIMethod(m);
		}
		String extendsName = getters.getClassExtends();
		if (!extendsName.equals("") && !fp.whiteList.contains(extendsName) && Helpers.isClassNameValid(extendsName)) {
			c.addIArrow(new UMLArrow(className, extendsName, "", "extends", "onormal", "solid", "", false));
		}
		String[] implementsNames = getters.getClassImplements();
		for (int i = 0; i < implementsNames.length; i++) {
			if (!Helpers.isClassNameValid(implementsNames[i]) || fp.whiteList.contains(implementsNames[i])) {
				continue;
			}
			c.addIArrow(new UMLArrow(className, implementsNames[i], "", "implements", "onormal", "dashed", "", false));
		}
		ArrayList<String> associatedList = getters.getClassAssociates();
		ArrayList<String> fieldList = getters.getFieldNames();
		String type;
		for (String associated : associatedList) {
			type = Helpers.getName(associated);
			if (!Helpers.isClassNameValid(type)) {
				continue;
			}
			if (!type.equals("") && !fp.whiteList.contains(type) && !usedList.contains(type) && !type.equals(className)) {
				c.addIArrow(new UMLArrow(className, type, "", "associated", "open", "solid", "", false));
				usedList.add(type);
			}
		}
		String signature;
		for (String fieldName : fieldList) {
			signature = Helpers.getName(getters.getFieldSignature(fieldName));
			if (!Helpers.isClassNameValid(signature)) {
				continue;
			}
			if (!signature.equals("EMPTY")) {
				if (!signature.equals("") && !fp.whiteList.contains(signature) && !usedList.contains(signature)) {
					c.addIArrow(new UMLArrow(className, signature, "", "associated", "open", "solid", "", false));
					usedList.add(signature);
				}
			}
		}
		
		ArrayList<String> methodList = getters.getMethodNames();
		ArrayList<String> usesList = getters.getClassUses();
		
		String[] argsArray;
		ArrayList<String> argTypes;
		for (String methodName : methodList) {
			argsArray = getters.getMethodArgTypes(methodName);
			argTypes = new ArrayList<String>();
			for (int i = 0; i < argsArray.length; i++) {
				argTypes.add(Helpers.getName(argsArray[i].trim()));
			}
			for (int i = 0; i < argTypes.size(); i++) {
				String argType = argTypes.get(i);
				if (!Helpers.isClassNameValid(argType)) {
					continue;
				}
				if (!argType.equals("") && !fp.whiteList.contains(argType) && !usedList.contains(argType)) {
					c.addIArrow(new UMLArrow(className, argType, "", "uses", "open", "dashed", "", false));
					usedList.add(argType);
				}
			}
		}
		
		String owner;
		for (String uses : usesList) {
			owner = Helpers.getName(uses);
			if (!Helpers.isClassNameValid(owner)) {
				continue;
			}
			if (!owner.equals("") && !fp.whiteList.contains(owner) && !usedList.contains(owner) && !owner.equals(className)) {
				c.addIArrow(new UMLArrow(className, owner, "", "uses", "open", "dashed", "", false));
				usedList.add(owner);
			}
		}
		
		ClassStorage.addIClass(c);
	}
}

//	@Override
//	public void initializeGraph() {
//		sb.append("digraph G{\n");
//		sb.append("rankdir=BT;\n");
//	}
//	
//	@Override
//	public void closeGraph() {
//		for (IPatternDetector detector : this.patternDetectors) {
//			detector.detectPattern(classProperties, classCode);
//		}
//		
//		for (String s : this.classCode.values()) {
//			sb.append(s);
//		}
//		
//		int colorOffset;
//		while ((colorOffset = sb.indexOf(Constants.COLOR_OFFSET)) != -1) {
//			sb.replace(colorOffset, colorOffset + Constants.COLOR_OFFSET.length(), "");
//		}
//		int labelOffset;
//		while ((labelOffset = sb.indexOf(Constants.LABEL_OFFSET)) != -1) {
//			sb.replace(labelOffset, labelOffset + Constants.LABEL_OFFSET.length(), "");
//		}
//		int arrowOffset;
//		while ((arrowOffset = sb.indexOf(Constants.ARROW_OFFSET)) != -1) {
//			sb.replace(arrowOffset, arrowOffset + Constants.ARROW_OFFSET.length(), "");
//		}
//		sb.append("}");
//	}
//
//	@Override
//	public void generateGraph() throws IOException {
//		OutputStream out = new FileOutputStream(fp.fileIn);
//		out.write(sb.toString().getBytes());
//		out.close();
//
//		Runtime rt = Runtime.getRuntime();
//		Process pr = rt
//				.exec(new String[] {
//						"cmd.exe", "/k", "\"" + fp.graphVizPath + "\" " + fp.flags + " " + fp.fileIn + " > " + fp.fileOut});
//
//	}
//
//	@Override
//	public void addCodeGetter(IGraphCode getter) {
//		this.codeGetters.add(getter);
//	}
//
//	@Override
//	public void removeCodeGetter(IGraphCode getter) {
//		this.codeGetters.remove(getter);		
//	}
//	
//	public List<IGraphCode> getCodeGetters() {
//		return this.codeGetters;
//	}
//
//	public StringBuilder getGraphStringBuilder() {
//		return sb;
//	}
//
//	@Override
//	public void useDefaultCodeGetters() {
//		IGraphCode item1 = new GraphDeclarationCode();
//		IGraphCode item2 = new GraphFieldCode();
//		IGraphCode item3 = new GraphMethodCode();
//		IGraphCode item4 = new GraphClassCloserCode();
//		IGraphCode item5 = new GraphExtensionAndImplementCode();
//		IGraphCode item6 = new GraphUsesCode();
//		addCodeGetter(item1);
//		addCodeGetter(item2);
//		addCodeGetter(item3);
//		addCodeGetter(item4);
//		addCodeGetter(item5);
//		addCodeGetter(item6);		
//	}
//
//	@Override
//	public void addPatternDetector(IPatternDetector detector) {
//		this.patternDetectors.add(detector);
//	}
//
//	@Override
//	public void removePatternDetector(IPatternDetector detector) {
//		this.patternDetectors.remove(detector);
//	}
//
//	@Override
//	public void useDefaultPatternDetectors() {
//		addPatternDetector(new SingletonPatternDetector());
//		addPatternDetector(new DecoratorPatternDetector());
//		addPatternDetector(new AdapterPatternDetector());
//		addPatternDetector(new CompositePatternDetector());
//	}
//
//}
