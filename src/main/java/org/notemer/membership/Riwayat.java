package org.notemer.membership;

import java.time.LocalDateTime;

public class Riwayat {
    private String username;
    private LocalDateTime waktu;
    private String keterangan;

    public Riwayat(String username, LocalDateTime waktu, String keterangan) {
        this.username = username;
        this.waktu = waktu;
        this.keterangan = keterangan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getWaktu() {
        return waktu;
    }

    public void setWaktu(LocalDateTime waktu) {
        this.waktu = waktu;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
