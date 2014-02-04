package net.harmelink.powerio;

import gnu.io.CommPortIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

import java.util.Enumeration;

public class SerialReader extends Verticle {
    private static final Logger LOG = LoggerFactory.getLogger(SerialReader.class);

    @Override
    public void start() {
        super.start();
        final JsonObject vertxConfig = container.config();
        final String serialPortName = vertxConfig.getString("serial.port.name");
        final int serialPortBaudrate = vertxConfig.getInteger("serial.port.baudrate");
        final CommPortIdentifier portId = getSerialPort(serialPortName);

        if (portId != null) {
            new SimpleRead(portId, serialPortBaudrate).start();
        }
    }

    private CommPortIdentifier getSerialPort(final String serialPortName) {
        System.setProperty("gnu.io.rxtx.SerialPorts", serialPortName);
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

}
