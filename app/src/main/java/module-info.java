module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // 👇 estos son los paquetes que JavaFX debe poder ver para crear controladores
    opens com.example.app.controllers to javafx.fxml;
    opens com.example.app.database to javafx.fxml;
    opens com.example.app to javafx.fxml;

    // 👇 exporta los paquetes (para que sean accesibles desde otros módulos si hace falta)
    exports com.example.app;
    exports com.example.app.controllers;
    exports com.example.app.database;
}
