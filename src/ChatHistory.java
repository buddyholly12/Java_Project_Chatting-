/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ricky K
 */
public class ChatHistory {
    private int id_chat;
    private String nama_users;
    private String History;

    public ChatHistory(int id_chat, String nama_users, String History) {
        this.id_chat = id_chat;
        this.nama_users = nama_users;
        this.History = History;
    }

    public ChatHistory() {
    }

    public int getId_chat() {
        return id_chat;
    }

    public void setId_chat(int id_chat) {
        this.id_chat = id_chat;
    }

    public String getNama_users() {
        return nama_users;
    }

    public void setNama_users(String nama_users) {
        this.nama_users = nama_users;
    }

    public String getHistory() {
        return History;
    }

    public void setHistory(String History) {
        this.History = History;
    }
    
}
