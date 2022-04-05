package cryptoTrader.utils;

import java.util.List;

/**
 * class that stores chart data and formats the data for the trade log table 
 * utilizes the Singleton design pattern
 * 
 * @author Ali Mohamed
 * @author Vicky Jiang
 * @author Ian Guenther Green
 *
 */

public class ChartData {
	private static Object[][] tableData;
	private static ChartData instance = null;
	
	private static List<TradeResult> results;
	
	/**
	 * Constructor that takes a list of trade results and assigns to instance variable
	 * @param listOfTradeResults
	 */
	private ChartData(List<TradeResult> listOfTradeResults) {
		ChartData.results = listOfTradeResults;
	}
	
	/**
	 * getter function that returns the instance of ChartData class
	 * @param listOfTradeResults
	 * @return instance of ChartData class
	 */
	public static ChartData getInstance(List<TradeResult> listOfTradeResults) {
		if(instance == null)
			instance = new ChartData(listOfTradeResults);
		
		tableData = createTableData();
		
		return instance;
	}
	
	/**
	 * formats trades in TradeResult list so that it can be output on interface
	 * @return Object[][] that can be used in a swing table
	 */
	private static Object[][] createTableData() {
		tableData = new Object[results.size()][7];
		
		// loops through results list, putting information into tableData
		// puts results in tableData so most recent trades are at top
		for(int i = 0; i < results.size(); i++) {
			tableData[results.size() - i - 1][0] = results.get(i).getTraderName();
			tableData[results.size() - i - 1][1] = results.get(i).getStrategy();
			tableData[results.size() - i - 1][2] = results.get(i).getCoinTraded();
			tableData[results.size() - i - 1][3] = results.get(i).getAction();
			tableData[results.size() - i - 1][4] = results.get(i).getQuantity();
			tableData[results.size() - i - 1][5] = results.get(i).getPrice();
			tableData[results.size() - i - 1][6] = results.get(i).getDate();
			
		}
		
		return tableData;
		
	}
	
	/**
	 * getter function for tableData
	 * @return Object[][] containing tableData
	 */
	public Object[][] getTableData() {
		return ChartData.tableData;
	}
	
}
