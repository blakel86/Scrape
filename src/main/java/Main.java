/**
 * Created by laroux0b on 27/04/2017.
 */

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("java.net.useSystemProxies", "true");
        System.setProperty("http.proxyHost", "DCA-WBSAPP-P001.rac.com.au");
        System.setProperty("http.proxyPort", "80");

        if (!Server.isProcessRunning("MongoDB")) {

            Server server = new Server();

            try {
                server.startServer();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        Mongo mongo = new Mongo();

        if(Mongo.checkDatabaseStatus()){
            Dialog dialog = new Dialog();
            if(Dialog.parameter != "Query the existing database"){
                mongo.createMongoDB();
                Connect connection = new Connect();
            }
            else{
                SearchGUI search = new SearchGUI();
            }
        }
        else{
            mongo.createMongoDB();
            Connect connection = new Connect();
            SearchGUI search = new SearchGUI();
        }

        if(!SearchGUI.searchGUIFrame.isVisible()) {
            Mongo.mongoClose();
            Server.stopServer();
        }
    }
}