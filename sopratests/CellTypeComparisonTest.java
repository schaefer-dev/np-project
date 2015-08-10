package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.controller.CellTypeComparison;
import de.unisaarland.cs.st.pirates.group5.controller.Command;
import de.unisaarland.cs.st.pirates.group5.controller.Operator;
import de.unisaarland.cs.st.pirates.group5.model.Base;
import de.unisaarland.cs.st.pirates.group5.model.CellType;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.Register;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.Team;

public class CellTypeComparisonTest {

	private CellTypeComparison compare;
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
	public void CellTypeComparisonTest0r(){
		
		compare = new CellTypeComparison(Operator.Equal, Register.sense_celltype, CellType.Empty);
		ship.setSenseRegister(Register.sense_celltype, CellType.Empty.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new CellTypeComparison(Operator.Equal, Register.sense_celltype, CellType.EnemyHome);
		ship.setSenseRegister(Register.sense_celltype, CellType.EnemyHome.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new CellTypeComparison(Operator.Equal, Register.sense_celltype, CellType.Island);
		ship.setSenseRegister(Register.sense_celltype, CellType.Island.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new CellTypeComparison(Operator.Unequal, Register.sense_celltype, CellType.Home);
		ship.setSenseRegister(Register.sense_celltype, CellType.Home.ordinal());
		assertFalse(compare.eval(ship));

		compare = new CellTypeComparison(Operator.Unequal, Register.sense_celltype, CellType.Empty);
		ship.setSenseRegister(Register.sense_celltype, CellType.Undefined.ordinal());
		assertFalse(compare.eval(ship));
		
		compare = new CellTypeComparison(Operator.Unequal, Register.sense_celltype, CellType.EnemyHome);
		ship.setSenseRegister(Register.sense_celltype, CellType.Home.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new CellTypeComparison(Operator.Unequal, Register.sense_celltype, CellType.Island);
		ship.setSenseRegister(Register.sense_celltype, CellType.Empty.ordinal());
		assertTrue(compare.eval(ship));
		
		try{
			compare = new CellTypeComparison(Operator.Equal, Register.sense_celltype, CellType.Undefined);
			ship.setSenseRegister(Register.sense_celltype, CellType.EnemyHome.ordinal());
			fail("You failed big time");
		}
		catch(IllegalArgumentException e){}

		compare = new CellTypeComparison(Operator.Equal, Register.sense_celltype, CellType.Empty);
		ship.setSenseRegister(Register.sense_celltype, CellType.Undefined.ordinal());
		assertFalse(compare.eval(ship));
	}
	
	@Test
	public void WrongOperatorTest(){
		
		String message = "Operator undefined for CellTypeComparison";
		
		try{
			compare = new CellTypeComparison(Operator.Less, Register.sense_celltype, CellType.Empty);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			compare = new CellTypeComparison(Operator.Greater, Register.sense_celltype, CellType.Empty);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
	}
	
}
