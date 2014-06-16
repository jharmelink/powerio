package net.harmelink.powerio.mapper.p1;

import net.harmelink.powerio.mapper.RegexMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeMapper extends RegexMapper<String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss");

    @Override
    public String mapToObject(final String dateTimeString) {
        final LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, FORMATTER);
        return ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).toString();
    }
}
