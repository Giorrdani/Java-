package clientside.one;


import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoClient extends JFrame {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
                new EchoClient();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private final static String IP_ADDRESS = "localhost"; //127.0.0.1 ip address
    private final static int SERVER_PORT = 8081;

    private JTextField msgInputField;
    private static JTextArea chatArea;

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private static boolean timer = true;

    private boolean isAuthorized;

    public EchoClient() throws FileNotFoundException {
        try {
            connection();
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
        prepareGUI();
    }
    ExecutorService executorService = Executors.newFixedThreadPool(1);

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    private void connection() throws IOException {
        socket = new Socket(IP_ADDRESS, SERVER_PORT);
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
        setAuthorized(false);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    new Thread(() -> {
                        long timeMillis = System.currentTimeMillis();
                        while (timer) {
                            if (System.currentTimeMillis() - timeMillis >= 120000 && !isAuthorized) {
                                System.out.println("Time authorized is end");
                                closeConnection();
                                timer = false;
                                break;
                            }
                        }
                    }).start();
                    while (true) {
                        try {
                            String serverMessage = dis.readUTF();
                            if (serverMessage.startsWith("/auth ok - ")) {
                                setAuthorized(true);
                                showCash();
                                chatArea.append(serverMessage + "\n");
                                break;
                            }
                            chatArea.append(serverMessage + "\n");
                        } catch (IOException ignored) {
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    while (true) {
                        String serverMessage = dis.readUTF();
                        if (serverMessage.equals("/q")) {
                            break;
                        }
                        chatArea.append(serverMessage + "\n");
                    }

                } catch (IOException ignored) {
                    ignored.printStackTrace();
                }
                closeConnection();
            }
        });
        executorService.shutdown();
    }
    private void sendMessageToServer() {
        if (!msgInputField.getText().trim().isEmpty()) {
            try {
                String messageToServer = msgInputField.getText();
                dos.writeUTF(messageToServer);
                msgInputField.setText("");
            } catch (IOException ignored) {

            }
        }
    }

    public static void cashChat() throws BadLocationException, IOException {
        List<String> list = new ArrayList<>();
        String s = chatArea.getText();
        String [] asd = s.split("\n");
        for (String a : asd){
            list.add(a);
        }

        if (list.size()>10){
            list = list.subList(10, list.size());
        }else {
            list.subList(0,list.size());
        }
        String joined = String.join("\n", list);
        try (FileWriter fileWriter = new FileWriter("src\\liveCash.txt",true)) {
            fileWriter.write(joined + "\n");
        }
    }

    public void showCash () throws IOException, ClassNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\liveCash.txt"))) {
            String s;
            List <String> str = new ArrayList<>();
            while ((s = reader.readLine()) !=null) {
            str.add(s);
            }
            if (str.size()> 10){
                str = str.subList(str.size() - 10, str.size());
            }else {
                str = str.subList(0, str.size()-1);
            }
            String joined = String.join("\n", str);
            chatArea.append(joined + "\n");
        }
    }

    public void prepareGUI() {
        setBounds(600, 300, 500, 500);
        setTitle("Клиент");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        chatArea = new JTextArea();
        chatArea.setPreferredSize( new Dimension(390, 500));
        chatArea.setEnabled(false);
        chatArea.setBackground(new Color(15, 200, 150));
        chatArea.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setDisabledTextColor(Color.WHITE);



        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton btnSendMsg = new JButton("Отправить");
        bottomPanel.add(btnSendMsg, BorderLayout.EAST);
        msgInputField = new JTextField();
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(msgInputField, BorderLayout.CENTER);

        btnSendMsg.addActionListener(e -> {
            sendMessageToServer();
        });

        msgInputField.addActionListener(e -> {
            sendMessageToServer();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    dos.writeUTF("/q");
                    cashChat();
                } catch (BadLocationException badLocationException) {
                        badLocationException.printStackTrace();
                } catch (IOException ignored) {
                }
            }
        });

        setVisible(true);
    }

    private void closeConnection() {
        try {
            dos.flush();
        } catch (IOException ignored) {
        }

        try {
            dis.close();
        } catch (IOException ignored) {
        }

        try {
            dos.close();
        } catch (IOException ignored) {
        }

        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }
}




