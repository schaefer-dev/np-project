package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.commands.Mark;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.Team;
import de.unisaarland.cs.st.pirates.group5.model.Water;

public class CommandMarkTest {
	
	@Test
	public void TestMarkBuoy(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		Team teamD = new Team('d', null);
		
		
		
		Mark testMark0 = new Mark(0);
		Mark testMark1 = new Mark(1);
		Mark testMark3 = new Mark(3);
		
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(null, testLog);
		Field waterField = new Water(testMap, 0,0, null);
		
		Ship shipA = new Ship(teamA, waterField, 0, null);
		Ship shipB = new Ship(teamB, waterField, 0, null);
		Ship shipC = new Ship(teamC, waterField, 0, null);
		Ship shipD = new Ship(teamD, waterField, 0, null);
		
		testMark0.execute(shipA);
		assertEquals("A bouy should have been added to the List", 1, waterField.getBuoys().size());
		assertTrue(waterField.getBuoys().get(0).getTeam().equals(teamA));	// assuming that we insert in the same order we got the placeBuoys
		assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamB));	
		assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamC));	
		assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamD));	
	}
	
	@Test
	public void TestMark2Buoys01(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		Team teamD = new Team('d', null);
		
		Mark testMark0 = new Mark(0);
		Mark testMark1 = new Mark(1);
		Mark testMark3 = new Mark(3);
		
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(null, testLog);
		Field waterField = new Water(testMap, 0,0, null);
		
		Ship shipA = new Ship(teamA, waterField, 0, null);
		Ship shipB = new Ship(teamB, waterField, 0, null);
		Ship shipC = new Ship(teamC, waterField, 0, null);
		Ship shipD = new Ship(teamD, waterField, 0, null);
		
		testMark0.execute(shipA);
		testMark0.execute(shipB);
		
		try{
			assertTrue(waterField.getBuoys().get(0).getTeam().equals(teamA));	
			assertTrue(waterField.getBuoys().get(1).getTeam().equals(teamB));	// assuming that we insert in the same order we got the placeBuoys
			assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamC));	
			assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamD));	
		}
		catch(IndexOutOfBoundsException error){
			assertTrue("index out of bounds",false);
		}
	}
	
	@Test
	public void TestMark2Buoys02(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		Team teamD = new Team('d', null);
		
		Mark testMark0 = new Mark(0);
		Mark testMark1 = new Mark(1);
		Mark testMark3 = new Mark(3);
		
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(null, testLog);
		Field waterField = new Water(testMap, 0,0, null);
		
		Ship shipA = new Ship(teamA, waterField, 0, null);
		Ship shipB = new Ship(teamB, waterField, 0, null);
		Ship shipC = new Ship(teamC, waterField, 0, null);
		Ship shipD = new Ship(teamD, waterField, 0, null);
		
		testMark0.execute(shipA);
		testMark0.execute(shipA);
		try{
			assertTrue(waterField.getBuoys().get(0).getTeam().equals(teamA));	// assuming that we insert in the same order we got the placeBuoys
			assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamB));	
			assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamC));	
			assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamD));	
			assertTrue(waterField.getBuoys().size()==1);
		}
		catch(IndexOutOfBoundsException error){
			assertTrue("index out of bounds",false);
		}
	}
	
	@Test
	public void TestMark2Buoys03(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		Team teamD = new Team('d', null);
		
		Mark testMark0 = new Mark(0);
		Mark testMark1 = new Mark(1);
		Mark testMark3 = new Mark(3);
		
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(null, testLog);
		Field waterField = new Water(testMap, 0,0, null);
		
		Ship shipA = new Ship(teamA, waterField, 0, null);
		Ship shipB = new Ship(teamB, waterField, 0, null);
		Ship shipC = new Ship(teamC, waterField, 0, null);
		Ship shipD = new Ship(teamD, waterField, 0, null);
		
		testMark0.execute(shipA);
		testMark0.execute(shipA);
		testMark3.execute(shipB);
		testMark3.equals(testMark3);
		testMark3.equals(null);
		
		try{
			assertTrue(waterField.getBuoys().get(0).getTeam().equals(teamA));	// assuming that we insert in the same order we got the placeBuoys
			assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamB));	
			assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamC));	
			assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamD));	
			assertFalse(waterField.getBuoys().get(1).getTeam().equals(teamA));	// assuming that we insert in the same order we got the placeBuoys
			assertTrue(waterField.getBuoys().get(1).getTeam().equals(teamB));	
			assertFalse(waterField.getBuoys().get(1).getTeam().equals(teamC));	
			assertFalse(waterField.getBuoys().get(1).getTeam().equals(teamD));	
			assertTrue(waterField.getBuoys().size()==2);
		}
		catch(IndexOutOfBoundsException error){
			assertTrue("index out of bounds",false);
		}
	}
	
	@Test
	public void TestMark2Buoys04(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		Team teamD = new Team('d', null);
		
		Mark testMark0 = new Mark(0);
		Mark testMark1 = new Mark(1);
		Mark testMark3 = new Mark(3);
		
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(null, testLog);
		Field waterField = new Water(testMap, 0,0, null);
		
		Ship shipA = new Ship(teamA, waterField, 0, null);
		Ship shipB = new Ship(teamB, waterField, 0, null);
		Ship shipC = new Ship(teamC, waterField, 0, null);
		Ship shipD = new Ship(teamD, waterField, 0, null);
		
		testMark0.execute(shipA);
		testMark0.execute(shipA);
		testMark3.execute(shipB);
		testMark0.execute(shipA);
		testMark0.execute(shipC);
		
		try{
			assertTrue(waterField.getBuoys().get(0).getTeam().equals(teamA));	// assuming that we insert in the same order we got the placeBuoys
			assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamB));	
			assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamC));	
			assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamD));	
			assertFalse(waterField.getBuoys().get(1).getTeam().equals(teamA));	// assuming that we insert in the same order we got the placeBuoys
			assertTrue(waterField.getBuoys().get(1).getTeam().equals(teamB));	
			assertFalse(waterField.getBuoys().get(1).getTeam().equals(teamC));	
			assertFalse(waterField.getBuoys().get(1).getTeam().equals(teamD));	
			assertFalse(waterField.getBuoys().get(2).getTeam().equals(teamA));	// assuming that we insert in the same order we got the placeBuoys
			assertFalse(waterField.getBuoys().get(2).getTeam().equals(teamB));	
			assertTrue(waterField.getBuoys().get(2).getTeam().equals(teamC));	
			assertFalse(waterField.getBuoys().get(2).getTeam().equals(teamD));	
			assertTrue(waterField.getBuoys().size()==3);
		}
		catch(IndexOutOfBoundsException error){
			assertTrue("index out of bounds",false);
		}
	}
	
}
