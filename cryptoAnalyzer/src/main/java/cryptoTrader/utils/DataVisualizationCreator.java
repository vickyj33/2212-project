package cryptoTrader.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import cryptoTrader.gui.MainUI;

/**
 * 
 * This class creates the objects that will display the trade log table and strategies used histogram on the mainUI
 * This code was adapted from code provided by CS2212
 * 
 * @author CS2212
 * @author Ali Mohamed
 * @author Ian Guenther Green
 *
 */

public class DataVisualizationCreator {
	private ChartData chartData;
	private List<TradingBroker> tradingBrokerList;
	private String[] strategyNames = {"Strategy-A", "Strategy-B", "Strategy-C", "Strategy-D", "Strategy-E"};
	
	/**
	 * Constructor that assigns instance variables including the formatted chartData
	 * @param chartData data for the table
	 * @param brokers used to get data for histogram
	 */
	public DataVisualizationCreator(ChartData chartData, List<TradingBroker> brokers) {
		this.chartData = chartData;
		tradingBrokerList = brokers;
	}
	
	/**
	 * Method used to create charts on the MainUI
	 */
	public void createCharts() {
		createTableOutput();
		createBar();
	}

	/**
	 * Method that creates output the the trade log table on the MainUI. This includes data for all trades made in the format
	 * "Trader","Strategy","CryptoCoin","Action","Quantity","Price (cad)","Date"
	 */
	private void createTableOutput() {
		// Object that stores the names for each of the columns in the trade log
		Object[] columnNames = {"Trader","Strategy","CryptoCoin","Action","Quantity","Price (cad)","Date"};

		JTable table = new JTable(chartData.getTableData(), columnNames);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                "Trader Actions",
                TitledBorder.CENTER,
                TitledBorder.TOP));
		
			
		scrollPane.setPreferredSize(new Dimension(700, 300));
		table.setFillsViewportHeight(true);;
		
		MainUI.getInstance().updateStats(scrollPane);
	}
	
	/**
	 * Method that creates output for histogram on MainUI. 
	 * This shows the brokers with how many times they have used each strategy
	 */
	private void createBar() {
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		// looks at which brokers have used which strategies and sets that for the bar chart
		for(int i = 0; i < tradingBrokerList.size(); i++) {
			
			for(String strategy:strategyNames) {
				dataset.setValue(tradingBrokerList.get(i).getStrategyUses(strategy), tradingBrokerList.get(i).getTradingBrokerName(), strategy);				
			}
			
		}
		
		CategoryPlot plot = new CategoryPlot();
		BarRenderer barrenderer1 = new BarRenderer();

		plot.setDataset(0, dataset);
		plot.setRenderer(0, barrenderer1);
		CategoryAxis domainAxis = new CategoryAxis("Strategy");
		plot.setDomainAxis(domainAxis);
		LogAxis rangeAxis = new LogAxis("Actions(Buys or Sells)");
		rangeAxis.setRange(new Range(0.1, 20.0));
		plot.setRangeAxis(rangeAxis);

		JFreeChart barChart = new JFreeChart("Actions Performed By Traders So Far", new Font("Serif", java.awt.Font.BOLD, 18), plot,
				true);

		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new Dimension(600, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		chartPanel.setBackground(Color.white);
		MainUI.getInstance().updateStats(chartPanel);
	}

}
