import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static Socket socket = null;
    static DataInputStream in;
    static DataOutputStream out;
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(8189)){
            System.out.println("Сервер запустился");
            socket = serverSocket.accept();
            System.out.println("Клиент подлючился");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            while (true) {

                new Thread(()-> {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            System.out.println(str);
                            if (str.equals("/end")) {
                                break;
                            }
                        }
                    }catch (IOException e) {
                        closeConnection();
                    }
                closeConnection();
                }).start();

                while(true){
                String str2 = bf.readLine();
                if (!str2.isEmpty()) {
                    out.writeUTF(str2);
                }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public static void closeConnection (){
        try {
            out.flush();
        } catch (IOException ignored) {}

        try {
            in.close();
        } catch (IOException ignored) {}

        try {
            out.close();
        } catch (IOException ignored) {}

        try {
            socket.close();
        } catch (IOException ignored) {}
    }


}