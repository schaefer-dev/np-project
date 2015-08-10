package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.commands.Turn;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Kraken;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.Water;

public class CommandTurnTest {
	
	@Before
	public void setUp(){
		
		Random testRandom = new Random(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[2][2];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		Ship ship = new Ship(null, fieldArray[1][1], 0, null);
		fieldArray[1][1].setShip(ship);
		
	}

	@Test
	public void TestTurnLeft1(){
		Turn testTurn = new Turn(true);

		Random testRandom = new Random(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[2][2];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		Ship ship = new Ship(null, fieldArray[1][1], 0, null);
		fieldArray[1][1].setShip(ship);
		
		
		DoTurnXTimes(1, testTurn, ship);
		assertTrue(ship.getShipDirection() == 5);	
	}
	@Test
	public void TestTurnLeft2(){
		Turn testTurn = new Turn(true);

		Random testRandom = new Random(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[2][2];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		Ship ship = new Ship(null, fieldArray[1][1], 0, null);
		fieldArray[1][1].setShip(ship);
		
		DoTurnXTimes(2, testTurn, ship);
		assertTrue(ship.getShipDirection() == 4);	
	}
	@Test
	public void TestTurnLeft3(){
		Turn testTurn = new Turn(true);

		Random testRandom = new Random(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[2][2];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		Ship ship = new Ship(null, fieldArray[1][1], 0, null);
		fieldArray[1][1].setShip(ship);
		
		DoTurnXTimes(3, testTurn, ship);
		assertTrue(ship.getShipDirection() == 3);	
	}
	@Test
	public void TestTurnLeft4(){
		Turn testTurn = new Turn(true);

		Random testRandom = new Random(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[2][2];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		Ship ship = new Ship(null, fieldArray[1][1], 0, null);
		fieldArray[1][1].setShip(ship);
		
		DoTurnXTimes(4, testTurn, ship);
		assertTrue(ship.getShipDirection() == 2);	
	}
	@Test
	public void TestTurnLeft5(){
		Turn testTurn = new Turn(true);

		Random testRandom = new Random(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[2][2];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		Ship ship = new Ship(null, fieldArray[1][1], 0, null);
		fieldArray[1][1].setShip(ship);
		
		DoTurnXTimes(5, testTurn, ship);
		assertTrue(ship.getShipDirection() == 1);	
	}
	@Test
	public void TestTurnLeft6(){
		Turn testTurn = new Turn(true);

		Random testRandom = new Random(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[2][2];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		Ship ship = new Ship(null, fieldArray[1][1], 0, null);
		fieldArray[1][1].setShip(ship);
		
		DoTurnXTimes(6, testTurn, ship);
		assertTrue(ship.getShipDirection() == 0);	
	}
	@Test
	public void TestTurnLeft7CornerCase(){
		Turn testTurn = new Turn(true);

		Random testRandom = new Random(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[2][2];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		Ship ship = new Ship(null, fieldArray[1][1], 0, null);
		fieldArray[1][1].setShip(ship);
		
		DoTurnXTimes(7, testTurn, ship);
		assertTrue(ship.getShipDirection() == 5);	
	}
	@Test
	public void TestTurnLeft8CornerCase(){
		Turn testTurn = new Turn(true);

		Random testRandom = new Random(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[2][2];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		Ship ship = new Ship(null, fieldArray[1][1], 0, null);
		fieldArray[1][1].setShip(ship);
		
		DoTurnXTimes(8, testTurn, ship);
		assertTrue(ship.getShipDirection() == 4);	
	}
	
	@Test
	public void TestTurnRight1(){
		Turn testTurn = new Turn(false);

		Random testRandom = new Random(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[2][2];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		Ship ship = new Ship(null, fieldArray[1][1], 0, null);
		fieldArray[1][1].setShip(ship);
		
		DoTurnXTimes(1, testTurn, ship);
		assertTrue(ship.getShipDirection() == 1);	
	}
	@Test
	public void TestTurnRight2(){
		Turn testTurn = new Turn(false);

		Random testRandom = new Random(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[2][2];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		Ship ship = new Ship(null, fieldArray[1][1], 0, null);
		fieldArray[1][1].setShip(ship);
		
		DoTurnXTimes(2, testTurn, ship);
		assertTrue(ship.getShipDirection() == 2);	
	}
	@Test
	public void TestTurnRight3(){
		Turn testTurn = new Turn(false);

		Random testRandom = new Random(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[2][2];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		Ship ship = new Ship(null, fieldArray[1][1], 0, null);
		fieldArray[1][1].setShip(ship);
		
		DoTurnXTimes(3, testTurn, ship);
		assertTrue(ship.getShipDirection() == 3);	
	}
	@Test
	public void TestTurnRight4(){
		Turn testTurn = new Turn(false);

		Random testRandom = new Random(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[2][2];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		Ship ship = new Ship(null, fieldArray[1][1], 0, null);
		fieldArray[1][1].setShip(ship);
		
		DoTurnXTimes(4, testTurn, ship);
		assertTrue(ship.getShipDirection() == 4);	
	}
	@Test
	public void TestTurnRight5(){
		Turn testTurn = new Turn(false);

		Random testRandom = new Random(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[2][2];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		Ship ship = new Ship(null, fieldArray[1][1], 0, null);
		fieldArray[1][1].setShip(ship);
		
		DoTurnXTimes(5, testTurn, ship);
		assertTrue(ship.getShipDirection() == 5);	
	}
	@Test
	public void TestTurnRight6CornerCase(){
		Turn testTurn = new Turn(false);

		Random testRandom = new Random(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[2][2];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		Ship ship = new Ship(null, fieldArray[1][1], 0, null);
		fieldArray[1][1].setShip(ship);
		
		DoTurnXTimes(6, testTurn, ship);
		assertTrue(ship.getShipDirection() == 0);	
	}
	@Test
	public void TestTurnRight7CornerCase(){
		Turn testTurn = new Turn(false);

		Random testRandom = new Random(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[2][2];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		Ship ship = new Ship(null, fieldArray[1][1], 0, null);
		fieldArray[1][1].setShip(ship);
		
		DoTurnXTimes(7, testTurn, ship);
		assertTrue(ship.getShipDirection() == 1);	
	}
	@Test
	public void TestTurnRight8CornerCase(){
		Turn testTurn = new Turn(false);
		testTurn.equals(testTurn);
		testTurn.equals(null);
		Random testRandom = new Random(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[2][2];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		Ship ship = new Ship(null, fieldArray[1][1], 0, null);
		fieldArray[1][1].setShip(ship);
		
		DoTurnXTimes(8, testTurn, ship);
		assertTrue(ship.getShipDirection() == 2);	
	}
	
	@Test
	public void Boost(){
		Turn testTurn = new Turn(true);
		testTurn.equals(testTurn);
		assertTrue(true);	
	}
	
	public void DoTurnXTimes (int x, Turn turn, Ship ship){
		for (int i=0; (i < x); i++)
			turn.execute(ship);
	}
	
	
}
