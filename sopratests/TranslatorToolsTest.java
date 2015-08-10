package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.controller.BoolComparison;
import de.unisaarland.cs.st.pirates.group5.controller.CellTypeComparison;
import de.unisaarland.cs.st.pirates.group5.controller.IntComparison;
import de.unisaarland.cs.st.pirates.group5.controller.ShipTypeComparison;
import de.unisaarland.cs.st.pirates.group5.controller.TranslatorTools;

public class TranslatorToolsTest {
	TranslatorTools toolBox = new TranslatorTools();

	@Test
	public void test() {
		/**testes bool comparisons**/
		if(!(toolBox.buildComparison("sense_shiploaded") instanceof BoolComparison))
			fail("BoolComparison not build.");
		if(!(toolBox.buildComparison("sense_supply") instanceof BoolComparison))
			fail("BoolComparison not build.");
		if(!(toolBox.buildComparison("!sense_treasure") instanceof BoolComparison))
			fail("BoolComparison not build.");
		if(!(toolBox.buildComparison("sense_enemymarker") instanceof BoolComparison))
			fail("BoolComparison not build.");
		if(!(toolBox.buildComparison("sense_marker0") instanceof BoolComparison))
			fail("BoolComparison not build.");
		if(!(toolBox.buildComparison("sense_marker1") instanceof BoolComparison))
			fail("BoolComparison not build.");
		if(!(toolBox.buildComparison("sense_marker2") instanceof BoolComparison))
			fail("BoolComparison not build.");
		if(!(toolBox.buildComparison("sense_marker3") instanceof BoolComparison))
			fail("BoolComparison not build.");
		if(!(toolBox.buildComparison("sense_marker4") instanceof BoolComparison))
			fail("BoolComparison not build.");
		if(!(toolBox.buildComparison("sense_marker5") instanceof BoolComparison))
			fail("BoolComparison not build.");
		
		/**testes shiptype comparisons**/
		if(!(toolBox.buildComparison("sense_shiptype==enemy") instanceof ShipTypeComparison))
			fail("ShipTypeComparison not build.");
		if(!(toolBox.buildComparison("friend!=sense_shiptype") instanceof ShipTypeComparison))
			fail("ShipTypeComparison not build.");
		if(toolBox.buildComparison("sense_shiptype") instanceof ShipTypeComparison)
			fail("ShipTypeComparison should not be build.");
		
		/**testes celltype comparisons**/
		if(!(toolBox.buildComparison("sense_celltype!=empty") instanceof CellTypeComparison))
			fail("ShipTypeComparison not build.");
		if(!(toolBox.buildComparison("island==sense_celltype") instanceof CellTypeComparison))
			fail("ShipTypeComparison not build.");
		
		/**testes int comparisons**/
		if(!(toolBox.buildComparison("sense_shipcondition==2") instanceof IntComparison))
			fail("ShipTypeComparison not build.");
		if(!(toolBox.buildComparison("sense_shipdirection==sense_shipdirection") instanceof IntComparison))
			fail("ShipTypeComparison not build.");
		if(!(toolBox.buildComparison("ship_condition<2") instanceof IntComparison))
			fail("ShipTypeComparison not build.");
		if(!(toolBox.buildComparison("4>ship_direction") instanceof IntComparison))
			fail("ShipTypeComparison not build.");
		if(!(toolBox.buildComparison("ship_load!=2") instanceof IntComparison))
			fail("ShipTypeComparison not build.");
		if(!(toolBox.buildComparison("4!=ship_moral") instanceof IntComparison))
			fail("ShipTypeComparison not build.");
		
	}

}
