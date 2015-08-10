package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.commands.Drop;
import de.unisaarland.cs.st.pirates.group5.model.Base;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Kraken;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.Team;
import de.unisaarland.cs.st.pirates.group5.model.Treasure;
import de.unisaarland.cs.st.pirates.group5.model.Water;


public class CommandDropTest {
	/*
	 * drop auf Basis = kein minus 2 Moral
	 * 
	 * drop auf Feld wenn tatsaechlich gedroppt wurde = minus 2 Moral
	 * 
	 * drop mit schiffladung =0 und Wasserschatz = 1
	 * 
	 * drop mit Schiffladung = 4 und leerem Wasser
	 * 
	 * drop mit Schiffladung = 3 und insel = 10
	 * 
	 * drop mit schiffladung = 0 und insel = 0
	 */
	List<Kraken> kraken = new LinkedList<Kraken>();

	@Test
	public void drop0Treasure3Moral(){
		
		Drop testDrop = new Drop();
		Random testRandom = new Random(1);	
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		Treasure testTreasure = new Treasure(0,1);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Water(testMap, z, i, null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		
		fieldArray[1][1]=new Water(testMap, 0, 0, null);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(0);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-1);
		
		testDrop.execute(testShip);
		
		assertTrue("load is not 0"+ " but is "+ testShip.getLoad(),testShip.getLoad()==0);
		assertTrue("moral is not 3",testShip.getMoral()==3); // No Load so moral does not change.
		assertTrue("ShipPC is not 5",testShip.getPC()==5);				// PC already increased in act-method of ship
		assertTrue("treasure found, should be null", fieldArray[1][1].getTreasure()==null);	
	}
	
	@Test
	public void drop4Treasure3Moral(){
		
		Drop testDrop = new Drop();
		
		Random testRandom = new Random(1);	
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		Treasure testTreasure = new Treasure(0,1);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Water(testMap, z, i, null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		
		fieldArray[1][1]=new Water(testMap, 0, 0, null);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(4);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-1);
		
		testDrop.execute(testShip);
		
		assertTrue("load should be 0"+ " but is "+ testShip.getLoad(),testShip.getLoad()==0);
		assertTrue("moral should be 1",testShip.getMoral()==1);
		assertTrue("pc should be 5",testShip.getPC()==5);				// PC already increased in act-method of ship
		assertTrue("Treasure not found",fieldArray[1][1].getTreasure()!=null);	
		assertTrue("Treasure does not have value 4",fieldArray[1][1].getTreasure().getValue()==4);	
	}
	
	@Test
	public void drop4Treasure1Moral(){
		
		Drop testDrop = new Drop();
		
		Random testRandom = new Random(1);	
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		Treasure testTreasure = new Treasure(0,1);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Water(testMap, z, i, null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		
		fieldArray[1][1]=new Water(testMap, 0, 0, null);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		assertTrue(fieldArray[1][1].getTreasure()==null);
		
		fieldArray[1][1].exchangeTreasure(4);
		fieldArray[1][1].exchangeTreasure(1);
		
		assertTrue(fieldArray[1][1].getTreasure()!=null);
		
		testShip.setLoad(4);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-3);
		
		testDrop.execute(testShip);
		
		assertTrue("load should be 0"+ " but is "+ testShip.getLoad(),testShip.getLoad()==0);
		assertTrue("moral should be 0",testShip.getMoral()==0);
		assertTrue("pc should be 5",testShip.getPC()==5);				// PC already increased in act-method of ship
		assertTrue("no Treasure found",fieldArray[1][1].getTreasure()!=null);	
		assertTrue("Treasure should have value 9",fieldArray[1][1].getTreasure().getValue()==9);	
	}
	
	@Test
	public void drop3TreasureBase(){
		
		Drop testDrop = new Drop();
		
		Random testRandom = new Random(1);	
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		Treasure testTreasure = new Treasure(0,1);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Water(testMap, z, i, null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		
		Team testTeam = new Team('a', null);
		
		Ship testShip = new Ship(testTeam, fieldArray[1][1], 0, null);
		fieldArray[1][1]=new Base(testMap, 1, 1, testTeam, testShip);
		
		assertTrue(fieldArray[1][1].getTreasure()==null);
		

		
		
		testShip.setLoad(3);
		
		testShip.setPC(5);
		
		testShip.changeMoral(+4);
		
		testDrop.execute(testShip);
		testDrop.equals(testDrop);
		testDrop.equals(null);
		
		assertTrue("load should be 0"+ " but is "+ testShip.getLoad(),testShip.getLoad()==0);
		assertTrue("moral should be 4",testShip.getMoral()==4);
		assertTrue("pc should be 5",testShip.getPC()==5);				// PC already increased in act-method of ship
		assertTrue("Trasure found",fieldArray[1][1].getTreasure()==null);	
		assertTrue("Team score should be 3",testTeam.getScore()==3);
	}
	//+ " but is "+ testShip.getLoad();
	
}
