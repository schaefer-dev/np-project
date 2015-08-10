package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.controller.Command;
import de.unisaarland.cs.st.pirates.group5.controller.Operator;
import de.unisaarland.cs.st.pirates.group5.controller.ShipTypeComparison;
import de.unisaarland.cs.st.pirates.group5.model.Base;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.Register;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.ShipType;
import de.unisaarland.cs.st.pirates.group5.model.Team;

public class ShipTypeComparisonTest {

	private ShipTypeComparison compare;
	private Ship ship;
	
	private Map map;
	private Field field;
	private Team team;
	
	@Before
	public void setUp() throws ArrayIndexOutOfBoundsException, NullPointerException, IOException{
		List<Command> commands = new ArrayList<Command>();
		team = new Team('a', commands);
		map = new Map(new Random(), new DummyLogWriter());
		ship = new Ship(team, null, 0, null);	
		field = new Base(map, 0, 0, team, ship);
		ship.setField(field);
	}
	
	@Test
	public void ShipTypeTest0r(){
		
		compare = new ShipTypeComparison(Operator.Equal, Register.sense_shiptype, ShipType.Enemy);
		ship.setSenseRegister(Register.sense_shiptype, ShipType.Friend.ordinal());
		assertFalse(compare.eval(ship));
		
		try{
			compare = new ShipTypeComparison(Operator.Equal, Register.sense_shiptype, ShipType.Undefined);
			ship.setSenseRegister(Register.sense_shiptype, ShipType.Friend.ordinal());
			fail("Dude...");
		}
		catch(IllegalArgumentException e) {}

		
		compare = new ShipTypeComparison(Operator.Unequal, Register.sense_shiptype, ShipType.Friend);
		ship.setSenseRegister(Register.sense_shiptype, ShipType.Undefined.ordinal());
		assertFalse(compare.eval(ship));
		
		compare = new ShipTypeComparison(Operator.Equal, Register.sense_shiptype, ShipType.Friend);
		ship.setSenseRegister(Register.sense_shiptype, ShipType.Undefined.ordinal());
		assertFalse(compare.eval(ship));
		
		compare = new ShipTypeComparison(Operator.Equal, Register.sense_shiptype, ShipType.Friend);
		ship.setSenseRegister(Register.sense_shiptype, ShipType.Friend.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new ShipTypeComparison(Operator.Equal, Register.sense_shiptype, ShipType.Enemy);
		ship.setSenseRegister(Register.sense_shiptype, ShipType.Enemy.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new ShipTypeComparison(Operator.Unequal, Register.sense_shiptype, ShipType.Enemy);
		ship.setSenseRegister(Register.sense_shiptype, ShipType.Enemy.ordinal());
		assertFalse(compare.eval(ship));
		
		compare = new ShipTypeComparison(Operator.Unequal, Register.sense_shiptype, ShipType.Friend);
		ship.setSenseRegister(Register.sense_shiptype, ShipType.Enemy.ordinal());
		assertTrue(compare.eval(ship));
	}
	
	@Test
	public void WrongOperatorTest(){
		
		String message = "Operator undefined for ShipTypeComparison";
		
		try{
			compare = new ShipTypeComparison(Operator.Less, Register.sense_shiptype, ShipType.Enemy);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			compare = new ShipTypeComparison(Operator.Greater, Register.sense_shiptype, ShipType.Enemy);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
	}
}
