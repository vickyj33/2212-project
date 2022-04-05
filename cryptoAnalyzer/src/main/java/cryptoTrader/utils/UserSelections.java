package cryptoTrader.utils;

/**
 * UserSelections class stores all the information in one line of the input table on the MainUI
 * This includes broker name, coins of interest, and trading strategy
 * Getters and Setters are defined for instance variables
 * 
 * @author Vicky Jiang
 * @author Ian Guenther Green
 *
 */

public class UserSelections {
	
	private String tradingBroker;
	private String[] coinsList;
	private String strategyName;
	
	/**
	 * Constructor for UserSelections class. Assigns instance variables
	 * @param name - name of the trading broker
	 * @param coins - list of coins entered in row on input table
	 * @param strategy - strategy chosen from fixed list of strategies
	 */
	public UserSelections(String name, String[] coins, String strategy) {
		tradingBroker = name;
		this.coinsList = coins;
		this.strategyName = strategy;
	}
	
	/**
	 * Getter method that returns the broker name.
	 * @return broker name String.
	 */
	public String getTradingBroker() {
		return this.tradingBroker;
	}
	
	/**
	 * Getter method that returns the list of coins for the trading broker.
	 * @return coinsList String array.
	 */
	public String[] getCoinsList() {
		return this.coinsList;
	}
	
	/**
	 * Getter for strategy entered
	 * @return strategy name 
	 */
	public String getStrategy() {
		return this.strategyName;
	}
	
	/**
	 * Sets the name of the trading broker
	 * @param broker name
	 */
	public void setTradingBroker(String name) {
		this.tradingBroker = name;
	}
	
	/**
	 * Sets the coins list
	 * @param String array with names of crypto coins
	 */
	public void setCoinsList(String[] coins) {
		this.coinsList = coins;
	}
	
	/**
	 * Sets the strategy to be used
	 * @param name of the strategy
	 */
	public void setStrategy(String strategy) {
		this.strategyName = strategy;
	}

}
