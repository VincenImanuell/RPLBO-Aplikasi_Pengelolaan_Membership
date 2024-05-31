package org.notemer.membership;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminControllerUser implements Initializable {
    public MenuButton sort;
    public TableView<User> tabelUser;
    public TableColumn<User,String> username;
    public TableColumn<User,String> password;
    public TableColumn<User,String> namadepan;
    public TableColumn<User,String> namabelakang;
    public TableColumn<User,String> email;
    public TableColumn aksi;
    public Connection conn;
    public Label homeUserName;

    public static int IdUser;
    public static String userna;
    public static String pass;
    public static String depan;
    public static String belakang;
    public static String emailuser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeUserName.setText(LoginController.tampunganUsername);
        tabelUser.setEditable(false);
        username.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        password.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        namadepan.setCellValueFactory(new PropertyValueFactory<User, String>("namaDepan"));
        namabelakang.setCellValueFactory(new PropertyValueFactory<User, String>("namaBelakang"));
        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        Callback<TableColumn<User, String>, TableCell<User, String>> cellFactory = (param) -> {
            final  TableCell<User, String> cell = new TableCell<User, String>(){

                @Override
                public void updateItem(String item, boolean empty){
                    super.updateItem(item, empty);

                    if (empty){
                        setGraphic(null);
                        setText(null);
                    }else{
                        final Button editbutton = new Button("Edit");
                        final Button delbutton = new Button("Delete");

                        editbutton.setStyle("-fx-background-color:#8686e1");
                        delbutton.setStyle("-fx-background-color:#e58585");
                        HBox buton = new HBox(editbutton, delbutton);
                        buton.setPadding(new Insets(5,0,5,0));
                        buton.setAlignment(Pos.CENTER);
//
                        buton.setSpacing(10);

                        editbutton.setOnAction(actionEvent -> {
                            User u = getTableView().getItems().get(getIndex());
                            IdUser = u.getId();
                            userna = u.getUsername();
                            pass = u.getPassword();
                            depan = u.getNamaDepan();
                            belakang = u.getNamaBelakang();
                            emailuser = u.getEmail();

                            try {
                                GuiApp.setRoot("editUserAdmin","Halaman Edit User",false);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        delbutton.setOnAction(actionEvent -> {
                            User user = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Konfirmasi Hapus Data");
                            alert.setHeaderText("Apakah Yakin Anda Ingin Menghapus Data ?");
                            alert.setContentText("Data Anda Akan Dihapus Permanen");
                            Optional<ButtonType> del = alert.showAndWait();
                            if (del.get() == ButtonType.OK){
                                try {
                                    koneksiDB();
                                    String query = "DELETE FROM user WHERE id_user = ?";
                                    PreparedStatement preparedStatement = conn.prepareStatement(query);
                                    preparedStatement.setInt(1,user.getId());
                                    preparedStatement.executeUpdate();
                                    tabelUser.setItems(getDataFromTable());
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                } catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }

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
            tabelUser.setItems(getDataFromTable());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    protected void koneksiDB() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        //SQL Database connection params
        String connectionString = "jdbc:sqlite:membership.sqlite";
        conn = DriverManager.getConnection(connectionString);
    }

    public ObservableList<User> getDataFromTable() throws SQLException, ClassNotFoundException {
        conn = DriverManager.getConnection("jdbc:sqlite:membership.sqlite");
        ResultSet rs;
        String select = "SELECT * FROM user";
        PreparedStatement preparedStatement = conn.prepareStatement(select);
        ResultSet rowsAffected = preparedStatement.executeQuery();

        rs = preparedStatement.executeQuery();
        ObservableList<User> user = FXCollections.observableArrayList(dataBaseArrayList(rs));
        return user;
    }

    private ArrayList dataBaseArrayList(ResultSet rs) throws SQLException {
        ArrayList<User> data = new ArrayList<>();
        while (rs.next()) {
            User p = new User(rs.getInt("id_user"),rs.getString("username"),rs.getString("password"), rs.getString("nama_depan"), rs.getString("nama_belakang"), rs.getString("email"));
            data.add(p);
        }
        return data;
    }

    public void onLogOutClick(ActionEvent actionEvent) throws IOException {
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

    public void onAZ(ActionEvent actionEvent) throws SQLException {
        sort.setText("Username A-Z");
        String query = "SELECT * FROM user ORDER BY username ASC ";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<User> users = FXCollections.observableArrayList();
        while (rs.next()) {
            User m = new User(rs.getInt("id_user"), rs.getString("username"),
                    rs.getString("password"), rs.getString("nama_depan"),
                    rs.getString("nama_belakang"),rs.getString("email"));
            users.add(m);
        }

        tabelUser.setItems(users);
    }

    public void onNone(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        sort.setText("Default");
        MenuItem menuItem = (MenuItem) actionEvent.getSource();
        String sortOption = menuItem.getText();
        sort.setText(sortOption);
        ObservableList<User> sortedData = getDataFromTable();
        tabelUser.setItems(sortedData);
    }

    public void onDetailClick(ActionEvent actionEvent) {
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

    public void onKembali(MouseEvent mouseEvent) throws IOException {
        GuiApp.setRoot("Admin","Halaman Admin User",false);
    }
}
