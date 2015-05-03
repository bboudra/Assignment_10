import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class Assignment_10_Test {
	Assignment_10_Benjamin_Boudra a10;
	@Before
	public void setUp() throws Exception {
		a10 = new Assignment_10_Benjamin_Boudra();
		a10.setNumRout(6);
	}

	@Test
	public void testReadFile1() {
		assertEquals(1,a10.readFile1("badCost1.txt"));
		assertEquals(3,a10.readFile1("badCost2.txt"));
		assertEquals(4,a10.readFile1("badCost3.txt"));
		assertEquals(-1,a10.readFile1("cost.txt"));
	}
	
	@Test
	public void testReadFile2()
	{
		assertEquals(2,a10.readFile2("badSV1.txt"));
		assertEquals(3,a10.readFile2("badSV2.txt"));
		assertEquals(-1,a10.readFile2("source_vectors.txt"));
	}
	
	@Test
	public void testReadFile3()
	{
		assertEquals(2,a10.readFile3("badNV1.txt"));
		assertEquals(5,a10.readFile3("badNV2.txt"));
		assertEquals(-1,a10.readFile3("neighbor_vectors.txt"));
	}
	

}
