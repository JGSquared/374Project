package problem.pattern;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import problem.ClassDesignParser;
import problem.DotGraphDesign;
import problem.api.IDesignParser;

public class CompositePatternTest {

	@Test
	public void checkCompositeExample() throws IOException {
		IDesignParser dp = new ClassDesignParser();
		DotGraphDesign graphDesigner = new DotGraphDesign();
		
		graphDesigner.useDefaultCodeGetters();
		graphDesigner.useDefaultPatternDetectors();
		String[] args = new String[12];
		args[0] = "problem.sprites.AbstractSprite";
		args[1] = "problem.sprites.CircleSprite";
		args[2] = "problem.sprites.CompositeSprite";
		args[3] = "problem.sprites.CompositeSpriteIterator";
		args[4] = "problem.sprites.CrystalBall";
		args[5] = "problem.sprites.ISprite";
		args[6] = "problem.sprites.NullSpriteIterator";
		args[7] = "problem.sprites.RectangleSprite";
		args[8] = "problem.sprites.RectangleTower";
		args[9] = "problem.sprites.SpriteComponent";
		args[10] = "problem.sprites.SpriteComposite";
		args[11] = "problem.sprites.SpriteFactory";
		dp.parse(args, graphDesigner);
		assertTrue(graphDesigner.getGraphStringBuilder().toString().contains("<Component"));
		assertTrue(graphDesigner.getGraphStringBuilder().toString().contains("<Composite"));
		assertTrue(graphDesigner.getGraphStringBuilder().toString().contains("<Leaf"));
	}
}
