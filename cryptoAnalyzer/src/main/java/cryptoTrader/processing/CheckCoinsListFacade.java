package cryptoTrader.processing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CheckCoinsListFacade {
	private List<String> requiredList = new ArrayList<>();
	private List<String> inputList = new ArrayList<>();
	
	public CheckCoinsListFacade(Collection<? extends String> expectedCoins, Collection<? extends String> inputCoins) {
		requiredList.addAll(expectedCoins);
		inputList.addAll(inputCoins);
	}
	
	public boolean checkCoinsList() {
		// loops through the coins in the required list
		 for(String coin:requiredList) {
			 // if the required coins are not in the inputList, return false
			 if(!inputList.contains(coin))
				 return false;
		 }
		 
		 // return true if all coins in requiredList are in inputList
		 return true;
			 
	}
	
}