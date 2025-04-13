package src.gui;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartReportsScreen extends JFrame {

    public ChartReportsScreen() {
        setTitle("Sunflower Banking - Chart Reports");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(5000, "Savings", "January");
        dataset.addValue(5200, "Savings", "February");
        dataset.addValue(5400, "Savings", "March");
        dataset.addValue(2000, "Current", "January");
        dataset.addValue(2100, "Current", "February");
        dataset.addValue(2200, "Current", "March");

        // Create chart
        JFreeChart barChart = ChartFactory.createBarChart(
                "Account Balances Over Time",
                "Month",
                "Balance ($)",
                dataset
        );

        // Add chart to panel
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new ChartReportsScreen();
    }
}
