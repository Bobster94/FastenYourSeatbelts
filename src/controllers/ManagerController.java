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
<<<<<<< HEAD
    @FXML private SwingNode snChart;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JFreeChart chart = FreeChartDemo("luggage vs years");
=======

    @FXML
    private SwingNode snChart;
    @FXML
    int handled = 0;
    int addLostLuggage = 0;
    int addLuggage = 0;
    int addCustomer = 0;
    int year = 2016;
    // Array periodes make from days a month
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
    
    // making arrays and give a length
    Integer[] periodResultsHandled = new Integer[periodes.length];
    Integer[] periodResultsLost = new Integer[periodes.length];
    Integer[] periodResultsFound = new Integer[periodes.length];
    Integer[] periodResultsCustomer = new Integer[periodes.length];
    // Array for looping the months per year.
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
        populateChart();
    }

    private void populateChart() {
        int index = 0;
        // watch in databas and get all data from history table and sort on date.
        for (String[] periode : periodes) {
            String handledSQL = "SELECT * FROM history "
                    + "WHERE history.status = 'handled' "
                    + "AND history.dateHandled between '" + periode[0] + "' AND '" + periode[1] + "';";
            String lostSQL = "SELECT * FROM history "
                    + "WHERE history.status = 'addLostLuggage' "
                    + "AND history.dateHandled between '" + periode[0] + "' AND '" + periode[1] + "';";
            String foundSQL = "SELECT * FROM history "
                    + "WHERE history.status = 'addLuggage' "
                    + "AND history.dateHandled between '" + periode[0] + "' AND '" + periode[1] + "';";
            String customerSQL = "SELECT * FROM history "
                    + "WHERE history.status = 'addCustomer' "
                    + "AND history.dateHandled between '" + periode[0] + "' AND '" + periode[1] + "';";
            //making connection met database and whatch whitch SQL you need to lode and show data.
            try {
                Connection conn = Database.initDatabase();
                ResultSet handledRS = conn.createStatement().executeQuery(handledSQL);
                ResultSet lostRS = conn.createStatement().executeQuery(lostSQL);
                ResultSet foundRS = conn.createStatement().executeQuery(foundSQL);
                ResultSet customerRS = conn.createStatement().executeQuery(customerSQL);
                handled = 0;
                addLostLuggage = 0;
                addLuggage = 0;
                addCustomer = 0;
                // put data in varible for the jfreeChart
                while (handledRS.next()) {
                    int idCustomer = handledRS.getInt("idCustomer");
                    int idLuggage = handledRS.getInt("idLuggage");
                    String status = handledRS.getString("status");
                    if ("handled".equals(status)) {
                        handled++;
                    }
                }
                // put data in varible for the jfreeChart
                while (lostRS.next()) {
                    int idCustomer = lostRS.getInt("idCustomer");
                    int idLuggage = lostRS.getInt("idLuggage");
                    String status = lostRS.getString("status");
                    if ("addLostLuggage".equals(status)) {
                        addLostLuggage++;
                    }
                }
                // put data in varible for the jfreeChart
                while (foundRS.next()) {
                    int idCustomer = foundRS.getInt("idCustomer");
                    int idLuggage = foundRS.getInt("idLuggage");
                    String status = foundRS.getString("status");
                    if ("addLuggage".equals(status)) {
                        addLuggage++;
                    }
                }
                // put data in varible for the jfreeChart
                while (customerRS.next()) {
                    int idCustomer = customerRS.getInt("idCustomer");
                    int idLuggage = customerRS.getInt("idLuggage");
                    String status = customerRS.getString("status");
                    if ("addCustomer".equals(status)) {
                        addCustomer++;
                    }
                }
                // making the variable for the data in the jfreechart 
                periodResultsHandled[index] = handled;
                periodResultsLost[index] = addLostLuggage;
                periodResultsFound[index] = addLuggage;
                periodResultsCustomer[index] = addCustomer;
                
                index++;
            // print error in e if sql cant run.
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        // making jFreeChart/ call method 
        JFreeChart chart = FreeChartDemo("luggage vs month");
>>>>>>> origin/master
        ChartPanel chartPanel = new ChartPanel(chart);
        snChart.setContent(chartPanel);
    }

<<<<<<< HEAD
=======
    /*
     * Returns the manager screen
     * @return BorderPane returns the manager screen as  borderpane
     */
>>>>>>> origin/master
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
