package np2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;

public class NPOsmose {

	public static void main(String[] args) throws IOException, InterruptedException {
		Gson gson = new Gson();
		String json = "";
		// read data in
		if (args.length != 0) {
			Path path = Paths.get(args[0]);
			try {
				json = new String(Files.readAllBytes(path)); 
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("You must provide the serialized file as the first argument!");
		}
		GraphInfo ginfo = gson.fromJson(json, GraphInfo.class);
		
		// Your implementation can now access ginfo to read out all important values
		
		int width = ginfo.width;
		int height = ginfo.height;
		int barriercount = 1000;
		double epsilon = ginfo.epsilon;
		
		//TODO wie lesen wir den initial node korrekt aus!
		int x = 0;
		int y = 0;
		int value = 1;
		
		
		ImageConvertible graph = new Picture(ginfo,width,height,x,y,value,epsilon,barriercount); // <--- you should implement ImageConvertible to write the graph out
		graph.runAllColumns();
		
		ginfo.write2File("./result.txt", graph);
	}

}
