package org.notemer.membership;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminEditControllerUser implements Initializable {

    private Connection conn;
    @FXML
    public TextField email;

    @FXML
    public TextField namabelakang;

    @FXML
    public TextField namadepan;

    @FXML
    public TextField password;

    @FXML
    public TextField username;

    public int idUser = AdminControllerUser.IdUser;

    @FXML
    public void back(MouseEvent event) throws IOException {
        GuiApp.setRoot("userAdmin","Halaman Admin User",false);

    }

    @FXML
    public void edit(MouseEvent event) throws SQLException, IOException {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Apakah Anda Yakin Ingin Mengupdate Data ?");
        a.setTitle("Konfirmasi Edit Data");
        a.setContentText("Anda Nanti Dapat Mengeditnya Kembali");
        Optional<ButtonType> jwb = a.showAndWait();
        if(jwb.get() == ButtonType.OK){
            String connectionString = "jdbc:sqlite:membership.sqlite";
            conn = DriverManager.getConnection(connectionString);
            PreparedStatement preparedStatement =
                    conn.prepareStatement("update user set username=?, password=?, nama_depan=?, nama_belakang=?, email=?  where id_user=?");
            preparedStatement.setString(1, username.getText());
            preparedStatement.setString(2, password.getText());
            preparedStatement.setString(3, namadepan.getText());
            preparedStatement.setString(4, namabelakang.getText());
            preparedStatement.setString(5, email.getText());
            preparedStatement.setInt(6, idUser);
            int hasil = preparedStatement.executeUpdate();

            GuiApp.setRoot("userAdmin","Halaman Admin User",false);
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(AdminControllerUser.userna);
        password.setText(AdminControllerUser.pass);
        namabelakang.setText(AdminControllerUser.belakang);
        namadepan.setText(AdminControllerUser.depan);
        email.setText(AdminControllerUser.emailuser);
    }
}
