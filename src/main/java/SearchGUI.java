import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class SearchGUI extends JFrame {

    JFrame searchGUIFrame;
    private ResultsGUI resultsGUI;

    private String[] years;
    private String[] numbers;
    private String[] countries;

    private String yearSearchValue;
    private String numberSearchValue;
    private String countrySearchValue;
//
//    private int resultCount;

    void createSearchGUI() {
        Main.mongo.getDatabase();
        searchGUIFrame = new JFrame();

        years = returnDistinctYears();
        numbers = returnDistinctNumbers();
        countries = returnDistinctCountries();
        System.out.println(Arrays.toString(years));
        System.out.println(Arrays.toString(numbers));
        System.out.println(Arrays.toString(countries));

        searchGUIFrame = new JFrame();
        searchGUIFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        searchGUIFrame.setTitle("Search Triple J Hottest 100");
        searchGUIFrame.setSize(300, 200);
        searchGUIFrame.setLocationRelativeTo(null);
        searchGUIFrame.setEnabled(true);

        searchGUIFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Main.mongo.mongoClose();
                Server.stopServer();
            }
        });

        addComponents();

        searchGUIFrame.setVisible(true);
    }

    private void addComponents(){

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
                queryGUI();
                resultsGUI.createResultsGUI();
            }
        });

        searchGUIFrame.add(comboPanel, BorderLayout.PAGE_START);
        searchGUIFrame.add(queryButton, BorderLayout.PAGE_END);
    }

    private String[] returnDistinctYears() {
        List list = Main.mongo.col.distinct("year");
        String[] yearsList = new String[list.size()+1];
        list.sort(Comparator.comparingInt(Object::hashCode).reversed());
        yearsList[0] = "SELECT";
        for(int i = 0; i <list.size(); i++) {
            yearsList[i+1] = list.get(i).toString();
        }
        return yearsList;
    }

    private String[] returnDistinctNumbers() {
        List list = Main.mongo.col.distinct("number");
        list.sort(Comparator.comparingInt(Object::hashCode));
        String[] numbersList = new String[list.size()+1];
        numbersList[0] = "SELECT";
        for(int i = 0; i <list.size(); i++) {
            numbersList[i+1] = list.get(i).toString();
        }
        return numbersList;
    }

    private String[] returnDistinctCountries() {
        List list = Main.mongo.col.distinct("country");
        list.sort(Comparator.comparing(Object::toString));
        String[] countriesList = new String[list.size()+1];
        countriesList[0] = "SELECT";
        for(int i = 0; i <list.size(); i++) {
            countriesList[i+1] = list.get(i).toString();
        }
        return countriesList;
    }

    private void queryGUI(){
        resultsGUI = new ResultsGUI();
        resultsGUI.clearSearchResults();
        BasicDBObject queryGUI = new BasicDBObject();

        int recordYear;
        int recordNumber;
        String recordSong;
        String recordArtist;
        String recordLength;
        String recordCountry;

        int yearSearchValueInt;
        int numberSearchValueInt;

//        ArrayList outputList = new ArrayList<>();
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

        DBCursor cursor = Main.mongo.col.find(queryGUI, Mongo.fields());
//        resultCount = cursor.count();
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
                resultsGUI.addRow(recordYear, recordNumber, recordSong, recordArtist, recordLength, recordCountry);
//                ResultsGUI.addRow(recordYear, recordNumber, recordSong, recordArtist, recordLength, recordCountry);
            }
        }
    }
}
