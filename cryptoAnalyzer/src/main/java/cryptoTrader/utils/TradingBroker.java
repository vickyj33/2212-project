package cryptoTrader.utils;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Class that creates trading brokers
 * Each broker is unique and can make many trades using varying strategies
 * 
 * @author Vicky Jiang
 * @author Ian Guenther Green
 *
 */

public class TradingBroker {
	
	private String tradingBrokerName;
	private ArrayList<String> coinsList = new ArrayList<>();
	private double[] coinsPriceList;
	private String strategyName;
	
	private Hashtable<String, Integer> strategiesUsed;
	
	/**
	 * Constructor to create a trading broker
	 * @param name - broker name
	 * @param coins - list of coins of interest
	 * @param prices - prices for the coins of interest
	 * @param strategy - current strategy being used
	 */
	public TradingBroker(String name, String[] coins, double[] prices, String strategy) {
		tradingBrokerName = name;
		
		for(String coin:coins) 
			coinsList.add(coin);
		
		// initiates strategiesUsed dictionary which tracks how many times each trading broker uses each strategy
		strategiesUsed = new Hashtable<>();
		strategiesUsed.put("Strategy-A", 0);
		strategiesUsed.put("Strategy-B", 0);
		strategiesUsed.put("Strategy-C", 0);
		strategiesUsed.put("Strategy-D", 0);
		strategiesUsed.put("Strategy-E", 0);
		
		this.coinsPriceList = prices;
		this.strategyName = strategy;
	}
	
	/**
	 * Getter for the name of the trading broker
	 * @return tradingBrokerName string
	 */
	public String getTradingBrokerName() {
		return this.tradingBrokerName;
	}
	
	/**
	 * Getter for the coins list
	 * @return ArrayList of coins of interest
	 */
	public ArrayList<String> getCoinsList() {
		return this.coinsList;
	}
	
	/**
	 * Getter for the coins price list
	 * @return double array of prices of coins of interest
	 */
	public double[] getCoinsPriceList() {
		return coinsPriceList;
	}
	
	/**
	 * Getter for current strategy
	 * @return strategyName string
	 */
	public String getStrategy() {
		return this.strategyName;
	}
	
	/**
	 * Getter for how many times Strategy strategy has been used
	 * @param strategy - name of strategy
	 * @return how many times that strategy has been used
	 */
	public int getStrategyUses(String strategy) {
		return strategiesUsed.get(strategy);
	}
	
	// note: no setter for brokerName as this is fixed
	
	/**
	 * Setter for list of coins of interest. Converts String array to ArrayList in instance variable coinsList
	 * @param coins - String array with coins of interest
	 */
	public void setCoinsList(String[] coins) {
		coinsList.clear();
		for(String coin:coins) 
			coinsList.add(coin);
	}
	
	/**
	 * Setter for strategy of interest
	 * @param strategy - name of strategy
	 */
	public void setStrategy(String strategy) {
		this.strategyName = strategy;
	}

	/**
	 * Setter for prices of coins of interest
	 * @param coinsPriceList
	 */
	public void setCoinsPriceList(double[] coinsPriceList) {
		this.coinsPriceList = coinsPriceList;
	}
	
	/**
	 * Method that is called when TradingBroker object uses a strategy
	 * Increments the strategy in strategiesUsed by one
	 * @param strategy
	 */
	public void useStrategy(String strategy) {
		strategiesUsed.put(strategy, strategiesUsed.get(strategy) + 1);
	}
	
}
