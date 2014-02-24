package net.harmelink.powerio.model2;

import org.joda.time.DateTime;

import java.util.List;

public class Telegram implements P1Model {
    private DateTime timestamp;

    private Manufacturer manufacturer;

    private String serialNumber;

    private String equipmentIdentifier;

    private Consumption totalPowerConsumedPeak;

    private Consumption totalPowerConsumedOffPeak;

    private Consumption totalPowerDeliveredPeak;

    private Consumption totalPowerDeliveredOffPeak;

    private Consumption powerTariffIndicator;

    private Consumption actualPowerConsumption;

    private Consumption actualPowerDelivery;

    private Consumption actualPowerThreshold;

    private SwitchPosition powerSwitchPosition;

    private String textMessageCodes;

    private String textMessage;

    private List<BusDevice> busDevices;

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(final Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(final String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getEquipmentIdentifier() {
        return equipmentIdentifier;
    }

    public void setEquipmentIdentifier(final String equipmentIdentifier) {
        this.equipmentIdentifier = equipmentIdentifier;
    }

    public Consumption getTotalPowerConsumedPeak() {
        return totalPowerConsumedPeak;
    }

    public void setTotalPowerConsumedPeak(final Consumption totalPowerConsumedPeak) {
        this.totalPowerConsumedPeak = totalPowerConsumedPeak;
    }

    public Consumption getTotalPowerConsumedOffPeak() {
        return totalPowerConsumedOffPeak;
    }

    public void setTotalPowerConsumedOffPeak(final Consumption totalPowerConsumedOffPeak) {
        this.totalPowerConsumedOffPeak = totalPowerConsumedOffPeak;
    }

    public Consumption getTotalPowerDeliveredPeak() {
        return totalPowerDeliveredPeak;
    }

    public void setTotalPowerDeliveredPeak(final Consumption totalPowerDeliveredPeak) {
        this.totalPowerDeliveredPeak = totalPowerDeliveredPeak;
    }

    public Consumption getTotalPowerDeliveredOffPeak() {
        return totalPowerDeliveredOffPeak;
    }

    public void setTotalPowerDeliveredOffPeak(final Consumption totalPowerDeliveredOffPeak) {
        this.totalPowerDeliveredOffPeak = totalPowerDeliveredOffPeak;
    }

    public Consumption getPowerTariffIndicator() {
        return powerTariffIndicator;
    }

    public void setPowerTariffIndicator(final Consumption powerTariffIndicator) {
        this.powerTariffIndicator = powerTariffIndicator;
    }

    public Consumption getActualPowerConsumption() {
        return actualPowerConsumption;
    }

    public void setActualPowerConsumption(final Consumption actualPowerConsumption) {
        this.actualPowerConsumption = actualPowerConsumption;
    }

    public Consumption getActualPowerDelivery() {
        return actualPowerDelivery;
    }

    public void setActualPowerDelivery(final Consumption actualPowerDelivery) {
        this.actualPowerDelivery = actualPowerDelivery;
    }

    public Consumption getActualPowerThreshold() {
        return actualPowerThreshold;
    }

    public void setActualPowerThreshold(final Consumption actualPowerThreshold) {
        this.actualPowerThreshold = actualPowerThreshold;
    }

    public SwitchPosition getPowerSwitchPosition() {
        return powerSwitchPosition;
    }

    public void setPowerSwitchPosition(final SwitchPosition powerSwitchPosition) {
        this.powerSwitchPosition = powerSwitchPosition;
    }

    public String getTextMessageCodes() {
        return textMessageCodes;
    }

    public void setTextMessageCodes(final String textMessageCodes) {
        this.textMessageCodes = textMessageCodes;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(final String textMessage) {
        this.textMessage = textMessage;
    }

    public List<BusDevice> getBusDevices() {
        return busDevices;
    }

    public void setBusDevices(final List<BusDevice> busDevices) {
        this.busDevices = busDevices;
    }
}
