package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.commands.Goto;
import de.unisaarland.cs.st.pirates.group5.controller.Command;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.Team;
import de.unisaarland.cs.st.pirates.group5.model.Water;

public class CommandGotoTest {
	int pc = 5;
	Goto go;
	List<Command> goList = new ArrayList<Command>();
	Team test;
	Ship ship;
	Field field;
	Map map;
	
	@Before
	public void setUp(){
		go = new Goto(pc);
		goList.add(go);
		test = new Team('a', goList);
		ship = new Ship(test,null,1,null);
		map = new Map(null, new DummyLogWriter());
		field = new Water(map, 0, 0, null);
		field.setShip(ship);
		ship.setField(field);
	}

	@Test
	public void test() {
		ship.act();
		assertEquals("Falscher PC.",pc, ship.getPC());
	}
	
	@Test
	public void negativTest() {
		try{
		new Goto(-4);
		fail("Goto should not be creatable with negative PC");
		}
		catch(IllegalArgumentException e)
		{}
	}

}
