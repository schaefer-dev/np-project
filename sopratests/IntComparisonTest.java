package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.controller.Command;
import de.unisaarland.cs.st.pirates.group5.controller.IntComparison;
import de.unisaarland.cs.st.pirates.group5.controller.Operator;
import de.unisaarland.cs.st.pirates.group5.model.Base;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.Register;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.Team;

public class IntComparisonTest {

	private IntComparison compare;
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
	public void CompareDirectionTest(){
	
		compare = new IntComparison(Operator.Equal, Register.ship_direction, 1);
		ship = new Ship(team, field, 0, null);	
		ship.changeDirection(false);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, 1, Register.ship_direction);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(false);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_direction, 2);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(false);
		ship.changeDirection(false);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, 2, Register.ship_direction);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(false);
		ship.changeDirection(false);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_direction, 5);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(false);
		ship.changeDirection(false);
		ship.changeDirection(false);
		ship.changeDirection(false);
		ship.changeDirection(false);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_direction, 5);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(true);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_direction, 3);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(true);
		ship.changeDirection(true);
		ship.changeDirection(true);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, 3, Register.ship_direction);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(true);
		ship.changeDirection(true);
		ship.changeDirection(true);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_direction, 1);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(true);
		assertFalse(compare.eval(ship));

		compare = new IntComparison(Operator.Equal, Register.ship_direction, 2);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(true);
		ship.changeDirection(true);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_direction, 4);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(false);
		ship.changeDirection(false);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, 4, Register.ship_direction);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(false);
		ship.changeDirection(false);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Unequal, Register.ship_direction, 5);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(true);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Unequal, 4, Register.ship_direction);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(false);
		ship.changeDirection(false);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Unequal, Register.ship_direction, 4);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(false);
		ship.changeDirection(false);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Greater, Register.ship_direction, 3);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(true);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Greater, 3, Register.ship_direction);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(true);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Greater, Register.ship_direction, 2);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(false);
		ship.changeDirection(false);
		ship.changeDirection(false);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Greater, Register.ship_direction, 3);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(false);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Less, Register.ship_direction, 2);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(false);
		ship.changeDirection(false);
		ship.changeDirection(false);
		ship.changeDirection(false);
		ship.changeDirection(false);
		ship.changeDirection(false);
		ship.changeDirection(false);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Less, 2, Register.ship_direction);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(false);
		ship.changeDirection(false);
		ship.changeDirection(false);
		ship.changeDirection(false);
		ship.changeDirection(false);
		ship.changeDirection(false);
		ship.changeDirection(false);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Less, Register.ship_direction, 5);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(true);
		ship.changeDirection(true);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Less, 5, Register.ship_direction);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(true);
		ship.changeDirection(true);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Less, Register.ship_direction, 3);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(true);
		ship.changeDirection(true);
		ship.changeDirection(true);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Less, 3, Register.ship_direction);
		ship = new Ship(team, field, 0, null);
		ship.changeDirection(true);
		ship.changeDirection(true);
		ship.changeDirection(true);
		assertFalse(compare.eval(ship));
	}
	
	@Test
	public void CompareLoadTest(){

		compare = new IntComparison(Operator.Equal, Register.ship_load, 4);
		ship.setLoad(4);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_load, 4);
		ship.setLoad(4);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, 4, Register.ship_load);
		ship.setLoad(4);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_load, 0);
		ship.setLoad(0);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_load, 5);
		ship.setLoad(2);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, 5, Register.ship_load);
		ship.setLoad(2);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_load, -3);
		ship.setLoad(4);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, -3, Register.ship_load);
		ship.setLoad(4);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_load, 666);
		ship.setLoad(1);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Unequal, 4, Register.ship_load);
		ship.setLoad(4);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Unequal, Register.ship_load, 5);
		ship.setLoad(2);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Unequal, 4, Register.ship_load);
		ship.setLoad(4);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Greater, Register.ship_load, 666);
		ship.setLoad(4);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Greater, Register.ship_load, 4);
		ship.setLoad(2);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Greater, 4, Register.ship_load);
		ship.setLoad(2);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Greater, Register.ship_load, 2);
		ship.setLoad(4);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Greater, Register.ship_load, 3);
		ship.setLoad(4);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Greater, 3, Register.ship_load);
		ship.setLoad(4);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Less, Register.ship_load, 2);
		ship.setLoad(4);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Less, 2, Register.ship_load);
		ship.setLoad(4);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Less, Register.ship_load, 1);
		ship.setLoad(2);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Less, Register.ship_load, 3);
		ship.setLoad(1);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Less, 3, Register.ship_load);
		ship.setLoad(1);
		assertFalse(compare.eval(ship));
		
	}
	
	@Test
	public void CompareMoralTest(){

		compare = new IntComparison(Operator.Equal, Register.ship_moral, 0);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(-10);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_moral, 4);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(10);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_moral, 3);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(-1);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_moral, 1);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(-3);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, 1, Register.ship_moral);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(-3);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_moral, 0);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(-4);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_moral, 5);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(2);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, 5, Register.ship_moral);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(2);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_moral, -3);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(3);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_moral, 666);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(4);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Unequal, Register.ship_moral, -3);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(3);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Unequal, 1, Register.ship_moral);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(-3);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Unequal, Register.ship_moral, 1);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(-3);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Greater, Register.ship_moral, 666);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(4);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Greater, Register.ship_moral, 4);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(-2);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Greater, 4, Register.ship_moral);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(-2);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Greater, Register.ship_moral, 2);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(-1);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Greater, Register.ship_moral, 3);
		ship = new Ship(team, field, 0, null);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Greater, 3, Register.ship_moral);
		ship = new Ship(team, field, 0, null);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Less, Register.ship_moral, 2);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(-1);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Less, 2, Register.ship_moral);
		ship = new Ship(team, field, 0, null);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Less, Register.ship_moral, 1);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(-2);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Less, Register.ship_moral, 3);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(-3);
		assertTrue(compare.eval(ship));		
	}
	
	@Test
	public void CompareConditionTest(){
		
		compare = new IntComparison(Operator.Equal, Register.ship_condition, 2);
		ship = new Ship(team, field, 0, null);
		ship.changeCondition(-1);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_condition, 3);
		ship = new Ship(team, field, 0, null);
		ship.changeCondition(100);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_condition, 0);
		ship = new Ship(team, field, 0, null);
		ship.changeCondition(-1);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, 1, Register.ship_condition);
		ship = new Ship(team, field, 0, null);
		ship.changeCondition(-1);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, 0, Register.ship_condition);
		ship = new Ship(team, field, 0, null);
		ship.changeCondition(-1);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Unequal, Register.ship_condition, 2);
		ship = new Ship(team, field, 0, null);
		ship.changeCondition(-1);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Unequal, 3, Register.ship_condition);
		ship = new Ship(team, field, 0, null);
		ship.changeCondition(-1);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Greater, 3, Register.ship_condition);
		ship = new Ship(team, field, 0, null);
		ship.changeCondition(-1);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Less, 2, Register.ship_condition);
		ship = new Ship(team, field, 0, null);
		assertTrue(compare.eval(ship));
		
	}
	
	@Test
	public void CompareRegisterTest(){
		
		compare = new IntComparison(Operator.Equal, Register.ship_direction, Register.ship_condition);
		ship = new Ship(team, field, 0, null);
		ship.changeCondition(-1);
		ship.changeDirection(false);
		ship.changeDirection(false);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Unequal, Register.ship_direction, Register.ship_moral);
		ship = new Ship(team, field, 0, null);
		ship.changeMoral(-1);
		ship.changeDirection(false);
		ship.changeDirection(false);
		ship.changeDirection(false);
		ship.changeDirection(false);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Less, Register.ship_condition, Register.ship_moral);
		ship = new Ship(team, field, 0, null);
		ship.changeCondition(-1);
		ship.changeMoral(-1);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Greater, Register.ship_load, Register.ship_moral);
		ship = new Ship(team, field, 0, null);
		ship.setLoad(3);
		ship.changeMoral(-3);
		assertTrue(compare.eval(ship));
	}
}
