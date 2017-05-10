/**
 * Created by laroux0b on 9/05/2017.
 */

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

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

    public int yearSearchValue;
    public int numberSearchValue;
    public String countrySearchValue;

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
        yearComboBox.setSelectedIndex(-1);
        yearComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventYear) {
                if(yearComboBox.getSelectedIndex() != -1) {
                    yearSearchValue = Integer.parseInt(years[yearComboBox.getSelectedIndex()]);
                    System.out.println(yearSearchValue);
                }
            }
        });

        JLabel numberComboLbl = new JLabel("Number:", SwingConstants.CENTER);
        JComboBox numberComboBox = new JComboBox(numbers);
        comboPanel.add(numberComboLbl);
        comboPanel.add(numberComboBox);
        numberComboBox.setSelectedIndex(-1);
        numberComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventNumber) {
                if(numberComboBox.getSelectedIndex() != -1) {
                    numberSearchValue = Integer.parseInt(numbers[numberComboBox.getSelectedIndex()]);
                    System.out.println(numberSearchValue);
                }
            }
        });

        JLabel countryComboLbl = new JLabel("Country:", SwingConstants.CENTER);
        JComboBox countryComboBox = new JComboBox(countries);
        comboPanel.add(countryComboLbl);
        comboPanel.add(countryComboBox);
        countryComboBox.setSelectedIndex(-1);
        countryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventCountry) {
                if(countryComboBox.getSelectedIndex() != -1) {
                    countrySearchValue = countries[countryComboBox.getSelectedIndex()];
                    System.out.println(countrySearchValue);
                }
            }
        });

        final JPanel listPanel = new JPanel();
        listPanel.setVisible(false);
        JLabel numberListLbl = new JLabel("Number:");
        JList numberList = new JList(numbers);
        numberList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        listPanel.add(numberListLbl);
        listPanel.add(numberList);
        JButton yearNumberButton = new JButton("Year or Number");

        yearNumberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //When the fruit of veg button is pressed
                //the setVisible value of the listPanel and
                //comboPanel is switched from true to
                //value or vice versa.
                listPanel.setVisible(!listPanel.isVisible());
                comboPanel.setVisible(!comboPanel.isVisible());
            }
        });

        guiFrame.add(comboPanel, BorderLayout.PAGE_START);
        guiFrame.add(listPanel, BorderLayout.CENTER);
        guiFrame.add(yearNumberButton, BorderLayout.PAGE_END);
        guiFrame.setVisible(true);
    }

    public static String[] returnDistinctYears() {
        List list = col.distinct("year");
        String[] yearsList = new String[list.size()];
        list.sort(Comparator.comparingInt(Object::hashCode).reversed());
        for(int i = 0; i <list.size(); i++) {
            yearsList[i] = list.get(i).toString();
        }
        return yearsList;
    }

    public static String[] returnDistinctNumbers() {
        List list = col.distinct("number");
        list.sort(Comparator.comparingInt(Object::hashCode));
        String[] numberList = new String[list.size()];
        for(int i = 0; i <list.size(); i++) {
            numberList[i] = list.get(i).toString();
        }
        return numberList;
    }

    public static String[] returnDistinctCountries() {
        List list = col.distinct("country");
        list.sort(Comparator.comparing(Object::toString));
        String[] countryList = new String[list.size()];
        for(int i = 0; i <list.size(); i++) {
            countryList[i] = list.get(i).toString();
        }
        return countryList;
    }
}
