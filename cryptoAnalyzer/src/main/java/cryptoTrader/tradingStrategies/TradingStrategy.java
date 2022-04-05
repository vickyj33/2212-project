package cryptoTrader.tradingStrategies;

import cryptoTrader.utils.TradeResult;

/**
 * Interface for Trading Strategy classes
 * @author Ian Guenther Green
 *
 */

public interface TradingStrategy {
	
	/**
	 *  will perform "rule" for specific trading strategy
	 * @return TradeResult object containing trades made
	 */
	TradeResult performTrade();
	
}