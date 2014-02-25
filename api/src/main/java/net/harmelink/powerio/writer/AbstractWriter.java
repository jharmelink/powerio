package net.harmelink.powerio.writer;

import net.harmelink.powerio.mapper.Mapper;

import java.io.IOException;

public abstract class AbstractWriter implements Writer {
    private final StringBuffer dataBuffer = new StringBuffer();

    private final Mapper mapper;

    public AbstractWriter(final Mapper mapper) {
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(final byte[] input) throws IOException {
        final String inputString = new String(input);

        if (inputString.contains(String.valueOf(mapper.getEndChar())) && dataBuffer.length() > 0) {
            dataBuffer.append(inputString.substring(0, inputString.indexOf(mapper.getEndChar()) - 1));
            writeMessage(dataBuffer.toString());
            dataBuffer.setLength(0);
        }

        if (inputString.contains(String.valueOf(mapper.getStartChar()))) {
            dataBuffer.append(inputString.substring(inputString.indexOf(mapper.getStartChar()) + 1));
        } else if (dataBuffer.length() > 0) { // Don't start a message without a start character
            dataBuffer.append(inputString);
        }
    }

    protected abstract void writeMessage(final String message) throws IOException;
}
