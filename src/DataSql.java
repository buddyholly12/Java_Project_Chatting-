
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ricky K
 */
public class DataSql {
    private Statement statement;
    Connection con = null;
    
    public Object[] getAllData(){
        Vector<DataClient> data = new Vector<DataClient>();
        try {
            statement = DB_MANAGER.get_connection().createStatement();
            int i = 0;
            ResultSet rs = statement.executeQuery("select * from tbl_client");
           try {
                while(rs.next()){
                    DataClient br = new DataClient();
                    br.setId(rs.getInt("id_client"));
                    br.setNama_barang(rs.getString("Nama_Client"));
                    br.setPassword(rs.getInt("Password_Client"));
                    br.setKelamin(rs.getString("Jenis_Kelamin"));
                    br.setStatus(rs.getInt("Status"));
                    data.add(br);
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataSql.class.getName()).log(Level.SEVERE, null, ex);
            }
            rs.close();
            statement.close();
            DB_MANAGER.close_connection();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
//            Logger.getLogger(BarangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data.toArray();
    }
    
    public void addBarang(String nama, int password, String kelamin){
        try{
            statement = DB_MANAGER.get_connection().createStatement();
            statement.executeUpdate("Insert into tbl_client (Nama_Client, Password_Client, Jenis_Kelamin) values('"+
                    nama+"',"+password+", '"+kelamin+"')");
            statement.close();
            DB_MANAGER.close_connection();       
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
    public static String getIP() {
        String ip = null;
        String op = null;
        try{

        InetAddress ia=InetAddress.getLocalHost();

        ip = "RickyK";
        InetAddress io = InetAddress.getByName(ip);
        op = io.getHostAddress();
       
        }catch(Exception a){}
        //System.out.print(IP);
        return op;
    }
    public void addFoto(String path, String nama){
        String ip = getIP();
        try {
            con = DriverManager.getConnection("jdbc:mysql://"+ip+":3306/chating","root","");
            statement = con.createStatement();
            File imgfile = new File(path);
            
            FileInputStream is = new FileInputStream(imgfile);
            
            PreparedStatement pre = con.prepareStatement("update tbl_client SET Foto = ? WHERE Nama_Client = ?");
            pre.setBinaryStream(1,(InputStream)is,(int)imgfile.length());
            pre.setString(2,nama);
            pre.executeUpdate();
            pre.close();
            con.close(); 
        } catch (SQLException ex) {
            Logger.getLogger(DataSql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataSql.class.getName()).log(Level.SEVERE, null, ex);
        }
//        try {
//            File imgfile = new File(path);
//            FileInputStream is = new FileInputStream(imgfile);
//            statement = DB_MANAGER.get_connection().createStatement();
//            statement.executeUpdate("update tbl_client SET Foto = '"+is+"' WHERE Nama_Client = '"+nama+"'");
//            statement.close();
//            DB_MANAGER.close_connection();       
//        } catch (SQLException ex) {
//            Logger.getLogger(DataSql.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(DataSql.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
    }
    
    
     public void deleteBarang(int row){
         int nihil = 0;
        try{
            statement = DB_MANAGER.get_connection().createStatement();
            statement.executeUpdate("update tbl_barang SET status = '"+nihil+"' WHERE id = '"+row+"'");
            statement.close();
           DB_MANAGER.close_connection();       
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
     public void updateStatus(int isi, String nama){
         //int nihil = 0;
        try{
            statement = DB_MANAGER.get_connection().createStatement();
            //statement.executeUpdate("update tbl_barang SET kode_barang = '"+kode+"','"+"'nama_barang = '"+nama+"','"
                    //+"'harga_barang = '"+harga+"' WHERE id = '"+row+"'");
            statement.executeUpdate("update tbl_client SET Status = "+isi+" WHERE Nama_Client = '"+nama+"'");
            statement.close();
            DB_MANAGER.close_connection();       
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
    public Object[] getAllStatus(){
        Vector<DataOnline> data = new Vector<DataOnline>();
        try {
            statement = DB_MANAGER.get_connection().createStatement();
            int i = 0;
            ResultSet rs = statement.executeQuery("select Nama_Client from tbl_client where Status = 1 ");
           try {
                while(rs.next()){
                    DataOnline br = new DataOnline();
                    //br.setId(rs.getInt("id_users"));
                    br.setNama(rs.getString("Nama_Client"));
                    data.add(br);
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataSql.class.getName()).log(Level.SEVERE, null, ex);
            }
            rs.close();
            statement.close();
            DB_MANAGER.close_connection();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
//            Logger.getLogger(BarangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data.toArray();
    }
    public void updatePassword(int pass, String nama){
         //int nihil = 0;
        try{
            statement = DB_MANAGER.get_connection().createStatement();
            statement.executeUpdate("update tbl_client SET Password_Client = "+pass+" WHERE Nama_Client = '"+nama+"'");
            JOptionPane.showMessageDialog(null, "Password Berhasil Diganti!!!");
            statement.close();
            DB_MANAGER.close_connection();       
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}
