package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.commands.Pickup;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Island;
import de.unisaarland.cs.st.pirates.group5.model.Kraken;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.Treasure;
import de.unisaarland.cs.st.pirates.group5.model.Water;

public class CommandPickupTest {
	/**
	 * pickup mit schiffladung =0 und Insel = 1
	 * 
	 * pickup auf falsches Feld (keine Island)
	 * 
	 * pickup mit Schiffladung = 4
	 * 
	 * pickup mit Schiffladung = 3 und insel = 2
	 * 
	 * pickup mit schiffladung = 0 und insel = 3
	 * 
	 * pickup mit schiffladung = 0 und insel = 4
	 * 
	 * pickup mit schiffladung = 0 und insel = 5
	 * 
	 * pickup mit schiffladung = 3 und insel = 0
	 * 
	 * pickup auf eigenem Feld (schatz im wasser)
	 * 
	 * lastPositiveActionCounter not important here, already tested in changemoral!
	 * 
	 * moraländerungen testen (+ keine wenn fehlschlägt)
	 **/
	
	List<Kraken> krakens = new ArrayList<Kraken>();

	@Test
	public void pickup1Treasure3Moral(){
		
		Pickup testPickup = new Pickup(4, 10);
		
		Random testRandom = new Random(1);	
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		Treasure testTreasure = new Treasure(0,1);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Island(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, krakens);
		
		fieldArray[1][1]=new Water(testMap, 1, 1, null);
		
		fieldArray[1][0]=new Island(testMap,1,0,testTreasure);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(0);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-1);
		
		testPickup.execute(testShip);
		testPickup.equals(testPickup);
		testPickup.equals(null);
		
		assertEquals(1, testShip.getLoad());
		assertEquals(4, testShip.getMoral());
		assertEquals(5, testShip.getPC());				// PC already increased in act-method of ship
		assertEquals(null,fieldArray[1][0].getTreasure());	
	}
	
	@Test
	public void pickup1Treasure1Moral(){
		
		Pickup testPickup = new Pickup(4, 10);
		
		Random testRandom = new Random(1);	
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		Treasure testTreasure = new Treasure(0,1);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Island(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, krakens);
		
		fieldArray[1][1]=new Water(testMap, 1, 1, null);
		
		fieldArray[1][0]=new Island(testMap,1,0,testTreasure);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(0);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-3);
		
		testPickup.execute(testShip);
		
		assertEquals(1, testShip.getLoad());
		assertEquals(3, testShip.getMoral());
		assertEquals(5, testShip.getPC());				// PC already increased in act-method of ship
		assertEquals(null, fieldArray[1][0].getTreasure());
	}
	
	@Test
	public void pickup5Treasure1Moral(){
		
		Pickup testPickup = new Pickup(4, 10);
		
		Random testRandom = new Random(1);	
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		Treasure testTreasure = new Treasure(0,5);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Island(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, krakens);
		
		fieldArray[1][1]=new Water(testMap, 1, 1, null);
		
		fieldArray[1][0]=new Island(testMap,1,0,testTreasure);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(0);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-3);
		
		testPickup.execute(testShip);
		
		assertEquals(4, testShip.getLoad());
		assertEquals(3, testShip.getMoral());
		assertEquals(5, testShip.getPC());				// PC already increased in act-method of ship
		assertEquals(1, fieldArray[1][0].getTreasure().getValue());
	}
	
	@Test
	public void pickup5Treasure1MoralAlreadyfull(){
		
		Pickup testPickup = new Pickup(4, 10);
		
		Random testRandom = new Random(1);	
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		Treasure testTreasure = new Treasure(0,5);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Island(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, krakens);
		
		fieldArray[1][1]=new Water(testMap, 1, 1, null);
		
		fieldArray[1][0]=new Island(testMap,1,0,testTreasure);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(4);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-3);
		
		testPickup.execute(testShip);
		
		assertEquals(4, testShip.getLoad());
		assertEquals(1, testShip.getMoral());
		assertEquals(10, testShip.getPC());				// else Part
		assertEquals(5, fieldArray[1][0].getTreasure().getValue());
	}
	
	@Test
	public void pickup0Treasure1Moral(){
		
		Pickup testPickup = new Pickup(4, 10);
		Random testRandom = new Random(1);	
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Island(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, krakens);
		fieldArray[1][1]=new Water(testMap, 1, 1, null);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(1);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-3);
		
		testPickup.execute(testShip);
		
		assertEquals(1, testShip.getLoad());
		assertEquals(1, testShip.getMoral());
		assertEquals(10, testShip.getPC());				// else Part
		//assertTrue(fieldArray[1][0].getTreasure().getValue()==5);
	}
	
	@Test
	public void pickup5Treasure1MoralInWater(){
		
		Pickup testPickup = new Pickup(4, 10);
		
		Random testRandom = new Random(1);	
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Island(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, krakens);
		
		fieldArray[1][1]=new Water(testMap, 1, 1, null);
		
		fieldArray[1][0]=new Water(testMap,1,0,null);
		
		assertTrue(fieldArray[0][0].getTreasure()==null);
		
		fieldArray[1][0].exchangeTreasure(4);
		
		assertTrue(fieldArray[1][0].getTreasure()!=null);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(1);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-3);
		
		testPickup.execute(testShip);
		
		assertEquals(4, testShip.getLoad());
		assertEquals(3, testShip.getMoral());
		assertEquals(5, testShip.getPC());				// else Part
		assertEquals(1, fieldArray[1][0].getTreasure().getValue());
	}
	
	@Test
	public void pickup1Treasure1MoralInWater(){
		
		Pickup testPickup = new Pickup(4, 10);
		
		Random testRandom = new Random(1);	
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Island(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, krakens);
		
		fieldArray[1][1]=new Water(testMap, 1, 1, null);
		
		fieldArray[1][0]=new Water(testMap,1,0,null);
		
		assertTrue(fieldArray[1][0].getTreasure()==null);
		
		fieldArray[1][0].exchangeTreasure(1);
		
		assertTrue(fieldArray[1][0].getTreasure()!=null);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(1);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-3);
		
		testPickup.execute(testShip);
		
		assertEquals(2, testShip.getLoad());
		assertEquals(3, testShip.getMoral());
		assertEquals(5, testShip.getPC());				// else Part
		assertEquals(null, fieldArray[1][1].getTreasure());
	}
	
	
}
