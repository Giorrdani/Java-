import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends JFrame {

    private static final String SERVER_ADDRES = "localhost";
    private static final int SERVER_PORT = 8189;

    private JTextField msgInputField;
    private JTextArea chatArea;


    private Socket socket = null;
    private DataInputStream in;
    private DataOutputStream out;

    public Client() {
        try {
            openConnection();
        } catch (IOException ignored) {
        }
        prepareGUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> {
            new Client();
        });
    }

    public void openConnection() throws IOException {
        socket = new Socket(SERVER_ADDRES, SERVER_PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        new Thread(() -> {
                while (true) {
                    try {
                    String strFromServer = in.readUTF();
                    if (strFromServer.equals("/end")) {
                        closeConnection();
                        break;
                    }
                    chatArea.append(strFromServer + "\n");
                }catch (IOException ignored) {
                    closeConnection();
                }
            }
            closeConnection();
        }).start();
    }

    public void closeConnection (){
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

    public void sendMessage (){
        if (!msgInputField.getText().trim().isEmpty()){
            try {
                String messageToServer = msgInputField.getText();
                out.writeUTF(messageToServer);
                if (messageToServer.equalsIgnoreCase("/end")) {
                    chatArea.append(messageToServer + "\n");
                    closeConnection();
                }
                chatArea.append(messageToServer + "\n");
                msgInputField.setText("");

            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Ошибка отправки сообщения");
            }
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
        chatArea.setFont(new Font("Times new Roman", Font.PLAIN, 20));
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

        btnSendMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        msgInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    out.writeUTF("/end");
                    closeConnection();
                  } catch (IOException ignore) {
                    closeConnection();
                }
            }
        });

        setVisible(true);
    }

}


