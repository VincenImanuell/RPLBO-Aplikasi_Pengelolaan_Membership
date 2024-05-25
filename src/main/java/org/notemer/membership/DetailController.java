package org.notemer.membership;

import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class DetailController implements Initializable {
    public Text kontak;
    public Text nama_organisasi;
    public Text status;
    public Text deskripsi;
    public Text jenis;
    public Text manfaat;
    public Text pembaruan;
    public Text harga;
    public Text mulai;
    public Text berakhir;
    public Rectangle warna;
    public ImageView image;
    public ProgressBar progres;
    public Text hari;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kontak.setText(UtamaController.kontak);
        if (Objects.equals(UtamaController.status, "Tidak Aktif")){
            progres.setProgress(1);
            progres.setStyle("-fx-accent: orangered");
            status.setText(UtamaController.status);
            warna.setFill(Color.ORANGERED);
        }else{
            progres.setProgress(0.5);
            progres.setStyle("-fx-accent: greenyellow");
            status.setText(UtamaController.status);
            warna.setFill(Color.GREENYELLOW);

        }
        deskripsi.setText(UtamaController.deskripsi);
        jenis.setText(UtamaController.jenis);
        manfaat.setText(UtamaController.manfaat);
        pembaruan.setText(UtamaController.pembaharuan);
        harga.setText(String.valueOf(UtamaController.harga));


        long secondnow = System.currentTimeMillis() / 1000 ;

        LocalDate end = UtamaController.tanggal_berakhir;
        ZoneOffset zoneOffset = ZoneOffset.of("Z");
        LocalTime time = LocalTime.parse("00:00:00");
        long secondend = end.toEpochSecond(time,zoneOffset);

        int sisa = (int) ((secondend - secondnow) / 86400) + 1;

//
        if (sisa <= 0){
            hari.setText("Habis !!!");
            hari.setStyle("-fx-fill: red");
        }else{
            hari.setText(sisa + " Hari Lagi");
            hari.setStyle("-fx-fill: #18da28");
        }



        String mulaistring = String.valueOf(UtamaController.tanggal_mulai);
        String odlmulai = "yyyy-MM-dd";
        String newmulai = "dd MMMM yyyy";

        SimpleDateFormat dateFormat = new SimpleDateFormat(odlmulai);
        try {
            Date date = dateFormat.parse(mulaistring);
            dateFormat.applyPattern(newmulai);
            String convertmulai = dateFormat.format(date);
            mulai.setText( "MULAI : " + convertmulai);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String berakhirstring = String.valueOf(UtamaController.tanggal_berakhir);
        String odlberakhir = "yyyy-MM-dd";
        String newberakhir = "dd MMMM yyyy";

        SimpleDateFormat dateFormat1 = new SimpleDateFormat(odlberakhir);
        try {
            Date date = dateFormat1.parse(berakhirstring);
            dateFormat1.applyPattern(newberakhir);
            String converberakhir = dateFormat1.format(date);
            berakhir.setText("AKHIR : " + converberakhir);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (Objects.equals(UtamaController.nama, "Spotify")){
            File file = new File("images/spotify.png");
            Image images = new Image(file.toURI().toString());
            image.setImage(images);

        } else if (Objects.equals(UtamaController.nama, "Coc")) {
            nama_organisasi.setText(UtamaController.nama);
            File file = new File("images/coc.png");
            Image images = new Image(file.toURI().toString());
            image.setImage(images);
        } else if (Objects.equals(UtamaController.nama, "Youtube")) {
            nama_organisasi.setText(UtamaController.nama);
            File file = new File("images/youtube.png");
            Image images = new Image(file.toURI().toString());
            image.setImage(images);
        } else if (Objects.equals(UtamaController.nama, "Telegram")) {
            nama_organisasi.setText(UtamaController.nama);
            File file = new File("images/telegram.png");
            Image images = new Image(file.toURI().toString());
            image.setImage(images);
        }  else if (Objects.equals(UtamaController.nama, "Google Play")) {
            nama_organisasi.setText(UtamaController.nama);
            File file = new File("images/googleplay.png");
            Image images = new Image(file.toURI().toString());
            image.setImage(images);
        }   else if (Objects.equals(UtamaController.nama, "ChatGpt")) {
            nama_organisasi.setText(UtamaController.nama);
            File file = new File("images/chatgpt.jpg");
            Image images = new Image(file.toURI().toString());
            image.setImage(images);
        }   else if (Objects.equals(UtamaController.nama, "Netflix")) {
            nama_organisasi.setText(UtamaController.nama);
            File file = new File("images/netflix.jpg");
            Image images = new Image(file.toURI().toString());
            image.setImage(images);
        }   else if (Objects.equals(UtamaController.nama, "Wps Office")) {
            nama_organisasi.setText(UtamaController.nama);
            File file = new File("images/wps.png");
            Image images = new Image(file.toURI().toString());
            image.setImage(images);
        }   else if (Objects.equals(UtamaController.nama, "Canva")) {
            nama_organisasi.setText(UtamaController.nama);
            File file = new File("images/canva.png");
            Image images = new Image(file.toURI().toString());
            image.setImage(images);
        }


    }

    public void back(MouseEvent mouseEvent) throws IOException {
        GuiApp.setRoot("utama","HomePage-NoteMer",false);
    }
}
