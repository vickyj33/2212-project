package cryptoTrader.coins;

import java.util.*;

import cryptoTrader.utils.DataFetcher;

/**
 * Class that stores the cryptocoins that the user is allowed to use as well as the ones that have been inputted
 * Used as a Facade to get the prices of entered coins using the DataFetcher from the CoinGecko API
 * 
 * @author Ian Guenther Green
 * @author Vicky Jiang
 *
 */

public class CryptoCoins {
	Hashtable<String, String> cryptoIdNameDict; 
	Hashtable<String, Double> cryptoCoinsList;
	private static CryptoCoins instance = null;
	
	/*
	 * class constructor for the singleton class CryptoCoins
	 */
	private CryptoCoins() {
		cryptoIdNameDict = new Hashtable<String, String>();
		
		// Initializes id, name pairs for all the crypto coins that can be used
		cryptoIdNameDict.put("BTC", "bitcoin");
		cryptoIdNameDict.put("ETH", "ethereum");
		cryptoIdNameDict.put("USDT", "tether");
		cryptoIdNameDict.put("USDC", "usd-coin");
		cryptoIdNameDict.put("ADA", "cardano");
		cryptoIdNameDict.put("SOL", "solana");
		cryptoIdNameDict.put("ONE", "harmony");
		cryptoIdNameDict.put("MANA", "decentraland");
		cryptoIdNameDict.put("AVAX", "avalanche-2");
		cryptoIdNameDict.put("LUNA", "terra-luna");
		cryptoIdNameDict.put("FTM", "fantom");
		cryptoIdNameDict.put("HNT", "helium");
		cryptoIdNameDict.put("DOGE", "dogecoin");
		
		// create dictionary for cryptoCoins that user has entered
		cryptoCoinsList = new Hashtable<String, Double>();
	}
	
	/**
	 * getter class that returns the one instance of the CryptoCoins class
	 * @return instance of CryptoCoins class
	 */
	public static CryptoCoins getInstance() {
		if(instance == null)
			instance = new CryptoCoins();
		return instance;
	}
	
	/**
	 * function that adds valid cryto coins in coins to cryptoCoinsList
	 * dictionary is used so a coin will only ever have one occurrence
	 * @param coins - String array of cryptoCoins
	 * @return true if coins successfully put into dictionary, false if not
	 */
	public boolean putCoin(String[] coins) {
		for (int i = 0; i < coins.length; i++) {
			if(!cryptoIdNameDict.containsKey(coins[i])) {
				System.out.println("invalid coin " + coins[i]);
				return false;					
			}
				
			cryptoCoinsList.put(coins[i], 0d);
			
		}
		
		return true;
		
	}
	
	/**
	 * function that uses DataFetcher to get prices for the coins in CryptoCoinsList
	 * utilizes facade design patter to get prices for coins using CryptoCoins rather than directly from MainUI client
	 */
	public void getPrices() {
		DataFetcher fetcher = new DataFetcher();
		
		String[] coinNames = new String[cryptoCoinsList.size()];
		
		Enumeration<String> key = cryptoCoinsList.keys();
		
		for (int i = 0; i < coinNames.length; i++)
			coinNames[i] = 	cryptoIdNameDict.get(key.nextElement());;
						
		double[] prices = fetcher.getPricesForCoins(coinNames);
		key = cryptoCoinsList.keys();
		
		for (int i = 0; i < coinNames.length; i++)
			cryptoCoinsList.put(key.nextElement(), prices[i]);
		
	}
	
	/**
	 * receives a String array of coin names and returns a double array with the prices of those coins
	 * @param coinNames - String array with the prices of needed coins
	 * @return coinsPriceList - double array with coins prices
	 */
	public double[] getPriceList(String[] coinNames) {
		double[] coinPriceList = new double[coinNames.length];
		
		for (int i = 0; i < coinNames.length; i++)
			coinPriceList[i] = cryptoCoinsList.get(coinNames[i]);
		
		return coinPriceList;
		
	}
	
	/**
	 * prints coins that have been entered into system (stored in cryptoCoinsList)
	 */
	public void printCoinsEntered() {
		for (Enumeration<String> i = cryptoCoinsList.keys(); i.hasMoreElements();) {
            System.out.println("Value in Dictionary : " + i.nextElement());
        }
		
	}
	
	/**
	 * prints the coins and their prices for the coins that have been entered into system (stored in cryptoCoinsList)
	 */
	public void printCoinPrices() {
		Enumeration<String> key = cryptoCoinsList.keys();
		Enumeration<Double> element = cryptoCoinsList.elements();
		
		while(key.hasMoreElements()) {
			System.out.println("Coin: " + key.nextElement() + " Price: " + element.nextElement());
		}
		
	}
	
}
