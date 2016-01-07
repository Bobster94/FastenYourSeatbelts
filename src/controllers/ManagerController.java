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
    @FXML private SwingNode snChart;
    int handled = 0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
        
        try (Connection conn = Database.initDatabase()) {
            String SQL = "SELECT idCustomer "
                    + "FROM history";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            System.out.println(rs.getMetaData());
        } catch (Exception e) {
            System.out.println(e);
        }
        
        String hanledSQL = "SELECT * FROM history "
                + "WHERE history.status = handled" 
                + "AND history.dateHandled between";
        
        try (Connection conn = Database.initDatabase()){
            ResultSet rs = conn.createStatement().executeQuery(SQL);
               
        while (rs.next()) {
            int idCustomer = rs.getInt("idCustomer");
            int idLuggage = rs.getInt("idLuggage");
            String status = rs.getString("status");
            
            System.out.println(idCustomer + "\t" + idLuggage + "\t" + status);
                               
            if ("handled".equals(status)) {
                handled++;
            }
        }
        System.out.println(handled);
        
        JFreeChart chart = FreeChartDemo("luggage vs month");
        ChartPanel chartPanel = new ChartPanel(chart);
        snChart.setContent(chartPanel);
        
        } catch (Exception e ) {
            System.out.println(e);
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
        dataset.addValue(handled, "Handled luggage", "January");
        dataset.addValue(handled, "Handled luggage", "February");
        dataset.addValue(handled, "Handled luggage", "March");
        dataset.addValue(handled, "Handled luggage", "April");
        dataset.addValue(handled, "Handled luggage", "May");
        dataset.addValue(handled, "Handled luggage", "June");
        dataset.addValue(handled, "Handled luggage", "July");
        dataset.addValue(handled, "Handled luggage", "August");
        dataset.addValue(handled, "Handled luggage", "September");
        dataset.addValue(handled, "Handled luggage", "October");
        dataset.addValue(handled, "Handled luggage", "November");
        dataset.addValue(handled, "Handled luggage", "December");
        String[][] periode = {
            {year+"-01-01-",year+"31-01-"+year}, //januari
            {year+"-02-02-",year+"31-02-"+year}, //feb
            {year+"-03-03-",year+"31-03"}
        };
        String[] results
        SELECT * FROM history where between
        
        return dataset;
    }
}
