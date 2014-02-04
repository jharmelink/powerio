package net.harmelink.powerio;

import gnu.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.TooManyListenersException;

public class SimpleRead implements Runnable, SerialPortEventListener {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleRead.class);

    private final CommPortIdentifier portId;

    private final int serialPortBaudrate;

    private final ModelWriter modelWriter = new ModelWriter();

    private InputStream inputStream;

    public SimpleRead(final CommPortIdentifier portId, final int serialPortBaudrate) {
        this.portId = portId;
        this.serialPortBaudrate = serialPortBaudrate;
    }

    public void start() {
        try {
            final SerialPort serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
            inputStream = serialPort.getInputStream();
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            serialPort.setSerialPortParams(serialPortBaudrate, SerialPort.DATABITS_7, SerialPort.STOPBITS_1,
                    SerialPort.PARITY_EVEN);
        } catch (final PortInUseException e) {
            LOG.error("Port in use. {}", e.getMessage());
        } catch (final IOException e) {
            LOG.error("No serial port found. {}", e.getMessage());
        } catch (final TooManyListenersException e) {
            LOG.error("Too many listeners. {}", e.getMessage());
        } catch (final UnsupportedCommOperationException e) {
            LOG.error("Unsupported communication operation. {}", e.getMessage());
        }

        final Thread readThread = new Thread(this);
        readThread.start();
    }

    public void run() {
        try {
            Thread.sleep(10000);
        } catch (final InterruptedException e) {
            LOG.error(e.getMessage());
        }
    }

    public void serialEvent(final SerialPortEvent event) {
        switch (event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:
                final byte[] readBuffer = new byte[20];

                try {
                    while (inputStream.available() > 0) {
                        int numBytes = inputStream.read(readBuffer);
                    }
                    modelWriter.write(readBuffer);
                } catch (final IOException e) {
                    LOG.error("Unable to read serial event. {}", e.getMessage());
                }
                break;
        }
    }
}