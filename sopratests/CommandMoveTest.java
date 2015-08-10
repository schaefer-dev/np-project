package de.unisaarland.cs.st.pirates.group5.tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.commands.Move;
import de.unisaarland.cs.st.pirates.group5.model.Base;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Island;
import de.unisaarland.cs.st.pirates.group5.model.Kraken;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.ProvisionIsland;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.Team;
import de.unisaarland.cs.st.pirates.group5.model.Water;



//TODO add tests for treasure creation / random fight / condition-dependend fight
public class CommandMoveTest {
	

	
	private Map map;
	
	private Field[][] fields;
	
	private Field island1;
	private Field water1;
	private Field provision1;
	private Field baseenemy;
	private Field waterme;
	private Field waterkraken;
	private Field basefriend;
	private Field waterenemy;
	private Field waterfriend;
	
	private Ship shipme;
	private Ship shipfriend;
	private Ship shipenemy;
	
	private Team teamfriend;
	private Team teamenemy;
	
	private char a;
	private char b;
	
	private Kraken kraken;
	
	private Move moveit;
	
	private int mecondition;
	private int enemycondition;
	private int meload;

	

	@Before
	public void setUp(){
		
		fields = new Field[3][3];
		
		moveit = new Move(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Random testRandom = new Random();
		map = new Map(testRandom, testLog);
		
		
		a = 0;
		b = 1;
		teamfriend = new Team(a, null);
		teamenemy = new Team(b, null);
		
		
		
		
		island1 = new Island(map, 0, 0, null);
		water1 = new Water(map, 1, 0, null);
		provision1 = new ProvisionIsland(map, 2, 0);
		baseenemy = new Base(map, 0, 1, teamenemy, null);
		waterme = new Water(map, 1, 1, null);

		waterkraken = new Water(map, 2, 1, kraken);

		basefriend = new Base(map, 0, 2, teamfriend, null);
		waterenemy = new Water(map, 1, 2, null);
		waterfriend = new Water(map, 2, 2, null);
		
		kraken = new Kraken(0, waterkraken);
		shipme = new Ship(teamfriend, waterme, 1, shipfriend);
		shipenemy = new Ship(teamenemy, waterenemy, 2, shipme);
		shipfriend = new Ship(teamfriend, waterfriend, 0, null);
		
		shipfriend.setNextShip(shipme);
		shipme.setNextShip(shipenemy);
		
		teamfriend.addShip(shipme);
		teamenemy.addShip(shipenemy);
		teamfriend.addShip(shipfriend);
		
		waterfriend.setShip(shipfriend);
		waterenemy.setShip(shipenemy);
		waterkraken.setKraken(kraken);
		waterme.setShip(shipme);
		
		fields[0][0] = island1;
		fields[1][0] = water1;
		fields[2][0] = provision1;
		fields[0][1] = baseenemy;
		fields[1][1] = waterme;
		fields[2][1] = waterkraken;
		fields[0][2] = basefriend;
		fields[1][2] = waterenemy;
		fields[2][2] = waterfriend;
		
		ArrayList<Kraken> krakenlist = new ArrayList<Kraken>();
		krakenlist.add(kraken);
		
		map.setMapValues(fields, krakenlist);
		
		map.setFirstShip(shipme);
		
		mecondition = shipme.getCondition();
		enemycondition = shipenemy.getCondition();
		meload = shipme.getLoad();
		
				
	}
	

	@Test
	 public void testMoveBaseShip(){
		Ship shipenemy2 = new Ship(teamenemy, baseenemy, 3, null);
		baseenemy.setShip(shipenemy2);
		
		shipenemy.changeDirection(true);
		shipenemy.changeDirection(true);
		
		moveit.execute(shipenemy);
		
		assertTrue ("don´t move!", shipenemy.getPosition().equals(waterenemy));
		assertTrue("Don´t move!",waterenemy.getShip().equals(shipenemy));
		assertTrue("pause must be 0", shipme.getPause() == 0);
		assertTrue("Don´t move!",baseenemy.getShip().equals(shipenemy2));
	 }

	@Test
	 public void testMoveWater1(){
		shipme.changeDirection(true);
		shipme.changeDirection(true);
		
		
		moveit.execute(shipme);
		
		assertTrue ("move to water1", shipme.getPosition().equals(water1));
		assertNull("Ship must be deleted from old field",waterme.getShip());
		assertTrue("pause must be 4", shipme.getPause() == 4);
	 }
	
	@Test
	 public void testMoveWater2(){
		shipme.changeDirection(true);
		shipme.changeDirection(true);
		shipme.setLoad(1);
		
		moveit.execute(shipme);
		
		assertTrue ("move to water1", shipme.getPosition().equals(water1));
		assertNull("Ship must be deleted from old field",waterme.getShip());
		assertTrue("pause must be 6", shipme.getPause() == 6);
	 }
	
	@Test
	 public void testMoveWater3(){
		shipme.changeDirection(true);
		shipme.changeDirection(true);
		shipme.setLoad(1);
		shipme.changeMoral(-4);
		
		moveit.execute(shipme);
		
		assertTrue ("move to water1", shipme.getPosition().equals(water1));
		assertNull("Ship must be deleted from old field",waterme.getShip());
		assertTrue("pause must be 8", shipme.getPause() == 8);
	 }
	
	@Test
	public void testMoveShipFriend(){
		shipme.changeDirection(false);
		moveit.execute(shipme);
		
		assertTrue ("move must not be succesfull", shipme.getPosition().equals(waterme));
		assertTrue("ship must not move, still old field",waterme.getShip().equals(shipme));
		assertTrue("pc must be changed to else case",shipme.getPC() == 13);
		assertTrue ("pause must be 0", shipme.getPause() == 0);
	}
	
	@Test
	public void testMoveShipEnemyLoosesurvive(){
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipme.changeMoral(4);
		shipenemy.changeMoral(-4);
		
		moveit.execute(shipme);
		
		
		assertTrue ("ship must not changed fields",shipme.getPosition().equals(waterme));
		assertTrue("ship must not changed fields",waterme.getShip().equals(shipme));
		assertTrue ("ship must not changed fields",shipenemy.getPosition().equals(waterenemy));
		assertTrue("ship must not changed fields",waterenemy.getShip().equals(shipenemy));
		assertTrue ("winner ship must not changed condition is "+shipme.getCondition()+" instead of "+mecondition,shipme.getCondition() == mecondition);
		assertTrue ("loser ship must loose condition",shipenemy.getCondition() == (enemycondition - 1));
		assertTrue("pc must changed to else case",shipme.getPC() == 13);
		assertTrue ("pause must be 0", shipme.getPause() == 0);
		
	}
	
	@Test
	public void testMoveShipEnemyLooseNotsurvive(){
		
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipme.changeMoral(4);
		shipenemy.changeMoral(-4);
		shipenemy.changeCondition(-2);
		
		moveit.execute(shipme);
		
		assertTrue("wrong x or y coordinate",((shipme.getPosition().getX()==waterenemy.getX())&&(shipme.getPosition().getY()==waterenemy.getY())));
		assertTrue ("move must be succesfull, ship must changed fieds",shipme.getPosition().equals(waterenemy));
		assertNull("move must be succesfull, ship must changed fieds",waterme.getShip());
		assertTrue("move must be succesfull, ship must changed fieds",waterenemy.getShip().equals(shipme));
		assertTrue ("winner ship must not changed condition",shipme.getCondition() == mecondition);
		assertTrue("pc not in else case",shipme.getPC() != 13);
		assertTrue ("pause must be 4", shipme.getPause() == 4);
		
	}
	
	@Test
	public void testMoveShipEnemyWinSurvive(){
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipme.changeMoral(-4);
		shipenemy.changeMoral(4);
		
		moveit.execute(shipme);
		
		assertTrue ("move not succesfull, fields must not changed",shipme.getPosition().equals(waterme));
		assertTrue("move not succesfull, fields must not changed",waterme.getShip().equals(shipme));
		assertTrue ("move not succesfull, fields must not changed",shipenemy.getPosition().equals(waterenemy));
		assertTrue("move not succesfull, fields must not changed",waterenemy.getShip().equals(shipenemy));
		assertTrue ("winner ship must not changed condition",shipenemy.getCondition() == enemycondition);
		assertTrue ("loser ship must loose condition",shipme.getCondition() == (mecondition - 1));
		assertTrue("pc must changed to else case",shipme.getPC() == 13);
		assertTrue ("pause must be 0", shipme.getPause() == 0);
	}
	@Test
	public void testMoveShipEnemyWinNotSurvive(){
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipme.changeMoral(-4);
		shipme.changeCondition(-2);
		shipenemy.changeMoral(4);
		
		moveit.execute(shipme);
		
		assertNull("ship looses, must be destroyed",waterme.getShip());
		assertTrue ("ship must not changed fields",shipenemy.getPosition().equals(waterenemy));
		assertTrue("ship must not changed fields",waterenemy.getShip().equals(shipenemy));
		assertTrue ("winnership must not changed condition",shipenemy.getCondition() == enemycondition);
		
	}
	
	@Test
	public void testMoveKraken(){
		
		moveit.execute(shipme);
		assertTrue("wrong x coordinate",((shipme.getPosition().getX()==waterkraken.getX())&&(shipme.getPosition().getY()==waterkraken.getY())));
		//assertTrue("move was succesfull",shipme.getPosition().equals(waterkraken));
		assertTrue ("move was succesfull",waterkraken.getShip().equals(shipme));
		assertTrue ("condition must changed", shipme.getCondition() == (mecondition-1));
		assertTrue("move was succesfull",shipme.getPC() != 13);
		assertTrue ("pause must be 4", shipme.getPause() == 4);
		
		
	}
	
	@Test
	public void testMoveBaseEnemy(){
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		
		moveit.execute(shipme);
		
		assertTrue("move not succesfull",shipme.getPosition().equals(waterme));
		assertTrue ("must not loose condition", shipme.getCondition() == mecondition);
		assertTrue("move not succesfull, else case", shipme.getPC() == 13);
		assertTrue ("pause must be 0", shipme.getPause() == 0);
	}
	
	@Test
	public void testMoveBaseFriend(){
		shipenemy.changeDirection(true);
		shipenemy.changeDirection(true);
		
		moveit.execute(shipenemy);
		
		assertTrue("move succesfull", shipenemy.getPosition().equals(baseenemy));
		assertTrue("move succesfull", baseenemy.getShip().equals(shipenemy));
		assertTrue (" must not loose condition",shipenemy.getCondition() == enemycondition);
		assertTrue("move was succesfull, not else case", shipme.getPC() != 13);
		assertTrue ("pause must be 4", shipenemy.getPause() == 4);
		
		
	}
	
	@Test
	public void testMoveIsland(){
		
		
		shipme.changePause(-20);//simuliert warten zwischen den zügen
		shipme.changeDirection(true);
		shipme.changeDirection(true);
		
		moveit.execute(shipme);
		shipme.changePause(-4);//simuliert warten zwischen den zügen
		
		shipme.changeDirection(true);
		
		
		moveit.execute(shipme);
		
		assertTrue("move not succesfull", shipme.getPosition().equals(water1));
		assertTrue("must loose condition",shipme.getCondition() == (mecondition-1));
		assertTrue ("must loose treasure", shipme.getLoad() <= meload);
		assertTrue("move not succesfull, else case",shipme.getPC() == 13);
		assertEquals (0, shipme.getPause());
		
	}
	
	@Test
	public void testMoveProvisionIsland(){
		shipme.changeDirection(true);
		
		moveit.execute(shipme);
		
		assertTrue("move not succesfull", shipme.getPosition().equals(waterme));
		assertTrue("must loose condition",shipme.getCondition() == (mecondition-1));
		assertTrue (shipme.getLoad() <= meload); //???
		assertTrue("move not succesfull, else case",shipme.getPC() == 13);
		assertTrue ("pause must be 0", shipme.getPause() == 0);
		
	}
	
	@Test
	public void testMoveShipFightTreasureNew(){
		shipme.setLoad(0);
		shipme.changePause(-20);
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipme.changeMoral(4);
		shipme.setLoad(3);
		shipenemy.changeMoral(-4);
		shipenemy.changeCondition(-2);
		shipenemy.setLoad(4);
		int FreeId = shipenemy.getPosition().getMap().giveNewEntityID();
		
		moveit.execute(shipme);
		
		assertTrue("wrong x or y coordinate",((shipme.getPosition().getX()==waterenemy.getX())&&(shipme.getPosition().getY()==waterenemy.getY())));
		assertTrue ("move must be succesfull, ship must changed fieds",shipme.getPosition().equals(waterenemy));
		assertNull("move must be succesfull, ship must changed fieds",waterme.getShip());
		assertTrue("move must be succesfull, ship must changed fieds",waterenemy.getShip().equals(shipme));
		assertTrue ("winner ship must not changed condition",shipme.getCondition() == mecondition);
		assertTrue("pc not in else case",shipme.getPC() != 13);
		assertEquals ("pause must be 6", 6, shipme.getPause());
		assertTrue ("new Treasure must be on enemywater", waterenemy.getTreasure() != null );
		assertEquals ("new treasure must have value 3",3,  waterenemy.getTreasure().getValue());
		assertTrue ("wrong id", (shipme.getPosition().getMap().giveNewEntityID()-2) == FreeId);
		assertEquals("", 4, shipme.getLoad());
		
		
	}
	
	@Test
	public void testMoveShipFightTreasureOld(){
		shipme.changePause(-8);
		waterenemy.exchangeTreasure(2);
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipme.changeMoral(4);
		shipenemy.changeMoral(-4);
		shipenemy.changeCondition(-2);
		shipenemy.setLoad(4);
		shipme.setLoad(2);
		int FreeId = shipenemy.getPosition().getMap().giveNewEntityID();
		
		moveit.execute(shipme);
		
		assertTrue("wrong x or y coordinate",((shipme.getPosition().getX()==waterenemy.getX())&&(shipme.getPosition().getY()==waterenemy.getY())));
		assertTrue ("move must be succesfull, ship must changed fieds",shipme.getPosition().equals(waterenemy));
		assertNull("move must be succesfull, ship must changed fieds",waterme.getShip());
		assertTrue("move must be succesfull, ship must changed fieds",waterenemy.getShip().equals(shipme));
		assertTrue ("winner ship must not changed condition",shipme.getCondition() == mecondition);
		assertTrue("pc not in else case",shipme.getPC() != 13);
		assertEquals ("pause must be 4", 6, shipme.getPause());
		assertTrue ("Treasure must be on enemywater", waterenemy.getTreasure() != null );
		assertEquals ("new treasure must have value 4",4,  waterenemy.getTreasure().getValue());
		assertTrue ("wrong id", (shipme.getPosition().getMap().giveNewEntityID()-1) == FreeId);
		assertEquals ("enemywater must have treasure 4",4, waterenemy.getTreasure().getValue());
		
		
	}
	
	@Test
	public void testMoveShipFightKraken(){
		shipme.changePause(-8);
		waterenemy.exchangeTreasure(2);
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipme.changeMoral(4);
		shipenemy.changeMoral(-4);
		shipenemy.changeCondition(-2);
		shipenemy.setLoad(4);
		shipme.setLoad(2);
		shipenemy.getPosition().setKraken(new Kraken(0, shipenemy.getPosition()));
		int FreeId = shipenemy.getPosition().getMap().giveNewEntityID();
		
		moveit.execute(shipme);
		
		assertTrue("wrong x or y coordinate",((shipme.getPosition().getX()==waterenemy.getX())&&(shipme.getPosition().getY()==waterenemy.getY())));
		assertTrue ("move must be succesfull, ship must changed fieds",shipme.getPosition().equals(waterenemy));
		assertNull("move must be succesfull, ship must changed fieds",waterme.getShip());
		assertTrue("move must be succesfull, ship must changed fieds",waterenemy.getShip().equals(shipme));
		assertTrue("pc not in else case",shipme.getPC() != 13);
		assertEquals ("pause must be 4", 6, shipme.getPause());
		assertTrue ("Treasure must be on enemywater", waterenemy.getTreasure() != null );
		assertEquals ("new treasure must have value 4",4,  waterenemy.getTreasure().getValue());
		assertTrue ("wrong id", (shipme.getPosition().getMap().giveNewEntityID()-1) == FreeId);
		assertEquals ("", 4, shipme.getLoad());
		assertEquals ("Ship most likely lost no condition because of kraken on field", 2, shipme.getCondition());
		assertEquals ("enemywater must have treasure 4",4, waterenemy.getTreasure().getValue());
		
		
	}
	
	
	
	
	
	@Test
	public void testMoveShipFightTreasureOldsurvive(){
		shipme.changePause(-8);
		waterenemy.exchangeTreasure(2);
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipme.changeMoral(4);
		shipenemy.changeMoral(-4);
		shipenemy.changeCondition(-1);
		shipenemy.setLoad(4);
		shipme.setLoad(2);
		int FreeId = shipenemy.getPosition().getMap().giveNewEntityID();
		
		moveit.execute(shipme);
		
		assertTrue("wrong x or y coordinate",((shipme.getPosition().getX()==waterme.getX()) &&(shipme.getPosition().getY()==waterme.getY())));
		assertTrue ("move must not be succesfull, ship must not changed fieds",shipme.getPosition().equals(waterme));
		assertTrue("move must not be succesfull, ship must not changed fieds",waterme.getShip().equals(shipme));
		assertTrue("move must not be succesfull, ship must not changed fieds",waterenemy.getShip().equals(shipenemy));
		assertTrue ("winner ship must not changed condition",shipme.getCondition() == mecondition);
		assertTrue("pc  in else case",shipme.getPC() == 13);
		assertEquals ("pause must be 0", 0, shipme.getPause());
		assertTrue ("Treasure must be on enemywater", waterenemy.getTreasure() != null );
		assertEquals ("new treasure must have value 4",4,  waterenemy.getTreasure().getValue());
		assertTrue ("wrong id", (shipme.getPosition().getMap().giveNewEntityID()-1) == FreeId);
		assertEquals ("", 4, shipme.getLoad());
		assertEquals ("enemywater must have treasure 4",4, waterenemy.getTreasure().getValue());
		assertEquals ("treasure in looser ship ust be 0", 0, shipenemy.getLoad());
		
		
	}
	
	@Test
	public void testMoveShipFightTreasureOldsurvive2(){
		shipme.changePause(-8);
		waterme.exchangeTreasure(2);
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipenemy.changeMoral(4);
		
		shipme.changeMoral(-4);
		shipme.changeCondition(-1);
		shipme.setLoad(4);
		shipenemy.setLoad(2);
		int FreeId = shipme.getPosition().getMap().giveNewEntityID();
		
		moveit.execute(shipme);
		
		assertTrue("wrong x or y coordinate",((shipme.getPosition().getX()==waterme.getX()) &&(shipme.getPosition().getY()==waterme.getY())));
		assertTrue ("move must not be succesfull, ship must not changed fieds",shipme.getPosition().equals(waterme));
		assertTrue("move must not be succesfull, ship must not changed fieds",waterme.getShip().equals(shipme));
		assertTrue("move must not be succesfull, ship must not changed fieds",waterenemy.getShip().equals(shipenemy));
		assertEquals("winner ship must not changed condition",3,shipenemy.getCondition());
		assertEquals ("looser ship must  changed condition",1,shipme.getCondition());
		assertTrue("pc  in else case",shipme.getPC() == 13);
		assertEquals ("pause must be 0", 0, shipme.getPause());
		assertTrue ("Treasure must be on mewater", waterme.getTreasure() != null );
		assertEquals ("new treasure must have value 4",4,  waterme.getTreasure().getValue());
		assertTrue ("wrong id", (shipme.getPosition().getMap().giveNewEntityID()-1) == FreeId);
		assertEquals ("", 4, shipenemy.getLoad());
		assertEquals ("mewater must have treasure 4",4, waterme.getTreasure().getValue());
		assertEquals ("treasure in looser ship must be 0", 0, shipme.getLoad());
		
		
	}
}
