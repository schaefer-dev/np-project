package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.commands.Refresh;
import de.unisaarland.cs.st.pirates.group5.model.Base;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Island;
import de.unisaarland.cs.st.pirates.group5.model.Kraken;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.ProvisionIsland;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.Water;


public class CommandRefreshTest {
	private List<Kraken> kraken = new LinkedList<Kraken>();
	
	@Test
	public void refreshProvisionIsland01(){
		
		Refresh testRefresh = new Refresh(4, 10);
		Random testRandom = new Random(1);	
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Water(testMap, z, i, null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		
		fieldArray[1][0]=new ProvisionIsland(testMap, 1, 0);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(0);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-4);
		
		testRefresh.execute(testShip);
		
		assertTrue(testShip.getLoad()==0);
		assertTrue(testShip.getMoral()==4);
		assertTrue(testShip.getPC()==5);				// PC already increased in act-method of ship
	}
	
	@Test
	public void refreshProvisionIsland02(){
		
		Refresh testRefresh = new Refresh(4, 10);
		
		Random testRandom = new Random(1);	
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Water(testMap, z, i, null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		
		//fieldArray[1][0]=new ProvisionIsland(testMap, 1, 0);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(0);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-4);
		
		testRefresh.execute(testShip);
		
		assertTrue(testShip.getLoad()==0);
		assertTrue(testShip.getMoral()==0);
		assertTrue(testShip.getPC()==10);
	}

	@Test
	public void refreshProvisionIsland(){
		
		Refresh testRefresh = new Refresh(4, 10);
		
		Random testRandom = new Random(1);	
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Water(testMap, z, i, null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		
		Ship testShip1 = new Ship(null, fieldArray[1][0], 0, null);
		
		fieldArray[1][0]=new Base(testMap, 1, 0, null, testShip1);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 1, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(0);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-4);
		
		testRefresh.execute(testShip);
		testRefresh.equals(testRefresh);
		testRefresh.equals(null);
		
		assertTrue(testShip.getLoad()==0);
		assertTrue(testShip.getMoral()==0);
		assertTrue(testShip.getPC()==10);
	}
	
	@Test
	public void refreshProvisionIsland03(){
		
		Refresh testRefresh = new Refresh(4, 10);
		
		Random testRandom = new Random(1);	
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Water(testMap, z, i, null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		
		fieldArray[1][0]=new Island(testMap, 1, 0, null);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(0);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-4);
		
		testRefresh.execute(testShip);
		
		assertTrue(testShip.getLoad()==0);
		assertTrue(testShip.getMoral()==0);
		assertTrue(testShip.getPC()==10);
	}
}
