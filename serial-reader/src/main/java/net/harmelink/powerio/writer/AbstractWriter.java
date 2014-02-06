package net.harmelink.powerio.writer;

import java.io.IOException;

public abstract class AbstractWriter implements Writer {
    private final StringBuffer dataBuffer = new StringBuffer();

    private final char endChar;

    public AbstractWriter(final char endChar) {
        this.endChar = endChar;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(final byte[] input) throws IOException {
        final String inputString = new String(input);

        if (inputString.contains(String.valueOf(endChar))) {
            if (dataBuffer.length() > 0) {
                dataBuffer.append(inputString.substring(0, inputString.indexOf(endChar) - 1));
                writeMessage(dataBuffer.toString());
                dataBuffer.setLength(0);
            }

            dataBuffer.append(inputString.substring(inputString.indexOf(endChar)));
        } else {
            dataBuffer.append(inputString);
        }
    }

    protected abstract void writeMessage(final String message) throws IOException;
}
