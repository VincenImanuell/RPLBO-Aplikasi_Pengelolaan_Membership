package org.notemer.membership;

import java.security.PrivateKey;
import java.util.Date;

public class Member {
        private  int id_membership;
        private String nama_membership;
        private String jenis_keanggotaan;
        private Date tanggal_mulai;
        private Date tanggal_berakhir;
        private String siklus_pembaruan;
        private Integer kontak;
        private String status;
        private int harga;
        private String manfaat;
        private String deskripsi;

    public Member(int id_membership, String nama_membership, String jenis_keanggotaan, Date tanggal_mulai, Date tanggal_berakhir, String siklus_pembaruan, Integer kontak, String status, int harga, String manfaat, String deskripsi) {
        this.id_membership = id_membership;
        this.nama_membership = nama_membership;
        this.jenis_keanggotaan = jenis_keanggotaan;
        this.tanggal_mulai = tanggal_mulai;
        this.tanggal_berakhir = tanggal_berakhir;
        this.siklus_pembaruan = siklus_pembaruan;
        this.kontak = kontak;
        this.status = status;
        this.harga = harga;
        this.manfaat = manfaat;
        this.deskripsi = deskripsi;
    }

    public int getId_membership() {
        return id_membership;
    }

    public void setId_membership(int id_membership) {
        this.id_membership = id_membership;
    }

    public String getNama_membership() {
        return nama_membership;
    }

    public void setNama_membership(String nama_membership) {
        this.nama_membership = nama_membership;
    }

    public String getJenis_keanggotaan() {
        return jenis_keanggotaan;
    }

    public void setJenis_keanggotaan(String jenis_keanggotaan) {
        this.jenis_keanggotaan = jenis_keanggotaan;
    }

    public Date getTanggal_mulai() {
        return tanggal_mulai;
    }

    public void setTanggal_mulai(Date tanggal_mulai) {
        this.tanggal_mulai = tanggal_mulai;
    }

    public Date getTanggal_berakhir() {
        return tanggal_berakhir;
    }

    public void setTanggal_berakhir(Date tanggal_berakhir) {
        this.tanggal_berakhir = tanggal_berakhir;
    }

    public String getSiklus_pembaruan() {
        return siklus_pembaruan;
    }

    public void setSiklus_pembaruan(String siklus_pembaruan) {
        this.siklus_pembaruan = siklus_pembaruan;
    }

    public Integer getKontak() {
        return kontak;
    }

    public void setKontak(Integer kontak) {
        this.kontak = kontak;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getManfaat() {
        return manfaat;
    }

    public void setManfaat(String manfaat) {
        this.manfaat = manfaat;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}

