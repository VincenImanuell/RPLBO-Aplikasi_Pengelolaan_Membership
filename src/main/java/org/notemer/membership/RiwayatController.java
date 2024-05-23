package org.notemer.membership;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class RiwayatController implements Initializable {

    @FXML
    private TableView<Riwayat> tabelRiwayat;

//    @FXML
//    private TableColumn<Riwayat, String> usernameColumn;

    @FXML
    private TableColumn<Riwayat, LocalDateTime> waktuColumn;

    @FXML
    private TableColumn<Riwayat, String> keteranganColumn;


    private ObservableList<Riwayat> riwayatList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        waktuColumn.setCellValueFactory(new PropertyValueFactory<>("waktu"));
        keteranganColumn.setCellValueFactory(new PropertyValueFactory<>("keterangan"));

        loadRiwayatAktivitasFromDB(LoginController.tampunganUsername);
        tabelRiwayat.setItems(riwayatList);
    }

    private void loadRiwayatAktivitasFromDB(String username) {
        String query = "SELECT * FROM riwayat WHERE username = ? ORDER BY waktu DESC";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:membership.sqlite");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String user = rs.getString("username");
                LocalDateTime waktu = LocalDateTime.parse(rs.getString("waktu"));
                String keterangan = rs.getString("keterangan");

                Riwayat riwayat = new Riwayat(user, waktu, keterangan);
                riwayatList.add(riwayat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
