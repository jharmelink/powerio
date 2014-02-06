package net.harmelink.powerio.writer;

import java.io.IOException;

public interface Writer {
    /**
     * Writes a byte buffer to a message {@link String}.
     *
     * @param input a byte buffer
     * @throws IOException
     */
    void write(final byte[] input) throws IOException;
}
