/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ricky K
 */
public class SingleChat {
    private int id_chat;
    private String Nama_Client1;
    private String Nama_Client2;
    private String History;

    public SingleChat(int id_chat, String Nama_Client1, String Nama_Client2, String History) {
        this.id_chat = id_chat;
        this.Nama_Client1 = Nama_Client1;
        this.Nama_Client2 = Nama_Client2;
        this.History = History;
    }

    public SingleChat() {
    }

    
    public int getId_chat() {
        return id_chat;
    }

    public void setId_chat(int id_chat) {
        this.id_chat = id_chat;
    }

    public String getNama_Client1() {
        return Nama_Client1;
    }

    public void setNama_Client1(String Nama_Client1) {
        this.Nama_Client1 = Nama_Client1;
    }

    public String getNama_Client2() {
        return Nama_Client2;
    }

    public void setNama_Client2(String Nama_Client2) {
        this.Nama_Client2 = Nama_Client2;
    }

    public String getHistory() {
        return History;
    }

    public void setHistory(String History) {
        this.History = History;
    }
    
    
}
