package cz.jaktoviditoka.projectmagellan.controller;

import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.InitializeResponse;
import cz.jaktoviditoka.projectmagellan.razer.chroma.model.RazerSynapseModel;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class RazerSynapseController {

    @Autowired
    RazerSynapseModel model;

    @FXML
    Text text;

    boolean keepAlive;

    @FXML
    private void initialize() {
        keepAlive = true;
        model.initialize()
            .map(InitializeResponse::getSessionid)
            .map(Integer::valueOf)
            .subscribe(consumer -> {
                model.setPort(consumer);
            }, error -> {
                text.setText("SYNAPSE NOT INSTALLED");
            }, () -> {
                text.setText("SYNAPSE INITIALIZED");
                model
                    .ping()
                    .delaySubscription(Duration.ofSeconds(5))
                    .repeat(() -> {
                        return keepAlive;
                    })
                    .subscribe(consumer -> {
                        log.debug("SYNAPSE HEARTBEAT");
                    });
            });

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        keepAlive = false;
    }

}
