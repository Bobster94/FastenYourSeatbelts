package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
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

    @FXML
    private SwingNode snChart;
    int handled = 0;
    int year = 2016;
        String[][] periodes = {
            {year+"-01-01",year+"-01-31"}, //January
            {year+"-02-01",year+"-02-31"}, //February
            {year+"-03-01",year+"-03-31"}, //March
            {year+"-04-01",year+"-04-31"}, //April
            {year+"-05-01",year+"-05-31"}, //May
            {year+"-06-01",year+"-06-31"}, //June
            {year+"-07-01",year+"-07-31"}, //Juli
            {year+"-08-01",year+"-08-31"}, //August
            {year+"-09-01",year+"-09-31"}, //September
            {year+"-10-01",year+"-10-31"}, //October
            {year+"-11-01",year+"-11-31"}, //November
            {year+"-12-01",year+"-12-31"}, //December
        };
    Integer[] periodResults = new Integer[periodes.length];
    
    String Month[] ={
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "Juli",
        "August",
        "September",
        "Oktober",
        "November",
        "December"
    };
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        int index = 0;
        for (String[] periode : periodes) {
            String hanledSQL = "SELECT * FROM history "
                    + "WHERE history.status = handled "
                    + "AND history.dateHandled between " + periode[0] + " AND " + periode[1];

            try (Connection conn = Database.initDatabase()) {
                ResultSet rs = conn.createStatement().executeQuery(hanledSQL);

                while (rs.next()) {
                    int idCustomer = rs.getInt("idCustomer");
                    int idLuggage = rs.getInt("idLuggage");
                    String status = rs.getString("status");

                    System.out.println(idCustomer + "\t" + idLuggage + "\t" + status);

                    if ("handled".equals(status)) {
                        handled++;
                    }
                }
                periodResults[index] = handled;
                index++;
            } catch (Exception e) {
                System.out.println(e);
            }
            JFreeChart chart = FreeChartDemo("luggage vs month");
            ChartPanel chartPanel = new ChartPanel(chart);
            snChart.setContent(chartPanel);
        }
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
                "Month", "Luggage",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false
        );

        return lineChart;
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //Found luggage
        dataset.addValue(5, "Found luggage", "January");
        dataset.addValue(3, "Found luggage", "February");
        dataset.addValue(6, "Found luggage", "March");
        dataset.addValue(8, "Found luggage", "April");
        dataset.addValue(10, "Found luggage", "May");
        dataset.addValue(3, "Found luggage", "June");
        dataset.addValue(3, "Found luggage", "July");
        dataset.addValue(3, "Found luggage", "August");
        dataset.addValue(3, "Found luggage", "September");
        dataset.addValue(3, "Found luggage", "October");
        dataset.addValue(3, "Found luggage", "November");
        dataset.addValue(3, "Found luggage", "December");

        //Lost luggage
        dataset.addValue(1, "lost luggage", "January");
        dataset.addValue(2, "lost luggage", "February");
        dataset.addValue(3, "lost luggage", "March");
        dataset.addValue(4, "lost luggage", "April");
        dataset.addValue(4, "lost luggage", "May");
        dataset.addValue(4, "lost luggage", "June");
        dataset.addValue(4, "lost luggage", "July");
        dataset.addValue(4, "lost luggage", "August");
        dataset.addValue(4, "lost luggage", "September");
        dataset.addValue(4, "lost luggage", "October");
        dataset.addValue(4, "lost luggage", "November");
        dataset.addValue(4, "lost luggage", "December");

        //Handled luggage
        int index = 0;
        for (Integer periodResult : periodResults) {
            dataset.addValue(periodResult, "Handled luggage", "January");
            index++;
        }
        return dataset;
    }
}
