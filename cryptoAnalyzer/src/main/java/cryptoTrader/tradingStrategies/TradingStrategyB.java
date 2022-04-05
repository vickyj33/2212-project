package cryptoTrader.tradingStrategies;

import java.util.ArrayList;

import cryptoTrader.processing.CheckCoinsListFacade;
import cryptoTrader.utils.TradeResult;
import cryptoTrader.utils.TradingBroker;

import java.util.Date;
import java.text.SimpleDateFormat; 

/**
 * defines TradingStrategyB
 * @author Ian Guenther Green
 */

public class TradingStrategyB implements TradingStrategy{
	
	private String strategyName = "Strategy-B";
	private String tradingBroker;
	private ArrayList<String> coinsList = new ArrayList<>();
	private double[] coinsPriceList;
	private ArrayList<String> requiredCoins = new ArrayList<>();
			
	Date date;
	SimpleDateFormat dateFormat;
	
	/**
	 * Constructor
	 * Assigns instance variables and required coins
	 * @param broker
	 */
	public TradingStrategyB(TradingBroker broker) {
		this.tradingBroker = broker.getTradingBrokerName();
		this.coinsList = broker.getCoinsList();
		this.coinsPriceList = broker.getCoinsPriceList();
		
		requiredCoins.add("ETH");
		requiredCoins.add("USDT");
		requiredCoins.add("ADA");
		
		date = new Date();		
		dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
		
	}
	
	/**
	 * method called to perform trade with specified strategy
	 * @return TradeResult object containing trades made
	 */
	@Override
	public TradeResult performTrade() {
		
		// rule: if price of ETH (ethereum) <= 4500 or USDT (tether) > 2 buy $1000 worth of USDT
		// if not, sell $800 worth of ADA
			
		CheckCoinsListFacade checker = new CheckCoinsListFacade(requiredCoins, coinsList);
		TradeResult result;
		
		float quantity;
		
		// if required coins are not in coins entered, trade fails
		if (!checker.checkCoinsList()) {
			return result = new TradeResult(tradingBroker, strategyName, "Fail", "Fail", 0f, 0d, dateFormat.format(date));
		}

		if((coinsPriceList[coinsList.indexOf("ETH")] <= 4500) || (coinsPriceList[coinsList.indexOf("USDT")] > 2)) {
			
			quantity = (float) (1000/coinsPriceList[coinsList.indexOf("USDT")]);
			return result = new TradeResult(tradingBroker, strategyName, "USDT", "Buy", quantity, coinsPriceList[coinsList.indexOf("USDT")], dateFormat.format(date));
			
		}
		
		quantity = (float) (800/coinsPriceList[coinsList.indexOf("ADA")]);
		return result = new TradeResult(tradingBroker, strategyName, "ADA", "Sell", quantity, coinsPriceList[coinsList.indexOf("ADA")], dateFormat.format(date));
		
	}
	
}