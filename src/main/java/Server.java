/**
 * Created by laroux0b on 5/05/2017.
 */

import java.io.IOException;

public class Server {

    private static Process process;

    public static String FILEPATH = "D:/Apps/MongoDB/Server/3.4/bin/mongod.exe";

    public void startServer() throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        process = runtime.exec(FILEPATH);
        Thread.sleep(5000);
        if (process != null) {
            System.out.println("MongoDB server started");
        }
    }

    public void stopServer() {
        if (process != null) {
            process.destroy();
        }
        System.out.println("MongoDB server stopped");
    }
}
