package org.notemer.membership;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.lang.Integer.parseInt;

public class InputController {

    @FXML
    private TextArea deskripsi;

    @FXML
    private TextField harga;

    @FXML
    private TextField jenisMembership;

    @FXML
    private TextField kontak;

    @FXML
    private TextArea manfaat;

    @FXML
    private TextField namaMembership;

    @FXML
    private TextField siklus;

    @FXML
    private TextField status;

    @FXML
    private DatePicker tglMulai;

    @FXML
    private DatePicker tglSelesai;

    public void onBatalClick() {
        try {
            GuiApp.setRoot("utama", "HomePage-NoteMer", false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onTambahClick(ActionEvent actionEvent) throws ClassNotFoundException, SQLException, IOException {
        if (deskripsi.getText() == "" || harga.getText() == "" || jenisMembership.getText() == "" || kontak.getText() == "" || manfaat.getText() == "" || namaMembership.getText() == "" || siklus.getText() == "" || status.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Informasi");
            alert.setContentText("Pastikan semua data terisi!");
            alert.showAndWait();
        } else {
                String desc, type, benefit, name, cycle, statuss;
                desc = deskripsi.getText();
                String price = harga.getText();
                type = jenisMembership.getText();
                String contact = kontak.getText();
                benefit = manfaat.getText();
                name = namaMembership.getText();
                cycle = siklus.getText();
                statuss = status.getText();
                LocalDate starts = tglMulai.getValue();
                LocalDate ends = tglSelesai.getValue();

                Member member = new Member(name,type,starts,ends,cycle, contact, statuss, price, benefit, desc);
//                table.getItems().add(member);

                Connection conn;
                ResultSet rs;
                Class.forName("org.sqlite.JDBC");
                //SQL Database connection params
                String connectionString = "jdbc:sqlite:membership.sqlite";
                conn = DriverManager.getConnection(connectionString);

                String sql = "INSERT INTO membership (username, nama_membership, jenis_keanggotaan, tanggal_mulai, tanggal_selesai, siklus_pembaruan, kontak, status, harga, manfaat, deskripsi) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, LoginController.tampunganUsername);
                stmt.setString(2, name);
                stmt.setString(3, type);
                stmt.setDate(4, Date.valueOf(starts));
                stmt.setDate(5, Date.valueOf(ends));
                stmt.setString(6, cycle);
                stmt.setString(7, contact);
                stmt.setString(8, statuss);
                stmt.setString(9, price);
                stmt.setString(10, benefit);
                stmt.setString(11, desc);
                int affectedRows = stmt.executeUpdate();

                // Check if data insertion was successful
                if (affectedRows > 0) {
                    Alert alert=new Alert(Alert.AlertType.INFORMATION, "Data berhasil disimpan");
                    alert.showAndWait();
                    GuiApp.setRoot("utama", "HomePage-NoteMer", false);
                }

                Riwayat riwayat = new Riwayat(LoginController.tampunganUsername, LocalDateTime.now(), "Menambahkan Membership: " + member.getNama_membership());
                simpanRiwayatAktivitas(riwayat);

                // Close the database connection
                conn.close();
        }
    }

    public void simpanRiwayatAktivitas(Riwayat riwayat) {
        String insertSQL = "INSERT INTO riwayat (waktu, keterangan, username) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:membership.sqlite");
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, riwayat.getWaktu().toString());
            pstmt.setString(2, riwayat.getKeterangan());
            pstmt.setString(3, riwayat.getUsername());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
