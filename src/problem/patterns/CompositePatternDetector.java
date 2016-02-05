package problem.patterns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import problem.Constants;
import problem.Helpers;
import problem.api.IPatternDetector;

public class CompositePatternDetector implements IPatternDetector {
	private static final String colorString = "yellow";
	private static final String componentLabel = "\\n\\<\\<Decorator\\>\\>";
	private static final String compositeLabel = "\\n\\<\\<Component\\>\\>";
	private static final String leafLabel = "\\n\\<\\<Leaf\\>\\>";
	private boolean componentLabeled = false;
	private HashMap<String, String> component;
	private HashMap<String, String> classCode;
	private List<HashMap<String, String>> classProperties;

	public CompositePatternDetector() {
	}

	@Override
	public void detectPattern(List<HashMap<String, String>> classProperties,
			HashMap<String, String> classCode) {
		this.classProperties = classProperties;
		this.classCode = classCode;
		for (HashMap<String, String> classProps : classProperties) {
			if (checkComposite(classProps)) {
				labelComponent(Helpers.getName(this.component.get("className")));
				labelComposite(classProps.get("className"));
			}
		}
	}
	
	private boolean checkComposite(HashMap<String, String> classProps) {
		this.component = getComponent(classProps);
		String componentName = Helpers.getName(this.component.get("className"));
		int methodEqualCount = 0;
		for (String s : classProps.keySet()) {
			if (s.contains("method")) {
				String method = classProps.get(s);
				String[] methodProps = method.split(":");
				
				String argTypesString = methodProps[2];
				argTypesString = argTypesString.replaceAll("\\[", "");
				argTypesString = argTypesString.replaceAll("\\]", "");

				String[] splitArgs = argTypesString.split(",");
				ArrayList<String> argTypes = new ArrayList<String>();
				for (int i = 0; i < splitArgs.length; i++) {
					argTypes.add(Helpers.getName(splitArgs[i].trim()));
				}
				if (argTypes.contains(componentName) && methodSigInComponent(method)) {
					methodEqualCount++;			
				}
			}
		}
		if (methodEqualCount > 2)
			return true;
		
		return false;
	}
	
	private boolean methodSigInComponent(String method) {
		for (String s : this.component.keySet()) {
			if (s.contains("method")) {
				String componentMethod = this.component.get(s);
				if (componentMethod.equals(method)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private HashMap<String, String> getComponent(HashMap<String, String> classProps) {
		// TODO
		
		return null;
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
			int colorOffset = sb.indexOf(Constants.COLOR_OFFSET);
			sb.replace(colorOffset,
					colorOffset + Constants.COLOR_OFFSET.length(), colorString);
			int labelOffset = sb.indexOf(Constants.LABEL_OFFSET);
			sb.replace(labelOffset,
					labelOffset + Constants.LABEL_OFFSET.length(),
					componentLabel);
			componentLabeled = true;
			this.classCode.put(className, sb.toString());
		}
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
		sb.replace(colorOffset,
				colorOffset + Constants.COLOR_OFFSET.length(), colorString);
		int labelOffset = sb.indexOf(Constants.LABEL_OFFSET);
		sb.replace(labelOffset,
				labelOffset + Constants.LABEL_OFFSET.length(),
				leafLabel);
		this.classCode.put(name, sb.toString());
	}

}
