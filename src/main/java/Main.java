/**
 * Created by laroux0b on 27/04/2017.
 */

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        //Set proxy settings
        System.setProperty("java.net.useSystemProxies", "true");
        System.setProperty("http.proxyHost", "DCA-WBSAPP-P001.rac.com.au");
        System.setProperty("http.proxyPort", "80");

        //Check if Server is already running
        if (!Server.isProcessRunning("MongoDB")) {

            Server server = new Server();

            try {
                server.startServer();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Create new Connection to MongoDB
        Mongo mongo = new Mongo();

        /* Check if the Database and Table exists, if it does exist open parameter dialog, if it doesn't exist, create
        database from scratch */
        if(Mongo.checkDatabaseStatus()){
            //if it does exist open parameter dialog
            Dialog dialog = new Dialog();
            //If user selects anything but "Query the existing database", create database from scratch
            if(Dialog.parameter != "Query the existing database"){
                mongo.createMongoDB();
                Connect connection = new Connect();
            }
            //If the user selects "Query the existing database", go straight to Search GUI
            else{
                SearchGUI search = new SearchGUI();
            }
        }
        //If the database and Table doesn't exist, create from scratch
        else{
            mongo.createMongoDB();
            Connect connection = new Connect();
            SearchGUI search = new SearchGUI();
        }

        //Close Connection and stop server if SearchGUI Closed
        if(!SearchGUI.searchGUIFrame.isVisible()) {
            Mongo.mongoClose();
            Server.stopServer();
        }
    }
}