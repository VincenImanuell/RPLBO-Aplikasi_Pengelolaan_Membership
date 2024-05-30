package org.notemer.membership;

import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AdminGrafikController implements Initializable {

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
        String query = "SELECT * FROM membership";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
