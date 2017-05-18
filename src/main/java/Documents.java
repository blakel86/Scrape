import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

class Documents {

    private int year;
    private int number;
    private String song;
    private String artist;
    private String length;
    private String country;

    private final ArrayList<Hottest100> hottest100 = new ArrayList<>();

    private void setHottest100Ob(Hottest100 hottest100Ob) {
        this.hottest100Ob = hottest100Ob;
    }

    private Hottest100 hottest100Ob = new Hottest100(year, number, song, artist, length, country);

    private void addEntry(int year, int number, String song, String artist, String length, String country){
        hottest100.add(new Hottest100(year, number, song, artist, length, country));
        setHottest100Ob(new Hottest100(year, number, song, artist, length, country));
    }

    void processDocument(Document doc){
        Element table = doc.select("table").get(0); //select the first table
        processTable(table);
    }

    ArrayList<Hottest100> getHottest100(){
        return hottest100;
    }

    private void processTable(Element table) {
        Elements rows = table.select("tr");
        int numOfRows = rows.size();

        for (int i = 1; i < numOfRows && i <= 100; i++) {
            Element row = rows.get(i);
            Elements rowCols = row.select("td");
            year = Main.connection.getHottest100Year();
            for (int j = 0; j < rowCols.size(); j++) {
                if (j == 0) {
                    number = Integer.parseInt(rowCols.get(j).text());
                } else if (j == 1) {
                    song = rowCols.get(j).text();
                } else if (j == 2) {
                    artist = rowCols.get(j).text();
                } else if (j == 3) {
                    length = rowCols.get(j).text();
                } else if (j == 4) {
                    country = rowCols.get(j).text();
                } else {
                    break;
                }
            }
            addEntry(year, number, song, artist, length, country);
            Main.mongo.addRecord(hottest100Ob);
        }
//        int size = hottest100.size();
    }
}