package net.harmelink.powerio.cli;

import com.google.inject.Inject;
import net.harmelink.powerio.reader.InputReader;

class Controller implements Runnable {

    private final InputReader inputReader;

    @Inject
    public Controller(final InputReader inputReader) {
        this.inputReader = inputReader;
    }

    @Override
    public void run() {
        inputReader.start();
    }
}
