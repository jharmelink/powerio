package net.harmelink.powerio;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerialEventListener implements SerialPortEventListener {
    private static final Logger LOG = LoggerFactory.getLogger(SerialEventListener.class);

    private final SerialPort serialPort;

    public SerialEventListener(final SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public void serialEvent(final SerialPortEvent event) {
        if (event.isRXCHAR()) { // If data is available
            if (event.getEventValue() == 10) { // Check bytes count in the input buffer
                try {
                    byte buffer[] = serialPort.readBytes(10); // Read data, if 10 bytes available
                    LOG.debug(buffer.toString());
                } catch (final SerialPortException e) {
                    LOG.error("Unable to read from serial port: {}", e.getMessage());
                }
            }
        } else if (event.isCTS()) {
            LOG.debug("CTS - {}", event.getEventValue() == 1 ? "ON" : "OFF");
        } else if (event.isDSR()) {
            LOG.debug("DSR - ON{}", event.getEventValue() == 1 ? "ON" : "OFF");
        }
    }
}