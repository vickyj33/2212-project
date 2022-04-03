package cryptoTrader.tradingStrategies;

import java.util.ArrayList;
import java.util.List;

import cryptoTrader.processing.CheckCoinsListFacade;
import cryptoTrader.utils.TradeResult;
import cryptoTrader.utils.UserSelections;

import java.util.Date;  
import java.text.SimpleDateFormat; 

public class TradingStrategyA implements TradingStrategy{
	
	private String strategyName = "Strategy-A";
	private String tradingBroker;
	private ArrayList<String> coinsList;
	private double[] coinsPriceList;
	private List<String> requiredCoins;
			
	Date date;
	SimpleDateFormat dateFormat;
	
	public TradingStrategyA(UserSelections selection) {
		this.tradingBroker = selection.getTradingBroker();
		coinsList = new ArrayList<>();
		this.coinsPriceList = selection.getCoinsPriceList();
		requiredCoins = new ArrayList<>();
		
		
		for(String coin:selection.getCoinsList()) 
			coinsList.add(coin);
		
		requiredCoins.add("BTC");
		requiredCoins.add("ADA");
		requiredCoins.add("ETH");
		
		date = new Date();		
		dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
		
	}
	
	@Override
	public TradeResult performTrade() {
		
		// rule: if BTC (bitcoin) > $55000 and ADA (cardano) < $2, buy 2 BTC
		// if not, sell 600 ETH (ethereum)
		
		CheckCoinsListFacade checker = new CheckCoinsListFacade(requiredCoins, coinsList);
		TradeResult result;
		
		if (!checker.checkCoinsList()) {
			return result = new TradeResult(tradingBroker, strategyName, "Fail", "", 0f, 0d, dateFormat.format(date));
		}

		if((coinsPriceList[coinsList.indexOf("BTC")] > 55000) && (coinsPriceList[coinsList.indexOf("ADA")] < 2)) {
			return result = new TradeResult(tradingBroker, strategyName, "BTC", "Buy", 2, coinsPriceList[coinsList.indexOf("BTC")], dateFormat.format(date));
		}
		
		return result = new TradeResult(tradingBroker, strategyName, "ETH", "Sell", 600, coinsPriceList[coinsList.indexOf("ETH")], dateFormat.format(date));
		
	}
	
}
