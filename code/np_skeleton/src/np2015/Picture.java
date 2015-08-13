/**
 * 
 */
package np2015;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

/**
 * @author daniel
 *
 */
public class Picture implements ImageConvertible{
	// height and width are already public inside GraphInfo
	public final int height; 
	public final int width;
	public ArrayList<Column> columnList;
	private int barriercount;
	public final double epsilon;
	public GraphInfo graph;
	private CyclicBarrier barrier1;
	private CyclicBarrier barrier15;
    private CyclicBarrier barrier2;
    private CyclicBarrier barrier3;
    private Picture pic = this;    // DEBUG for observer
    public boolean preciseTest;
    public int testresultcounter;
	
	
	/**
	 * @param graph the graph in which this matrix in contained
	 * @param width the width of the picture
	 * @param height the height of the picture
	 * @param x the x coordinate of the initial node
	 * @param y the y coordinate of the initial node
	 * @param value the value of the initial node
	 * @param epsilon the epsilon
	 * @param barriercount the barriercount (potentially chosen by user)
	 * @param testresultcounter the testresultcounter (potentially chosen by user)
	 */
	public Picture(GraphInfo graph, int width, int height, int x, int y, double value, double epsilon, int barriercount, int testresultcounter){
		this.graph = graph;
		this.width = width;
		this.height = height;
		this.barriercount = barriercount;
		this.testresultcounter = testresultcounter;
		this.epsilon = epsilon;
		
		this.preciseTest = false;
		
		createBarrier1();
		createBarrier15();
		createBarrier2();
		createBarrier3();
		
		this.columnList = new ArrayList<Column>(width);
		
		// erstellen der Spalten
		for (int i = 0; i < width; i++){
			columnList.add(new Column(height, this, barrier1, barrier2, barrier3, barrier15));
			//columnList.set(i, new Column(height, this, barrier1, barrier2, barrier3));
		}
		
		// setzen der Nachbarn
		columnList.get(0).setNeighbour(null, columnList.get(1));
		columnList.get(width-1).setNeighbour(columnList.get(width-2), null);
		for (int i = 1; i < width-1; i++){
			columnList.get(i).setNeighbour(columnList.get(i-1), columnList.get(i+1));
		}
		
		//setzen des initialen Nodes
		columnList.get(x).createInitialNode(x, y, value);
					
	}
	
	
	/**
	 * this method starts every column as a Thread and waits for them to finish their work
	 * to terminate.
	 */
	public void runAllColumns(){
		ArrayList<Thread> threadList = new ArrayList<Thread>(width);
		
		for (int i = 0; i < width; i++)
			threadList.add(null);
		
		// erstellen unsere Threads
		for (int i = 0; i < width; i++)
			threadList.set(i, new Thread(columnList.get(i)));
		
		// starten alle Threads
		for (int i = 0; i < width; i++)
			threadList.get(i).start();
		
		System.out.println("all threads started!");
		
		// wartet hier bis alle Threads terminiert sind!
		for (int i = 0; i < width; i++){
			try {
				threadList.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new IllegalArgumentException("joining our Thread failed!");
			}
		}
	}
	
	/**
	 * creates Barrier1 which checks for local convergence if precisetest was 
	 * not started yet. If barrier1 sees local convergence in every column
	 * we set the precisetset var in every column by calling setPreciseTest()
	 */
	private void createBarrier1(){
		barrier1 = new CyclicBarrier(width, new Runnable(){

			@Override
			public void run() {
				if (!preciseTest){
					for (int i = 0; i < width; i++){
						if (!columnList.get(i).checkLocalTerminate()){
							return;   	// we can return as soon as we find ONE column which does not fullfill our conditions
										// for local termination
						}
							
					}
					System.out.println("preciseTestStart!!!");
					setPreciseTest();	// we only get here when every column fullfills our conditon, so we can start
										// our precise test
				}
				return;				
			}});
	}
	
	/**
	 * Creates Barrier15 which just serves for synchronisation (see run method in column)
	 */
	private void createBarrier15(){
		barrier15 = new CyclicBarrier(width, new Runnable(){

			@Override
			public void run() {
						
			}});
	}
	
	/**
	 * Creates Barrier2 which produces testresults depending on testresultcounter set by the user
	 * Barrier2 also does the precisetest using the second euklid. norm on the whole matrix and compares
	 * to epsilon. If barrier2 notices global convergence barrier2 calls terminateThreats() which tells
	 * every thread to terminate. 
	 */
	private void createBarrier2(){
		barrier2 = new CyclicBarrier(width, new Runnable(){
			int counter = 0; //OBSERVER
			int namecounter = 0; //OBSERVER
			
			@Override
			public void run() {
				
				if (testresultcounter != 0){
					if ((counter % testresultcounter) == 0){
						graph.write2File("./testresult"+namecounter+".txt", pic);
						System.out.println("new testresult"+namecounter+".txt printed!");
						counter++;
						namecounter++;
					}
					counter++;
				}
				
				
				//System.out.println("barrier2 reached");
				if (!preciseTest){
					return;
				}
				
				double epsilon2  = epsilon * epsilon;
				double diffsums = 0.0;
				
				for (int i = 0; i < width; i++){
					diffsums += columnList.get(i).checkPreciseTest();
					if (diffsums > epsilon2){
						return;   	// we can return as soon as we find ONE column which does NOT fullfill our conditions
									// for precise termination
					}
						
				}
				terminateThreats();	// we only get here when we fullfill the entire convergence-conditon, so we can terminate all threats			
			}		
		});

	}

	/**
	 * Creates Barrier3 which just serves for synchronisation (see run method in column)
	 */
	private void createBarrier3(){
		
		barrier3 = new CyclicBarrier(width, new Runnable(){

			@Override
			public void run() {
				//System.out.println("barrier3!");
				return; 	//ich synchronisiere nur!
				
			}
			
		});
	}
	
	

	/**
	 * we iterate over every Column in the picture and call terminate() to set the terminate var in the column true
	 * The column will terminate fairly soon
	 */
	public void terminateThreats(){
		for (int i = 0; i < width; i++)
			columnList.get(i).terminate();
	}
	
	public synchronized void setBarrierCount(int newBarrierCount){
		this.barriercount = newBarrierCount;
	}
	
	public synchronized int getBarrierCount(){
		return this.barriercount;
	}
	

	/**
	 * @param column the column of the desired value
	 * @param row the row of the desired value
	 * @return the value of the node at position (column,row)
	 */
	public double getValueAt(int column, int row){
		return columnList.get(column).getValueAtY(row);
	}
	
	/*
	 * is setting preciseTest=true in every column contained in this picture
	 */
	public void setPreciseTest(){
		preciseTest = true;
		for (int i = 0; i < width; i++)
			columnList.get(i).setPreciseTest(true);
	}
}
