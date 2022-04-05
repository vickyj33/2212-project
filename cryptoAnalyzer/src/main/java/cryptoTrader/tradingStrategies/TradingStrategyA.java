package cryptoTrader.tradingStrategies;

import java.util.ArrayList;

import cryptoTrader.processing.CheckCoinsListFacade;
import cryptoTrader.utils.TradeResult;
import cryptoTrader.utils.TradingBroker;

import java.util.Date;  
import java.text.SimpleDateFormat; 

/**
 * defines TradingStrategyA
 * @author Ian Guenther Green
 */

public class TradingStrategyA implements TradingStrategy{
	
	private String strategyName = "Strategy-A";
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
	public TradingStrategyA(TradingBroker broker) {
		this.tradingBroker = broker.getTradingBrokerName();
		this.coinsList = broker.getCoinsList();
		this.coinsPriceList = broker.getCoinsPriceList();
		
		requiredCoins.add("BTC");
		requiredCoins.add("ADA");
		requiredCoins.add("ETH");
		
		date = new Date();		
		dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
		
	}
	
	/**
	 * method called to perform trade with specified strategy
	 * @return TradeResult object containing trades made
	 */
	@Override
	public TradeResult performTrade() {
		
		// rule: if BTC (bitcoin) > $55000 and ADA (cardano) < $2, buy 2 BTC
		// if not, sell 600 ETH (ethereum)
		
		CheckCoinsListFacade checker = new CheckCoinsListFacade(requiredCoins, coinsList);
		TradeResult result;
		
		// if required coins are not in coins entered, trade fails
		if (!checker.checkCoinsList()) {
			return result = new TradeResult(tradingBroker, strategyName, "Fail", "Fail", 0f, 0d, dateFormat.format(date));
		}
		
		if((coinsPriceList[coinsList.indexOf("BTC")] > 55000) && (coinsPriceList[coinsList.indexOf("ADA")] < 2)) {
			return result = new TradeResult(tradingBroker, strategyName, "BTC", "Buy", 2, coinsPriceList[coinsList.indexOf("BTC")], dateFormat.format(date));
		}
		
		return result = new TradeResult(tradingBroker, strategyName, "ETH", "Sell", 600, coinsPriceList[coinsList.indexOf("ETH")], dateFormat.format(date));
		
	}
	
}
