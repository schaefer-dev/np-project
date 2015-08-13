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

public class TestPicture {

	@Test
	public void PictureConstruction1() {
		
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
		
		Picture pic = new Picture(ginfo,width, height,1,1,value,epsilon,barriercount,0);
		
		assertTrue(value == pic.getValueAt(1, 1));
		assertTrue(0.0 == pic.getValueAt(0, 1));
		assertTrue(0.0 == pic.getValueAt(1, 0));
		assertTrue(0.0 == pic.getValueAt(1, 2));
		assertTrue(0.0 == pic.getValueAt(2, 1));
		
		for (int y = 2; y < 6; y++)
			for (int x = 2; x < 6; x++)
				assertTrue(0.0 == pic.getValueAt(y, x));
		
	}


}
