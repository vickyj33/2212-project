package cryptoTrader.utils;

/**
 * Class that stores the result from the trades. Includes information for all columns in trade log table
 * Each trade made creates a separate TradeResult object
 * 
 * @author Ali Mohamed
 * @author Ian Guenther Green
 *
 */

public class TradeResult {

	private String traderName;
	private String strategy;
	private String coinTraded;
	private String action;
	private float quantity;
	private double price;
	private String date;
	
	/**
	 * Constructor to set instance variables that pertain to the trade that was made
	 * @param traderName - name of trading broker
	 * @param strategy - trading strategy used
	 * @param coinTraded - coin that was traded
	 * @param action - whether coin was bought or sold
	 * @param quantity - how much was bought or sold
	 * @param price - unit price of the coin
	 * @param date - date of transaction
	 */
	public TradeResult(String traderName, String strategy, String coinTraded, String action, float quantity, double price, String date) {
		this.traderName = traderName;
		this.strategy = strategy;
		this.coinTraded = coinTraded;
		this.action = action;
		this.quantity = quantity;
		this.price = price;
		this.date = date;
	}
	
	/**
	 * Getter method for trader names
	 * @return traderName
	 */
	public String getTraderName() {
		return this.traderName;
	}
	
	/**
	 * Getter for strategy type
	 * @return strategy used
	 */
	public String getStrategy() {
		return this.strategy;
	}
	
	/**
	 * Getter for coin traded
	 * @return crypto coin that was traded
	 */
	public String getCoinTraded() {
		return coinTraded;
	}
	
	/**
	 * Getter for action performed
	 * @return action - either "buy" or "sell"
	 */
	public String getAction() {
		return this.action;
	}
	
	/**
	 * Getter for quantity in transaction
	 * @return quantity
	 */
	public Float getQuantity() {
		return this.quantity;
	}
	
	/**
	 * Getter for unit price of coin
	 * @return price
	 */
	public double getPrice() {
		return this.price;
	}
	
	/**
	 * Getter for date of transaction
	 * @return date
	 */
	public String getDate() {
		return this.date;
	}
	
	/**
	 * Setter for traderName
	 * @param name
	 */
	public void setTraderName(String name) {
		this.traderName = name;
	}
	
	/**
	 * Setter for strategy
	 * @param strategy
	 */
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	
	/**
	 * Setter for coinTraded
	 * @param coin
	 */
	public void setCoinTraded(String coin) {
		this.coinTraded = coin;
	}
	
	/**
	 * Setter for action
	 * @param action
	 */
	public void setAction(String action) {
		this.action = action;
	}
	
}
