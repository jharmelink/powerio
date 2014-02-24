package net.harmelink.powerio.mapper;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeMapper implements Mapper<String> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yymmddHHmmss");

    @Override
    public String map(final String dateTimeString) throws InstantiationException, IllegalAccessException {
        return FORMATTER.parseDateTime(dateTimeString).toString();
    }
}
