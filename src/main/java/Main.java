import java.io.IOException;

class Main {

    static Mongo mongo;
    static Connect connection;
    private static SearchGUI search;
    private static Boolean doesDatabaseExist;
    private static String parameter;
    public static void main(String[] args) throws IOException, InterruptedException {

        //Set proxy settings
        setProxySettings();

        //Check if Server is already running
        if (!Server.isProcessRunning()) {
            System.out.println(Server.isProcessRunning());
            System.out.println("Server is already running");
            try {
                Server.startServer();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Create new Connection to MongoDB
        createMongoConnection();

        /* Check if the Database and Table exists, if it does exist open parameter dialog, if it doesn't exist, create
        database from scratch */
        if (doesDatabaseExist) {
            //if it does exist open parameter dialog
            createDialog();

            //If user selects anything but "Query the existing database", create database from scratch
            //            if(!Dialog.parameter.equals("Query the existing database")){
            if (!parameter.equals("Query the existing database")) {
                createAll();
            }
            //If the user selects "Query the existing database", go straight to Search GUI
            else {
                createSearchGUI();
            }
        }
        //If the database and Table doesn't exist, create from scratch
        else {
            createAll();
        }
    }

    private static void setProxySettings(){
        System.setProperty("java.net.useSystemProxies", "true");
        System.setProperty("http.proxyHost", "DCA-WBSAPP-P001.rac.com.au");
        System.setProperty("http.proxyPort", "80");
    }

    private static void createDialog() {
        Dialog dialog = new Dialog();
        dialog.prompt();
        parameter = dialog.getParameter();
    }

    private static void createMongoConnection(){
        mongo = new Mongo();
        mongo.createMongoConnection();
        mongo.checkDatabaseStatus();
        doesDatabaseExist = mongo.getDoesDatabaseExist();
    }

    private static void createAll() throws InterruptedException {
        mongo.createMongoDB();
        connection = new Connect();
        connection.connectToPages();
        search = new SearchGUI();
        search.createSearchGUI();
    }

    private static void createSearchGUI(){
        search = new SearchGUI();
        search.createSearchGUI();
    }
}