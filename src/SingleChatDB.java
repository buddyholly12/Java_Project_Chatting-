
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
public class SingleChatDB {
     private Statement statement;
     
     public Object[] getAllData(){
        Vector<SingleChat> data = new Vector<SingleChat>();
        try {
            statement = DB_MANAGER.get_connection().createStatement();
            int i = 0;
            ResultSet rs = statement.executeQuery("select * from single_chat");
           try {
                while(rs.next()){
                    SingleChat br = new SingleChat();
                    br.setId_chat(rs.getInt("id_chat"));
                    br.setNama_Client1(rs.getString("Nama_Client1"));
                    br.setNama_Client2(rs.getString("Nama_Client2"));
                    br.setHistory(rs.getString("history"));
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
     public void addHistory(String nama1,String nama2,String history){
        try{
            statement = DB_MANAGER.get_connection().createStatement();
            statement.executeUpdate("Insert into single_chat (Nama_Client1,Nama_Client2, history) values('"+nama1+"','"+nama2+"','"+history+"')");
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
}
