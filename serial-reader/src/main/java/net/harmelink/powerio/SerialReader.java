package net.harmelink.powerio;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import net.harmelink.powerio.writer.LogWriter;
import net.harmelink.powerio.writer.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerialReader {
    private static final Logger LOG = LoggerFactory.getLogger(SerialReader.class);

    private static final String PORT = "/dev/ttyUSB0";

    public static void main(final String... args) {
        logPorts();
        final SerialPort serialPort = new SerialPort(PORT);
        final SerialEventListener serialEventListener = new SerialEventListener(serialPort, getWriter(args));

        try {
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_7, SerialPort.STOPBITS_1,
                    SerialPort.PARITY_EVEN);
            serialPort.setEventsMask(SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR);
            serialPort.addEventListener(serialEventListener);
        } catch (final SerialPortException e) {
            LOG.error("Unable to open serial port: {}", e.getMessage());
        }
    }

    private static Writer getWriter(final String... args) {
        return new LogWriter();
    }

    private static void logPorts() {
        LOG.debug("Available ports:");

        for (final String portName : SerialPortList.getPortNames()) {
            LOG.debug(portName);
        }

        LOG.debug("Using port: {}" + PORT);
    }
}
