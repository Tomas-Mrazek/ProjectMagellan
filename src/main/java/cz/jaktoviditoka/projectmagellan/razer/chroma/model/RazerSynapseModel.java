package cz.jaktoviditoka.projectmagellan.razer.chroma.model;

import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.EffectStaticColor;
import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.EffectStaticRequest;
import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.InitializeResponse;
import cz.jaktoviditoka.projectmagellan.razer.chroma.service.HeartbeatService;
import cz.jaktoviditoka.projectmagellan.razer.chroma.service.KeyboardService;
import cz.jaktoviditoka.projectmagellan.razer.chroma.service.RazerService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

import javax.annotation.PostConstruct;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class RazerSynapseModel {

    @Getter
    @Setter
    int port;

    @Getter
    @Setter
    BooleanProperty initialized;

    @Autowired
    RazerService razerService;

    @Autowired
    HeartbeatService heartbeatService;

    @Autowired
    KeyboardService keyboardService;

    boolean keepAlive;

    @PostConstruct
    public void init() {
        initialized = new SimpleBooleanProperty(false);
    }

    public void enableChromaService() {
        keepAlive = true;
        razerService.initialize()
            .map(InitializeResponse::getSessionid)
            .map(Integer::valueOf)
            .log()
            .subscribeOn(Schedulers.elastic())
            .subscribe(
                    consumer -> {
                        port = consumer;
                    }, error -> {
                        initialized.set(false);
                        keepAlive = false;
                        log.warn("Unable to initialize Razer Chroma service.", error);
                    }, () -> {
                        initialized.set(true);
                        heartbeatService.heartbeat(port)
                            .delayElement(Duration.ofSeconds(5))
                            .repeat(() -> keepAlive)
                            .log()
                            .subscribeOn(Schedulers.elastic())
                            .subscribe();
                    });
    }

    public void disableChromaService() {
        keepAlive = false;
        razerService.uninitialize()
            .log()
            .subscribeOn(Schedulers.elastic())
            .subscribe();
    }

    public void changeKeyboardColor(Color color) {
        EffectStaticColor staticColor = new EffectStaticColor();
        int red = Double.valueOf(color.getRed() * 255).intValue();
        int green = Double.valueOf(color.getGreen() * 255).intValue() << 8;
        int blue = Double.valueOf(color.getBlue() * 255).intValue() << 16;
        staticColor.setColor(red | green | blue);
        log.debug("Applying static color to keyboard: {}", staticColor.getColor());
        EffectStaticRequest request = new EffectStaticRequest();
        request.setParam(staticColor);
        keyboardService.applyEffect(port, request)
            .log()
            .subscribeOn(Schedulers.elastic())
            .subscribe();
    }

}
