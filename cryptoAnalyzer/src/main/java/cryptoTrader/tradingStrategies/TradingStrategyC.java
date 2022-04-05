package cryptoTrader.tradingStrategies;

import java.util.ArrayList;

import cryptoTrader.processing.CheckCoinsListFacade;
import cryptoTrader.utils.TradeResult;
import cryptoTrader.utils.TradingBroker;

import java.util.Date;  
import java.text.SimpleDateFormat; 

/**
 * defines TradingStrategyC
 * @author Ian Guenther Green
 */

public class TradingStrategyC implements TradingStrategy{
	
	private String strategyName = "Strategy-C";
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
	public TradingStrategyC(TradingBroker broker) {
		this.tradingBroker = broker.getTradingBrokerName();
		this.coinsList = broker.getCoinsList();
		this.coinsPriceList = broker.getCoinsPriceList();
		
		requiredCoins.add("FTM");
		requiredCoins.add("ONE");
		
		date = new Date();		
		dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
		
	}
	
	/**
	 * method called to perform trade with specified strategy
	 * @return TradeResult object containing trades made
	 */
	@Override
	public TradeResult performTrade() {
		
		// rule: if price of FTM (fantom) > 2 and ONE (harmony) <= 1 buy 400 FTM
		// if not, sell $500 worth of ONE		
		
		CheckCoinsListFacade checker = new CheckCoinsListFacade(requiredCoins, coinsList);
		TradeResult result;
		
		float quantity;
		
		// if required coins are not in coins entered, trade fails
		if (!checker.checkCoinsList()) {
			return result = new TradeResult(tradingBroker, strategyName, "Fail", "Fail", 0f, 0d, dateFormat.format(date));
		}

		if((coinsPriceList[coinsList.indexOf("FTM")] > 2) && (coinsPriceList[coinsList.indexOf("ONE")] <= 1)) {
			
			return result = new TradeResult(tradingBroker, strategyName, "FTM", "Buy", 400, coinsPriceList[coinsList.indexOf("FTM")], dateFormat.format(date));
			
		}
		
		quantity = (float) (500/coinsPriceList[coinsList.indexOf("ONE")]);
		return result = new TradeResult(tradingBroker, strategyName, "ONE", "Sell", quantity, coinsPriceList[coinsList.indexOf("ONE")], dateFormat.format(date));
		
	}
	
}