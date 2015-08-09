/**
 * 
 */
package np2015;

import java.util.ArrayList;

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
		//TODO columnList mit columns und nodes erstellen: this.columnList = new ArrayList<Column>();
		//TODO am ende der NodeListenerstellung unbedingt daran denken den initialen node mit (x,y)=value hier zu setzen
				
	}
	
	/*
	 * wir iterieren ueber columnList und halten jeden Threat an! Wichtig ist dass wir daran denken in jedem threat den interrupted flag zu beachten
	 */
	public void terminateThreats(){
		//TODO
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
		//TODO
		return 0;
	}
	
	/*
	 * is setting all preciseTest booleans=true (probably for all columns and barriers)
	 */
	public void startPreciseTest(){
		//TODO
	}
}
