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
	
	public Node(Column myColumn, int x, int y, double value,double rateTop, double rateBottom, double rateLeft, double rateRight){
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
	 * berechnet die Werte aller seiner akkumulatoren mit Hilfe von value und flowrates
	 */
	public void calculate(){
		akkuTop = value * rateTop;
		akkuBottom = value * rateBottom;
		double akkuLeftChange = value * rateLeft;
		akkuLeft = akkuLeft + akkuLeftChange;
		double akkuRightChange = value * rateRight;
		akkuRight = akkuRight + akkuRightChange ;
		value = value - (akkuTop + akkuBottom + akkuRightChange + akkuLeftChange );
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
