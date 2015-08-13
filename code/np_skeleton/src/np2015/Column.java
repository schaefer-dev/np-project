package np2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Column implements Runnable {
	private Column leftColumn;
	private Column rightColumn;
	public HashMap<Integer, Node> nodeMap; // wir starten mit y = 0, x = 0 'oben
											// links' in der matrix
	private final int size;
	private final Picture matrix;
	private int barrierCount;
	private boolean preciseTest;
	private CyclicBarrier barrier1;
	private CyclicBarrier barrier15;
	private CyclicBarrier barrier2;
	private CyclicBarrier barrier3;
	private LinkedList<Node> insertQueue;
	private boolean terminated;

	public Column(int size, Picture matrix, CyclicBarrier barrier1,
			CyclicBarrier barrier2, CyclicBarrier barrier3, CyclicBarrier barrier15) {
		this.size = size;
		this.matrix = matrix;
		this.nodeMap = new HashMap<>(size);
		this.insertQueue = new LinkedList<Node>();
		this.preciseTest = false;
		this.barrier1 = barrier1;
		this.barrier15 = barrier15;
		this.barrier2 = barrier2;
		this.barrier3 = barrier3;
		this.terminated = false;
		this.barrierCount = matrix.getBarrierCount();
		// TODO nodeList hier initialisieren mit der entsprechenden maimalen
		// laenge
	}

	public void setNeighbour(Column left, Column right) {
		this.leftColumn = left;
		this.rightColumn = right;
	}

	public void terminate() {
		terminated = true;
	}
	
	public void insertEverythingFromQueue(){
		for (Node node : insertQueue) {
			nodeMap.put(node.getY(), node);
		}
		insertQueue.clear();
	}

	/*
	 * Communicate schiebt die Akkumulatoren aus this auf die Knoden right und
	 * left. Dazu wird in left bzw right die Methode changeValue aufgerufen. Am
	 * ende Nullen wir die uebertragenen Akkumulatoren.
	 * 
	 * wichtiger Sonderfall wenn wir hier als nachbarnode right einen noch nicht
	 * existierenden node erhalten koennen wir an dieser stelle direkt einen
	 * neuen Node erstellen und dann right=newNode ... setzen mit
	 * enstsprechenden Koordinaten und value. Wir muessen uns vorher noch die
	 * rates des Knotens besorgen!
	 */
	public void communicate(Node node) {
		if (node == null)
			throw new IllegalArgumentException("flow node shoult not be null!");
		double akkuLeft = node.getAkkuLeft();
		double akkuRight = node.getAkkuRight();

		int x = node.getX();
		int y = node.getY();

		if (akkuLeft > 0.0) { // Not neccesary if flowrates at corners are 0!
								// Better safe than sorry
			if (x != 0) {
				Node leftNode = leftColumn.nodeMap.get(y);
				if (leftNode == null)
					leftColumn.createNewNode(x - 1, y, akkuLeft);
				else {
					leftNode.changeValue(akkuLeft);
				}
			}
		}

		if (akkuRight > 0.0) {
			if (x != matrix.width - 1) { // /TODO
				Node rightNode = rightColumn.nodeMap.get(y);
				if (rightNode == null)
					rightColumn.createNewNode(x + 1, y, akkuRight);
				else {
					rightNode.changeValue(akkuRight);
				}
			}
		}
		node.setCommunicateAkkuZero();
	}

	/*
	 * siehe communicate() aber in der Vertikalen
	 */
	public void flow(Node node) {
		if (node == null)
			throw new IllegalArgumentException("flow node shoult not be null!");
		double akkuTop = node.getAkkuTop();
		double akkuBottom = node.getAkkuBottom();

		int y = node.getY();
		int x = node.getX();

		if (akkuTop > 0.0) {
			if (y != 0) {
				Node topNode = nodeMap.get(y - 1);
				if (topNode == null)
					createNewNode(x, y - 1, akkuTop);
				else {
					topNode.changeValue(akkuTop);
				}
			}
		}
		if (akkuBottom > 0.0) {
			if (y != matrix.height - 1) {
				Node bottomNode = nodeMap.get(y + 1);
				if (bottomNode == null)
					createNewNode(x, y + 1, akkuBottom);
				else {
					bottomNode.changeValue(akkuBottom);
				}
			}
		}
		node.setFlowAkkuZero();

	}

	/*
	 * wir erstellen einen neuen Node in this, da von beiden benachbarten
	 * spalten ein aufruf dieser methode potentiell gleichzeitig passieren
	 * koennte muss die methode synchronized sein! Zu beginn der Methode muessen
	 * wir trotzdem ueberpruefen ob der Node nicht doch gerade erst erstellt
	 * wurde. In diesem Fall wuerden wiir einfach value auf die value des nodes
	 * addieren. Falls der knoten noch nicht 'da ist' erstellen wir einen neuen
	 * und hohlen uns wie gehabt aus guarded commands die flowrate usw.
	 * 
	 * der erstellte knoten wird in die nodelist hinzugefuegt
	 */
	public synchronized void createNewNode(int x, int y, double value) {
		
		for (Node node : insertQueue){
			if (node.getY() ==  y){
				node.changeValue(value);
				return;
			}
		}
		
		double rateTop = matrix.graph.getRateForTarget(x, y, Neighbor.Top);
		double rateBottom = matrix.graph.getRateForTarget(x, y,
				Neighbor.Bottom);
		double rateLeft = matrix.graph
				.getRateForTarget(x, y, Neighbor.Left);
		double rateRight = matrix.graph.getRateForTarget(x, y,
				Neighbor.Right);
		Node node = new Node(this, x, y, value, rateTop, rateBottom,
				rateLeft, rateRight);
		
		insertQueue.add(node);
	}
	
	
	/*
	 * hinzufuegen des initialen nodes
	 */
	public synchronized void createInitialNode(int x, int y, double value) {
		if (nodeMap.get(y) == null) {
			double rateTop = matrix.graph.getRateForTarget(x, y, Neighbor.Top);
			double rateBottom = matrix.graph.getRateForTarget(x, y,
					Neighbor.Bottom);
			double rateLeft = matrix.graph
					.getRateForTarget(x, y, Neighbor.Left);
			double rateRight = matrix.graph.getRateForTarget(x, y,
					Neighbor.Right);
			Node node = new Node(this, x, y, value, rateTop, rateBottom,
					rateLeft, rateRight);
			nodeMap.put(y, node);
		} else {
			nodeMap.get(y).changeValue(value);
		}
	}

	/*
	 * startet Iteration ueber jeden Knoten, in dem dann Communicate auf dem
	 * Node mit den entsprechenden Nachbarnodes aufgerufen wird.
	 */
	private void startCommunicate() {
		for (Node node : nodeMap.values()) {
			if (node != null)// if you do not put nulls in there you can delete
								// this line
				communicate(node);
		}
	}

	/*
	 * startet Iteration ueber jeden Knoten, in dem dann Communicate auf dem
	 * Node mit den entsprechenden Nachbarnodes aufgerufen wird.
	 */
	private void startFlow() {
		for (Node node : nodeMap.values()) {
			if (node != null)// if you do not put nulls in there you can delete
								// this line
				flow(node);
		}
	}

	/*
	 * starts iteration over all nodes to call the function 'calculate' of every node
	 *  to estimate the outflow values of every node
	 */
	private void startCalculate() {
		for (Node node : nodeMap.values()) {
			if (node != null)// if you do not put nulls in there you can delete
								// this line
				node.calculate();
		}
	}

	
	/**
	 * starts iteration over all nodes to 'update' the variable value_old of every node in this column
	 */
	private void startPreciseTest() {
		for (Node node : nodeMap.values()) {
			if (node != null)// if you do not put nulls in there you can delete
								// this line
				node.setValue_old(node.getValue());
		}

	}
	
	/**
	 * This function computes the sum of all right akkus of all nodes in this column and returns the result
	 * @return double : Retzurns the rightoutflow
	 */
	public double rightOutflow() {
		double rightOutflow = 0.0;

		for (Node node : nodeMap.values()) {
			if (node != null)// if you do not put nulls in there you can delete
								// this line
				rightOutflow += node.getAkkuRight();
		}

		return rightOutflow;
	}

	
	/**
	 * This function computes the sum of all left akkus of all nodes in this column and returns the result
	 * @return double : Retzurns the leftoutflow
	 */
	public double leftOutflow() {
		double leftOutflow = 0.0;

		for (Node node : nodeMap.values()) {
			if (node != null)// if you do not put nulls in there you can delete
								// this line
				leftOutflow += node.getAkkuLeft();
		}

		return leftOutflow;
	}

	
	/**
	 * We proof wheter inflow = outflow +- epsilion or not. 
	 * @return boolean: Returns true if the outflow of this colummn is the inflow +- epsilion othwerwise it returns false.
	 */
	public boolean checkLocalTerminate() {

		boolean rightKonvergenz = false;
		boolean leftKonvergenz = false;

		if (leftColumn != null) {
			if (Math.abs(leftColumn.rightOutflow() - this.leftOutflow()) <= matrix.epsilon)
				leftKonvergenz = true;
		} else {
			leftKonvergenz = true;
		}

		if (!leftKonvergenz) {
			// debug
			//System.out.println("local check! left " + leftKonvergenz);
			// debugend
			return false;
		}

		if (rightColumn != null) {
			if (Math.abs(rightColumn.leftOutflow() - this.rightOutflow()) <= matrix.epsilon)
				rightKonvergenz = true;
		} else {
			rightKonvergenz = true;
		}
		// debug
		//if (rightKonvergenz)
			//System.out.println("local check! right " + rightKonvergenz);
		// debugend
		return rightKonvergenz;
	}

	
	/**
	 * @return boolean: Returns wheter all nodes of this column fullfill our properties of precisetest or not
	 */
	public boolean checkPreciseTest() {
		double epsilon = matrix.epsilon;	
		
		for (Node node : nodeMap.values()) {
			double step = Math.pow((node.getValue() - node.getValue_old()),2);
			double euklidnorm = Math.sqrt( step ) ;
			if (euklidnorm > epsilon){
				return false;
			}
		}
		return true;
	}

	/*
	 * die Funktionalitaet einer Column iteriert grundsaetzlich immer
	 * barrierCount-mal.
	 * 
	 * im ersten Schritt wird fuer jeden enthaltenen Node zuerst einmal
	 * calculate() aufgerufen, anschliessend einmal flow() auf allen um den
	 * Ausstausch innerhalb der Spalte zu realisieren. Nach barriercount
	 * Durchlaeufen tritt der Threat in die Barrier barrier1 ein. Wenn alle
	 * Column-threats die Barrier erreicht haben testet die Barrier ob
	 * preciseTest != true. Falls das gilt checkt die Barrier die
	 * Spaltenterminierung indem sie auf jeder Spalte checkLocalTest() aufruft.
	 * Falls dies fuer jede Spalte true ist setzt die barrier fuer jede Spalte
	 * precisetest = true ueber die methode setPreciseTest() in Picture.
	 * 
	 * Nachdem barrier1 fertig ist werden die threads Column fortgesetzt und
	 * jede Spalte ruft communcate() auf jedem ihrer Knoten auf. Nach diesem
	 * Communicate- Aufruf wartet jeder Threat auf barrier2.
	 * 
	 * Sobald jeder thread in barrier2 angekommen ist testet barrier2 ob
	 * precisetest=true. Falls ja iteriert barrier2 ueber alle nodes in jeder
	 * column und addiert die unterschiede zwischen value und value_old jedes
	 * Nodes auf. Sobald diese Summe > epsilon ist kann abgebrochen werden.
	 * Falls die Summe der Unterschiede in ALLEN nodes < epsilon erkennen wir
	 * globale konvergenz und rufen in picture terminateThreats() auf.
	 * 
	 * Die threads werden wieder fortgesetzt und falls nun preciseTest = true
	 * wird fuer jeden enthaltenen node value_old = value gesetzt und
	 * anschliessend auf barrier3 gewartet. Barrier3 implementiert keine eigene
	 * run methode, sie sorgt nur dafuer das this.run() erst nach setzen von
	 * allen value_old geschieht Falls precisetest = false war wird direkt
	 * this.run aufgerufen und kein warten auf barrier3 notwendig.
	 * 
	 * Nachtrag:
	 * barrier15 sorgt dafuer das neue knoten in jeder column erst dann eingefuegt
	 * werden wenn jede spalte fertig communicated hat
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
			if (!preciseTest) {
				for (int i = 0; i < barrierCount; i++) {
					startCalculate();
					startFlow();
					insertEverythingFromQueue();
				}

				try {
					barrier1.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
					throw new IllegalArgumentException("barrier1 await fail!");
				}

				startCommunicate();
				
				try {
					barrier15.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
					throw new IllegalArgumentException("barrier1 await fail!");
				}
				
				insertEverythingFromQueue();

				try {
					barrier2.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
					throw new IllegalArgumentException("barrier2 await fail!");
				}

				// NOTE: wir koennten den precisetest auch nebenlaeufig fuer
				// alle spalten ausfuehren! Moeglicher Speedup

				// terminierung an dieser Stelle falls barrier2 globale
				// konvergenz erkannt hat
				if (terminated)
					break;

				if (preciseTest) {
					startPreciseTest();
					try {
						barrier3.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
						throw new IllegalArgumentException(
								"barrier3 await fail!");
					}
				}
			} else {
				for (int i = 0; i < 1; i++) {  // da wir nach jedem schritt horizontal propagieren sollen
					startCalculate();
					startFlow();
				}

				try {
					barrier1.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
					throw new IllegalArgumentException("barrier1 await fail!");
				}

				startCommunicate();

				try {
					barrier2.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
					throw new IllegalArgumentException("barrier2 await fail!");
				}

				// NOTE: wir koennten den precisetest auch nebenlaeufig fuer
				// alle spalten ausfuehren! Moeglicher Speedup

				// terminierung an dieser Stelle falls barrier2 globale
				// konvergenz erkannt hat
				if (terminated)
					break;

				if (preciseTest) {
					startPreciseTest();
					try {
						barrier3.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
						throw new IllegalArgumentException(
								"barrier3 await fail!");
					}
				}
			}
		}
	}

	/**
	 * @return: If preciseTest is true it returns true otherwise it returns false.
	 */
	public boolean isPreciseTest() {
		return preciseTest;
	}

	/**
	 * Sets the variable preciseTest to the given parameter
	 * @param preciseTest to set the value of the variable preciseTest 
	 */
	public void setPreciseTest(boolean preciseTest) {
		this.preciseTest = preciseTest;
	}

	/**
	 * Returns the value of the node on position y. If there isn't a node at this position it returns 0.0 
	 * @param int y: the y-coordinate of the wanted node
	 * @return double: The value of the wanted node
	 */
	public double getValueAtY(int y) {
		Node node = nodeMap.get(y);
		if (node == null)
			return 0.0;
		return node.getValue();
	}
}
