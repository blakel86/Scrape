/**
 * Created by laroux0b on 9/05/2017.
 */

import com.mongodb.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SearchGUI {

    public static JFrame searchGUIFrame;

    public static DB db;
    public static MongoClient mongoClient;
    public static DBCollection col;

    public String[] years;
    public String[] numbers;
    public String[] countries;

    public String yearSearchValue;
    public String numberSearchValue;
    public String countrySearchValue;

    public ArrayList outputList;

    public int resultCount;

    public SearchGUI() throws IOException {
        connectMongo();

        years = returnDistinctYears();
        numbers = returnDistinctNumbers();
        countries = returnDistinctCountries();
        System.out.println(Arrays.toString(years));
        System.out.println(Arrays.toString(numbers));
        System.out.println(Arrays.toString(countries));

        searchGUIFrame = new JFrame();
        searchGUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchGUIFrame.setTitle("Search Triple J Hottest 100");
        searchGUIFrame.setSize(300, 200);
        searchGUIFrame.setLocationRelativeTo(null);
        searchGUIFrame.setEnabled(true);

        addComponents();

        searchGUIFrame.setVisible(true);
    }

    public void connectMongo(){
        System.setProperty("java.net.useSystemProxies", "true");
        System.setProperty("http.proxyHost", "DCA-WBSAPP-P001.rac.com.au");
        System.setProperty("http.proxyPort", "80");

        try {
            mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDB("hottest100DB");
            System.out.println("Connect to hottest100DB database successfully");
            col = db.getCollection("hottest100");
            System.out.println("Collection hottest100 selected successfully");
        }
        catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("Database does not exist");
        }
    }

    public void addComponents(){

        final JPanel comboPanel = new JPanel(new GridLayout(4, 2, 3, 3));

        JLabel yearComboLbl = new JLabel("Year:", SwingConstants.CENTER);
        JComboBox yearComboBox = new JComboBox(years);
        comboPanel.add(yearComboLbl);
        comboPanel.add(yearComboBox);
        yearComboBox.setSelectedIndex(0);
        yearSearchValue = years[yearComboBox.getSelectedIndex()];
        yearComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventYear) {
                    yearSearchValue = years[yearComboBox.getSelectedIndex()];
                    System.out.println(yearSearchValue);
            }
        });

        JLabel numberComboLbl = new JLabel("Number:", SwingConstants.CENTER);
        JComboBox numberComboBox = new JComboBox(numbers);
        comboPanel.add(numberComboLbl);
        comboPanel.add(numberComboBox);
        numberComboBox.setSelectedIndex(0);
        numberSearchValue = numbers[numberComboBox.getSelectedIndex()];
        numberComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventNumber) {
                    numberSearchValue = numbers[numberComboBox.getSelectedIndex()];
                    System.out.println(numberSearchValue);
            }
        });

        JLabel countryComboLbl = new JLabel("Country:", SwingConstants.CENTER);
        JComboBox countryComboBox = new JComboBox(countries);
        comboPanel.add(countryComboLbl);
        comboPanel.add(countryComboBox);
        countryComboBox.setSelectedIndex(0);
        countrySearchValue = countries[countryComboBox.getSelectedIndex()];
        countryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventCountry) {
                    countrySearchValue = countries[countryComboBox.getSelectedIndex()];
                    System.out.println(countrySearchValue);
//                }
            }
        });

        JButton queryButton = new JButton("Run Query");
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventQuery) {
                ResultsGUI.clearSearchResults();
                queryGUI();
                try {
                    ResultsGUI resultsGUI = new ResultsGUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        searchGUIFrame.add(comboPanel, BorderLayout.PAGE_START);
        searchGUIFrame.add(queryButton, BorderLayout.PAGE_END);
    }

    public String[] returnDistinctYears() {
        List list = col.distinct("year");
        String[] yearsList = new String[list.size()+1];
        list.sort(Comparator.comparingInt(Object::hashCode).reversed());
        yearsList[0] = "SELECT";
        for(int i = 0; i <list.size(); i++) {
            yearsList[i+1] = list.get(i).toString();
        }
        return yearsList;
    }

    public String[] returnDistinctNumbers() {
        List list = col.distinct("number");
        list.sort(Comparator.comparingInt(Object::hashCode));
        String[] numbersList = new String[list.size()+1];
        numbersList[0] = "SELECT";
        for(int i = 0; i <list.size(); i++) {
            numbersList[i+1] = list.get(i).toString();
        }
        return numbersList;
    }

    public String[] returnDistinctCountries() {
        List list = col.distinct("country");
        list.sort(Comparator.comparing(Object::toString));
        String[] countriesList = new String[list.size()+1];
        countriesList[0] = "SELECT";
        for(int i = 0; i <list.size(); i++) {
            countriesList[i+1] = list.get(i).toString();
        }
        return countriesList;
    }

    public void queryGUI(){
        BasicDBObject queryGUI = new BasicDBObject();

        int recordYear = 0;
        int recordNumber = 0;
        String recordSong = null;
        String recordArtist = null;
        String recordLength = null;
        String recordCountry = null;

        int yearSearchValueInt;
        int numberSearchValueInt;

        outputList = new ArrayList<>();
        ArrayList recordsLists;

        if(!yearSearchValue.equals("SELECT")) {
            yearSearchValueInt = Integer.parseInt(yearSearchValue);
            queryGUI.put("year", new BasicDBObject("$eq", yearSearchValueInt));
        }
        if(!numberSearchValue.equals("SELECT")) {
            numberSearchValueInt = Integer.parseInt(numberSearchValue);
            queryGUI.put("number", new BasicDBObject("$eq", numberSearchValueInt));
        }
        if(!countrySearchValue.equals("SELECT")) {
            queryGUI.put("country", new BasicDBObject("$eq", countrySearchValue));
        }

        DBCursor cursor = col.find(queryGUI, Mongo.fields());
        resultCount = cursor.count();
        while(cursor.hasNext()) {
            recordsLists = (ArrayList) cursor.toArray();
            for(int i = 0; i < recordsLists.size(); i++) {
                BasicDBObject recordObject = (BasicDBObject) recordsLists.get(i);
                recordYear = Integer.parseInt(recordObject.getString("year"));
                recordNumber = Integer.parseInt(recordObject.getString("number"));
                recordSong = recordObject.getString("song");
                recordArtist = recordObject.getString("artist");
                recordLength = recordObject.getString("length");
                recordCountry = recordObject.getString("country");
                ResultsGUI.addRow(recordYear, recordNumber, recordSong, recordArtist, recordLength, recordCountry);
            }
        }
    }
}
