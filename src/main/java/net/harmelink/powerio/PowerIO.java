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

    public static void main(final String[] args) {
        new PowerIO(initProperties());
    }

    public PowerIO(final Properties properties) {
        final String serialPortName = properties.getProperty("serial.port.name");
        final int serialPortBaudrate = Integer.valueOf(properties.getProperty("serial.port.baudrate"));
        final CommPortIdentifier portId = getSerialPort(serialPortName);

        if (portId != null) {
            final SimpleRead reader = new SimpleRead(portId, serialPortBaudrate);
        }
    }

    private CommPortIdentifier getSerialPort(final String serialPortName) {
        final Enumeration portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            final CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
            LOG.debug("Found port {}", portId.getName());

            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals(serialPortName)) {
                    LOG.debug("Using port {}", serialPortName);
                    return portId;
                }
            }
        }

        LOG.error("No serial port found");
        return null;
    }

    private static Properties initProperties() {
        final Properties properties = new Properties();
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final InputStream stream = loader.getResourceAsStream("powerio.properties");

        try {
            properties.load(stream);
        } catch (final IOException e) {
            LOG.error("Error loading properties file. {}", e.getMessage());
            e.printStackTrace();
        }

        return properties;
    }
}
