package org.notemer.membership;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.sql.*;

public class UtamaController implements Initializable {

    public Label homeUserName;
    public Label ambilId;
    public TableView<Member> tabelMember;
    public TableColumn<Member, String> kolomNama;
    public TableColumn<Member, String> kolomJenis;
    public TableColumn<Member, Date> kolomMulai;
    public TableColumn<Member, Date> kolomBerakhir;
    public TableColumn<Member, Integer> kolomHarga;
    public TableColumn<Member, String> kolomStatus;
    public TableColumn aksi;
    private Connection conn;

    @FXML
    private TextField searchBox;

    public static int id_membership;
    public static String nama;
    public static String jenis;
    public static LocalDate tanggal_mulai;
    public static LocalDate tanggal_berakhir;
    public static String pembaharuan;
    public static String kontak;
    public static String status;
    public static int harga;
    public  static String manfaat;

    public static String deskripsi;



    private ObservableList<Member> memberList;
    private FilteredList<Member> filteredData;

    protected void koneksiDB() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        //SQL Database connection params
        String connectionString = "jdbc:sqlite:membership.sqlite";
        conn = DriverManager.getConnection(connectionString);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        homeUserName.setText(LoginController.tampunganUsername);
        tabelMember.setEditable(false);
        kolomNama.setCellValueFactory(new PropertyValueFactory<Member, String>("nama_membership"));
        kolomJenis.setCellValueFactory(new PropertyValueFactory<Member, String>("jenis_keanggotaan"));
        kolomMulai.setCellValueFactory(new PropertyValueFactory<Member, Date>("tanggal_mulai"));
        kolomBerakhir.setCellValueFactory(new PropertyValueFactory<Member, Date>("tanggal_berakhir"));
        kolomHarga.setCellValueFactory(new PropertyValueFactory<Member, Integer>("harga"));
        kolomStatus.setCellValueFactory(new PropertyValueFactory<Member, String>("status"));

        Callback<TableColumn<Member, String>, TableCell<Member, String>> cellFactory = (param) -> {
            final TableCell<Member, String> cell = new TableCell<Member, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button editbutton = new Button("Edit");
                        final Button delbutton = new Button("Delete");
                        final Button detail = new Button("Detail");

                        editbutton.setStyle("-fx-background-color:#8686e1");
                        delbutton.setStyle("-fx-background-color:#e58585");
                        detail.setStyle("-fx-background-color: aqua");
                        HBox buton = new HBox(detail, editbutton, delbutton);
                        buton.setPadding(new Insets(5, 0, 5, 0));
                        buton.setAlignment(Pos.CENTER);
//
                        buton.setSpacing(10);

                        editbutton.setOnAction(actionEvent -> {
                            Member m = getTableView().getItems().get(getIndex());
                            id_membership = m.getId_membership();
                            nama = m.getNama_membership();
                            jenis = m.getJenis_keanggotaan();
                            tanggal_mulai = m.getTanggal_mulai();
                            tanggal_berakhir = m.getTanggal_berakhir();
                            pembaharuan = m.getSiklus_pembaruan();
                            kontak = m.getKontak();
                            status = m.getStatus();
                            harga = Integer.parseInt(m.getHarga());
                            manfaat = m.getManfaat();
                            deskripsi = m.getDeskripsi();
                            try {
                                GuiApp.setRoot("edit", "Halaman edit", false);

                                Riwayat riwayat = new Riwayat(LoginController.tampunganUsername, LocalDateTime.now(), "Mengedit Membership: " + nama);
                                simpanRiwayatAktivitas(riwayat);

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        delbutton.setOnAction(actionEvent -> {
                            Member m = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Konfirmasi Hapus Data");
                            alert.setHeaderText("Apakah Yakin Anda Ingin Menghapus Data ?");
                            alert.setContentText("Data Anda Akan Dihapus Permanen");
                            Optional<ButtonType> del = alert.showAndWait();
                            if (del.get() == ButtonType.OK) {
                                try {
                                    koneksiDB();
                                    String query = "DELETE FROM membership WHERE id_membership = ?";
                                    PreparedStatement preparedStatement = conn.prepareStatement(query);
                                    preparedStatement.setInt(1, m.getId_membership());
                                    preparedStatement.executeUpdate();
                                    tabelMember.setItems(getDataFromTable());

                                    Riwayat riwayat = new Riwayat(LoginController.tampunganUsername, LocalDateTime.now(), "Menghapus Membership: " + m.getNama_membership());
                                    simpanRiwayatAktivitas(riwayat);

                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                } catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        });

                        detail.setOnAction(actionEvent -> {
                            Member m = getTableView().getItems().get(getIndex());
                            id_membership = m.getId_membership();
                            nama = m.getNama_membership();
                            jenis = m.getJenis_keanggotaan();
                            tanggal_mulai = m.getTanggal_mulai();
                            tanggal_berakhir = m.getTanggal_berakhir();
                            pembaharuan = m.getSiklus_pembaruan();
                            kontak = m.getKontak();
                            status = m.getStatus();
                            harga = Integer.parseInt(m.getHarga());
                            manfaat = m.getManfaat();
                            deskripsi = m.getDeskripsi();
                            try {
                                GuiApp.setRoot("detail-membership", "Halaman Detail", false);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        setGraphic(buton);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        aksi.setCellFactory(cellFactory);

        try {
            koneksiDB();
            memberList = getDataFromTable();
            filteredData = new FilteredList<>(memberList, p -> true);

            SortedList<Member> sortedData = new SortedList<>(filteredData);

            tabelMember.setItems(sortedData);
            searchBox.setOnKeyPressed(this::handleSearch);
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

        rs = preparedStatement.executeQuery();
        ObservableList<Member> member = FXCollections.observableArrayList(dataBaseArrayList(rs));
        return member;
    }

    private ArrayList dataBaseArrayList(ResultSet rs) throws SQLException {
        ArrayList<Member> data = new ArrayList<>();
        while (rs.next()) {
            Member p = new Member(rs.getInt("id_membership"),rs.getString("nama_membership"), rs.getString("jenis_keanggotaan"), rs.getObject("tanggal_mulai", LocalDate.class), rs.getObject("tanggal_selesai", LocalDate.class), rs.getString("siklus_pembaruan"), rs.getString("kontak"), rs.getString("status"), rs.getString("harga"), rs.getString("manfaat"), rs.getString("deskripsi"));
            data.add(p);
        }
        return data;
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

    private void handleSearch(KeyEvent event) {
        String searchText = searchBox.getText().toLowerCase();
        filteredData.setPredicate(membership -> {
            if (searchText.isEmpty()) {
                return true;
            } else {
                return (
                        membership.getNama_membership().toLowerCase().contains(searchText) ||
                                membership.getJenis_keanggotaan().toLowerCase().contains(searchText) ||
                                membership.getStatus().toLowerCase().contains(searchText)
                );
            }
        });
    }
    public void onProfilClick() {
        try {
            GuiApp.setRoot("profil-detail", "Profil-NoteMer", false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onAddClick() {
        try {
            GuiApp.setRoot("input", "Add-Membership-NoteMer", false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onRiwayatClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("riwayat.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Riwayat Aktivitas");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void simpanRiwayatAktivitas(Riwayat riwayat) {
        String insertSQL = "INSERT INTO riwayat (waktu, keterangan, username) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:membership.sqlite");
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, riwayat.getWaktu().toString());
            pstmt.setString(2, riwayat.getKeterangan());
            pstmt.setString(3, riwayat.getUsername());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
