package net.harmelink.powerio.reader.serial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.IOException;

class SerialEventListener implements SerialPortEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(SerialEventListener.class);

    private static final int BUFFER = 20;

    private final SerialPort serialPort;

    private final BufferedOutputStream outputStream;

    public SerialEventListener(final SerialPort serialPort, final BufferedOutputStream outputStream) {
        this.serialPort = serialPort;
        this.outputStream = outputStream;
    }

    public void serialEvent(final SerialPortEvent event) {
        if (event.isRXCHAR()) {
            if (event.getEventValue() >= BUFFER) {
                try {
                    final byte[] buffer = serialPort.readBytes(BUFFER);
                    outputStream.write(buffer);
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