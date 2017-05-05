/**
 * Created by laroux0b on 27/04/2017.
 */

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("java.net.useSystemProxies", "true");
        System.setProperty("http.proxyHost", "DCA-WBSAPP-P001.rac.com.au");
        System.setProperty("http.proxyPort", "80");

        Server server = new Server();
        try {
            server.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Mongo mongo = new Mongo();

        Connect connection = new Connect();

        server.stopServer();
    }
}