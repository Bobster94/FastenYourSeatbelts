package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * FXML Controller class
 *
 * @author Bas
 */
public class ManagerController implements Initializable {
    @FXML private SwingNode snChart;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JFreeChart chart = FreeChartDemo("luggage vs years");
        ChartPanel chartPanel = new ChartPanel(chart);
        snChart.setContent(chartPanel);
    }

    public BorderPane getManagerScreen() {
        BorderPane screen = null;
        try {
            screen = FXMLLoader.load(
                    getClass().getResource("/views/Manager.fxml")
            );
        } catch (IOException ex) {
            Logger.getLogger(
                    LoginController.class.getName()).log(Level.SEVERE, null, ex
                    );
        }
        return screen;
    }

    public JFreeChart FreeChartDemo(String chartTitle) {
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Years", "Luggage",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false
        );
        
        return lineChart;
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //Found luggage
        dataset.addValue(15, "Found luggage", "1970");
        dataset.addValue(30, "Found luggage", "1980");
        dataset.addValue(60, "Found luggage", "1990");
        dataset.addValue(120, "Found luggage", "2000");
        dataset.addValue(240, "Found luggage", "2010");
        dataset.addValue(300, "Found luggage", "2014");

        //Lost luggage
        dataset.addValue(200, "lost luggage", "1970");
        dataset.addValue(250, "lost luggage", "1980");
        dataset.addValue(310, "lost luggage", "1990");
        dataset.addValue(100, "lost luggage", "2000");

        //Handled luggage
        dataset.addValue(10, "Handled luggage", "1970");
        dataset.addValue(25, "Handled luggage", "1980");
        dataset.addValue(31, "Handled luggage", "1990");
        dataset.addValue(10, "Handled luggage", "2000");
        
        return dataset;
    }
}
