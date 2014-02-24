package net.harmelink.powerio.model2;

import org.joda.time.DateTime;

public class BusDevice implements P1Model {
    private Integer deviceType;

    private Consumption totalConsumed;

    private DateTime measureDate;

    private ValvePosition valvePosition;

    private String equipmentIdentifier;

    public String getEquipmentIdentifier() {
        return equipmentIdentifier;
    }

    public void setEquipmentIdentifier(final String equipmentIdentifier) {
        this.equipmentIdentifier = equipmentIdentifier;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(final Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Consumption getTotalConsumed() {
        return totalConsumed;
    }

    public void setTotalConsumed(final Consumption totalConsumed) {
        this.totalConsumed = totalConsumed;
    }

    public DateTime getMeasureDate() {
        return measureDate;
    }

    public void setMeasureDate(final DateTime measureDate) {
        this.measureDate = measureDate;
    }

    public ValvePosition getValvePosition() {
        return valvePosition;
    }

    public void setValvePosition(final ValvePosition valvePosition) {
        this.valvePosition = valvePosition;
    }
}
