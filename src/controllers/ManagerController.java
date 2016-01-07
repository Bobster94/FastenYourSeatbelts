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
import javafx.scene.layout.BorderPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * FXML Controller class
 * This class shows the graphs for the manager
 * @author Bas
 * @version 1.0
 */
public class ManagerController implements Initializable {

    @FXML
    private SwingNode snChart;
    int handled = 0;
    int addLostLuggage = 0;
    int addLuggage = 0;
    int year = 2016;
    String[][] periodes = {
        {year + "-01-01", year + "-01-31"}, //January
        {year + "-02-01", year + "-02-31"}, //February
        {year + "-03-01", year + "-03-31"}, //March
        {year + "-04-01", year + "-04-31"}, //April
        {year + "-05-01", year + "-05-31"}, //May
        {year + "-06-01", year + "-06-31"}, //June
        {year + "-07-01", year + "-07-31"}, //Juli
        {year + "-08-01", year + "-08-31"}, //August
        {year + "-09-01", year + "-09-31"}, //September
        {year + "-10-01", year + "-10-31"}, //October
        {year + "-11-01", year + "-11-31"}, //November
        {year + "-12-01", year + "-12-31"}, //December
    };
    Integer[] periodResultsHandled = new Integer[periodes.length];
    Integer[] periodResultsLost = new Integer[periodes.length];
    Integer[] periodResultsFound = new Integer[periodes.length];
    String Month[] = {
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        int index = 0;
        for (String[] periode : periodes) {
            String handledSQL = "SELECT * FROM history "
                    + "WHERE history.status = 'handled' "
                    + "AND history.dateHandled between '" + periode[0] + "' AND '" + periode[1]+"';";
            String lostSQL = "SELECT * FROM history "
                    + "WHERE history.status = 'addLostLuggage' "
                    + "AND history.dateHandled between '" + periode[0] + "' AND '" + periode[1]+"';";
            String foundSQL = "SELECT * FROM history "
                    + "WHERE history.status = 'addLuggage' "
                    + "AND history.dateHandled between '" + periode[0] + "' AND '" + periode[1]+"';";
            try {
                Connection conn = Database.initDatabase();
                ResultSet handledRS = conn.createStatement().executeQuery(handledSQL);
                ResultSet lostRS = conn.createStatement().executeQuery(lostSQL);
                ResultSet foundRS = conn.createStatement().executeQuery(foundSQL);
                handled = 0;
                addLostLuggage = 0;
                addLuggage = 0;
                while (handledRS.next()) {
                    int idCustomer = handledRS.getInt("idCustomer");
                    int idLuggage = handledRS.getInt("idLuggage");
                    String status = handledRS.getString("status");
                    if ("handled".equals(status)) {
                        handled++;
                    }
                }
                while (lostRS.next()) {
                    int idCustomer = lostRS.getInt("idCustomer");
                    int idLuggage = lostRS.getInt("idLuggage");
                    String status = lostRS.getString("status");
                    if ("addLostLuggage".equals(status)) {
                        addLostLuggage++;
                    }
                }
                while (foundRS.next()) {
                    int idCustomer = foundRS.getInt("idCustomer");
                    int idLuggage = foundRS.getInt("idLuggage");
                    String status = foundRS.getString("status");
                    if ("addLuggage".equals(status)) {
                        addLuggage++;
                    }
                }
                
                periodResultsHandled[index] = handled;
                periodResultsLost[index] = addLostLuggage;
                periodResultsFound[index] = addLuggage;
                index++;
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        JFreeChart chart = FreeChartDemo("luggage vs month");
        ChartPanel chartPanel = new ChartPanel(chart);
        snChart.setContent(chartPanel);
    }

    /*
    * Returns the manager screen
    * @return BorderPane returns the manager screen as  borderpane
    */
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

    /*
    * Creates the lineChart
    * @param String chartTitle  The title for the chart 
    * @return JFreeChart        The LineChart with data    
    */
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

    /*
    *Prepare a dataset for the linechart
    *@return DefaultCategoryDataset The dataset which can be added to the linechart
    */
    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
       
        //Found luggage
        int index1 = 0;
        for (Integer periodResultFound : periodResultsFound) {
            dataset.addValue(periodResultFound, "Found luggagee", Month[index1]);
            index1++;
        }
        
        //Lost luggage
        int index2 = 0;
        for (Integer periodResultLost : periodResultsLost) {
            dataset.addValue(periodResultLost, "lost luggage", Month[index2]);
            index2++;
        }
        
        //Handled luggage
        int index3 = 0;
        for (Integer periodResultHandled : periodResultsHandled) {
            dataset.addValue(periodResultHandled, "Handled luggage", Month[index3]);
            index3++;
        }
        return dataset;
    }
}
