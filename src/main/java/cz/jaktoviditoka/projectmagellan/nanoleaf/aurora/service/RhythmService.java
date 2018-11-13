package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service;

public interface RhythmService {

    void isConnected();

    void isActive();

    void getId();

    void getHardwareVersion();

    void getFirmwareVersion();

    void isAuxAvailable();

    void getMode();

    void setMode();

    void getPosition();

}
