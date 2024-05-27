package org.notemer.membership;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProfilController implements Initializable {
    public ImageView profilePicture;
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

    public void initialize(URL url, ResourceBundle resourceBundle) {
        namaUser.setText(LoginController.tampunganUsername);
        try {
            String[] ambil = getDataFromTable();
            namaDepan.setText(ambil[0]);
            namaBelakang.setText(ambil[1]);
            emailUser.setText(ambil[2]);
            namaLengkap.setText(ambil[3]);

            byte[] profileImageData = getProfileImageFromTable();
            if (profileImageData != null) {
                Image image = new Image(new ByteArrayInputStream(profileImageData));
                profilePicture.setImage(image);
            }
            Circle clip = new Circle(83.5, 84, 83.5);
            profilePicture.setClip(clip);
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
    private byte[] getProfileImageFromTable() throws SQLException {
        String select = "SELECT profile_picture FROM user WHERE username = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(select);
        preparedStatement.setString(1, LoginController.tampunganUsername);

        ResultSet rs = preparedStatement.executeQuery();
        return rs.getBytes("profile_picture");
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
    public void onUploadPictureClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                FileInputStream fis = new FileInputStream(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                    bos.write(buf, 0, readNum);
                }
                byte[] bytes = bos.toByteArray();

                String update = "UPDATE user SET profile_picture = ? WHERE username = ?";
                PreparedStatement pstmt = conn.prepareStatement(update);
                pstmt.setBytes(1, bytes);
                pstmt.setString(2, LoginController.tampunganUsername);
                pstmt.executeUpdate();

                profilePicture.setImage(new Image(new ByteArrayInputStream(bytes)));
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
