package np2015;

import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class Column implements Runnable{
	private Column leftColumn;
	private Column rightColumn;
	private ArrayList<Node> nodeList;
	private final int size;
	private final Picture matrix;
	private int barrierCount;
	private boolean preciseTest;
	private CyclicBarrier barrier1;

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
	 * startet Iteration ueber jeden Knoten, in dem dann Communicate auf dem Node mit den entsprechenden Nachbarnodes aufgerufen wird.
	 */
	private void startCommunicate(){
		//TODO
	}
	
	/*
	 * wir checken lokale Konvergenz indem wir fuer die Spalte testen ob inflow = outflow +- epsilon
	 */
	private boolean checkLocalTerminate(){
		//TODO
		return false;
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
