package net.harmelink.powerio.reader.serial;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import net.harmelink.powerio.reader.InputReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;

public class SerialReader implements InputReader {

    private static final Logger LOG = LoggerFactory.getLogger(SerialReader.class);

    private final String port;

    private final BufferedOutputStream outputStream;

    private final boolean debugMode;

    private SerialReader(final String port, final BufferedOutputStream outputStream, final boolean debugMode) {
        this.port = port;
        this.outputStream = outputStream;
        this.debugMode = debugMode;
    }

    public static SerialReader newInstance(final String port, final BufferedOutputStream outputStream,
                                           final boolean debugMode) {
        return new SerialReader(port, outputStream, debugMode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        if (debugMode) {
            logAvailablePorts();
        }

        final SerialPort serialPort = new SerialPort(port);
        final SerialEventListener serialEventListener = new SerialEventListener(serialPort, outputStream);

        try {
            LOG.debug("Using port: {}", port);
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_7, SerialPort.STOPBITS_1,
                    SerialPort.PARITY_EVEN);
            serialPort.setEventsMask(SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR);
            serialPort.addEventListener(serialEventListener);
        } catch (final SerialPortException e) {
            LOG.error("Unable to open serial port for reading: {}", port);
        }
    }

    private void logAvailablePorts() {
        LOG.debug("Available ports:");

        for (final String portName : SerialPortList.getPortNames()) {
            LOG.debug(portName);
        }
    }
}
