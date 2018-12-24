package cz.jaktoviditoka.projectmagellan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;

@Slf4j
@SpringBootApplication
public class ProjectMagellan extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        context = SpringApplication.run(ProjectMagellan.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(context::getBean);
        fxmlLoader.setLocation(getClass().getResource("/fxml/Main.fxml"));
        Parent rootNode = fxmlLoader.load();
        Scene scene = new Scene(rootNode);
        scene.getStylesheets().add("/style.css");
        stage.setWidth(Screen.getPrimary().getBounds().getWidth() * 0.85);
        stage.setHeight(Screen.getPrimary().getBounds().getHeight() * 0.85);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void stop() {
        Schedulers.shutdownNow();
        SpringApplication.exit(context, () -> 0);
    }

    public static void main(String[] args) {
        launch();
    }

}
