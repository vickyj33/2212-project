package cryptoTrader.utils;

public class UserSelections {
	
	private String tradingBroker;
	private String[] coinsList;
	private double[] coinsPriceList;
	private String strategyName;
	
	public UserSelections(String name, String[] coins, String strategy) {
		tradingBroker = name;
		this.coinsList = coins;
		this.strategyName = strategy;
	}
	
	// getters and setters
	/**
	 * Getter method that returns the broker name.
	 * @return String that represents the broker name.
	 */
	public String getTradingBroker() {
		return this.tradingBroker;
	}
	
	/**
	 * Getter method that returns the list of coins for the trading broker.
	 * @return coinsList that is stored in UserSelection in String format.
	 */
	public String[] getCoinsList() {
		return this.coinsList;
	}
	
	public double[] getCoinsPriceList() {
		return coinsPriceList;
	}
	
	/**
	 * Getter method that returns the start date that has been stored.
	 * @return Start date that is stored in UserSelection as a Date object. 
	 */
	public String getStrategy() {
		return this.strategyName;
	}
	
	/**
	 * Sets the coin of interest for storage in UserSelection.
	 * @param theCoin String representing the coin name.
	 */
	public void setTradingBroker(String name) {
		this.tradingBroker = name;
	}
	
	/**
	 * Sets the interval over which the data will be represented for storage in UserSelection.
	 * @param theInterval A string representing the interval of interest.
	 */
	public void setCoinsList(String[] coins) {
		this.coinsList = coins;
	}
	
	/**
	 * Sets the starting date for storage in UserSelection.
	 * @param theDate A Date object representing the start date from which the data will be collected.
	 */
	public void setStrategy(String strategy) {
		this.strategyName = strategy;
	}

	public void setCoinsPriceList(double[] coinsPriceList) {
		this.coinsPriceList = coinsPriceList;
	}

	
}
