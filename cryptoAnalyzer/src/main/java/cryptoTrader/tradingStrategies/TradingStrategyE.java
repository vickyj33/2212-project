package cryptoTrader.tradingStrategies;

import java.util.ArrayList;
import java.util.List;

import cryptoTrader.processing.CheckCoinsListFacade;
import cryptoTrader.utils.TradeResult;
import cryptoTrader.utils.UserSelections;

import java.util.Date;  
import java.text.SimpleDateFormat; 
		
public class TradingStrategyE implements TradingStrategy{
	
	private String strategyName = "Strategy-E";
	private String tradingBroker;
	private ArrayList<String> coinsList;
	private double[] coinsPriceList;
	private List<String> requiredCoins;
			
	Date date;
	SimpleDateFormat dateFormat;
	
	public TradingStrategyE(UserSelections selection) {
		this.tradingBroker = selection.getTradingBroker();
		coinsList = new ArrayList<>();
		this.coinsPriceList = selection.getCoinsPriceList();
		requiredCoins = new ArrayList<>();
		
		
		for(String coin:selection.getCoinsList()) 
			coinsList.add(coin);
		
		requiredCoins.add("SOL");
		requiredCoins.add("USDC");
		requiredCoins.add("HNT");
		
		date = new Date();		
		dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
		
	}
	
	@Override
	public TradeResult performTrade() {
		
		// rule: if price of SOL (solana) <= 168 or USDC (usd-coin) >= 1 buy $1000 worth of HNT (helium)
		// if not, sell 600 SOL
		
		CheckCoinsListFacade checker = new CheckCoinsListFacade(requiredCoins, coinsList);
		TradeResult result;
		
		float quantity;
		
		if (!checker.checkCoinsList()) {
			return result = new TradeResult(tradingBroker, strategyName, "Fail", "", 0f, 0d, dateFormat.format(date));
		}

		if((coinsPriceList[coinsList.indexOf("SOL")] <= 168) || (coinsPriceList[coinsList.indexOf("USDC")] >= 1)) {
			
			quantity = (float) (1000/coinsPriceList[coinsList.indexOf("HNT")]);
			return result = new TradeResult(tradingBroker, strategyName, "HNT", "Buy", quantity, coinsPriceList[coinsList.indexOf("HNT")], dateFormat.format(date));
			
		}
		
		return result = new TradeResult(tradingBroker, strategyName, "SOL", "Sell", 600, coinsPriceList[coinsList.indexOf("SOL")], dateFormat.format(date));
		
	}
	
}