package de.unisaarland.cs.st.pirates.group5.tests;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Island;
import de.unisaarland.cs.st.pirates.group5.model.Kraken;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.ProvisionIsland;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.Team;
import de.unisaarland.cs.st.pirates.group5.model.Treasure;
import de.unisaarland.cs.st.pirates.group5.model.Water;
import de.unisaarland.cs.st.pirates.group5.view.SimpleLogWriter;

public class MapTest {


	Random random;
	Map map = new Map(random, new SimpleLogWriter());

	Team a;
	Field[][] fields = new Field[4][4];
	Ship first = new Ship(a, null, 1, null);
	Ship second = new Ship(a, null, 2, first);
	Ship third = new Ship(a, null, 3, second);
	Kraken kraken = new Kraken(1, null);
	List<Kraken> krakens = new ArrayList<Kraken>();
	Water water = new Water(map, 0, 0, kraken);
	Island geradeaus = new Island(map, 1,0,null);
	Water rechtsUntenVorne = new Water(map,0,1,null);
	Water rechtsUntenHinten = new Water(map,3,1,null);
	Water hinten = new Water(map,3,0,null);
	ProvisionIsland linksObenVorne = new ProvisionIsland(map,0,3);
	Treasure treasure = new Treasure(0, 9);
	Island linksObenHinten = new Island(map,3,3,treasure);	
	
	@Before
	public void setUp(){
		krakens.add(kraken);
		
		fields[0][0] = water;
		fields[1][0] = geradeaus;
		fields[0][1] = rechtsUntenVorne;
		fields[3][1] = rechtsUntenHinten;
		fields[3][0] = hinten;
		fields[0][3] = linksObenVorne;
		fields[3][3] = linksObenHinten;
		map.setFirstShip(first);
		map.setMapValues(fields, krakens);
	}
	
	@Test
	public void testGetNeighbour(){
		
		assertEquals("getNeighbour fail!",map.getNeighbour(water, 0), geradeaus);
		assertEquals("getNeighbour fail!",map.getNeighbour(water, 1), rechtsUntenVorne);
		assertEquals("getNeighbour fail!",map.getNeighbour(water, 2), rechtsUntenHinten);
		assertEquals("getNeighbour fail!",map.getNeighbour(water, 3), hinten);
		assertEquals("getNeighbour fail!",map.getNeighbour(water, 4), linksObenHinten);
		assertEquals("getNeighbour fail!",map.getNeighbour(water, 5), linksObenVorne);
		assertEquals("getNeighbour fail!",map.getNeighbour(geradeaus, 3), water);
	}
	
	@Test
	public void testGetNeighbour1(){
		Field[][]fieldmap = new Field[3][3];		
		for (int x=0; x<3 ; x++){
			for (int y=0; y<3 ; y++){
				Water field = new Water(map,x,y,null);
				fieldmap[x][y]=field;
			}
		}
		map.setMapValues(fieldmap, krakens);
		
		assertEquals(fieldmap[2][1],map.getNeighbour(fieldmap[1][1], 0));
		assertEquals(fieldmap[1][0],map.getNeighbour(fieldmap[1][1], 4));
		assertEquals(fieldmap[0][1],map.getNeighbour(fieldmap[1][1], 3));
		assertEquals(fieldmap[1][2],map.getNeighbour(fieldmap[1][1], 2));
		assertEquals(fieldmap[2][2],map.getNeighbour(fieldmap[1][1], 1));
		assertEquals(fieldmap[2][0],map.getNeighbour(fieldmap[1][1], 5));
		assertEquals(fieldmap[2][1],map.getNeighbour(fieldmap[0][1], 3));
		assertEquals(fieldmap[0][1],map.getNeighbour(fieldmap[2][1], 0));
		assertEquals(fieldmap[0][2],map.getNeighbour(fieldmap[2][1], 1));
		assertEquals(fieldmap[0][0],map.getNeighbour(fieldmap[2][1], 5));
	}
	
	@Test
	public void testGetNeighbour265(){
		Field[][]fieldmap = new Field[28][28];		
		for (int x=0; x<28 ; x++){
			for (int y=0; y<28 ; y++){
				Water field = new Water(map,x,y,null);
				fieldmap[x][y]=field;
			}
		}
		map.setMapValues(fieldmap, krakens);
		
		assertEquals(fieldmap[27][5], map.getNeighbour(fieldmap[26][5], 0));
		assertEquals(fieldmap[27][4], map.getNeighbour(fieldmap[26][5], 5));
	}
	
	@Test 
	public void testGetFirstShip(){

		assertEquals("Konstukter Set FirstShip fail!",map.getFirstShip(), first);
			map.setFirstShip(second);
			assertEquals("SetFirstShip fail!",map.getFirstShip(), second);
	}
	@Test 
	public void testGetKraken(){	
		assertEquals("Map/Kraken relation broken!", map.getKraken().size(), 1);
			assertTrue("Ultra sinnloser Test.",map.getKraken().contains(kraken));
	}
	@Test
	public void testGetIDs(){
		assertEquals("Not next free ActorID.",map.giveNewActorID(),0);
			assertEquals("Not next free ActorID.",map.giveNewActorID(),1);

		assertEquals("Not next free EntityID.",map.giveNewEntityID(),0);
			assertEquals("Not next free EntityID.",map.giveNewEntityID(),1);
	}
	
	@Test
	public void testGetTreasure(){		
		if(linksObenHinten.getTreasure() == null)
			fail("da muss ein Schatz liegen");
		
		if(geradeaus.getTreasure() != null )
			fail("da darf kein Schatz liegen");
				
	}
}
