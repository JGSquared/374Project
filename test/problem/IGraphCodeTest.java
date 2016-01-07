package problem;

import static org.junit.Assert.*;

import java.util.HashMap;

import junit.framework.Assert;

import org.junit.Test;

import problem.api.IGraphCode;

public class IGraphCodeTest {

	@Test
	public final void testGraphUsesCode() {
		IGraphCode codeGetter = new GraphUsesCode();
		HashMap<String, String> items = new HashMap<String, String>();
		StringBuilder expected = new StringBuilder();
		
		items.put("method1", "1:<init>:[String,String]");
		items.put("className", "Test");
		
		//Test bad method name
		assertEquals(expected.toString(), codeGetter.getCode(items));
		
		items.put("method2", "1:TestMethod:[TestClass, int]:ReturnClass");
		
		expected.append("Test -> TestClass [arrowhead=\"open\", style=\"dashed\"" + "];");
		expected.append("Test -> ReturnClass [arrowhead=\"open\", style=\"dashed\"" + "];");
		
		assertEquals(expected.toString(), codeGetter.getCode(items));
	}
	
	@Test
	public final void testGraphMethodCode() {
		IGraphCode codeGetter = new GraphMethodCode();
		HashMap<String, String> items = new HashMap<String, String>();
		StringBuilder expected = new StringBuilder();
		
		items.put("method1", "1:<init>:[String,String]");
		items.put("className", "Test");
		
		//Test bad method name
		assertEquals(expected.toString(), codeGetter.getCode(items));
		
		items.put("method2", "1:TestMethod:[int]:String");
		
		expected.append("+ TestMethod(int) : String\\l");
		
		assertEquals(expected.toString(), codeGetter.getCode(items));
	}
	
}
