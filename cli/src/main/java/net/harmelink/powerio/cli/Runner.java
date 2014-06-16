package net.harmelink.powerio.cli;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import net.harmelink.powerio.mapper.Mapper;
import net.harmelink.powerio.mapper.p1.P1Mapper;
import net.harmelink.powerio.persistence.PrintWriter;
import net.harmelink.powerio.persistence.Writer;
import net.harmelink.powerio.persistence.rrd.RrdWriter;
import net.harmelink.powerio.reader.InputReader;
import net.harmelink.powerio.reader.serial.P1Reader;
import org.apache.commons.cli.*;

final class Runner extends AbstractModule {

    private static final String APP = "powerio";

    private static final String DEFAULT_PORT = "/dev/ttyUSB0";

    private final CommandLine commandLine;

    private Runner(final CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    @Override
    protected void configure() {
        bind(String.class).annotatedWith(Names.named("Port")).toInstance(getPort(commandLine));
        bind(Boolean.class).annotatedWith(Names.named("Debug")).toInstance(getDebug(commandLine));
        bind(Mapper.class).to(getMapper(commandLine));
        bind(Writer.class).to(getWriter(commandLine));
        bind(InputReader.class).to(getInputReader(commandLine));
    }

    public static void main(final String... args) {
        final CommandLine commandLine = getCommandLine(args);
        final Injector injector = Guice.createInjector(new Runner(commandLine));
        final Controller controller = injector.getInstance(Controller.class);

        controller.run();
    }

    private static Options getOptions() {
        final Options options = new Options();
        options.addOption("d", "debug", false, "Enable debug mode");
        options.addOption("h", "help", false, "Show this message");
        options.addOption("i", "in", true, "Input reader (default P1 reader)");
        options.addOption("m", "map", true, "Input mapper (default P1 mapper)");
        options.addOption("o", "out", true, "Output persistence (default print writer)");
        options.addOption("p", "port", true, "Port (default " + DEFAULT_PORT + ")");

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

    private static Class<? extends InputReader> getInputReader(final CommandLine commandLine) {
        if (commandLine.hasOption("i")) {
            switch (commandLine.getOptionValue("i")) {
                case "p1":
                    break;
                default:
                    System.out.println("Unknown input reader: " + commandLine.getOptionValue("i"));
                    System.exit(1);
            }
        }

        System.out.println("Using P1 reader");
        return P1Reader.class;
    }

    private static String getPort(final CommandLine commandLine) {
        if (commandLine.hasOption("p")) {
            System.out.println("Using port " + commandLine.getOptionValue("p"));
            return commandLine.getOptionValue("p");
        }

        System.out.println("Using port " + DEFAULT_PORT);
        return DEFAULT_PORT;
    }

    private static Class<? extends Writer> getWriter(final CommandLine commandLine) {
        if (commandLine.hasOption("o")) {
            switch (commandLine.getOptionValue("o")) {
                case "rrd":
                    System.out.println("Using round robin database");
                    return RrdWriter.class;
                case "print":
                    break;
                case "elastic":
                default:
                    System.out.println("Unknown output: " + commandLine.getOptionValue("o"));
                    System.exit(1);
            }
        }

        System.out.println("Using print writer");
        return PrintWriter.class;
    }

    private static Class<? extends Mapper> getMapper(final CommandLine commandLine) {
        if (commandLine.hasOption("m")) {
            switch (commandLine.getOptionValue("m")) {
                case "p1":
                    break;
                default:
                    System.out.println("Unknown mapper: " + commandLine.getOptionValue("m"));
                    System.exit(1);
            }
        }

        System.out.println("Using P1 mapper");
        return P1Mapper.class;
    }

    private static Boolean getDebug(final CommandLine commandLine) {
        if (commandLine.hasOption("d")) {
            System.out.println("Running in debug mode");
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }
}
