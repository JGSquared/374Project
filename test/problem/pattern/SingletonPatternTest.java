package problem.pattern;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import problem.code.GraphDeclarationCode;
import problem.visitor.ClassAssociationVisitor;
import problem.visitor.ClassDeclarationVisitor;
import problem.visitor.ClassFieldVisitor;
import problem.visitor.ClassMethodVisitor;
import problem.visitor.ClassUsesVisitor;

public class SingletonPatternTest {

	@Test
	public void eagerSingletonTest() throws IOException {
		HashMap<String, String> items = new HashMap<String, String>();

		ClassReader reader = new ClassReader("problem.pattern.EagerSingleton");
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, items);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, items);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, items);
		ClassVisitor usesVisitor = new ClassUsesVisitor(Opcodes.ASM5, methodVisitor, items);
		ClassAssociationVisitor associationVisitor = new ClassAssociationVisitor(Opcodes.ASM5, usesVisitor, items);
		reader.accept(associationVisitor, ClassReader.EXPAND_FRAMES);
		items = associationVisitor.getParsedCode();
		
		GraphDeclarationCode guc = new GraphDeclarationCode();
		String generatedCode = guc.getCode(items);
		assertTrue(generatedCode.contains("Singleton"));
	}
	
	@Test
	public void lazySingletonTtest() throws IOException {
		HashMap<String, String> items = new HashMap<String, String>();

		ClassReader reader = new ClassReader("problem.pattern.LazySingleton");
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, items);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, items);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, items);
		ClassVisitor usesVisitor = new ClassUsesVisitor(Opcodes.ASM5, methodVisitor, items);
		ClassAssociationVisitor associationVisitor = new ClassAssociationVisitor(Opcodes.ASM5, usesVisitor, items);
		reader.accept(associationVisitor, ClassReader.EXPAND_FRAMES);
		items = associationVisitor.getParsedCode();
		
		GraphDeclarationCode guc = new GraphDeclarationCode();
		String generatedCode = guc.getCode(items);
		assertTrue(generatedCode.contains("Singleton"));
	}
	
	@Test
	public void threadSafeSingletonTest() throws IOException {
		HashMap<String, String> items = new HashMap<String, String>();

		ClassReader reader = new ClassReader("problem.pattern.ThreadSafeSingleton");
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, items);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, items);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, items);
		ClassVisitor usesVisitor = new ClassUsesVisitor(Opcodes.ASM5, methodVisitor, items);
		ClassAssociationVisitor associationVisitor = new ClassAssociationVisitor(Opcodes.ASM5, usesVisitor, items);
		reader.accept(associationVisitor, ClassReader.EXPAND_FRAMES);
		items = associationVisitor.getParsedCode();
		
		GraphDeclarationCode guc = new GraphDeclarationCode();
		String generatedCode = guc.getCode(items);
		assertTrue(generatedCode.contains("Singleton"));
	}
	
	@Test
	public void incorrectPatternTest() throws IOException {
		HashMap<String, String> items = new HashMap<String, String>();

		ClassReader reader = new ClassReader("problem.pattern.IncorrectPattern");
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, items);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, items);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, items);
		ClassVisitor usesVisitor = new ClassUsesVisitor(Opcodes.ASM5, methodVisitor, items);
		ClassAssociationVisitor associationVisitor = new ClassAssociationVisitor(Opcodes.ASM5, usesVisitor, items);
		reader.accept(associationVisitor, ClassReader.EXPAND_FRAMES);
		items = associationVisitor.getParsedCode();
		
		GraphDeclarationCode guc = new GraphDeclarationCode();
		String generatedCode = guc.getCode(items);
		assertTrue(!generatedCode.contains("Singleton"));
	}
	
	@Test
	public void runtimeSingletonTest() throws IOException {
		HashMap<String, String> items = new HashMap<String, String>();

		ClassReader reader = new ClassReader("java.lang.Runtime");
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, items);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, items);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, items);
		ClassVisitor usesVisitor = new ClassUsesVisitor(Opcodes.ASM5, methodVisitor, items);
		ClassAssociationVisitor associationVisitor = new ClassAssociationVisitor(Opcodes.ASM5, usesVisitor, items);
		reader.accept(associationVisitor, ClassReader.EXPAND_FRAMES);
		items = associationVisitor.getParsedCode();
		
		GraphDeclarationCode guc = new GraphDeclarationCode();
		String generatedCode = guc.getCode(items);
		assertTrue(generatedCode.contains("Singleton"));
	}
	
	@Test
	public void desktopSingletonTest() throws IOException {
		HashMap<String, String> items = new HashMap<String, String>();

		ClassReader reader = new ClassReader("java.awt.Desktop");
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, items);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, items);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, items);
		ClassVisitor usesVisitor = new ClassUsesVisitor(Opcodes.ASM5, methodVisitor, items);
		ClassAssociationVisitor associationVisitor = new ClassAssociationVisitor(Opcodes.ASM5, usesVisitor, items);
		reader.accept(associationVisitor, ClassReader.EXPAND_FRAMES);
		items = associationVisitor.getParsedCode();
		
		GraphDeclarationCode guc = new GraphDeclarationCode();
		String generatedCode = guc.getCode(items);
		System.out.println(generatedCode);
		assertTrue(!generatedCode.contains("Singleton"));
	}
	
	@Test
	public void calendarSingletonTest() throws IOException {
		HashMap<String, String> items = new HashMap<String, String>();

		ClassReader reader = new ClassReader("java.util.Calendar");
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, items);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, items);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, items);
		ClassVisitor usesVisitor = new ClassUsesVisitor(Opcodes.ASM5, methodVisitor, items);
		ClassAssociationVisitor associationVisitor = new ClassAssociationVisitor(Opcodes.ASM5, usesVisitor, items);
		reader.accept(associationVisitor, ClassReader.EXPAND_FRAMES);
		items = associationVisitor.getParsedCode();
		
		GraphDeclarationCode guc = new GraphDeclarationCode();
		String generatedCode = guc.getCode(items);
		System.out.println(generatedCode);
		assertTrue(!generatedCode.contains("Singleton"));
	}

	@Test
	public void fileterInputStreamSingletonTest() throws IOException {
		HashMap<String, String> items = new HashMap<String, String>();

		ClassReader reader = new ClassReader("java.io.FilterInputStream");
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, items);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, items);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, items);
		ClassVisitor usesVisitor = new ClassUsesVisitor(Opcodes.ASM5, methodVisitor, items);
		ClassAssociationVisitor associationVisitor = new ClassAssociationVisitor(Opcodes.ASM5, usesVisitor, items);
		reader.accept(associationVisitor, ClassReader.EXPAND_FRAMES);
		items = associationVisitor.getParsedCode();
		
		GraphDeclarationCode guc = new GraphDeclarationCode();
		String generatedCode = guc.getCode(items);
		System.out.println(generatedCode);
		assertTrue(!generatedCode.contains("Singleton"));
	}
}
