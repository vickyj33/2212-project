package cryptoTrader.utils;

public class TradeResult {

	private String traderName;
	private String strategy;
	private String coinTraded;
	private String action;
	private float quantity;
	private double price;
	private String date;
	
	public TradeResult(String traderName, String strategy, String coinTraded, String action, float quantity, double price, String date) {
		this.traderName = traderName;
		this.strategy = strategy;
		this.coinTraded = coinTraded;
		this.action = action;
		this.quantity = quantity;
		this.price = price;
		this.date = date;
	}
	
	public String getTraderName() {
		return this.traderName;
	}

	public String getStrategy() {
		return this.strategy;
	}
	
	public String getCoinTraded() {
		return coinTraded;
	}
	
	public String getAction() {
		return this.action;
	}
	
	public Float getQuantity() {
		return this.quantity;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public void setTraderName(String name) {
		this.traderName = name;
	}
	
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	
	public void setCoinTraded(String coin) {
		this.coinTraded = coin;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	
}
