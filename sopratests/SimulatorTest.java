package de.unisaarland.cs.st.pirates.group5.tests;


import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.controller.Simulator;

public class SimulatorTest {
	String[] ships = {"src/test/ressources/TestShip.ship"};
	String map;
	String logfile;
	int rounds;
	@Before
	public void setUp(){
		map = "src/test/ressources/map.txt";
		logfile = "src/test/ressources/log.log";
		rounds = 2000;
	}

	
	/*
	@Test (expected = IllegalArgumentException.class)
	public void test() throws URISyntaxException, ArrayIndexOutOfBoundsException, NullPointerException, IOException {
		String[] ships2 = {"/TestShip.ship","/TestShip2.ship","/TestShip3.ship"};
		Simulator sim = new Simulator(ships2,map, 5,logfile, rounds);
	}
	*/
	
	@Test
	public void test2() throws ArrayIndexOutOfBoundsException, NullPointerException, IOException, URISyntaxException{
		Simulator sim = new Simulator(ships,map, 5,logfile, rounds);
		sim.step(rounds);
		//sim.end();
	}
}
