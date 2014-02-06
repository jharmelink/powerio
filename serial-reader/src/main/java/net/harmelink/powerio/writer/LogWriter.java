package net.harmelink.powerio.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LogWriter implements Writer {
    private static final Logger LOG = LoggerFactory.getLogger(ModelWriter.class);

    private static final char END_CHAR = '!';

    private StringBuffer dataBuffer = new StringBuffer();

    public void write(final byte[] input) throws IOException {
        final String inputString = new String(input);

        if (inputString.contains(String.valueOf(END_CHAR))) {
            if (dataBuffer.length() == 0) {
                dataBuffer.append(inputString.substring(inputString.indexOf(END_CHAR)));
            } else {
                dataBuffer.append(inputString.substring(0, inputString.indexOf(END_CHAR) - 1));
                LOG.debug(dataBuffer.toString());
                dataBuffer.setLength(0);
            }
        } else {
            dataBuffer.append(inputString);
        }
    }
}
