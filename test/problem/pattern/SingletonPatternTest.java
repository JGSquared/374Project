package problem.pattern;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import problem.ClassDesignParser;
import problem.DotGraphDesign;
import problem.api.IDesignParser;
import problem.api.IGraphDesign;
import problem.code.GraphDeclarationCode;
import problem.visitor.ClassAssociationVisitor;
import problem.visitor.ClassDeclarationVisitor;
import problem.visitor.ClassFieldVisitor;
import problem.visitor.ClassMethodVisitor;
import problem.visitor.ClassUsesVisitor;

public class SingletonPatternTest {

	@Test
	public void eagerSingletonTest() throws IOException {
		IDesignParser dp = new ClassDesignParser();
		DotGraphDesign graphDesigner = new DotGraphDesign();
		
		graphDesigner.useDefaultCodeGetters();
		graphDesigner.useDefaultPatternDetectors();
		String[] args = new String[1];
		args[0] = "problem.pattern.EagerSingleton";
		dp.parse(args, graphDesigner);
		assertTrue(graphDesigner.getGraphStringBuilder().toString().contains("<Singleton"));
	}
	
	@Test
	public void lazySingletonTest() throws IOException {
		
		IDesignParser dp = new ClassDesignParser();
		DotGraphDesign graphDesigner = new DotGraphDesign();
		
		graphDesigner.useDefaultCodeGetters();
		graphDesigner.useDefaultPatternDetectors();
		String[] args = new String[1];
		args[0] = "problem.pattern.LazySingleton";
		dp.parse(args, graphDesigner);
		assertTrue(graphDesigner.getGraphStringBuilder().toString().contains("<Singleton"));
	}
	
	@Test
	public void threadSafeSingletonTest() throws IOException {
		
		IDesignParser dp = new ClassDesignParser();
		DotGraphDesign graphDesigner = new DotGraphDesign();
		
		graphDesigner.useDefaultCodeGetters();
		graphDesigner.useDefaultPatternDetectors();
		String[] args = new String[1];
		args[0] = "problem.pattern.ThreadSafeSingleton";
		dp.parse(args, graphDesigner);
		assertTrue(graphDesigner.getGraphStringBuilder().toString().contains("<Singleton"));
	}
	
	@Test
	public void incorrectPatternTest() throws IOException {
		
		IDesignParser dp = new ClassDesignParser();
		DotGraphDesign graphDesigner = new DotGraphDesign();
		
		graphDesigner.useDefaultCodeGetters();
		graphDesigner.useDefaultPatternDetectors();
		String[] args = new String[1];
		args[0] = "problem.pattern.IncorrectPattern";
		dp.parse(args, graphDesigner);
		assertTrue(!graphDesigner.getGraphStringBuilder().toString().contains("<Singleton"));
	}
	
	@Test
	public void runtimeSingletonTest() throws IOException {
		
		IDesignParser dp = new ClassDesignParser();
		DotGraphDesign graphDesigner = new DotGraphDesign();
		
		graphDesigner.useDefaultCodeGetters();
		graphDesigner.useDefaultPatternDetectors();
		String[] args = new String[1];
		args[0] = "java.lang.Runtime";
		dp.parse(args, graphDesigner);
		assertTrue(graphDesigner.getGraphStringBuilder().toString().contains("<Singleton"));
	}
	
	@Test
	public void desktopSingletonTest() throws IOException {
		
		IDesignParser dp = new ClassDesignParser();
		DotGraphDesign graphDesigner = new DotGraphDesign();
		
		graphDesigner.useDefaultCodeGetters();
		graphDesigner.useDefaultPatternDetectors();
		String[] args = new String[1];
		args[0] = "java.awt.Desktop";
		dp.parse(args, graphDesigner);
		assertTrue(!graphDesigner.getGraphStringBuilder().toString().contains("<Singleton"));
	}
	
	@Test
	public void calendarSingletonTest() throws IOException {
		
		IDesignParser dp = new ClassDesignParser();
		DotGraphDesign graphDesigner = new DotGraphDesign();
		
		graphDesigner.useDefaultCodeGetters();
		graphDesigner.useDefaultPatternDetectors();
		String[] args = new String[1];
		args[0] = "java.util.Calendar";
		dp.parse(args, graphDesigner);
		assertTrue(!graphDesigner.getGraphStringBuilder().toString().contains("<Singleton"));
	}

	@Test
	public void fileterInputStreamSingletonTest() throws IOException {
		
		IDesignParser dp = new ClassDesignParser();
		DotGraphDesign graphDesigner = new DotGraphDesign();
		
		graphDesigner.useDefaultCodeGetters();
		graphDesigner.useDefaultPatternDetectors();
		String[] args = new String[1];
		args[0] = "java.io.FilterInputStream";
		dp.parse(args, graphDesigner);
		assertTrue(!graphDesigner.getGraphStringBuilder().toString().contains("<Singleton"));
	}
}
