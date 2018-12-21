package cz.jaktoviditoka.projectmagellan.controller;

import com.jfoenix.controls.JFXSpinner;
import cz.jaktoviditoka.projectmagellan.domain.NanoleafAuroraDevice;
import cz.jaktoviditoka.projectmagellan.gui.view.DiscoveredDeviceView;
import cz.jaktoviditoka.projectmagellan.gui.view.PairedDeviceView;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model.DeviceModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class NanoleafController {

    @Autowired
    ApplicationContext context;

    ObservableSet<NanoleafAuroraDevice> devices;

    @Autowired
    DeviceModel deviceModel;

    Set<AuroraController> deviceControllers;

    @FXML
    VBox leftWindow;

    @FXML
    Button discoverButton;

    @FXML
    VBox pairedDevices;

    @FXML
    ScrollPane actionWindowScroll;

    AuroraController activeDeviceController;

    static final String IMAGE_NANOLEAF_AURORA = "image/nanoleaf-aurora-transparent-hires.png";

    @FXML
    private void initialize() {
        deviceControllers = new HashSet<>();

        devices = FXCollections.observableSet(new HashSet<>());
        SetChangeListener<NanoleafAuroraDevice> devicesListener = change -> {
            if (change.wasAdded()) {
                FXMLLoader loader = createFxmlLoader("/fxml/NanoleafAurora.fxml");
                FlowPane window;
                try {
                    window = loader.load();
                    AuroraController deviceController = loader.getController();
                    deviceController.setDevice(change.getElementAdded());
                    deviceController.refresh();
                    PairedDeviceView deviceView = createPiredDeviceView(deviceController.getDevice(), window,
                            deviceController);
                    Platform.runLater(() -> {
                        pairedDevices.getChildren().add(deviceView);
                    });
                } catch (IOException e) {
                    log.error("Failed to load FXML.", e);
                    Platform.exit();
                }
            }
        };
        devices.addListener(devicesListener);
        devices.addAll(deviceModel.getDevices());
    }

    //@Scheduled(initialDelay = 10000, fixedRate = 5000)
    public void refreshState() {
        Optional<AuroraController> deviceControllerOpt = deviceControllers.stream()
            .filter(e -> e.equals(activeDeviceController))
            .findFirst();
        if (deviceControllerOpt.isPresent()) {
            deviceControllerOpt.get().refresh();
        }
    }

    private PairedDeviceView createPiredDeviceView(NanoleafAuroraDevice device, FlowPane window, AuroraController deviceController) {
        PairedDeviceView view = new PairedDeviceView();

        EventHandler<MouseEvent> event = e -> {
            actionWindowScroll.setContent(window);
            activeDeviceController = deviceController;
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

    private DiscoveredDeviceView createDiscoveredDeviceView(NanoleafAuroraDevice device) {
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

    @FXML
    private void discover() {
        FXMLLoader loader = createFxmlLoader("/fxml/NanoleafDiscover.fxml");
        FlowPane actionWindowDiscover;
        try {
            JFXSpinner spinner = new JFXSpinner();
            spinner.getStyleClass().add("spinner");

            actionWindowDiscover = loader.load();
            actionWindowDiscover.getChildren().clear();
            actionWindowDiscover.getChildren().add(spinner);
            actionWindowScroll.setContent(actionWindowDiscover);
            discoverButton.setDisable(true);
            ObservableSet<NanoleafAuroraDevice> newDevices = FXCollections.observableSet(new HashSet<>());
            SetChangeListener<NanoleafAuroraDevice> newDevicesListener = change -> {
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
        } catch (IOException e) {
            log.error("Failed to load FXML.", e);
            Platform.exit();
        }

    }

    private Mono<Boolean> pair(NanoleafAuroraDevice device) {
        return deviceModel.pair(device);
    }

    private Mono<Boolean> unpair(NanoleafAuroraDevice device) {
        return deviceModel.unpair(device);
    }

    private FXMLLoader createFxmlLoader(String location) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(context::getBean);
        fxmlLoader.setLocation(getClass().getResource(location));
        return fxmlLoader;
    }

}
