package net.harmelink.powerio;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import net.harmelink.powerio.writer.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SerialEventListener implements SerialPortEventListener {
    private static final Logger LOG = LoggerFactory.getLogger(SerialEventListener.class);

    private final SerialPort serialPort;

    private final Writer writer;

    public SerialEventListener(final SerialPort serialPort, final Writer writer) {
        this.serialPort = serialPort;
        this.writer = writer;
    }

    public void serialEvent(final SerialPortEvent event) {
        LOG.debug("Status: {} - {}", event.getEventType(), event.getEventValue());

        if (event.isDSR()) { // If data is available
            if (event.getEventValue() == 10) { // Check bytes count in the input buffer
                try {
                    byte buffer[] = serialPort.readBytes(10); // Read data, if 10 bytes available
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
            LOG.debug("DSR - ON{}", event.getEventValue() == 1 ? "ON" : "OFF");
        }
    }
}