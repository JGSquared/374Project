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
	
	@Test
	public final void testGraphFieldCode() {
		IGraphCode codeGetter = new GraphFieldCode();
		HashMap<String, String> items = new HashMap<String, String>();
		StringBuilder expected = new StringBuilder();
		expected.append("|");
		
		items.put("field1", "1:<init>:TestType");
		items.put("className", "Test");
		
		//Test bad method name
		assertEquals(expected.toString(), codeGetter.getCode(items));
		items = new HashMap<String, String>();
		expected = new StringBuilder();
		items.put("field2", "1:Test:String");
		expected.append("+ Test : String\\l|");
		
		assertEquals(expected.toString(), codeGetter.getCode(items));
	}
	
	@Test
	public final void testGraphExtensionAndImplementationCode() {
		IGraphCode codeGetter = new GraphExtensionAndImplementCode();
		HashMap<String, String> items = new HashMap<String, String>();
		StringBuilder expected = new StringBuilder();
		
		items.put("extends", "");
		items.put("className", "TestClass");
		items.put("implements", "[]");

		assertEquals(expected.toString(), codeGetter.getCode(items));
		
		items = new HashMap<String, String>();
		items.put("extends", "TestExtenderClass");
		items.put("className", "TestClass");
		items.put("implements", "[]");
		
		expected.append("TestClass -> TestExtenderClass"
					+ " [arrowhead=\"onormal\", style=\"solid\"" + "];");
		assertEquals(expected.toString(), codeGetter.getCode(items));
		
		items = new HashMap<String, String>();
		expected = new StringBuilder();
		
		items.put("extends", "");
		items.put("className", "TestClass");
		items.put("implements", "[TestInterface]");
		
		expected.append("TestClass -> TestInterface"
				+ " [arrowhead=\"onormal\", style=\"dashed\"" + "];");
		assertEquals(expected.toString(), codeGetter.getCode(items));
	}
	
	@Test
	public final void testGraphDeclarationCode() {
		IGraphCode codeGetter = new GraphDeclarationCode();
		HashMap<String, String> items = new HashMap<String, String>();
		StringBuilder expected = new StringBuilder();
		
		items.put("className", "TestClass");
		items.put("access", "1");
		expected.append("TestClass [\nshape=\"record\",\nlabel = \"{TestClass|");
		
		assertEquals(expected.toString(), codeGetter.getCode(items));
	}
	
	@Test
	public final void testGraphCloserCode() {
		IGraphCode codeGetter = new GraphClassCloserCode();
		HashMap<String, String> items = new HashMap<String, String>();
		StringBuilder expected = new StringBuilder();
		
		expected.append("}\"\n];");
		
		assertEquals(expected.toString(), codeGetter.getCode(items));
	}
}
