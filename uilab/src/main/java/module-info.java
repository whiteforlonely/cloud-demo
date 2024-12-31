module com.example.uilab {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires okhttp3;
    requires annotations;
    requires static lombok;
    requires cn.hutool;
    requires java.sql;

    opens com.example.uilab to javafx.fxml;
    exports com.example.uilab;
}