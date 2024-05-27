package org.notemer.membership;

import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.sql.*;
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

        try {
            byte[] memberPic = getProfileImageFromTable();
            if (memberPic != null) {
                Image imagex = new Image(new ByteArrayInputStream(memberPic));
                image.setImage(imagex);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
        nama_organisasi.setText(UtamaController.nama);

        if (Objects.equals(UtamaController.nama, "Spotify")){
            File file = new File("images/spotify.png");
            Image images = new Image(file.toURI().toString());
            image.setImage(images);

        } else if (Objects.equals(UtamaController.nama, "Coc")) {
            File file = new File("images/coc.png");
            Image images = new Image(file.toURI().toString());
            image.setImage(images);
        } else if (Objects.equals(UtamaController.nama, "Youtube")) {
            File file = new File("images/youtube.png");
            Image images = new Image(file.toURI().toString());
            image.setImage(images);
        } else if (Objects.equals(UtamaController.nama, "Telegram")) {
            File file = new File("images/telegram.png");
            Image images = new Image(file.toURI().toString());
            image.setImage(images);
        }  else if (Objects.equals(UtamaController.nama, "Google Play")) {
            File file = new File("images/googleplay.png");
            Image images = new Image(file.toURI().toString());
            image.setImage(images);
        }   else if (Objects.equals(UtamaController.nama, "ChatGpt")) {
            File file = new File("images/chatgpt.jpg");
            Image images = new Image(file.toURI().toString());
            image.setImage(images);
        }   else if (Objects.equals(UtamaController.nama, "Netflix")) {
            File file = new File("images/netflix.jpg");
            Image images = new Image(file.toURI().toString());
            image.setImage(images);
        }   else if (Objects.equals(UtamaController.nama, "Wps Office")) {
            File file = new File("images/wps.png");
            Image images = new Image(file.toURI().toString());
            image.setImage(images);
        }   else if (Objects.equals(UtamaController.nama, "Canva")) {
            File file = new File("images/canva.png");
            Image images = new Image(file.toURI().toString());
            image.setImage(images);
        }

    }

    public void back(MouseEvent mouseEvent) throws IOException {
        GuiApp.setRoot("utama","HomePage-NoteMer",false);
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

                String update = "UPDATE membership SET membership_picture = ? WHERE username = ? AND nama_membership = ?";
                Connection conn = DriverManager.getConnection("jdbc:sqlite:membership.sqlite");
                PreparedStatement pstmt = conn.prepareStatement(update);
                pstmt.setBytes(1, bytes);
                pstmt.setString(2, LoginController.tampunganUsername);
                pstmt.setString(3, UtamaController.nama);
                pstmt.executeUpdate();

                image.setImage(new Image(new ByteArrayInputStream(bytes)));
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getProfileImageFromTable() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:membership.sqlite")) {
            String select = "SELECT membership_picture FROM membership WHERE username = ? AND nama_membership = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(select);
            preparedStatement.setString(1, LoginController.tampunganUsername);
            preparedStatement.setString(2, UtamaController.nama);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.getBytes("membership_picture");
        }
    }
}
