package serverside.service;

import serverside.interfce.AuthenticationService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class MyServer {
    private static final Logger logger = Logger.getLogger(MyServer.class.getName());
    static Handler handler;

    private final int PORT = 8081;

    private List<ClientHandler> clientsList;
    private AuthenticationService authService;

    public AuthenticationService getAuthService() {
        return this.authService;
    }

    public MyServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            handler = new FileHandler("ch");
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
            logger("Server start");
            this.authService = new AuthenticationServiceImpl();
            authService.start();
            clientsList = new ArrayList<>();

            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            logger("Server error");
        } finally {
            if (authService != null) {
                authService.stop();
            }
        }
    }

    public synchronized void sendMessageToClients(String message) {
            if (message.startsWith(": ",1) && !message.startsWith(" /q",2)){
                logger("Client send message");
            }
            for (ClientHandler c : clientsList) {
                c.sendMessage(message);
            }
    }

    public void sendMessageToOneClient (ClientHandler clientHandler, String nick, String message) {
        for(ClientHandler c : clientsList){
            if (c.getName().equalsIgnoreCase(nick)){
                c.sendMessage("PM from " + clientHandler.getName().toUpperCase() + ": " + message);
                clientHandler.sendMessage("PM for " + nick.toUpperCase() + ": " + message);
            }
        }
    }

    public synchronized void subscribe(ClientHandler c) {
        logger("Client connect");
        clientsList.add(c);
    }

    public synchronized void unSubscribe(ClientHandler c) {
        clientsList.remove(c);
    }

    public synchronized boolean isNickBusy(String nick) {
        return clientsList.stream()
                .anyMatch(a -> a.getName().equals(nick));
    }

    public synchronized void editNick(ClientHandler c, String newNick){
        for (ClientHandler x : clientsList){
            if (x.getName().equals(c.getName())){
                x.editName(newNick);
            }
        }
    }

    public static void logger (String msg){
        logger.log(Level.INFO,msg);
    }

}
