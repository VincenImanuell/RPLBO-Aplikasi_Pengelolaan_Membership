package org.notemer.membership;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProfilController implements Initializable {
    @FXML
    private Text namaLengkap;

    @FXML
    private Label namaUser;

    @FXML
    private Text emailUser;

    @FXML
    private Text namaDepan;

    @FXML
    private Text namaBelakang;

    Connection conn;

    protected void koneksiDB() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        //SQL Database connection params
        String connectionString = "jdbc:sqlite:membership.sqlite";
        conn = DriverManager.getConnection(connectionString);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        namaUser.setText(LoginController.tampunganUsername);
        try {
            String[] ambil = getDataFromTable();
            namaDepan.setText(ambil[0]);
            namaBelakang.setText(ambil[1]);
            emailUser.setText(ambil[2]);
            namaLengkap.setText(ambil[3]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] getDataFromTable() throws SQLException, ClassNotFoundException {

        conn = DriverManager.getConnection("jdbc:sqlite:membership.sqlite");
        ResultSet rs;
        String select = "SELECT * FROM user WHERE username = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(select);
        preparedStatement.setString(1, LoginController.tampunganUsername);

        rs = preparedStatement.executeQuery();

        String namadepan = rs.getString(4);
        String namabelakang = rs.getString(5);
        String emailx = rs.getString(6);
        String namaLengkap = namadepan + " " + namabelakang;

        String[] hasil = {namadepan, namabelakang, emailx, namaLengkap};
        return hasil;
    }

    public void onBackClick() {
        try {
            GuiApp.setRoot("utama", "HomePage-NoteMer", false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onHapusAkunclick() {
        Alert konfirmasiAlert = new Alert(Alert.AlertType.CONFIRMATION);
        konfirmasiAlert.setTitle("Konfirmasi Hapus Akun");
        konfirmasiAlert.setHeaderText(null);
        konfirmasiAlert.setContentText("Apakah Anda yakin ingin menghapus akun Anda?");

        ButtonType Ya = new ButtonType("Ya", ButtonBar.ButtonData.OK_DONE);
        ButtonType Batal = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
        konfirmasiAlert.getButtonTypes().setAll(Ya, Batal);

        konfirmasiAlert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == Ya) {
                try {
                    hapusAkun(LoginController.tampunganUsername);
                    Alert berhasilAlert = new Alert(Alert.AlertType.INFORMATION);
                    berhasilAlert.setTitle("Sukses");
                    berhasilAlert.setHeaderText(null);
                    berhasilAlert.setContentText("Akun berhasil dihapus.");
                    berhasilAlert.showAndWait();
                    GuiApp.setRoot("login", "Login NoteMer: Note Member", false);
                } catch (SQLException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Gagal menghapus akun. Silakan coba lagi.");
                    errorAlert.showAndWait();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
            }
        });
    }

    private void hapusAkun(String username) throws SQLException {
        String deleteSQL = "DELETE FROM user WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(deleteSQL);
        pstmt.setString(1, username);
        pstmt.executeUpdate();
    }
}
