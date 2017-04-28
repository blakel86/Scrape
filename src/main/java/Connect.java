import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by laroux0b on 27/04/2017.
 */
public class Connect {

    public static final String url = "http://hottest100.org/";
    public Document doc;

    //    public static void connect() throws IOException {
    public Connect() throws IOException {
        System.setProperty("http.proxyHost", "DCA-WBSAPP-P001.rac.com.au");
        System.setProperty("http.proxyPort", "80");

//        String encoded = new String("bGFyb3V4MGI6MTk4OUdyYWNl");
//        String encoded = new String(Base64.encode(new String("laroux0b:1989Grace").getBytes()));

//        URL url=new URL("http://hottest100.org/1994.html");
//        URLConnection uc = url.openConnection();
//
////        uc.setRequestProperty("Proxy-Authorization", "Basic " + encoded);
//        uc.connect();
//
        for (int y = 1993; y < Year.now().getValue(); y++){
            scrapePages(y);
        }
    }

    public void scrapePages(Integer... years) {

        Map<Integer, Documents> hottest100ByYear = new HashMap<>();
        for (Integer year : years) {
            try {
                doc = Jsoup.connect(url+ year +".html").get();
                Documents documents = new Documents();
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
            System.out.println(key + "," + hottest100);
        }
    }
}