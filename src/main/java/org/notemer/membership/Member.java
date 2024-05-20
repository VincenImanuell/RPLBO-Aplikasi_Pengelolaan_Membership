package org.notemer.membership;

import java.time.LocalDate;

public class Member {
        private  int id_membership;
        private String nama_membership;
        private String jenis_keanggotaan;
        private LocalDate tanggal_mulai;
        private LocalDate tanggal_berakhir;
        private String siklus_pembaruan;
        private String kontak;
        private String status;
        private String harga;
        private String manfaat;
        private String deskripsi;

    public Member(int id_membership, String nama_membership, String jenis_keanggotaan, LocalDate tanggal_mulai, LocalDate tanggal_berakhir, String siklus_pembaruan, String kontak, String status, String harga, String manfaat, String deskripsi) {
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

    public Member(String nama_membership, String jenis_keanggotaan, LocalDate tanggal_mulai, LocalDate tanggal_berakhir, String siklus_pembaruan, String kontak, String status, String harga, String manfaat, String deskripsi) {
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

    public LocalDate getTanggal_mulai() {
        return tanggal_mulai;
    }

    public void setTanggal_mulai(LocalDate tanggal_mulai) {
        this.tanggal_mulai = tanggal_mulai;
    }

    public LocalDate getTanggal_berakhir() {
        return tanggal_berakhir;
    }

    public void setTanggal_berakhir(LocalDate tanggal_berakhir) {
        this.tanggal_berakhir = tanggal_berakhir;
    }

    public String getSiklus_pembaruan() {
        return siklus_pembaruan;
    }

    public void setSiklus_pembaruan(String siklus_pembaruan) {
        this.siklus_pembaruan = siklus_pembaruan;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
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

