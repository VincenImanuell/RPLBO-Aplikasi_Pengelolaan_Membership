package org.notemer.membership;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
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
}
