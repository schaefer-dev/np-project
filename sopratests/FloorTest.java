package de.unisaarland.cs.st.pirates.group5.tests;

import org.junit.*;
import static org.junit.Assert.*;

public class FloorTest {

	@Test
	public void testFloor() {
		// Math.floor() is tested
		assertEquals(Math.floor(2.0/3.0), 0.0);
		assertFalse(Math.floor(2.0/3.0 * 1000) == 0);
	}

}
