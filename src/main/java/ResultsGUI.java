/**
 * Created by laroux0b on 11/05/2017.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

public class ResultsGUI extends JFrame {

    public static String[] columnNames = {"Year",
                                    "Number",
                                    "Song",
                                    "Artist",
                                    "Length",
                                    "Country"};

    public static DefaultTableModel model = new DefaultTableModel(columnNames, 0);

    public ResultsGUI() throws IOException {
        initComponent();
        pack();
    }

    public void initComponent() {
        JFrame resultsGUIFrame = new JFrame();
        JPanel tablePanel = new JPanel(new BorderLayout());
        JTable resultsTable = new JTable(model);
        int height = resultsTable.getRowHeight() * resultsTable.getRowCount();

        resultsGUIFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultsGUIFrame.setTitle("Triple J Hottest 100 Results");
        resultsGUIFrame.setSize(1000, 500);

        tablePanel.setSize(950, height);
        tablePanel.add(resultsTable.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(resultsTable, BorderLayout.CENTER);
        resultsGUIFrame.add(tablePanel);
        JScrollPane scrollPane = new JScrollPane(tablePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultsGUIFrame.add(scrollPane, BorderLayout.CENTER);
        resultsGUIFrame.setLocationRelativeTo(null);
        resultsGUIFrame.setVisible(true);
    }

    public static void addRow(int recordYear, int recordNumber, String recordSong, String recordArtist, String recordLength, String recordCountry){
        model.addRow(new Object[]{recordYear, recordNumber, recordSong, recordArtist, recordLength, recordCountry});
    }

    public static void clearSearchResults(){
        model.setRowCount(0);
    }

//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
////                super.windowClosing(e);
//                resultsGUIClose();
//            }
//
//            @Override
//            public void windowClosed(WindowEvent e) {
////                super.windowClosed(e);
//                resultsGUIClose();
//            }
//        });
//    }
//
//    public void resultsGUIClose(){
//        resultsGUIFrame.setVisible(false);
//        SearchGUI.searchGUIFrame.setVisible(true);
//    }
}