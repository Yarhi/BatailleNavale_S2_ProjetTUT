package Bataille_Navale_UnitTest;

/*import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.Before;
import org.junit.Test;
import Bataille_navale.*;

public class Bateau_Unit_Test {
	
	protected Bateau bateau;
	
	@Before
	public void setUp() {
		bateau = new Bateau(4);
		Point2D[] coord = {new Point2D.Double(0,0), new Point2D.Double(0,1), new Point2D.Double(0,2), new Point2D.Double(0,3)};
		bateau.SetCoord(coord);
	}

	@Test
	public void getName_UnitTest() {
		Bateau testName = new Bateau(5);
		assertEquals(testName.getName() , "Porte-avions");
		testName = new Bateau(4);
		assertEquals(testName.getName() , "Croiseur");
		testName = new Bateau(3);
		assertEquals(testName.getName() , "Sous-marin");
		testName = new Bateau(2);
		assertEquals(testName.getName() , "Torpilleur");
		
	}
	
	@Test
	public void toucher_UnitTest(){
		assertTrue(bateau.toucher(new Point2D.Double(0,0)));
		assertFalse(bateau.toucher(new Point2D.Double(0,4)));
	}

}
*/