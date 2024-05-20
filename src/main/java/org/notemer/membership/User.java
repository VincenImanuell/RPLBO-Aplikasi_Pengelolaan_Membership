package org.notemer.membership;

public class User {
    private int id;
    private String username;
    private String password;
    private String namaDepan;
    private String namaBelakang;
    private String email;

    public User(int id, String username, String password, String namaDepan, String namaBelakang, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.namaDepan = namaDepan;
        this.namaBelakang = namaBelakang;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNamaDepan() {
        return namaDepan;
    }

    public void setNamaDepan(String namaDepan) {
        this.namaDepan = namaDepan;
    }

    public String getNamaBelakang() {
        return namaBelakang;
    }

    public void setNamaBelakang(String namaBelakang) {
        this.namaBelakang = namaBelakang;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
