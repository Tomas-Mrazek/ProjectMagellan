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
    private Parent rootNode;
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
        rootNode = fxmlLoader.load();
        stage.setScene(new Scene(rootNode));
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void stop() {
        context.stop();
    }

    public static void main(String[] args) {
        launch();
    }

}
