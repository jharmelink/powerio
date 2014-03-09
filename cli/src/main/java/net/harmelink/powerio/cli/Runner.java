package net.harmelink.powerio.cli;

import net.harmelink.powerio.mapper.Mapper;
import net.harmelink.powerio.mapper.p1.TelegramMapper;
import net.harmelink.powerio.reader.InputReader;
import net.harmelink.powerio.reader.serial.SerialReader;
import net.harmelink.powerio.writer.Writer;
import net.harmelink.powerio.writer.log.LogWriter;
import org.apache.commons.cli.*;

public final class Runner {
    private static final String DEFAULT_PORT = "/dev/ttyUSB0";

    private static InputReader inputReader;

    private Runner() {
    }

    public static void main(final String... args) {
        final CommandLine commandLine = getCommandLine(args);
        setInputReader(commandLine);
        inputReader.start();
    }

    private static Options getOptions() {
        final Options options = new Options();
        options.addOption("i", true, "input reader (default serial)");
        options.addOption("m", true, "input mapper (default mapper)");
        options.addOption("o", true, "output writer (default mongo)");
        options.addOption("p", true, "port (default /dev/ttyUSB0)");

        return options;
    }

    private static CommandLine getCommandLine(final String... args) {
        final CommandLineParser parser = new DefaultParser();

        try {
            return parser.parse(getOptions(), args);
        } catch (final ParseException e) {
            System.out.println("Unable to read command line options.");
            System.exit(1);
            return null;
        }
    }

    private static void setInputReader(final CommandLine commandLine) {
        final String port = getPort(commandLine);
        final Writer writer = getWriter(commandLine);
        final boolean debugMode = commandLine.hasOption("d");

        if (commandLine.hasOption("i")) {
            switch (commandLine.getOptionValue("i")) {
                case "p1":
                    inputReader = new SerialReader(port, writer, debugMode);
            }
        } else {
            inputReader = new SerialReader(port, writer, debugMode);
        }
    }

    private static String getPort(final CommandLine commandLine) {
        if (commandLine.hasOption("p")) {
            return commandLine.getOptionValue("p");
        } else {
            return DEFAULT_PORT;
        }
    }

    private static Writer getWriter(final CommandLine commandLine) {
        final Mapper mapper = getMapper(commandLine);

        if (commandLine.hasOption("o")) {
            switch (commandLine.getOptionValue("o")) {
                default:
                    System.out.println("Unknown output: " + commandLine.getOptionValue("o"));
                    System.exit(1);
                    return null;
            }
        } else {
            return new LogWriter(mapper);
        }
    }

    private static Mapper getMapper(final CommandLine commandLine) {
        if (commandLine.hasOption("m")) {
            switch (commandLine.getOptionValue("m")) {
                default:
                    System.out.println("Unknown mapper: " + commandLine.getOptionValue("m"));
                    System.exit(1);
                    return null;
            }
        } else {
            return new TelegramMapper();
        }
    }
}
