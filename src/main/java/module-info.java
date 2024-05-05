module org.notemer.membership {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens org.notemer.membership to javafx.fxml;
    exports org.notemer.membership;
}