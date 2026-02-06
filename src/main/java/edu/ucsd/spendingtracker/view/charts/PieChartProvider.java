package edu.ucsd.spendingtracker.view.charts;

import edu.ucsd.spendingtracker.model.Category;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;

import java.util.Map;

public class PieChartProvider implements IChartProvider {

    @Override
    public Node createChart(Map<Category, Double> data) {
        PieChart chart = new PieChart();

        data.forEach((cat, sum) -> {
            PieChart.Data slice = new PieChart.Data(cat.name(), sum);
            chart.getData().add(slice);
        });

        chart.applyCss();
        chart.layout();

        for (PieChart.Data slice : chart.getData()) {
            String color = Category.valueOf(slice.getName()).color;
            Node node = slice.getNode();
            if (node != null) {
                node.setStyle("-fx-pie-color: " + color + ";");
            }
        }

        chart.setLegendVisible(false);
        return chart;
    }

    @Override
    public String getDisplayName() {
        return "Pie Chart";
    }
}