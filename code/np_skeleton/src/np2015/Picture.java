/**
 * 
 */
package np2015;

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
	private ArrayList<Column> columnList;
	private int barriercount;
	public final double epsilon;
	public GraphInfo graph;
	private CyclicBarrier barrier1;
    private CyclicBarrier barrier2;
    private CyclicBarrier barrier3;
    public boolean preciseTest;
	
	
	
	/*
	 * @param x: x-Koordinate des gesetzen initialen Osmose-Nodes
	 * @param y: y-Koordinate des gesetzt initialen Osmose-Nodes
	 * @param value: value des initial gesetzen Osmods-Nodes
	 * 
	 * Im Konstruktor erstellen wir bereits alle benoetigten Spalten und Nodes die fuer das 'momentane' Bild relevant sind.
	 * 
	 */
	public Picture(GraphInfo graph, int width, int height, int x, int y, double value, double epsilon, int barriercount){
		this.graph = graph;
		this.width = width;
		this.height = height;
		this.barriercount = barriercount;
		this.epsilon = epsilon;
		
		this.preciseTest = false;
		
		createBarrier1();
		createBarrier2();
		createBarrier3();
		
		this.columnList = new ArrayList<Column>(width);
		
		// erstellen der Spalten
		for (int i = 0; i < width; i++){
			columnList.add(new Column(height, this, barrier1, barrier2, barrier3));
			//columnList.set(i, new Column(height, this, barrier1, barrier2, barrier3));
		}
		
		// setzen der Nachbarn
		columnList.get(0).setNeighbour(null, columnList.get(1));
		columnList.get(width-1).setNeighbour(columnList.get(width-2), null);
		for (int i = 1; i < width-1; i++){
			columnList.get(i).setNeighbour(columnList.get(i-1), columnList.get(i+1));
		}
		
		//setzen des initialen Nodes
		columnList.get(x).createNewNode(x, y, value);
					
	}
	
	/*
	 * diese Methode wird aufgerufen um alle unsere Threads zu erstellen, zu starten und anschliessend auf ihre terminierung zu warten
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
	
	private void createBarrier1(){
		barrier1 = new CyclicBarrier(width, new Runnable(){

			@Override
			public void run() {
				System.out.println("barrier1 reached");
				if (!preciseTest){
					for (int i = 0; i < width; i++){
						if (!columnList.get(i).checkLocalTerminate()){
							return;   	// we can return as soon as we find ONE column which does not fullfill our conditions
										// for local termination
						}
							
					}
					setPreciseTest();	// we only get here when every column fullfills our conditon, so we can start
										// our precise test
				}
				return;				
			}});
	}
	
	private void createBarrier2(){
		barrier2 = new CyclicBarrier(width, new Runnable(){

			@Override
			public void run() {
				System.out.println("barrier2 reached");
				if (!preciseTest){
					return;
				}
				
				for (int i = 0; i < width; i++){
					if (!columnList.get(i).checkPreciseTest()){
						return;   	// we can return as soon as we find ONE column which does not fullfill our conditions
									// for precise termination
					}
						
				}
				terminateThreats();	// we only get here when every column fullfills our conditon, so we can terminate all threats			
			}		
		});

	}

	private void createBarrier3(){
		
		barrier3 = new CyclicBarrier(width, new Runnable(){

			@Override
			public void run() {
				System.out.println("barrier3!");
				return; 	//ich synchronisiere nur!
				
			}
			
		});
	}
	
	
	/*
	 * wir iterieren ueber columnList und halten jeden Threat an! Wichtig ist dass wir daran denken in jedem threat den interrupted flag zu beachten
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
	
	/*
	 * returns the value of the node at position (x,y)
	 */
	public double getValueAt(int row, int column){
		return columnList.get(column).getValueAtX(row);
	}
	
	/*
	 * is setting all preciseTest booleans=true (probably for all columns and barriers)
	 */
	public void setPreciseTest(){
		preciseTest = true;
		for (int i = 0; i < width; i++)
			columnList.get(i).setPreciseTest(true);
	}
}
