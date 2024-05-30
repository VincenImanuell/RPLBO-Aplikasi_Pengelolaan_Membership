package org.notemer.membership;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    private Connection conn;
    private final String DB_URL = "jdbc:sqlite:membership.sqlite";


    public static String tampunganUsername = "";

    public ImageView lblOpenEye;
    public ImageView lblCloseEye;
    String password;

    public TextField txtPassword2;


    public void initialize(){
        txtPassword2.setVisible(false);
        lblOpenEye.setVisible(false);
    }

    public void hidePassword(KeyEvent event) {
        password=txtPassword.getText();
        txtPassword2.setText(password);
    }

    public void showPassword(KeyEvent event) {
        password=txtPassword2.getText();
        txtPassword.setText(password);
    }

    @FXML
    public void Close_Eye_ClickOnAct(MouseEvent mouseEvent) {
        txtPassword2.setVisible(true);
        lblOpenEye.setVisible(true);
        lblCloseEye.setVisible(false);
        txtPassword.setVisible(false);
    }
    public void Open_Eye_ClickOnAction(MouseEvent mouseEvent) {
        txtPassword2.setVisible(false);
        lblOpenEye.setVisible(false);
        lblCloseEye.setVisible(true);
        txtPassword.setVisible(true);
    }

    @FXML
    protected void onKeyPressEvent(KeyEvent event) throws IOException, SQLException {
        if( event.getCode() == KeyCode.ENTER ) {
            btnLoginClick();
        }
    }

    @FXML
    protected void btnLoginClick() throws IOException, SQLException {
        Alert alert;
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        tampunganUsername = username;

        if (Objects.equals(username, "admin") && Objects.equals(password, "admin")){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Informasi");
            alert.setContentText("Login Berhasil!!");
            alert.showAndWait();
            GuiApp.setRoot("Admin", "Halaman Utama Admin", false);
        }else {
            getConnection();
            String query = "SELECT * FROM user WHERE username = ? AND password = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet rowsAffected = preparedStatement.executeQuery();

                if (!rowsAffected.isBeforeFirst()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Gagal");
                    alert.setContentText("Login Gagal!! Password atau Username Salah.");
                    alert.showAndWait();
                    txtUsername.requestFocus();
                } else {
                    SessionManager.getInstance().login();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Informasi");
                    alert.setContentText("Login Berhasil!!");
                    alert.showAndWait();
                    GuiApp.setRoot("utama", "HomePage-NoteMer", false);
                    checkMembershipExpiration();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }




    }

    public Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(DB_URL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public void onLupa() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Lupa Username/Password?");
        alert.setContentText("Silahkan hubungi admin dengan nomor berikut ini!!\n081241211600");
        alert.showAndWait();
    }

    public void onRegistrasi() throws IOException {
        GuiApp.setRoot("registrasi", "Registrasi-NoteMer", false);
    }

    public void checkMembershipExpiration() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:membership.sqlite");
            String query = "SELECT * FROM membership WHERE username = ?";


            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, LoginController.tampunganUsername);
            ResultSet rs = stmt.executeQuery();

            LocalDate today = LocalDate.now();

            while (rs.next()) {
                int id = rs.getInt("id_membership");
                String nama = rs.getString("nama_membership");

                java.sql.Date sqlDate = rs.getDate("tanggal_selesai");

                if (sqlDate == null) {
                    System.out.println("Skipping row with invalid tanggal_selesai: " + id);
                    continue;
                }

                // Convert the java.sql.Date to LocalDate
                LocalDate expirationDate = sqlDate.toLocalDate();

                // Calculate the remaining days until expiration
                long daysLeft = ChronoUnit.DAYS.between(today, expirationDate);

                // Check if the membership is about to expire
                if (daysLeft <= 3 && daysLeft >= 0) {
                    // Show the alert in JavaFX Application Thread
                    String message = "Membership " + nama + " akan segera berakhir!\n" +
                            "Membership akan berakhir " + daysLeft + " hari lagi pada tanggal: " + expirationDate;
                    Platform.runLater(() -> showAlert(Alert.AlertType.WARNING, "Peringatan!!", message));
                }
            }

            // Close the ResultSet, PreparedStatement, and Connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}



