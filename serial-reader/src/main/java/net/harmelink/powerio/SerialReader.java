package net.harmelink.powerio;

import jssc.SerialPort;
import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerialReader {
    private static final Logger LOG = LoggerFactory.getLogger(SerialReader.class);

    public static void main(final String... args) {
        final SerialPort serialPort = new SerialPort("/dev/ttyUSB0");

        try {
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_7, SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
//            serialPort.writeBytes("This is a test string".getBytes());//Write data to port
//            serialPort.readBytes();
//            serialPort.closePort();
            //final int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR; // Prepare mask
            //serialPort.setEventsMask(mask); // Set mask
            serialPort.addEventListener(new SerialEventListener(serialPort)); // Add SerialPortEventListener
        } catch (final SerialPortException e) {
            LOG.error("Unable to open seial port: {}", e.getMessage());
        }
    }
}
