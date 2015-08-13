package np2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

public class NPOsmose {

	/**
	 * @param args 
	 * first Argument: Path of the json File containing Picture details, 
	 * second Argument: (optional) value for barriercount override
	 * third Argument: (optional) value for testResultCounter (0 = disable testresults)
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
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
		
		// zusaetzlicher input fuer 1. int:barriercount 2. zwischenergebnisse alle x erreichen von Barriercount(0 = keine zwischenergebnisse)
		int barriercount = 100000;
		
		int testresultcounter = 0;
		
		if (args.length >= 2){
			barriercount = Integer.parseInt(args[1]);
			System.out.println("Barriercount was set to: "+barriercount);
		}else{
			System.out.println("you could set the number of iterations before reaching the barrier in your second argument! Default: "+barriercount);
		}
		
		
		if (args.length >= 3){
			testresultcounter = Integer.parseInt(args[2]);
			System.out.println("testresults every (" + testresultcounter+ " x barriercount) is enabled");
		}else{
			System.out.println("you could set the number of (iterations x barriercount) to print testresults in your third argument! Default: "+testresultcounter);
		}
		
		
		int width = ginfo.width;
		int height = ginfo.height;
		
		double epsilon = ginfo.epsilon;
		
		int x = ginfo.column2row2initialValue.keySet().iterator().next();
		int y = ginfo.column2row2initialValue.values().iterator().next().keySet().iterator().next();
		double value = ginfo.column2row2initialValue.values().iterator().next().values().iterator().next();
		
		System.out.println("Initial Node: x " + x+ " -- y "+y + " -- value: "+value);
		
		
		ImageConvertible graph = new Picture(ginfo,width,height,x,y,value,epsilon,barriercount,testresultcounter); // <--- you should implement ImageConvertible to write the graph out
		

		graph.runAllColumns();
		
		
		ginfo.write2File("./result.txt", graph);
	}

}
