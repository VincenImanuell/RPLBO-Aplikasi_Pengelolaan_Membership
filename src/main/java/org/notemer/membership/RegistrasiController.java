package org.notemer.membership;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RegistrasiController {

    @FXML
    private TextField email;

    @FXML
    private PasswordField konfirmasi;

    @FXML
    private TextField namaBelakang;

    @FXML
    private TextField namaDepan;

    @FXML
    private PasswordField password;

    @FXML
    private TextField userName;

    @FXML
    void onBatal(ActionEvent event) throws IOException {
        GuiApp.setRoot("login", "Login NoteMer: Note Member", false);
    }

    @FXML
    void onSimpan(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        if (userName.getText().trim().isEmpty()||namaDepan.getText().trim().isEmpty()||namaBelakang.getText().trim().isEmpty()||email.getText().trim().isEmpty()||password.getText().trim().isEmpty()||konfirmasi.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Informasi");
            alert.setContentText("Pastikan semua data terisi!");
            alert.showAndWait();
        } else if (!password.getText().equals(konfirmasi.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Informasi");
            alert.setContentText("Password dan konfirmasi password anda tidak sama!");
            alert.showAndWait();
        } else {


            String name, first, last, mail, pass;
            name = userName.getText();
            first = namaDepan.getText();
            last = namaBelakang.getText();
            mail = email.getText();
            pass = password.getText();

            User user = new User(name, pass, first, last, mail);

            Connection conn;

            Class.forName("org.sqlite.JDBC");

            String connectionString = "jdbc:sqlite:membership.sqlite";
            conn = DriverManager.getConnection(connectionString);

            String query = "SELECT * FROM user WHERE username = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, name);
            ResultSet rowsAffected = stm.executeQuery();

            Alert alert;
            if (!rowsAffected.isBeforeFirst()) {
                String sql = "INSERT INTO user (username, password, nama_depan, nama_belakang, email) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, pass);
                stmt.setString(3, first);
                stmt.setString(4, last);
                stmt.setString(5, mail);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    alert = new Alert(Alert.AlertType.INFORMATION, "User berhasil didaftarkan.");
                    alert.showAndWait();
                    GuiApp.setRoot("login", "Login-NoteMer", false);
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Username sudah ada");
                alert.setContentText("Maaf, silahkan gunakan username yang belum ada!");
                alert.showAndWait();
            }

            conn.close();
        }
    }

}
