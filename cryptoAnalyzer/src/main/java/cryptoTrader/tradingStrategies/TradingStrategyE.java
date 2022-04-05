package cryptoTrader.tradingStrategies;

import java.util.ArrayList;

import cryptoTrader.processing.CheckCoinsListFacade;
import cryptoTrader.utils.TradeResult;
import cryptoTrader.utils.TradingBroker;

import java.util.Date;  
import java.text.SimpleDateFormat; 
	
/**
 * defines TradingStrategyE
 * @author Ian Guenther Green
 */

public class TradingStrategyE implements TradingStrategy{
	
	private String strategyName = "Strategy-E";
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
	public TradingStrategyE(TradingBroker broker) {
		this.tradingBroker = broker.getTradingBrokerName();
		this.coinsList = broker.getCoinsList();
		this.coinsPriceList = broker.getCoinsPriceList();
		
		requiredCoins.add("SOL");
		requiredCoins.add("USDC");
		requiredCoins.add("HNT");
		
		date = new Date();		
		dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
		
	}
	
	/**
	 * method called to perform trade with specified strategy
	 * @return TradeResult object containing trades made
	 */
	@Override
	public TradeResult performTrade() {
		
		// rule: if price of SOL (solana) <= 168 or USDC (usd-coin) >= 1 buy $1000 worth of HNT (helium)
		// if not, sell 600 SOL
		
		CheckCoinsListFacade checker = new CheckCoinsListFacade(requiredCoins, coinsList);
		TradeResult result;
		
		float quantity;
		
		// if required coins are not in coins entered, trade fails
		if (!checker.checkCoinsList()) {
			return result = new TradeResult(tradingBroker, strategyName, "Fail", "Fail", 0f, 0d, dateFormat.format(date));
		}

		if((coinsPriceList[coinsList.indexOf("SOL")] <= 168) || (coinsPriceList[coinsList.indexOf("USDC")] >= 1)) {
			
			quantity = (float) (1000/coinsPriceList[coinsList.indexOf("HNT")]);
			return result = new TradeResult(tradingBroker, strategyName, "HNT", "Buy", quantity, coinsPriceList[coinsList.indexOf("HNT")], dateFormat.format(date));
			
		}
		
		return result = new TradeResult(tradingBroker, strategyName, "SOL", "Sell", 600, coinsPriceList[coinsList.indexOf("SOL")], dateFormat.format(date));
		
	}
	
}