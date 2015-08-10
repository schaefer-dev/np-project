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
	private final ArrayList<Column> columnList;
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
		
		this.columnList = null;
		this.preciseTest = false;
		
		createBarrier1();
		createBarrier2();
		createBarrier3();
		
		//TODO columnList mit columns und nodes erstellen: this.columnList = new ArrayList<Column>();
		//TODO am ende der NodeListenerstellung unbedingt daran denken den initialen node mit (x,y)=value hier zu setzen
				
	}
	
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
