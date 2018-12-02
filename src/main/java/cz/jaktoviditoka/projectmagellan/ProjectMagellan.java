package cz.jaktoviditoka.projectmagellan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
public class ProjectMagellan extends Application {

    private ConfigurableApplicationContext context;
    private FXMLLoader fxmlLoader;

    @Override
    public void init() {
        context = SpringApplication.run(ProjectMagellan.class);
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(context::getBean);
    }

    @Override
    public void start(Stage stage) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/nanoleaf-aurora.fxml"));
        Parent rootNode = fxmlLoader.load();
        Scene scene = new Scene(rootNode);
        scene.getStylesheets().add("/style.css");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void stop() {
        SpringApplication.exit(context, () -> 0);
    }

    public static void main(String[] args) {
        launch();
    }

}
