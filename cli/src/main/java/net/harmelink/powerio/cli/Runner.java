package net.harmelink.powerio.cli;

import net.harmelink.powerio.mapper.Mapper;
import net.harmelink.powerio.mapper.p1.TelegramMapper;
import net.harmelink.powerio.reader.InputReader;
import net.harmelink.powerio.reader.serial.SerialReader;
import net.harmelink.powerio.writer.Writer;
import net.harmelink.powerio.writer.log.LogWriter;
import org.apache.commons.cli.*;

public final class Runner {
    private static final String APP = "powerio";

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
        options.addOption("d", "debug", false, "Enable debug mode");
        options.addOption("h", "help", false, "Show this message");
        options.addOption("i", "in", true, "Input reader (default p1)");
        options.addOption("m", "map", true, "Input mapper (default mapper)");
        options.addOption("o", "out", true, "Output writer (default mongo)");
        options.addOption("p", "port", true, "Port (default /dev/ttyUSB0)");

        return options;
    }

    private static CommandLine getCommandLine(final String... args) {
        final CommandLineParser parser = new DefaultParser();
        final Options options = getOptions();

        try {
            final CommandLine commandLine = parser.parse(options, args);
            if (commandLine.hasOption("h")) {
                showHelp(options);
            }

            return commandLine;
        } catch (final ParseException e) {
            System.out.println(APP + ": " + e.getMessage());
            showHelp(options);
        }

        return null;
    }

    private static void showHelp(final Options options) {
        final HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp(APP, options);
        System.exit(1);
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
