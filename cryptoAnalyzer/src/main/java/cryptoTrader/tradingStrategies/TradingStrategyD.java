package cryptoTrader.tradingStrategies;

import java.util.ArrayList;
import java.util.List;

import cryptoTrader.processing.CheckCoinsListFacade;
import cryptoTrader.utils.TradeResult;
import cryptoTrader.utils.UserSelections;

import java.util.Date;  
import java.text.SimpleDateFormat; 
		
		// rule 4: DOGE
		
		// rule 5: SOL

public class TradingStrategyD implements TradingStrategy{
	
	private String strategyName = "Strategy-D";
	private String tradingBroker;
	private ArrayList<String> coinsList;
	private double[] coinsPriceList;
	private List<String> requiredCoins;
			
	Date date;
	SimpleDateFormat dateFormat;
	
	public TradingStrategyD(UserSelections selection) {
		this.tradingBroker = selection.getTradingBroker();
		coinsList = new ArrayList<>();
		this.coinsPriceList = selection.getCoinsPriceList();
		requiredCoins = new ArrayList<>();
		
		
		for(String coin:selection.getCoinsList()) 
			coinsList.add(coin);
		
		requiredCoins.add("DOGE");
		requiredCoins.add("BTC");
		requiredCoins.add("MANA");
		
		date = new Date();		
		dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
		
	}
	
	@Override
	public TradeResult performTrade() {
		
		// rule: if price of DOGE (dogecoin) < 0.2 and BTC (bitcoin) >= 57000 sell 750 DOGE 
		// if not, buy $900 worth of MANA
		
		CheckCoinsListFacade checker = new CheckCoinsListFacade(requiredCoins, coinsList);
		TradeResult result;
		
		float quantity;
		
		if (!checker.checkCoinsList()) {
			return result = new TradeResult(tradingBroker, strategyName, "Fail", "", 0f, 0d, dateFormat.format(date));
		}

		if((coinsPriceList[coinsList.indexOf("DOGE")] < 0.2) && (coinsPriceList[coinsList.indexOf("BTC")] >= 57000)) {
			
			return result = new TradeResult(tradingBroker, strategyName, "DOGE", "Sell", 750, coinsPriceList[coinsList.indexOf("DOGE")], dateFormat.format(date));
			
		}
		
		quantity = (float) (900/coinsPriceList[coinsList.indexOf("MANA")]);
		return result = new TradeResult(tradingBroker, strategyName, "MANA", "Buy", quantity, coinsPriceList[coinsList.indexOf("MANA")], dateFormat.format(date));
		
	}
	
}