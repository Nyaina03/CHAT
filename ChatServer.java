package clientserver2;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServer extends javax.swing.JFrame {

    public ChatServer() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaChat = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaMessage = new javax.swing.JTextArea();
        jButtonSend = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.BorderLayout(10, 10));

        jTextAreaChat.setColumns(20);
        jTextAreaChat.setRows(5);
        jTextAreaChat.setText("\n");
        jScrollPane1.setViewportView(jTextAreaChat);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));
        jPanel1.setBackground(Color.MAGENTA);

        jTextAreaMessage.setColumns(20);
        jTextAreaMessage.setRows(5);
        jTextAreaMessage.setBackground(Color.LIGHT_GRAY);
        jScrollPane2.setViewportView(jTextAreaMessage);

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jButtonSend.setText("Send");
        jButtonSend.setBackground(Color.BLACK);
        jButtonSend.setForeground(Color.GREEN);
        jButtonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonSend, java.awt.BorderLayout.LINE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        setBounds(0, 0, 407, 342);
    }// </editor-fold>                        

    private void jButtonSendActionPerformed(java.awt.event.ActionEvent evt) {                                            
        String message = jTextAreaMessage.getText();
        writer.println(message);
        jTextAreaChat.append("Server: " + message + "\n");
        jTextAreaMessage.setText("");     
    }                                           

    private void formWindowOpened(java.awt.event.WindowEvent evt) {                                  

        try {
            // TODO add your handling code here:
            serverSocket = new ServerSocket(4789);
            clients = new ArrayList<>();
            pool = Executors.newFixedThreadPool(4);

            Thread myThread = new Thread(new Runnable() {

                @Override
                public void run() {

                    while (true) {

                        try {
                            jTextAreaChat.append("Waiting for Client..." + "\n");
                            socket = serverSocket.accept();
                            jTextAreaChat.append("Client found." + "\n");
                            ClientHandler clientThread = new ClientHandler(socket);
                            clients.add(clientThread);
                            pool.execute(clientThread);
                        } catch (IOException ex) {
                            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            });
            myThread.start();
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }                                 

             
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatServer().setVisible(true);
            }
        });
    }
    
    public class ClientHandler implements Runnable {
        //private final Socket socket;        
        public ClientHandler(Socket socket) throws IOException {
            //this.socket = clientSocket;
            scanner = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream(), true);
        }

        @Override
        public void run() {                      
            while (true) {
                String message = scanner.nextLine();
                jTextAreaChat.append("Client: " + message + "\n");

                if (message.equalsIgnoreCase("products")) {
                    try {
                        File file = new File("C:\\Users\\jades\\Desktop\\Java\\ClientServer2\\src\\clientserver2\\Products.txt");
                        Scanner sc = new Scanner(file);
                        while (sc.hasNextLine()) {
                            writer.println(sc.nextLine());
                            writer.flush();
                        }
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (message.equalsIgnoreCase("customers")) {
                    try {
                        File file = new File("C:\\Users\\jades\\Desktop\\Java\\ClientServer2\\src\\clientserver2\\Customers.txt");
                        Scanner sc = new Scanner(file);
                        while (sc.hasNextLine()) {
                            writer.println(sc.nextLine());
                            writer.flush();
                        }
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
               
        }       
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButtonSend;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaChat;
    private javax.swing.JTextArea jTextAreaMessage;
    // End of variables declaration                   
    private ServerSocket serverSocket;
    private Socket socket;
    private Scanner scanner;
    private PrintWriter writer;
    private static ArrayList<ClientHandler> clients;
    private static ExecutorService pool;
}
