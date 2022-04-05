package cryptoTrader.processing;

import cryptoTrader.tradingStrategies.*;
import cryptoTrader.utils.TradingBroker;

/**
 * This class takes in a broker and returns the an object of type TradingStrategy based on what strategy the broker want to use 
 * Implements the factory design pattern
 * @author Ian Guenther Green
 * @author VIcky Jiang
 * 
 */

public class Factory {
	
	/**
	 * Determines what strategy broker wants to use and creates a new object of that strategy type
	 * @param broker is the current trading broker
	 * @return object of type TradingStrategy
	 */
	public TradingStrategy create(TradingBroker broker) {
		
		switch(broker.getStrategy()) {
			case "Strategy-A":
				return new TradingStrategyA(broker);
			case "Strategy-B":
				return new TradingStrategyB(broker);
			case "Strategy-C":
				return new TradingStrategyC(broker);
			case "Strategy-D":
				return new TradingStrategyD(broker);
			case "Strategy-E":
				return new TradingStrategyE(broker);
			default:
				return null;
			
		}

	}
	
}
