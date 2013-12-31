package net.harmelink.powerio;

import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.TooManyListenersException;

public class SimpleRead implements Runnable, SerialPortEventListener {
    private InputStream inputStream;
    private SerialPort serialPort;
    private Thread readThread;

    public SimpleRead(final CommPortIdentifier portId) {
        try {
            serialPort = portId.open("SimpleReadApp", 2000);
        } catch (final PortInUseException e) {
            System.out.println(e);
        }
        try {
            inputStream = serialPort.getInputStream();
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            serialPort.addEventListener(this);
        } catch (TooManyListenersException e) {
            System.out.println(e);
        }
        serialPort.notifyOnDataAvailable(true);
        try {
            serialPort.setSerialPortParams(9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
        } catch (final UnsupportedCommOperationException e) {
            System.out.println(e);
        }
        readThread = new Thread(this);
        readThread.start();
    }

    public void run() {
        try {
            Thread.sleep(20000);
        } catch (final InterruptedException e) {
            System.out.println(e);
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
                    System.out.print(new String(readBuffer));
                } catch (IOException e) {
                    System.out.println(e);
                }
                break;
        }
    }
}