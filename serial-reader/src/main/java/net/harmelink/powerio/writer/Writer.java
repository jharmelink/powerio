package net.harmelink.powerio.writer;

import java.io.IOException;

public interface Writer {
    void write(final byte[] input) throws IOException;
}
