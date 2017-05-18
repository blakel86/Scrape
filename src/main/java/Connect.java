import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;

class Connect {

    private Document doc;
    private int hottest100Year;

    void connectToPages() throws InterruptedException {
        for (int y = 2014; y <= Year.now().getValue(); y++){
            try{
                String url = "http://hottest100.org/";
                doc = Jsoup.connect(url + y + ".html").get();
            }
            catch(IOException e) {
                break;
            }
            hottest100Year = y;
            scrapePages();
        }
        Main.mongo.queryClose();
    }

    private void scrapePages() {
        ArrayList<Documents> documentList = new ArrayList<>();
        Documents documents = new Documents();
        documents.processDocument(doc);
        documentList.add(documents);
        printDocumentsList(documents);
    }

    private void printDocumentsList(Documents documents) {
        for(Hottest100 hottest100 : documents.getHottest100()){
            System.out.println(hottest100);
        }
    }

    int getHottest100Year(){
        return hottest100Year;
    }

// --Commented out by Inspection STOP (18/05/2017 12:53 PM)

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