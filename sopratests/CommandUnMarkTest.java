package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.commands.Unmark;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.Team;
import de.unisaarland.cs.st.pirates.group5.model.Water;

public class CommandUnMarkTest {
	
	@Test
	public void TestUnMarkBuoy(){
		Team teamA = new Team('a', null);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(null, testLog);
		Field waterField = new Water(testMap, 0,0, null);
		waterField.placeBuoy(0, teamA);
		assertTrue("placebuy error",waterField.getBuoys().size()==1);
		
		Ship shipA = new Ship(teamA, waterField, 0, null);
		Unmark testUnmark = new Unmark(0);
		testUnmark.execute(shipA);
		assertTrue(waterField.getBuoys().size()==0);	
	}
	
	@Test
	public void TestUnMark2Buoys01(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(null, testLog);
		Field waterField = new Water(testMap, 0,0, null);
		waterField.placeBuoy(0, teamA);
		waterField.placeBuoy(2, teamB);
		waterField.placeBuoy(0, teamC);
		assertTrue("placebuy error",waterField.getBuoys().size()==3);
		
		Ship shipB = new Ship(teamB, waterField, 0, null);
		Unmark testUnmark = new Unmark(2);
		testUnmark.execute(shipB);
		assertTrue(waterField.getBuoys().size()==2);	
	}
	
	@Test
	public void TestUnMark2Buoys02(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(null, testLog);
		Field waterField = new Water(testMap, 0,0, null);
		waterField.placeBuoy(0, teamA);
		waterField.placeBuoy(2, teamB);
		waterField.placeBuoy(0, teamC);
		assertTrue("placebuy error",waterField.getBuoys().size()==3);
		Ship shipB = new Ship(teamB, waterField, 0, null);
		Ship shipC = new Ship(teamC, waterField, 0, null);
		Unmark testUnmark = new Unmark(1);
		testUnmark.execute(shipB);
		testUnmark.execute(shipC);
		assertTrue(waterField.getBuoys().size()==3);	
	}
	
	@Test
	public void TestUnMark2Buoys03(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(null, testLog);
		Field waterField = new Water(testMap, 0,0, null);
		waterField.placeBuoy(0, teamA);
		waterField.placeBuoy(2, teamB);
		waterField.placeBuoy(2, teamC);
		waterField.placeBuoy(0, teamA);
		assertTrue("placebuy error",waterField.getBuoys().size()==3);
		Ship shipA = new Ship(teamA, waterField, 0, null);
		Ship shipB = new Ship(teamB, waterField, 0, null);
		Ship shipC = new Ship(teamC, waterField, 0, null);
		Unmark testUnmark = new Unmark(0);
		Unmark testUnmark2 = new Unmark(2);
		testUnmark2.execute(shipB);
		testUnmark2.execute(shipC);
		testUnmark.execute(shipA);
		assertTrue(waterField.getBuoys().size()==0);	
	}
	
	@Test
	public void TestUnMark2Buoys04(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		Team teamD = new Team('d', null);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(null, testLog);
		Field waterField = new Water(testMap, 0,0, null);
		waterField.placeBuoy(0, teamA);
		waterField.placeBuoy(2, teamB);
		waterField.placeBuoy(2, teamC);
		waterField.placeBuoy(0, teamA);
		assertTrue("placebuy error",waterField.getBuoys().size()==3);	
		Ship shipA = new Ship(teamA, waterField, 0, null);
		Ship shipB = new Ship(teamB, waterField, 0, null);
		Ship shipC = new Ship(teamC, waterField, 0, null);
		Ship shipD = new Ship(teamD, waterField, 0, null);
		Unmark testUnmark = new Unmark(0);
		Unmark testUnmark2 = new Unmark(2);
		testUnmark2.execute(shipB);
		testUnmark2.execute(shipC);
		testUnmark.execute(shipA);
		testUnmark.execute(shipD);
		assertTrue(waterField.getBuoys().size()==0);	
	}
	
	@Test
	public void TestUnMark2Buoys05(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		Team teamD = new Team('d', null);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(null, testLog);
		Field waterField = new Water(testMap, 0,0, null);
		assertTrue("Water constructor failed",waterField.getBuoys().size()==0);
		Ship shipA = new Ship(teamA, waterField, 0, null);
		Ship shipB = new Ship(teamB, waterField, 0, null);
		Ship shipC = new Ship(teamC, waterField, 0, null);
		Ship shipD = new Ship(teamD, waterField, 0, null);
		Unmark testUnmark = new Unmark(0);
		Unmark testUnmark2 = new Unmark(2);
		testUnmark.equals(testUnmark);
		testUnmark.equals(null);
		testUnmark2.execute(shipB);
		testUnmark2.execute(shipC);
		testUnmark.execute(shipA);
		testUnmark.execute(shipD);
		assertTrue(waterField.getBuoys().size()==0);	
		//assertTrue(waterField.getBuoys()==null);		//depending on implementation, might not be null if implemented in array?
	}
	
}
