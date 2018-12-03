
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
public class ChatHistoryDB {
    private Statement statement;
    
    public Object[] getAllData(){
        Vector<ChatHistory> data = new Vector<ChatHistory>();
        try {
            statement = DB_MANAGER.get_connection().createStatement();
            int i = 0;
            ResultSet rs = statement.executeQuery("select * from chat");
           try {
                while(rs.next()){
                    ChatHistory br = new ChatHistory();
                    br.setId_chat(rs.getInt("id_chat"));
                    br.setNama_users(rs.getString("Nama_Client"));
                    br.setHistory(rs.getString("History"));
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
    public void addHistory(String nama,String history){
        try{
            statement = DB_MANAGER.get_connection().createStatement();
            statement.executeUpdate("Insert into chat (Nama_Client, History) values('"+nama+"','"+history+"')");
            statement.close();
            DB_MANAGER.close_connection();       
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new IllegalStateException("Cannot connect the database!", e);
            }
//        ArrayList<ChatHistory> datas = new ArrayList<ChatHistory>();
//        
//        Object data[] = getAllData();
//        for(int i=0;i<data.length;i++)
//        {
//            ChatHistory data1 = (ChatHistory) data[i];
//            
//        }
        
    }
    public void deleteHistory(int id){
        try{
            statement = DB_MANAGER.get_connection().createStatement();
            statement.executeUpdate("DELETE FROM chat WHERE id_chat = "+id+"");
            //statement.executeUpdate("Insert into data_online (Nama_Users) values('"+nama+"')");
            statement.close();
            DB_MANAGER.close_connection();       
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}
