package cz.jaktoviditoka.projectmagellan.controller;

import com.jfoenix.controls.JFXListView;
import cz.jaktoviditoka.projectmagellan.gui.view.DiscoveredDeviceView;
import cz.jaktoviditoka.projectmagellan.gui.view.PairedDeviceView;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.exception.NotAuthorizedExxception;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model.DeviceModel;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.StateService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import javax.annotation.PreDestroy;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
@Component
public class NanoleafAuroraController {

    private Device activeDevice;
    private ObservableSet<Device> devices;

    @Autowired
    ExecutorService executor;

    @Autowired
    DeviceModel deviceModel;

    @Autowired
    StateService stateService;

    Set<DeviceController> deviceControllers;

    @FXML
    JFXListView<PairedDeviceView> pairedDevices;

    @FXML
    ScrollPane actionWindowScroll;

    FlowPane actionWindowDiscover;
    FlowPane actionWindowControl;

    TabPane colorModeTab;
    Tab whiteTab;
    Tab colorTab;
    Tab effectTab;
    Slider colorTemperature;

    static final String IMAGE_NANOLEAF_AURORA = "image/nanoleaf-aurora-transparent-2.png";

    public void initialize() {
        deviceControllers = new HashSet<>();

        colorModeTab = new TabPane();
        whiteTab = new Tab();
        colorTab = new Tab();
        effectTab = new Tab();
        colorTemperature = new Slider();

        actionWindowScroll.setFitToHeight(true);
        actionWindowScroll.setFitToWidth(true);

        actionWindowDiscover = new FlowPane();
        actionWindowDiscover.setOrientation(Orientation.HORIZONTAL);
        actionWindowDiscover.getStyleClass().add("actionWindow");

        devices = FXCollections.observableSet(new HashSet<>());
        SetChangeListener<Device> devicesListener = change -> {
            if (change.wasAdded()) {
                DeviceController deviceController = createDeviceController(change.getElementAdded());
                createPiredDeviceView(deviceController);
            }
        };
        devices.addListener(devicesListener);
        devices.addAll(deviceModel.getDevices());

        Optional<Device> activeDeviceOpt = devices.stream().findFirst();
        if (activeDeviceOpt.isPresent()) {
            activeDevice = activeDeviceOpt.get();
            actionWindowScroll.setContent(actionWindowControl);
        } else {
            actionWindowScroll.setContent(actionWindowDiscover);
        }
    }

    @PreDestroy
    public void shutdown() {
        executor.shutdown();
    }

    private DeviceController createDeviceController(Device device) {
        DeviceController deviceController = new DeviceController(deviceModel, device);
        deviceController.init();
        deviceController.refresh();
        return deviceController;
    }

    private void createPiredDeviceView(DeviceController deviceController) {
        Device device = deviceController.getDevice();

        PairedDeviceView view = new PairedDeviceView();
        ImageView image = new ImageView(IMAGE_NANOLEAF_AURORA);
        image.setFitWidth(100);
        image.setPreserveRatio(true);
        Label name = new Label(device.getResolvedName());
        Button unpair = new Button("X");
        unpair.getStyleClass().add("close-icon");
        unpair.setOnMouseClicked(event -> {
            unpair(device);
            pairedDevices.getItems().remove(view);
        });

        view.setImage(image);
        view.setName(name);
        view.setUnpairButton(unpair);
        view.addComponents();

        FlowPane window = createActiveWindowControl(deviceController);

        view.setOnMouseClicked(event -> {
            actionWindowScroll.setContent(window);
            activeDevice = device;
            refreshState();
        });

        pairedDevices.getItems().add(view);
    }

    private FlowPane createActiveWindowControl(DeviceController deviceController) {
        FlowPane window = new FlowPane();
        window.setOrientation(Orientation.VERTICAL);
        window.getStyleClass().add("actionWindow");

        window.getChildren().add(deviceController.getOn());
        window.getChildren().add(deviceController.getBrightness());
        window.getChildren().add(deviceController.getColorTemperature());

        return window;
    }

    //@Scheduled(initialDelay = 10000, fixedRate = 5000)
    public void refreshState() {
        Optional<DeviceController> deviceControllerOpt = deviceControllers.stream()
            .filter(e -> e.getDevice().equals(activeDevice))
            .findFirst();
        if (deviceControllerOpt.isPresent()) {
            deviceControllerOpt.get().refresh();
        }
    }

    private DiscoveredDeviceView createDiscoveredDeviceView(Device device) {
        DiscoveredDeviceView view = new DiscoveredDeviceView();
        view.getStyleClass().add("actionTile");
        view.prefWidth(600);
        view.setSpacing(20);
        view.setAlignment(Pos.CENTER);

        ImageView image = new ImageView(IMAGE_NANOLEAF_AURORA);
        image.setFitWidth(200);
        image.setPreserveRatio(true);
        Text name = new Text(device.getResolvedName());
        name.getStyleClass().add("discoverTileText");
        Button pairButton = new Button("PAIR");
        Text status = new Text();
        pairButton.setOnMouseClicked(event -> {
            status.setText("PAIRING");
            status.setStyle("-fx-fill: yellow; -fx-font-size: 16px;");
            try {
                if (pair(device)) {
                    status.setText("PAIRED");
                    status.setStyle("-fx-fill: green; -fx-font-size: 16px;");
                    pairButton.setDisable(true);
                }
            } catch (NotAuthorizedExxception e) {
                status.setText("DEVICE NOT IN PAIRING MODE");
                status.setStyle("-fx-fill: yellow; -fx-font-size: 16px;");
            }
        });
        view.setImage(image);
        view.setName(name);
        view.setPairButton(pairButton);
        view.setStatus(status);
        view.addComponents();

        if (devices.contains(device)) {
            status.setText("PAIRED");
            status.setStyle("-fx-fill: green; -fx-font-size: 16px;");
            pairButton.setDisable(true);
        } else {
            status.setText("NOT PAIRED");
            status.setStyle("-fx-fill: red; -fx-font-size: 16px;");
        }
        return view;
    }

    @FXML
    void discover() {
        actionWindowDiscover.getChildren().clear();
        actionWindowDiscover.setOrientation(Orientation.HORIZONTAL);
        actionWindowScroll.setContent(actionWindowDiscover);

        ObservableSet<Device> newDevices = FXCollections.observableSet(new HashSet<>());
        SetChangeListener<Device> newDevicesListener = change -> {
            if (change.wasAdded()) {
                log.debug("Added device to discover list.");

                DiscoveredDeviceView view = createDiscoveredDeviceView(change.getElementAdded());
                Platform.runLater(() -> {
                    actionWindowDiscover.getChildren().add(view);
                });

            }
        };
        newDevices.addListener(newDevicesListener);

        Task<Void> task = new Task<>() {

            @Override
            protected Void call() throws Exception {
                deviceModel.discover(newDevices);
                log.debug("Discovered devices: {}", newDevices);
                return null;
            }

        };

        executor.execute(task);
    }

    private boolean pair(Device device) throws NotAuthorizedExxception {
        if (deviceModel.pair(device)) {
            devices.add(device);
            return true;
        }
        return false;
    }

    private boolean unpair(Device device) {
        if (deviceModel.unpair(device)) {
            devices.remove(device);
            return true;
        }
        return false;
    }

}
