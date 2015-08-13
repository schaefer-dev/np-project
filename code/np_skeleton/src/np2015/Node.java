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
	
	/**
	 * @param x x-coordinate of this node
	 * @param y y-coordinate of this node
	 * @param value (initial) value of this node
	 * @param rateTop topRate of this node
	 * @param rateBottom bottomrate of this node
	 * @param rateLeft leftrate of this node
	 * @param rateRight rightrate of this node
	 */
	public Node(int x, int y, double value,double rateTop, double rateBottom, double rateLeft, double rateRight){
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
	 * calculates the akkus depending on value/flowrates of this node
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
	 * synchronized change for the value of this nodes (no change is lost!)
	 */
	public synchronized void changeValue(double Akku){
		value += Akku;
	}


	public double getAkkuTop() {
		return akkuTop;
	}

	public double getAkkuBottom() {
		return akkuBottom;
	}

	public double getAkkuLeft() {
		return akkuLeft;
	}


	public double getAkkuRight() {
		return akkuRight;
	}


	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}

	
	/**
	 * reset akku top and akku bottom to 0
	 */
	public void setFlowAkkuZero(){
		akkuTop = 0;
		akkuBottom = 0;
	}
	
	/**
	 * reset akku left and akku right to 0
	 */
	public void setCommunicateAkkuZero(){
		akkuLeft = 0;
		akkuRight = 0;
	}


	public double getValue() {
		return value;
	}


	public void setValue(double value) {
		this.value = value;
	}


	public double getValue_old() {
		return value_old;
	}


	public void setValue_old(double value_old) {
		this.value_old = value_old;
	}
}
