package net.harmelink.powerio;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import net.harmelink.powerio.writer.AbstractWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SerialEventListener implements SerialPortEventListener {
    private static final Logger LOG = LoggerFactory.getLogger(SerialEventListener.class);

    private static final int BUFFER = 20;

    private final SerialPort serialPort;

    private final AbstractWriter writer;

    public SerialEventListener(final SerialPort serialPort, final AbstractWriter writer) {
        this.serialPort = serialPort;
        this.writer = writer;
    }

    public void serialEvent(final SerialPortEvent event) {
        if (event.isRXCHAR()) {
            if (event.getEventValue() >= BUFFER) {
                try {
                    byte buffer[] = serialPort.readBytes(BUFFER);
                    writer.write(buffer);
                } catch (final SerialPortException e) {
                    LOG.error("Unable to read from serial port: {}", e.getMessage());
                } catch (final IOException e) {
                    LOG.error("Unable to serialize serial data: {}", e.getMessage());
                }
            }
        } else if (event.isCTS()) {
            LOG.debug("CTS - {}", event.getEventValue() == 1 ? "ON" : "OFF");
        } else if (event.isDSR()) {
            LOG.debug("DSR - {}", event.getEventValue() == 1 ? "ON" : "OFF");
        }
    }
}