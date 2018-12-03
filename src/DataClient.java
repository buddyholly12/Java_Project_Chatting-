/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ricky K
 */
public class DataClient {
    private int id, password;
    private String nama;
    private String kelamin;
    private int status;

    public DataClient(int id, int password, String nama_barang, String kelamin,int status) {
        this.id = id;
        this.password = password;
        this.nama = nama_barang;
        this.kelamin = kelamin;
        this.status = status;
    }

    DataClient() {
    
    }

    @Override
    public String toString() {
        return "DataClient{" + "id=" + id + ", password=" + password + ", nama=" + nama + ", kelamin=" + kelamin + ", status=" + status + '}';
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getNama_barang() {
        return nama;
    }

    public void setNama_barang(String nama_barang) {
        this.nama = nama_barang;
    }

    public String getKelamin() {
        return kelamin;
    }

    public void setKelamin(String kelamin) {
        this.kelamin = kelamin;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

   


}

