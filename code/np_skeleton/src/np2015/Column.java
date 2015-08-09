package np2015;

import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;
import np2015.Node;

public class Column implements Runnable{
	private Column leftColumn;
	private Column rightColumn;
	private ArrayList<Node> nodeList;   // wir starten mit y = 0 'unten' in der matrix
	private final int size;
	private final Picture matrix;
	private int barrierCount;
	private boolean preciseTest;
	private CyclicBarrier barrier1;
    private CyclicBarrier barrier2;
    private CyclicBarrier barrier3;

	public Column(int size, Picture matrix){
		this.size = size;
		this.matrix = matrix;
		this.nodeList = new ArrayList<Node>();
		this.preciseTest = false;
		//TODO nodeList hier initialisieren mit der entsprechenden maimalen laenge
	}
	
	public void setNeighbour(Column left, Column right){
		this.leftColumn = left;
		this.rightColumn = right;
	}
	
	/*
	 * Communicate schiebt die Akkumulatoren aus this auf die Knoden right und left. Dazu wird in left bzw right die Methode changeValue aufgerufen.
	 * Am ende Nullen wir die uebertragenen Akkumulatoren.
	 * 
	 * wichtiger Sonderfall wenn wir hier als nachbarnode right einen noch nicht existierenden node erhalten koennen wir an dieser stelle direkt einen neuen
	 * Node erstellen und dann right=newNode ... setzen mit enstsprechenden Koordinaten und value. Wir muessen uns vorher noch die rates des Knotens besorgen!
	 */
	public void communicate(Node node){
		if (node == null)
			throw new IllegalArgumentException("flow node shoult not be null!");
		double akkuLeft = node.getAkkuLeft();
		double akkuRight = node.getAkkuRight();
		
		int x = node.getX();
		int y = node.getY();
		
		if (akkuLeft > 0.0){           // Not neccesary if flowrates at corners are 0! Better safe than sorry
			if (x != 0){
				Node leftNode = leftColumn.nodeList.get(y);
				if (leftNode == null)
					leftColumn.createNewNode(x-1,y,akkuLeft);
				else{
					leftNode.changeValue(akkuLeft);
				}
			}	
		}
		
		if (akkuRight > 0.0){
			if (x != matrix.width-1){ ///TODO
				Node rightNode = rightColumn.nodeList.get(y);
				if (rightNode == null)
					rightColumn.createNewNode(x+1,y,akkuRight);
				else{
					rightNode.changeValue(akkuRight);
				}
			}	
		}
		node.setCommunicateAkkuZero();
	}
	
	
	/*
	 * siehe communicate() aber in der Vertikalen
	 */
	public void flow(Node node){
		if (node == null)
			throw new IllegalArgumentException("flow node shoult not be null!");
		double akkuTop = node.getAkkuTop();
		double akkuBottom = node.getAkkuBottom();
		
		int y = node.getY();
		int x = node.getX();
		
		if (akkuTop > 0.0){
			if (y != matrix.height-1){
				Node topNode = nodeList.get(y+1);
				if (topNode == null)
					createNewNode(x,y+1,akkuTop);
				else{
					topNode.changeValue(akkuTop);
				}
			}		
		}
		if (akkuBottom > 0.0){
			if (y != 0){
				Node bottomNode = nodeList.get(y-1);
				if (bottomNode == null)
					createNewNode(x,y-1,akkuBottom);
				else{
					bottomNode.changeValue(akkuBottom);
				}
			}
		}		
		node.setFlowAkkuZero();	
	}
	
	/*
	 * wir erstellen einen neuen Node in this, da von beiden benachbarten spalten ein aufruf dieser methode potentiell gleichzeitig
	 * passieren koennte muss die methode synchronized sein! Zu beginn der Methode muessen wir trotzdem ueberpruefen ob der Node
	 * nicht doch gerade erst erstellt wurde. In diesem Fall wuerden wiir einfach value auf die value des nodes addieren.
	 * Falls der knoten noch nicht 'da ist' erstellen wir einen neuen und hohlen uns wie gehabt aus guarded commands die flowrate usw.
	 * 
	 * der erstellte knoten wird in die nodelist hinzugefuegt
	 */
	public synchronized void createNewNode(int x, int y, double value){
		if (nodeList.get(y)==null){
			double rateTop = matrix.graph.getRateForTarget(x, y, Neighbor.Top);
			double rateBottom = matrix.graph.getRateForTarget(x, y, Neighbor.Bottom);
			double rateLeft = matrix.graph.getRateForTarget(x, y, Neighbor.Left);
			double rateRight = matrix.graph.getRateForTarget(x, y, Neighbor.Right);
			Node node = new Node(this, x, y, value, rateTop, rateBottom, rateLeft, rateRight);
			nodeList.set(y, node);
		}
		else{
			nodeList.get(y).changeValue(value);
		}
	}
	
	
	/*
	 * startet Iteration ueber jeden Knoten, in dem dann Communicate auf dem Node mit den entsprechenden Nachbarnodes aufgerufen wird.
	 */
	private void startCommunicate(){
		int height = matrix.height;
		for (int i = 0; i < height; i++){
			Node node = nodeList.get(i);
			if (node != null)
				communicate(node);
		}
	}
	
	
	/*
	 * startet Iteration ueber jeden Knoten, in dem dann Communicate auf dem Node mit den entsprechenden Nachbarnodes aufgerufen wird.
	 */
	private void startFlow(){
		int height = matrix.height;
		for (int i = 0; i < height; i++){
			Node node = nodeList.get(i);
			if (node != null)
				flow(node);
		}
	}
	
	
	/*
	 * wir checken lokale Konvergenz indem wir fuer die Spalte testen ob inflow = outflow +- epsilon
	 */
	private boolean checkLocalTerminate(){
		double leftOutflow = 0.0;
		double rightOutflow = 0.0;
		
		double rightInflow = 0.0;
		double leftInflow = 0.0;
		
		boolean rightKonvergenz = false;
		boolean leftKonvergenz = false;
		
		int height = matrix.height;
		
		
		for (int i = 0; i < height; i++){
			Node node = nodeList.get(i);
			if (node != null){
				leftOutflow += node.getAkkuLeft();
				rightOutflow += node.getAkkuRight();
			}
		}
		
		if (leftColumn == null)
			leftKonvergenz = true;
		else{
			for (int i = 0; i < height; i++){
				Node node = leftColumn.nodeList.get(i);
				if (node != null){
					leftInflow += node.getAkkuRight();
				}
			}
			if (Math.abs(leftInflow - leftOutflow) >= matrix.epsilon)
				return false;
			leftKonvergenz = true;
		}
		
		if (rightColumn == null)
			rightKonvergenz = true;
		else{
			for (int i = 0; i < height; i++){
				Node node = rightColumn.nodeList.get(i);
				if (node != null){
					rightInflow += node.getAkkuLeft();
				}
			}
			if (Math.abs(rightInflow - rightOutflow) >= matrix.epsilon)
				return false;
			leftKonvergenz = true;
		}	
		
		return (rightKonvergenz && leftKonvergenz);
	}
	
	/*
	 * die Funktionalitaet einer Column iteriert grundsaetzlich immer barrierCount-mal.
	 * 
	 * im ersten Schritt wird fuer jeden enthaltenen Node zuerst einmal calculate() aufgerufen, anschliessend einmal flow() auf allen um den Ausstausch
	 * innerhalb der Spalte zu realisieren. Nach barriercount Durchlaeufen tritt der Threat in die Barrier barrier1 ein. Wenn alle Column-threats die Barrier
	 * erreicht haben testet die Barrier ob preciseTest != true. Falls das gilt checkt die Barrier die Spaltenterminierung indem sie auf jeder Spalte
	 * checkLocalTest() aufruft. Falls dies fuer jede Spalte true ist setzt die spalte precisetest = true ueber die methode startPreciseTest() in Picture.
	 * 
	 * Nachdem barrier1 fertig ist werden die threads Column fortgesetzt und jede Spalte ruft communcate() auf jedem ihrer Knoten auf. Nach diesem Communicate-
	 * Aufruf wartet jeder Threat auf barrier2.
	 * 
	 * Sobald jeder thread in barrier2 angekommen ist testet barrier2 ob precisetest=true. Falls ja iteriert barrier2 ueber alle nodes in jeder column und
	 * addiert die unterschiede zwischen value und value_old jedes Nodes auf. Sobald diese Summe > epsilon ist kann abgebrochen werden. Falls die Summe der
	 * Unterschiede in ALLEN nodes < epsilon erkennen wir globale konvergenz und rufen in picture terminateThreats() auf.
	 * 
	 * Die threads werden wieder fortgesetzt und falls nun preciseTest = true wird fuer jeden enthaltenen node value_old = value gesetzt und anschliessend auf
	 * barrier3 gewartet. Barrier3 implementiert keine eigene run methode, sie sorgt nur dafuer das this.run() erst nach setzen von allen value_old geschieht
	 * Falls precisetest = false war wird direkt this.run aufgerufen und kein warten auf barrier3 notwendig.
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
