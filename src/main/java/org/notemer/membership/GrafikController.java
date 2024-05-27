package org.notemer.membership;

import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

public class GrafikController implements Initializable {
    public LineChart linechart;
    private Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series series = new XYChart.Series();
        series.setName("Grafik Membership");

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:membership.sqlite");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String user = LoginController.tampunganUsername;
        String query = "SELECT * FROM membership WHERE username = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1,user);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String nama_membership = resultSet.getString("nama_membership");
                int harga = Integer.parseInt(resultSet.getString("harga"));
                series.getData().add(new XYChart.Data(nama_membership, harga));
            }
            linechart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database query error
        }

    }
}
