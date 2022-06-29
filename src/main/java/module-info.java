module com.example.finanzmanager_java {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires itextpdf;
    requires org.postgresql.jdbc;
    requires org.joda.time;

    opens finanzmanager;
    exports finanzmanager;
    exports database;
    opens database to javafx.fxml;
}