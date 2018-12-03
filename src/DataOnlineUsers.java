
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ricky K
 */
public class DataOnlineUsers {
    private Statement statement;
    
    public Object[] getAllDatas(){
        Vector<DataOnline> data = new Vector<DataOnline>();
        try {
            statement = DB_MANAGER.get_connection().createStatement();
            int i = 0;
            ResultSet rs = statement.executeQuery("select * from data_online");
           try {
                while(rs.next()){
                    DataOnline br = new DataOnline();
                    //br.setId(rs.getInt("id_users"));
                    br.setNama(rs.getString("Nama_Users"));
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
    public void addUserOnline(String nama){
        try{
            statement = DB_MANAGER.get_connection().createStatement();
            statement.executeUpdate("Insert into data_online (Nama_Users) values('"+nama+"')");
            statement.close();
            DB_MANAGER.close_connection();       
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
    public void deleteUserOnline(String nama){
        try{
            statement = DB_MANAGER.get_connection().createStatement();
            statement.executeUpdate("DELETE FROM data_online WHERE Nama_Users = '"+nama+"'");
            //statement.executeUpdate("Insert into data_online (Nama_Users) values('"+nama+"')");
            statement.close();
            DB_MANAGER.close_connection();       
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
    
    
    
    
    
}
