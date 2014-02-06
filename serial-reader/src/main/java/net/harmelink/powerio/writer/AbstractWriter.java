package net.harmelink.powerio.writer;

import java.io.IOException;

public abstract class AbstractWriter implements Writer {
    private final StringBuffer dataBuffer = new StringBuffer();

    private final char startChar;

    private final char endChar;

    public AbstractWriter(final char startChar, final char endChar) {
        this.startChar = startChar;
        this.endChar = endChar;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(final byte[] input) throws IOException {
        final String inputString = new String(input);

        if (inputString.contains(String.valueOf(endChar)) && dataBuffer.length() > 0) {
            dataBuffer.append(inputString.substring(0, inputString.indexOf(endChar) - 1));
            writeMessage(dataBuffer.toString());
            dataBuffer.setLength(0);
        }

        if (inputString.contains(String.valueOf(startChar))) {
            dataBuffer.append(inputString.substring(inputString.indexOf(startChar) + 1));
        } else if (dataBuffer.length() > 0) { // Don't start a message without a start character
            dataBuffer.append(inputString);
        }
    }

    protected abstract void writeMessage(final String message) throws IOException;
}
