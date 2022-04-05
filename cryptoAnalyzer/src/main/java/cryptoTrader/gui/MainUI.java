package cryptoTrader.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import cryptoTrader.utils.UserSelections;
import cryptoTrader.utils.TradingBroker;
import cryptoTrader.utils.ChartData;
import cryptoTrader.utils.DataVisualizationCreator;
import cryptoTrader.utils.TradeResult;
import cryptoTrader.processing.*;
import cryptoTrader.tradingStrategies.TradingStrategy;
import cryptoTrader.coins.*;

/** 
 * CS 2212 Project
 * The MainUI class represents the main window of the cryptoTrader program.
 * Users can fill in the name of a trading broker, crypto coins the broker is interested in, and what trading strategy to use.
 * The prices for the cryptocurrencies are then fetched from the CoinGecko API.
 * Using these coins and their prices, the trading brokers perform the trading strategies.
 * The results for the trades as well as what strategies each broker used are output in a table and a histogram, respectively.
 * Adapted from code provided for CS2212 project
 * @author Ian Guenther Green
 * @author Vicky Jiang
 * @author Ali Mohamed
 * @author Hala Elewa
 * @author CS2212
 * 
 */

public class MainUI extends JFrame implements ActionListener {
	
	/* Declare instance variables */
	
	private static final long serialVersionUID = 1L;

	// instances for the main UI window
	private static MainUI instance;
	
	// declare JPanel object that will hold table and histogram
	private JPanel stats;

	// declare swing objects to access input data
	private DefaultTableModel dtm;
	private JTable table;
	
	// declare object to store data for the chart to be displayed
	private ChartData chartData;
	
	// List to store UserSelections (lines within the input table)
	private List<UserSelections> rowSelections = new ArrayList<UserSelections>();
	
	// List to store currently being used brokers and all brokers in the system
	private List<TradingBroker> currentBrokerList = new ArrayList<>();
	private List<TradingBroker> totalBrokerList = new ArrayList<>();
	
	// List to store results from trades
	private List<TradeResult> listOfTradeResults;
		
	/**
	 * Getter method for instance of MainUI class
	 * Utilizes Singleton design pattern
	 * @return MainUI instance
	*/
	public static MainUI getInstance() {
		if (instance == null)
			instance = new MainUI();

		return instance;
	}

	/**
	 * Constructor for the MainUI class. It is private to ensure only one instance of MainUI can be created (Singleton)
	 */
	private MainUI() {

		// Set window title
		super("Crypto Trading Tool");

		// Set top bar
		JPanel north = new JPanel();
		
		// creates new array list of trade results
		listOfTradeResults = new ArrayList<TradeResult>();
		
		JButton trade = new JButton("Perform Trade");
		trade.setActionCommand("refresh");
		trade.addActionListener(this);

		JPanel south = new JPanel();
		
		south.add(trade);
		
		// setup for UI
		dtm = new DefaultTableModel(new Object[] { "Trading Client", "Coin List", "Strategy Name" }, 1);
		table = new JTable(dtm);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Trading Client Actions",
				TitledBorder.CENTER, TitledBorder.TOP));
		Vector<String> strategyNames = new Vector<String>();
		strategyNames.add("Strategy-A");
		strategyNames.add("Strategy-B");
		strategyNames.add("Strategy-C");
		strategyNames.add("Strategy-D");
		strategyNames.add("Strategy-E");
		TableColumn strategyColumn = table.getColumnModel().getColumn(2);
		JComboBox comboBox = new JComboBox(strategyNames);
		strategyColumn.setCellEditor(new DefaultCellEditor(comboBox));
		JButton addRow = new JButton("Add Row");
		JButton remRow = new JButton("Remove Row");
		addRow.setActionCommand("addTableRow");
		addRow.addActionListener(this);
		remRow.setActionCommand("remTableRow");
		remRow.addActionListener(this);

		scrollPane.setPreferredSize(new Dimension(800, 300));
		table.setFillsViewportHeight(true);
		

		JPanel east = new JPanel();
		east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
		east.add(scrollPane);
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		buttons.add(addRow);
		buttons.add(remRow);
		east.add(buttons);

		// Set charts region
		JPanel west = new JPanel();
		west.setPreferredSize(new Dimension(1250, 650));
		stats = new JPanel();
		stats.setLayout(new GridLayout(2, 2));

		west.add(stats);

		getContentPane().add(north, BorderLayout.NORTH);
		getContentPane().add(east, BorderLayout.EAST);
		getContentPane().add(west, BorderLayout.CENTER);
		getContentPane().add(south, BorderLayout.SOUTH);

	}
	
	/**
	 * This function updates the stats JPanel
	 * DataVisualizationCreator will call this method to update the charts on the user interface.
	 * @param component - Java swing component of type JComponent referencing a single type of chart
	 */
	public void updateStats(JComponent component) {
		stats.add(component);
		stats.revalidate();
	}
	
	/**
	 * This method is called when the Perform Trade button is pressed
	 * Collects data entered in the table and performs trades based on trading strategies
	 * @param e - an event of type ActionEvent
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		// if the Perform Trade button has been pressed
		if ("refresh".equals(command)) {
			
			// checks each row in input table to ensure all cells have been filled
			// outputs appropriate error message and returns if not
			for (int count = 0; count < dtm.getRowCount(); count++){
				Object traderObject = dtm.getValueAt(count, 0);
				if (traderObject == null) {
					JOptionPane.showMessageDialog(this, "please fill in Trader name on line " + (count + 1) );
					return;
				}

				Object coinObject = dtm.getValueAt(count, 1);
				if (coinObject == null) {
					JOptionPane.showMessageDialog(this, "please fill in cryptocoin list on line " + (count + 1) );
					return;
				}

				Object strategyObject = dtm.getValueAt(count, 2);
				if (strategyObject == null) {
					JOptionPane.showMessageDialog(this, "please fill in strategy name on line " + (count + 1) );
					return;
				}
										
	        }
			
			/* all rows have been filled in correctly so we can fill in rowSelections array */
			
			// empties prior rowSelections from previous trades
			rowSelections.clear();	

			// empties current brokers from previous trades
			currentBrokerList.clear();
			
			// creates a list for all coins entered
			CryptoCoins coinsList = CryptoCoins.getInstance();
			
			
			// loops through each row, adding UserSelections (brokerName, coinsList, Strategy) to rowSelections
			for (int count = 0; count < dtm.getRowCount(); count++) {
				UserSelections newRow = new UserSelections(dtm.getValueAt(count, 0).toString(), dtm.getValueAt(count, 1).toString().replaceAll(" ", "").split(","), dtm.getValueAt(count, 2).toString());
				rowSelections.add(newRow);
				
				// adds coins to the coinList and check if invalid coins have been entered
				if(!coinsList.putCoin(dtm.getValueAt(count, 1).toString().replaceAll(" ", "").split(","))) {
					JOptionPane.showMessageDialog(this, "invalid coin name entered on line" + (count + 1));
					return;
				}
							
				
			}
			
			// printRowSelections(); // comment in to see rowSelections
			// coinsList.printCoinsEntered(); // comment in to see coins that have been entered
			
			// computes prices for all the coins that have been entered
			coinsList.getPrices();

			// coinsList.printCoinPrices(); // comment in to see coins w/ their prices
			
			
			/* Explanation for how brokers work */
			// two lists totalBrokerList and currentBrokerList
			// total brokers contains all the brokers ever to be used and current brokers contains the one currently on the table
			
			// clear current brokers
			// look at the brokers in the table
			// if a broker in the table is in total brokers update the broker object and add it to current brokers
			// else create a new broker and add it to both lists
			
			for (int i = 0; i < rowSelections.size(); i++) {
				
				if(!totalBrokerList.isEmpty()) {
					int j = 0;
					
					// loops through the list of total brokers to see if the current row selection broker exists as a broker
					while((j < totalBrokerList.size()) && !(totalBrokerList.get(j).getTradingBrokerName().equals(rowSelections.get(i).getTradingBroker()))) {
						j++;
					}
					
					// if it does, update this broker with the new coins entered, their prices and trading strategy to be used and add it to current brokers
					if ((j != totalBrokerList.size()) && (totalBrokerList.get(j).getTradingBrokerName().equals(rowSelections.get(i).getTradingBroker()))) {			
						totalBrokerList.get(j).setCoinsList(rowSelections.get(i).getCoinsList());
						totalBrokerList.get(j).setCoinsPriceList(coinsList.getPriceList(rowSelections.get(i).getCoinsList()));
						totalBrokerList.get(j).setStrategy(rowSelections.get(i).getStrategy());
						
						// if broker is already in currentBrokers it cannot be used multiple times at once
						if(currentBrokerList.contains(totalBrokerList.get(j))) {
							JOptionPane.showMessageDialog(this, "broker can be used only once per entry");
							return;
						}
						
						currentBrokerList.add(totalBrokerList.get(j));
					}
					// if it does not, create a new broker and add it to the list of total brokers and list of current brokers
					else {
						TradingBroker broker = new TradingBroker(rowSelections.get(i).getTradingBroker(), rowSelections.get(i).getCoinsList(), coinsList.getPriceList(rowSelections.get(i).getCoinsList()), rowSelections.get(i).getStrategy());
						currentBrokerList.add(broker);
						totalBrokerList.add(broker);
					}
					
				}
				// if total brokers is empty, create new broker and add it to total and current broker lists
				else {
					TradingBroker broker = new TradingBroker(rowSelections.get(i).getTradingBroker(), rowSelections.get(i).getCoinsList(), coinsList.getPriceList(rowSelections.get(i).getCoinsList()), rowSelections.get(i).getStrategy());
					currentBrokerList.add(broker);
					totalBrokerList.add(broker);
				}
				
			}
			
			for (int i = 0; i < currentBrokerList.size(); i++) {
				
				// creates factory based on the brokers strategy
				Factory factory = new Factory();
				TradingStrategy strategy = factory.create(currentBrokerList.get(i));
				
				// computes result using strategy
				TradeResult result = strategy.performTrade();
				
				// if strategy is able to be used, updates brokers with the strategies they have used
				if(result.getCoinTraded() != "Fail")
					currentBrokerList.get(i).useStrategy(currentBrokerList.get(i).getStrategy());
				
				listOfTradeResults.add(result);
				
			}
			
			// computes chart data
			chartData = ChartData.getInstance(listOfTradeResults);
			
			//printBrokerInfo();
			
			stats.removeAll();
			
			// creates output charts
			DataVisualizationCreator creator = new DataVisualizationCreator(chartData, totalBrokerList);
			creator.createCharts();
		
		// to add table row or remove table row
		} else if ("addTableRow".equals(command)) {
			dtm.addRow(new String[3]);
		} else if ("remTableRow".equals(command)) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1)
				dtm.removeRow(selectedRow);
		}
	}
	
	/* helper methods */
	
	/**
	 * function to print users input into the table
	 */
	public void printRowSelections() {
		System.out.println("Row Selections");
		for(int i = 0; i < rowSelections.size(); i++)
			System.out.println(rowSelections.get(i).getTradingBroker() + " " + Arrays.toString(rowSelections.get(i).getCoinsList()) + " " + rowSelections.get(i).getStrategy());
	}
	
	/**
	 * function that prints all brokers in system that can be used for trading
	 */
	public void printBrokerInfo() {
		System.out.println("Current List of All Brokers");
		for(int i = 0; i < totalBrokerList.size(); i++)
			System.out.println(totalBrokerList.get(i).getTradingBrokerName() + " " + (totalBrokerList.get(i).getCoinsList()).toString() + " " + Arrays.toString(totalBrokerList.get(i).getCoinsPriceList()) + " " + totalBrokerList.get(i).getStrategy());
	}	

}
