package net.harmelink.powerio.reader.serial;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import net.harmelink.powerio.reader.InputReader;
import net.harmelink.powerio.writer.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerialReader implements InputReader {
    private static final Logger LOG = LoggerFactory.getLogger(SerialReader.class);

    private final String port;

    private final Writer writer;

    private final boolean debugMode;

    public SerialReader(final String port, final Writer writer, final boolean debugMode) {
        this.port = port;
        this.writer = writer;
        this.debugMode = debugMode;
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
        final SerialEventListener serialEventListener = new SerialEventListener(serialPort, writer);

        try {
            LOG.debug("Using port: {}", port);
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_7, SerialPort.STOPBITS_1,
                    SerialPort.PARITY_EVEN);
            serialPort.setEventsMask(SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR);
            serialPort.addEventListener(serialEventListener);
        } catch (final SerialPortException e) {
            LOG.error("Unable to open serial port: {}", e.getMessage());
        }
    }

    private void logAvailablePorts() {
        LOG.debug("Available ports:");

        for (final String portName : SerialPortList.getPortNames()) {
            LOG.debug(portName);
        }
    }
}
