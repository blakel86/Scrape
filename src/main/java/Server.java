/**
 * Created by laroux0b on 5/05/2017.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Server {

    private static Process process;
    public static String FILEPATH = "D:/Apps/MongoDB/Server/3.4/bin/mongod.exe";
    public String line;
    public String pidInfo ="";

    public Server() throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec(System.getenv(FILEPATH));
        BufferedReader input =  new BufferedReader(new InputStreamReader(p.getInputStream()));

        while ((line = input.readLine()) != null) {
            pidInfo+=line;
        }

        input.close();

        if(pidInfo.contains("your process name")) {
            System.out.println("MongoDB server already running");
        }
        else{
            startServer();
        }
    }

    public void startServer() throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        process = runtime.exec(FILEPATH);
        Thread.sleep(5000);
        if (process != null) {
            System.out.println("MongoDB server started");
        }
    }

    public static void stopServer() {
        if (process != null) {
            process.destroy();
        }
        System.out.println("MongoDB server stopped");
    }

    public static boolean isProcessRunning(String processName) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(Server.FILEPATH);
        Process process = processBuilder.start();
        String tasksList = toString(process.getInputStream());

        return tasksList.contains(processName);
    }

    private static String toString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
        String string = scanner.hasNext() ? scanner.next() : "";
        scanner.close();

        return string;
    }
}
