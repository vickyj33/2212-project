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

public class DataFetcher {

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
	
	private JsonObject getDataForMultipleCryptos(String[] ids) {
		
		// creates urlString using all crypto ids for single https request
		String urlString = "https://api.coingecko.com/api/v3/simple/price?ids=";
		
		for (int i = 0; i < ids.length; i++) {
			urlString = urlString.concat(ids[i]);
			urlString = urlString.concat("%2C");
		}

		urlString = urlString.concat("&vs_currencies=cad");
		
		System.out.println(urlString);
		
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
	
	public double[] getPricesForCoins(String ids[]) {
		double[] prices = new double[ids.length];
		
		JsonObject jsonObject = getDataForMultipleCryptos(ids);
		if (jsonObject != null) {
			
			for(int i = 0; i < ids.length; i++) {
				JsonObject coin = jsonObject.get(ids[i]).getAsJsonObject();
				prices[i] = coin.get("cad").getAsDouble();
				
				System.out.println(ids[i] + ": " + prices[i]);	//remove later
				
			}
			
		}
		
		return prices;
		
	}
	
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
	
	public double getMarketCapForCoin(String id, String date) {
		double marketCap = 0.0;
		
		JsonObject jsonObject = getDataForCrypto(id, date);
		if (jsonObject != null) {
			JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
			JsonObject currentPrice = marketData.get("market_cap").getAsJsonObject();
			marketCap = currentPrice.get("cad").getAsDouble();
		}
		
		return marketCap;
	}
	
	public double getVolumeForCoin(String id, String date) {
		double volume = 0.0;
		
		JsonObject jsonObject = getDataForCrypto(id, date);
		if (jsonObject != null) {
			JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
			JsonObject currentPrice = marketData.get("total_volume").getAsJsonObject();
			volume = currentPrice.get("cad").getAsDouble();
		}
		
		return volume;
	}
	
	public static void main(String[] args) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");		
		LocalDateTime now = LocalDateTime.now();
		
		DataFetcher fetcher = new DataFetcher();
		double price = fetcher.getPriceForCoin("bitcoin", dtf.format(now));	//dd-mm-yyyy
		double marketCap = fetcher.getMarketCapForCoin("bitcoin", dtf.format(now));
		double volume = fetcher.getVolumeForCoin("bitcoin", dtf.format(now));
		
		String[] coinNames = new String[] {"bitcoin", "tether", "ethereum", "cardano", "dogecoin", "usd-coin", "tether", "avalanche-2"};
		double[] prices = fetcher.getPricesForCoins(coinNames);
		
		for(int i = 0; i < prices.length; i++)
			System.out.println(coinNames[i] + prices[i]);
		/*
		System.out.println("Bitcoin=>\tPrice: " + price + 
								"\n\t\tMarket Cap: " + marketCap + 
								"\n\t\tVolume: "+volume);*/
		
	}
}
