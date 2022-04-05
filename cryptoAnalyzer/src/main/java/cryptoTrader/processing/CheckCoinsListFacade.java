package cryptoTrader.processing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class checks if the entered coins match the needed coins for a strategy
 * 
 * @author Ian Guenther Green
 * @author Vicky Jiang
 * 
 */

public class CheckCoinsListFacade {
	private List<String> requiredList = new ArrayList<>();
	private List<String> inputList = new ArrayList<>();
	
	/**
	 * Class constructor, used to assign instance variables
	 * @param expectedCoins
	 * @param inputCoins
	 */
	public CheckCoinsListFacade(Collection<? extends String> expectedCoins, Collection<? extends String> inputCoins) {
		requiredList.addAll(expectedCoins);
		inputList.addAll(inputCoins);
	}
	
	/**
	 * Method to check if the needed coins for a strategy are in the inputted coins
	 * @return true if needed coins are in entered coins and false otherwise
	 */
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