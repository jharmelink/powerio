package net.harmelink.powerio.reader.serial;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import net.harmelink.powerio.persistence.Writer;
import net.harmelink.powerio.reader.InputReader;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;

public class P1Reader implements InputReader {

    private final String port;

    private final Writer writer;

    private final boolean debug;

    @Inject
    public P1Reader(@Named("Port") final String port, final Writer writer, @Named("Debug") final Boolean debug) {
        this.port = port;
        this.writer = writer;
        this.debug = debug;
    }

    public void start() {
        final BufferedOutputStream outputStream = new BufferedOutputStream(new ByteArrayOutputStream() {
            private final StringBuffer dataBuffer = new StringBuffer();

            @Override
            public void write(byte[] bytes) {
                final String inputString = new String(bytes);

                if (inputString.contains("!") && dataBuffer.length() > 0) {
                    dataBuffer.append(inputString.substring(0, inputString.indexOf('!') - 1));
                    writer.write(dataBuffer.toString());
                    dataBuffer.setLength(0);
                }

                if (inputString.contains("/")) {
                    dataBuffer.append(inputString.substring(inputString.indexOf('/') + 1));
                } else if (dataBuffer.length() > 0) { // Don't start a message without a start character
                    dataBuffer.append(inputString);
                }
            }
        });
        SerialReader.newInstance(port, outputStream, debug).start();
    }
}
