package cryptoTrader.tradingStrategies;

import cryptoTrader.utils.TradeResult;

public interface TradingStrategy {
	
	// will perform "rule" for specific trading strategy
	/* TradeResult will hold the information to be displayed in the Table and histogram, coinList is a list of coin names 
	e.g [ADA, ETH, BTC] and coinPriceList the unit prices of the coins in the coinList (e.g. [1.5, 3,500, 55,000] indicating 
	that the unit price of ADA is 1.5$, of ETH is 3,500$, and BTC’s 55,000$) , the action (e.g. Buy or Sell), the quantity traded, and the time-stamp. */
	TradeResult performTrade();
	
	
}