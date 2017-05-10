/**
 * Created by laroux0b on 27/04/2017.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;

public class Connect {

    public static final String url = "http://hottest100.org/";
    public Document doc;
    public static int hottest100Year;
    public static int yearCount;

    public Connect() throws IOException, InterruptedException {
        for (int y = 1993; y <= Year.now().getValue(); y++){
            try{
                doc = Jsoup.connect(url + y + ".html").get();
            }
            catch(IOException e) {
                break;
            }
            hottest100Year = y;
            yearCount++;
            scrapePages();
        }
        Mongo.queryClose();
    }

    public void scrapePages() {
        ArrayList<Documents> documentList = new ArrayList<>();
        Documents documents;
        documents = new Documents();
        documents.processDocument(doc);
        documentList.add(documents);
        printDocumentsList(documents);
    }

    private static void printDocumentsList(Documents documents) {
        for(Hottest100 hottest100 : documents.getHottest100()){
            System.out.println(hottest100);
        }
    }

    public static int getHottest100Year(){
        return hottest100Year;
    }

    public static int getYearCount() {
        return yearCount;
    }

    //    private void printDocumentsMap(Map<Integer, Documents> hottest100ByYear) {
//        Set<Map.Entry<Integer, Documents>> entries = hottest100ByYear.entrySet();
//
//        for(Map.Entry<Integer, Documents> entry : entries){
//            printDocumentsMapEntry(entry.getKey(), entry.getValue());
//        }
//    }

//    private static void printDocumentsMapEntry(Integer key, Documents documents) {
////        documents.getHottest100().stream().forEach(d -> System.out.println(key + "," + d.toString()));
//        for(Hottest100 hottest100 : documents.getHottest100()){
////            System.out.println(key + "," + hottest100);
//            System.out.println(hottest100);
//        }
//    }
}