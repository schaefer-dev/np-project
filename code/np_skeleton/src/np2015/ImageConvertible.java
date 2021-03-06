package np2015;

public interface ImageConvertible {
	/**
	 * 
	 * @param column
	 * @param row
	 * @return the value in the graph with the coordinates (column, row)
	 * The graph starts with (0,0) in the upper left corner
	 */
	public double getValueAt(int column, int row);
	
	/*
	 * 
	 * 
	 * startet die ausfuehrung
	 */
	public void runAllColumns();
}
