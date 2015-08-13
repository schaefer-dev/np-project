package np2015.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import np2015.GraphInfo;
import np2015.Node;
import np2015.Picture;
import np2015.GuardedCommand;

import org.junit.Test;

import com.google.gson.Gson;

public class NodeTests {

	@Test
	public void nodeRates() {
		/*
		
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
		
		double flowRateTop = (node.getAkkuTop());
		double flowRateBottom = (node.getAkkuBottom());
		double flowRateLeft = (node.getAkkuLeft());
		double flowRateRight = (node.getAkkuRight());
		
		assertTrue(node.getValue() < 1);
		*/
	}

}
