/**
 * Created by laroux0b on 27/04/2017.
 */

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

public class Documents {
    public int year;
    public int number;
    public String song;
    public String artist;
    public String length;
    public static int size;
    public int numOfRows;

    ArrayList<Hottest100> hottest100 = new ArrayList<>();

    public void setHottest100Ob(Hottest100 hottest100Ob) {
        this.hottest100Ob = hottest100Ob;
    }

    Hottest100 hottest100Ob = new Hottest100(year, number, song, artist, length);

    public void addEntry(int year, int number, String song, String artist, String length){
        hottest100.add(new Hottest100(year, number, song, artist, length));
        setHottest100Ob(new Hottest100(year, number, song, artist, length));
    }

    public void processDocument(Document doc){
//        Element title = doc.select("page")
        Element table = doc.select("table").get(0); //select the first table
        processTable(table);
    }

    public ArrayList<Hottest100> getHottest100(){
        return hottest100;
    }

    public void processTable(Element table) {
        Elements rows = table.select("tr");
//        Element cols = table.select("tr").get(0);
        numOfRows = rows.size();

        for (int i = 1; i < numOfRows; i++) {
            Element row = rows.get(i);
            Elements rowCols = row.select("td");
            year = Connect.getHottest100Year();
            for (int j = 0; j < rowCols.size() - 1; j++) {
                if (j == 0) {
                    number = Integer.parseInt(rowCols.get(j).text());
                } else if (j == 1) {
                    song = rowCols.get(j).text();
                } else if (j == 2) {
                    artist = rowCols.get(j).text();
                } else if (j == 3) {
                    length = rowCols.get(j).text();
                } else {
                    break;
                }
            }
            addEntry(year, number, song, artist, length);
            Mongo.addRecord(hottest100Ob);
        }
        size = hottest100.size();
    }
}