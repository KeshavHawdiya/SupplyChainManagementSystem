module com.example.supplychain {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.SupplyChainSystem to javafx.fxml;
    exports com.example.SupplyChainSystem;
}