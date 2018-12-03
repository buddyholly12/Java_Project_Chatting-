
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ricky K
 */
public class DB_MANAGER {
    //jdbc:mysql://ip server db:port mysql/tabel mysql
    
    private static String url = "jdbc:mysql://"+getIP()+":3306/chating";
    //private static String url = "jdbc:mysql://localhost:3306/chating";
    private static String username = "root";
    private static String password = "";
    private static Connection conn;
    //private static String IP;

    
    
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
    
    public static Connection get_connection(){
       // getIP();
        //String url = "jdbc:mysql://192.168.0.10:3306/chating";
        //System.out.print(url);
        String ip = getIP();
        System.out.println(ip);
        try {
            conn = DriverManager.getConnection(url, username,password);
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return conn;
    }
    public static void close_connection() throws SQLException{
        conn.close();
   }
}
