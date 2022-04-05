package cryptoTrader.tradingStrategies;

import java.util.ArrayList;

import cryptoTrader.processing.CheckCoinsListFacade;
import cryptoTrader.utils.TradeResult;
import cryptoTrader.utils.TradingBroker;

import java.util.Date;  
import java.text.SimpleDateFormat; 
	
/**
 * defines TradingStrategyD
 * @author Ian Guenther Green
 */

public class TradingStrategyD implements TradingStrategy{
	
	private String strategyName = "Strategy-D";
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
	public TradingStrategyD(TradingBroker broker) {
		this.tradingBroker = broker.getTradingBrokerName();
		this.coinsList = broker.getCoinsList();
		this.coinsPriceList = broker.getCoinsPriceList();
		
		requiredCoins.add("DOGE");
		requiredCoins.add("BTC");
		requiredCoins.add("MANA");
		
		date = new Date();		
		dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
		
	}
	
	/**
	 * method called to perform trade with specified strategy
	 * @return TradeResult object containing trades made
	 */
	@Override
	public TradeResult performTrade() {
		
		// rule: if price of DOGE (dogecoin) < 0.2 and BTC (bitcoin) >= 57000 sell 750 DOGE 
		// if not, buy $900 worth of MANA (decentraland)
		
		CheckCoinsListFacade checker = new CheckCoinsListFacade(requiredCoins, coinsList);
		TradeResult result;
		
		float quantity;
		
		// if required coins are not in coins entered, trade fails
		if (!checker.checkCoinsList()) {
			return result = new TradeResult(tradingBroker, strategyName, "Fail", "Fail", 0f, 0d, dateFormat.format(date));
		}

		if((coinsPriceList[coinsList.indexOf("DOGE")] < 0.2) && (coinsPriceList[coinsList.indexOf("BTC")] >= 57000)) {
			
			return result = new TradeResult(tradingBroker, strategyName, "DOGE", "Sell", 750, coinsPriceList[coinsList.indexOf("DOGE")], dateFormat.format(date));
			
		}
		
		quantity = (float) (900/coinsPriceList[coinsList.indexOf("MANA")]);
		return result = new TradeResult(tradingBroker, strategyName, "MANA", "Buy", quantity, coinsPriceList[coinsList.indexOf("MANA")], dateFormat.format(date));
		
	}
	
}