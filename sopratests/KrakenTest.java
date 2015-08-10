package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.model.Base;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Island;
import de.unisaarland.cs.st.pirates.group5.model.Kraken;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.ProvisionIsland;
import de.unisaarland.cs.st.pirates.group5.model.Team;
import de.unisaarland.cs.st.pirates.group5.model.Water;



public class KrakenTest {

	/*
	 * move Test
	 */
	
	@Test
	public void moveKrakenWaterWithoutKraken01(){
		
		/* no kraken on field */
		Random testRandom = new Random(12);	// ergebnis des ersten Randoms ist 0! //TODO random werte nicht konstant -> test nutzlos
		
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[3][3];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		
		Kraken testKraken = new Kraken(0,fieldArray[1][1]);
		fieldArray[1][1]=new Water(testMap,1,1,testKraken);
		testKraken.setField(fieldArray[1][1]);
		
		testKraken.move();
		testKraken.equals(testKraken);
		testKraken.equals(null);
		assertTrue("KrakenX wrong", testKraken.getField().getX()==2);
		assertTrue("KrakenY wrong", testKraken.getField().getY()==1);
		assertTrue("Kraken not deleted correctly", testMap.getNeighbour(fieldArray[1][1], 0).getKraken()!=null);
		assertTrue("Kraken vanished", fieldArray[1][1].getKraken()==null);
		assertTrue("no correct Kraken found", testMap.getNeighbour(fieldArray[1][1], 0).getKraken()==testKraken);
	}
	
	@Test
	public void moveKrakenWaterWithKraken01(){
		
		/* with kraken on field */
		Random testRandom = new Random(12);	// ergebnis des ersten Randoms ist 0!
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[3][3];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		Kraken errorKraken = new Kraken(0,fieldArray[2][1]);
		fieldArray[2][1].setKraken(errorKraken);
		kraken.add(errorKraken);
		
		
		Kraken testKraken = new Kraken(0,fieldArray[1][1]);
		fieldArray[1][1].setKraken(testKraken);
		kraken.add(testKraken);
		
		testMap.setMapValues(fieldArray, kraken);
		
		testKraken.move();
		assertTrue("KrakenX wrong", testKraken.getField().getX()==1);
		assertTrue("KrakenY wrong", testKraken.getField().getY()==1);
		assertTrue("Kraken not found where he should be", testMap.getNeighbour(fieldArray[1][1], 0).getKraken()!=null);
		assertTrue("correct Kraken not found where he should be", testMap.getNeighbour(fieldArray[1][1], 0).getKraken()==errorKraken);
		assertTrue("Kraken which was hit not found correctly", fieldArray[1][1].getKraken()==testKraken);
	}
	
	
	@Test
	public void moveKrakenBase01(){
		Random testRandom = new Random(12);	// ergebnis des ersten Randoms ist 0!
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		Team testTeam = new Team('a',null);
		
		Field[][] fieldArray=new Field[3][3];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Base(testMap,z,i,testTeam,null);
			}
		}	
		Kraken testKraken = new Kraken(0,fieldArray[1][1]);
		fieldArray[1][1]=new Water(testMap,1,1,testKraken);
		
		testMap.setMapValues(fieldArray, kraken);
		
		testKraken.move();
		assertTrue("KrakenX wrong", testKraken.getField().getX()==1);
		assertTrue("KrakenY wrong", testKraken.getField().getY()==1);
		
		assertTrue("No Kraken found where it should be", testMap.getNeighbour(fieldArray[1][1], 0).getKraken()==null);
		assertTrue("No correct Kraken found where it should be", testMap.getNeighbour(fieldArray[1][1], 0).getKraken()!=testKraken);
	}
	
	@Test
	public void moveKrakenIsland01(){
		Random testRandom = new Random(12);	// ergebnis des ersten Randoms ist 0!
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[3][3];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Island(testMap,z,i,null);
			}
		}	
		Kraken testKraken = new Kraken(0,fieldArray[1][1]);
		fieldArray[1][1]=new Water(testMap,1,1,testKraken);
		
		testMap.setMapValues(fieldArray, kraken);
		
		testKraken.move();
		assertTrue("KrakenX wrong", testKraken.getField().getX()==1);
		assertTrue("KrakenY wrong", testKraken.getField().getY()==1);
		//assertTrue("Kraken moved but he should not!",testMap.getNeighbour(fieldArray[2][2], 0).getKraken()==null);
		assertTrue("Kraken vanished", testMap.getNeighbour(fieldArray[1][1], 0).getKraken()==null);
		assertTrue("Kraken not found where it should be", testMap.getNeighbour(fieldArray[1][1], 0).getKraken()!=testKraken);
		
	}
	
	@Test
	public void moveKrakenProvisionIsland01(){
		Random testRandom = new Random(12);	// ergebnis des ersten Randoms ist 0!
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[3][3];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new ProvisionIsland(testMap,z,i);
			}
		}	
		Kraken testKraken = new Kraken(0,fieldArray[1][1]);
		fieldArray[1][1]=new Water(testMap,1,1,testKraken);
		
		testMap.setMapValues(fieldArray, kraken);
		
		testKraken.move();
		assertTrue("KrakenX wrong", testKraken.getField().getX()==1);
		assertTrue("KrakenY wrong", testKraken.getField().getY()==1);
		assertTrue("Kraken moved but he should not!",testMap.getNeighbour(fieldArray[2][2], 0).getKraken()==null);
		assertTrue("Kraken vanished", testMap.getNeighbour(fieldArray[1][1], 0).getKraken()==null);
		assertTrue("Kraken not found where it should be", testMap.getNeighbour(fieldArray[1][1], 0).getKraken()!=testKraken);
	}
	
	
}
