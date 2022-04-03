package cryptoTrader.coins;

import java.util.*;

import cryptoTrader.utils.DataFetcher;

public class CryptoCoins {
	Hashtable<String, String> cryptoIdNameDict; 
	Hashtable<String, Double> cryptoCoinsList;
	private static CryptoCoins instance = null;
	
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
	
	public static CryptoCoins getInstance() {
		if(instance == null)
			instance = new CryptoCoins();
		return instance;
	}
	
	
	public void putCoin(String[] coins) {
		for (int i = 0; i < coins.length; i++) {
			cryptoCoinsList.put(coins[i], 0d);
			//System.out.print(coins[i]);
			
		}
		
	}
	
	public void getPrices() {
		DataFetcher fetcher = new DataFetcher();
		
		String[] coinNames = new String[cryptoCoinsList.size()];
		
		Enumeration<String> key = cryptoCoinsList.keys();
		
		for (int i = 0; i < coinNames.length; i++)
			coinNames[i] = 	cryptoIdNameDict.get(key.nextElement());;
			
		// maybe add in error checking for if correct coins are entered
			
		double[] prices = fetcher.getPricesForCoins(coinNames);
		key = cryptoCoinsList.keys();
		
		for (int i = 0; i < coinNames.length; i++)
			cryptoCoinsList.put(key.nextElement(), prices[i]);
		
	}
	
	public double[] getPriceList(String[] coinNames) {
		double[] coinPriceList = new double[coinNames.length];
		
		for (int i = 0; i < coinNames.length; i++)
			coinPriceList[i] = cryptoCoinsList.get(coinNames[i]);
		
		return coinPriceList;
		
	}
	
	public void printCoinsEntered() {
		for (Enumeration<String> i = cryptoCoinsList.keys(); i.hasMoreElements();) {
            System.out.println("Value in Dictionary : " + i.nextElement());
        }
		
	}
	
	public void printCoinPrices() {
		Enumeration<String> key = cryptoCoinsList.keys();
		Enumeration<Double> element = cryptoCoinsList.elements();
		
		while(key.hasMoreElements()) {
			System.out.println("Coin: " + key.nextElement() + " Price: " + element.nextElement());
		}
		
	}
	
	
}
