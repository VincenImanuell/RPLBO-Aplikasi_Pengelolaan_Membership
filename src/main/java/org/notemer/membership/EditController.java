package org.notemer.membership;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.spi.DateFormatProvider;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditController implements Initializable {

    public int id = UtamaController.id_membership;
    private Connection conn;
    public TextField status;
    public TextField nama;
    public  TextField harga;
    public TextField kontak;
    public TextField jenis;
    public TextField siklus;
    public DatePicker mulai;
    public DatePicker berakhir;
    public TextArea manfaat;
    public TextArea deskripsi;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nama.setText(UtamaController.nama);
        jenis.setText(UtamaController.jenis);
        harga.setText(String.valueOf(UtamaController.harga));
        kontak.setText(UtamaController.kontak );
        siklus.setText(UtamaController.pembaharuan);
        deskripsi.setText(UtamaController.deskripsi);
        status.setText(UtamaController.status);
        manfaat.setText(UtamaController.manfaat);


//        DateTimeFormatter outputformatmulai = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        String output = date.format(outputformat);

        String tglmulai = String.valueOf(UtamaController.tanggal_mulai);
        DateTimeFormatter inputformatmulai = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate datemulai = LocalDate.parse(tglmulai,inputformatmulai);
        mulai.setValue(datemulai);

        String tglberakhir = String.valueOf(UtamaController.tanggal_berakhir);
        DateTimeFormatter inputformatberakhir = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateberakhir = LocalDate.parse(tglberakhir,inputformatberakhir);
        berakhir.setValue(dateberakhir);

    }

    public void back(MouseEvent mouseEvent) throws IOException {
        GuiApp.setRoot("utama","HomePage-NoteMer",false);

    }

    public void edit(MouseEvent mouseEvent) throws SQLException, IOException, ParseException {

        String datestart = String.valueOf(mulai.getValue());
        SimpleDateFormat formattermulai = new SimpleDateFormat("yyyy-MM-dd");
        Date datemulai = (Date)formattermulai.parse(datestart);
        long millsmulai = datemulai.getTime();

        String dateend = String.valueOf(berakhir.getValue());
        SimpleDateFormat formattrberakhir = new SimpleDateFormat("yyyy-MM-dd");
        Date dateberakhir = (Date)formattrberakhir.parse(dateend);
        long millsberakhir = dateberakhir.getTime();


        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Apakah Anda Yakin Ingin Mengupdate Data ?");
        a.setTitle("Konfirmasi Edit Data");
        a.setContentText("Anda Nanti Dapat Mengeditnya Kembali");
        Optional<ButtonType> jwb = a.showAndWait();
        if(jwb.get() == ButtonType.OK){
            String connectionString = "jdbc:sqlite:membership.sqlite";
            conn = DriverManager.getConnection(connectionString);
            PreparedStatement preparedStatement =
                    conn.prepareStatement("update membership set nama_membership=?, jenis_keanggotaan=?, tanggal_mulai=?, tanggal_selesai=?, siklus_pembaruan=?, kontak=?, status=?, harga=?, manfaat=?, deskripsi=?  where id_membership=?");
            preparedStatement.setString(1, nama.getText());
            preparedStatement.setString(2, jenis.getText());
            preparedStatement.setString(3, String.valueOf(millsmulai));
            preparedStatement.setString(4, String.valueOf(millsberakhir));
            preparedStatement.setString(5, siklus.getText());
            preparedStatement.setString(6, kontak.getText());
            preparedStatement.setString(7, status.getText());
            preparedStatement.setString(8, harga.getText());
            preparedStatement.setString(9, manfaat.getText());
            preparedStatement.setString(10, deskripsi.getText());
            preparedStatement.setString(11, String.valueOf(id));
            int hasil = preparedStatement.executeUpdate();

            GuiApp.setRoot("utama","HomePage-NoteMer",false);
        }

    }

}
