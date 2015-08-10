package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.commands.Drop;
import de.unisaarland.cs.st.pirates.group5.commands.Move;
import de.unisaarland.cs.st.pirates.group5.commands.Unmark;
import de.unisaarland.cs.st.pirates.group5.controller.Command;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.Team;

public class TeamTest {

	/*
	 * test addLoot for pointinc i
	 * 
	 * test addShip/deleteShip in ShipList
	 * 
	 * test getCommand which jumps into Tactic[pc] and gives Command
	 * 
	 * test Constructor
	 */
	

	@Test
	public void TestAddLoot01(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		Team teamD = new Team('d', null);
		
		teamA.addLoot(0);
		teamA.addLoot(3);
		
		assertTrue(teamA.getScore()==3);	
	}
	
	public void TestAddLoot02(){
		Team teamA = new Team('a', null);
		
		teamA.addLoot(2);
		teamA.addLoot(3);
		
		assertTrue(teamA.getScore()==5);	
	}
	public void TestAddLoot03(){
		Team teamA = new Team('a', null);
		
		teamA.addLoot(4);
		teamA.addLoot(3);
		teamA.addLoot(0);
		teamA.addLoot(3);
		teamA.addLoot(3);
		teamA.addLoot(3);
		teamA.addLoot(3);
		teamA.addLoot(20);
		teamA.addLoot(1);
		
		assertTrue(teamA.getScore()==40);	
	}
	
	@Test(expected=Exception.class)
	public void TestAddLootExceptionNegative01(){
		Team teamA = new Team('a', null);
		
		teamA.addLoot(1);
		teamA.addLoot(-2);
	}
	
	@Test(expected=Exception.class)
	public void TestAddLootExceptionNegative02(){
		Team teamA = new Team('a', null);
		
		teamA.addLoot(-2);
	}
	
	@Test(expected=Exception.class)
	public void TestAddLootExceptionNegative03(){
		Team teamA = new Team('a', null);
		
		teamA.addLoot(1);
		teamA.addLoot(1);
		teamA.addLoot(1);
		teamA.addLoot(1);
		teamA.addLoot(1);
		teamA.addLoot(-2);
		teamA.addLoot(-2);
		teamA.addLoot(-2);
	}
	
	@Test(expected=Exception.class)
	public void TestAddLootExceptionNegative04(){
		Team teamA = new Team('a', null);
		
		teamA.addLoot(1);
		teamA.addLoot(1);
		teamA.addLoot(-2);
		teamA.addLoot(1);
		teamA.addLoot(-2);
		teamA.addLoot(1);
		teamA.addLoot(1);
	}
	
	@Test
	public void TestShipAddDel01(){
		Team teamA = new Team('a', null);
		Ship shipA = new Ship(teamA,null,0,null);
		Ship shipB = new Ship(teamA,null,1,null);
		Ship shipC = new Ship(teamA,null,2,null);
		Ship shipD = new Ship(teamA,null,3,null);
		teamA.addShip(shipA);
		teamA.addShip(shipB);
		teamA.addShip(shipC);
		teamA.addShip(shipD);
		
		assertTrue(teamA.getShipCount()==4);
	}
	
	@Test
	public void TestShipAddDel02(){
		Team teamA = new Team('a', null);
		Ship shipA = new Ship(teamA,null,0,null);
		Ship shipB = new Ship(teamA,null,1,null);
		Ship shipC = new Ship(teamA,null,2,null);
		Ship shipD = new Ship(teamA,null,3,null);
		teamA.addShip(shipA);
		teamA.addShip(shipB);
		teamA.addShip(shipC);
		teamA.deleteShip(shipA);
		
		assertTrue(teamA.getShipCount()==2);
	}
	
	@Test
	public void TestShipAddDel03(){
		Team teamA = new Team('a', null);
		Ship shipA = new Ship(teamA,null,0,null);
		Ship shipB = new Ship(teamA,null,1,null);
		Ship shipC = new Ship(teamA,null,2,null);
		Ship shipD = new Ship(teamA,null,3,null);
		Ship shipE = new Ship(teamA,null,4,null);
		Ship shipF = new Ship(teamA,null,5,null);
		teamA.addShip(shipA);
		teamA.addShip(shipB);
		teamA.addShip(shipC);
		teamA.deleteShip(shipD);
		teamA.addShip(shipE);
		teamA.addShip(shipF);
		
		assertTrue(teamA.getShipCount()==5);
	}
	
	@Test (expected=Exception.class)
	public void TestShipDelException01(){
		Team teamA = new Team('a', null);
		Ship shipA = new Ship(teamA,null,0,null);
		teamA.deleteShip(shipA);	
	}
	
	@Test (expected=Exception.class)
	public void TestShipDelException02(){
		Team teamA = new Team('a', null);
		Ship shipA = new Ship(teamA,null,0,null);
		Ship shipB = new Ship(teamA,null,1,null);
		teamA.addShip(shipA);
		teamA.deleteShip(shipA);
		teamA.deleteShip(shipB);
		teamA.addShip(shipB);	
	}
	
	@Test
	public void TestGetCommand01(){
		Command commandA= new Move(12);
		Command commandB= new Unmark(3);
		Command commandC= new Drop();
		ArrayList<Command> commandListA=new ArrayList<Command>();
		commandListA.add(commandA);
		commandListA.add(commandB);
		commandListA.add(commandC);
		
		Team teamA = new Team('a', commandListA);
			assertNotNull("commandlist not correctly implemented",teamA.getCommands());
			assertNotNull("commandlist not correctly implemented",teamA.getCommands().get(0));
			assertNotNull("commandlist not correctly implemented",teamA.getCommands().get(1));
			assertNotNull("commandlist not correctly implemented",teamA.getCommands().get(2));
			assertTrue(teamA.getCommands().get(0).equals(commandA));
			assertTrue(teamA.getCommands().get(1).equals(commandB));
			assertTrue(teamA.getCommands().get(2).equals(commandC));
	
	}
	
	@Test
	public void TestCreation01(){
		Team teamA = new Team('a', null);
		
		assertTrue(teamA.getScore()==0);	
		assertTrue(teamA.getName()=='a');
		assertTrue(teamA.getShipCount()==0);
	}
	
	@Test
	public void TestCreation02(){
		Command commandA= new Move(12);
		ArrayList<Command> commandListA=new ArrayList<Command>();
		commandListA.add(commandA);
		
		Team teamA = new Team('a', commandListA);
		
		assertTrue(teamA.getScore()==0);	
		assertTrue(teamA.getName()=='a');
		assertTrue(teamA.getShipCount()==0);
		assertTrue(teamA.getCommands().size()==1);
		assertTrue(teamA.getCommands().get(0).equals(commandA));
	}
	@Test
	public void TestCreation03(){
		Command commandA= new Move(12);
		Command commandB= new Unmark(3);
		Command commandC= new Drop();
		ArrayList<Command> commandListA=new ArrayList<Command>();
		commandListA.add(commandA);
		commandListA.add(commandB);
		commandListA.add(commandC);
		
		Team teamA = new Team('a', commandListA);
		
		assertTrue(teamA.getScore()==0);	
		assertTrue(teamA.getName()=='a');
		assertTrue(teamA.getShipCount()==0);
		assertTrue(teamA.getCommands().size()==3);
		assertTrue(teamA.getCommands().get(0).equals(commandA));
		assertTrue(teamA.getCommands().get(1).equals(commandB));
		assertTrue(teamA.getCommands().get(2).equals(commandC));
	}
}
