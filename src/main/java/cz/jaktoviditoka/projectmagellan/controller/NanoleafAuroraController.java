package cz.jaktoviditoka.projectmagellan.controller;

import com.jfoenix.controls.JFXSpinner;
import cz.jaktoviditoka.projectmagellan.gui.view.DiscoveredDeviceView;
import cz.jaktoviditoka.projectmagellan.gui.view.PairedDeviceView;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model.DeviceModel;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.StateService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.Order;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
@Component
public class NanoleafAuroraController {

    private Device activeDevice;
    private ObservableSet<Device> devices;

    @Autowired
    DeviceModel deviceModel;

    @Autowired
    StateService stateService;

    Set<DeviceController> deviceControllers;

    @FXML
    VBox rootVbox;

    @FXML
    VBox leftWindow;

    @FXML
    Button discoverButton;

    @FXML
    VBox pairedDevices;

    @FXML
    ScrollPane actionWindowScroll;

    FlowPane actionWindowDiscover;
    FlowPane actionWindowControl;

    static final String IMAGE_NANOLEAF_AURORA = "image/nanoleaf-aurora-transparent-hires.png";

    public void initialize() {
        deviceControllers = new HashSet<>();

        leftWindow.getStyleClass().add("left-window");
        leftWindow.setAlignment(Pos.TOP_CENTER);
        leftWindow.setFillWidth(true);

        discoverButton.setText("DISCOVER");
        discoverButton.getStyleClass().add("discover-button");
        discoverButton.setOnAction(event -> {
            discover();
        });

        actionWindowScroll.getStyleClass().add("action-window-scroll");
        actionWindowScroll.setFitToHeight(true);
        actionWindowScroll.setFitToWidth(true);

        actionWindowDiscover = new FlowPane();
        actionWindowDiscover.setOrientation(Orientation.HORIZONTAL);
        actionWindowDiscover.getStyleClass().add("action-window");

        devices = FXCollections.observableSet(new HashSet<>());
        SetChangeListener<Device> devicesListener = change -> {
            if (change.wasAdded()) {
                DeviceController deviceController = createDeviceController(change.getElementAdded());
                FlowPane window = createActiveWindowControl(deviceController);
                PairedDeviceView deviceView = createPiredDeviceView(deviceController.getDevice(), window);
                Platform.runLater(() -> {
                    pairedDevices.getChildren().add(deviceView);
                });
            }
        };
        devices.addListener(devicesListener);
        devices.addAll(deviceModel.getDevices());
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

    private DeviceController createDeviceController(Device device) {
        DeviceController deviceController = new DeviceController(deviceModel, device);
        deviceController.init();
        deviceController.refresh();
        return deviceController;
    }

    private FlowPane createActiveWindowControl(DeviceController deviceController) {
        FlowPane window = new FlowPane();
        window.setOrientation(Orientation.VERTICAL);
        window.getStyleClass().add("action-window");

        window.getChildren().add(deviceController.getPowerTile());
        window.getChildren().add(deviceController.getBrightnessTile());
        window.getChildren().add(deviceController.getColorModeTab());

        return window;
    }

    private PairedDeviceView createPiredDeviceView(Device device, FlowPane window) {
        PairedDeviceView view = new PairedDeviceView();

        EventHandler<MouseEvent> event = e -> {
            actionWindowScroll.setContent(window);
            activeDevice = device;
            refreshState();
        };

        view.setOnMouseClicked(event);

        view.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));
        view.getStyleClass().add("paired-device");
        view.getStyleClass().add("paired-device-selected");
        ImageView image = new ImageView(IMAGE_NANOLEAF_AURORA);
        image.setFitWidth(64);
        image.setPreserveRatio(true);
        image.setSmooth(true);

        TextField name = new TextField(device.getResolvedName());
        name.getStyleClass().add("paired-device-name");
        name.setDisable(true);

        GlyphFont glyph = GlyphFontRegistry.font("FontAwesome");
        Button unpairButton = new Button("", glyph.create(FontAwesome.Glyph.TRASH).color(Color.RED));
        Button confirmButton = new Button("", glyph.create(FontAwesome.Glyph.CHECK).color(Color.LAWNGREEN));
        Button editButton = new Button("", glyph.create(FontAwesome.Glyph.PENCIL).color(Color.DEEPSKYBLUE));

        unpairButton.getStyleClass().add("button-icon");
        unpairButton.setOnMouseClicked(evt -> {
            unpair(device).subscribe(consumer -> {
                if (consumer) {
                    pairedDevices.getChildren().remove(view);
                    devices.remove(device);
                }
            });
        });

        confirmButton.getStyleClass().add("button-icon");
        confirmButton.setVisible(false);
        confirmButton.setOnMouseClicked(evt -> {
            confirmButton.setVisible(false);
            editButton.setVisible(true);
            name.setDisable(true);
            view.setOnMouseClicked(event);
            device.setDisplayName(name.getText());
            try {
                deviceModel.saveSettings();
                log.debug("device: {}\ndevies: {}", device, deviceModel.getDevices());
            } catch (IOException e) {
                log.warn("Failed to save settings.", e);
            }
        });

        editButton.getStyleClass().add("button-icon");
        editButton.setOnMouseClicked(evt -> {
            confirmButton.setVisible(true);
            editButton.setVisible(false);
            name.setDisable(false);
            view.setOnMouseClicked(null);
        });

        view.setImage(image);
        view.setName(name);
        view.setUnpairButton(unpairButton);
        view.setConfirmButton(confirmButton);
        view.setEditButton(editButton);
        view.addComponents();

        return view;
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
            pairButton.setDisable(true);
            pair(device).log().subscribe(consumer -> {
                if (consumer) {
                    devices.add(device);
                    Platform.runLater(() -> {
                        status.setText("PAIRED");
                        status.setStyle("-fx-fill: green; -fx-font-size: 16px;");
                        pairButton.setDisable(true);
                    });
                } else {
                    Platform.runLater(() -> {
                        status.setText("NOT PAIRED");
                        status.setStyle("-fx-fill: red; -fx-font-size: 16px;");
                        pairButton.setDisable(false);
                    });
                }
            }, errorConsumer -> {
                Platform.runLater(() -> {
                    status.setText("NOT PAIRED");
                    status.setStyle("-fx-fill: red; -fx-font-size: 16px;");
                    pairButton.setDisable(false);
                });
            });
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

    private void discover() {
        JFXSpinner spinner = new JFXSpinner();
        spinner.getStyleClass().add("spinner");

        actionWindowDiscover.getChildren().clear();
        actionWindowDiscover.getChildren().add(spinner);
        actionWindowDiscover.setOrientation(Orientation.HORIZONTAL);
        actionWindowDiscover.setAlignment(Pos.CENTER);
        actionWindowScroll.setContent(actionWindowDiscover);
        discoverButton.setDisable(true);

        ObservableSet<Device> newDevices = FXCollections.observableSet(new HashSet<>());
        SetChangeListener<Device> newDevicesListener = change -> {
            if (change.wasAdded()) {
                log.debug("Added device to discover list.");

                DiscoveredDeviceView view = createDiscoveredDeviceView(change.getElementAdded());
                Platform.runLater(() -> {
                    actionWindowDiscover.setAlignment(Pos.TOP_LEFT);
                    actionWindowDiscover.getChildren().remove(spinner);
                    actionWindowDiscover.getChildren().add(view);
                });

            }
        };
        newDevices.addListener(newDevicesListener);

        deviceModel.discover()
            .log()
            .subscribeOn(Schedulers.elastic())
            .subscribe(device -> {
                newDevices.add(device);
            }, error -> {
                log.error("Error during SSDP discovery.", error);
            }, () -> {
                Platform.runLater(() -> {
                    discoverButton.setDisable(false);
                });
            });
    }

    private Mono<Boolean> pair(Device device) {
        return deviceModel.pair(device);
    }

    private Mono<Boolean> unpair(Device device) {
        return deviceModel.unpair(device);
    }

}
