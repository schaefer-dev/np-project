package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.commands.Goto;
import de.unisaarland.cs.st.pirates.group5.commands.Move;
import de.unisaarland.cs.st.pirates.group5.commands.Sense;
import de.unisaarland.cs.st.pirates.group5.controller.Command;
import de.unisaarland.cs.st.pirates.group5.model.Base;
import de.unisaarland.cs.st.pirates.group5.model.BoolWert;
import de.unisaarland.cs.st.pirates.group5.model.CellType;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Island;
import de.unisaarland.cs.st.pirates.group5.model.Kraken;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.ProvisionIsland;
import de.unisaarland.cs.st.pirates.group5.model.Register;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.ShipType;
import de.unisaarland.cs.st.pirates.group5.model.Team;
import de.unisaarland.cs.st.pirates.group5.model.Treasure;
import de.unisaarland.cs.st.pirates.group5.model.Water;

public class CommandSenseTest {
	
	Sense sense1 = new Sense(0);
	Sense sense2 = new Sense(1);
	Sense sense3 = new Sense(2);
	Sense sense4 = new Sense(3);
	Sense sense5 = new Sense(4);
	Sense sense6 = new Sense(5);
	Sense sense7 = new Sense(6);
	Move  move   = new Move(0);
	Goto  goto7  = new Goto(7);
	
	Treasure treasure = new Treasure(15,9);

	List<Command> senses = new ArrayList<Command>();
	Team a = new Team('a',senses);
	Team b = new Team('b',senses);
	

	
	Field[][] fields = new Field[4][4];
	Random random;
	DummyLogWriter dummyLog = new DummyLogWriter();
	Map map = new Map(random,dummyLog);
	Ship shipAtNullNull = new Ship(a,null,1, null);
	Ship enemyShip = new Ship(b,null,2,shipAtNullNull);
	Ship alliedShip = new Ship(a,null,3,enemyShip);

	Base 			nullNull_homeBase 		 = new Base(map,0,0,a, shipAtNullNull);
	Water 			nullDrei_Water 			 = new Water(map, 0,3,null);
	ProvisionIsland dreiNull_ProvisionIsland = new ProvisionIsland(map,3,0);
	Island 			nullEins_TreasureIsland  = new Island(map,0,1, treasure);
	Water 			einsNull_EnemyHere 		 = new Water(map,1,0,null);
	Base 			dreiEinsEnemyBase 		 = new Base(map,3,1,b, null);
	Water 			dreiDrei_AllyHere 		 = new Water(map,3,3,null);
	
	
		
		@Before
		public void setUp(){
			
			senses.add(sense1);
			senses.add(sense2);
			senses.add(sense3);
			senses.add(sense4);
			senses.add(sense5);
			senses.add(sense6);
			senses.add(sense7);
			senses.add(move);
			senses.add(goto7);
			dreiDrei_AllyHere.setShip(alliedShip);//3,3
			dreiDrei_AllyHere.placeBuoy(3, a);
			dreiDrei_AllyHere.placeBuoy(4, a);
			einsNull_EnemyHere.setShip(enemyShip);//1,0
			einsNull_EnemyHere.placeBuoy(5, a);
			nullDrei_Water.placeBuoy(1, a);//0,3
			nullDrei_Water.placeBuoy(1, b);
			nullDrei_Water.placeBuoy(4, a);
			nullDrei_Water.placeBuoy(2, a);
			
			enemyShip.setField(einsNull_EnemyHere);
			alliedShip.setField(dreiDrei_AllyHere);
			nullDrei_Water.exchangeTreasure(3);
			
			shipAtNullNull.setField(nullNull_homeBase);
			
			a.addShip(shipAtNullNull);
			a.addShip(alliedShip);
			b.addShip(enemyShip);

			shipAtNullNull.setLoad(3);
	
			enemyShip.setLoad(3);
			enemyShip.changeMoral(-3);
			enemyShip.changeCondition(-2);
	
			fields[0][0] = nullNull_homeBase;
			fields[0][1] = nullEins_TreasureIsland;
			fields[3][1] = dreiEinsEnemyBase;
			fields[3][0] = dreiNull_ProvisionIsland;
			fields[3][3] = dreiDrei_AllyHere;
			fields[1][0] = einsNull_EnemyHere;
			fields[0][3] = nullDrei_Water;
			
			map.setMapValues(fields, new ArrayList<Kraken>());
		}



		@Test
		public void testAtInit() {
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_celltype),CellType.Undefined.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_marker0),BoolWert.Undefined.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_marker1),BoolWert.Undefined.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_marker2),BoolWert.Undefined.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_marker3),BoolWert.Undefined.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_marker4),BoolWert.Undefined.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_marker5),BoolWert.Undefined.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_enemymarker),BoolWert.Undefined.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_shiptype),ShipType.Undefined.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_supply), BoolWert.Undefined.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_treasure), BoolWert.Undefined.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_supply), BoolWert.Undefined.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_shipdirection), Ship.undefinedInt);
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_shipcondition), Ship.undefinedInt);
		}
		
		@Test
		public void testEinsNull(){
			shipAtNullNull.act();
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_celltype),CellType.Empty.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_marker5),BoolWert.True.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_marker3),BoolWert.False.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_shiptype),ShipType.Enemy.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_shipdirection), 0);
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_shipcondition), 1);
		}
		
		@Test
		public void testNullEins(){
			shipAtNullNull.act();
			shipAtNullNull.act();
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_celltype),CellType.Island.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_treasure), BoolWert.True.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_marker5),BoolWert.False.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_shiptype),ShipType.Undefined.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_shipdirection), Ship.undefinedInt);
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_shipcondition), Ship.undefinedInt);
		}
		
		@Test
		public void testDreiEins(){
			shipAtNullNull.act();
			shipAtNullNull.act();
			shipAtNullNull.act();
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_celltype),CellType.EnemyHome.ordinal());
		}
		
		@Test
		public void testDreiNull(){
			shipAtNullNull.act();
			shipAtNullNull.act();
			shipAtNullNull.act();
			shipAtNullNull.act();
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_celltype),CellType.Island.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_supply), BoolWert.True.ordinal());
		}
		
		@Test
		public void testDreiDrei(){
			shipAtNullNull.act();
			shipAtNullNull.act();
			shipAtNullNull.act();
			shipAtNullNull.act();
			shipAtNullNull.act();
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_celltype),CellType.Empty.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_shiptype),ShipType.Friend.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_marker3),BoolWert.True.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_marker4),BoolWert.True.ordinal());
		}
		
		@Test
		public void testNullDrei(){
			shipAtNullNull.act();
			shipAtNullNull.act();
			shipAtNullNull.act();
			shipAtNullNull.act();
			shipAtNullNull.act();
			shipAtNullNull.act();
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_celltype),CellType.Empty.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_shiptype),ShipType.Undefined.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_marker1),BoolWert.True.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_marker2),BoolWert.True.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_marker4),BoolWert.True.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_enemymarker),BoolWert.True.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_treasure),BoolWert.True.ordinal());
		}
		
		@Test
		public void testNullNull(){

			shipAtNullNull.act();//schaut auf 0,0. Hat: water, HomeBase.(1)
			shipAtNullNull.act();
			shipAtNullNull.act();
			shipAtNullNull.act();
			shipAtNullNull.act();
			shipAtNullNull.act();
			shipAtNullNull.act();
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_celltype),CellType.Home.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_marker1),BoolWert.False.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_marker2),BoolWert.False.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_marker4),BoolWert.False.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_enemymarker),BoolWert.False.ordinal());
			assertEquals("",shipAtNullNull.getSenseRegister(Register.sense_treasure),BoolWert.False.ordinal());	
		}
		

}
