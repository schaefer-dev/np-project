package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.commands.Drop;
import de.unisaarland.cs.st.pirates.group5.commands.Goto;
import de.unisaarland.cs.st.pirates.group5.controller.Command;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.Register;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.Team;
import de.unisaarland.cs.st.pirates.group5.model.Water;


public class ShipTest {
	
	
	private Ship ship;
	private Ship shipv;
	private Ship shipn;
	
	private Field field;
	private Ship sship;
	
	private Register shipdirection = Register.ship_direction;
	private Register shipload = Register.ship_load;
	private Register shipmoral= Register.ship_moral;
	private Register shipcondition = Register.ship_condition;
	private Register sensecelltype = Register.sense_celltype;
	private Register sensetreasure = Register.sense_treasure;
	private Register sensemarker0  = Register.sense_marker0;
	private Register sensemarker1  = Register.sense_marker1;
	private Register sensemarker2 = Register.sense_marker2;
	private Register sensemarker3 = Register.sense_marker3;
	private Register sensemarker4 = Register.sense_marker4;
	private Register sensemarker5 = Register.sense_marker5;
	private Register senseenemymarker = Register.sense_enemymarker;
	private Register senseshiptype = Register.sense_shiptype;
	private Register sensedirection = Register.sense_shipdirection;
	private Register senseloaded= Register.sense_shiploaded;
	private Register sensesupply = Register.sense_supply;
	private Register sensecondition = Register.sense_shipcondition;
	private Random random;
	private DummyLogWriter testlog;
	private Map map;
	
	@Test
	public void testgetPosition(){
		Random testRandom = new Random(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		Field water = new Water(testMap, 0, 0, null);
		ship = new Ship(null, water, 0, null);
		
		assertTrue(ship.getPosition().equals(water));
	
	}
	
	@Test
	public void testNoPositivActionCounter(){
		
		ship = new Ship(null, null, 0, null);
		
		assertTrue(ship.getNoPositivActionCounter() == 0);
	
	}
	
	@Test
	public void testAct(){
		
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		Drop drops = new Drop();
		Goto go= new Goto(0);
		commands.add(drops);
		commands.add(go);
		char a = 0;
		Team team = new Team(a, commands);
		Field field = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, null);
		field.setShip(ship);
		
		ship.act();
		assertTrue (ship.getPC() == 1);
		assertTrue (testlog.what.remove("notify"));
		
		
		ship.act();
		assertTrue("command goto needs to be implemented for this to work",ship.getPC() == 0);
		assertTrue ( testlog.what.remove("notify"));
		
	}
	
	@Test
	public void testgetCommand(){
		
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		Drop drops = new Drop();
		Goto go= new Goto(0);
		commands.add(drops);
		commands.add(go);
		char a = 0;
		Team team = new Team(a, commands);
		Field field = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, null);
		field.setShip(ship);
		
		
		assertTrue (ship.getCommand().equals(drops));
	}
	
	@Test
	public void testSetLoad(){
		
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		
		char a = 0;
		Team team = new Team(a, commands);
		Field field = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, null);
		field.setShip(ship);
		
		ship.setLoad(4);
		assertTrue(ship.getLoad() == 4);
		assertTrue (testlog.what.remove("notify"));
		
		ship.setLoad(0);
		assertTrue(ship.getLoad() == 0);
		assertTrue (testlog.what.remove("notify"));
	}
	
	
	@Test (expected=IllegalArgumentException.class)
	public void testInvalidSetLoad1(){	
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		
		char a = 0;
		Team team = new Team(a, commands);
		Field field = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, null);
		field.setShip(ship);
		ship.setLoad(6);
		assertTrue(ship.getLoad() == 4);
		assertTrue (testlog.what.remove("notify"));
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testInvalidSetLoad2(){	
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		
		char a = 0;
		Team team = new Team(a, commands);
		Field field = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, null);
		field.setShip(ship);
		ship.setLoad(-7);
		assertTrue(ship.getLoad() == 0);
		assertTrue (testlog.what.remove("notify"));
	}
	
	@Test
	public void testgetID(){
		ship = new Ship(null, null, 666, null);
		assertTrue(ship.getID() == 666);
		
	}
	
	@Test
	public void testgetPC(){
		ship = new Ship(null, null, 666, null);
		ship.setPC(13);
		assertTrue(ship.getPC() == 13);
		
	}
	@Test
	public void testNextShip(){
		shipv = new Ship(null, null, 0, null);
		ship = new Ship(null, null, 0, shipv);
		shipn = new Ship(null, null, 0, ship);
		
		shipv.setNextShip(ship);
		assertTrue (shipv.getNextShip().equals(ship));
		
		ship.setNextShip(shipn);
		assertTrue (ship.getNextShip().equals(shipn));
		
		shipn.setNextShip(null);
		assertNull (shipn.getNextShip());
		
	}
	
	@Test
	public void testPC(){
		
		ship = new Ship(null, null, 0, null);
		
		ship.setPC(13);
		assertTrue (ship.getPC() == 13);
		
	}
	
	@Test
	public void testgetTeam(){
		char a = 0;
		Team t = new Team(a, null);
		ship = new Ship(t, null, 0, null);
		
		assertTrue (ship.getTeam().equals(t));
		
	}
	
	@Test
	public void testChangeMoral1(){
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		
		char a = 0;
		Team team = new Team(a, commands);
		Field field = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, null);
		field.setShip(ship);
		
		
		ship.changeMoral(-1);
		assertTrue ("ChangeMoral",ship.getMoral() == 3) ;
		assertTrue ("hilfe", testlog.what.remove("notify"));
	}

	@Test
	public void testChangeMoral2(){
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		
		char a = 0;
		Team team = new Team(a, commands);
		Field field = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, null);
		field.setShip(ship);
		
		//ship = new Ship(null, null, 0, shipv);
		ship.changeMoral(3);
		
		assertTrue ("ChangeMoral bigger 4"+" was instead "+ship.getMoral(),ship.getMoral() == 4) ;
		assertTrue ("NoPositivActionCounter not 0 after positiv action", ship.getNoPositivActionCounter() == 0);
		assertFalse (testlog.what.remove("notify"));
	}
	
	@Test
	public void testChangeMoral3(){
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		
		char a = 0;
		Team team = new Team(a, commands);
		Field field = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, null);
		shipv = new Ship(null, null, 0, null);
		field.setShip(ship);
		
		
		ship.changeMoral(-7);
		
		assertTrue ("ChangeMoral must not < 0", ship.getMoral() == 0) ;
		assertTrue (testlog.what.remove("notify"));
	}
	
	@Test
	public void testChangeMoral4(){
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		
		char a = 0;
		Team team = new Team(a, commands);
		Field field = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, null);
		field.setShip(ship);
		
		
		ship.changeMoral(0);
		
		assertTrue ("ChangeMoral should be 4", ship.getMoral() == 4) ;
		assertFalse (testlog.what.remove("notify"));
	}
	
	
	@Test
	public void testChangeCondition1(){
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		
		char a = 0;
		Team team = new Team(a, commands);
		Field field = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, null);
		field.setShip(ship);
		
		
		
	ship.changeCondition(-1);
	assertTrue ("ChangeCondition down",ship.getCondition() == 2);
	assertTrue (testlog.what.remove("notify"));
	
	ship.changeCondition(1);
	assertTrue ("ChangeCondition up",ship.getCondition() == 3);
	assertTrue (testlog.what.remove("notify"));
	
	ship.changeCondition(0);
	assertTrue ("ChangeCondition up",ship.getCondition() == 3);
	assertFalse (testlog.what.remove("notify"));
	}
	
	@Test
	public void testChangeCondition2(){
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		
		char a = 0;
		Team team = new Team(a, commands);
		Field field = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, null);
		field.setShip(ship);
		
		
		ship.changeCondition(3);
		
		assertTrue ("ChangeCondition: Condition must not be bigger 3", ship.getCondition() == 3);
		assertFalse (testlog.what.remove("notify"));

	}
	
	@Test
	public void testChangeCondition3(){
		
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		
		Team team = new Team('a', commands);
		Field field = new Water(map, 0, 0, null);
		Field field2 = new Water(map, 0, 0, null);
		Field field3 = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, shipv);
		shipv = new Ship(team, field2, 1, null);
		shipn = new Ship(team, field3, 2, ship);
		ship.setNextShip(shipn);
		shipv.setNextShip(ship);
		field.setShip(ship);
		team.addShip(ship);
		team.addShip(shipn);
		team.addShip(shipv);
		
		
		ship.changeCondition(-1);
		ship.changeCondition(-1);
		ship.changeCondition(-1);					// Test not 100% working
		
		assertNull ("ChangeCondition == 0 : ship must be deleted", field.getShip());
		assertTrue (testlog.what.remove("destroy"));

	}
	
	@Test
	public void testChangeCondition4(){
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		
		char a = 0;
		Team team = new Team(a, commands);
		Field field = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, null);
		field.setShip(ship);
		
		
		ship.changeCondition(0);
		
		assertTrue ("ChangeCondition: Condition must be 3 at the beginning", ship.getCondition() == 3);
		assertFalse (testlog.what.remove("notify"));
	}

	@Test
	public void testChangePause1(){
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		
		char a = 0;
		Team team = new Team(a, commands);
		Field field = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, null);
		field.setShip(ship);
		
		
		ship.changePause(0);
		
		assertTrue ("ChangePause: Pause must be 0 at the beginning", ship.getPause() == 0);
		assertFalse (testlog.what.remove("notify"));
	}
	
	@Test
	public void testChangePause2(){
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		
		char a = 0;
		Team team = new Team(a, commands);
		Field field = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, null);
		field.setShip(ship);
		
		
		ship.changePause(1);
		
		assertTrue ("ChangePause up", ship.getPause() == 1);
		assertTrue (testlog.what.remove("notify"));
	}
	
	@Test
	public void testChangePause3(){
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		
		char a = 0;
		Team team = new Team(a, commands);
		Field field = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, null);
		field.setShip(ship);
		
		
		ship.changePause(9);
		
		assertTrue ("ChangePause" ,ship.getPause() == 9);
		assertTrue (testlog.what.remove("notify"));
	}
	

	@Test (expected= IllegalStateException.class)
	public void testChangePause4(){
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		
		char a = 0;
		Team team = new Team(a, commands);
		Field field = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, null);
		field.setShip(ship);
		ship.changePause(4);
		
		ship.changePause(4);
		
		assertTrue ("ChangePause: Pause must not be < 0", ship.getPause() == 0);
		assertTrue (testlog.what.remove("notify"));
	}
	
	@Test
	public void testChangeDirectionleft(){
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		
		char a = 0;
		Team team = new Team(a, commands);
		Field field = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, null);
		field.setShip(ship);
		
		
		
		ship.changeDirection(true);
		assertTrue("changeDirection left first time ", ship.getShipDirection() == 5);
		assertTrue (testlog.what.remove("notify"));
		ship.changeDirection(true);
		assertTrue  ("changeDirection left second time ", ship.getShipDirection() == 4);
		assertTrue (testlog.what.remove("notify"));
		ship.changeDirection(true);
		assertTrue ("changeDirection left 3. time ", ship.getShipDirection() == 3);
		assertTrue (testlog.what.remove("notify"));
		ship.changeDirection(true);
		assertTrue  ("changeDirection left 4. time ", ship.getShipDirection() == 2);
		assertTrue (testlog.what.remove("notify"));
		ship.changeDirection(true);
		assertTrue ("changeDirection left 5. time ", ship.getShipDirection() == 1);
		assertTrue (testlog.what.remove("notify"));
		ship.changeDirection(true);
		assertTrue  ("changeDirection left 6. time ", ship.getShipDirection() == 0);
		assertTrue (testlog.what.remove("notify"));
		
	}
	
	@Test
	public void testChangeDirectionright(){
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		
		char a = 0;
		Team team = new Team(a, commands);
		Field field = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, null);
		field.setShip(ship);
		
		
		
		ship.changeDirection(false);
		assertTrue ("changeDirection right 1. time ", ship.getShipDirection() == 1);
		assertTrue (testlog.what.remove("notify"));
		ship.changeDirection(false);
		assertTrue ("changeDirection right 2. time ", ship.getShipDirection() == 2);
		assertTrue (testlog.what.remove("notify"));
		ship.changeDirection(false);
		assertTrue ("changeDirection right 3. time ", ship.getShipDirection() == 3);
		assertTrue (testlog.what.remove("notify"));
		ship.changeDirection(false);
		assertTrue ("changeDirection right 4. time ", ship.getShipDirection() == 4);
		assertTrue (testlog.what.remove("notify"));
		ship.changeDirection(false);
		assertTrue ("changeDirection right 5. time ", ship.getShipDirection() == 5);
		assertTrue (testlog.what.remove("notify"));
		ship.changeDirection(false);
		assertTrue ("changeDirection right 6. time ", ship.getShipDirection() == 0);
		assertTrue (testlog.what.remove("notify"));
	}
	
	@Test
	public void testRelativeToAbsolute1(){
		
		random = new Random(1);	
		testlog = new DummyLogWriter();
		map = new Map(random, testlog);
		List<Command> commands = new ArrayList<Command>();
		
		char a = 0;
		Team team = new Team(a, commands);
		Field field = new Water(map, 0, 0, null);
		ship = new Ship(team, field, 0, null);
		field.setShip(ship);
		
		assertTrue ("relativetoabsolutedir 0 rel = abs", ship.relativeToAbsoluteDirection(0) == 0);
		assertTrue ("relativetoabsolutedir 1 rel = abs",ship.relativeToAbsoluteDirection(1) == 1);
		assertTrue ("relativetoabsolutedir 2 rel = abs",ship.relativeToAbsoluteDirection(2) == 2);
		assertTrue ("relativetoabsolutedir 3 rel = abs",ship.relativeToAbsoluteDirection(3) == 3);
		assertTrue ("relativetoabsolutedir 4 rel = abs",ship.relativeToAbsoluteDirection(4) == 4);
		assertTrue ("relativetoabsolutedir 5 rel = abs",ship.relativeToAbsoluteDirection(5) == 5);
		assertTrue ("relativetoabsolutedir 6 rel = abs",ship.relativeToAbsoluteDirection(6) == 6);
		
		ship.changeDirection(false);
		assertTrue ("relativetoabsolutedir turned right one time",ship.relativeToAbsoluteDirection(0) == 1);
		assertTrue ("relativetoabsolutedir turned right one time",ship.relativeToAbsoluteDirection(1) == 2);
		assertTrue ("relativetoabsolutedir turned right one time",ship.relativeToAbsoluteDirection(2) == 3);
		assertTrue ("relativetoabsolutedir turned right one time",ship.relativeToAbsoluteDirection(3) == 4);
		assertTrue ("relativetoabsolutedir turned right one time",ship.relativeToAbsoluteDirection(4) == 5);
		assertTrue ("relativetoabsolutedir turned right one time",ship.relativeToAbsoluteDirection(5) == 0);
		assertTrue ("relativetoabsolutedir turned right one time",ship.relativeToAbsoluteDirection(6) == 6);
		
		ship.changeDirection(false);
		assertTrue ("relativetoabsolutedir turned right two times",ship.relativeToAbsoluteDirection(0) == 2);
		assertTrue ("relativetoabsolutedir turned right two times",ship.relativeToAbsoluteDirection(1) == 3);
		assertTrue ("relativetoabsolutedir turned right two times",ship.relativeToAbsoluteDirection(2) == 4);
		assertTrue ("relativetoabsolutedir turned right two times",ship.relativeToAbsoluteDirection(3) == 5);
		assertTrue ("relativetoabsolutedir turned right two times",ship.relativeToAbsoluteDirection(4) == 0);
		assertTrue ("relativetoabsolutedir turned right two times",ship.relativeToAbsoluteDirection(5) == 1);
		assertTrue ("relativetoabsolutedir turned right two times",ship.relativeToAbsoluteDirection(6) == 6);
		
		ship.changeDirection(false);
		assertTrue ("relativetoabsolutedir turned right three times",ship.relativeToAbsoluteDirection(0) == 3);
		assertTrue ("relativetoabsolutedir turned right three times",ship.relativeToAbsoluteDirection(1) == 4);
		assertTrue ("relativetoabsolutedir turned right three times",ship.relativeToAbsoluteDirection(2) == 5);
		assertTrue ("relativetoabsolutedir turned right three times",ship.relativeToAbsoluteDirection(3) == 0);
		assertTrue ("relativetoabsolutedir turned right three times",ship.relativeToAbsoluteDirection(4) == 1);
		assertTrue ("relativetoabsolutedir turned right three times",ship.relativeToAbsoluteDirection(5) == 2);
		assertTrue ("relativetoabsolutedir turned right three times",ship.relativeToAbsoluteDirection(6) == 6);
		
		ship.changeDirection(false);
		assertTrue ("relativetoabsolutedir turned right four times",ship.relativeToAbsoluteDirection(0) == 4);
		assertTrue ("relativetoabsolutedir turned right four times",ship.relativeToAbsoluteDirection(1) == 5);
		assertTrue ("relativetoabsolutedir turned right four times",ship.relativeToAbsoluteDirection(2) == 0);
		assertTrue ("relativetoabsolutedir turned right four times",ship.relativeToAbsoluteDirection(3) == 1);
		assertTrue ("relativetoabsolutedir turned right four times",ship.relativeToAbsoluteDirection(4) == 2);
		assertTrue ("relativetoabsolutedir turned right four times",ship.relativeToAbsoluteDirection(5) == 3);
		assertTrue ("relativetoabsolutedir turned right four times",ship.relativeToAbsoluteDirection(6) == 6);
		
		ship.changeDirection(false);
		assertTrue ("relativetoabsolutedir turned right 5 times",ship.relativeToAbsoluteDirection(0) == 5);
		assertTrue ("relativetoabsolutedir turned right 5 times",ship.relativeToAbsoluteDirection(1) == 0);
		assertTrue ("relativetoabsolutedir turned right 5 times",ship.relativeToAbsoluteDirection(2) == 1);
		assertTrue ("relativetoabsolutedir turned right 5 times",ship.relativeToAbsoluteDirection(3) == 2);
		assertTrue ("relativetoabsolutedir turned right 5 times",ship.relativeToAbsoluteDirection(4) == 3);
		assertTrue ("relativetoabsolutedir turned right 5 times",ship.relativeToAbsoluteDirection(5) == 4);
		assertTrue ("relativetoabsolutedir turned right 5 times",ship.relativeToAbsoluteDirection(6) == 6);
		
		ship.changeDirection(false);
		assertTrue ("relativetoabsolutedir turned right 6 times rel = abs",ship.relativeToAbsoluteDirection(0) == 0);
		assertTrue ("relativetoabsolutedir turned right 6 times rel = abs",ship.relativeToAbsoluteDirection(1) == 1);
		assertTrue ("relativetoabsolutedir turned right 6 times rel = abs",ship.relativeToAbsoluteDirection(2) == 2);
		assertTrue ("relativetoabsolutedir turned right 6 times rel = abs",ship.relativeToAbsoluteDirection(3) == 3);
		assertTrue ("relativetoabsolutedir turned right 6 times rel = abs",ship.relativeToAbsoluteDirection(4) == 4);
		assertTrue ("relativetoabsolutedir turned right 6 times rel = abs",ship.relativeToAbsoluteDirection(5) == 5);
		assertTrue ("relativetoabsolutedir turned right 6 times rel = abs",ship.relativeToAbsoluteDirection(6) == 6);
	}
	
	@Test (expected = Exception.class)
	public void testRelativeToAbsolute2(){
		
		ship = new Ship(null, null, 0, shipv);
		assertTrue ("relativetoabsolutedir 0 rel = abs", ship.relativeToAbsoluteDirection(7) == 7);
		assertTrue ("relativetoabsolutedir 0 rel = abs", ship.relativeToAbsoluteDirection(-1) == -1);
		
	}
	@Test
	public void testDestroy(){
		
		testlog = new DummyLogWriter();
		Map testMap = new Map(random, testlog);
		Team testTeam = new Team('a', null);
		Field testField = new Water(testMap, 0, 0, null);
		
		shipv = new Ship(testTeam, null, 0, null);
		ship = new Ship(testTeam, testField, 0, shipv);
		shipn = new Ship(testTeam, null, 0, ship);
		field = new Water(testMap, 0, 0, null);
		testField.setShip(ship);
		testTeam.addShip(ship);
		ship.changeCondition(-3);
		
		assertNull ("Destroyed because condition 0, field.getShip() must be null", field.getShip());
		assertTrue ("Destroyed because condition 0, ship must be deleted out of the previous/next ship ",shipv.getNextShip().getID() == shipn.getID());
		assertTrue (testlog.what.remove("destroy"));
		
	}
	
	@Test
	public void testSenseRegistershipdirection1(){
		
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensedirection, 5);
		assertTrue (sship.getSenseRegister(sensedirection) == 5);
		
		sship.setSenseRegister(sensedirection, 0);
		assertTrue (sship.getSenseRegister(sensedirection) == 0);
	}
	

	@Test (expected=IllegalArgumentException.class)
	public void testSenseRegistershipdirection2(){
		
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensedirection, -1);
		assertFalse ("ShipDirection not <0 ",sship.getSenseRegister(sensedirection) == -1);
		
		sship.setSenseRegister(sensedirection, 6);
		assertFalse ("ShipDirection not >5 ",sship.getSenseRegister(sensedirection) == 6);
	}

	
	
	
	@Test
	public void testSenseRegistershipload1(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(senseloaded, 0);
		assertTrue (sship.getSenseRegister(senseloaded) == 0);
		
		sship.setSenseRegister(senseloaded, 1);
		assertTrue (sship.getSenseRegister(senseloaded) == 1);
	}
	

	@Test (expected=IllegalArgumentException.class)
	public void testSenseRegistershipload2(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(senseloaded, -1);
		assertFalse ("Shipload not <0", sship.getSenseRegister(senseloaded) == -1);
		
		sship.setSenseRegister(senseloaded, 5);
		assertFalse ("Shipload not > 4", sship.getSenseRegister(senseloaded) == 5);
	}
	
		
	@Test
	public void testSenseRegistershipcondition1(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensecondition, 3);
		assertTrue (sship.getSenseRegister(sensecondition) == 3);
		
		sship.setSenseRegister(sensecondition, 1);
		assertTrue (sship.getSenseRegister(sensecondition) == 1);
	}
	

	@Test (expected=IllegalArgumentException.class)
	public void testSenseRegistershipcondition2(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensecondition, -1);
		assertFalse ("Shipcondition not <0", sship.getSenseRegister(sensecondition) == -1);
		
		sship.setSenseRegister(sensecondition, 4);
		assertFalse ("Shipcondition not > 3", sship.getSenseRegister(sensecondition) == 4);
	}
	
	@Test
	public void testSenseRegistersensecelltype1(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensecelltype, 0);
		assertTrue (sship.getSenseRegister(sensecelltype) == 0);
		
		sship.setSenseRegister(sensecelltype, 1);
		assertTrue (sship.getSenseRegister(sensecelltype) == 1);
		
		sship.setSenseRegister(sensecelltype, 2);
		assertTrue (sship.getSenseRegister(sensecelltype) == 2);
		
		sship.setSenseRegister(sensecelltype, 3);
		assertTrue (sship.getSenseRegister(sensecelltype) == 3);
	}
	

	@Test(expected=IllegalArgumentException.class)
	public void testSenseRegistersensecelltype2(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensecelltype, -1);
		assertFalse ("Sensecelltype not < 0", sship.getSenseRegister(sensecelltype) == -1);
		
		sship.setSenseRegister(sensecelltype, 4);
		assertFalse ("Sensecelltype not > 3", sship.getSenseRegister(sensecelltype) == 4);
	}
	
	@Test
	public void testSenseRegistersense1(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensetreasure, 0);
		assertTrue (sship.getSenseRegister(sensetreasure) == 0);
		
		sship.setSenseRegister(sensetreasure, 1);
		assertTrue (sship.getSenseRegister(sensetreasure) == 1);
		
	}
	

	@Test (expected=IllegalArgumentException.class)
	public void testSenseRegistersensetreasure2(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensetreasure, -1);
		assertFalse ("Sensetreasure not < 0", sship.getSenseRegister(sensetreasure) == -1);
		
		sship.setSenseRegister(sensetreasure, 2);
		assertFalse ("Sensetreasure not > 1", sship.getSenseRegister(sensetreasure) == 2);
	}
	
	@Test
	public void testSenseRegistersensesupply1(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensesupply, 0);
		assertTrue (sship.getSenseRegister(sensesupply) == 0);
		
		sship.setSenseRegister(sensesupply, 1);
		assertTrue (sship.getSenseRegister(sensesupply) == 1);
		
	}
	

	@Test (expected=IllegalArgumentException.class)
	public void testSenseRegistersensesupply2(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensesupply, -1);
		assertFalse ("Sensesupply not <0", sship.getSenseRegister(sensesupply) == -1);
		
		sship.setSenseRegister(sensesupply, 2);
		assertFalse ("Sensesupply not >1", sship.getSenseRegister(sensesupply) == 2);
	}
	
	@Test
	public void testSenseRegistersenseenemymarker(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(senseenemymarker, 0);
		assertTrue (sship.getSenseRegister(senseenemymarker) == 0);
		
		sship.setSenseRegister(senseenemymarker, 1);
		assertTrue (sship.getSenseRegister(senseenemymarker) == 1);
		
	}
	

	@Test (expected=IllegalArgumentException.class)
	public void testSenseRegistersenseenemymarker2(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(senseenemymarker, -1);
		assertFalse ("Senseenemymarker not <0", sship.getSenseRegister(senseenemymarker) == -1);
		
		sship.setSenseRegister(senseenemymarker, 2);
		assertFalse ("Senseenemymarker not > 1", sship.getSenseRegister(senseenemymarker) == 2);
	}
	
	@Test
	public void testSenseRegistersenseshiptype1(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(senseshiptype, 0);
		assertTrue (sship.getSenseRegister(senseshiptype) == 0);
		
		sship.setSenseRegister(senseshiptype, 1);
		assertTrue (sship.getSenseRegister(senseshiptype) == 1);
		
	}
	

	@Test (expected=IllegalArgumentException.class)
	public void testSenseRegistersenseshiptype2(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(senseshiptype, -1);
		assertFalse ("Senseshiptype not <0", sship.getSenseRegister(senseshiptype) == -1);
		
		sship.setSenseRegister(senseshiptype, 2);
		assertFalse ("Senseshiptype not >1",sship.getSenseRegister(senseshiptype) == 2);
	}
	

	@Test
	public void testSenseRegistersenseshiploaded1(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(senseloaded, 0);
		assertTrue (sship.getSenseRegister(senseloaded) == 0);
		
		sship.setSenseRegister(senseloaded, 1);
		assertTrue (sship.getSenseRegister(senseloaded) == 1);
		
	}
	

	@Test (expected=IllegalArgumentException.class)
	public void testSenseRegistersenseshiploaded2(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(senseloaded, -1);
		assertFalse ("Senseloaded not <0", sship.getSenseRegister(senseloaded) == -1);
		
		sship.setSenseRegister(senseloaded, 2);
		assertFalse ("Senseloaded not >1", sship.getSenseRegister(senseloaded) == 2);
	}
	
	@Test
	public void testSenseRegistersenseshipdirection1(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensedirection, 0);
		assertTrue (sship.getSenseRegister(sensedirection) == 0);
		
		sship.setSenseRegister(sensedirection, 1);
		assertTrue (sship.getSenseRegister(sensedirection) == 1);
		
		sship.setSenseRegister(sensedirection, 2);
		assertTrue (sship.getSenseRegister(sensedirection) == 2);
		
		sship.setSenseRegister(sensedirection, 3);
		assertTrue (sship.getSenseRegister(sensedirection) == 3);
		
		sship.setSenseRegister(sensedirection, 4);
		assertTrue (sship.getSenseRegister(sensedirection) == 4);
		
		sship.setSenseRegister(sensedirection, 5);
		assertTrue (sship.getSenseRegister(sensedirection) == 5);
		
	}
	

	@Test (expected=IllegalArgumentException.class)
	public void testSenseRegistersenseshipcondition2(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensedirection, -1);
		assertFalse ("Sensedirection not <0", sship.getSenseRegister(sensedirection) == -1);
		
		sship.setSenseRegister(sensedirection, 6);
		assertFalse ("Sensedirection not >5", sship.getSenseRegister(sensedirection) == 6);
	}
	

	@Test
	public void testSenseRegistersensesmarker01(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker0, 0);
		assertTrue (sship.getSenseRegister(sensemarker0) == 0);
		
		sship.setSenseRegister(sensemarker0, 1);
		assertTrue (sship.getSenseRegister(sensemarker0) == 1);
			
	}
	@Test (expected=IllegalArgumentException.class)
	public void testSenseRegistersensesensemarker02(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker0, -1);
		assertFalse ("Sensemarker not < 0", sship.getSenseRegister(sensemarker0) == -1);
		
		sship.setSenseRegister(sensemarker0, 2);
		assertFalse ("Sensemarker not >1", sship.getSenseRegister(sensemarker0) == 2);
	}
	
	@Test
	public void testSenseRegistersensesmarker11(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker1, 0);
		assertTrue (sship.getSenseRegister(sensemarker1) == 0);
		
		sship.setSenseRegister(sensemarker1, 1);
		assertTrue (sship.getSenseRegister(sensemarker1) == 1);
			
	}
	

	@Test(expected=IllegalArgumentException.class)
	public void testSenseRegistersensesensemarker12(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker1, -1);
		assertFalse ("Sensemarker1 not < 0", sship.getSenseRegister(sensemarker1) == -1);
		
		sship.setSenseRegister(sensemarker1, 2);
		assertFalse("Sensemarker1 not >1", sship.getSenseRegister(sensemarker1) == 2);
	}
	
	@Test
	public void testSenseRegistersensesmarker21(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker2, 0);
		assertTrue (sship.getSenseRegister(sensemarker2) == 0);
		
		sship.setSenseRegister(sensemarker2, 1);
		assertTrue (sship.getSenseRegister(sensemarker2) == 1);
			
	}
	

	@Test(expected=IllegalArgumentException.class)
	public void testSenseRegistersensesensemarker22(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker2, -1);
		assertFalse ("Sensemarker2 not < 0", sship.getSenseRegister(sensemarker2) == -1);
		
		sship.setSenseRegister(sensemarker2, 2);
		assertFalse ("Sensemarker2 not >1", sship.getSenseRegister(sensemarker2) == 2);
	}
	
	@Test
	public void testSenseRegistersensesmarker31(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker3, 0);
		assertTrue (sship.getSenseRegister(sensemarker3) == 0);
		
		sship.setSenseRegister(sensemarker3, 1);
		assertTrue (sship.getSenseRegister(sensemarker3) == 1);
			
	}
	@Test(expected=IllegalArgumentException.class)
	public void testSenseRegistersensesensemarker32(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker3, -1);
		assertFalse ("Sensemarker3 not < 0", sship.getSenseRegister(sensemarker3) == -1);
		
		sship.setSenseRegister(sensemarker3, 2);
		assertFalse ("Sensemarker3 not >1", sship.getSenseRegister(sensemarker3) == 2);
	}
	

	@Test 
	public void testSenseRegistersensesensemarker41(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker4, 0);
		assertTrue (sship.getSenseRegister(sensemarker4) == 0);
		
		sship.setSenseRegister(sensemarker4, 1);
		assertTrue (sship.getSenseRegister(sensemarker4) == 1);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testSenseRegistersensesensemarker42(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker4, -1);
		assertFalse ("Sensemarker4 not < 0", sship.getSenseRegister(sensemarker4) == -1);
		
		sship.setSenseRegister(sensemarker4, 2);
		assertFalse ("Sensemarker4 not >1", sship.getSenseRegister(sensemarker4) == 2);
	}
	
	@Test
	public void testSenseRegistersensesmarker51(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker5, 0);
		assertTrue (sship.getSenseRegister(sensemarker5) == 0);
		
		sship.setSenseRegister(sensemarker5, 1);
		assertTrue (sship.getSenseRegister(sensemarker5) == 1);
			
	}
	@Test (expected=IllegalArgumentException.class)
	public void testSenseRegistersensesensemarker52(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker5, -1);
		assertFalse ("Sensemarker5 not < 0", sship.getSenseRegister(sensemarker5) == -1);
		
		sship.setSenseRegister(sensemarker5, 2);
		assertFalse ("Sensemarker5 not >1", sship.getSenseRegister(sensemarker5) == 2);
	}
		
	
}
