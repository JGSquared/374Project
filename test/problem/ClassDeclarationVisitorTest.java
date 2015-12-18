package problem;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

public class ClassDeclarationVisitorTest {

	@Test
	public final void testClassDeclarationVisitor() {
		ClassDeclarationVisitor visitor = new ClassDeclarationVisitor(Opcodes.ASM5, new HashMap<String, String>());
		
		visitor.visit(1, 2, "name", "signature", "superName", new String[] {"interface"});
		
		HashMap<String, String> parsedCode = visitor.getParsedCode();
		
		assertTrue(parsedCode.containsKey("className"));
		assertTrue(parsedCode.containsKey("access"));
		assertTrue(parsedCode.containsKey("extends"));
		assertTrue(parsedCode.containsKey("implements"));
	}
	
}
