package org.notemer.membership;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.sql.*;

public class UtamaController implements Initializable {

    public Label homeUserName;
    public Label ambilId;
    public TableView tabelMember;
    public TableColumn<Member, String> kolomNama;
    public TableColumn<Member, String> kolomJenis;
    public TableColumn<Member, Date> kolomMulai;
    public TableColumn<Member, Date> kolomBerakhir;
    public TableColumn<Member, Integer> kolomHarga;
    public TableColumn<Member, String> kolomStatus;
    private Connection conn;

    private int isiId;

    protected void koneksiDB() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        //SQL Database connection params
        String connectionString = "jdbc:sqlite:membership.sqlite";
        conn = DriverManager.getConnection(connectionString);
    }

    public void initialize(URL url, ResourceBundle resourceBundle){
        homeUserName.setText(LoginController.tampunganUsername);
        tabelMember.setEditable(false);
        kolomNama.setCellValueFactory(new PropertyValueFactory<Member, String>("nama_membership"));
        kolomJenis.setCellValueFactory(new PropertyValueFactory<Member, String>("jenis_keanggotaan"));
        kolomMulai.setCellValueFactory(new PropertyValueFactory<Member, Date>("tanggal_mulai"));
        kolomBerakhir.setCellValueFactory(new PropertyValueFactory<Member, Date>("tanggal_berakhir"));
        kolomHarga.setCellValueFactory(new PropertyValueFactory<Member, Integer>("harga"));
        kolomStatus.setCellValueFactory(new PropertyValueFactory<Member, String>("status"));

        try {
            koneksiDB();
            tabelMember.setItems(getDataFromTable());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Member> getDataFromTable() throws SQLException, ClassNotFoundException {

        conn = DriverManager.getConnection("jdbc:sqlite:membership.sqlite");
        ResultSet rs;
        String select = "SELECT * FROM membership WHERE username = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(select);
        preparedStatement.setString(1, LoginController.tampunganUsername);
        ResultSet rowsAffected = preparedStatement.executeQuery();


        rs = preparedStatement.executeQuery();
        ObservableList<Member> member = FXCollections.observableArrayList(dataBaseArrayList(rs));
        return member;
    }

    private ArrayList dataBaseArrayList(ResultSet rs) throws SQLException {
        ArrayList<Member> data = new ArrayList<>();
        while (rs.next()) {
            Member p = new Member(rs.getInt("id_membership"),rs.getString("nama_membership"), rs.getString("jenis_keanggotaan"), rs.getDate("tanggal_mulai"), rs.getDate("tanggal_selesai"), rs.getString("siklus_pembaruan"), rs.getInt("kontak"), rs.getString("status"), rs.getInt("harga"), rs.getString("manfaat"), rs.getString("deskripsi"));
            data.add(p);
        }
        return data;
    }

//    public void getNamaUser(String text) {
//        homeUserName.setText(text);
//    }

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

    public void onLogOutClick() throws IOException {
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
