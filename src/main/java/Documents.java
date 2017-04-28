import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laroux0b on 27/04/2017.
 */
public class Documents {

//    public int number;
//    public String song;
//    public String artist;
//    public double length;
    public String number;
    public String song;
    public String artist;
    public String length;
//    public Element table;
    ArrayList<Hottest100> hottest100 = new ArrayList<Hottest100>();


    public void addEntry(String number, String song, String artist, String length){
        hottest100.add(new Hottest100(number, song, artist, length));
    }

    public void processDocument(Document doc){
        Element table = doc.select("table").get(0); //select the first table
        processTable(table);
    }

    public List<Hottest100> getHottest100(){
        return hottest100;
    }

    public void processTable(Element table){

        Elements rows = table.select("tr");
        Element cols = table.select("tr").get(0);
        int numOfRows = rows.size();

        for (int i = 1; i < numOfRows; i++) {
            Element row = rows.get(i);
            Elements rowCols = row.select("td");
            for (int j = 0; j < rowCols.size()-1; j++) {
                if(j == 0){
                    number = rowCols.get(j).text();
                }
                else if(j == 1){
                    song = rowCols.get(j).text();
                }
                else if(j == 2){
                    artist = rowCols.get(j).text();
                }
                else if(j == 3) {
                    length = rowCols.get(j).text();
                }
                else{
                    break;
                }
            }
            addEntry(number, song, artist, length);
        }

    }
}