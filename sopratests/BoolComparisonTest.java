package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.controller.BoolComparison;
import de.unisaarland.cs.st.pirates.group5.controller.Command;
import de.unisaarland.cs.st.pirates.group5.controller.Comparison;
import de.unisaarland.cs.st.pirates.group5.model.Base;
import de.unisaarland.cs.st.pirates.group5.model.BoolWert;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.Register;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.Team;

public class BoolComparisonTest {

	private BoolComparison compare;
	private Ship ship;
	
	private Map map;
	private Field field;
	private Team team;
	
	@Before
	public void setUp(){
		List<Command> commands = new ArrayList<Command>();
		team = new Team('a', commands);
		map = new Map(new Random(), new DummyLogWriter());
		ship = new Ship(team, null, 0, null);	
		field = new Base(map, 0, 0, team, ship);
		ship.setField(field);
	}
	@Test
	public void BoolComparisonTest(){
		
		compare = new BoolComparison(Register.sense_marker0, false);
		ship.setSenseRegister(Register.sense_marker0, BoolWert.True.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new BoolComparison(Register.sense_marker1, true);
		ship.setSenseRegister(Register.sense_marker1, BoolWert.False.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new BoolComparison(Register.sense_marker2, false);
		ship.setSenseRegister(Register.sense_marker2, BoolWert.False.ordinal());
		assertFalse(compare.eval(ship));
		
		compare = new BoolComparison(Register.sense_marker3, false);
		ship.setSenseRegister(Register.sense_marker3, BoolWert.Undefined.ordinal());
		assertFalse(compare.eval(ship));
		
		compare = new BoolComparison(Register.sense_marker4, true);
		ship.setSenseRegister(Register.sense_marker4, BoolWert.Undefined.ordinal());
		assertFalse(compare.eval(ship));
		
		compare = new BoolComparison(Register.sense_marker5, false);
		ship.setSenseRegister(Register.sense_marker5, BoolWert.True.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new BoolComparison(Register.sense_enemymarker, true);
		ship.setSenseRegister(Register.sense_enemymarker, BoolWert.False.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new BoolComparison(Register.sense_enemymarker, true);
		ship.setSenseRegister(Register.sense_enemymarker, BoolWert.True.ordinal());
		assertFalse(compare.eval(ship));
		
		compare = new BoolComparison(Register.sense_shiploaded, true);
		ship.setSenseRegister(Register.sense_shiploaded, BoolWert.False.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new BoolComparison(Register.sense_supply, true);
		ship.setSenseRegister(Register.sense_supply, BoolWert.False.ordinal());
		assertTrue(compare.eval(ship));
	}
	@Test
	public void testSelf()
	{
		ship.setSenseRegister(Register.sense_shiploaded, BoolWert.False.ordinal());
		Comparison comp = new BoolComparison(Register.sense_shiploaded, true);
		assertTrue(comp.eval(ship));
	}
}
