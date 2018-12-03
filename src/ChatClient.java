
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ricky K
 */
public class ChatClient extends javax.swing.JFrame {

    /**
     * Creates new form ChatClient
     */
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket socket;
    private String server, username;
    private int port;
    private List<String> clients;
    private ArrayList <DataOnline> users;
    private ArrayList <ChatHistory> group;
    private DataSql client = new DataSql();
    private DataOnlineUsers online = new DataOnlineUsers();
    private DefaultTableModel temp = new DefaultTableModel();
    private ChatHistoryDB history = new ChatHistoryDB();
    private Statement statement;
    private byte[] gambar;
    private int count = 0;
    private ActionEvent ae;
  
    public ChatClient() {
        clients = new ArrayList<>();
        users = new ArrayList<DataOnline>();
        group = new ArrayList<ChatHistory>();
        initComponents();
        temp.addColumn("Clients");
        
        Object data[] = history.getAllData();
        for(int i=0;i<data.length;i++)
        {
            ChatHistory data1 = (ChatHistory) data[i];
            group.add(new ChatHistory(data1.getId_chat(),data1.getNama_users(),data1.getHistory()));
        }
         for(int i=0;i<group.size();i++)
                            {
                                 String message = group.get(i).getNama_users() + ": " + group.get(i).getHistory();
                                 viewTextArea.setText(viewTextArea.getText()+message+ "\n");
                            }
        //SendVirtualActionPerformed(evt);
    }

    public boolean start() {
        try {
            socket = new Socket(server, port);
        } catch (Exception ec) {
            System.out.println("Error connectiong to server:" + ec);
            return false;
        }
        
        String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
        System.out.println(msg);
 
        try {
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException eIO) {
            System.out.println("Exception creating new Input/output Streams: " + eIO);
            return false;
        }
        
        new ChatClient.ListenFromServer().start();
 
        try {
            output.writeObject("login~" + username + "~" + username + " sedang login...~server~\n");
            output.writeObject("list~" + username + "~" + username + " sedang login...~server~\n");
 
        } catch (IOException eIO) {
            System.out.println("Exception doing login : " + eIO);
            disconnect();
            return false;
        }
        //JOptionPane.showMessageDialog(null, count);
//        if(count==0)
//        {
//            for(int i=0;i<group.size();i++)
//            {
//                 String message = group.get(i).getNama_users() + ": " + group.get(i).getHistory();
//                 viewTextArea.setText(viewTextArea.getText()+message+ "\n");
//                 //res = pengirim + ": " + text;
//                         //   viewTextArea.setText(viewTextArea.getText() + res + "\n");
////               try {
////                String message = "postText~" + group.get(i).getNama_users() + "~" + group.get(i).getHistory() + "~all~\n";
////                output.writeObject(message);
////                } catch (IOException ex) {
////                Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
////                }
//            }
//        }
         try {
                    statement = DB_MANAGER.get_connection().createStatement();
                    ResultSet rs = statement.executeQuery("select * from tbl_client where Nama_Client = '"+usernameTextField.getText()+"'");
                    if(rs.next()){
                       
                    byte[] img = rs.getBytes("Foto");
                    gambar = img;
                     
                    //label1.setSize(50, 50);
                    //Resize The ImageIcon
                    ImageIcon image = new ImageIcon(img);
                    Image im = image.getImage();
                    Image myImg = im.getScaledInstance(label1.getWidth(), label1.getHeight(),Image.SCALE_SMOOTH);
                    ImageIcon newImage = new ImageIcon(myImg);
                    
                    //String n = new String(img);
                    //label1.setText(n);
                    label1.setIcon(newImage);
                }
                
                else{
                    JOptionPane.showMessageDialog(null, "No Data");
                }
                } catch (SQLException ex) {
                    Logger.getLogger(ChatClient2.class.getName()).log(Level.SEVERE, null, ex);
                }
         count = 1;
        return true;
    }
    
    private void disconnect() {
        try {
            // TODO add your handling code here:
            output.writeObject("logout~" + username + "~" + username + " sudah logout...~Server~\n");
            //output.writeObject("list~" + username + "~" + username + " sedang login...~server~\n");
        } catch (IOException ex) {
            //Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        try {
            if (input != null) {
                input.close();
            }
        } catch (Exception e) {
        }
        try {
            if (output != null) {
                output.close();
            }
        } catch (Exception e) {
        }
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
        }
    }
    private void disconnectkhusus() {
//        try {
//            // TODO add your handling code here:
//            output.writeObject("logout~" + username + "~" + username + " sudah logout...~Server~\n");
//            //output.writeObject("list~" + username + "~" + username + " sedang login...~server~\n");
//        } catch (IOException ex) {
//            //Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
//        }
 
        try {
            if (input != null) {
                input.close();
            }
        } catch (Exception e) {
        }
        try {
            if (output != null) {
                output.close();
            }
        } catch (Exception e) {
        }
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
        }
    }
     private void refreshTabled() {
            ArrayList <String> makan = new ArrayList<String>();
            Object data[] = client.getAllStatus();
            for(int i=0;i<data.length;i++)
            {
                DataOnline data1 = (DataOnline) data[i];
                makan.add(data1.getNama());
                users.add(new DataOnline(data1.getNama()));
            }
            int a = makan.size();
            String[] header = {"Clients"};
            //Object[][] data = new Objenct[a][1];
            DefaultTableModel dm = new DefaultTableModel();
            dm.addColumn("Clients");
            //dm.addRow(data);
            
            for(int i=0;i<a;i++)
            {
                //String b = clientTable.getModel().getValueAt(i, 0).toString();
               //dm.addRow(new Object[]{(users.get(i).getNama())});
                dm.addRow(new Object[]{(makan.get(i).toString())});
            }
            for(int i=0;i<a;i++)
            {
                System.out.print(makan.get(i).toString());
            }
            clientTable.setModel(dm);
        }
     public void actionPerformed(ActionEvent ae) {
           ArrayList <String> makan = new ArrayList<String>();
            Object data[] = client.getAllStatus();
            for(int i=0;i<data.length;i++)
            {
                DataOnline data1 = (DataOnline) data[i];
                makan.add(data1.getNama());
                users.add(new DataOnline(data1.getNama()));
            }
            int a = makan.size();
            String[] header = {"Clients"};
            //Object[][] data = new Object[a][1];
            DefaultTableModel dm = new DefaultTableModel();
            dm.addColumn("Clients");
            //dm.addRow(data);
            
            for(int i=0;i<a;i++)
            {
                //String b = clientTable.getModel().getValueAt(i, 0).toString();
               //dm.addRow(new Object[]{(users.get(i).getNama())});
                dm.addRow(new Object[]{(makan.get(i).toString())});
            }
            for(int i=0;i<a;i++)
            {
                System.out.print(makan.get(i).toString());
            }
            clientTable.setModel(dm);
        }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        usernameTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        portTextField = new javax.swing.JTextField();
        serverTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        masukButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btn_masuk = new javax.swing.JButton();
        SendVirtual = new javax.swing.JButton();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        label1 = new javax.swing.JLabel();
        User = new javax.swing.JTextField();
        Btn_Putus = new javax.swing.JButton();
        refresh = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        clientTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        viewTextArea = new javax.swing.JTextArea();
        postTextField = new javax.swing.JTextField();
        kirimButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        usernameTextField.setText("user");

        jLabel3.setText("Username");

        portTextField.setText("2222");

        serverTextField.setText("localhost");

        jLabel2.setText("Port");

        jLabel1.setText("Server");

        masukButton.setText("Masuk");
        masukButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masukButtonActionPerformed(evt);
            }
        });

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btn_masuk.setText("jButton3");

        SendVirtual.setText("jButton1");
        SendVirtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendVirtualActionPerformed(evt);
            }
        });

        jMenu1.setText("jMenu1");

        jMenu2.setText("jMenu2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 204, 0));

        label1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label1MouseClicked(evt);
            }
        });

        User.setEditable(false);
        User.setBackground(new java.awt.Color(204, 204, 204));
        User.setFont(new java.awt.Font("Tahoma", 3, 16)); // NOI18N
        User.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserActionPerformed(evt);
            }
        });

        Btn_Putus.setBackground(new java.awt.Color(255, 204, 204));
        Btn_Putus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-shutdown-50.png"))); // NOI18N
        Btn_Putus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_PutusActionPerformed(evt);
            }
        });

        refresh.setBackground(new java.awt.Color(255, 255, 255));
        refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsz_icons8-synchronize-240.png"))); // NOI18N
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });

        clientTable.setBackground(new java.awt.Color(102, 255, 102));
        clientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        clientTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clientTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(clientTable);

        viewTextArea.setEditable(false);
        viewTextArea.setColumns(20);
        viewTextArea.setRows(5);
        jScrollPane1.setViewportView(viewTextArea);

        postTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postTextFieldActionPerformed(evt);
            }
        });

        kirimButton.setBackground(new java.awt.Color(51, 153, 255));
        kirimButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-email-send-50.png"))); // NOI18N
        kirimButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kirimButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(User, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Btn_Putus))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(postTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kirimButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(User, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Btn_Putus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refresh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(postTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kirimButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jMenu5.setText("File");

        jMenuItem2.setText("Single Chat");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem2);

        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem1);

        jMenuBar1.add(jMenu5);

        jMenu6.setText("Help");

        jMenuItem3.setText("About Us");
        jMenu6.add(jMenuItem3);

        jMenuBar1.add(jMenu6);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void masukButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masukButtonActionPerformed
        // TODO add your handling code here:
        this.server = serverTextField.getText();
        this.port = new Integer(portTextField.getText());
        this.username = usernameTextField.getText();
        start();
    }//GEN-LAST:event_masukButtonActionPerformed

    private void kirimButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kirimButtonActionPerformed
        // TODO add your handling code here:
        if(postTextField.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Jangan Kosong !!!");
        }
        else
        {
            try {
            String message = "postText~" + username + "~" + postTextField.getText() + "~all~\n";
            output.writeObject(message);
            history.addHistory(usernameTextField.getText(), postTextField.getText());
            postTextField.setText("");
            } catch (IOException ex) {
                Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
    }//GEN-LAST:event_kirimButtonActionPerformed

    private void postTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postTextFieldActionPerformed
        // TODO add your handling code here:
        kirimButtonActionPerformed(evt);
    }//GEN-LAST:event_postTextFieldActionPerformed

    private void Btn_PutusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_PutusActionPerformed
        // TODO add your handling code here:
        disconnect();
        client.updateStatus(0, usernameTextField.getText());
        System.exit(0);
        //refreshTable();
    }//GEN-LAST:event_Btn_PutusActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void UserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UserActionPerformed

    private void clientTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clientTableMouseClicked
        // TODO add your handling code here:
        refreshTabled();
    }//GEN-LAST:event_clientTableMouseClicked

    private void SendVirtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendVirtualActionPerformed
        // TODO add your handling code here:
         
    }//GEN-LAST:event_SendVirtualActionPerformed

    private void label1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label1MouseClicked
        // TODO add your handling code here:
        GantiGambar n = new GantiGambar();
        n.nama = usernameTextField.getText();
        n.ip = serverTextField.getText();
        n.user = User.getText();
        n.images = gambar;
        n.room = 1;
        n.setTitle("Ganti Foto Profil");
        n.setVisible(true);
        n.starts();
        disconnectkhusus();
        dispose();
    }//GEN-LAST:event_label1MouseClicked

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        // TODO add your handling code here:
        refreshTabled();
    }//GEN-LAST:event_refreshActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        ChatClient2 d = new ChatClient2();
        d.setTitle("Single Chat");
        d.usernameTextField.setText(usernameTextField.getText());
        d.portTextField.setText("2222");
        d.serverTextField.setText(serverTextField.getText());
        d.User.setText(usernameTextField.getText());
        d.btn_masukiActionPerformed(evt);
        d.setVisible(true);
        dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        disconnect();
        client.updateStatus(0, usernameTextField.getText());
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed
    
    public void btn_masukActionPerformed(java.awt.event.ActionEvent evt)
    {
        this.server = serverTextField.getText();
        this.port = new Integer(portTextField.getText());
        this.username = usernameTextField.getText();
        start();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatClient().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Putus;
    private javax.swing.JButton SendVirtual;
    public static javax.swing.JTextField User;
    private javax.swing.JButton btn_masuk;
    private javax.swing.JTable clientTable;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton kirimButton;
    private javax.swing.JLabel label1;
    public static javax.swing.JButton masukButton;
    public static javax.swing.JTextField portTextField;
    private javax.swing.JTextField postTextField;
    private javax.swing.JButton refresh;
    public static javax.swing.JTextField serverTextField;
    public static javax.swing.JTextField usernameTextField;
    private javax.swing.JTextArea viewTextArea;
    // End of variables declaration//GEN-END:variables
    
    class ListenFromServer extends Thread implements ActionListener {
        private ActionEvent ae;
        @Override
        public void run() {
            while (true) {
                actionPerformed(ae);
                try {
                    String msg = (String) input.readObject();
                    String res;
                    String type = msg.split("~")[0];
                    String pengirim = msg.split("~")[1];
                    String text = msg.split("~")[2];
                    String kepada = msg.split("~")[3];
                    switch (type) {
                        case "recieveText":
                            res = pengirim + ": " + text;
                            viewTextArea.setText(viewTextArea.getText() + res + "\n");
                            break;
                        case "recievePrivateText":
                            res = pengirim + ": " + text;
                            if (kepada.equals(username)) {
                                viewTextArea.setText(viewTextArea.getText() + res + "\n");
                            }
                            break;
                        case "login":
                            //viewTextArea.setText("");
                            viewTextArea.setText(viewTextArea.getText() + pengirim + " sedang login..." + "\n");
//                            for(int i=0;i<group.size();i++)
//                            {
//                                 String message = group.get(i).getNama_users() + ": " + group.get(i).getHistory();
//                                 viewTextArea.setText(viewTextArea.getText()+message+ "\n");
//                            }
                            temp.addRow(new Object[]{usernameTextField.getText()});
                            clients.add(pengirim);
                            
                            break;
                        case "logout":
                            viewTextArea.setText(viewTextArea.getText() + pengirim + " telah logout..." + "\n");
                            //setTable(text);
                            actionPerformed(ae);
                            
                            clients.remove(pengirim);
                            
                            break;
                        case "list":
                            setTable(text);
                            break;
                    }
                } catch (IOException e) {
                    System.out.println("Server has close the connection: " + e);
                    break;
                } catch (ClassNotFoundException e2) {
                }
            }
        }
        private void setTable(String text) {
            int rows = text.split(":").length - 1;
            Object[][] data = new Object[rows][1];
            for (int i = 0; i < rows; i++) {
                String t = text.split(":")[i];
                data[i][0] = t;
            }
 
            String[] header = {"Clients"};
 
            clientTable.setModel(new DefaultTableModel(data, header));
        }
        public void refreshTabled() {
            ArrayList <String> makan = new ArrayList<String>();
            Object data[] = client.getAllStatus();
            for(int i=0;i<data.length;i++)
            {
                DataOnline data1 = (DataOnline) data[i];
                makan.add(data1.getNama());
                users.add(new DataOnline(data1.getNama()));
            }
            int a = makan.size();
            String[] header = {"Clients"};
            //Object[][] data = new Object[a][1];
            DefaultTableModel dm = new DefaultTableModel();
            dm.addColumn("Clients");
            //dm.addRow(data);
            
            for(int i=0;i<a;i++)
            {
                //String b = clientTable.getModel().getValueAt(i, 0).toString();
               //dm.addRow(new Object[]{(users.get(i).getNama())});
                dm.addRow(new Object[]{(makan.get(i).toString())});
            }
            for(int i=0;i<a;i++)
            {
                System.out.print(makan.get(i).toString());
            }
            clientTable.setModel(dm);
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
           ArrayList <String> makan = new ArrayList<String>();
            Object data[] = client.getAllStatus();
            for(int i=0;i<data.length;i++)
            {
                DataOnline data1 = (DataOnline) data[i];
                makan.add(data1.getNama());
                users.add(new DataOnline(data1.getNama()));
            }
            int a = makan.size();
            String[] header = {"Clients"};
            //Object[][] data = new Object[a][1];
            DefaultTableModel dm = new DefaultTableModel();
            dm.addColumn("Clients");
            //dm.addRow(data);
            
            for(int i=0;i<a;i++)
            {
                //String b = clientTable.getModel().getValueAt(i, 0).toString();
               //dm.addRow(new Object[]{(users.get(i).getNama())});
                dm.addRow(new Object[]{(makan.get(i).toString())});
            }
            for(int i=0;i<a;i++)
            {
                System.out.print(makan.get(i).toString());
            }
            clientTable.setModel(dm);
        }
       
    }
}
