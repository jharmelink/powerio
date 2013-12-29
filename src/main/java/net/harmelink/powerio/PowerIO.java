package net.harmelink.powerio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.comm.CommPortIdentifier;
import java.util.Enumeration;

public class PowerIO {
    private static final Logger LOG = LoggerFactory.getLogger(PowerIO.class);

    private static final String SERIAL_PORT_NAME = "COM1";

    public static void main(final String[] args) {
        final CommPortIdentifier portId = getSerialPort();
        final SimpleRead reader = new SimpleRead(portId);
    }

    private static CommPortIdentifier getSerialPort() {
        final Enumeration portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            final CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();

            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals(SERIAL_PORT_NAME)) {
                    LOG.error("Found serial port " + SERIAL_PORT_NAME);
                    return portId;
                }
            }
        }

        LOG.error("No serial port found");
        return null;
    }
}