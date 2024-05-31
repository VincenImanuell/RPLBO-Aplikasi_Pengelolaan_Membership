package org.notemer.membership;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public Label homeUserName;

    public void menuriwayat(MouseEvent mouseEvent) throws IOException {
        GuiApp.setRoot("riwayatAdmin","Halaman Admin Riwayat",false);
    }

    public void menumembership(MouseEvent mouseEvent) throws IOException {
        GuiApp.setRoot("membershipAdmin","Halaman Admin Riwayat",false);
    }

    public void menuuser(MouseEvent mouseEvent) throws IOException {
        GuiApp.setRoot("userAdmin","Halaman Admin Riwayat",false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeUserName.setText(LoginController.tampunganUsername);
    }

    public void onDetailClick() {
        Alert alert;
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tentang Kami");
        alert.setHeaderText("NoteMer");
        alert.setContentText("NoteMer adalah sebuah aplikasi manajemen akun membership yang dirancang untuk memudahkan pengguna dalam mengelola akun membership mereka dengan cara yang mudah dan efisien.\n\n" +
                "Pengembang:\n" +
                "1. 71220854 / David Arya Seta\n" +
                "2. 71220856 / Vincen Imanuel\n" +
                "3. 71220928 / Natanael\n" +
                "4. 71220956 / Vicky Yohanes Putra Setiawan");
        alert.showAndWait();
    }

    @FXML
    void onLogOutClick(ActionEvent event) throws IOException {
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi LogOut");
        alert.setHeaderText("Apakah anda yakin ingin melakukan LogOut?");
        alert.setContentText("Anda akan diminta untuk login kembali jika anda melakukan LogOut.");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
            for ( ButtonType bt : alert.getDialogPane().getButtonTypes() )
            {
                if ( bt.getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE )
                {
                    Button cancelButton = ( Button ) alert.getDialogPane().lookupButton( bt );
                    cancelButton.fire();
                    break;
                }
            }
        } else if (option.get() == ButtonType.OK) {
            GuiApp.setRoot("login", "Login NoteMer: Note Member", false);
        } else if (option.get() == ButtonType.CANCEL) {
            for (ButtonType bt : alert.getDialogPane().getButtonTypes()) {
                if (bt.getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
                    Button cancelButton = (Button) alert.getDialogPane().lookupButton(bt);
                    cancelButton.fire();
                    break;
                }
            }
        }
    }
}
