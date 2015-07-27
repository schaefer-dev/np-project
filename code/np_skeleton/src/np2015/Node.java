package np2015;

public class Node {
	
	private final int x;
	private final int y;
	private double value;
	private double value_old;
	private final double rateTop;
	private final double rateBottom;
	private final double rateLeft;
	private final double rateRight;
	private double akkuTop;
	private double akkuBottom;
	private double akkuLeft;
	private double akkuRight;
	private Column myColumn;
	
	public Node(Column myColumn, int x, int y, double value,int rateTop, int rateBottom, int rateLeft, int rateRight){
		this.x = x;
		this.y = y;
		this.value = value;
		this.value_old = 0;
		
		this.rateBottom = rateBottom;
		this.rateLeft = rateLeft;
		this.rateRight = rateRight;
		this.rateTop = rateTop;
	}
	
	/*
	 * Communicate schiebt die Akkumulatoren aus this auf die Knoden right und left. Dazu wird in left bzw right die Methode changeValue aufgerufen.
	 * Am ende Nullen wir die uebertragenen Akkumulatoren.
	 * 
	 * wichtiger Sonderfall wenn wir hier als nachbarnode right einen noch nicht existierenden node erhalten koennen wir an dieser stelle direkt einen neuen
	 * Node erstellen und dann right=newNode ... setzen mit enstsprechenden Koordinaten und value. Wir muessen uns vorher noch die rates des Knotens besorgen!
	 */
	public void communicate(Node left, Node right){
		//TODO
	}
	
	
	/*
	 * siehe communicate() aber in der Vertikalen
	 */
	public void flow(Node top, Node bottom){
		//TODO
	}
	
	
	/*
	 * berechnet die Werte aller seiner akkumulatoren mit Hilfe von value und flowrates
	 */
	public void calculate(){
		//TODO
	}
	
	/*
	 * synchronisierter 'Setter' fuer value. 
	 */
	public synchronized void changeValue(double Akku){
		value += Akku;
	}

	/* 
	 * returnt den Unterschied zwischen value und value_old
	 */
	public double checkKonvergenz(){
		return Math.abs(value_old - value);
	}

}
