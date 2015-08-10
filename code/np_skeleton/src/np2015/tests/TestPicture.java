package np2015.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import np2015.GraphInfo;
import np2015.Picture;

import org.junit.Test;

import com.google.gson.Gson;

public class TestPicture {

	@Test
	public void setup() {
		
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
		
		Picture pic = new Picture(ginfo,2,2,0,0,1,0.1,1);
	}

}
