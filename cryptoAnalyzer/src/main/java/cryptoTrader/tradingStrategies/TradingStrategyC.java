package cryptoTrader.tradingStrategies;

import java.util.ArrayList;
import java.util.List;

import cryptoTrader.processing.CheckCoinsListFacade;
import cryptoTrader.utils.TradeResult;
import cryptoTrader.utils.UserSelections;

import java.util.Date;  
import java.text.SimpleDateFormat; 

public class TradingStrategyC implements TradingStrategy{
	
	private String strategyName = "Strategy-C";
	private String tradingBroker;
	private ArrayList<String> coinsList;
	private double[] coinsPriceList;
	private List<String> requiredCoins;
			
	Date date;
	SimpleDateFormat dateFormat;
	
	public TradingStrategyC(UserSelections selection) {
		this.tradingBroker = selection.getTradingBroker();
		coinsList = new ArrayList<>();
		this.coinsPriceList = selection.getCoinsPriceList();
		requiredCoins = new ArrayList<>();
		
		
		for(String coin:selection.getCoinsList()) 
			coinsList.add(coin);
		
		requiredCoins.add("FTM");
		requiredCoins.add("ONE");
		
		date = new Date();		
		dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
		
	}
	
	@Override
	public TradeResult performTrade() {
		
		// rule: if price of FTM (fantom) > 2 and ONE (harmony) <= 1 buy 400 FTM
		// if not, sell $500 worth of ONE		
		
		CheckCoinsListFacade checker = new CheckCoinsListFacade(requiredCoins, coinsList);
		TradeResult result;
		
		float quantity;
		
		if (!checker.checkCoinsList()) {
			return result = new TradeResult(tradingBroker, strategyName, "Fail", "", 0f, 0d, dateFormat.format(date));
		}

		if((coinsPriceList[coinsList.indexOf("FTM")] > 2) && (coinsPriceList[coinsList.indexOf("ONE")] <= 1)) {
			
			return result = new TradeResult(tradingBroker, strategyName, "FTM", "Buy", 400, coinsPriceList[coinsList.indexOf("FTM")], dateFormat.format(date));
			
		}
		
		quantity = (float) (500/coinsPriceList[coinsList.indexOf("ONE")]);
		return result = new TradeResult(tradingBroker, strategyName, "ONE", "Sell", quantity, coinsPriceList[coinsList.indexOf("ONE")], dateFormat.format(date));
		
	}
	
}