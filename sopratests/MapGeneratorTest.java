package de.unisaarland.cs.st.pirates.group5.tests;
import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.controller.MapGenerator;
import de.unisaarland.cs.st.pirates.group5.model.Base;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Island;
import de.unisaarland.cs.st.pirates.group5.model.Map;
import de.unisaarland.cs.st.pirates.group5.model.ProvisionIsland;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
import de.unisaarland.cs.st.pirates.group5.model.Team;
import de.unisaarland.cs.st.pirates.group5.model.Water;
public class MapGeneratorTest {
	
	
	
	private InputStream stream;
	private MapGenerator mapgen;
	private Map map;
	private Field field;
	private Field field2;
	private Field field3;
	private Field field4;
	private Ship ship;
	private Team team;
	ArrayList<Team> teamlist = new ArrayList<Team>();
	char a = 0;
	Random random = new Random();
	
	@Before
	public void setUp() throws FileNotFoundException {
		
		stream = getClass().getResourceAsStream("src/test/ressources/map2x2.txt");
		if(stream == null)
			stream = new FileInputStream("src/test/ressources/map2x2.txt");
		mapgen = new MapGenerator();
		team = new Team(a, null);
		teamlist.add(team);
		Map testmap = new Map(random, null);
		field = new Island(testmap, 0, 0, null);
		field2 = new Water(testmap, 1, 0, null);
		ship = new Ship(team, null, 0, null);
		field3 = new Base(testmap, 0, 1, null, ship);
		ship.setField(field3);
		field4 = new ProvisionIsland(testmap, 1, 1);
		
	}

	@Test
	public void mapGenTest() throws IOException, URISyntaxException {
		map = mapgen.createMap(stream, teamlist, null, random, null, null, null);
		assertTrue (map.giveNewEntityID() == 0);
		assertTrue (map.giveNewActorID() == 1);
		assertTrue (map.getKraken().size() == 0);
		assertFalse (map.getFirstShip() == null);
		
		
		assertTrue (map.getNeighbour(field, 0).getFieldType().equals(field2.getFieldType()));
		assertTrue (map.getNeighbour(field, 1).getFieldType().equals(field3.getFieldType()));
		assertTrue (map.getNeighbour(field3, 0).getFieldType().equals(field4.getFieldType()));
		
		assertTrue (map.getNeighbour(field, 0).getBuoys().equals(field2.getBuoys()));
		assertTrue (map.getNeighbour(field, 1).getBuoys().equals(field3.getBuoys()));
		assertTrue (map.getNeighbour(field3, 0).getBuoys().equals(field4.getBuoys()));
		
		
		
		assertTrue (map.getNeighbour(field, 0).getX() == (field2.getX()));
		assertTrue (map.getNeighbour(field, 1).getX() == (field3.getX()));
		assertTrue (map.getNeighbour(field3, 0).getX() == (field4.getX()));
		
		assertTrue (map.getNeighbour(field, 0).getY() == (field2.getY()));
		assertTrue (map.getNeighbour(field, 1).getY() == (field3.getY()));
		assertTrue (map.getNeighbour(field3, 0).getY() == (field4.getY()));
	}
	
}
