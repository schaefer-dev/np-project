package np2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Column implements Runnable {
	private Column leftColumn;
	private Column rightColumn;
	public HashMap<Integer, Node> nodeMap; // we start with (0,0) in the top left corner
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

	/**
	 * Column implements Runnable to serve as a thread
	 * 
	 * @param size size of the column (height of the matrix)
	 * @param matrix Matrix in which the column is placed
	 * @param barrier1 @see barrier1
	 * @param barrier2 @see barrier2
	 * @param barrier3 @see barrier3
	 * @param barrier15 @see barrier15
	 */
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
	}
	
	/**
	 * @param left Left Neighbour column
	 * @param right Right Neighbour column
	 */
	public void setNeighbour(Column left, Column right) {
		this.leftColumn = left;
		this.rightColumn = right;
	}

	/**
	 * set terminated var true
	 */
	public void terminate() {
		terminated = true;
	}
	
	/**
	 * Function to add all new nodes which were created during flow/communicate into the node hashmap
	 */
	public void insertEverythingFromQueue(){
		for (Node node : insertQueue) {
			nodeMap.put(node.getY(), node);
		}
		insertQueue.clear();
	}


	/**
	 * Communicate will move the akkus to the node on the right and on the left
	 * If one of this nodes does not exist we call the createNewNode method with
	 * the given values
	 * 
	 * @param node the Node from which we communicate to 'neighbour-nodes' on the right and left
	 * 
	 */
	public void communicate(Node node) {
		if (node == null)
			throw new IllegalArgumentException("flow node shoult not be null!");
		double akkuLeft = node.getAkkuLeft();
		double akkuRight = node.getAkkuRight();

		int x = node.getX();
		int y = node.getY();

		if (akkuLeft > 0.0) {
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
			if (x != matrix.width - 1) { 
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


	/**
	 * Communicate will move the akkus to the node on top and on bottom
	 * If one of this nodes does not exist we call the createNewNode method with
	 * the given values
	 * 
	 * @param node the Node from which we communicate to 'neighbour-nodes' on top and bottom
	 * 
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

	/**
	 * we create a new node in this Column. We protected this method with sychronized
	 * to avoid dataraces when this method is called twice on the same column with
	 * potentially the same node to create. We avoid creating nodes twice entirely
	 * but every value is added guaranteed.
	 * 
	 * @param x x-koordinate of the Node to create
	 * @param y y-koordinate of the Node to create
	 * @param value value of the Node to create
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
		Node node = new Node(x, y, value, rateTop, rateBottom,
				rateLeft, rateRight);
		
		insertQueue.add(node);
	}
	

	/**
	 * we create the initial node in this column.
	 * 
	 * @param x x-koordinate of the Node to create
	 * @param y y-koordinate of the Node to create
	 * @param value value of the Node to create
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
			Node node = new Node(x, y, value, rateTop, rateBottom,
					rateLeft, rateRight);
			nodeMap.put(y, node);
		} else {
			nodeMap.get(y).changeValue(value);
		}
	}


	
	/**
	 * Starts iteration on this column which calls communicate on every node
	 */
	private void startCommunicate() {
		for (Node node : nodeMap.values()) {
			communicate(node);
		}
	}


	/**
	 * Starts iteration on this column which calls flow on every node
	 */
	private void startFlow() {
		for (Node node : nodeMap.values()) {
			flow(node);
		}
	}

	
	/**
	 * Starts iteration on this column which calls calculate on every node
	 */
	private void startCalculate() {
		for (Node node : nodeMap.values()) {
			node.calculate();
		}
	}

	
	/**
	 * starts iteration on this column to 'update' the variable value_old of every node in this column
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
	 * @return double : Returns the rightoutflow
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
	 * @return double : Returns the leftoutflow
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
	 * We check whether rightInflow - rightOutflow <= epsilon AND leftInflow - leftOutflow <= epsilon and returns the result
	 * 
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
	 * @return boolean: Returns whether all nodes of this column fulfill our properties of precisetest or not
	 */
	public double checkPreciseTest() {
		double epsilon = matrix.epsilon;	
		double diffsum = 0.0;
		
		for (Node node : nodeMap.values()) {
			diffsum += Math.pow((node.getValue() - node.getValue_old()),2);		
		}
		return diffsum;
	}

	
	 /**
	 * function which starts the main process of a column. Generally we iterate
	 * barriercount times (was set earlier) and every iteration calls startCalculate
	 * which calculates the akkus of every node in this column, afterwards startflow()
	 * is called which starts moving the top and bottom akkus inside this column,
	 * afterwards all new nodes waiting in the queue are inserted into the hashmap
	 * by calling insertEverythingFromQueue().
	 * 
	 * After this iteration Block every column waits for barrier1. In barrier1 we
	 * check for convergence in every column. If every column meets the conditions
	 * we start the preciseTest, which will change the behaviour of this run() method
	 * mainly by setting barriercount to 1.
	 * 
	 * After barrier1 every column calls startsCommunicate() which moves akkuLeft and
	 * akkuRight of the nodes to neighbour nodes in neighbourcolumns.
	 * 
	 * Afterwards every Column waits for barrier15 to be sure that every column finished
	 * communicting.
	 * 
	 * Afterwards we call insertEverythingFromQueue() again to add all new nodes which
	 * were created during communicate in the hashmap. And we wait for barrier2.
	 * 
	 * Barrier2 will print testresults depending on the input for testResultcount and
	 * will check for global convergence if precisetest is already started.
	 * 
	 * after barrier2 we check if our thread was terminated with terminateall()
	 * if this is the case we stop our run, otherwise we check if precisetest is
	 * already enabled. If yes we set value_old to the current value of every
	 * node contained in this column. 
	 * 
	 * If we did not terminate at this point we start run again.
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
