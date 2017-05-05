/**
 * Created by laroux0b on 27/04/2017.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Connect {

    public static final String url = "http://hottest100.org/";
    public Document doc;
    public static int hottest100Year;

    public Connect() throws IOException {

        for (int y = 1993; y < Year.now().getValue(); y++){
            scrapePages(y);
        }
        Mongo.queryClose();
    }

    public void scrapePages(Integer... years) {

        Map<Integer, Documents> hottest100ByYear = new HashMap<>();
        for (Integer year : years) {
            try {
                doc = Jsoup.connect(url+ year +".html").get();
                Documents documents = new Documents();
                hottest100Year = year;
                documents.processDocument(doc);
                hottest100ByYear.put(year, documents);

            } catch (IOException e) {
                e.printStackTrace();
            }
            printDocumentsMap(hottest100ByYear);
        }
    }

    private void printDocumentsMap(Map<Integer, Documents> hottest100ByYear) {
        Set<Map.Entry<Integer, Documents>> entries = hottest100ByYear.entrySet();

        for(Map.Entry<Integer, Documents> entry : entries){
            printDocumentsMapEntry(entry.getKey(), entry.getValue());
        }
    }

    private static void printDocumentsMapEntry(Integer key, Documents documents) {
//        documents.getHottest100().stream().forEach(d -> System.out.println(key + "," + d.toString()));
        for(Hottest100 hottest100 : documents.getHottest100()){
//            System.out.println(key + "," + hottest100);
            System.out.println(hottest100);
        }
    }

    public static int getHottest100Year(){
        return hottest100Year;
    }
}