package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.controller.Command;
import de.unisaarland.cs.st.pirates.group5.model.Base;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.FieldType;
import de.unisaarland.cs.st.pirates.group5.model.Island;
import de.unisaarland.cs.st.pirates.group5.model.Kraken;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.ProvisionIsland;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.Team;
import de.unisaarland.cs.st.pirates.group5.model.Water;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;

//import commands.Drop;
//import commands.Move;

public class FieldTest {
/*enthaelt noch Tests, wenn nachher die Commands fertig sind, die exchangeTreasure(i), in verschiedenen Situationen, 
 * die von verschiedenen Commandos; gleichen Commandos mit unterschieldichen Ausgaengen; ausgeloest
 * werden, testen.*/
	
	Random random;
	DummyLogWriter log = new DummyLogWriter();
	Map map = new Map(random, log);
	List<Kraken> krakens = new ArrayList<Kraken>();
	List<Command> tactics = new ArrayList<Command>();
	Team a = new Team('a', tactics);
	List<Command> btactics = new ArrayList<Command>();
	Team b = new Team('b', btactics);	
	Field[][] fields = new Field[4][4];
	Kraken kraken;
	Ship shipB;
	Ship shipA;
	
	Water water;
	Water geradeaus;
	Base rechtsUntenVorne;
	Base rechtsUntenHinten;
	Water hinten;
	Island linksObenVorne;
	Island linksObenHinten;
	
	//Water  elsewhere = new Water(map, 2,0,null);
	//Water  nextToElseWhere = new Water(map, 1,1, null);

	/*
	;
	Move goAhead = new Move(0);
	Drop letItGo = new Drop();
	
	
	
	Ship firstShip = new Ship(a,null,0,null);
	Ship alliedShip = new Ship(a,null,1,firstShip);
	Ship enemyShip = new Ship(b,null,2,alliedShip);
	Ship enemyShip2 = new Ship(b,null,3,enemyShip);
	Ship alliedShip2 = new Ship(a,null,1,enemyShip2);*/
	
	@Before
	public void setUp(){
		
		
		kraken = new Kraken(map.giveNewEntityID(), null);
		shipB = new Ship(b, null, map.giveNewActorID(), null);
		shipA = new Ship(a, null, map.giveNewActorID(), shipB);
		
		water = new Water(map, 0, 0, null);
		geradeaus = new Water(map, 1,0,null);
		rechtsUntenVorne = new Base(map,0,1,b,shipB);
		rechtsUntenHinten = new Base(map,3,1,a, shipA);
		hinten = new Water(map,3,0,kraken);
		linksObenVorne = new Island(map,0,3,null);
		linksObenHinten = new ProvisionIsland(map,3,3);
		krakens.add(kraken);

		fields[0][0] = water;//ship
		fields[0][1] = rechtsUntenVorne;//EnemyBase, enemyShip
		fields[1][0] = geradeaus;//alliedsip
		fields[3][0] = hinten;//kraken, treasure
		fields[0][3] = linksObenVorne;
		fields[3][3] = linksObenHinten; //ProvisionIsland
		fields[3][1] = rechtsUntenHinten;//Base
	//	fields[2][0] = elsewhere; //enemyship2
	//	fields[1][1] = nextToElseWhere;//alliedship2
		
		map.setMapValues(fields, krakens);
		
		/*tactics.add(goAhead);
		btactics.add(goAhead);
		btactics.add(letItGo);
		
		b = new Team('b',btactics);
		a = new Team('a',tactics); 
		
		
		hinten.setKraken(kraken);
		hinten.exchangeTreasure(5);
		water.setShip(firstShip);
		
		firstShip.setField(water);
		firstShip.changeCondition(-3);
		firstShip.setLoad(3);
		firstShip.changeDirection(false);//firstship einmal um sich selbst drehen
		firstShip.changeDirection(false);
		firstShip.changeDirection(false);
		water.setShip(firstShip);
		
		alliedShip.setField(geradeaus);
		alliedShip.changeCondition(-3);
		alliedShip.setLoad(4);
		alliedShip.changeMoral(-2);
		alliedShip.changeDirection(false);//ebenso alliedship
		alliedShip.changeDirection(false);
		alliedShip.changeDirection(false);
		geradeaus.setShip(alliedShip);
		
		enemyShip.setField(rechtsUntenVorne);
		enemyShip.setLoad(2);
		enemyShip.changeDirection(true);//enemyShip soll nach 0,0 schauen
		rechtsUntenVorne.setShip(enemyShip);
		
		alliedShip2.setField(elsewhere);
		enemyShip2.changeDirection(false);
		elsewhere.setShip(alliedShip2);
		
		enemyShip2.setField(nextToElseWhere);
		enemyShip2.changeDirection(true);
		nextToElseWhere.setShip(enemyShip2);		*/
	}
	public static Integer toInteger(int i)
	{
		return new Integer(i);
	}
	public static Integer toInteger(Key k)
	{
		return toInteger(k.ordinal());
	}
	
	@Test
	public void testLogger()
	{
 		assertSame("Wrong logger was given", log,water.provideLogger());
	}
	@Test
	public void testGetNeighbour() {
		assertEquals("getNeighbour fail!",water.getNeigbour(0),geradeaus);
		assertEquals("getNeighbour fail!",water.getNeigbour(1),rechtsUntenVorne);
		assertEquals("getNeighbour fail!",water.getNeigbour(2),rechtsUntenHinten);
		assertEquals("getNeighbour fail!",water.getNeigbour(3),hinten);
		assertEquals("getNeighbour fail!",water.getNeigbour(4),linksObenHinten);
		assertEquals("getNeighbour fail!",water.getNeigbour(5),linksObenVorne);
			assertEquals("getNeighbour fail!",hinten.getNeigbour(0),water);
			assertEquals("getNeighbour fail!",linksObenHinten.getNeigbour(1),water);
			assertEquals("getNeighbour fail!",linksObenHinten.getNeigbour(0),linksObenVorne);
	}		
	@Test
	public void testGetFieldType(){
		assertEquals("FieldType broken",geradeaus.getFieldType(), FieldType.Water);
		assertEquals("FieldType broken",water.getFieldType(), FieldType.Water);
		assertEquals("FieldType broken",hinten.getFieldType(), FieldType.Water);
		assertEquals("FieldType broken",rechtsUntenVorne.getFieldType(), FieldType.Base);
		assertEquals("FieldType broken",rechtsUntenHinten.getFieldType(), FieldType.Base);
		assertEquals("FieldType broken",linksObenHinten.getFieldType(), FieldType.ProvisionIsland);
		assertEquals("FieldType broken",linksObenVorne.getFieldType(), FieldType.Island);
	}	
	@Test 
	public void testExchangeTreasure(){
		log.emptyLists();
		int id = hinten.getMap().giveNewEntityID();
		hinten.exchangeTreasure(4);
		try{
		assertEquals("ExchangeTreasure broken.", hinten.getTreasure().getValue(),4);
		assertFalse("New treasure should be created, therefore there is no need for a notify", log.what.contains("notify"));
		assertTrue("Creation of treasure must be logged.", log.what.remove("create"));
		assertTrue("Wrong entity was logged in create",log.entities.remove(Entity.TREASURE));
		assertTrue("Not the right keys were logged", log.values.remove(toInteger(Key.VALUE)) && log.values.remove(toInteger(Key.X_COORD)) && log.values.remove(toInteger(Key.Y_COORD)));
		assertTrue("Not the right values were logged", log.values.remove(toInteger(id+1)) && log.values.remove(toInteger(hinten.getX())) && log.values.remove(toInteger(hinten.getY())) && log.values.remove(toInteger(4)));
		assertTrue("Too much logged", log.values.size() == 0 && log.entities.size()==0 && log.what.size() == 0);
		hinten.exchangeTreasure(-1);
		assertEquals("ExchangeTreasure broken. ", hinten.getTreasure().getValue(),3);
		assertTrue("change was not logged via notify", log.what.remove("notify"));
		assertFalse("Treasure already exists, no need to log create.", log.what.contains("create"));
		assertTrue("Wrong entity was logged in notify",log.entities.remove(Entity.TREASURE));
		assertTrue("Logged the wrong key", log.values.remove(toInteger(Key.VALUE)));
		assertTrue("Not the right values were logged", log.values.remove(toInteger(id + 1)) && log.values.remove(toInteger(3)));
		assertTrue("Too much logged", log.values.size() == 0 && log.entities.size()==0 && log.what.size() == 0);
		hinten.exchangeTreasure(1);
		assertEquals("ExchangeTreasure broken.", hinten.getTreasure().getValue(),4);
		assertTrue("change was not logged via notify", log.what.remove("notify"));
		assertFalse("Treasure already exists, no need to log create.", log.what.contains("create"));
		assertTrue("Wrong entity was logged in create",log.entities.remove(Entity.TREASURE));
		assertTrue("Logged the wrong key", log.values.remove(toInteger(Key.VALUE)));
		assertTrue("Not the right values were logged", log.values.remove(toInteger(id+1)) && log.values.remove(toInteger(4)));
		hinten.exchangeTreasure(-4);
		assertNull("Treasure was not deleted!", hinten.getTreasure());
		assertFalse("Treasure already exists, no need to log create.", log.what.contains("create"));
		assertFalse("No need to notify if deleting treasure", log.what.contains("notify"));
		assertTrue("Must log destruction of Treasure", log.what.remove("destroy"));
		assertTrue("Wrong entity was logged in notify",log.entities.remove(Entity.TREASURE));
		assertTrue("Didn't log ID", log.values.remove(toInteger(id+1)));
		assertTrue("Too much logged", log.values.size() == 0 && log.entities.size()==0 && log.what.size() == 0);
		}
		catch(NullPointerException e)
		{
			fail("This TreasureIsland does not seem to contain any treasure.");
		}
	}
	
	@Test
	public void testExchangeTreasureBase()
	{
		log.emptyLists();
		Base base = ((Base) rechtsUntenHinten);
		int score = base.getTeam().getScore();
		
		rechtsUntenHinten.dropTreasure(3);
		assertTrue("giving Treasure to the base did not work.", score+3 == base.getTeam().getScore());
		assertTrue("change in FleetScore was not logged.", log.what.remove("changeScore"));
		assertTrue("wrong values were logged", log.values.remove(toInteger(base.getTeam().getName()-'a')) && log.values.remove(toInteger(3)));
		assertTrue("Logged wrong things as well.", log.cells.size() == 0 && log.entities.size() == 0 && log.values.size() == 0 && log.what.size() == 0);
	}
	
	@Test
	public void testSetShipBaseWrongTeam()
	{
		assertFalse("Wrong ship should not be moved on Base", rechtsUntenHinten.setShip(shipB));
		rechtsUntenHinten.setShip(null);
		assertTrue("Ship of own Team should be set on Base", rechtsUntenHinten.setShip(shipA));
	}
	@Test 
	public void testKrakenInit()
	{
		assertSame("The field of Kraken was not set correctly in the Constructor of Water,", hinten, kraken.getField());
	}
	
	@Test
	public void testMoveKraken(){
		log.emptyLists();
		hinten.moveKraken(water);
		assertEquals("moveKraken hat nicht funktioniert", kraken.getField(), water);
		assertEquals("Kraken was not removed from old field",hinten.getKraken(), null);
		assertEquals("Kraken was not set on new field",water.getKraken(),kraken);
		assertTrue("move of Kraken wasn't logged.",log.what.remove("notify") && log.entities.remove(Entity.KRAKEN) && log.values.remove(toInteger(kraken.getId())) && log.values.remove(toInteger(Key.X_COORD)) && log.values.remove(toInteger(water.getX())));
		assertFalse("Y-Coordinate was logged although it hasn't changed.", log.values.contains(toInteger(Key.Y_COORD)));
		assertTrue("Logged to much", log.entities.size() == 0 && log.values.size() == 0 && log.what.size() ==0);
		
	}
	@Test
	public void testBuoyfunctions(){
		log.emptyLists();
		assertTrue("Bouy appeared out of nowhere. That's not good." ,water.getBuoys().size() == 0);
		int idBefore = map.giveNewEntityID();
		water.placeBuoy(1, a);
		assertEquals("buoy not placed",water.getBuoys().size(),1);
		assertTrue("No new bouy was created.", log.what.remove("create"));
		assertTrue("The wrong entity was passed on to the logger.", log.entities.remove(Entity.BUOY));
		assertTrue("Id was not logged.", log.values.remove(toInteger(idBefore + 1)));
		assertTrue("Keys were not logged correctly.", log.values.remove(toInteger(Key.X_COORD)) && log.values.remove(toInteger(Key.Y_COORD)) && log.values.remove(toInteger(Key.FLEET)) && log.values.remove(toInteger(Key.VALUE)));
		assertTrue("Wrong values were logged", log.values.remove(toInteger(water.getX())) && log.values.remove(toInteger(water.getY())) && log.values.remove(toInteger(1)) && log.values.remove(toInteger('a'-'a')));
		assertTrue("Logged something false.", log.entities.size() == 0 && log.values.size() == 0 && log.what.size() ==0);
		assertFalse("Bouy cannot be placed twice", water.placeBuoy(1,a) || log.what.contains("create") || log.what.contains("notify"));
		
		water.deleteBuoy(a, 1);
		assertEquals("buoy not deleted",water.getBuoys().size(),0);
		assertTrue("Deletion of Bouy not logged correctly.", log.what.remove("destroy") && log.entities.remove(Entity.BUOY) && log.values.remove(toInteger(idBefore+1)));
		
		idBefore = map.giveNewEntityID();
		assertTrue("why not buoy 1", water.placeBuoy(1,a));
		assertEquals("buoy not placed",water.getBuoys().size(),1);
		assertTrue("No new bouy was created.", log.what.remove("create"));
		assertTrue("The wrong entity was passed on to the logger.", log.entities.remove(Entity.BUOY));
		assertTrue("Id was not logged.", log.values.remove(toInteger(idBefore + 1)));
		assertTrue("Keys were not logged correctly.", log.values.remove(toInteger(Key.X_COORD)) && log.values.remove(toInteger(Key.Y_COORD)) && log.values.remove(toInteger(Key.FLEET)) && log.values.remove(toInteger(Key.VALUE)));
		assertTrue("Wrong values were logged", log.values.remove(toInteger(water.getX())) && log.values.remove(toInteger(water.getY())) && log.values.remove(toInteger(1)) && log.values.remove(toInteger('a'-'a')));
		assertTrue("Logged something false.", log.entities.size() == 0 && log.values.size() == 0 && log.what.size() ==0);
		
		idBefore = map.giveNewEntityID();
		assertTrue("why not buoy 2", water.placeBuoy(2,a));
		assertEquals("buoy not placed",water.getBuoys().size(),2);
		assertTrue("No new bouy was created.", log.what.remove("create"));
		assertTrue("The wrong entity was passed on to the logger.", log.entities.remove(Entity.BUOY));
		assertTrue("Id was not logged.", log.values.remove(toInteger(idBefore + 1)));
		assertTrue("Keys were not logged correctly.", log.values.remove(toInteger(Key.X_COORD)) && log.values.remove(toInteger(Key.Y_COORD)) && log.values.remove(toInteger(Key.FLEET)) && log.values.remove(toInteger(Key.VALUE)));
		assertTrue("Wrong values were logged", log.values.remove(toInteger(water.getX())) && log.values.remove(toInteger(water.getY())) && log.values.remove(toInteger(2)) && log.values.remove(toInteger('a'-'a')));
		assertTrue("Logged something false.", log.entities.size() == 0 && log.values.size() == 0 && log.what.size() ==0);
		
		idBefore = map.giveNewEntityID();
		assertTrue("why not buoy 3", water.placeBuoy(3,a));
		assertEquals("buoy not placed",water.getBuoys().size(),3);
		assertTrue("No new bouy was created.", log.what.remove("create"));
		assertTrue("The wrong entity was passed on to the logger.", log.entities.remove(Entity.BUOY));
		assertTrue("Id was not logged.", log.values.remove(toInteger(idBefore + 1)));
		assertTrue("Keys were not logged correctly.", log.values.remove(toInteger(Key.X_COORD)) && log.values.remove(toInteger(Key.Y_COORD)) && log.values.remove(toInteger(Key.FLEET)) && log.values.remove(toInteger(Key.VALUE)));
		assertTrue("Wrong values were logged", log.values.remove(toInteger(water.getX())) && log.values.remove(toInteger(water.getY())) && log.values.remove(toInteger(3)) && log.values.remove(toInteger('a'-'a')));
		assertTrue("Logged something false.", log.entities.size() == 0 && log.values.size() == 0 && log.what.size() ==0);
		
		idBefore = map.giveNewEntityID();
		assertTrue("why not buoy 4", water.placeBuoy(4,a));
		assertEquals("buoy not placed",water.getBuoys().size(),4);
		assertTrue("No new bouy was created.", log.what.remove("create"));
		assertTrue("The wrong entity was passed on to the logger.", log.entities.remove(Entity.BUOY));
		assertTrue("Id was not logged.", log.values.remove(toInteger(idBefore + 1)));
		assertTrue("Keys were not logged correctly.", log.values.remove(toInteger(Key.X_COORD)) && log.values.remove(toInteger(Key.Y_COORD)) && log.values.remove(toInteger(Key.FLEET)) && log.values.remove(toInteger(Key.VALUE)));
		assertTrue("Wrong values were logged", log.values.remove(toInteger(water.getX())) && log.values.remove(toInteger(water.getY())) && log.values.remove(toInteger(4)) && log.values.remove(toInteger('a'-'a')));
		assertTrue("Logged something false.", log.entities.size() == 0 && log.values.size() == 0 && log.what.size() ==0);
		
		idBefore = map.giveNewEntityID();
		assertTrue("why not buoy 5", water.placeBuoy(5,a));
		assertEquals("buoy not placed",water.getBuoys().size(),5);
		assertTrue("No new bouy was created.", log.what.remove("create"));
		assertTrue("The wrong entity was passed on to the logger.", log.entities.remove(Entity.BUOY));
		assertTrue("Id was not logged.", log.values.remove(toInteger(idBefore + 1)));
		assertTrue("Keys were not logged correctly.", log.values.remove(toInteger(Key.X_COORD)) && log.values.remove(toInteger(Key.Y_COORD)) && log.values.remove(toInteger(Key.FLEET)) && log.values.remove(toInteger(Key.VALUE)));
		assertTrue("Wrong values were logged", log.values.remove(toInteger(water.getX())) && log.values.remove(toInteger(water.getY())) && log.values.remove(toInteger(5)) && log.values.remove(toInteger('a'-'a')));
		assertTrue("Logged something false.", log.entities.size() == 0 && log.values.size() == 0 && log.what.size() ==0);
		
		water.deleteBuoy(b, 3);
		assertTrue("Deleted Bouy did not exist, thus no logging should take place.", log.entities.size()==0 && log.values.size() == 0 && log.what.size() == 0 && log.cells.size() == 0);
	}
	@Test 
	public void testSetShip(){
		Ship firstShip = new Ship(a,water,map.giveNewActorID(),null);
		log.emptyLists();
		water.setShip(firstShip);
		assertEquals("Ship insertion failed.",firstShip, water.getShip());
		assertFalse("setting of Ship should not habe been logged since ships positions has not changed.",log.what.remove("notify"));
	}
	
	@Test
	public void testMoveShip()
	{
		log.emptyLists();
		rechtsUntenHinten.moveShip(hinten);
		assertEquals("moveShip hat nicht funktioniert", hinten, shipA.getPosition());
		assertEquals("Ship was not removed from old field",rechtsUntenHinten.getShip(), null);
		assertEquals("Ship was not set on new field", hinten.getShip(),shipA);
		assertTrue("move of Kraken wasn't logged.",log.what.remove("notify") && log.entities.remove(Entity.SHIP) && log.values.remove(toInteger(shipA.getID())) && log.values.remove(toInteger(Key.Y_COORD)) && log.values.remove(toInteger(hinten.getY())));
		assertFalse("X-Coordinate was logged although it hasn't changed.", log.values.contains(toInteger(Key.X_COORD)));
		assertTrue("Logged to much", log.entities.size() == 0 && log.values.size() == 0 && log.what.size() ==0);
	}
	@Test
	public void testCheckBase(){	
		assertEquals("",rechtsUntenHinten.getTeam().getName(),'a');
	}	
		
		/*	@Test (expected=Exception.class)
		public void testWithExtendedSetup(){ 
		firstShip.act();
			assertEquals("ExchangeTreasure broken.", hinten.getTreasure().getValue(),3);
				
				//Fall2: Ein Schiff, das stand, versinkt
				alliedShip.act();
				enemyShip.act();
				assertEquals("ExchangeTreasure broken.", water.getTreasure().getValue(),2);
				assertEquals("ExchangeTreasure broken.", water.getShip().getLoad(),4);
				
				//Fall3: Ein Schiff, das sich bewegt hat, verliert,ohne zu versinken; und Schiff, 
				alliedShip2.setLoad(1);
				enemyShip2.changeCondition(-2);
				enemyShip2.setLoad(4);
				enemyShip2.act();				
				assertEquals("ExchangeTreasure broken.", elsewhere.getShip().getLoad(),4);
				assertEquals("ExchangeTreasure broken.", nextToElseWhere.getTreasure().getValue(),1);
				
				//Fall4: Ein Schiff, das sich nicht bewegt hat, verliert, ohne zu versinken. 
				alliedShip2.setLoad(4);
				alliedShip2.changeCondition(-2);
				enemyShip2.setLoad(0);
				enemyShip2.act();
				assertEquals("ExchangeTreasure broken.", nextToElseWhere.getShip().getLoad(),4);
				if(elsewhere.getTreasure() != null)	//Kein Schatz mit Value 0 darf entstehen.
					fail("Da sollte kein Schatz liegen");
				
							//Fall5: Ein Schiff, das sich bewegt hat, verliert und versinkt
							alliedShip2.setLoad(1);
							enemyShip2.changeCondition(-3);
							enemyShip2.setLoad(4);
							enemyShip2.act();
							assertEquals("ExchangeTreasure broken.", nextToElseWhere.getTreasure().getValue(),1);
							assertEquals("ExchangeTreasure broken.", elsewhere.getShip().getLoad(),4);


		}*/	

}
