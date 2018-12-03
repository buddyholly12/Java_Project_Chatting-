
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
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
public class ChatClient2 extends javax.swing.JFrame {

    /**
     * Creates new form ChatClient
     */
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket socket;
    private String server, username;
    private int port;
    private List<String> clients;
    private List<PMDialog> dialogs;
    private DataSql client = new DataSql();
    private Statement statement;
    private byte[] gambar;
    private boolean cek = false;
    private ArrayList <DataOnline> users;
    private DataOnlineUsers online = new DataOnlineUsers();
    private ArrayList <SingleChat> solo = new ArrayList<SingleChat>();
    private SingleChatDB history = new SingleChatDB();
  
    public ChatClient2() {
        clients = new ArrayList<>();
        users = new ArrayList<DataOnline>();
        dialogs = new ArrayList<>();
        initComponents();
        
        
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
        
        new ChatClient2.ListenFromServer().start();
 
        try {
            output.writeObject("login~" + username + "~" + username + " sedang login...~server~\n");
            output.writeObject("list~" + username + "~" + username + " sedang login...~server~\n");
 
        } catch (IOException eIO) {
            System.out.println("Exception doing login : " + eIO);
            disconnect();
            return false;
        }
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
        return true;
    }
    public void keluar()
    {
        JOptionPane.showMessageDialog(null, "Exit");
    }
    
    private void disconnect() {
        try {
            // TODO add your handling code here:
            output.writeObject("logout~" + username + "~" + username + " sudah logout...~Server~\n");
           // output.writeObject("list~" + username + "~" + username + " sedang login...~server~\n");
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
        serverTextField = new javax.swing.JTextField();
        portTextField = new javax.swing.JTextField();
        usernameTextField = new javax.swing.JTextField();
        masukButton = new javax.swing.JButton();
        postTextField = new javax.swing.JTextField();
        kirimButton = new javax.swing.JButton();
        btn_masuki = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        viewTextArea = new javax.swing.JTextArea();
        refreshgmbar = new javax.swing.JButton();
        jMenu4 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        User = new javax.swing.JTextField();
        label1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        clientTable = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btn_pilih = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
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

        serverTextField.setText("localhost");

        portTextField.setText("2222");

        usernameTextField.setText("ads");

        masukButton.setText("Masuk");
        masukButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masukButtonActionPerformed(evt);
            }
        });

        postTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postTextFieldActionPerformed(evt);
            }
        });

        kirimButton.setText("Send");
        kirimButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kirimButtonActionPerformed(evt);
            }
        });

        btn_masuki.setText("jButton1");

        viewTextArea.setColumns(20);
        viewTextArea.setRows(5);
        jScrollPane1.setViewportView(viewTextArea);

        refreshgmbar.setText("jButton2");

        jMenu4.setText("jMenu4");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 204, 0));

        User.setEditable(false);
        User.setBackground(new java.awt.Color(204, 204, 204));
        User.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        User.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UserMouseClicked(evt);
            }
        });

        label1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label1MouseClicked(evt);
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

        jButton2.setBackground(new java.awt.Color(204, 204, 204));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsz_icons8-synchronize-240.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 204, 204));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-shutdown-50.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btn_pilih.setBackground(new java.awt.Color(102, 255, 51));
        btn_pilih.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsz_feedback-512.png"))); // NOI18N
        btn_pilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pilihActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(User, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_pilih, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(label1, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                    .addComponent(User))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_pilih, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jMenu1.setText("File");

        jMenuItem1.setText("Multi Chat");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help");

        jMenuItem3.setText("About Us");
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
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
            DefaultTableModel dm = new DefaultTableModel();
            dm.addColumn("Clients");
        
            for(int i=0;i<a;i++)
            {
                dm.addRow(new Object[]{(makan.get(i).toString())});
            }
            for(int i=0;i<a;i++)
            {
                System.out.print(makan.get(i).toString());
            }
            clientTable.setModel(dm);
        }
    private void masukButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masukButtonActionPerformed
        // TODO add your handling code here:
        this.server = serverTextField.getText();
        this.port = new Integer(portTextField.getText());
        this.username = usernameTextField.getText();
        setTitle(server + ":" + port + "/" + username);
        start();
    }//GEN-LAST:event_masukButtonActionPerformed

    private void kirimButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kirimButtonActionPerformed
        // TODO add your handling code here:
        try {
            String message = "postText~" + username + "~" + postTextField.getText() + "~all~\n";
            output.writeObject(message);
            postTextField.setText("");
        } catch (IOException ex) {
            Logger.getLogger(ChatClient2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_kirimButtonActionPerformed

    private void postTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postTextFieldActionPerformed
        // TODO add your handling code here:
        kirimButtonActionPerformed(evt);
        
    }//GEN-LAST:event_postTextFieldActionPerformed

    private void clientTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clientTableMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2 && clientTable.getSelectedRow() >= 0) {
            Object data[] = history.getAllData();
            for(int i=0;i<data.length;i++)
            {
                SingleChat data1 = (SingleChat) data[i];
                solo.add(new SingleChat(data1.getId_chat(),data1.getNama_Client1(),data1.getNama_Client2(),data1.getHistory()));
            }
            String kepada = (String) clientTable.getValueAt(clientTable.getSelectedRow(), 0);
            for (PMDialog pMDialog : dialogs) {
                 
                if (pMDialog.getName().equals(kepada) && !kepada.equals(username)) {
                    pMDialog.setTitle(username + "/" + kepada);
                   pMDialog.viewTextArea.setText("");
                    for(int i=0;i<solo.size();i++)
                    {
                        if((solo.get(i).getNama_Client1().equals(username))&&(solo.get(i).getNama_Client2().equals(kepada)))
                        {
                            String pengirim = solo.get(i).getNama_Client1();
                            pMDialog.display(pengirim + ": " + solo.get(i).getHistory());
                        }
                        if((solo.get(i).getNama_Client1().equals(kepada))&&(solo.get(i).getNama_Client2().equals(username)))
                        {
                            String pengirim = solo.get(i).getNama_Client1();
                            pMDialog.display(pengirim + ": " + solo.get(i).getHistory());
                        }
                    }
                    solo.clear();
                    pMDialog.display("Silakan tulis pesan kepada " + kepada);
                    pMDialog.setVisible(true);
                    return;
                }
            }
        }
    }//GEN-LAST:event_clientTableMouseClicked

    private void btn_pilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pilihActionPerformed
        // TODO add your handling code here:
        if (clientTable.getSelectedRow() >= 0) {
            String kepada = (String) clientTable.getValueAt(clientTable.getSelectedRow(), 0);
            Object data[] = history.getAllData();
            for(int i=0;i<data.length;i++)
            {
                SingleChat data1 = (SingleChat) data[i];
                solo.add(new SingleChat(data1.getId_chat(),data1.getNama_Client1(),data1.getNama_Client2(),data1.getHistory()));
            }
            for (PMDialog pMDialog : dialogs) {
                
                if (pMDialog.getName().equals(kepada) && !kepada.equals(username)) {
                    pMDialog.setTitle(username + "/" + kepada);
                    pMDialog.viewTextArea.setText("");
                    for(int i=0;i<solo.size();i++)
                    {
                        if((solo.get(i).getNama_Client1().equals(username))&&(solo.get(i).getNama_Client2().equals(kepada)))
                        {
                            String pengirim = solo.get(i).getNama_Client1();
                            pMDialog.display(pengirim + ": " + solo.get(i).getHistory());
                        }
                        if((solo.get(i).getNama_Client1().equals(kepada))&&(solo.get(i).getNama_Client2().equals(username)))
                        {
                            String pengirim = solo.get(i).getNama_Client1();
                            pMDialog.display(pengirim + ": " + solo.get(i).getHistory());
                        }
                    }
                    solo.clear();
                    pMDialog.display("Silakan tulis pesan kepada " + kepada);
                    pMDialog.setVisible(true);
                    return;
                }
            }
        }
    }//GEN-LAST:event_btn_pilihActionPerformed

    private void UserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UserMouseClicked
        // TODO add your handling code here:
        User.setEnabled(false);
    }//GEN-LAST:event_UserMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        disconnect();
        client.updateStatus(0, usernameTextField.getText());
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void label1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label1MouseClicked
        // TODO add your handling code here:
        GantiGambar n = new GantiGambar();
        n.nama = usernameTextField.getText();
        n.ip = serverTextField.getText();
        n.user = User.getText();
        cek = true;
        n.images = gambar;
        n.room = 2;
        n.setTitle("Ganti Foto Profil");
        n.setVisible(true);
        n.starts();
        disconnect();
        dispose();
    }//GEN-LAST:event_label1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        refreshTabled();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
         ChatClient d = new ChatClient();
        d.setTitle("Multi Chat");
        d.usernameTextField.setText(usernameTextField.getText());
        d.portTextField.setText("2222");
        d.serverTextField.setText(serverTextField.getText());
        d.User.setText(usernameTextField.getText());
        d.btn_masukActionPerformed(evt);
        d.setVisible(true);
        dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        disconnect();
        client.updateStatus(0, usernameTextField.getText());
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    public void btn_masukiActionPerformed(java.awt.event.ActionEvent evt)
    {
        this.server = serverTextField.getText();
        this.port = new Integer(portTextField.getText());
        this.username = usernameTextField.getText();
        setTitle(server + ":" + port + "/" + username);
        start();
    }
    public void refreshgmbarActionPerformed(java.awt.event.ActionEvent evt) {
         JOptionPane.showMessageDialog(null, usernameTextField.getText());
       try {
                    statement = DB_MANAGER.get_connection().createStatement();
                    ResultSet rs = statement.executeQuery("select * from tbl_client where Nama_Client = '"+usernameTextField.getText()+"'");
                    if(rs.next()){
                       
                    byte[] img = rs.getBytes("Foto");
                     
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
            java.util.logging.Logger.getLogger(ChatClient2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatClient2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatClient2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatClient2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //masukButtonActionPerformed(evt);

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatClient2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTextField User;
    private javax.swing.JButton btn_masuki;
    private javax.swing.JButton btn_pilih;
    private javax.swing.JTable clientTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
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
    private javax.swing.JButton refreshgmbar;
    public static javax.swing.JTextField serverTextField;
    public static javax.swing.JTextField usernameTextField;
    private javax.swing.JTextArea viewTextArea;
    // End of variables declaration//GEN-END:variables
    
   
    class ListenFromServer extends Thread {
        @Override
        public void run() {
            while (true) {
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
                            Object data[] = history.getAllData();
                            for(int i=0;i<data.length;i++)
                            {
                                SingleChat data1 = (SingleChat) data[i];
                                solo.add(new SingleChat(data1.getId_chat(),data1.getNama_Client1(),data1.getNama_Client2(),data1.getHistory()));
                            }
                            if (kepada.equals(username)) {
                                 for (PMDialog pMDialog : dialogs) {
                                    if (pMDialog.getName().equals(pengirim)) {
                                        pMDialog.viewTextArea.setText("");
                                        for(int i=0;i<solo.size()-1;i++)
                                        {
                                            if((solo.get(i).getNama_Client1().equals(username))&&(solo.get(i).getNama_Client2().equals(pengirim)))
                                            {
                                                String pengirims = solo.get(i).getNama_Client1();
                                                pMDialog.display(pengirims + ": " + solo.get(i).getHistory());
                                            }
                                            if((solo.get(i).getNama_Client1().equals(pengirim))&&(solo.get(i).getNama_Client2().equals(username)))
                                            {
                                                String pengirims = solo.get(i).getNama_Client1();
                                                pMDialog.display(pengirims + ": " + solo.get(i).getHistory());
                                            }
                                        }
                                        solo.clear();
                                        pMDialog.display(res);
                                        pMDialog.setVisible(true);
                                        break;
                                    }
                                }
                            }
                            break;
                        case "login":
                            viewTextArea.setText(viewTextArea.getText() + pengirim + " sedah login..." + "\n");
                            clients.add(pengirim);
                            break;
                        case "logout":
                            viewTextArea.setText(viewTextArea.getText() + pengirim + " telah logout..." + "\n");
                            JOptionPane.showMessageDialog(null, pengirim+" Sudah Logout!");
                            clients.remove(pengirim);
                             for (PMDialog pMDialog : dialogs) {
                                if (pMDialog.getName().equals(pengirim)) {
                                    dialogs.remove(pMDialog);
                                    break;
                                }
                            }
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
                
                boolean ada = false;
                for (PMDialog pMDialog : dialogs) {
                    if (pMDialog.getName().equals(t)) {
                        ada = true;
                    }
                }
 
                if (!ada) {
                    PMDialog pmd = new PMDialog(ChatClient2.this, socket, input, output, username, t);
                    pmd.setName(t);
                    pmd.setTitle(username + "/" + t);
                    dialogs.add(pmd);
                }
            }
 
            String[] header = {"Clients"};
 
            clientTable.setModel(new DefaultTableModel(data, header)
            {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
        }
         private void refreshTables() {
            int a = clients.size();
            String[] header = {"Clients"};
            Object[][] data = new Object[a][1];
            DefaultTableModel dm = new DefaultTableModel();
           
            for(int i=0;i<a;i++)
            {
                String b = clientTable.getModel().getValueAt(i, 0).toString();
                dm.addColumn("Clients");
                dm.addRow(new Object[]{b});
            }
            clientTable.setModel(dm);
        }
    }
}
