package cz.jaktoviditoka.projectmagellan.ssdp;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Set;
import java.util.UUID;

import cz.jaktoviditoka.projectmagellan.domain.BaseDeviceType;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SSDPClient {

    private static final String SSDP_IP = "239.255.255.250";
    private static final int SSDP_PORT = 1900;

    int timeout = 5 * 1000;

    public Set<Device> mSearch(BaseDeviceType deviceType, Set<Device> devices) throws IOException {
        byte[] receiveData = new byte[1024];
        
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
        
        log.debug("request \n{}", request);
        
        DatagramPacket sendPacket = new DatagramPacket(request.toString().getBytes(),
                request.toString().getBytes().length, InetAddress.getByName(SSDP_IP), SSDP_PORT);
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(timeout);
        socket.send(sendPacket);
        
        boolean search = true;
        while (search) {
            try {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length,
                        InetAddress.getLocalHost(), SSDP_PORT);
                socket.receive(receivePacket);
                String received = new String(receivePacket.getData(), 0, receivePacket.getLength());
        
                log.debug("response {}\n", received);
        
                if (received.contains("nanoleaf_aurora:light")) {
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
                    devices.add(device);
                }
        
            } catch (SocketTimeoutException e) {
                log.debug("search timeout");
                search = false;
            }
        }
        
        socket.close();
        return devices;
    }

}
