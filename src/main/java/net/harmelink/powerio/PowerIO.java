package net.harmelink.powerio;

import gnu.io.CommPortIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class PowerIO {
    private static final Logger LOG = LoggerFactory.getLogger(PowerIO.class);

    private static final String SERIAL_PORT_NAME = getProperty("serial.port.name");

    public static void main(final String[] args) {
        LOG.debug("Java extensions paths: {}", System.getProperty("java.ext.dirs"));
        final CommPortIdentifier portId = getSerialPort();

        if (portId != null) {
            final SimpleRead reader = new SimpleRead(portId);
        }
    }

    private static CommPortIdentifier getSerialPort() {
        final Enumeration portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            final CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
            LOG.debug("Found port {}", portId.getName());

            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals(SERIAL_PORT_NAME)) {
                    LOG.debug("Using port {}", SERIAL_PORT_NAME);
                    return portId;
                }
            }
        }

        LOG.error("No serial port found");
        return null;
    }

    private static String getProperty(final String name) {
        final Properties properties = new Properties();
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final InputStream stream = loader.getResourceAsStream("powerio.properties");

        try {
            properties.load(stream);
        } catch (final IOException e) {
            LOG.error("Error loading properties file.\n{}", e.getMessage());
            e.printStackTrace();
        }

        return properties.getProperty(name);
    }
}
