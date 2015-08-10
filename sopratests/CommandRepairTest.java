package de.unisaarland.cs.st.pirates.group5.tests;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.commands.Repair;
import de.unisaarland.cs.st.pirates.group5.controller.Command;
import de.unisaarland.cs.st.pirates.group5.model.Base;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.Team;
import de.unisaarland.cs.st.pirates.group5.model.Water;
import de.unisaarland.cs.st.pirates.logger.LogWriter;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;
import static org.junit.Assert.*;

public class CommandRepairTest {
	
	private class DummyMap extends Map{

		public DummyMap(LogWriter log) {
			super(new Random(), log);
		}
		
		public LogWriter getLogWriter(){
			return this.logWriter;
	}
	}
	
	
	Team teamA;
	Ship ship;
	DummyLogWriter log;
	DummyMap map;
	Field field;
	Command repair;
	public CommandRepairTest()
	{	
	}
	
	@Before
	public void setUp(){
		teamA = new Team('a', null);
		ship = new Ship(teamA, null, 0, null);
		teamA.addShip(ship);
		log = new DummyLogWriter();
		map = new DummyMap(log);
		field = new Base(map,0,0,teamA, ship);
		ship.setField(field);
		repair = new Repair(12);
	}
	
	@Test
	public void testWorkingRepair()
	{
		assertNotNull("teamA is Null", teamA);
		teamA.addLoot(2);
		ship.changeCondition(-2);
		assertEquals("changeCondition did not work properly", 1, ship.getCondition());
		log.entities.clear();
		log.values.clear();
		log.what.clear();
		repair.execute(ship);
		assertEquals("repair does not pay the price from the teams loot", 0, teamA.getScore());
		assertEquals("repair did not repair the ship properly. The ship's condition is not 3.", 3, ship.getCondition());
		assertEquals("ship does not pause for 4 rounds after beeing repaired",4,ship.getPause());
		assertEquals("Programm counter should not have been changed.", 0, ship.getPC());
		assertTrue("not the right logger Methods where called",log.what.size()==3 && log.what.remove("changeScore") && log.what.remove("notify") && log.what.remove("notify"));
		assertTrue("during logging the wrong entity was passed on.", log.entities.size()==2 && log.entities.remove(Entity.SHIP) && log.entities.remove(Entity.SHIP));
		List<Integer> vals = log.getValues();
		Integer nul = new Integer (0);
		assertTrue(vals.remove(nul) && vals.remove(nul) && vals.remove(nul) && vals.remove(nul) && vals.remove(new Integer(3)) && vals.remove(new Integer(Key.CONDITION.ordinal())) && vals.remove(new Integer(Key.RESTING.ordinal())) && vals.remove(new Integer(4))); // tests if the ids of Team and Ship and the Ship again as well as the Key.ordinal for Condition and Resting and the new values of score and condition and resting where logged
	}
	@Test
	public void testNonWorkingRepair()
	{
		teamA.addLoot(1);
		ship.changeCondition(-2);
		assertEquals("changeCondition did not work properly", 1, ship.getCondition());
		log.entities.clear();
		log.values.clear();
		log.what.clear();
		repair.execute(ship);
		assertEquals("Team can't pay for repair, so the Score should not have changed, but did.", 1, teamA.getScore());
		assertEquals("Condition of ship should not have changed.", 1, ship.getCondition());
		assertEquals("Ship should not have to pause because repair failed.",0,ship.getPause());
		assertEquals("Programm counter should have been changed to 12", 12, ship.getPC());
		
		
	}
	@Test
	public void testWrongField()
	{
		field = new Water(map, 0,0, null);
		field.setShip(ship);
		ship.setField(field);
		teamA.addLoot(4);
		log.entities.clear();
		log.values.clear();
		log.what.clear();
		repair.execute(ship);
		assertEquals("Team payed for repair which should not have happend.", 4, teamA.getScore());
		assertEquals("Condition of ship should not have changed.", 3, ship.getCondition());
		assertEquals("Ship should not have to pause because repair failed.",0,ship.getPause());
		assertEquals("Programm counter should have been changed to 12", 12, ship.getPC());
		
	}
	
	@Test
	public void testWorkingRepairOfUndamagedShip()
	{
		teamA.addLoot(3);
		assertEquals("At the begin ship should have condition 3", 3, ship.getCondition());
		log.entities.clear();
		log.values.clear();
		log.what.clear();
		repair.execute(ship);
		assertEquals("repair does not pay the price from the teams loot", 1, teamA.getScore());
		assertEquals("Repair should not have changed ship's condition, since ship was undamaged.", 3, ship.getCondition());
		assertEquals("ship does not pause for 4 rounds after beeing repaired",4,ship.getPause());
		assertEquals("Programm counter should not have been changed.", 0, ship.getPC());
		assertTrue("not the right or to many or to few logger Methods where called",log.what.size()==2 && log.what.remove("changeScore")  && log.what.remove("notify"));
		assertTrue("during logging the wrong entity was passed on.", log.entities.size()==1 && log.entities.remove(Entity.SHIP));
		List<Integer> vals = log.getValues();
		Integer nul = new Integer (0);
		assertTrue(vals.remove(nul) && vals.remove(nul) && vals.remove(new Integer(1)) && vals.remove(new Integer(Key.RESTING.ordinal())) && vals.remove(new Integer(4))); // tests if the ids of Team and the ship as well as the Key.ordinal for Resting and the new values of score and resting where logged
	}
}
