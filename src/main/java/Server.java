import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Server {

    private static Process process;

    static void startServer() throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        String FILEPATH = "D:/Apps/MongoDB/Server/3.4/bin/mongod.exe";
        process = runtime.exec(FILEPATH);
        Thread.sleep(5000);
        if (process != null) {
            System.out.println("MongoDB server started");
        }
    }

    static void stopServer() {
        if (process != null) {
            process.destroy();
        }
        System.out.println("MongoDB server stopped");
    }

    static boolean isProcessRunning() {
        try {
            String line;
            Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line);
                String processName = "mongod.exe";
                if (line.contains(processName)) {
                    return true;
                }
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
        //split line on white space line.split
        //check first index
        return false;
    }


}