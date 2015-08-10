package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.commands.If;
import de.unisaarland.cs.st.pirates.group5.commands.IfAll;
import de.unisaarland.cs.st.pirates.group5.commands.IfAny;
import de.unisaarland.cs.st.pirates.group5.controller.BoolComparison;
import de.unisaarland.cs.st.pirates.group5.controller.Command;
import de.unisaarland.cs.st.pirates.group5.controller.Comparison;
import de.unisaarland.cs.st.pirates.group5.controller.IntComparison;
import de.unisaarland.cs.st.pirates.group5.controller.Operator;
import de.unisaarland.cs.st.pirates.group5.model.Base;
import de.unisaarland.cs.st.pirates.group5.model.BoolWert;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.Register;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.Team;

public class IfTest {

	private If ifCom;
	private IfAll ifAll;
	private IfAny ifAny;
	private Ship ship;
	
	private IntComparison intComp = new IntComparison(Operator.Equal, 4, Register.ship_moral);
	private BoolComparison boComp = new BoolComparison(Register.sense_enemymarker, true);
	private BoolComparison buComp = new BoolComparison(Register.sense_enemymarker, false);
	
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
		ship.setSenseRegister(Register.sense_enemymarker, BoolWert.False.ordinal());
	}
	
	@Test
	public void SimpleIfTest(){
		int i = ship.getPC();
		ifCom = new If(intComp, 5);
		ifCom.execute(ship);
		int j = ship.getPC();
		assertTrue(j == i);
	}
	
	@Test
	public void SimpleIfTest2(){
		ifCom = new If(intComp, 5);
		ship.changeMoral(-2);
		ifCom.execute(ship);
		int j = ship.getPC();
		assertTrue(j == 5);	
	}
	
	@Test
	public void SimpleIfAllTest(){
		List<Comparison> comps = new LinkedList<Comparison>();
		comps.add(boComp);
		comps.add(boComp);
		comps.add(boComp);
		comps.add(boComp);
		int i = ship.getPC();
		ifAll = new IfAll(comps, 10);
		ifAll.execute(ship);
		int j = ship.getPC();
		assertTrue(j == i);
	}
	
	@Test
	public void SimpleIfAllTest2(){
		List<Comparison> comps = new LinkedList<Comparison>();
		comps.add(boComp);
		comps.add(boComp);
		comps.add(buComp);
		comps.add(boComp);
		ifAll = new IfAll(comps, 10);
		ifAll.execute(ship);
		int j = ship.getPC();
		assertTrue(j == 10);
	}
	
	@Test
	public void SimpleIfAnyTest(){
		List<Comparison> comps = new LinkedList<Comparison>();
		comps.add(boComp);
		comps.add(boComp);
		comps.add(boComp);
		comps.add(boComp);
		int i = ship.getPC();
		ifAny = new IfAny(comps, 15);
		ifAny.execute(ship);
		int j = ship.getPC();
		assertTrue(j == i);
	}
	
	@Test
	public void SimpleIfAnyTest2(){
		List<Comparison> comps = new LinkedList<Comparison>();
		comps.add(boComp);
		comps.add(buComp);
		comps.add(buComp);
		comps.add(buComp);
		int i = ship.getPC();
		ifAny = new IfAny(comps, 15);
		ifAny.execute(ship);
		int j = ship.getPC();
		assertTrue(j == i);
	}
	
	@Test
	public void SimpleIfAnyTest3(){
		List<Comparison> comps = new LinkedList<Comparison>();
		comps.add(buComp);
		comps.add(buComp);
		comps.add(buComp);
		comps.add(buComp);
		ifAny = new IfAny(comps, 15);
		ifAny.execute(ship);
		int j = ship.getPC();
		assertTrue(j == 15);
	}
	
	@Test
	public void NullTest(){
		
		String message = "Empty parameters aren't handled";
		
		try{
			ifCom = new If(null, 5);
			fail(message);
		}
		catch(Exception e) {}
		
		try{
			ifAll = new IfAll(null, 5);
			fail(message);
		}
		catch(Exception e) {}
		
		try{
			ifAny = new IfAny(null, 5);
			fail(message);
		}
		catch(Exception e) {}	
	}
	
	@Test
	public void InvalidPCTest(){
		
		String message = "Invalid PC isn't handled";
		
		try{
			ifCom = new If(null, -1);
			fail(message);
		}
		catch(Exception e) {}
		
		try{
			ifAll = new IfAll(null, -1);
			fail(message);
		}
		catch(Exception e) {}
		
		try{
			ifAny = new IfAny(null, -1);
			fail(message);
		}
		catch(Exception e) {}	
	}
}
