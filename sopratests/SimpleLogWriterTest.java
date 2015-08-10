package de.unisaarland.cs.st.pirates.group5.tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.view.Log;
import de.unisaarland.cs.st.pirates.group5.view.SimpleLogWriter;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Cell;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Transaction;

public class SimpleLogWriterTest {

	
	@Test
	public void LogWriterTest() throws ArrayIndexOutOfBoundsException, NullPointerException, IOException{
		File file = new File("log.log");
		FileOutputStream stream = new FileOutputStream(file);
		Log log = new Log();
		log.addLogger(new SimpleLogWriter());
		//log.addLogger(new GUIController());
		
		String map = "4\n3\n# # # \n. . . \n# # #";
		
		String a = "sense 0";
		String b = "sense 0";
		String[] tactics = {a, b};
		
		log.init(stream, map, tactics);
		
		log.addCell(Cell.ISLAND, null, 5, 1);
		log.addCell(Cell.WATER, 2, 2, 2);
		Key[] keys = {Key.CONDITION,  Key.MORAL, Key.DIRECTION};
		int[] values = {3, 4, 0};
		log.create(Entity.SHIP, 152, keys , values);
		Key[] keys2 = {Key.X_COORD, Key.Y_COORD, Key.VALUE};
		int[] values2 = {2,1, 9};
		log.create(Entity.TREASURE, 34, keys2, values2);
		log.logStep();
		log.notify(Entity.SHIP, 152, Key.MORAL, 3);
		log.create(Entity.BUOY, 1544, keys2, values2);
		log.logStep();
		log.destroy(Entity.SHIP, 152);
		log.fleetScore(0, 200);

		
		Transaction t = log.beginTransaction(Entity.BUOY, 32);
		t.set(Key.FLEET, 1);
		t.set(Key.X_COORD, 1);
		t.set(Key.Y_COORD, 1);
		log.commitTransaction(t);
		
		log.close();
	}
}
