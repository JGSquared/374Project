package problem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class Helpers {
	
	public static String getName(String path) {
		// takes a path to a class name (e.g. problem/DotGraphDesign) and
		// returns the name with no path (e.g. DotGraphDesign)
		String[] paths = path.split("/");

		String intermediatePath = paths[paths.length - 1];
		String[] intermediatePaths = intermediatePath.split("\\.");
		String newPath = intermediatePaths[intermediatePaths.length - 1];
		if (newPath.indexOf("<") != -1) {
			newPath = newPath.substring(0, newPath.indexOf("<"));
		}
		return newPath;
	}
	
	public static String getAccessSymbol(int access) {
		// returns the proper symbol (e.g. '+', '-', etc.) given an access int
		switch (access) {
		case Opcodes.ACC_PUBLIC:
			return "+";
		case Opcodes.ACC_PRIVATE:
			return "-";
		case Opcodes.ACC_PROTECTED:
			return "#";
		default:
			return "";
		}
	}
	
	public static String getCamelCase(String name) {
		//Takes the name of a class (or anything), and returns the value
		//With the first character set to lower case.
		String firstLetter = name.substring(0, 1);
		return firstLetter.toLowerCase() + name.substring(1);
	}
	
	public static void sortListByNum(ArrayList<String> list, final int nameLength) {
		//Takes a list with strings in the format <String>+<int> and sorts it
		//by the <int>. nameLength tells the sort how to substring list items.
		Collections.sort(list, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				Integer i1 = Integer.parseInt(o1.substring(nameLength));
				Integer i2 = Integer.parseInt(o2.substring(nameLength));
				return i1.compareTo(i2);
			}
		});
	}
	
	public static boolean isClassNameValid(String className) {
		// Performs necessary checks to ensure the className follows correct syntax
		return !className.contains("$");
	}
}
