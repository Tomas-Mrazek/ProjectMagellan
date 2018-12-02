package cz.jaktoviditoka.projectmagellan.controller;

import org.apache.logging.log4j.core.config.Order;
import org.controlsfx.control.ToggleSwitch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.concurrent.ExecutorService;

import javax.annotation.PreDestroy;

import cz.jaktoviditoka.projectmagellan.gui.view.DiscoveredDeviceView;
import cz.jaktoviditoka.projectmagellan.gui.view.PairedDeviceView;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.state.*;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.exception.NotAuthorizedExxception;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model.DeviceModel;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.StateService;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;

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

    @FXML
    VBox pairedDevices;

    @FXML
    AnchorPane actionWindowPane;

    @FXML
    VBox actionWindow;

    ScrollPane actionWindowDiscover;

    @FXML
    ToggleSwitch power;

    @FXML
    Slider brightness;

    @FXML
    TabPane colorModeTab;

    @FXML
    Tab whiteTab;

    @FXML
    Tab colorTab;

    @FXML
    Tab effectTab;

    @FXML
    Slider colorTemperature;

    static final String IMAGE_NANOLEAF_AURORA = "image/nanoleaf-aurora-transparent.png";

    public void initialize() {
        ChangeListener<Boolean> actionWindowDiscoverListener = (obs, wasShowing, isNowShowing) -> {
            if (!isNowShowing) {
                actionWindowDiscover.setContent(new Text("No devices found."));
            }
        };
        actionWindowDiscover = new ScrollPane();
        actionWindowDiscover.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        actionWindowDiscover.setContent(new Text("Searching..."));
        actionWindowDiscover.setFitToWidth(true);
        actionWindowDiscover.setFitToHeight(true);
        actionWindowDiscover.setVisible(false);
        actionWindowDiscover.visibleProperty().addListener(actionWindowDiscoverListener);
        actionWindowPane.getChildren().add(actionWindowDiscover);

        devices = FXCollections.observableSet(new HashSet<>());
        SetChangeListener<Device> devicesListener = change -> {
            if (change.wasAdded()) {
                createPiredDeviceView(change.getElementAdded());
            }
        };
        devices.addListener(devicesListener);
        devices.addAll(deviceModel.getDevices());
    }

    @PreDestroy
    public void shutdown() {
        executor.shutdown();
    }

    private void createPiredDeviceView(Device device) {
        PairedDeviceView view = new PairedDeviceView();
        ImageView image = new ImageView(IMAGE_NANOLEAF_AURORA);
        image.setFitWidth(100);
        image.setPreserveRatio(true);
        Text name = new Text(device.getResolvedName());
        name.setWrappingWidth(200);
        Button unpair = new Button("");
        unpair.getStyleClass().add("close-icon");
        unpair.setOnMouseClicked(event -> {
            unpair(device);
            pairedDevices.getChildren().remove(view);
        });

        view.setImage(image);
        view.setName(name);
        view.setUnpairButton(unpair);
        view.addComponents();

        view.setOnMouseClicked(event -> {
            activeDevice = device;
            actionWindowDiscover.setVisible(false);
            actionWindow.setVisible(true);
            refreshState(device);
        });

        pairedDevices.getChildren().add(view);
    }

    private void refreshState(Device device) {
        OnResponse onResponse = stateService.isOn(device);
        power.setSelected(onResponse.isValue());

        BrightnessResponse brightnessResponse = stateService.getBrightness(device);
        brightness.setMin(brightnessResponse.getMin());
        brightness.setMax(brightnessResponse.getMax());
        brightness.setValue(brightnessResponse.getValue());

        ColorTemperatureResponse colorTemperatureResponse = stateService.getColorTemperature(device);
        colorTemperature.setMin(colorTemperatureResponse.getMin());
        colorTemperature.setMax(colorTemperatureResponse.getMax());
        colorTemperature.setValue(colorTemperatureResponse.getValue());

        ColorMode colorMode = stateService.getColorMode(device);
        SelectionModel<Tab> selectionModel = colorModeTab.getSelectionModel();
        switch (colorMode) {
            case COLOR_TEMPERATURE:
                selectionModel.select(whiteTab);
                break;
            case EFFECT:
                selectionModel.select(effectTab);
                break;
            case HUE_SATURATION:
                selectionModel.select(colorTab);
                break;
            default:
                selectionModel.select(whiteTab);
                break;
        }
    }

    @FXML
    private void changePower() {
        boolean selected = power.isSelected();
        On on = new On(selected);
        OnRequest onRequest = new OnRequest(on);
        stateService.setOn(activeDevice, onRequest);
    }

    @FXML
    private void changeBrightness() {
        Double value = brightness.getValue();
        BrightnessValue brightnessValue = new BrightnessValue();
        brightnessValue.setValue(value.intValue());
        BrightnessRequest brightnessRequest = new BrightnessRequest(brightnessValue);
        stateService.setBrightness(activeDevice, brightnessRequest);
    }

    @FXML
    private void changeColorTemperature() {
        Double value = colorTemperature.getValue();
        ColorTemperatureValue colorTemperatureValue = new ColorTemperatureValue();
        colorTemperatureValue.setValue(value.intValue());
        ColorTemperatureRequest colorTemperatureRequest = new ColorTemperatureRequest(colorTemperatureValue);
        stateService.setColorTemperature(activeDevice, colorTemperatureRequest);
    }

    @FXML
    private void getEffect() {
        //
    }

    @FXML
    void discover() {
        actionWindowDiscover.setContent(new Text("Searching..."));
        FlowPane flowPane = new FlowPane();

        ObservableSet<Device> newDevices = FXCollections.observableSet(new HashSet<>());
        SetChangeListener<Device> newDevicesListener = change -> {
            if (change.wasAdded()) {
                log.debug("Added device to discover list.");
                Device added = change.getElementAdded();

                DiscoveredDeviceView view = new DiscoveredDeviceView();
                ImageView image = new ImageView(IMAGE_NANOLEAF_AURORA);
                image.setFitWidth(100);
                image.setPreserveRatio(true);
                Text name = new Text(added.getResolvedName());
                Button pairButton = new Button("PAIR");
                Text status = new Text();
                pairButton.setOnMouseClicked(event -> {
                    status.setText("PAIRING");
                    status.setStyle("-fx-fill: yellow; -fx-font-size: 16px;");
                    try {
                        if (pair(added)) {
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

                if (devices.contains(change.getElementAdded())) {
                    status.setText("PAIRED");
                    status.setStyle("-fx-fill: green; -fx-font-size: 16px;");
                    pairButton.setDisable(true);
                } else {
                    status.setText("NOT PAIRED");
                    status.setStyle("-fx-fill: red; -fx-font-size: 16px;");
                }

                flowPane.getChildren().add(view);

                if (!change.getSet().isEmpty()) {
                    Platform.runLater(() -> {
                        actionWindowDiscover.setContent(flowPane);
                    });
                } else {
                    Platform.runLater(() -> {
                        actionWindowDiscover.setContent(new Text("No devices found."));
                    });
                }
            }
        };
        newDevices.addListener(newDevicesListener);

        Task<Void> task = new Task<>() {

            @Override
            protected Void call() throws Exception {
                actionWindow.setVisible(false);
                actionWindowDiscover.setVisible(true);
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

    @FXML
    void getBrightness() {
        devices.forEach(d -> {
            log.debug("getBrightness -> \n{}", stateService.getBrightness(d));
        });
    }

    @FXML
    void setBrightness() {
        throw new UnsupportedOperationException();
    }

    @FXML
    void getHue() {
        devices.forEach(d -> {
            log.debug("getHue -> \n{}", stateService.getHue(d));
        });
    }

    @FXML
    void setHue() {
        throw new UnsupportedOperationException();
    }

    @FXML
    void getSaturation() {
        devices.forEach(d -> {
            log.debug("getSaturation -> \n{}", stateService.getSaturation(d));
        });
    }

    @FXML
    void setSaturation() {
        throw new UnsupportedOperationException();
    }

    @FXML
    void getColorTemperature() {
        devices.forEach(d -> {
            log.debug("getColorTemperature -> \n{}", stateService.getColorTemperature(d));
        });
    }

    @FXML
    void setColorTemperature() {
        throw new UnsupportedOperationException();
    }

    @FXML
    void getColorMode() {
        devices.forEach(d -> {
            log.debug("getColorMode -> \n{}", stateService.getColorMode(d));
        });
    }

}
