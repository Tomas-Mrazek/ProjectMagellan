package cz.jaktoviditoka.projectmagellan.razer.chroma.model;

import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.HeartbeatResponse;
import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.InitializeResponse;
import cz.jaktoviditoka.projectmagellan.razer.chroma.service.HeartbeatService;
import cz.jaktoviditoka.projectmagellan.razer.chroma.service.RazerService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class RazerSynapseModel {

    @Getter
    @Setter
    int port;

    @Autowired
    HeartbeatService heartbeatService;

    @Autowired
    RazerService razerService;

    public Mono<InitializeResponse> initialize() {
        return razerService.initialize()
            .subscribeOn(Schedulers.elastic());
    }

    public Mono<HeartbeatResponse> ping() {
        return heartbeatService.heartbeat(port)
            .subscribeOn(Schedulers.elastic());
    }

}
