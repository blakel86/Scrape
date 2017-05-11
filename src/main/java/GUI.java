/**
 * Created by laroux0b on 9/05/2017.
 */

import com.mongodb.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class GUI {
    public static JFrame guiFrame;

    public static DB db;
    public static MongoClient mongoClient;
    public static DBCollection col;

    public static String[] years;
    public static String[] numbers;
    public static String[] countries;

//    public int yearSearchValue;
//    public int numberSearchValue;
    public static String yearSearchValue;
    public static String numberSearchValue;
    public static String countrySearchValue;

    public static BasicDBObject fields;

    public static void main(String[] args) throws IOException {
        System.setProperty("java.net.useSystemProxies", "true");
        System.setProperty("http.proxyHost", "DCA-WBSAPP-P001.rac.com.au");
        System.setProperty("http.proxyPort", "80");

        try {
            mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDB("hottest100DB");
            System.out.println("Connect to hottest100DB database successfully");
            col = db.getCollection("hottest100");
            System.out.println("Collection hottest100 selected successfully");

            years = returnDistinctYears();
            numbers = returnDistinctNumbers();
            countries = returnDistinctCountries();
            System.out.println(Arrays.toString(years));
            System.out.println(Arrays.toString(numbers));
            System.out.println(Arrays.toString(countries));

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        guiFrame = new JFrame();
        new GUI(guiFrame);
    }

    public GUI(JFrame guiFrame) throws IOException {
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Example GUI");
        guiFrame.setSize(300, 200);
        guiFrame.setLocationRelativeTo(null);
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
//                if(yearComboBox.getSelectedIndex() != -1) {
//                    yearSearchValue = Integer.parseInt(years[yearComboBox.getSelectedIndex()]);
                    yearSearchValue = years[yearComboBox.getSelectedIndex()];
                    System.out.println(yearSearchValue);
//                }
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
//                if(numberComboBox.getSelectedIndex() != -1) {
//                    numberSearchValue = Integer.parseInt(numbers[numberComboBox.getSelectedIndex()]);
                    numberSearchValue = numbers[numberComboBox.getSelectedIndex()];
                    System.out.println(numberSearchValue);
//                }
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
//                if(countryComboBox.getSelectedIndex() != -1) {
                    countrySearchValue = countries[countryComboBox.getSelectedIndex()];
                    System.out.println(countrySearchValue);
//                }
            }
        });

        final JPanel listPanel = new JPanel();
        listPanel.setVisible(false);
        JLabel numberListLbl = new JLabel("Number:");
        JList numberList = new JList(numbers);
        numberList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        listPanel.add(numberListLbl);
        listPanel.add(numberList);
//        JButton yearNumberButton = new JButton("Year or Number");

//        yearNumberButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent event) {
//                //When the fruit of veg button is pressed
//                //the setVisible value of the listPanel and
//                //comboPanel is switched from true to
//                //value or vice versa.
//                listPanel.setVisible(!listPanel.isVisible());
//                comboPanel.setVisible(!comboPanel.isVisible());
//            }
//        });

        JButton queryButton = new JButton("Run Query");
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventQuery) {
                queryGUI();
            }
        });


        guiFrame.add(comboPanel, BorderLayout.PAGE_START);
        guiFrame.add(listPanel, BorderLayout.CENTER);
//        guiFrame.add(yearNumberButton, BorderLayout.PAGE_END);
        guiFrame.add(queryButton, BorderLayout.PAGE_END);
        guiFrame.setVisible(true);
    }

    public static String[] returnDistinctYears() {
        List list = col.distinct("year");
        String[] yearsList = new String[list.size()+1];
        list.sort(Comparator.comparingInt(Object::hashCode).reversed());
        yearsList[0] = "SELECT";
        for(int i = 0; i <list.size(); i++) {
            yearsList[i+1] = list.get(i).toString();
        }
        return yearsList;
    }

    public static String[] returnDistinctNumbers() {
        List list = col.distinct("number");
        list.sort(Comparator.comparingInt(Object::hashCode));
        String[] numbersList = new String[list.size()+1];
        numbersList[0] = "SELECT";
        for(int i = 0; i <list.size(); i++) {
            numbersList[i+1] = list.get(i).toString();
        }
        return numbersList;
    }

    public static String[] returnDistinctCountries() {
        List list = col.distinct("country");
        list.sort(Comparator.comparing(Object::toString));
        String[] countriesList = new String[list.size()+1];
        countriesList[0] = "SELECT";
        for(int i = 0; i <list.size(); i++) {
            countriesList[i+1] = list.get(i).toString();
        }
        return countriesList;
    }

    public static BasicDBObject fields(){
        fields = new BasicDBObject();

        fields.put("_id", 0);
        fields.put("year", 1);
        fields.put("number", 1);
        fields.put("song", 1);
        fields.put("artist", 1);
        fields.put("length", 1);
        fields.put("country", 1);

        return fields;
    }

    public static void queryGUI(){
        BasicDBObject queryGUI = new BasicDBObject();

        int yearSearchValueInt;
        int numberSearchValueInt;

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

        DBCursor cursor = col.find(queryGUI, fields());
        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }
}
