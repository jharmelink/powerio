package net.harmelink.powerio;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import net.harmelink.powerio.writer.AbstractWriter;
import net.harmelink.powerio.writer.LogWriter;
import net.harmelink.powerio.writer.ModelWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class SerialReader {
    private static final Logger LOG = LoggerFactory.getLogger(SerialReader.class);

    private static final String PORT = "/dev/ttyUSB0";

    private static final char START_CHAR = '/';

    private static final char END_CHAR = '!';

    private static boolean testRun = false;

    private static boolean listPorts = false;

    public static void main(final String... args) {
        init(args);

        if (listPorts) {
            logAvailablePorts();
        }

        final SerialPort serialPort = new SerialPort(PORT);
        final SerialEventListener serialEventListener = new SerialEventListener(serialPort, getWriter());

        try {
            LOG.debug("Using port: {}", PORT);
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_7, SerialPort.STOPBITS_1,
                    SerialPort.PARITY_EVEN);
            serialPort.setEventsMask(SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR);
            serialPort.addEventListener(serialEventListener);
        } catch (final SerialPortException e) {
            LOG.error("Unable to open serial port: {}", e.getMessage());
        }
    }

    private static void init(final String... args) {
        final List<String> arguments = Arrays.asList(args);
        testRun = arguments.contains("-t");
        listPorts = arguments.contains("-l");
    }

    private static AbstractWriter getWriter() {
        if (testRun) {
            return new LogWriter(START_CHAR, END_CHAR);
        } else {
            return new ModelWriter(START_CHAR, END_CHAR);
        }
    }

    private static void logAvailablePorts() {
        LOG.debug("Available ports:");

        for (final String portName : SerialPortList.getPortNames()) {
            LOG.debug(portName);
        }
    }
}
