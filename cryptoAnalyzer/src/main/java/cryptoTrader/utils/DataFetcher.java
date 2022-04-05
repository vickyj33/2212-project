package cryptoTrader.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * class that fetches prices for cryptocoins from the CoinGecko API
 * adapted from code given in the cs2212 class for the trader project
 * 
 * @author CS2212
 * @author Ian Guenther Green
 * @author Vicky Jiang
 *
 */

public class DataFetcher {

	/**
	 * Method to get all the information for a cryptocoin from the coinGecko API at a specific date
	 * 
	 * @param id of the coin to get data for
	 * @param date to get data from
	 * @return JsonObject with data about coin
	 */
	
	private JsonObject getDataForCrypto(String id, String date) {

		String urlString = String.format(
				"https://api.coingecko.com/api/v3/coins/%s/history?date=%s", id, date);
		
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if (responsecode == 200) {
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
				JsonObject jsonObject = new JsonParser().parse(inline).getAsJsonObject();
				return jsonObject;
			}

		} catch (IOException e) {
			System.out.println("Something went wrong with the API call.");
		}
		return null;
	}
	
	/**
	 * Method to get all the prices for multiple cryptocoins from the coinGecko API at the current date in cad
	 * 
	 * @param ids[] String array containing ids to get data for
	 * @return JsonObject with data about coins
	 */
	
	private JsonObject getDataForMultipleCryptos(String[] ids) {
		
		// creates urlString using all crypto ids for single https request
		// single call as that is what was specified in the project
		String urlString = "https://api.coingecko.com/api/v3/simple/price?ids=";
		
		for (int i = 0; i < ids.length; i++) {
			urlString = urlString.concat(ids[i]);
			urlString = urlString.concat("%2C");
		}

		urlString = urlString.concat("&vs_currencies=cad");
		
		//System.out.println(urlString);
		
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if (responsecode == 200) {
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
				JsonObject jsonObject = new JsonParser().parse(inline).getAsJsonObject();
				
				return jsonObject;	
				
			}

		} catch (IOException e) {
			System.out.println("Something went wrong with the API call.");
		}
		return null;
	}
	
	/**
	 * gets the prices for multiple coins and returns them in a double array
	 * index of the prices match the indexes of the names of the respective coin
	 * @param ids String array containing ids to get prices for
	 * @return double array containing the prices for the input coins 
	 */
	public double[] getPricesForCoins(String ids[]) {
		double[] prices = new double[ids.length];
		
		JsonObject jsonObject = getDataForMultipleCryptos(ids);
		if (jsonObject != null) {
			
			for(int i = 0; i < ids.length; i++) {
				JsonObject coin = jsonObject.get(ids[i]).getAsJsonObject();
				prices[i] = coin.get("cad").getAsDouble();
				
				//System.out.println(ids[i] + ": " + prices[i]);
				
			}
			
		}
		
		return prices;
		
	}
	
	/**
	 * gets the price for a coin
	 * @param id of coin
	 * @param date to get price from
	 * @return price of coin in double variable
	 */
	public double getPriceForCoin(String id, String date) {
		double price = 0.0;
		
		JsonObject jsonObject = getDataForCrypto(id, date);
		if (jsonObject != null) {
			JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
			JsonObject currentPrice = marketData.get("current_price").getAsJsonObject();
			price = currentPrice.get("cad").getAsDouble();
		}
		
		return price;
	}
	
}
