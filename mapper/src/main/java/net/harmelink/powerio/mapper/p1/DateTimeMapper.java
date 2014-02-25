package net.harmelink.powerio.mapper.p1;

import net.harmelink.powerio.mapper.AbstractMapper;
import net.harmelink.powerio.mapper.Mapper;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeMapper extends AbstractMapper implements Mapper<String> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yymmddHHmmss");

    @Override
    public String map(final String dateTimeString) throws InstantiationException, IllegalAccessException {
        return FORMATTER.parseDateTime(dateTimeString).toString();
    }
}
