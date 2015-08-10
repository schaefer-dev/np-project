package np2015.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import np2015.GraphInfo;
import np2015.Node;
import np2015.Picture;

import org.junit.Test;

import com.google.gson.Gson;

public class TestFlow {

	@Test
	public void flowOnceTest() {
		
		Gson gson = new Gson();
		String json = "";
		
		// read data in
		Path path = Paths.get("testfiles/eins.json");
		try {
			json = new String(Files.readAllBytes(path)); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GraphInfo ginfo = gson.fromJson(json, GraphInfo.class);	
		
		int width = ginfo.width;
		int height = ginfo.height;
		int barriercount = 10000000;
		double value = 1.0;
		double epsilon = ginfo.epsilon;
		
		Picture pic = new Picture(ginfo,width, height,1,1,value,epsilon,barriercount);
		
		Node node = pic.columnList.get(1).nodeList.get(1);
			
		node.calculate();
		
		double flowRateTop = (node.getAkkuTop());  // 0.0
		double flowRateBottom = (node.getAkkuBottom()); //0.039735
		double flowRateLeft = (node.getAkkuLeft()); // 0.0
		double flowRateRight = (node.getAkkuRight()); // 1.192E-6
		
		pic.columnList.get(1).flow(node);
		
		assertTrue(pic.getValueAt(2, 1) == 0.039735); // bottom Node ok
		assertTrue(pic.getValueAt(0, 1) == 0.0); // Top Node ok
		assertTrue(pic.getValueAt(1, 0) == 0.0); // Left Node ok
		assertTrue(pic.getValueAt(1, 2) == 0.0); // Right Node ok
		assertTrue(pic.getValueAt(1, 1) == 1.0 - 0.039735 - 0.000001192); // node ok
		
		assertTrue(pic.columnList.get(1).nodeList.get(1).getAkkuBottom() == 0);
		assertTrue(pic.columnList.get(1).nodeList.get(1).getAkkuTop() == 0);
		assertTrue(pic.columnList.get(1).nodeList.get(1).getAkkuLeft() == 0);
		assertTrue(pic.columnList.get(1).nodeList.get(1).getAkkuRight() == 0.000001192);
	}
	
	@Test
	public void flowTwiceTest() {
		
		Gson gson = new Gson();
		String json = "";
		
		// read data in
		Path path = Paths.get("testfiles/eins.json");
		try {
			json = new String(Files.readAllBytes(path)); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GraphInfo ginfo = gson.fromJson(json, GraphInfo.class);	
		
		int width = ginfo.width;
		int height = ginfo.height;
		int barriercount = 10000000;
		double value = 1.0;
		double epsilon = ginfo.epsilon;
		
		Picture pic = new Picture(ginfo,width, height,1,1,value,epsilon,barriercount);
		
		Node node = pic.columnList.get(1).nodeList.get(1);
			
		node.calculate();
		
		double flowRateTop = (node.getAkkuTop());  // 0.0
		double flowRateBottom = (node.getAkkuBottom()); //0.039735
		double flowRateLeft = (node.getAkkuLeft()); // 0.0
		double flowRateRight = (node.getAkkuRight()); // 1.192E-6
		
		pic.columnList.get(1).flow(node);
		
		assertTrue(pic.getValueAt(2, 1) == 0.039735); // bottom Node ok
		assertTrue(pic.getValueAt(0, 1) == 0.0); // Top Node ok
		assertTrue(pic.getValueAt(1, 0) == 0.0); // Left Node ok
		assertTrue(pic.getValueAt(1, 2) == 0.0); // Right Node ok
		
		node.calculate();
		
		pic.columnList.get(1).flow(node);
		
		
		
		assertTrue(pic.getValueAt(2, 1) == 0.039735 + (1.0-0.039735-0.000001192)*0.039735); // bottom Node ok
	}

}
