package cz.jaktoviditoka.projectmagellan.razer.chroma.model;

import cz.jaktoviditoka.projectmagellan.domain.BaseDeviceType;
import cz.jaktoviditoka.projectmagellan.domain.RazerGenericDevice;
import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.DeviceType;
import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.EffectStaticColor;
import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.EffectStaticRequest;
import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.InitializeResponse;
import cz.jaktoviditoka.projectmagellan.razer.chroma.service.DeviceService;
import cz.jaktoviditoka.projectmagellan.razer.chroma.service.HeartbeatService;
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
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class RazerSynapseModel {

    @Autowired
    RazerService razerService;

    @Autowired
    HeartbeatService heartbeatService;

    @Autowired
    DeviceService deviceService;

    @Getter
    Set<RazerGenericDevice> devices;

    @Getter
    @Setter
    int port;

    @Getter
    @Setter
    BooleanProperty initialized;

    boolean keepAlive;

    @PostConstruct
    public void init() {
        initialized = new SimpleBooleanProperty(false);
        keepAlive = false;
        createGenericDevices();
    }

    public void getInfo() {
        razerService.getInto()
            .log()
            .subscribeOn(Schedulers.elastic())
            .subscribe();
    }

    public void keepAliveService() {
        heartbeatService.heartbeat(port)
            .delayElement(Duration.ofSeconds(5))
            .repeat(() -> keepAlive)
            .log()
            .subscribeOn(Schedulers.elastic())
            .subscribe();
    }

    public Mono<InitializeResponse> enableChromaService() {
        keepAlive = true;
        return razerService.initialize();
    }

    public Mono<Void> disableChromaService() {
        keepAlive = false;
        return razerService.uninitialize();
    }

    public void changeDeviceColor(Color color, DeviceType device) {
        EffectStaticColor staticColor = new EffectStaticColor();
        staticColor.setColor(colorToBgr(color));
        log.debug("Applying static color to keyboard: {}", staticColor.getColor());
        EffectStaticRequest request = new EffectStaticRequest();
        request.setParam(staticColor);
        deviceService.applyEffect(port, device, request)
            .log()
            .subscribeOn(Schedulers.elastic())
            .subscribe();
    }

    private void createGenericDevices() {
        devices = new HashSet<>();
        Set<BaseDeviceType> baseDeviceTypes = Set.of(BaseDeviceType.values());
        baseDeviceTypes.stream()
            .filter(element -> {
                return element.name().contains("RAZER");
            })
            .forEach(element -> {
                RazerGenericDevice device = new RazerGenericDevice(element);
                device.setUuid(UUID.randomUUID());
                switch (element) {
                    case RAZER_CHROMA_LINK:
                        device.setName("Razer Chroma Link");
                        break;
                    case RAZER_HEADSET:
                        device.setName("Razer generic headset");
                        break;
                    case RAZER_KEYBOARD:
                        device.setName("Razer generic keyboard");
                        break;
                    case RAZER_KEYPAD:
                        device.setName("Razer generic keypad");
                        break;
                    case RAZER_MOUSE:
                        device.setName("Razer generic mouse");
                        break;
                    case RAZER_MOUSEPAD:
                        device.setName("Razer generic mousepad");
                        break;
                    default:
                        break;
                }
                devices.add(device);
            });
    }

    private int colorToBgr(Color color) {
        int red = Double.valueOf(color.getRed() * 255).intValue();
        int green = Double.valueOf(color.getGreen() * 255).intValue() << 8;
        int blue = Double.valueOf(color.getBlue() * 255).intValue() << 16;
        return red | green | blue;
    }

}
