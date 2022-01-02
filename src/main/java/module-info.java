module com.example.project_test {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project_test to javafx.fxml;
    exports com.example.project_test;
}