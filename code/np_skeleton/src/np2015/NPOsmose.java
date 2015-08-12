package np2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

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
		int barriercount = 100000;
		double epsilon = ginfo.epsilon;
		
		System.out.println(ginfo.column2row2initialValue.toString());
		
		int x = ginfo.column2row2initialValue.keySet().iterator().next();
		int y = ginfo.column2row2initialValue.values().iterator().next().keySet().iterator().next();
		double value = ginfo.column2row2initialValue.values().iterator().next().values().iterator().next();
		
		System.out.println("x " + x+ " -- y "+y + " -- value: "+value);
		
		
		ImageConvertible graph = new Picture(ginfo,width,height,x,y,value,epsilon,barriercount); // <--- you should implement ImageConvertible to write the graph out
		
		/////////////////////////OBSERVER/////////////////////
		
		Thread obs = new Thread(new Runnable() { public void run() { 
				int counter = 0;
				while(true)	{
					try {
						System.in.read();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ginfo.write2File("./testresult"+counter+".txt", graph);
					System.out.println("new testresult"+counter+".txt printed!");
					counter++;
				}
				
			}});
		
		obs.start();
		
		
		
		
		/////////////////////OBSERVEREND///////////////////////
		

		graph.runAllColumns();
		
		ginfo.write2File("./result.txt", graph);
	}

}
