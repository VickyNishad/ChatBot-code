
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Client() {
        try {
            System.out.println("sending request to server");
            socket = new Socket("127.0.0.1", 7778);
            System.out.println("server is ready to accept connection");
            System.out.println("connection done.");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void startReading() {
        Runnable runnable1 = () -> {
            System.out.println("reader started...");

            while (true) {
                try {
                    String msg;
                    msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Server terminated the chat");
                        // JOptionPane.showMessageDialog(this, "server terminated the chat");
                        break;
                    }
                    System.out.println("Server : " + msg);
                } catch (IOException e) {

                    e.printStackTrace();
                }

            }

        };
        new Thread(runnable1).start();
    }

    public void startWriting() {

        Runnable runnable2 = () -> {
            System.out.println("writer started...");

            while (true) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                    var content = bufferedReader.readLine();
                    out.println(content);
                    out.flush();
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

        };
        new Thread(runnable2).start();
    }

    public static void main(String[] args) {
        System.out.println("this is client...");
        new Client();
    }
}
