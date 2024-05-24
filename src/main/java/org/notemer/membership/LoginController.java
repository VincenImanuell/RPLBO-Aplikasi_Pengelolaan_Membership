package org.notemer.membership;

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
import java.util.ResourceBundle;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    private Connection conn;
    private final String DB_URL = "jdbc:sqlite:membership.sqlite";


    public static String tampunganUsername;

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
        getConnection();

        Alert alert;
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        tampunganUsername = username;


        String query = "SELECT * FROM user WHERE username = ? AND password = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rowsAffected = preparedStatement.executeQuery();

            if (!rowsAffected.isBeforeFirst() ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Gagal");
                alert.setContentText("Login Gagal!! Password atau Username Salah.");
                alert.showAndWait();
                txtUsername.requestFocus();
            } else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Informasi");
                alert.setContentText("Login Berhasil!!");
                alert.showAndWait();

                GuiApp.setRoot("utama", "HomePage-NoteMer", false);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(DB_URL);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle database connection error
            }
        }
        return conn;
    }

}



