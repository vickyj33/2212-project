package cryptoTrader.tradingStrategies;

import java.util.ArrayList;
import java.util.List;

import cryptoTrader.processing.CheckCoinsListFacade;
import cryptoTrader.utils.TradeResult;
import cryptoTrader.utils.UserSelections;

import java.util.Date;
import java.text.SimpleDateFormat; 

public class TradingStrategyB implements TradingStrategy{
	
	private String strategyName = "Strategy-B";
	private String tradingBroker;
	private ArrayList<String> coinsList;
	private double[] coinsPriceList;
	private List<String> requiredCoins;
			
	Date date;
	SimpleDateFormat dateFormat;
	
	public TradingStrategyB(UserSelections selection) {
		this.tradingBroker = selection.getTradingBroker();
		coinsList = new ArrayList<>();
		this.coinsPriceList = selection.getCoinsPriceList();
		requiredCoins = new ArrayList<>();
		
		for(String coin:selection.getCoinsList()) 
			coinsList.add(coin);
		
		requiredCoins.add("ETH");
		requiredCoins.add("USDT");
		requiredCoins.add("ADA");
		
		date = new Date();		
		dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
		
	}
	
	@Override
	public TradeResult performTrade() {
		
		// rule: if price of ETH (ethereum) <= 4500 or USDT (tether) > 2 buy $1000 worth of USDT
		// if not, sell $800 worth of ADA
		
		
		CheckCoinsListFacade checker = new CheckCoinsListFacade(requiredCoins, coinsList);
		TradeResult result;
		
		float quantity;
		
		if (!checker.checkCoinsList()) {
			return result = new TradeResult(tradingBroker, strategyName, "Fail", "", 0f, 0d, dateFormat.format(date));
		}

		if((coinsPriceList[coinsList.indexOf("ETH")] <= 4500) || (coinsPriceList[coinsList.indexOf("USDT")] > 2)) {
			
			quantity = (float) (1000/coinsPriceList[coinsList.indexOf("USDT")]);
			return result = new TradeResult(tradingBroker, strategyName, "USDT", "Buy", quantity, coinsPriceList[coinsList.indexOf("USDT")], dateFormat.format(date));
			
		}
		
		quantity = (float) (800/coinsPriceList[coinsList.indexOf("ADA")]);
		return result = new TradeResult(tradingBroker, strategyName, "ADA", "Sell", quantity, coinsPriceList[coinsList.indexOf("ADA")], dateFormat.format(date));
		
	}
	
}