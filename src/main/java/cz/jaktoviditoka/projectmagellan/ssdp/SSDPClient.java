package cz.jaktoviditoka.projectmagellan.ssdp;

import cz.jaktoviditoka.projectmagellan.domain.BaseDeviceType;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.net.*;
import java.util.UUID;

@Slf4j
@Component
public class SSDPClient {

    private static final String SSDP_IP = "239.255.255.250";
    private static final int SSDP_PORT = 1900;
    private static final int TIMEOUT = 5000;

    private static final String NANOLEAF_AURORA = "nanoleaf_aurora:light";

    public Flux<Device> mSearch(BaseDeviceType deviceType) throws UnknownHostException {

        StringBuilder request = new StringBuilder();
        switch (deviceType) {
            case NANOLEAF_AURORA:
                request.append("M-SEARCH * HTTP/1.1\r\n");
                request.append("Host: " + SSDP_IP + ":" + SSDP_PORT + "\r\n");
                request.append("Man: ssdp:discover\r\n");
                request.append("ST: nanoleaf_aurora:light\n");
                request.append("MX: 3\r\n");
                break;
            default:
                throw new IllegalArgumentException("DeviceType not provided.");
        }
        log.trace("request \n{}", request);

        byte[] receiveData = new byte[1024];
        DatagramPacket sendPacket = new DatagramPacket(request.toString().getBytes(),
                request.toString().getBytes().length, InetAddress.getByName(SSDP_IP), SSDP_PORT);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length,
                InetAddress.getLocalHost(), SSDP_PORT);

        return Flux.create(emitter -> {
            try (DatagramSocket socket = new DatagramSocket()) {
                socket.setSoTimeout(TIMEOUT);
                socket.send(sendPacket);
                while (true) {
                    socket.receive(receivePacket);
                    emitter.next(receivePacket.getData());
                }
            } catch (SocketTimeoutException e) {
                emitter.complete();
            } catch (IOException e) {
                emitter.error(e);
            }
        })
            .map(mapper -> new String(receivePacket.getData(), 0, receivePacket.getLength()))
            .filter(filter -> supportedDevices(filter))
            .map(mapper -> transform(mapper));
    }

    private boolean supportedDevices(String response) {
        boolean supported = false;
        if (response.contains(NANOLEAF_AURORA)) {
            supported = true;
        }
        return supported;
    }

    private Device transform(String received) {
        try {
            if (received.contains(NANOLEAF_AURORA)) {
                int indexLocation = received.indexOf("Location:") + 10;
                int indexName = received.indexOf("nl-devicename:") + 15;
                int indexUUID = received.indexOf("USN: uuid:") + 10;
                String urlString = received.substring(indexLocation, received.indexOf("\r\n", indexLocation));
                URL url = new URL(urlString);
                Device device = new Device(BaseDeviceType.NANOLEAF_AURORA);
                device.setUuid(UUID.fromString(received.substring(indexUUID, received.indexOf("\r\n", indexUUID))));
                device.setName(received.substring(indexName, received.indexOf("\r\n", indexName)));
                device.setIp(InetAddress.getByName(url.getHost()));
                device.setPort(url.getPort());
                return device;
            }
        } catch (MalformedURLException | UnknownHostException e) {
            log.warn("Failed to create device from SSDP response.", e);
        }
        return null;
    }

}
