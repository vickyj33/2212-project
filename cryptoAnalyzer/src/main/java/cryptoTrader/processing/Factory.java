package cryptoTrader.processing;

import cryptoTrader.tradingStrategies.*;
import cryptoTrader.utils.UserSelections;

public class Factory {

	public TradingStrategy create(UserSelections selection) {
		
		switch(selection.getStrategy()) {
			case "Strategy-A":
				return new TradingStrategyA(selection);
			case "Strategy-B":
				return new TradingStrategyB(selection);
			case "Strategy-C":
				return new TradingStrategyC(selection);
			case "Strategy-D":
				return new TradingStrategyD(selection);
			case "Strategy-E":
				return new TradingStrategyE(selection);
			default:
				return null;
			
		}

	}
	
}
